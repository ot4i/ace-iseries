package com.ibm.swg.iseries;

import java.util.Properties;
import java.util.UUID;

import com.ibm.broker.plugin.MbElement;
import com.ibm.broker.plugin.MbException;
import com.ibm.broker.plugin.MbInputNode;
import com.ibm.broker.plugin.MbMessage;
import com.ibm.broker.plugin.MbPolicy;
import com.ibm.broker.plugin.MbService;
import com.ibm.swg.iseries.FlowNode.FlowNodeMessages;
import com.ibm.swg.iseries.cache.ConfigurationData;
import com.ibm.swg.iseries.cache.ConnectionCache;

/**
 * InputNode.java, May 18, 2011, 2011
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
public abstract class InputNode extends MbInputNode {
	/**
	 * controls the tracing facility, if true a trace file is written
	 */
	protected boolean enable = false;

	/**
	 * path to write the trace files, if blank the trace get written to brokers work path
	 */
	protected String directory = "";

	/**
	 * name of used policy
	 */
	protected String policyName = "myISeries";

	/**
	 * name of project containing the policy
	 */
	protected String policyProject = "IAM7";

	/**
	 * @return
	 */
	public String getPolicy() {
		return policyName;
	}

	/**
	 * @param configurableService
	 */
	public void setPolicy(String policyName) {
		this.policyName = policyName;
	}

	/**
	 * @return
	 */
	public String getEnable() {
		return Boolean.toString(enable);
	}

	/**
	 * @param enable
	 */
	public void setEnable(String enable) {
		this.enable = Boolean.parseBoolean(enable);
	}

	/**
	 * @return
	 */
	public String getDirectory() {
		return directory;
	}

	/**
	 * @param traceDirectory
	 */
	public void setDirectory(String traceDirectory) {
		this.directory = traceDirectory;
	}


	public InputNode() throws MbException {
		super();
		}
	
	public void loadPolicy() throws MbException {
		ConfigurationData configurationData = ConnectionCache
				.getConfigurationData("UserDefined", policyName);

		if (configurationData == null) {
			Properties properties = readPolicy("UserDefined",
					policyName);

			ConnectionCache.setISeriesPool("UserDefined", policyName,
					properties);
		}
	}
	/**
	 * Function which builds the trace path und trace filename for the current run.
	 * 
	 * @return
	 */
	protected String buildFileName() {
		String fileName = null;

		// create a unique identifier for the run
		UUID uuid = UUID.randomUUID();

		// get the java tmp directory
		String tmpPath = System.getProperty("java.io.tmpdir");

		// get the operation system of the broker
		String OS = System.getProperty("os.name").toLowerCase();

		// if no trace directory is provided the the java tmp dir as trace
		// directory
		if (directory == null || directory == "") {
			setDirectory(tmpPath);
		}

		// create the trace file name is unix or windows style
		if (OS.indexOf("windows") > -1) {
			fileName = getDirectory() + "\\" + "Trace_PCML_" + uuid.toString();
		} else {
			fileName = getDirectory() + "/" + "Trace_PCML_" + uuid.toString();
		}

		return fileName;
	}
	
	/**
	 * @param fileName
	 * @return
	 */
	protected String buildPathToXsl(String fileName) {
		String mbWorkPath = System.getProperty("broker.localWorkpath");
		String OS = System.getProperty("os.name").toLowerCase();

		if (OS.indexOf("windows") > -1) {
			if (mbWorkPath != null) {
				fileName = mbWorkPath + "\\XSL\\" + fileName;
			}
		} else {
			if (mbWorkPath != null) {
				fileName = mbWorkPath + "/XSL/" + fileName;
			}
		}

		return fileName;
	}

	/**
	 * Get the policy from the policyProject
	 * 
	 * @param policyName
	 * @return
	 * @throws MbException
	 */
	private Properties readPolicy(String policyType, String policyName)
			throws MbException {
		Properties iSeriesProps = new Properties();
		try {
			MbPolicy myISeries = getPolicy(policyType, "{"+policyProject+"}:"+policyName);
			if (myISeries == null) {
				MbService.logError(this, "readPolicy",
						FlowNodeMessages.MESSAGE_SOURCE,
						FlowNodeMessages.Error, "Policy: "
								+ policyName
								+ " does not exists.", null);
			}
			if (myISeries != null) {
				try {
					iSeriesProps.setProperty("iSeriesAddress", myISeries.getPropertyValueAsString("iSeriesAddress"));
					iSeriesProps.setProperty("iSeriesUser", myISeries.getPropertyValueAsString("iSeriesUser"));
					iSeriesProps.setProperty("iSeriesPassword", myISeries.getPropertyValueAsString("iSeriesPassword"));
					iSeriesProps.setProperty("CCSID", myISeries.getPropertyValueAsString("CCSID"));
					iSeriesProps.setProperty("maxConnections", myISeries.getPropertyValueAsString("maxConnections"));
					iSeriesProps.setProperty("minConnections", myISeries.getPropertyValueAsString("minConnections"));
					iSeriesProps.setProperty("maxUseTimeout", myISeries.getPropertyValueAsString("maxUseTimeout"));
			
				} catch (Exception e) {
					MbService.logError(this, "readPolicy",
							FlowNodeMessages.MESSAGE_SOURCE,
							FlowNodeMessages.Error,
							"Error processing Policy: "
									+ policyName, null);	
				}		
			}
			
		} catch (Exception e) {
			MbService.logError(this, "readPolicy",
					FlowNodeMessages.MESSAGE_SOURCE, FlowNodeMessages.Error,
					"Exception: "
							+ e.toString(), null);
		}
		return iSeriesProps;
	}

	/**
	 * Copy message header for the message
	 * 
	 * @param inMessage
	 * @param outMessage
	 * @throws MbException
	 */
	protected void copyMessageHeaders(MbMessage inMessage, MbMessage outMessage) throws MbException {
		MbElement outRoot = outMessage.getRootElement();

		// iterate though the headers starting with the first child of the root
		// element
		MbElement header = inMessage.getRootElement().getFirstChild();

		while (header != null && header.getNextSibling() != null) {
			// copy the header and add it to the out message
			outRoot.addAsLastChild(header.copy());

			// move along to next header
			header = header.getNextSibling();
		}
	}
}
