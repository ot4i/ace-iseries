package com.ibm.swg.iseries.dataqueue;

import com.ibm.broker.config.appdev.InputTerminal;
import com.ibm.broker.config.appdev.Node;
import com.ibm.broker.config.appdev.NodeProperty;
import com.ibm.broker.config.appdev.OutputTerminal;

/*** 
 * <p>  <I>DataQueueGetNodeUDN</I> instance</p>
 * <p></p>
 */
public class DataQueueGetNodeUDN extends Node {

	private static final long serialVersionUID = 1L;

	// Node constants
	protected final static String NODE_TYPE_NAME = "com/ibm/swg/iseries/dataqueue/DataQueueGetNode";
	protected final static String NODE_GRAPHIC_16 = "platform:/plugin/ISeries/icons/full/obj16/com/ibm/swg/iseries/dataqueue/DataQueueGet.gif";
	protected final static String NODE_GRAPHIC_32 = "platform:/plugin/ISeries/icons/full/obj30/com/ibm/swg/iseries/dataqueue/DataQueueGet.gif";

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
	 * <I>ENUM_DATAQUEUEGET_SEARCHTYPE</I>
	 * <pre>
	 * ENUM_DATAQUEUEGET_SEARCHTYPE.EQ = EQ
	 * ENUM_DATAQUEUEGET_SEARCHTYPE.NE = NE
	 * ENUM_DATAQUEUEGET_SEARCHTYPE.LT = LT
	 * ENUM_DATAQUEUEGET_SEARCHTYPE.LE = LE
	 * ENUM_DATAQUEUEGET_SEARCHTYPE.GT = GT
	 * ENUM_DATAQUEUEGET_SEARCHTYPE.GE = GE
	 * </pre>
	 */
	public static class ENUM_DATAQUEUEGET_SEARCHTYPE {
		private String value;

		public static final ENUM_DATAQUEUEGET_SEARCHTYPE EQ = new ENUM_DATAQUEUEGET_SEARCHTYPE("EQ");
		public static final ENUM_DATAQUEUEGET_SEARCHTYPE NE = new ENUM_DATAQUEUEGET_SEARCHTYPE("NE");
		public static final ENUM_DATAQUEUEGET_SEARCHTYPE LT = new ENUM_DATAQUEUEGET_SEARCHTYPE("LT");
		public static final ENUM_DATAQUEUEGET_SEARCHTYPE LE = new ENUM_DATAQUEUEGET_SEARCHTYPE("LE");
		public static final ENUM_DATAQUEUEGET_SEARCHTYPE GT = new ENUM_DATAQUEUEGET_SEARCHTYPE("GT");
		public static final ENUM_DATAQUEUEGET_SEARCHTYPE GE = new ENUM_DATAQUEUEGET_SEARCHTYPE("GE");

		protected ENUM_DATAQUEUEGET_SEARCHTYPE(String value) {
			this.value = value;
		}
		public String toString() {
			return value;
		}

		protected static ENUM_DATAQUEUEGET_SEARCHTYPE getEnumFromString(String enumValue) {
			ENUM_DATAQUEUEGET_SEARCHTYPE enumConst = ENUM_DATAQUEUEGET_SEARCHTYPE.EQ;
			if (ENUM_DATAQUEUEGET_SEARCHTYPE.NE.value.equals(enumValue)) enumConst = ENUM_DATAQUEUEGET_SEARCHTYPE.NE;
			if (ENUM_DATAQUEUEGET_SEARCHTYPE.LT.value.equals(enumValue)) enumConst = ENUM_DATAQUEUEGET_SEARCHTYPE.LT;
			if (ENUM_DATAQUEUEGET_SEARCHTYPE.LE.value.equals(enumValue)) enumConst = ENUM_DATAQUEUEGET_SEARCHTYPE.LE;
			if (ENUM_DATAQUEUEGET_SEARCHTYPE.GT.value.equals(enumValue)) enumConst = ENUM_DATAQUEUEGET_SEARCHTYPE.GT;
			if (ENUM_DATAQUEUEGET_SEARCHTYPE.GE.value.equals(enumValue)) enumConst = ENUM_DATAQUEUEGET_SEARCHTYPE.GE;
			return enumConst;
		}

