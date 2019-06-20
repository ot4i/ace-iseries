package com.ibm.swg.iseries.pcml;

import com.ibm.as400.access.AS400;
import com.ibm.broker.plugin.MbElement;
import com.ibm.broker.plugin.MbException;
import com.ibm.broker.plugin.MbInputTerminal;
import com.ibm.broker.plugin.MbMessage;
import com.ibm.broker.plugin.MbMessageAssembly;
import com.ibm.broker.plugin.MbNodeInterface;
import com.ibm.broker.plugin.MbOutputTerminal;
import com.ibm.broker.plugin.MbUserException;
import com.ibm.broker.plugin.MbXMLNSC;
import com.ibm.swg.iseries.FlowNode;
import com.ibm.swg.iseries.cache.ConnectionCache;
import com.ibm.swg.iseries.cache.TemplateCache;
import com.ibm.swg.iseries.communication.ISeriesCall;

/**
 * ISeriesPcmlCallNode.java, May 16, 2011, 2011
 * 
 * Copyright (c) 2011 International Business Machines Corp. All rights reserved.
 * 
 * This software is the confidential and proprietary information of the IBM
 * Corporation. ("Confidential Information"). You shall not disclose such
 * Confidential Information and shall use it only in accordance with the terms
 * of the license agreement you entered into with IBM.
 * 
 * @author Jochen_Benke@de.ibm.com
 * 
 */
/**
 * ISeriesPcmlCallNode.java, May 20, 2011, 2011
 * 
 * Copyright (c) 2011 International Business Machines Corp. All rights reserved.
 * 
 * This software is the confidential and proprietary information of the IBM
 * Corporation. ("Confidential Information"). You shall not disclose such
 * Confidential Information and shall use it only in accordance with the terms
 * of the license agreement you entered into with IBM.
 * 
 * @author Jochen_Benke@de.ibm.com
 * 
 */
public class ISeriesPcmlCallNode extends FlowNode implements MbNodeInterface {
	/**
	 * name of the pcml call
	 */
	private String pcmlCall = null;
	
	/**
	 * identifier if call is an xpcml call or not
	 */
	private boolean isXPcml = false;
	
	/**
	 * list of the nees libraries separated by comma
	 */
	private String libraryList = null;
	
	/**
	 * style sheet to transform to pcml
	 */
	private String toPcml = "transformToPcml.xsl";
	
	/**
	 * style sheet to transform to pcml
	 */
	private String toXPcml = "transformToXPcml.xsl";
	
	/**
	 * style sheet to transform from pcml
	 */
	private String fromXPcml = "transformFromXPcml.xsl";
	
	/**
	 * cache for holding the the compiled style sheets
	 */
	private TemplateCache templateCache = null;

	/**
	 * for holding the the input CCSID
	 */
	private int ccsid = 1208;
	
	/**
	 * cache stored in thread
	 */
	private static ThreadLocal<TemplateCache> _localThread_templateCache = new ThreadLocal<TemplateCache>();

	/**
	 * @throws MbException
	 */
	private AS400 system = null;
	
	public ISeriesPcmlCallNode() throws MbException {
		createInputTerminal("in");
		createOutputTerminal("out");
		createOutputTerminal("failure");
	}

	/* (non-Javadoc)
	 * @see com.ibm.broker.plugin.MbNodeInterface#evaluate(com.ibm.broker.plugin.MbMessageAssembly, com.ibm.broker.plugin.MbInputTerminal)
	 */
	@Override
	public void evaluate(MbMessageAssembly inAssembly, MbInputTerminal inTerm) throws MbException {

		MbOutputTerminal out = getOutputTerminal("out");
		MbMessage inMessage = inAssembly.getMessage();
		MbMessage outMessage = new MbMessage();

		copyMessageHeaders(inMessage, outMessage);
		loadPolicy();

		// the local environment will overwrite the node properties
		getPropertiesFromLocalEnvironment(inAssembly);
		
		// connection to the AS400
		if (system ==null){
			// connect to i series
			system = ConnectionCache.getConnection("UserDefined", getPolicy());
			// set required libraries for the pcml call
			ISeriesCall.setProgramCallLibraryList(system, getLibraryListAsArray());
		}

		try {
			// initialize data queue cache
			setTemplateCache();
			
			// setTrace directory
			if (enable == true) {
				ISeriesCall.enableTrace(buildFileName());
			}

			/* Message body is the last child of Root */
			MbElement msgBody = inAssembly.getMessage().getRootElement().getLastChild();
			
			String fromStyleSheet = null;
			String toStyleSheet = null;
			
			// call the pcml program
			if(isXPcml == false){
				toStyleSheet = buildPathToXsl(toPcml);
			}else {
				toStyleSheet = buildPathToXsl(toXPcml);
			}

			fromStyleSheet = buildPathToXsl(fromXPcml);
			
			byte[] msgByteStreamOut = ISeriesCall.callIseriesProgram(system, pcmlCall, msgBody, templateCache, toStyleSheet, fromStyleSheet, isXPcml, ccsid);
			
			// Build the output message assembly before propagating the message.
			outMessage.getRootElement().createElementAsLastChildFromBitstream(msgByteStreamOut, MbXMLNSC.PARSER_NAME, "", "", "", 0, 0, 0);
			MbMessageAssembly outAssembly = new MbMessageAssembly(inAssembly, outMessage);
			
			// The following should only be changed if not propagating message
			out.propagate(outAssembly);
		} catch (MbUserException e) {
			throw e;
		} finally {
			ISeriesCall.disableTrace();
			
			// return the connection to the iSeries connection pool
			// will be hold 
			// ConnectionCache.releaseConnection("UserDefined", getConfigurableService(), system);
			
			outMessage.clearMessage();

		}
	}

