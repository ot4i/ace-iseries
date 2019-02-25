package com.ibm.swg.wmb.iseries.cache;

import java.util.HashMap;

import com.ibm.as400.access.AS400;
import com.ibm.as400.access.BaseDataQueue;

/**
 * DataQueueCache.java, May 10, 2011
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
public class DataQueueCache {
	/**
	 * Hashmap which caches the data queue objects
	 */
	private HashMap<Integer, BaseDataQueue> queueCache = new HashMap<Integer, BaseDataQueue>();

	/**
	 * Return a data queue from cache
	 * 
	 * @param path
	 * @return
	 */
	public BaseDataQueue getDataQueue(AS400 system, String path) {
		// get the unique hash from the data queue name 
		int hashCode = new String(system.getSystemName() + path).hashCode();
		
		// get the data queue object from cache
		BaseDataQueue dataQueue = queueCache.get(new Integer(hashCode));
		
		return dataQueue;
	}
	
	/**
	 * Set a data queue to cache
	 * 
	 * @param path
	 * @param dataQueue
	 */
	public void setDataQueue(AS400 system, String path, BaseDataQueue dataQueue) {
		// get the unique hash from the data queue name 
		int hashCode = new String(system.getSystemName() + path).hashCode();
		
		queueCache.put(new Integer(hashCode), dataQueue);
	}
}
