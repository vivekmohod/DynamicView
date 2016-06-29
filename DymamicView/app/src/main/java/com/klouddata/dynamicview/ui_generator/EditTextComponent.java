package com.klouddata.dynamicview.ui_generator;

import android.content.Context;
import android.graphics.Typeface;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.klouddata.dymamicview.R;
import com.klouddata.dynamicview.activity.BaseActivity;
import com.klouddata.dynamicview.entitytypes_form.DataType;
import com.klouddata.dynamicview.entitytypes_form.Field;
import com.klouddata.dynamicview.listener.EditTextChangeListener;

/**
 * Created by vivekm on 6/14/2016.
 */
public class EditTextComponent implements TextWatcher {
    private BaseActivity mAct;
    private Field field;
    private Context context;
    private View v;
    private EditText editText;
    private EditTextChangeListener listener;
    private TextView tvMandatoryLabel;
    private TextView tvPrimaryLabel;
    private TextView tvValidationMessage;

    public EditTextComponent(LayoutInflater inflater, BaseActivity mAct, Field field, EditTextChangeListener listener) {
        this.mAct = mAct;
        this.field = field;
        this.listener = listener;
        this.context = inflater.getContext();
        v = inflater.inflate(R.layout.component_edit_text, null);
        initUI();
    }

    private void initUI() {
        editText = (EditText) v.findViewById(R.id.textfield);
        tvMandatoryLabel = (TextView) v.findViewById(R.id.tv_is_mandatory);
        tvPrimaryLabel = (TextView) v.findViewById(R.id.tv_primary_label);
        tvValidationMessage = (TextView) v.findViewById(R.id.tv_secoundery_label);
        if (!field.isMandatory())
            tvMandatoryLabel.setVisibility(View.GONE);
        else
            tvMandatoryLabel.setVisibility(View.GONE);
        if (field.getDataObject() != null)
            editText.setText(field.getDataObject());
        if (field.getPrimaryLabel() != null) {
            tvPrimaryLabel.setVisibility(View.VISIBLE);
            tvPrimaryLabel.setText(field.getPrimaryLabel());
        } else tvPrimaryLabel.setVisibility(View.GONE);

      /*  if (field.getSecounderyLabel() != null) {
            tvValidationMessage.setVisibility(View.VISIBLE);
            tvValidationMessage.setText(field.getSecounderyLabel());
        } else tvValidationMessage.setVisibility(View.GONE);*/
        tvValidationMessage.setText(field.getSecounderyLabel());
        editText.addTextChangedListener(this);
        setInputType(editText, field.getDataType());
        if (field.getLines() > 1) {
            editText.setLines(field.getLines());
            editText.setSingleLine(false);
        } else editText.setSingleLine(true);

        Typeface myTypeface = Typeface.createFromAsset(mAct.getAssets(), "fonts/DroidSansFallback.ttf");
        editText.setTypeface(myTypeface);
    }

    private static void setInputType(EditText et, DataType dataType) {
        switch (dataType) {
            case INT:
                et.setInputType(InputType.TYPE_CLASS_NUMBER);
                break;
            case STRING:
                et.setInputType(InputType.TYPE_CLASS_TEXT);
                break;
            case DECIMAL:
                et.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                break;
        }

    }

    public View getEditText() {
        return v;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if (String.valueOf(charSequence).length() > 0)
            tvValidationMessage.setVisibility(View.GONE);
        field.setDataObject(String.valueOf(charSequence));
        listener.getEnteredText(field, tvValidationMessage);
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
}