		public static String[] values = new String[]{ "EQ", "NE", "LT", "LE", "GT", "GE" };

	}
	protected NodeProperty[] getNodeProperties() {
		return new NodeProperty[] {
			new NodeProperty(DataQueueGetNodeUDN.PROPERTY_CONFIGURABLESERVICE,		NodeProperty.Usage.OPTIONAL,	false,	NodeProperty.Type.STRING, "myISeries","","",	"com/ibm/swg/iseries/dataqueue/DataQueueGet",	"ISeries"),
			new NodeProperty(DataQueueGetNodeUDN.PROPERTY_QUEUENAME,		NodeProperty.Usage.OPTIONAL,	false,	NodeProperty.Type.STRING, null,"","",	"com/ibm/swg/iseries/dataqueue/DataQueueGet",	"ISeries"),
			new NodeProperty(DataQueueGetNodeUDN.PROPERTY_WAITINTERVAL,		NodeProperty.Usage.OPTIONAL,	false,	NodeProperty.Type.INTEGER, "0","","",	"com/ibm/swg/iseries/dataqueue/DataQueueGet",	"ISeries"),
			new NodeProperty(DataQueueGetNodeUDN.PROPERTY_LIBRARY,		NodeProperty.Usage.OPTIONAL,	false,	NodeProperty.Type.STRING, null,"","",	"com/ibm/swg/iseries/dataqueue/DataQueueGet",	"ISeries"),
			new NodeProperty(DataQueueGetNodeUDN.PROPERTY_TYPE,		NodeProperty.Usage.OPTIONAL,	false,	NodeProperty.Type.STRING, "DTAQ","","",	"com/ibm/swg/iseries/dataqueue/DataQueueGet",	"ISeries"),
			new NodeProperty(DataQueueGetNodeUDN.PROPERTY_KEYED,		NodeProperty.Usage.OPTIONAL,	false,	NodeProperty.Type.BOOLEAN, "false","","",	"com/ibm/swg/iseries/dataqueue/DataQueueGet",	"ISeries"),
			new NodeProperty(DataQueueGetNodeUDN.PROPERTY_KEY,		NodeProperty.Usage.OPTIONAL,	false,	NodeProperty.Type.STRING, null,"","",	"com/ibm/swg/iseries/dataqueue/DataQueueGet",	"ISeries"),
			new NodeProperty(DataQueueGetNodeUDN.PROPERTY_KEYLENGTH,		NodeProperty.Usage.OPTIONAL,	false,	NodeProperty.Type.INTEGER, null,"","",	"com/ibm/swg/iseries/dataqueue/DataQueueGet",	"ISeries"),
			new NodeProperty(DataQueueGetNodeUDN.PROPERTY_SEARCHTYPE,		NodeProperty.Usage.OPTIONAL,	false,	NodeProperty.Type.ENUMERATION, "EQ", ENUM_DATAQUEUEGET_SEARCHTYPE.class,"","",	"com/ibm/swg/iseries/dataqueue/DataQueueGet",	"ISeries"),
			new NodeProperty(DataQueueGetNodeUDN.PROPERTY_ENABLE,		NodeProperty.Usage.OPTIONAL,	false,	NodeProperty.Type.BOOLEAN, "false","","",	"com/ibm/swg/iseries/dataqueue/DataQueueGet",	"ISeries"),
			new NodeProperty(DataQueueGetNodeUDN.PROPERTY_DIRECTORY,		NodeProperty.Usage.OPTIONAL,	false,	NodeProperty.Type.STRING, null,"","",	"com/ibm/swg/iseries/dataqueue/DataQueueGet",	"ISeries")
		};
	}

	public DataQueueGetNodeUDN() {
	}

	public final InputTerminal INPUT_TERMINAL_IN = new InputTerminal(this,"InTerminal.in");
	@Override
	public InputTerminal[] getInputTerminals() {
		return new InputTerminal[] {
			INPUT_TERMINAL_IN
	};
	}

