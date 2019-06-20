package com.ibm.swg.iseries.cache;

import com.ibm.as400.access.AS400ConnectionPool;

/**
 * ISeriesConfigurationData.java, May 16, 2011, 2011
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
public class ConfigurationData {
	/**
	 * hostname if the iSeries Server
	 */
	private String _iSeriesAddress = null;

	/**
	 * user to access the iSeries
	 */
	private String _iSeriesUser = null;

	/**
	 * password to access the Series
	 */
	private String _iSeriesPassword = null;

	/**
	 * maximum number of connections to create in the iSeries connection pool
	 */
	private int _maxConnections = 1;

	/**
	 * minimum number of connections to create in the iSeries connection pool
	 */
	private int _minConnections = 1;

	/**
	 * time in milliseconds when a used connection is discarded
	 */
	private int _maxUseTimeout = -1;

	/**
	 * CCSID of the iSeries
	 */	
	private String _seriesCCSID = null;
	/**
	 * iSeries connection pool object 
	 */
	private AS400ConnectionPool _iSeriesConnectionPool = null;

	/**
	 * @return
	 */
	public int get_maxConnections() {
		return _maxConnections;
	}

	/**
	 * @param connections
	 */
	public void set_maxConnections(int connections) {
		_maxConnections = connections;
	}

	/**
	 * @return
	 */
	public int get_minConnections() {
		return _minConnections;
	}

	/**
	 * @param connections
	 */
	public void set_minConnections(int connections) {
		_minConnections = connections;
	}

	/**
	 * @return
	 */
	public int get_maxUseTimeout() {
		return _maxUseTimeout;
	}

	/**
	 * @param useTimeout
	 */
	public void set_maxUseTimeout(int useTimeout) {
		_maxUseTimeout = useTimeout;
	}

	/**
	 * @return
	 */
	public String get_iSeriesPassword() {
		return _iSeriesPassword;
	}

	/**
	 * @return
	 */
	public String get_iSeriesAddress() {
		return _iSeriesAddress;
	}

	/**
	 * @return
	 */
	public String get_iSeriesUser() {
		return _iSeriesUser;
	}

	/**
	 * @return
	 */
	public AS400ConnectionPool get_iSeriesConnectionPool() {
		return _iSeriesConnectionPool;
	}

	/**
	 * @param connectionPool
	 */
	public void set_iSeriesConnectionPool(AS400ConnectionPool connectionPool) {
		_iSeriesConnectionPool = connectionPool;
	}

	/**
	 * @param seriesAddress
	 */
	public void set_iSeriesAddress(String seriesAddress) {
		_iSeriesAddress = seriesAddress;
	}

	/**
	 * @param seriesUser
	 */
	public void set_iSeriesUser(String seriesUser) {
		_iSeriesUser = seriesUser;
	}

	/**
	 * @param seriesPassword
	 */
	public void set_iSeriesPassword(String seriesPassword) {
		_iSeriesPassword = seriesPassword;
	}

	/**
	 * @param CCSID
	 */
	public void set_CCSID(String seriesCCSID) {
		_seriesCCSID = seriesCCSID;
		}
	
	/**
	 * @return
	 */	
	public String  get_CCSID()	{
		return _seriesCCSID;
	}
		
}