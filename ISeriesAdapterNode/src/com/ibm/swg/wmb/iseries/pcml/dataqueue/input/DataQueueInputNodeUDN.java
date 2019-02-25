package com.ibm.swg.wmb.iseries.pcml.dataqueue.input;

import com.ibm.broker.config.appdev.InputTerminal;
import com.ibm.broker.config.appdev.Node;
import com.ibm.broker.config.appdev.NodeProperty;
import com.ibm.broker.config.appdev.OutputTerminal;

/*** 
 * <p>  <I>DataQueueInputNodeUDN</I> instance</p>
 * <p></p>
 */
public class DataQueueInputNodeUDN extends Node {

	private static final long serialVersionUID = 1L;

	// Node constants
	protected final static String NODE_TYPE_NAME = "com/ibm/swg/wmb/iseries/pcml/dataqueue/input/DataQueueInputNode";
	protected final static String NODE_GRAPHIC_16 = "platform:/plugin/ISeriesAdapterNode/icons/full/obj16/com/ibm/swg/wmb/iseries/pcml/dataqueue/input/DataQueueInput.gif";
	protected final static String NODE_GRAPHIC_32 = "platform:/plugin/ISeriesAdapterNode/icons/full/obj30/com/ibm/swg/wmb/iseries/pcml/dataqueue/input/DataQueueInput.gif";

	protected final static String PROPERTY_CONFIGURABLESERVICE = "configurableService";
	protected final static String PROPERTY_QUEUENAME = "queueName";
	protected final static String PROPERTY_WAITINTERVAL = "waitInterval";
	protected final static String PROPERTY_LIBRARY = "library";
	protected final static String PROPERTY_TYPE = "type";
	protected final static String PROPERTY_KEYED = "keyed";
	protected final static String PROPERTY_KEY = "key";
	protected final static String PROPERTY_KEYLENGTH = "keylength";
	protected final static String PROPERTY_SEARCHTYPE = "searchType";
	protected final static String PROPERTY_ENABLE = "enable";
	protected final static String PROPERTY_DIRECTORY = "directory";


	/**
	 * <I>ENUM_DATAQUEUEINPUT_SEARCHTYPE</I>
	 * <pre>
	 * ENUM_DATAQUEUEINPUT_SEARCHTYPE.EQ = EQ
	 * ENUM_DATAQUEUEINPUT_SEARCHTYPE.NE = NE
	 * ENUM_DATAQUEUEINPUT_SEARCHTYPE.LT = LT
	 * ENUM_DATAQUEUEINPUT_SEARCHTYPE.LE = LE
	 * ENUM_DATAQUEUEINPUT_SEARCHTYPE.GT = GT
	 * ENUM_DATAQUEUEINPUT_SEARCHTYPE.GE = GE
	 * </pre>
	 */
	public static class ENUM_DATAQUEUEINPUT_SEARCHTYPE {
		private String value;

		public static final ENUM_DATAQUEUEINPUT_SEARCHTYPE EQ = new ENUM_DATAQUEUEINPUT_SEARCHTYPE("EQ");
		public static final ENUM_DATAQUEUEINPUT_SEARCHTYPE NE = new ENUM_DATAQUEUEINPUT_SEARCHTYPE("NE");
		public static final ENUM_DATAQUEUEINPUT_SEARCHTYPE LT = new ENUM_DATAQUEUEINPUT_SEARCHTYPE("LT");
		public static final ENUM_DATAQUEUEINPUT_SEARCHTYPE LE = new ENUM_DATAQUEUEINPUT_SEARCHTYPE("LE");
		public static final ENUM_DATAQUEUEINPUT_SEARCHTYPE GT = new ENUM_DATAQUEUEINPUT_SEARCHTYPE("GT");
		public static final ENUM_DATAQUEUEINPUT_SEARCHTYPE GE = new ENUM_DATAQUEUEINPUT_SEARCHTYPE("GE");

		protected ENUM_DATAQUEUEINPUT_SEARCHTYPE(String value) {
			this.value = value;
		}
		public String toString() {
			return value;
		}

