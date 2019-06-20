package com.ibm.swg.iseries.communication;

import java.beans.PropertyVetoException;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringReader;

import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.Templates;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.stax.StAXSource;

import com.ibm.as400.access.AS400;
import com.ibm.as400.access.AS400Message;
import com.ibm.as400.access.AS400SecurityException;
import com.ibm.as400.access.CommandCall;
import com.ibm.as400.access.ErrorCompletingRequestException;
import com.ibm.as400.access.Trace;
import com.ibm.as400.data.PcmlException;
import com.ibm.as400.data.ProgramCallDocument;
import com.ibm.as400.data.XmlException;
import com.ibm.broker.plugin.MbElement;
import com.ibm.broker.plugin.MbException;
import com.ibm.broker.plugin.MbUserException;
import com.ibm.swg.iseries.cache.TemplateCache;

/**
 * ISeriesCall.java, May 10, 2011
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
public class ISeriesCall {
	/**
	 * @param fileName
	 * @throws MbUserException
	 */
	static public void enableTrace(String fileName) throws MbUserException {
		try {
			Trace.setFileName(fileName);
			Trace.setTraceOn(true);
			Trace.setTraceAllOn(true);
		} catch (IOException e) {
			throw new MbUserException(e, "enableTrace", "ISeriesCall", e.getMessage(), "Enable iSeries tracing", null);
		}
	}

	/**
	 * 
	 */
	static public void disableTrace() {
		Trace.setTraceAllOn(false);
		Trace.setTraceOn(false);
	}

	/**
	 * @param system
	 * @param programName
	 * @param message
	 * @param ccsid 
	 * @param templateToPCML
	 * @param templateFromPCML
	 * @return
	 * @throws MbUserException
	 */
	static public byte[] callIseriesProgram(AS400 system, String programName, MbElement message,
			TemplateCache templateCache, String toPCML, String fromPCML, boolean isXPcml,int ccsid) throws MbUserException {
		byte[] messageOut = null;
		ProgramCallDocument pcml = null;
		int pcmlType = ProgramCallDocument.SOURCE_PCML;

		try {
			ByteArrayInputStream bisXpcmlTransformed = transformMessageToPcml(message, templateCache
					.getTemplate(toPCML), ccsid);

			if (isXPcml == true) {
				pcmlType = ProgramCallDocument.SOURCE_XPCML;
			}

			pcml = new ProgramCallDocument(system, programName, bisXpcmlTransformed, null, null, pcmlType);

			boolean rc = pcml.callProgram(programName);

			AS400Message as400Message[] = pcml.getMessageList(programName);

			// return code
			if (rc == false) {
				throw new MbUserException("callIseriesProgram", "ISeriesCall", null, "Call the iSeries program", null,
						as400Message);
			}

			messageOut = transformPcmlToMessage(pcml, templateCache.getTemplate(fromPCML));
		} catch (PcmlException e) {
			throw new MbUserException(e, "callIseriesProgram", "ISeriesCall", e.getMessage(),
					"Call the iSeries program", null);
		}

		return messageOut;
	}

	/**
	 * @param system
	 * @param libList
	 * @throws MbUserException
	 */
	static public void setProgramCallLibraryList(AS400 system, String[] libList) throws MbUserException {
		try {
			CommandCall commandCall = new CommandCall(system);
			system.getJobs(AS400.COMMAND);
			if (libList != null) {
				for (int i = 0; i < libList.length; i++) {
					if (!libList[i].equals("")) {
						boolean rc = commandCall.run("ADDLIBLE " + libList[i]);

						AS400Message as400Message[] = commandCall.getMessageList();

						if (rc == false) {
							for (int j = 0; j < as400Message.length; j++) {
								if (!as400Message[j].getID().equals("CPF2103")) {
									throw new MbUserException("setProgramCallLibraryList", "ISeriesCall", null,
											"Set lib program call lib " + libList[i], null, as400Message);
								}
							}
						}
					}
				}
			}
		} catch (ErrorCompletingRequestException e) {
			throw new MbUserException(e, "setProgramCallLibraryList", "ISeriesCall", e.getMessage(),
					"Set lib program call lib list", null);
		} catch (IOException e) {
			throw new MbUserException(e, "setProgramCallLibraryList", "ISeriesCall", e.getMessage(),
					"Set lib program call lib list", null);
		} catch (PropertyVetoException e) {
			throw new MbUserException(e, "setProgramCallLibraryList", "ISeriesCall", e.getMessage(),
					"Set lib program call lib list", null);
		} catch (InterruptedException e) {
			throw new MbUserException(e, "setProgramCallLibraryList", "ISeriesCall", e.getMessage(),
					"Set lib program call lib list", null);
		} catch (AS400SecurityException e) {
			throw new MbUserException(e, "setProgramCallLibraryList", "ISeriesCall", e.getMessage(),
					"Set lib program call lib list", null);
		}
	}

	/**
	 * @param message
	 * @param templateToPCML
	 * @param ccsid 
	 * @return
	 * @throws MbUserException
	 */
	static private ByteArrayInputStream transformMessageToPcml(MbElement message, Templates templateToPCML, int ccsid)
			throws MbUserException {
		ByteArrayInputStream bisXpcmlTransformed = null;

		try {
			//encoding and ccsid need be set by code
			byte[] msgByteStreamIn = message.toBitstream("", "", "", 0, ccsid, 0);
			ByteArrayInputStream bisXpcml = new ByteArrayInputStream(msgByteStreamIn);
			XMLStreamReader xmlSR = XMLInputFactory.newInstance().createXMLStreamReader(bisXpcml);
			// transform the input stream
			StAXSource stx = new StAXSource(xmlSR );
			ByteArrayOutputStream bosXpcmlTransformed = new ByteArrayOutputStream();
			Transformer transformer = templateToPCML.newTransformer();
			transformer.transform(stx,
					new javax.xml.transform.stream.StreamResult(bosXpcmlTransformed));

			bisXpcmlTransformed = new ByteArrayInputStream(bosXpcmlTransformed.toByteArray());
		} catch (MbException e) {
			throw new MbUserException(e, "transformMessageToPcml", "ISeriesCall", e.getMessage(),
					"Transfrom message to PCML", null);
		} catch (TransformerException e) {
			throw new MbUserException(e, "transformMessageToPcml", "ISeriesCall", e.getMessage(),
					"Transfrom message to PCML", null);
		} catch (XMLStreamException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FactoryConfigurationError e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return bisXpcmlTransformed;
	}

	/**
	 * @param programCallDocument
	 * @param templateFromPCML
	 * @return
	 * @throws MbUserException
	 */
	static private byte[] transformPcmlToMessage(ProgramCallDocument programCallDocument, Templates templateFromPCML)
			throws MbUserException {
		byte[] msgByteStreamOut = null;

		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			ByteArrayOutputStream bosTransformed = new ByteArrayOutputStream();
			programCallDocument.generateXPCML(bos);
			String bosStr = bos.toString();
			// transform the output stream
			Transformer transformer2 = templateFromPCML.newTransformer();
			transformer2.transform(new javax.xml.transform.stream.StreamSource(new StringReader(bosStr)), new javax.xml.transform.stream.StreamResult(bosTransformed));
			// changed code due to invalid NLS transformation
//			transformer2.transform(new javax.xml.transform.stream.StreamSource(new ByteArrayInputStream(bos
//					.toByteArray())), new javax.xml.transform.stream.StreamResult(bosTransformed));

			msgByteStreamOut = bosTransformed.toByteArray();
		} catch (XmlException e) {
			throw new MbUserException(e, "transformPcmlToMessage", "ISeriesCall", e.getMessage(),
					"Transfrom PCML to message", null);
		} catch (TransformerException e) {
			throw new MbUserException(e, "transformPcmlToMessage", "ISeriesCall", e.getMessage(),
					"Transfrom PCML to message", null);
		} catch (IOException e) {
			throw new MbUserException(e, "transformPcmlToMessage", "ISeriesCall", e.getMessage(),
					"Transfrom PCML to message", null);
		}
		return msgByteStreamOut;
	}
}