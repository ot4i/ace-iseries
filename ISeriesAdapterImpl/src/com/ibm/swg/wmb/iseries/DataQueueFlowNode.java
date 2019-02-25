/**
 * 
 */
package com.ibm.swg.wmb.iseries;

import com.ibm.broker.plugin.MbException;
import com.ibm.swg.wmb.iseries.cache.DataQueueCache;
import com.ibm.swg.wmb.iseries.communication.BaseDataQueueFactory;


/**
 * DataQueueFlowNode.java, May 17, 2011, 2011
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
public abstract class DataQueueFlowNode extends FlowNode {
	/**
	 * library name where the queue is placed
	 */
	protected String library = null;

	/**
	 * name of the data queue
	 */
	protected String queueName = null;

	/**
	 * type of the data queue
	 */
	protected String type = "DTAQ";

	/**
	 * cache of the data queue
	 */
	protected DataQueueCache dataQueueCache = null;

	/**
	 * cache of the data queue
	 */
	protected static ThreadLocal<DataQueueCache> _localThread_DataQueueCache = new ThreadLocal<DataQueueCache>();

	/**
	 * data queue factory to construct keyed data queues and non data queues
	 */
	protected BaseDataQueueFactory dataQueueFactory = null;

	/**
	 * Flag if the queue is a keyed dataqueue
	 */
	protected boolean keyed = false;

	/**
	 * The string that contains the key used to search for an entry.
	 */
	protected String key = null;
	
	protected int keylength = 0;

	/**
	 * 	The type of comparison to use to determine if a key is a match. 
	 * Valid values are EQ (equal), NE (not equal), LT (less than), 
	 * LE (less than or equal), GT (greater than), 
	 * and GE (greater than or equal).
	 */
	protected String searchType = "EQ";

	/**
	 * @throws MbException 
	 */
	public DataQueueFlowNode() throws MbException {
		super();

		// initialize data queue cache
		setDataQueueCache();
		
		// default initialization of the fabric, every set will reinitialize the fabric
		dataQueueFactory = BaseDataQueueFactory.getFactory(keyed, dataQueueCache);
	}

	/**
	 * set the current data queue cache
	 * 
	 * @return
	 */
	protected void setDataQueueCache() {
		// get the current cache from thread
		dataQueueCache = _localThread_DataQueueCache.get();

		// if cache not initialized is in thread create a new one
		if (dataQueueCache == null) {
			dataQueueCache = new DataQueueCache();
			_localThread_DataQueueCache.set(dataQueueCache);
		}
	}

	/**
	 * @return
	 */
	public String getLibrary() {
		return library;
	}

	/**
	 * @param library
	 */
	public void setLibrary(String library) {
		this.library = library;
	}

	/**
	 * @return
	 */
	public String getQueueName() {
		return queueName;
	}

	/**
	 * @param queueName
	 */
	public void setQueueName(String queueName) {
		this.queueName = queueName;
	}

	/**
	 * @return
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return
	 */
	public String getKeyed() {
		return Boolean.toString(keyed);
	}

	/**
	 * @param isKeyed
	 */
	public void setKeyed(String keyed) {
		boolean value = Boolean.parseBoolean(keyed);
		
		// set fabric if for keyed or nor keyed data queue
		dataQueueFactory = BaseDataQueueFactory.getFactory(value, dataQueueCache);
		
		this.keyed = value;
	}

	/**
	 * @return
	 */
	public String getKey() {
		return key;
	}

	/**
	 * @param key
	 */
	public void setKey(String key) {
		this.key = key;
	}

	/**
	 * @return
	 */
	public String getSearchType() {
		return searchType;
	}

	/**
	 * @param searchType
	 */
	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}
	
	/**
	 * @return
	 */
	public int getKeylength() {
		return keylength;
	}

	/**
	 * @param keylength
	 */
	public void setKeylength(int value) {
		this.keylength = value;
	}
}