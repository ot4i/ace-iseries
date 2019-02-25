package com.ibm.swg.wmb.iseries;

import java.util.ListResourceBundle;
import java.util.Properties;
import java.util.UUID;

import com.ibm.broker.config.proxy.BrokerProxy;
import com.ibm.broker.config.proxy.ConfigManagerProxyLoggedException;
import com.ibm.broker.config.proxy.ConfigManagerProxyPropertyNotInitializedException;
import com.ibm.broker.config.proxy.ConfigurableService;
import com.ibm.broker.plugin.MbElement;
import com.ibm.broker.plugin.MbException;
import com.ibm.broker.plugin.MbMessage;
import com.ibm.broker.plugin.MbNode;
import com.ibm.broker.plugin.MbService;
import com.ibm.swg.wmb.iseries.cache.ConfigurationData;
import com.ibm.swg.wmb.iseries.cache.ConnectionCache;

/**
 * FlowNode.java, May 10, 2011
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
public abstract class FlowNode extends MbNode {

	/**
	 * controls the tracing facility, if true a trace file is written
	 */
	protected boolean enable = false;

	/**
	 * path to write the trace files, if blank the trace get written to brokers
	 * work path
	 */
	protected String directory = "";

	/**
	 * name of used configurable service
	 */
	protected String configurableService = "myISeries";

	/**
	 * @return
	 */
	public String getConfigurableService() {
		return configurableService;
	}

	/**
	 * @param configurableService
	 */
	public void setConfigurableService(String configurableService) {
		this.configurableService = configurableService;
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

	public FlowNode() throws MbException {
		super();
	}
	
	public void loadConfigurableService() throws MbException {
		ConfigurationData configurationData = ConnectionCache
				.getConfigurationData("UserDefined", configurableService);

		if (configurationData == null) {
			Properties properties = readConfigurableService("UserDefined",
					configurableService);

			ConnectionCache.setISeriesPool("UserDefined", configurableService,
					properties);
		}
	}

	/**
	 * Function which builds the trace path und trace filename for the current
	 * run.
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
	 * Get the configurable service from the configuration manager proxy
	 * 
	 * @param configurableServiceName
	 * @return
	 * @throws MbException
	 */
	private Properties readConfigurableService(String configurableServiceType,
			String configurableServiceName) throws MbException {
		BrokerProxy brokerProxy;
		Properties iSeriesProps = null;

		try {
			// get the configuration proxy
			brokerProxy = BrokerProxy.getLocalInstance();

			// wait for CMP updates
			while (!brokerProxy.hasBeenPopulatedByBroker()) {
			} // Wait for broker to populate configurable services

			// Thread.sleep(1000);

			// find the needed configurable service object
			ConfigurableService myISeries = brokerProxy.getConfigurableService(
					configurableServiceType, configurableServiceName);

			if (myISeries == null) {

				ConfigurableService[] UD_set = brokerProxy
						.getConfigurableServices("UserDefined");
				String listUDCS = "No UDCS defined";
				if (UD_set.length > 0) {
					listUDCS = UD_set[0].getName();
					for (int i = 1; i < UD_set.length; i++) {
						listUDCS = listUDCS + "," + UD_set[i].getName();
					}
				}
				MbService.logError(this, "readConfigurableService",
						FlowNodeMessages.MESSAGE_SOURCE,
						FlowNodeMessages.Error, "Configurable Service: "
								+ configurableServiceName
								+ " does not exists. " + "Known services are: "
								+ listUDCS, null);
	
			}

			// get the the properties from the service
			try {
				iSeriesProps = myISeries.getProperties();
			} catch (Exception e) {
				MbService.logError(this, "readConfigurableService",
						FlowNodeMessages.MESSAGE_SOURCE,
						FlowNodeMessages.Error,
						"Error processing configurable service: "
								+ configurableServiceName, null);
				
			}
			// disconnect the proxy
			brokerProxy.disconnect();

		} catch (ConfigManagerProxyLoggedException e) {
			MbService.logError(this, "readConfigurableService",
					FlowNodeMessages.MESSAGE_SOURCE, FlowNodeMessages.Error,
					"ConfigManagerProxyLoggedException " + e.toString(), null);
		} catch (ConfigManagerProxyPropertyNotInitializedException e) {
			MbService.logError(this, "readConfigurableService",
					FlowNodeMessages.MESSAGE_SOURCE, FlowNodeMessages.Error,
					"ConfigManagerProxyPropertyNotInitializedException "
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
	protected void copyMessageHeaders(MbMessage inMessage, MbMessage outMessage)
			throws MbException {
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

	/**
	 * The class is the ResourceBundle contains all the messages for this
	 * example.
	 */
	public static class FlowNodeMessages extends ListResourceBundle {
		public static final String MESSAGE_SOURCE = FlowNodeMessages.class
				.getName();
		public static final String IOEX = "IOEX";
		public static final String Error = "Error";

		private Object[][] messages = {
				{ IOEX,
						"An IOException has been thrown by RoutingFileNode: message: %1 stackTrace: %2" },
				{ Error, "An Exception has occurred - see details" }, };

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.util.ListResourceBundle#getContents()
		 */
		public Object[][] getContents() {
			return messages;
		}
	}
}