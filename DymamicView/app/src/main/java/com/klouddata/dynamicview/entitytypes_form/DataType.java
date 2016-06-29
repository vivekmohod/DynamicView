package com.klouddata.dynamicview.entitytypes_form;

/**
 * Created by vivekm on 30-Jul-15.
 */
public enum DataType {

    INT, STRING, DATE, BITMAP,DECIMAL;

    public static DataType getDataType(String str) throws Exception {
        DataType dt = null;

        dt = str.equalsIgnoreCase("string")?STRING:dt;
        dt = str.equalsIgnoreCase("INT")?INT:dt;
        dt = str.equalsIgnoreCase("DATE")?DATE:dt;
        dt = str.equalsIgnoreCase("BITMAP")?BITMAP:dt;
        dt = str.equalsIgnoreCase("DECIMAL")?DECIMAL:dt;

        if(dt==null)
            throw new Exception("Data type mismatch. "+str);

        return dt;

    }
}
