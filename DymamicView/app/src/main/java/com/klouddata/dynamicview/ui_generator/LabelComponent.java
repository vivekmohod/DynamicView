package com.klouddata.dynamicview.ui_generator;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.klouddata.dymamicview.R;
import com.klouddata.dynamicview.activity.BaseActivity;
import com.klouddata.dynamicview.entitytypes_form.Field;

/**
 * Created by vivekm on 6/14/2016.
 */
public class LabelComponent {
    private BaseActivity mAct;
    private Field field;
    private Context context;
    private View v;
    private TextView tvLabel;

    public LabelComponent(LayoutInflater inflater, BaseActivity mAct, Field field) {
        this.mAct = mAct;
        this.field = field;
        this.context = inflater.getContext();
        v = inflater.inflate(R.layout.component_label, null);
        initUI();
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void initUI() {
        tvLabel = (TextView) v.findViewById(R.id.label);
        if (field.getDataObject() != null)
            tvLabel.setText(field.getDataObject());

    }

    public View getLabel() {
        return v;
    }

}