	/**
	 * @return
	 */
	public static String getNodeName() {
		return "com_ibm_swg_iseries_pcml_ISeriesPcmlCallNode";
		//return "ISeriesPcmlCallNode";
	}

	/**
	 * @return
	 */
	public String getPcmlCall() {
		return pcmlCall;
	}

	/**
	 * @param pcmlCall
	 */
	public void setPcmlCall(String pcmlCall) {
		this.pcmlCall = pcmlCall;
	}

	/**
	 * @return
	 */
	public String getIsXPcml() {
		return Boolean.toString(isXPcml);
	}

	/**
	 * @param isXPcml
	 */
	public void setIsXPcml(String isXPcml) {
		this.isXPcml = Boolean.parseBoolean(isXPcml);
	}

	/**
	 * @return
	 */
	public String getLibraryList() {
		return libraryList;
	}
	
	/**
	 * @return
	 */
	public String[] getLibraryListAsArray() {
		String[] libs = null;
		
		if (libraryList != null) {
			libs = libraryList.split(",");
		}
		
		return libs;
	}

	/**
	 * @param libraryList
	 */
	public void setLibraryList(String libraryList) {
		this.libraryList = libraryList;
	}

	/**
	 * @return
	 */
	public String getToPcml() {
		return toPcml;
	}

	/**
	 * @param toPcml
	 */
	public void setToPcml(String toPcml) {
		this.toPcml = toPcml;
	}

	/**
	 * @return
	 */
	public String getToXPcml() {
		return toXPcml;
	}

	/**
	 * @param toXPcml
	 */
	public void setToXPcml(String toXPcml) {
		this.toXPcml = toXPcml;
	}

	/**
	 * @return
	 */
	public String getFromXPcml() {
		return fromXPcml;
	}

	/**
	 * @param fromXPcml
	 */
	public void setFromXPcml(String fromXPcml) {
		this.fromXPcml = fromXPcml;
	}

	/**
	 * @param inAssembly
	 * @throws MbUserException
	 */
	private void getPropertiesFromLocalEnvironment(MbMessageAssembly inAssembly) throws MbUserException {
		try {
			// use external parameters
			// these properties can be overwritten by the LocalEnvironment
			MbElement localEnvironment = inAssembly.getLocalEnvironment().getRootElement().getFirstElementByPath("ISeriesPcmlCall");

			if (localEnvironment != null) {
				MbElement currentProperty = localEnvironment.getFirstElementByPath("pcmlCall");

				if (currentProperty != null) {
					pcmlCall = (String) currentProperty.getValue();
				}

				currentProperty = localEnvironment.getFirstElementByPath("pcmlCall");

				if (currentProperty != null) {
					isXPcml = Boolean.parseBoolean((String) currentProperty.getValue());
				}

				currentProperty = localEnvironment.getFirstElementByPath("libraryList");

				if (currentProperty != null) {
					libraryList = (String) currentProperty.getValue();
				}

				currentProperty = localEnvironment.getFirstElementByPath("toPcml");

				if (currentProperty != null) {
					toPcml = (String) currentProperty.getValue();
				}

				currentProperty = localEnvironment.getFirstElementByPath("toXPcml");

				if (currentProperty != null) {
					toXPcml = (String) currentProperty.getValue();
				}

				currentProperty = localEnvironment.getFirstElementByPath("fromXPcml");

				if (currentProperty != null) {
					fromXPcml = (String) currentProperty.getValue();
				}
			}
		} catch (MbException e) {
			throw new MbUserException(e, "getPropertiesFromLocalEnvironment", "ISeriesProgramCall", e.getMessage(), "Get Local Configuration", null);
		}
	}
	
	/**
	 * set the current data queue cache
	 * 
	 * @return
	 */
	protected void setTemplateCache() {
		// get the current cache from thread
		templateCache = _localThread_templateCache.get();

		// if cache not initialized is in thread create a new one
		if (templateCache == null) {
			templateCache = new TemplateCache();
			_localThread_templateCache.set(templateCache);
		}
	}

	public int getCcsid() {
		return ccsid;
	}

	public void setCcsid(int ccsid) {
		this.ccsid = ccsid;
	}
}