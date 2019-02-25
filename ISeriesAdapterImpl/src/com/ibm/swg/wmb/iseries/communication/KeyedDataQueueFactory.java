package com.ibm.swg.wmb.iseries.communication;

import java.beans.PropertyVetoException;
import java.io.IOException;

import com.ibm.as400.access.AS400;
import com.ibm.as400.access.AS400SecurityException;
import com.ibm.as400.access.BaseDataQueue;
import com.ibm.as400.access.DataQueueEntry;
import com.ibm.as400.access.ErrorCompletingRequestException;
import com.ibm.as400.access.IllegalObjectTypeException;
import com.ibm.as400.access.KeyedDataQueue;
import com.ibm.as400.access.ObjectDoesNotExistException;
import com.ibm.as400.access.QSYSObjectPathName;
import com.ibm.broker.plugin.MbUserException;

public class KeyedDataQueueFactory extends BaseDataQueueFactory {
	public BaseDataQueue getDataQueue(AS400 system, String libraryName, String dataQueueName, String queueType) {
		BaseDataQueue dataQueue = null;

		// construct i series object path name
		QSYSObjectPathName name = new QSYSObjectPathName(libraryName, dataQueueName, queueType);

		// create the data queue object.
		dataQueue = dataQueueCache.getDataQueue(system, name.getPath());
		
		if(dataQueue == null) {
			dataQueue = new KeyedDataQueue(system, name.getPath());
			
			dataQueueCache.setDataQueue(system, name.getPath(), dataQueue);
		}

		return dataQueue;
	}

	@Override
	public byte[] getMessage(BaseDataQueue baseDataQueue, String key, String searchType, int timeout, String ccsid)
			throws MbUserException {
		byte[] message = null;

		try {
			// get the data queue from cache
			KeyedDataQueue keyedDataQueue = (KeyedDataQueue) baseDataQueue;

			// calculate key length
			int keyLength = keyedDataQueue.getKeyLength();
			byte[] keyBuffer = new byte[keyLength];
			key = String.format("%-" + keyLength + "s", key);

			byte[] stringBuffer = null;
			// get byte array from string
			try {
				String ebcdic = "IBM-37";
				stringBuffer = key.getBytes(ebcdic);				
			} catch (Exception e) {
				throw new MbUserException("KeyedDataQueueFactory", "writeMessage", "KeyedDataQueueFactory",
						"Key could not be transformed.", "Write keyed message to data queue", null);
			}

			if (stringBuffer.length <= keyBuffer.length) {
				// copy string into key
				for (int i = 0; i < stringBuffer.length; i++) {
					keyBuffer[i] = stringBuffer[i];
				}
			} else {
				throw new MbUserException("KeyedDataQueueFactory", "getMessage", "KeyedDataQueueFactory",
						"The size of key is greater then queue key size.", "Get keyed message to data queue", null);
			}

			// Write the record to the data queue.
			DataQueueEntry queueEntry = keyedDataQueue.read(keyBuffer, timeout, searchType);

			if (queueEntry != null) {
				message = queueEntry.getData();
			}
		} catch (AS400SecurityException e) {
			throw new MbUserException(e, "getMessage", "KeyedDataQueueFactory", e.getMessage(),
					"Get keyed message to data queue", null);
		} catch (ErrorCompletingRequestException e) {
			throw new MbUserException(e, "getMessage", "KeyedDataQueueFactory", e.getMessage(),
					"Get keyed message to data queue", null);
		} catch (IOException e) {
			throw new MbUserException(e, "getMessage", "KeyedDataQueueFactory", e.getMessage(),
					"Get keyed message to data queue", null);
		} catch (IllegalObjectTypeException e) {
			throw new MbUserException(e, "getMessage", "KeyedDataQueueFactory", e.getMessage(),
					"Get keyed message to data queue", null);
		} catch (InterruptedException e) {
			throw new MbUserException(e, "getMessage", "KeyedDataQueueFactory", e.getMessage(),
					"Get keyed message to data queue", null);
		} catch (ObjectDoesNotExistException e) {
			throw new MbUserException(e, "getMessage", "KeyedDataQueueFactory", e.getMessage(),
					"Get keyed message to data queue", null);
		}

		return message;
	}

	@Override
	public void writeMessage(BaseDataQueue baseDataQueue,  String key, byte[] message, String ccsid) throws MbUserException {
		try {
			// get the data queue from cache
			KeyedDataQueue keyedDataQueue = (KeyedDataQueue) baseDataQueue;

			// calculate key length
			int keyLength = keyedDataQueue.getKeyLength();
			byte[] keyBuffer = new byte[keyLength];
			key = String.format("%-" + keyLength + "s", key);
			byte[] stringBuffer = null;
			// get byte array from string
			try {
				String ebcdic = "IBM-37";
				stringBuffer = key.getBytes(ebcdic);				
			} catch (Exception e) {
				throw new MbUserException("KeyedDataQueueFactory", "writeMessage", "KeyedDataQueueFactory",
						"Key could not be transformed.", "Write keyed message to data queue", null);
			}


			if (stringBuffer.length <= keyBuffer.length) {
				// copy string into key
				for (int i = 0; i < stringBuffer.length; i++) {
					keyBuffer[i] = stringBuffer[i];
				}
			} else {
				throw new MbUserException("KeyedDataQueueFactory", "writeMessage", "KeyedDataQueueFactory",
						"The size of key is greater then queue key size.", "Write keyed message to data queue", null);
			}
			//set CCSID 
			if (ccsid !="0") {
				try {
					keyedDataQueue.setCcsid(Integer.parseInt(ccsid));
				} catch (NumberFormatException e) {
					throw new MbUserException("KeyedDataQueueFactory", "writeMessage", "KeyedDataQueueFactory",
							"The CCSID can't parsed as integer.", "Write keyed message to data queue", null);
				} catch (PropertyVetoException e) {
					throw new MbUserException("KeyedDataQueueFactory", "writeMessage", "KeyedDataQueueFactory",
							"The CCSID can't parsed - PropertyVetoException.", "Write keyed message to data queue", null);
				}				
			}


			// Write the record to the data queue.
			keyedDataQueue.write(keyBuffer, message);
		} catch (AS400SecurityException e) {
			throw new MbUserException(e, "writeMessage", "KeyedDataQueueFactory", e.getMessage(),
					"Write keyed message to data queue", null);
		} catch (ErrorCompletingRequestException e) {
			throw new MbUserException(e, "writeMessage", "KeyedDataQueueFactory", e.getMessage(),
					"Write keyed message to data queue", null);
		} catch (IOException e) {
			throw new MbUserException(e, "writeMessage", "KeyedDataQueueFactory", e.getMessage(),
					"Write keyed message to data queue", null);
		} catch (IllegalObjectTypeException e) {
			throw new MbUserException(e, "writeMessage", "KeyedDataQueueFactory", e.getMessage(),
					"Write keyed message to data queue", null);
		} catch (InterruptedException e) {
			throw new MbUserException(e, "writeMessage", "KeyedDataQueueFactory", e.getMessage(),
					"Write keyed message to data queue", null);
		} catch (ObjectDoesNotExistException e) {
			throw new MbUserException(e, "writeMessage", "KeyedDataQueueFactory", e.getMessage(),
					"Write keyed message to data queue", null);
		}
	}
}
