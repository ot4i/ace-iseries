package com.ibm.swg.iseries.pcml;

import com.ibm.broker.config.appdev.InputTerminal;
import com.ibm.broker.config.appdev.Node;
import com.ibm.broker.config.appdev.NodeProperty;
import com.ibm.broker.config.appdev.OutputTerminal;

/*** 
 * <p>  <I>ISeriesPcmlCallNodeUDN</I> instance</p>
 * <p></p>
 */
public class ISeriesPcmlCallNodeUDN extends Node {

	private static final long serialVersionUID = 1L;

	// Node constants
	protected final static String NODE_TYPE_NAME = "com/ibm/swg/iseries/pcml/ISeriesPcmlCallNode";
	protected final static String NODE_GRAPHIC_16 = "platform:/plugin/ISeries/icons/full/obj16/com/ibm/swg/iseries/pcml/ISeriesPcmlCall.gif";
	protected final static String NODE_GRAPHIC_32 = "platform:/plugin/ISeries/icons/full/obj30/com/ibm/swg/iseries/pcml/ISeriesPcmlCall.gif";

	protected final static String PROPERTY_CONFIGURABLESERVICE = "configurableService";
	protected final static String PROPERTY_ISXPCML = "isXPcml";
	protected final static String PROPERTY_PCMLCALL = "pcmlCall";
	protected final static String PROPERTY_LIBRARYLIST = "libraryList";
	protected final static String PROPERTY_CCSID = "ccsid";
	protected final static String PROPERTY_TOPCML = "toPcml";
	protected final static String PROPERTY_TOXPCML = "toXPcml";
	protected final static String PROPERTY_FROMXPCML = "fromXPcml";
	protected final static String PROPERTY_ENABLE = "enable";
	protected final static String PROPERTY_DIRECTORY = "directory";

	protected NodeProperty[] getNodeProperties() {
		return new NodeProperty[] {
			new NodeProperty(ISeriesPcmlCallNodeUDN.PROPERTY_CONFIGURABLESERVICE,		NodeProperty.Usage.MANDATORY,	false,	NodeProperty.Type.STRING, "myISeries","","",	"com/ibm/swg/iseries/pcml/ISeriesPcmlCall",	"ISeries"),
			new NodeProperty(ISeriesPcmlCallNodeUDN.PROPERTY_ISXPCML,		NodeProperty.Usage.OPTIONAL,	false,	NodeProperty.Type.BOOLEAN, "false","","",	"com/ibm/swg/iseries/pcml/ISeriesPcmlCall",	"ISeries"),
			new NodeProperty(ISeriesPcmlCallNodeUDN.PROPERTY_PCMLCALL,		NodeProperty.Usage.OPTIONAL,	false,	NodeProperty.Type.STRING, null,"","",	"com/ibm/swg/iseries/pcml/ISeriesPcmlCall",	"ISeries"),
			new NodeProperty(ISeriesPcmlCallNodeUDN.PROPERTY_LIBRARYLIST,		NodeProperty.Usage.OPTIONAL,	false,	NodeProperty.Type.STRING, null,"","",	"com/ibm/swg/iseries/pcml/ISeriesPcmlCall",	"ISeries"),
			new NodeProperty(ISeriesPcmlCallNodeUDN.PROPERTY_CCSID,		NodeProperty.Usage.OPTIONAL,	false,	NodeProperty.Type.INTEGER, "1208","","",	"com/ibm/swg/iseries/pcml/ISeriesPcmlCall",	"ISeries"),
			new NodeProperty(ISeriesPcmlCallNodeUDN.PROPERTY_TOPCML,		NodeProperty.Usage.OPTIONAL,	false,	NodeProperty.Type.STRING, "transformToPcml.xsl","","",	"com/ibm/swg/iseries/pcml/ISeriesPcmlCall",	"ISeries"),
			new NodeProperty(ISeriesPcmlCallNodeUDN.PROPERTY_TOXPCML,		NodeProperty.Usage.OPTIONAL,	false,	NodeProperty.Type.STRING, "transformToXPcml.xsl","","",	"com/ibm/swg/iseries/pcml/ISeriesPcmlCall",	"ISeries"),
			new NodeProperty(ISeriesPcmlCallNodeUDN.PROPERTY_FROMXPCML,		NodeProperty.Usage.OPTIONAL,	false,	NodeProperty.Type.STRING, "transformFromXPcml.xsl","","",	"com/ibm/swg/iseries/pcml/ISeriesPcmlCall",	"ISeries"),
			new NodeProperty(ISeriesPcmlCallNodeUDN.PROPERTY_ENABLE,		NodeProperty.Usage.OPTIONAL,	false,	NodeProperty.Type.BOOLEAN, "false","","",	"com/ibm/swg/iseries/pcml/ISeriesPcmlCall",	"ISeries"),
			new NodeProperty(ISeriesPcmlCallNodeUDN.PROPERTY_DIRECTORY,		NodeProperty.Usage.OPTIONAL,	false,	NodeProperty.Type.STRING, null,"","",	"com/ibm/swg/iseries/pcml/ISeriesPcmlCall",	"ISeries")
		};
	}

