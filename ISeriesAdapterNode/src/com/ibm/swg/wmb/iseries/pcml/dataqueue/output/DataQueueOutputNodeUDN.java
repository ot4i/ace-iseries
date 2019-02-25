package com.ibm.swg.wmb.iseries.pcml.dataqueue.output;

import com.ibm.broker.config.appdev.InputTerminal;
import com.ibm.broker.config.appdev.Node;
import com.ibm.broker.config.appdev.NodeProperty;
import com.ibm.broker.config.appdev.OutputTerminal;

/*** 
 * <p>  <I>DataQueueOutputNodeUDN</I> instance</p>
 * <p></p>
 */
public class DataQueueOutputNodeUDN extends Node {

	private static final long serialVersionUID = 1L;

	// Node constants
	protected final static String NODE_TYPE_NAME = "com/ibm/swg/wmb/iseries/pcml/dataqueue/output/DataQueueOutputNode";
	protected final static String NODE_GRAPHIC_16 = "platform:/plugin/ISeriesAdapterNode/icons/full/obj16/com/ibm/swg/wmb/iseries/pcml/dataqueue/output/DataQueueOutput.gif";
	protected final static String NODE_GRAPHIC_32 = "platform:/plugin/ISeriesAdapterNode/icons/full/obj30/com/ibm/swg/wmb/iseries/pcml/dataqueue/output/DataQueueOutput.gif";

	protected final static String PROPERTY_CONFIGURABLESERVICE = "configurableService";
	protected final static String PROPERTY_QUEUENAME = "queueName";
	protected final static String PROPERTY_LIBRARY = "library";
	protected final static String PROPERTY_TYPE = "type";
	protected final static String PROPERTY_KEYED = "keyed";
	protected final static String PROPERTY_KEY = "key";
	protected final static String PROPERTY_KEYLENGTH = "keylength";
	protected final static String PROPERTY_ENABLE = "enable";
	protected final static String PROPERTY_DIRECTORY = "directory";

	protected NodeProperty[] getNodeProperties() {
		return new NodeProperty[] {
			new NodeProperty(DataQueueOutputNodeUDN.PROPERTY_CONFIGURABLESERVICE,		NodeProperty.Usage.OPTIONAL,	false,	NodeProperty.Type.STRING, "myISeries","","",	"com/ibm/swg/wmb/iseries/pcml/dataqueue/output/DataQueueOutput",	"ISeriesAdapterNode"),
			new NodeProperty(DataQueueOutputNodeUDN.PROPERTY_QUEUENAME,		NodeProperty.Usage.OPTIONAL,	false,	NodeProperty.Type.STRING, null,"","",	"com/ibm/swg/wmb/iseries/pcml/dataqueue/output/DataQueueOutput",	"ISeriesAdapterNode"),
			new NodeProperty(DataQueueOutputNodeUDN.PROPERTY_LIBRARY,		NodeProperty.Usage.OPTIONAL,	false,	NodeProperty.Type.STRING, null,"","",	"com/ibm/swg/wmb/iseries/pcml/dataqueue/output/DataQueueOutput",	"ISeriesAdapterNode"),
			new NodeProperty(DataQueueOutputNodeUDN.PROPERTY_TYPE,		NodeProperty.Usage.OPTIONAL,	false,	NodeProperty.Type.STRING, "DTAQ","","",	"com/ibm/swg/wmb/iseries/pcml/dataqueue/output/DataQueueOutput",	"ISeriesAdapterNode"),
			new NodeProperty(DataQueueOutputNodeUDN.PROPERTY_KEYED,		NodeProperty.Usage.OPTIONAL,	false,	NodeProperty.Type.BOOLEAN, "false","","",	"com/ibm/swg/wmb/iseries/pcml/dataqueue/output/DataQueueOutput",	"ISeriesAdapterNode"),
			new NodeProperty(DataQueueOutputNodeUDN.PROPERTY_KEY,		NodeProperty.Usage.OPTIONAL,	false,	NodeProperty.Type.STRING, null,"","",	"com/ibm/swg/wmb/iseries/pcml/dataqueue/output/DataQueueOutput",	"ISeriesAdapterNode"),
			new NodeProperty(DataQueueOutputNodeUDN.PROPERTY_KEYLENGTH,		NodeProperty.Usage.OPTIONAL,	false,	NodeProperty.Type.INTEGER, null,"","",	"com/ibm/swg/wmb/iseries/pcml/dataqueue/output/DataQueueOutput",	"ISeriesAdapterNode"),
			new NodeProperty(DataQueueOutputNodeUDN.PROPERTY_ENABLE,		NodeProperty.Usage.OPTIONAL,	false,	NodeProperty.Type.BOOLEAN, "false","","",	"com/ibm/swg/wmb/iseries/pcml/dataqueue/output/DataQueueOutput",	"ISeriesAdapterNode"),
			new NodeProperty(DataQueueOutputNodeUDN.PROPERTY_DIRECTORY,		NodeProperty.Usage.OPTIONAL,	false,	NodeProperty.Type.STRING, "","","",	"com/ibm/swg/wmb/iseries/pcml/dataqueue/output/DataQueueOutput",	"ISeriesAdapterNode")
		};
	}

