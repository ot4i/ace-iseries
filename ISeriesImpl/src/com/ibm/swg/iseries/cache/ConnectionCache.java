package com.ibm.swg.iseries.cache;

import java.util.HashMap;
import java.util.Properties;

import com.ibm.as400.access.AS400;
import com.ibm.as400.access.AS400ConnectionPool;
import com.ibm.as400.access.ConnectionPoolException;
import com.ibm.broker.plugin.MbUserException;

/**
 * ISeriesConnectionCache.java, May 16, 2011
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
public class ConnectionCache {
	/**
	 * Hashmap to store the different configurations.
	 */
	private HashMap<Integer, ConfigurationData> connectionPoolCache = new HashMap<Integer, ConfigurationData>();

	/**
	 * Hide the constructor
	 */
	private ConnectionCache() {
	}

	/**
	 * Provide the hash value of policy
	 * 
	 * @param policyType
	 * @param policyName
	 * @return
	 */
	private static int hashConnnectionPool(String policyType, String policyName) {
		return new String(policyType + policyName).hashCode();
	}

	/**
	 * Set the policy and the connection pool in the hashmap
	 * 
	 * @param policyType
	 * @param policyName
	 * @param properties
	 * @throws MbUserException
	 */
	public static synchronized void setISeriesPool(String policyType, String policyName,
			Properties properties) throws MbUserException {
		AS400ConnectionPool connectionPool = null;
		ConfigurationData configurationData = null;

		try {
			configurationData = getConfigurationData(policyType, policyName);

			if (configurationData == null) {
				configurationData = new ConfigurationData();
				//these options are required
				configurationData.set_iSeriesAddress(properties.getProperty("iSeriesAddress"));
				configurationData.set_iSeriesUser(properties.getProperty("iSeriesUser"));
				configurationData.set_iSeriesPassword(properties.getProperty("iSeriesPassword"));
				//these options are optional and might be replaced by their default values
				if ((properties.getProperty("minConnections")== null)||(properties.getProperty("minConnections").equals(""))) {
					configurationData.set_minConnections(1);
				} else {
					configurationData.set_minConnections(Integer.parseInt(properties.getProperty("minConnections")));
				}
				if ((properties.getProperty("maxConnections")== null)||(properties.getProperty("maxConnections").equals(""))){
					configurationData.set_maxConnections(5);
				} else {
					configurationData.set_maxConnections(Integer.parseInt(properties.getProperty("maxConnections")));
				}
				if ((properties.getProperty("maxUseTimeout")== null)||(properties.getProperty("maxUseTimeout").equals(""))) {
					configurationData.set_maxUseTimeout(-1);
				} else {
					configurationData.set_maxUseTimeout(Integer.parseInt(properties.getProperty("maxUseTimeout")));
				}
				if ((properties.getProperty("CCSID")== null)||(properties.getProperty("CCSID").equals(""))) {
					configurationData.set_CCSID("1208");
				} else {
					configurationData.set_CCSID(properties.getProperty("CCSID"));
				}
				connectionPool = new AS400ConnectionPool();
				// set the values for the pool behavior
				connectionPool.setMaxConnections(configurationData.get_maxConnections());
				connectionPool.setMaxUseTime(configurationData.get_maxUseTimeout());
				connectionPool.fill(configurationData.get_iSeriesAddress(), configurationData.get_iSeriesUser(),
						configurationData.get_iSeriesPassword(), AS400.COMMAND, configurationData.get_minConnections());

				configurationData.set_iSeriesConnectionPool(connectionPool);

				ISeriesConnectionCacheHolder.instance.connectionPoolCache.put(hashConnnectionPool(
						policyType, policyName), configurationData);
			}
		} catch (ConnectionPoolException e) {
			connectionPool = null;
			throw new MbUserException(e, "setISeriesPool", "ISeriesConnectionPool", e.getMessage(),
					"Setting of pool failed.", null);
		}
	}

	/**
	 * Get a iSeries connection from pool for the policy
	 * 
	 * @param systemName
	 * @param iSeriesUser
	 * @param iSeriesPassword
	 * @return
	 * @throws MbUserException
	 */
	public static AS400 getConnection(String policyType, String policyName)
			throws MbUserException {
		AS400 connection = null;
		ConfigurationData configurationData = getConfigurationData(policyType, policyName);

		if (configurationData != null) {
			try {
				connection = configurationData.get_iSeriesConnectionPool().getConnection(
						configurationData.get_iSeriesAddress(), configurationData.get_iSeriesUser(),
						configurationData.get_iSeriesPassword(), AS400.COMMAND);
			} catch (ConnectionPoolException e) {
				throw new MbUserException(e, "getConnection", "ISeriesConnectionPool", e.getMessage(),
						"Getting connection of pool failed.", null);
			}
		}

		return connection;
	}

	/**
	 * Receive the policy data from cache.
	 * 
	 * @param policyType
	 * @param policyName
	 * @return
	 */
	public static ConfigurationData getConfigurationData(String policyType, String policyName) {
		int hashCode = hashConnnectionPool(policyType, policyName);

		ConfigurationData configurationData = ISeriesConnectionCacheHolder.instance.connectionPoolCache
				.get(new Integer(hashCode));

		return configurationData;
	}

	/**
	 * release a connection to the pool
	 * 
	 * @param policyType
	 * @param policyName
	 * @param connection
	 */
	public static void releaseConnection(String policyType, String policyName,
			AS400 connection) {
		ConfigurationData configurationData = getConfigurationData(policyType, policyName);

		if (configurationData != null) {
			AS400ConnectionPool connectionPool = configurationData.get_iSeriesConnectionPool();

			if (connectionPool != null) {
				if (connection != null) {
					connectionPool.returnConnectionToPool(connection);
				}
			}
		}
	}

	private static class ISeriesConnectionCacheHolder {
		/**
		 * creation of the singleton
		 */
		static final ConnectionCache instance = new ConnectionCache();
	}
}