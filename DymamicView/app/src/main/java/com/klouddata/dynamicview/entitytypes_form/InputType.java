package com.klouddata.dynamicview.entitytypes_form;

/**
 * Created by vivekm on 6/15/2016.
 */
public enum InputType {

    INT, STRING, DATE, BITMAP,DECIMAL;

    public static InputType getDataType(String str) throws Exception {
        InputType dt = null;

        dt = str.equalsIgnoreCase("STRING")?STRING:dt;
        dt = str.equalsIgnoreCase("INT")?INT:dt;
        dt = str.equalsIgnoreCase("DATE")?DATE:dt;
        dt = str.equalsIgnoreCase("BITMAP")?BITMAP:dt;
        dt = str.equalsIgnoreCase("DECIMAL")?DECIMAL:dt;

        if(dt==null)
            throw new Exception("Data type mismatch. "+str);

        return dt;

    }
}
