package com.ibm.swg.iseries.dataqueue;

import com.ibm.as400.access.AS400;
import com.ibm.as400.access.BaseDataQueue;
import com.ibm.broker.plugin.MbBLOB;
import com.ibm.broker.plugin.MbElement;
import com.ibm.broker.plugin.MbException;
import com.ibm.broker.plugin.MbInputTerminal;
import com.ibm.broker.plugin.MbMessage;
import com.ibm.broker.plugin.MbMessageAssembly;
import com.ibm.broker.plugin.MbNodeInterface;
import com.ibm.broker.plugin.MbOutputTerminal;
import com.ibm.broker.plugin.MbUserException;
import com.ibm.swg.iseries.DataQueueFlowNode;
import com.ibm.swg.iseries.cache.ConfigurationData;
import com.ibm.swg.iseries.cache.ConnectionCache;
import com.ibm.swg.iseries.communication.ISeriesCall;

/**
 * DataQueueGetNode.java, May 10, 2011
 * 
 * Copyright (c) 2011 International Business Machines Corp. All rights reserved.
 * 
 * This software is the confidential and proprietary information of the IBM Corporation. ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with IBM.
 * 
 * @author Jochen_Benke@de.ibm.com
 * 
 */
public class DataQueueGetNode extends DataQueueFlowNode implements MbNodeInterface {
	/**
	 * 
	 */
	private int waitInterval = -1;

	/**
	 * @throws MbException
	 */
	public DataQueueGetNode() throws MbException {
		createInputTerminal("in");
		createOutputTerminal("out");
		createOutputTerminal("noMessage");
		createOutputTerminal("failure");
	}

	@Override
	public void evaluate(MbMessageAssembly inAssembly, MbInputTerminal inTerm) throws MbException {
		MbOutputTerminal out = getOutputTerminal("out");
		MbOutputTerminal noMessage = getOutputTerminal("noMessage");

		MbMessage inMessage = inAssembly.getMessage();
		loadPolicy();

		// connection to the AS400
		AS400 system = null;

		try {
			// the local environment will overwrite the node properties
			getPropertiesFromLocalEnvironment(inAssembly);

			// setTrace directory
			if (enable == true) {
				ISeriesCall.enableTrace(buildFileName());
			}

			// connect to i series
			system = ConnectionCache.getConnection("UserDefined", getPolicy());
			// get data queue
			BaseDataQueue baseDataQueue = dataQueueFactory.getDataQueue(system, library, queueName, type);
			// get CCSID
			ConfigurationData configurationData = ConnectionCache.getConfigurationData("UserDefined", getPolicy());
			String ccsid = configurationData.get_CCSID();
			// get message from data queue
			byte[] message = dataQueueFactory.getMessage(baseDataQueue, key, searchType, waitInterval,ccsid);
			
			MbMessageAssembly outAssembly = new MbMessageAssembly(inAssembly, inMessage);
			
			if (message != null) {
				// Build the output message assembly before propagating the
				// message.
				MbMessage localEnvironment = outAssembly.getLocalEnvironment();
				MbElement outParser = localEnvironment.getRootElement().createElementAsLastChild(MbBLOB.PARSER_NAME);
				outParser.createElementAsLastChild(MbElement.TYPE_NAME_VALUE, "BLOB", message);

				// The following should only be changed if not propagating
				// message
				out.propagate(outAssembly);
			} else {
				noMessage.propagate(outAssembly);
			}

		} catch (MbUserException e) {
			throw e;
		} finally {
			// turn off the tracing
			ISeriesCall.disableTrace();

			// return the connection to the iSeries connection pool
			ConnectionCache.releaseConnection("UserDefined", getPolicy(), system);
		}
	}

	/**
	 * Override queue values, if local environment is propagated
	 * 
	 * @param inAssembly
	 * @throws MbUserException
	 */
	private void getPropertiesFromLocalEnvironment(MbMessageAssembly inAssembly) throws MbUserException {
		try {
			// get local environment structure to override the default settings
			MbElement localEnvironment = inAssembly.getLocalEnvironment().getRootElement().getFirstElementByPath(
					"DataQueue");

			if (localEnvironment != null) {
				// change the data queu name
				MbElement currentProperty = localEnvironment.getFirstElementByPath("queueName");
				if (currentProperty != null) {
					queueName = (String) currentProperty.getValue();
				}

				// change the library name where the queue ist placed
				currentProperty = localEnvironment.getFirstElementByPath("library");
				if (currentProperty != null) {
					library = (String) currentProperty.getValue();
				}

				// change the type of the data queue
				currentProperty = localEnvironment.getFirstElementByPath("type");
				if (currentProperty != null) {
					type = (String) currentProperty.getValue();
				}

				// change the type of the data queue
				currentProperty = localEnvironment.getFirstElementByPath("waitInterval");
				if (currentProperty != null) {
					waitInterval = Integer.parseInt((String) currentProperty.getValue());
				}
				
				// change the type of the data queue
				currentProperty = localEnvironment.getFirstElementByPath("keyed");
				if (currentProperty != null) {
					String isKeyed = (String) currentProperty.getValue();
					
					setKeyed(isKeyed);
				}
				
				// change the type of the data queue
				currentProperty = localEnvironment.getFirstElementByPath("key");
				if (currentProperty != null) {
					key = (String) currentProperty.getValue();
				}

				// change the type of the data queue
				currentProperty = localEnvironment.getFirstElementByPath("searchType");
				if (currentProperty != null) {
					searchType = (String) currentProperty.getValue();
				}
			}
		} catch (MbException e) {
			throw new MbUserException(e, "getPropertiesFromLocalEnvironment", "ISeriesProgramCall", e.getMessage(),
					"Get Local Configuration", null);
		}
	}

	/**
	 * @return
	 */
	public static String getNodeName() {
		return "com_ibm_swg_iseries_dataqueue_DataQueueGetNode";
		//return "DataQueueGetNode";
	}

	/**
	 * @return
	 */
	public String getWaitInterval() {
		return Integer.toString(waitInterval);
	}

	/**
	 * @param waitInterval
	 */
	public void setWaitInterval(String waitInterval) {
		this.waitInterval = Integer.parseInt(waitInterval);
	}
}