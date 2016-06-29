package com.klouddata.dynamicview.entitytypes_form;

/**
 * Created by vivekm on 30-Jul-15.
 */
public enum FieldType {

    TEXTFIELD, RADIO_BUTTON, LABEL, USER_ID, CHECKBOX, DATE_PICKER, CAPTURE_IMAGE, SIGN, SIGN_USER, DROP_DOWN, AUTO_BTN,
    ROLE, SERIAL_NO,CAPTURE_VIDEO,IMAGE_VIEW,VIEW_GALLERY, BUTTON, TIME_PICKER;

    public static FieldType getFieldType(String str) throws Exception {
        FieldType ft = null;


        ft = str.equalsIgnoreCase("TEXTFIELD") ? TEXTFIELD : ft;
        ft = str.equalsIgnoreCase("RADIO_BUTTON") ? RADIO_BUTTON : ft;
        ft = str.equalsIgnoreCase("LABEL") ? LABEL : ft;
        ft = str.equalsIgnoreCase("checkbox") ? CHECKBOX : ft;
        ft = str.equalsIgnoreCase("user_id") ? USER_ID : ft;
        ft = str.equalsIgnoreCase("DATE_PICKER") ? DATE_PICKER : ft;
        ft = str.equalsIgnoreCase("TIME_PICKER") ? TIME_PICKER : ft;
        ft = str.equalsIgnoreCase("CAPTURE_IMAGE") ? CAPTURE_IMAGE : ft;
        ft = str.equalsIgnoreCase("SIGN") ? SIGN : ft;
        ft = str.equalsIgnoreCase("SIGN_USER") ? SIGN_USER : ft;
        ft = str.equalsIgnoreCase("DROP_DOWN") ? DROP_DOWN : ft;
        ft = str.equalsIgnoreCase("AUTO_BTN") ? AUTO_BTN : ft;
        ft = str.equalsIgnoreCase("ROLE") ? ROLE : ft;
        ft = str.equalsIgnoreCase("SERIAL_NO") ? SERIAL_NO : ft;
        ft = str.equalsIgnoreCase("CAPTURE_VIDEO") ? CAPTURE_VIDEO : ft;
        ft = str.equalsIgnoreCase("IMAGE_VIEW") ? IMAGE_VIEW : ft;
        ft = str.equalsIgnoreCase("VIEW_GALLERY") ? VIEW_GALLERY : ft;
        ft = str.equalsIgnoreCase("BUTTON") ? BUTTON : ft;
        if (ft != null)
            return ft;
        else
            throw new Exception("Unexpected FieldType " + str);

    }


}