	public ISeriesPcmlCallNodeUDN() {
	}

	public final InputTerminal INPUT_TERMINAL_IN = new InputTerminal(this,"InTerminal.in");
	@Override
	public InputTerminal[] getInputTerminals() {
		return new InputTerminal[] {
			INPUT_TERMINAL_IN
	};
	}

	public final OutputTerminal OUTPUT_TERMINAL_FAILURE = new OutputTerminal(this,"OutTerminal.failure");
	public final OutputTerminal OUTPUT_TERMINAL_OUT = new OutputTerminal(this,"OutTerminal.out");
	@Override
	public OutputTerminal[] getOutputTerminals() {
		return new OutputTerminal[] {
			OUTPUT_TERMINAL_FAILURE,
			OUTPUT_TERMINAL_OUT
		};
	}

	@Override
	public String getTypeName() {
		return NODE_TYPE_NAME;
	}

	protected String getGraphic16() {
		return NODE_GRAPHIC_16;
	}

	protected String getGraphic32() {
		return NODE_GRAPHIC_32;
	}

	/**
	 * Set the <I>ISeriesPcmlCallNodeUDN</I> "<I>configurableService</I>" property
	 * 
	 * @param value String ; the value to set the property "<I>configurableService</I>"
	 */
	public ISeriesPcmlCallNodeUDN setConfigurableService(String value) {
		setProperty(ISeriesPcmlCallNodeUDN.PROPERTY_CONFIGURABLESERVICE, value);
		return this;
	}

	/**
	 * Get the <I>ISeriesPcmlCallNodeUDN</I> "<I>configurableService</I>" property
	 * 
	 * @return String; the value of the property "<I>configurableService</I>"
	 */
	public String getConfigurableService() {
		return (String)getPropertyValue(ISeriesPcmlCallNodeUDN.PROPERTY_CONFIGURABLESERVICE);
	}

	/**
	 * Set the <I>ISeriesPcmlCallNodeUDN</I> "<I>isXPcml</I>" property
	 * 
	 * @param value boolean ; the value to set the property "<I>isXPcml</I>"
	 */
	public ISeriesPcmlCallNodeUDN setIsXPcml(boolean value) {
		setProperty(ISeriesPcmlCallNodeUDN.PROPERTY_ISXPCML, String.valueOf(value));
		return this;
	}

	/**
	 * Get the <I>ISeriesPcmlCallNodeUDN</I> "<I>isXPcml</I>" property
	 * 
	 * @return boolean; the value of the property "<I>isXPcml</I>"
	 */
	public boolean getIsXPcml(){
	if (getPropertyValue(ISeriesPcmlCallNodeUDN.PROPERTY_ISXPCML).equals("true")){
		return true;
	} else {
		return false;
		}
	}

	/**
	 * Set the <I>ISeriesPcmlCallNodeUDN</I> "<I>pcmlCall</I>" property
	 * 
	 * @param value String ; the value to set the property "<I>pcmlCall</I>"
	 */
	public ISeriesPcmlCallNodeUDN setPcmlCall(String value) {
		setProperty(ISeriesPcmlCallNodeUDN.PROPERTY_PCMLCALL, value);
		return this;
	}

	/**
	 * Get the <I>ISeriesPcmlCallNodeUDN</I> "<I>pcmlCall</I>" property
	 * 
	 * @return String; the value of the property "<I>pcmlCall</I>"
	 */
	public String getPcmlCall() {
		return (String)getPropertyValue(ISeriesPcmlCallNodeUDN.PROPERTY_PCMLCALL);
	}

	/**
	 * Set the <I>ISeriesPcmlCallNodeUDN</I> "<I>libraryList</I>" property
	 * 
	 * @param value String ; the value to set the property "<I>libraryList</I>"
	 */
	public ISeriesPcmlCallNodeUDN setLibraryList(String value) {
		setProperty(ISeriesPcmlCallNodeUDN.PROPERTY_LIBRARYLIST, value);
		return this;
	}

	/**
	 * Get the <I>ISeriesPcmlCallNodeUDN</I> "<I>libraryList</I>" property
	 * 
	 * @return String; the value of the property "<I>libraryList</I>"
	 */
	public String getLibraryList() {
		return (String)getPropertyValue(ISeriesPcmlCallNodeUDN.PROPERTY_LIBRARYLIST);
	}

