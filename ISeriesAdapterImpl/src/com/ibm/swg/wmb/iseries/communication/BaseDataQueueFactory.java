package com.ibm.swg.wmb.iseries.communication;

import com.ibm.as400.access.AS400;
import com.ibm.as400.access.BaseDataQueue;
import com.ibm.broker.plugin.MbUserException;
import com.ibm.swg.wmb.iseries.cache.DataQueueCache;

public abstract class BaseDataQueueFactory {
	static DataQueueCache dataQueueCache = null;
	
	public static BaseDataQueueFactory getFactory(boolean isKeyed, DataQueueCache argDataQueueCache) {
		dataQueueCache = argDataQueueCache;
		
		if (isKeyed == true) {
			return new KeyedDataQueueFactory();
		} else {
			return new DataQueueFactory();
		}
	}

	public abstract BaseDataQueue getDataQueue(AS400 system, String libraryName, String dataQueueName, String queueType);

	public abstract void writeMessage(BaseDataQueue baseDataQueue, String key, byte[] message, String ccsid) throws MbUserException;
	
	public abstract byte[] getMessage(BaseDataQueue baseDataQueue, String key, String searchType, int timeout, String ccsid) throws MbUserException;
}
