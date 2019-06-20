package com.ibm.swg.iseries.cache;

import java.util.HashMap;

import javax.xml.transform.Templates;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamSource;

import com.ibm.broker.plugin.MbUserException;

/**
 * ISeriesTemplateCache.java, May 16, 2011, 2011
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
public class TemplateCache {
	/**
	 * Hashmap which caches the template objects
	 */
	private HashMap<Integer, Templates> templateCache = new HashMap<Integer, Templates>();
	
	/**
	 * Get the templates from cache
	 * 
	 * @param templatePath
	 * @return
	 * @throws MbUserException
	 */
	public Templates getTemplate(String templatePath) throws MbUserException {
		int hashCode = new String(templatePath).hashCode();
		
		Templates template = templateCache.get(new Integer(hashCode));
		
		if (template == null) {
			template = createTemplates(templatePath);
			templateCache.put(new Integer(hashCode), template);
		}
		
		return template;
	}
	
	/**
	 * Create a template
	 * 
	 * @param templatePath
	 * @return
	 * @throws MbUserException
	 */
	private Templates createTemplates(String templatePath) throws MbUserException {
		Templates template = null;
			
		try {
			TransformerFactory transFact = TransformerFactory.newInstance();

			template = transFact.newTemplates(new StreamSource(templatePath));
		} catch (TransformerConfigurationException e) {
			throw new MbUserException(e, "createTemplates", "ISeriesProgramCall", e.getMessage(), "Template Creation", null);
		}
		
		return template;
	}
}
