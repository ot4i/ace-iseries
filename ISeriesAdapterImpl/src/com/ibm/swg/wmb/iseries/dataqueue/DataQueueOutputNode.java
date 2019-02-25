package com.ibm.swg.wmb.iseries.dataqueue;

import com.ibm.as400.access.AS400;
import com.ibm.as400.access.AS400Text;
import com.ibm.as400.access.BaseDataQueue;
import com.ibm.broker.plugin.MbElement;
import com.ibm.broker.plugin.MbException;
import com.ibm.broker.plugin.MbInputTerminal;
import com.ibm.broker.plugin.MbMessage;
import com.ibm.broker.plugin.MbMessageAssembly;
import com.ibm.broker.plugin.MbNodeInterface;
import com.ibm.broker.plugin.MbOutputTerminal;
import com.ibm.broker.plugin.MbUserException;
import com.ibm.swg.wmb.iseries.DataQueueFlowNode;
import com.ibm.swg.wmb.iseries.cache.ConfigurationData;
import com.ibm.swg.wmb.iseries.cache.ConnectionCache;
import com.ibm.swg.wmb.iseries.communication.ISeriesCall;

/**
 * DataQueueOutputNode.java, May 10, 2011
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
public class DataQueueOutputNode extends DataQueueFlowNode implements MbNodeInterface {

	/**
	 * @throws MbException
	 */
	public DataQueueOutputNode() throws MbException {
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
		MbMessage outMessage = new MbMessage(inMessage);

		// connection to the AS400
		AS400 system = null;
		loadConfigurableService();

		try {
			// the local environment will overwrite the node properties
			getPropertiesFromLocalEnvironment(inAssembly);
			
			// setTrace directory
			if (enable == true) {
				ISeriesCall.enableTrace(buildFileName());
			}

			// connect to i series
			system = ConnectionCache.getConnection("UserDefined", getConfigurableService());
			
			//get CCSID
			
			ConfigurationData configurationData = ConnectionCache.getConfigurationData("UserDefined", getConfigurableService());
			String ccsid = configurationData.get_CCSID();

			// Message body is the last child of Root
			MbElement msgBody = inAssembly.getMessage().getRootElement().getLastChild();
			
			// send message to the data queue
			byte[] message = msgBody.toBitstream("", "", "", 0, 0, 0);
			
			// get data queue
			BaseDataQueue baseDataQueue = dataQueueFactory.getDataQueue(system, library, queueName, type);
			
			//translate to target CCSID
			AS400Text textConverter = new AS400Text(message.length, Integer.parseInt(ccsid), system);
			String messageStr = new String(message);
			byte[] messageEBCDIC = textConverter.toBytes(messageStr);
			
			// write message to queue
			dataQueueFactory.writeMessage(baseDataQueue, key,  messageEBCDIC, ccsid);
			
			// Build the output message assembly before propagating the message.
			MbMessageAssembly outAssembly = new MbMessageAssembly(inAssembly, inMessage);

			// The following should only be changed if not propagating message
			out.propagate(outAssembly);
		} catch (MbUserException e) {
			throw e;
		} finally {
			// turn off the tracing
			ISeriesCall.disableTrace();
			
			// return the connection to the iSeries connection pool
			ConnectionCache.releaseConnection("UserDefined", getConfigurableService(), system);
			
			outMessage.clearMessage();
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
			MbElement localEnvironment = inAssembly.getLocalEnvironment().getRootElement().getFirstElementByPath("DataQueue");

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
			}
		} catch (MbException e) {
			throw new MbUserException(e, "getPropertiesFromLocalEnvironment", "ISeriesProgramCall", e.getMessage(), "Get Local Configuration", null);
		}
	}

	/**
	 * @return
	 */
	public static String getNodeName() {
		return "DataQueueOutputNode";
	}
}