	public DataQueueOutputNodeUDN() {
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
	 * Set the <I>DataQueueOutputNodeUDN</I> "<I>configurableService</I>" property
	 * 
	 * @param value String ; the value to set the property "<I>configurableService</I>"
	 */
	public DataQueueOutputNodeUDN setConfigurableService(String value) {
		setProperty(DataQueueOutputNodeUDN.PROPERTY_CONFIGURABLESERVICE, value);
		return this;
	}

	/**
	 * Get the <I>DataQueueOutputNodeUDN</I> "<I>configurableService</I>" property
	 * 
	 * @return String; the value of the property "<I>configurableService</I>"
	 */
	public String getConfigurableService() {
		return (String)getPropertyValue(DataQueueOutputNodeUDN.PROPERTY_CONFIGURABLESERVICE);
	}

	/**
	 * Set the <I>DataQueueOutputNodeUDN</I> "<I>queueName</I>" property
	 * 
	 * @param value String ; the value to set the property "<I>queueName</I>"
	 */
	public DataQueueOutputNodeUDN setQueueName(String value) {
		setProperty(DataQueueOutputNodeUDN.PROPERTY_QUEUENAME, value);
		return this;
	}

	/**
	 * Get the <I>DataQueueOutputNodeUDN</I> "<I>queueName</I>" property
	 * 
	 * @return String; the value of the property "<I>queueName</I>"
	 */
	public String getQueueName() {
		return (String)getPropertyValue(DataQueueOutputNodeUDN.PROPERTY_QUEUENAME);
	}

	/**
	 * Set the <I>DataQueueOutputNodeUDN</I> "<I>library</I>" property
	 * 
	 * @param value String ; the value to set the property "<I>library</I>"
	 */
	public DataQueueOutputNodeUDN setLibrary(String value) {
		setProperty(DataQueueOutputNodeUDN.PROPERTY_LIBRARY, value);
		return this;
	}

	/**
	 * Get the <I>DataQueueOutputNodeUDN</I> "<I>library</I>" property
	 * 
	 * @return String; the value of the property "<I>library</I>"
	 */
	public String getLibrary() {
		return (String)getPropertyValue(DataQueueOutputNodeUDN.PROPERTY_LIBRARY);
	}

	/**
	 * Set the <I>DataQueueOutputNodeUDN</I> "<I>type</I>" property
	 * 
	 * @param value String ; the value to set the property "<I>type</I>"
	 */
	public DataQueueOutputNodeUDN setType(String value) {
		setProperty(DataQueueOutputNodeUDN.PROPERTY_TYPE, value);
		return this;
	}

	/**
	 * Get the <I>DataQueueOutputNodeUDN</I> "<I>type</I>" property
	 * 
	 * @return String; the value of the property "<I>type</I>"
	 */
	public String getType() {
		return (String)getPropertyValue(DataQueueOutputNodeUDN.PROPERTY_TYPE);
	}

	/**
	 * Set the <I>DataQueueOutputNodeUDN</I> "<I>keyed</I>" property
	 * 
	 * @param value boolean ; the value to set the property "<I>keyed</I>"
	 */
	public DataQueueOutputNodeUDN setKeyed(boolean value) {
		setProperty(DataQueueOutputNodeUDN.PROPERTY_KEYED, String.valueOf(value));
		return this;
	}

	/**
	 * Get the <I>DataQueueOutputNodeUDN</I> "<I>keyed</I>" property
	 * 
	 * @return boolean; the value of the property "<I>keyed</I>"
	 */
	public boolean getKeyed(){
	if (getPropertyValue(DataQueueOutputNodeUDN.PROPERTY_KEYED).equals("true")){
		return true;
	} else {
		return false;
		}
	}

	/**
	 * Set the <I>DataQueueOutputNodeUDN</I> "<I>key</I>" property
	 * 
	 * @param value String ; the value to set the property "<I>key</I>"
	 */
	public DataQueueOutputNodeUDN setKey(String value) {
		setProperty(DataQueueOutputNodeUDN.PROPERTY_KEY, value);
		return this;
	}

	/**
	 * Get the <I>DataQueueOutputNodeUDN</I> "<I>key</I>" property
	 * 
	 * @return String; the value of the property "<I>key</I>"
	 */
	public String getKey() {
		return (String)getPropertyValue(DataQueueOutputNodeUDN.PROPERTY_KEY);
	}

	/**
	 * Set the <I>DataQueueOutputNodeUDN</I> "<I>keylength</I>" property
	 * 
	 * @param value int ; the value to set the property "<I>keylength</I>"
	 */
	public DataQueueOutputNodeUDN setKeylength(int value) {
		setProperty(DataQueueOutputNodeUDN.PROPERTY_KEYLENGTH, Integer.toString(value));
		return this;
	}

	/**
	 * Get the <I>DataQueueOutputNodeUDN</I> <I>keylength</I> property
	 * 
	 * @return int; the value of the property "<I>keylength</I>"
	 */
	public int getKeylength() {
		String value = (String)getPropertyValue(DataQueueOutputNodeUDN.PROPERTY_KEYLENGTH);
		return Integer.valueOf(value).intValue();
	}

	/**
	 * Set the <I>DataQueueOutputNodeUDN</I> "<I>enable</I>" property
	 * 
	 * @param value boolean ; the value to set the property "<I>enable</I>"
	 */
	public DataQueueOutputNodeUDN setEnable(boolean value) {
		setProperty(DataQueueOutputNodeUDN.PROPERTY_ENABLE, String.valueOf(value));
		return this;
	}

	/**
	 * Get the <I>DataQueueOutputNodeUDN</I> "<I>enable</I>" property
	 * 
	 * @return boolean; the value of the property "<I>enable</I>"
	 */
	public boolean getEnable(){
	if (getPropertyValue(DataQueueOutputNodeUDN.PROPERTY_ENABLE).equals("true")){
		return true;
	} else {
		return false;
		}
	}

	/**
	 * Set the <I>DataQueueOutputNodeUDN</I> "<I>directory</I>" property
	 * 
	 * @param value String ; the value to set the property "<I>directory</I>"
	 */
	public DataQueueOutputNodeUDN setDirectory(String value) {
		setProperty(DataQueueOutputNodeUDN.PROPERTY_DIRECTORY, value);
		return this;
	}

	/**
	 * Get the <I>DataQueueOutputNodeUDN</I> "<I>directory</I>" property
	 * 
	 * @return String; the value of the property "<I>directory</I>"
	 */
	public String getDirectory() {
		return (String)getPropertyValue(DataQueueOutputNodeUDN.PROPERTY_DIRECTORY);
	}

	public String getNodeName() {
		String retVal = super.getNodeName();
		if ((retVal==null) || retVal.equals(""))
			retVal = "DataQueueOutput";
		return retVal;
	};
}
