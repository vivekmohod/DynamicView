package com.klouddata.dynamicview.entitytypes_form;

/**
 * Created by vivekm on 30-Jul-15.
 */
public class Field implements Comparable {

    private FieldType fieldType;
    //    private Attributes attributes;
    private String name;
    private DataType dataType;
    private String text;
    private String hint;
    private String primaryLabel;
    private String secounderyLabel;
    private String dataObject;
    private int sectionDisplayOrder;
    private int screenDisplayOrder;
    private boolean isOutput;
    private boolean isMandatory;
    private boolean isFoundOnLoadData;
    private boolean visibility;

    private String dependentDesc;
    private String dependentFields;
    private boolean hideDependents;
    private boolean onLoadEnable;
    private int backgroundDrawable;
    private int lines;
    private String imageUrl;

    public Field() {
    }


    public Field(Field f) throws Exception {
        this.name = getNewStringValue(f.getName());
        this.hint = getNewStringValue(f.getHint());
        this.primaryLabel = getNewStringValue(f.getPrimaryLabel());
        this.text = getNewStringValue(f.getText());
        this.secounderyLabel = getNewStringValue(f.getSecounderyLabel());
        this.dataObject = getNewStringValue(f.getDataObject());
        this.dataType = DataType.getDataType(f.dataType.toString());
        this.fieldType = FieldType.getFieldType(f.getFieldType().toString());
        this.screenDisplayOrder = Integer.valueOf(f.getScreenDisplayOrder());
        this.sectionDisplayOrder = new Integer(f.getSectionDisplayOrder());
        this.visibility = new Boolean(f.isVisibility());
        this.isOutput = new Boolean(f.isOutput());
        this.isMandatory = new Boolean(f.isMandatory());
        this.isFoundOnLoadData = new Boolean(f.isFoundOnLoadData());


        this.dependentDesc = getNewStringValue(f.getDependentDesc());
        this.dependentFields = getNewStringValue(f.getDependentFields());
        this.hideDependents = new Boolean(f.isHideDependents());
        this.onLoadEnable = new Boolean(f.isOnLoadEnable());

    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getLines() {
        return lines;
    }

    public void setLines(int lines) {
        this.lines = lines;
    }

    private String getNewStringValue(String val) {
        return val != null ? new String(val) : new String("");
    }

    public int getScreenDisplayOrder() {
        return screenDisplayOrder;
    }

    public void setScreenDisplayOrder(int screenDisplayOrder) {
        this.screenDisplayOrder = screenDisplayOrder;
    }

    public boolean isVisibility() {
        return visibility;
    }

    public void setVisibility(boolean visibility) {
        this.visibility = visibility;

    }


//    public Attributes getAttributes() {
//        return attributes;
//    }
//
//    public void setAttributes(Attributes attributes) {
//        this.attributes = attributes;
//    }

    public FieldType getFieldType() {
        return fieldType;
    }

    public void setFieldType(FieldType type) {
        this.fieldType = type;
    }

    public String getDataObject() {
        return dataObject;
    }

    public void setDataObject(String dataObject) {
        this.dataObject = dataObject;
    }

    public String getSecounderyLabel() {
        return secounderyLabel;
    }

    public void setSecounderyLabel(String secounderyLabel) {
        this.secounderyLabel = secounderyLabel;
    }

    public String getPrimaryLabel() {
        return primaryLabel;
    }

    public void setPrimaryLabel(String primaryLabel) {
        this.primaryLabel = primaryLabel;
    }

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public DataType getDataType() {
        return dataType;
    }

    public void setDataType(DataType dataType) {
        this.dataType = dataType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSectionDisplayOrder() {
        return sectionDisplayOrder;
    }

    public void setSectionDisplayOrder(int sectionDisplayOrder) {
        this.sectionDisplayOrder = sectionDisplayOrder;
    }

    public boolean isOutput() {
        return isOutput;
    }

    public void setIsOutput(boolean is_output) {
        this.isOutput = isOutput;
    }

    public boolean isMandatory() {
        return isMandatory;
    }

    public void setIsMandatory(boolean is_output) {
        this.isMandatory = is_output;
    }

    public boolean isHideDependents() {
        return hideDependents;
    }

    public void setHideDependents(boolean hideDependents) {
        this.hideDependents = hideDependents;
    }

    public String getDependentDesc() {
        return dependentDesc;
    }

    public void setDependentDesc(String dependentDesc) {
        this.dependentDesc = dependentDesc;
    }

    public String getDependentFields() {
        return dependentFields;
    }

    public void setDependentFields(String dependentFields) {
        this.dependentFields = dependentFields;
    }

    @Override
    public int compareTo(Object another) {

        return this.sectionDisplayOrder - ((Field) another).getSectionDisplayOrder();
    }

    public boolean isFoundOnLoadData() {
        return isFoundOnLoadData;
    }

    public void setFoundOnLoadData(boolean is_FoundOnLoadData) {
        this.isFoundOnLoadData = is_FoundOnLoadData;
    }


    public boolean isOnLoadEnable() {
        return onLoadEnable;
    }

    public void setOnLoadEnable(boolean onLoadEnable) {
        this.onLoadEnable = onLoadEnable;
    }

    public int getBackgroundDrawable() {
        return backgroundDrawable;
    }

    public void setBackgroundDrawable(int backgroundDrawable) {
        this.backgroundDrawable = backgroundDrawable;
    }
}