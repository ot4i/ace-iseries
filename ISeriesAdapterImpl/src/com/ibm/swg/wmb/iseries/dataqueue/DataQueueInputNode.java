package com.ibm.swg.wmb.iseries.dataqueue;

import com.ibm.as400.access.AS400;
import com.ibm.as400.access.BaseDataQueue;
import com.ibm.broker.plugin.MbElement;
import com.ibm.broker.plugin.MbException;
import com.ibm.broker.plugin.MbInputNodeInterface;
import com.ibm.broker.plugin.MbMessage;
import com.ibm.broker.plugin.MbMessageAssembly;
import com.ibm.broker.plugin.MbOutputTerminal;
import com.ibm.broker.plugin.MbUserException;
import com.ibm.swg.wmb.iseries.DataQueueInNode;
import com.ibm.swg.wmb.iseries.cache.ConfigurationData;
import com.ibm.swg.wmb.iseries.cache.ConnectionCache;
import com.ibm.swg.wmb.iseries.communication.ISeriesCall;

/**
 * DataQueueInputNode.java, May 18, 2011, 2011
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
public class DataQueueInputNode extends DataQueueInNode implements MbInputNodeInterface {

	/**
	 * @throws MbException
	 */
	public DataQueueInputNode() throws MbException {
		createOutputTerminal("out");
		createOutputTerminal("failure");

		this.setAttribute("firstParserClassName", "BLOB");
	}

	/**
	 * @return
	 */
	public static String getNodeName() {
		return "DataQueueInputNode";
	}

	@Override
	public int run(MbMessageAssembly inAssembly) throws MbException{
		int returnCode = FAILURE_RETURN;
		int system_ccsid = 0;
		boolean catched = false;

		MbOutputTerminal out = getOutputTerminal("out");
		MbOutputTerminal failure = getOutputTerminal("failure");

		MbMessage outMessage = null;
		MbMessageAssembly outAssembly = null;

		byte[] message = null;

		// connection to the AS400
		AS400 system = null;
		loadConfigurableService();

		try {
			// setTrace directory
			if (enable == true) {
				ISeriesCall.enableTrace(buildFileName());
			}

			// connect to i series
			system = ConnectionCache.getConnection("UserDefined", getConfigurableService());

			// get data queue
			BaseDataQueue baseDataQueue = dataQueueFactory.getDataQueue(system, library, queueName, type);
			// CCSID
			ConfigurationData configurationData = ConnectionCache.getConfigurationData("UserDefined", getConfigurableService());
			String ccsid = configurationData.get_CCSID();
			// get message from data queue
			message = dataQueueFactory.getMessage(baseDataQueue, key, searchType, waitInterval,ccsid);

			if (message != null) {
				// span a additional instance
				dispatchThread();
				
				// get the code set of the message 
				system_ccsid = system.getCcsid();

				// create and out message with blob body
				outMessage = createMessage(message);

				// write the properties for further parsing
				MbElement properties = outMessage.getRootElement().getFirstChild();
				MbElement codedCharSetId = properties.getFirstElementByPath("CodedCharSetId");
				MbElement encoding = properties.getFirstElementByPath("Encoding");

				if (codedCharSetId != null) {
					codedCharSetId.setValue(Integer.valueOf(system_ccsid));
				}

				if (encoding != null) {
					encoding.setValue(Integer.valueOf(0));
				}

				// attach the out Message to the message assembly
				outAssembly = new MbMessageAssembly(inAssembly, outMessage);

				// check if the message can send to the out
				if (out.isAttached()) {
					out.propagate(outAssembly);

					returnCode = SUCCESS_CONTINUE;
				} else {
					returnCode = FAILURE_CONTINUE;
				}
			} else {
				returnCode = TIMEOUT;
			}

		} catch (MbException ex) {
			catched = true;
			throw new MbUserException(ex, "run", "DataQueueInputNode", ex.getMessage(), "Get message from data queue",
					null);
		} finally {
			// turn off the tracing
			ISeriesCall.disableTrace();

			// return the connection to the iSeries connection pool
			ConnectionCache.releaseConnection("UserDefined", getConfigurableService(), system);

			if (catched == true) {
				if (outAssembly == null) {
					if (outMessage == null) {
						if (message != null) {
							outMessage = createMessage(message);

							// write the properties for further parsing
							MbElement properties = outMessage.getRootElement().getFirstChild();
							MbElement codedCharSetId = properties.getFirstElementByPath("CodedCharSetId");
							MbElement encoding = properties.getFirstElementByPath("Encoding");

							if (codedCharSetId != null) {
								codedCharSetId.setValue(Integer.valueOf(system_ccsid));
							}

							if (encoding != null) {
								encoding.setValue(Integer.valueOf(0));
							}
						} else {
							outMessage = new MbMessage();
						}
					}

					outAssembly = new MbMessageAssembly(inAssembly, outMessage);
				}

				if (failure.isAttached()) {
					failure.propagate(outAssembly);
					 
				}
			}
			
			// clear out message
			if (outMessage != null) {
				outMessage.clearMessage();
			}
			
			if(returnCode != 0) {
				return returnCode;
			}
		}

		return returnCode;
	}
}