		protected static ENUM_DATAQUEUEINPUT_SEARCHTYPE getEnumFromString(String enumValue) {
			ENUM_DATAQUEUEINPUT_SEARCHTYPE enumConst = ENUM_DATAQUEUEINPUT_SEARCHTYPE.EQ;
			if (ENUM_DATAQUEUEINPUT_SEARCHTYPE.NE.value.equals(enumValue)) enumConst = ENUM_DATAQUEUEINPUT_SEARCHTYPE.NE;
			if (ENUM_DATAQUEUEINPUT_SEARCHTYPE.LT.value.equals(enumValue)) enumConst = ENUM_DATAQUEUEINPUT_SEARCHTYPE.LT;
			if (ENUM_DATAQUEUEINPUT_SEARCHTYPE.LE.value.equals(enumValue)) enumConst = ENUM_DATAQUEUEINPUT_SEARCHTYPE.LE;
			if (ENUM_DATAQUEUEINPUT_SEARCHTYPE.GT.value.equals(enumValue)) enumConst = ENUM_DATAQUEUEINPUT_SEARCHTYPE.GT;
			if (ENUM_DATAQUEUEINPUT_SEARCHTYPE.GE.value.equals(enumValue)) enumConst = ENUM_DATAQUEUEINPUT_SEARCHTYPE.GE;
			return enumConst;
		}

		public static String[] values = new String[]{ "EQ", "NE", "LT", "LE", "GT", "GE" };

	}
	protected NodeProperty[] getNodeProperties() {
		return new NodeProperty[] {
			new NodeProperty(DataQueueInputNodeUDN.PROPERTY_CONFIGURABLESERVICE,		NodeProperty.Usage.OPTIONAL,	false,	NodeProperty.Type.STRING, "myISeries","","",	"com/ibm/swg/wmb/iseries/pcml/dataqueue/input/DataQueueInput",	"ISeriesAdapterNode"),
			new NodeProperty(DataQueueInputNodeUDN.PROPERTY_QUEUENAME,		NodeProperty.Usage.MANDATORY,	false,	NodeProperty.Type.STRING, null,"","",	"com/ibm/swg/wmb/iseries/pcml/dataqueue/input/DataQueueInput",	"ISeriesAdapterNode"),
			new NodeProperty(DataQueueInputNodeUDN.PROPERTY_WAITINTERVAL,		NodeProperty.Usage.OPTIONAL,	false,	NodeProperty.Type.INTEGER, "0","","",	"com/ibm/swg/wmb/iseries/pcml/dataqueue/input/DataQueueInput",	"ISeriesAdapterNode"),
			new NodeProperty(DataQueueInputNodeUDN.PROPERTY_LIBRARY,		NodeProperty.Usage.MANDATORY,	false,	NodeProperty.Type.STRING, null,"","",	"com/ibm/swg/wmb/iseries/pcml/dataqueue/input/DataQueueInput",	"ISeriesAdapterNode"),
			new NodeProperty(DataQueueInputNodeUDN.PROPERTY_TYPE,		NodeProperty.Usage.OPTIONAL,	false,	NodeProperty.Type.STRING, "DTAQ","","",	"com/ibm/swg/wmb/iseries/pcml/dataqueue/input/DataQueueInput",	"ISeriesAdapterNode"),
			new NodeProperty(DataQueueInputNodeUDN.PROPERTY_KEYED,		NodeProperty.Usage.OPTIONAL,	false,	NodeProperty.Type.BOOLEAN, "false","","",	"com/ibm/swg/wmb/iseries/pcml/dataqueue/input/DataQueueInput",	"ISeriesAdapterNode"),
			new NodeProperty(DataQueueInputNodeUDN.PROPERTY_KEY,		NodeProperty.Usage.OPTIONAL,	false,	NodeProperty.Type.STRING, null,"","",	"com/ibm/swg/wmb/iseries/pcml/dataqueue/input/DataQueueInput",	"ISeriesAdapterNode"),
			new NodeProperty(DataQueueInputNodeUDN.PROPERTY_KEYLENGTH,		NodeProperty.Usage.OPTIONAL,	false,	NodeProperty.Type.INTEGER, null,"","",	"com/ibm/swg/wmb/iseries/pcml/dataqueue/input/DataQueueInput",	"ISeriesAdapterNode"),
			new NodeProperty(DataQueueInputNodeUDN.PROPERTY_SEARCHTYPE,		NodeProperty.Usage.OPTIONAL,	false,	NodeProperty.Type.ENUMERATION, "EQ", ENUM_DATAQUEUEINPUT_SEARCHTYPE.class,"","",	"com/ibm/swg/wmb/iseries/pcml/dataqueue/input/DataQueueInput",	"ISeriesAdapterNode"),
			new NodeProperty(DataQueueInputNodeUDN.PROPERTY_ENABLE,		NodeProperty.Usage.OPTIONAL,	false,	NodeProperty.Type.BOOLEAN, "false","","",	"com/ibm/swg/wmb/iseries/pcml/dataqueue/input/DataQueueInput",	"ISeriesAdapterNode"),
			new NodeProperty(DataQueueInputNodeUDN.PROPERTY_DIRECTORY,		NodeProperty.Usage.OPTIONAL,	false,	NodeProperty.Type.STRING, null,"","",	"com/ibm/swg/wmb/iseries/pcml/dataqueue/input/DataQueueInput",	"ISeriesAdapterNode")
		};
	}

