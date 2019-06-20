package com.ibm.swg.iseries.communication;

import java.beans.PropertyVetoException;
import java.io.IOException;

import com.ibm.as400.access.AS400;
import com.ibm.as400.access.AS400SecurityException;
import com.ibm.as400.access.BaseDataQueue;
import com.ibm.as400.access.DataQueue;
import com.ibm.as400.access.DataQueueEntry;
import com.ibm.as400.access.ErrorCompletingRequestException;
import com.ibm.as400.access.IllegalObjectTypeException;
import com.ibm.as400.access.ObjectDoesNotExistException;
import com.ibm.as400.access.QSYSObjectPathName;
import com.ibm.broker.plugin.MbUserException;

public class DataQueueFactory extends BaseDataQueueFactory {
	/**
	 * @param system
	 * @param libraryName
	 * @param dataQueueName
	 * @param queueType
	 * @return
	 */
	public BaseDataQueue getDataQueue(AS400 system, String libraryName, String dataQueueName, String queueType) {
		BaseDataQueue dataQueue = null;
		
		// construct i series object path name
		QSYSObjectPathName name = new QSYSObjectPathName(libraryName, dataQueueName, queueType);

		// create the data queue object.
		dataQueue = dataQueueCache.getDataQueue(system, name.getPath());
		
		if(dataQueue == null) {
			dataQueue = new DataQueue(system, name.getPath());
			
			dataQueueCache.setDataQueue(system, name.getPath(), dataQueue);
		}
		
		return dataQueue;
	}

	@Override
	public byte[] getMessage(BaseDataQueue baseDataQueue, String key, String searchType, int timeout, String ccsid) throws MbUserException {
		byte[] message = null;
		
		try {
			// get the data queue from cache
			DataQueue dataQueue = (DataQueue) baseDataQueue;

			// Write the record to the data queue.
			DataQueueEntry queueEntry = dataQueue.read(timeout);  
			
			if (queueEntry != null) {
				message = queueEntry.getData();	
			}
		} catch (AS400SecurityException e) {
			throw new MbUserException(e, "getMessage", "ISeriesCall", e.getMessage(), "Get message to data queue", null);
		} catch (ErrorCompletingRequestException e) {
			throw new MbUserException(e, "getMessage", "ISeriesCall", e.getMessage(), "Get message to data queue", null);
		} catch (IOException e) {
			throw new MbUserException(e, "getMessage", "ISeriesCall", e.getMessage(), "Get message to data queue", null);
		} catch (IllegalObjectTypeException e) {
			throw new MbUserException(e, "getMessage", "ISeriesCall", e.getMessage(), "Get message to data queue", null);
		} catch (InterruptedException e) {
			throw new MbUserException(e, "getMessage", "ISeriesCall", e.getMessage(), "Get message to data queue", null);
		} catch (ObjectDoesNotExistException e) {
			throw new MbUserException(e, "getMessage", "ISeriesCall", e.getMessage(), "Get message to data queue", null);
		}
		
		return message;
	}

	@Override
	public void writeMessage(BaseDataQueue baseDataQueue, String key, byte[] message, String ccsid) throws MbUserException {
		try {
			// get the data queue from cache
			DataQueue dataQueue = (DataQueue) baseDataQueue;

			//set CCSID 
			if (ccsid !="0") {
				try {
					dataQueue.setCcsid(Integer.parseInt(ccsid));
				} catch (NumberFormatException e) {
					throw new MbUserException("KeyedDataQueueFactory", "writeMessage", "KeyedDataQueueFactory",
							"The CCSID can't parsed as integer.", "Write keyed message to data queue", null);
				} catch (PropertyVetoException e) {
					throw new MbUserException("KeyedDataQueueFactory", "writeMessage", "KeyedDataQueueFactory",
							"The CCSID can't parsed - PropertyVetoException.", "Write keyed message to data queue", null);
				}				
			}
			
			// Write the record to the data queue.
			dataQueue.write(message);
		} catch (AS400SecurityException e) {
			throw new MbUserException(e, "writeMessage", "ISeriesCall", e.getMessage(), "Write message to data queue", null);
		} catch (ErrorCompletingRequestException e) {
			throw new MbUserException(e, "writeMessage", "ISeriesCall", e.getMessage(), "Write message to data queue", null);
		} catch (IOException e) {
			throw new MbUserException(e, "writeMessage", "ISeriesCall", e.getMessage(), "Write message to data queue", null);
		} catch (IllegalObjectTypeException e) {
			throw new MbUserException(e, "writeMessage", "ISeriesCall", e.getMessage(), "Write message to data queue", null);
		} catch (InterruptedException e) {
			throw new MbUserException(e, "writeMessage", "ISeriesCall", e.getMessage(), "Write message to data queue", null);
		} catch (ObjectDoesNotExistException e) {
			throw new MbUserException(e, "writeMessage", "ISeriesCall", e.getMessage(), "Write message to data queue", null);
		}
	}
}