	public final OutputTerminal OUTPUT_TERMINAL_NOMESSAGE = new OutputTerminal(this,"OutTerminal.noMessage");
	public final OutputTerminal OUTPUT_TERMINAL_FAILURE = new OutputTerminal(this,"OutTerminal.failure");
	public final OutputTerminal OUTPUT_TERMINAL_OUT = new OutputTerminal(this,"OutTerminal.out");
	@Override
	public OutputTerminal[] getOutputTerminals() {
		return new OutputTerminal[] {
			OUTPUT_TERMINAL_NOMESSAGE,
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
	 * Set the <I>DataQueueGetNodeUDN</I> "<I>configurableService</I>" property
	 * 
	 * @param value String ; the value to set the property "<I>configurableService</I>"
	 */
	public DataQueueGetNodeUDN setConfigurableService(String value) {
		setProperty(DataQueueGetNodeUDN.PROPERTY_CONFIGURABLESERVICE, value);
		return this;
	}

	/**
	 * Get the <I>DataQueueGetNodeUDN</I> "<I>configurableService</I>" property
	 * 
	 * @return String; the value of the property "<I>configurableService</I>"
	 */
	public String getConfigurableService() {
		return (String)getPropertyValue(DataQueueGetNodeUDN.PROPERTY_CONFIGURABLESERVICE);
	}

	/**
	 * Set the <I>DataQueueGetNodeUDN</I> "<I>queueName</I>" property
	 * 
	 * @param value String ; the value to set the property "<I>queueName</I>"
	 */
	public DataQueueGetNodeUDN setQueueName(String value) {
		setProperty(DataQueueGetNodeUDN.PROPERTY_QUEUENAME, value);
		return this;
	}

	/**
	 * Get the <I>DataQueueGetNodeUDN</I> "<I>queueName</I>" property
	 * 
	 * @return String; the value of the property "<I>queueName</I>"
	 */
	public String getQueueName() {
		return (String)getPropertyValue(DataQueueGetNodeUDN.PROPERTY_QUEUENAME);
	}

	/**
	 * Set the <I>DataQueueGetNodeUDN</I> "<I>waitInterval</I>" property
	 * 
	 * @param value int ; the value to set the property "<I>waitInterval</I>"
	 */
	public DataQueueGetNodeUDN setWaitInterval(int value) {
		setProperty(DataQueueGetNodeUDN.PROPERTY_WAITINTERVAL, Integer.toString(value));
		return this;
	}

	/**
	 * Get the <I>DataQueueGetNodeUDN</I> <I>waitInterval</I> property
	 * 
	 * @return int; the value of the property "<I>waitInterval</I>"
	 */
	public int getWaitInterval() {
		String value = (String)getPropertyValue(DataQueueGetNodeUDN.PROPERTY_WAITINTERVAL);
		return Integer.valueOf(value).intValue();
	}

	/**
	 * Set the <I>DataQueueGetNodeUDN</I> "<I>library</I>" property
	 * 
	 * @param value String ; the value to set the property "<I>library</I>"
	 */
	public DataQueueGetNodeUDN setLibrary(String value) {
		setProperty(DataQueueGetNodeUDN.PROPERTY_LIBRARY, value);
		return this;
	}

	/**
	 * Get the <I>DataQueueGetNodeUDN</I> "<I>library</I>" property
	 * 
	 * @return String; the value of the property "<I>library</I>"
	 */
	public String getLibrary() {
		return (String)getPropertyValue(DataQueueGetNodeUDN.PROPERTY_LIBRARY);
	}

	/**
	 * Set the <I>DataQueueGetNodeUDN</I> "<I>type</I>" property
	 * 
	 * @param value String ; the value to set the property "<I>type</I>"
	 */
	public DataQueueGetNodeUDN setType(String value) {
		setProperty(DataQueueGetNodeUDN.PROPERTY_TYPE, value);
		return this;
	}

	/**
	 * Get the <I>DataQueueGetNodeUDN</I> "<I>type</I>" property
	 * 
	 * @return String; the value of the property "<I>type</I>"
	 */
	public String getType() {
		return (String)getPropertyValue(DataQueueGetNodeUDN.PROPERTY_TYPE);
	}

	/**
	 * Set the <I>DataQueueGetNodeUDN</I> "<I>keyed</I>" property
	 * 
	 * @param value boolean ; the value to set the property "<I>keyed</I>"
	 */
	public DataQueueGetNodeUDN setKeyed(boolean value) {
		setProperty(DataQueueGetNodeUDN.PROPERTY_KEYED, String.valueOf(value));
		return this;
	}

	/**
	 * Get the <I>DataQueueGetNodeUDN</I> "<I>keyed</I>" property
	 * 
	 * @return boolean; the value of the property "<I>keyed</I>"
	 */
	public boolean getKeyed(){
	if (getPropertyValue(DataQueueGetNodeUDN.PROPERTY_KEYED).equals("true")){
		return true;
	} else {
		return false;
		}
	}

	/**
	 * Set the <I>DataQueueGetNodeUDN</I> "<I>key</I>" property
	 * 
	 * @param value String ; the value to set the property "<I>key</I>"
	 */
	public DataQueueGetNodeUDN setKey(String value) {
		setProperty(DataQueueGetNodeUDN.PROPERTY_KEY, value);
		return this;
	}

	/**
	 * Get the <I>DataQueueGetNodeUDN</I> "<I>key</I>" property
	 * 
	 * @return String; the value of the property "<I>key</I>"
	 */
	public String getKey() {
		return (String)getPropertyValue(DataQueueGetNodeUDN.PROPERTY_KEY);
	}

	/**
	 * Set the <I>DataQueueGetNodeUDN</I> "<I>keylength</I>" property
	 * 
	 * @param value int ; the value to set the property "<I>keylength</I>"
	 */
	public DataQueueGetNodeUDN setKeylength(int value) {
		setProperty(DataQueueGetNodeUDN.PROPERTY_KEYLENGTH, Integer.toString(value));
		return this;
	}

	/**
	 * Get the <I>DataQueueGetNodeUDN</I> <I>keylength</I> property
	 * 
	 * @return int; the value of the property "<I>keylength</I>"
	 */
	public int getKeylength() {
		String value = (String)getPropertyValue(DataQueueGetNodeUDN.PROPERTY_KEYLENGTH);
		return Integer.valueOf(value).intValue();
	}

	/**
	 * Set the <I>DataQueueGetNodeUDN</I> "<I>searchType</I>" property
	 * 
	 * @param value ENUM_DATAQUEUEGET_SEARCHTYPE ; the value to set the property "<I>searchType</I>"
	 */
	public DataQueueGetNodeUDN setSearchType(ENUM_DATAQUEUEGET_SEARCHTYPE value) {
		setProperty(DataQueueGetNodeUDN.PROPERTY_SEARCHTYPE, value.toString());
		return this;
	}

	/**
	 * Get the <I>DataQueueGetNodeUDN</I> "<I>searchType</I>" property
	 * 
	 * @return ENUM_DATAQUEUEGET_SEARCHTYPE; the value of the property "<I>searchType</I>"
	 */
	public ENUM_DATAQUEUEGET_SEARCHTYPE getSearchType() {
		ENUM_DATAQUEUEGET_SEARCHTYPE value = ENUM_DATAQUEUEGET_SEARCHTYPE.getEnumFromString((String)getPropertyValue(DataQueueGetNodeUDN.PROPERTY_SEARCHTYPE));
		return value;
	}

	/**
	 * Set the <I>DataQueueGetNodeUDN</I> "<I>enable</I>" property
	 * 
	 * @param value boolean ; the value to set the property "<I>enable</I>"
	 */
	public DataQueueGetNodeUDN setEnable(boolean value) {
		setProperty(DataQueueGetNodeUDN.PROPERTY_ENABLE, String.valueOf(value));
		return this;
	}

	/**
	 * Get the <I>DataQueueGetNodeUDN</I> "<I>enable</I>" property
	 * 
	 * @return boolean; the value of the property "<I>enable</I>"
	 */
	public boolean getEnable(){
	if (getPropertyValue(DataQueueGetNodeUDN.PROPERTY_ENABLE).equals("true")){
		return true;
	} else {
		return false;
		}
	}

	/**
	 * Set the <I>DataQueueGetNodeUDN</I> "<I>directory</I>" property
	 * 
	 * @param value String ; the value to set the property "<I>directory</I>"
	 */
	public DataQueueGetNodeUDN setDirectory(String value) {
		setProperty(DataQueueGetNodeUDN.PROPERTY_DIRECTORY, value);
		return this;
	}

	/**
	 * Get the <I>DataQueueGetNodeUDN</I> "<I>directory</I>" property
	 * 
	 * @return String; the value of the property "<I>directory</I>"
	 */
	public String getDirectory() {
		return (String)getPropertyValue(DataQueueGetNodeUDN.PROPERTY_DIRECTORY);
	}

	public String getNodeName() {
		String retVal = super.getNodeName();
		if ((retVal==null) || retVal.equals(""))
			retVal = "DataQueueGet";
		return retVal;
	};
}