	public DataQueueInputNodeUDN() {
	}

	@Override
	public InputTerminal[] getInputTerminals() {
		return null;
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
	 * Set the <I>DataQueueInputNodeUDN</I> "<I>configurableService</I>" property
	 * 
	 * @param value String ; the value to set the property "<I>configurableService</I>"
	 */
	public DataQueueInputNodeUDN setConfigurableService(String value) {
		setProperty(DataQueueInputNodeUDN.PROPERTY_CONFIGURABLESERVICE, value);
		return this;
	}

	/**
	 * Get the <I>DataQueueInputNodeUDN</I> "<I>configurableService</I>" property
	 * 
	 * @return String; the value of the property "<I>configurableService</I>"
	 */
	public String getConfigurableService() {
		return (String)getPropertyValue(DataQueueInputNodeUDN.PROPERTY_CONFIGURABLESERVICE);
	}

	/**
	 * Set the <I>DataQueueInputNodeUDN</I> "<I>queueName</I>" property
	 * 
	 * @param value String ; the value to set the property "<I>queueName</I>"
	 */
	public DataQueueInputNodeUDN setQueueName(String value) {
		setProperty(DataQueueInputNodeUDN.PROPERTY_QUEUENAME, value);
		return this;
	}

	/**
	 * Get the <I>DataQueueInputNodeUDN</I> "<I>queueName</I>" property
	 * 
	 * @return String; the value of the property "<I>queueName</I>"
	 */
	public String getQueueName() {
		return (String)getPropertyValue(DataQueueInputNodeUDN.PROPERTY_QUEUENAME);
	}

	/**
	 * Set the <I>DataQueueInputNodeUDN</I> "<I>waitInterval</I>" property
	 * 
	 * @param value int ; the value to set the property "<I>waitInterval</I>"
	 */
	public DataQueueInputNodeUDN setWaitInterval(int value) {
		setProperty(DataQueueInputNodeUDN.PROPERTY_WAITINTERVAL, Integer.toString(value));
		return this;
	}

	/**
	 * Get the <I>DataQueueInputNodeUDN</I> <I>waitInterval</I> property
	 * 
	 * @return int; the value of the property "<I>waitInterval</I>"
	 */
	public int getWaitInterval() {
		String value = (String)getPropertyValue(DataQueueInputNodeUDN.PROPERTY_WAITINTERVAL);
		return Integer.valueOf(value).intValue();
	}

	/**
	 * Set the <I>DataQueueInputNodeUDN</I> "<I>library</I>" property
	 * 
	 * @param value String ; the value to set the property "<I>library</I>"
	 */
	public DataQueueInputNodeUDN setLibrary(String value) {
		setProperty(DataQueueInputNodeUDN.PROPERTY_LIBRARY, value);
		return this;
	}

	/**
	 * Get the <I>DataQueueInputNodeUDN</I> "<I>library</I>" property
	 * 
	 * @return String; the value of the property "<I>library</I>"
	 */
	public String getLibrary() {
		return (String)getPropertyValue(DataQueueInputNodeUDN.PROPERTY_LIBRARY);
	}

	/**
	 * Set the <I>DataQueueInputNodeUDN</I> "<I>type</I>" property
	 * 
	 * @param value String ; the value to set the property "<I>type</I>"
	 */
	public DataQueueInputNodeUDN setType(String value) {
		setProperty(DataQueueInputNodeUDN.PROPERTY_TYPE, value);
		return this;
	}

	/**
	 * Get the <I>DataQueueInputNodeUDN</I> "<I>type</I>" property
	 * 
	 * @return String; the value of the property "<I>type</I>"
	 */
	public String getType() {
		return (String)getPropertyValue(DataQueueInputNodeUDN.PROPERTY_TYPE);
	}

	/**
	 * Set the <I>DataQueueInputNodeUDN</I> "<I>keyed</I>" property
	 * 
	 * @param value boolean ; the value to set the property "<I>keyed</I>"
	 */
	public DataQueueInputNodeUDN setKeyed(boolean value) {
		setProperty(DataQueueInputNodeUDN.PROPERTY_KEYED, String.valueOf(value));
		return this;
	}

	/**
	 * Get the <I>DataQueueInputNodeUDN</I> "<I>keyed</I>" property
	 * 
	 * @return boolean; the value of the property "<I>keyed</I>"
	 */
	public boolean getKeyed(){
	if (getPropertyValue(DataQueueInputNodeUDN.PROPERTY_KEYED).equals("true")){
		return true;
	} else {
		return false;
		}
	}

	/**
	 * Set the <I>DataQueueInputNodeUDN</I> "<I>key</I>" property
	 * 
	 * @param value String ; the value to set the property "<I>key</I>"
	 */
	public DataQueueInputNodeUDN setKey(String value) {
		setProperty(DataQueueInputNodeUDN.PROPERTY_KEY, value);
		return this;
	}

	/**
	 * Get the <I>DataQueueInputNodeUDN</I> "<I>key</I>" property
	 * 
	 * @return String; the value of the property "<I>key</I>"
	 */
	public String getKey() {
		return (String)getPropertyValue(DataQueueInputNodeUDN.PROPERTY_KEY);
	}

	/**
	 * Set the <I>DataQueueInputNodeUDN</I> "<I>keylength</I>" property
	 * 
	 * @param value int ; the value to set the property "<I>keylength</I>"
	 */
	public DataQueueInputNodeUDN setKeylength(int value) {
		setProperty(DataQueueInputNodeUDN.PROPERTY_KEYLENGTH, Integer.toString(value));
		return this;
	}

	/**
	 * Get the <I>DataQueueInputNodeUDN</I> <I>keylength</I> property
	 * 
	 * @return int; the value of the property "<I>keylength</I>"
	 */
	public int getKeylength() {
		String value = (String)getPropertyValue(DataQueueInputNodeUDN.PROPERTY_KEYLENGTH);
		return Integer.valueOf(value).intValue();
	}

	/**
	 * Set the <I>DataQueueInputNodeUDN</I> "<I>searchType</I>" property
	 * 
	 * @param value ENUM_DATAQUEUEINPUT_SEARCHTYPE ; the value to set the property "<I>searchType</I>"
	 */
	public DataQueueInputNodeUDN setSearchType(ENUM_DATAQUEUEINPUT_SEARCHTYPE value) {
		setProperty(DataQueueInputNodeUDN.PROPERTY_SEARCHTYPE, value.toString());
		return this;
	}

	/**
	 * Get the <I>DataQueueInputNodeUDN</I> "<I>searchType</I>" property
	 * 
	 * @return ENUM_DATAQUEUEINPUT_SEARCHTYPE; the value of the property "<I>searchType</I>"
	 */
	public ENUM_DATAQUEUEINPUT_SEARCHTYPE getSearchType() {
		ENUM_DATAQUEUEINPUT_SEARCHTYPE value = ENUM_DATAQUEUEINPUT_SEARCHTYPE.getEnumFromString((String)getPropertyValue(DataQueueInputNodeUDN.PROPERTY_SEARCHTYPE));
		return value;
	}

	/**
	 * Set the <I>DataQueueInputNodeUDN</I> "<I>enable</I>" property
	 * 
	 * @param value boolean ; the value to set the property "<I>enable</I>"
	 */
	public DataQueueInputNodeUDN setEnable(boolean value) {
		setProperty(DataQueueInputNodeUDN.PROPERTY_ENABLE, String.valueOf(value));
		return this;
	}

	/**
	 * Get the <I>DataQueueInputNodeUDN</I> "<I>enable</I>" property
	 * 
	 * @return boolean; the value of the property "<I>enable</I>"
	 */
	public boolean getEnable(){
	if (getPropertyValue(DataQueueInputNodeUDN.PROPERTY_ENABLE).equals("true")){
		return true;
	} else {
		return false;
		}
	}

	/**
	 * Set the <I>DataQueueInputNodeUDN</I> "<I>directory</I>" property
	 * 
	 * @param value String ; the value to set the property "<I>directory</I>"
	 */
	public DataQueueInputNodeUDN setDirectory(String value) {
		setProperty(DataQueueInputNodeUDN.PROPERTY_DIRECTORY, value);
		return this;
	}

	/**
	 * Get the <I>DataQueueInputNodeUDN</I> "<I>directory</I>" property
	 * 
	 * @return String; the value of the property "<I>directory</I>"
	 */
	public String getDirectory() {
		return (String)getPropertyValue(DataQueueInputNodeUDN.PROPERTY_DIRECTORY);
	}

	public String getNodeName() {
		String retVal = super.getNodeName();
		if ((retVal==null) || retVal.equals(""))
			retVal = "DataQueueInput";
		return retVal;
	};
}
