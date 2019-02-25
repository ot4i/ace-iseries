package com.ibm.swg.wmb.iseries;

import java.util.Properties;
import java.util.UUID;

import com.ibm.broker.config.proxy.BrokerProxy;
import com.ibm.broker.config.proxy.ConfigManagerProxyLoggedException;
import com.ibm.broker.config.proxy.ConfigManagerProxyPropertyNotInitializedException;
import com.ibm.broker.config.proxy.ConfigurableService;
import com.ibm.broker.plugin.MbElement;
import com.ibm.broker.plugin.MbException;
import com.ibm.broker.plugin.MbInputNode;
import com.ibm.broker.plugin.MbMessage;
import com.ibm.broker.plugin.MbUserException;
import com.ibm.swg.wmb.iseries.cache.ConfigurationData;
import com.ibm.swg.wmb.iseries.cache.ConnectionCache;

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


	public InputNode() throws MbException {
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
	 * Get the configurable service from the configuration manager proxy
	 * 
	 * @param configurableServiceName
	 * @return
	 * @throws MbUserException
	 */
	private Properties readConfigurableService(String configurableServiceType, String configurableServiceName)
			throws MbUserException {
		BrokerProxy brokerProxy;
		Properties iSeriesProps = null;

		try {
			// get the configuration proxy
			brokerProxy = BrokerProxy.getLocalInstance();

			Thread.currentThread();
			Thread.sleep(1000);

			// find the needed proxy
			ConfigurableService myISeries = brokerProxy.getConfigurableService(configurableServiceType,
					configurableServiceName);

			Thread.sleep(1000);

			// get the the properties from the service
			try {
				iSeriesProps = myISeries.getProperties();				
			} catch (Exception e) {
				throw new MbUserException(e, "readConfigurableService", "ISeriesMbNode", e.getMessage(),
						"Configurable Service: "+configurableServiceName+"does not exists", null);
			}

			
			// disconnect the proxy
			brokerProxy.disconnect();
		} catch (InterruptedException ie) {
			// If this thread was interrupted by other thread
		} catch (ConfigManagerProxyLoggedException e) {
			throw new MbUserException(e, "readConfigurableService", "ISeriesMbNode", e.getMessage(),
					"Call Configuration Manger Proxy", null);
		} catch (ConfigManagerProxyPropertyNotInitializedException e) {
			throw new MbUserException(e, "readConfigurableService", "ISeriesMbNode", e.getMessage(),
					"Call Configuration Manger Proxy", null);
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