	/**
	 * Set the <I>ISeriesPcmlCallNodeUDN</I> "<I>ccsid</I>" property
	 * 
	 * @param value int ; the value to set the property "<I>ccsid</I>"
	 */
	public ISeriesPcmlCallNodeUDN setCcsid(int value) {
		setProperty(ISeriesPcmlCallNodeUDN.PROPERTY_CCSID, Integer.toString(value));
		return this;
	}

	/**
	 * Get the <I>ISeriesPcmlCallNodeUDN</I> <I>ccsid</I> property
	 * 
	 * @return int; the value of the property "<I>ccsid</I>"
	 */
	public int getCcsid() {
		String value = (String)getPropertyValue(ISeriesPcmlCallNodeUDN.PROPERTY_CCSID);
		return Integer.valueOf(value).intValue();
	}

	/**
	 * Set the <I>ISeriesPcmlCallNodeUDN</I> "<I>toPcml</I>" property
	 * 
	 * @param value String ; the value to set the property "<I>toPcml</I>"
	 */
	public ISeriesPcmlCallNodeUDN setToPcml(String value) {
		setProperty(ISeriesPcmlCallNodeUDN.PROPERTY_TOPCML, value);
		return this;
	}

	/**
	 * Get the <I>ISeriesPcmlCallNodeUDN</I> "<I>toPcml</I>" property
	 * 
	 * @return String; the value of the property "<I>toPcml</I>"
	 */
	public String getToPcml() {
		return (String)getPropertyValue(ISeriesPcmlCallNodeUDN.PROPERTY_TOPCML);
	}

	/**
	 * Set the <I>ISeriesPcmlCallNodeUDN</I> "<I>toXPcml</I>" property
	 * 
	 * @param value String ; the value to set the property "<I>toXPcml</I>"
	 */
	public ISeriesPcmlCallNodeUDN setToXPcml(String value) {
		setProperty(ISeriesPcmlCallNodeUDN.PROPERTY_TOXPCML, value);
		return this;
	}

	/**
	 * Get the <I>ISeriesPcmlCallNodeUDN</I> "<I>toXPcml</I>" property
	 * 
	 * @return String; the value of the property "<I>toXPcml</I>"
	 */
	public String getToXPcml() {
		return (String)getPropertyValue(ISeriesPcmlCallNodeUDN.PROPERTY_TOXPCML);
	}

	/**
	 * Set the <I>ISeriesPcmlCallNodeUDN</I> "<I>fromXPcml</I>" property
	 * 
	 * @param value String ; the value to set the property "<I>fromXPcml</I>"
	 */
	public ISeriesPcmlCallNodeUDN setFromXPcml(String value) {
		setProperty(ISeriesPcmlCallNodeUDN.PROPERTY_FROMXPCML, value);
		return this;
	}

	/**
	 * Get the <I>ISeriesPcmlCallNodeUDN</I> "<I>fromXPcml</I>" property
	 * 
	 * @return String; the value of the property "<I>fromXPcml</I>"
	 */
	public String getFromXPcml() {
		return (String)getPropertyValue(ISeriesPcmlCallNodeUDN.PROPERTY_FROMXPCML);
	}

	/**
	 * Set the <I>ISeriesPcmlCallNodeUDN</I> "<I>enable</I>" property
	 * 
	 * @param value boolean ; the value to set the property "<I>enable</I>"
	 */
	public ISeriesPcmlCallNodeUDN setEnable(boolean value) {
		setProperty(ISeriesPcmlCallNodeUDN.PROPERTY_ENABLE, String.valueOf(value));
		return this;
	}

	/**
	 * Get the <I>ISeriesPcmlCallNodeUDN</I> "<I>enable</I>" property
	 * 
	 * @return boolean; the value of the property "<I>enable</I>"
	 */
	public boolean getEnable(){
	if (getPropertyValue(ISeriesPcmlCallNodeUDN.PROPERTY_ENABLE).equals("true")){
		return true;
	} else {
		return false;
		}
	}

	/**
	 * Set the <I>ISeriesPcmlCallNodeUDN</I> "<I>directory</I>" property
	 * 
	 * @param value String ; the value to set the property "<I>directory</I>"
	 */
	public ISeriesPcmlCallNodeUDN setDirectory(String value) {
		setProperty(ISeriesPcmlCallNodeUDN.PROPERTY_DIRECTORY, value);
		return this;
	}

	/**
	 * Get the <I>ISeriesPcmlCallNodeUDN</I> "<I>directory</I>" property
	 * 
	 * @return String; the value of the property "<I>directory</I>"
	 */
	public String getDirectory() {
		return (String)getPropertyValue(ISeriesPcmlCallNodeUDN.PROPERTY_DIRECTORY);
	}

	public String getNodeName() {
		String retVal = super.getNodeName();
		if ((retVal==null) || retVal.equals(""))
			retVal = "ISeriesPcmlCall";
		return retVal;
	};
}
