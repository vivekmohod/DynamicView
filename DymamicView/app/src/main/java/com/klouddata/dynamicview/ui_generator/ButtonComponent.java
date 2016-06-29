package com.klouddata.dynamicview.ui_generator;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.klouddata.dymamicview.R;
import com.klouddata.dynamicview.activity.BaseActivity;
import com.klouddata.dynamicview.entitytypes_form.Field;
import com.klouddata.dynamicview.listener.ButtonClickListener;

/**
 * Created by vivekm on 6/14/2016.
 */
public class ButtonComponent implements View.OnClickListener {
    private BaseActivity mAct;
    private Field field;
    private Context context;
    private View v;
    private Button button;
    private ButtonClickListener listener;

    public ButtonComponent(LayoutInflater inflater, BaseActivity mAct, Field field, ButtonClickListener listener) {
        this.mAct = mAct;
        this.field = field;
        this.listener = listener;
        this.context = inflater.getContext();
        v = inflater.inflate(R.layout.component_button, null);
        initUI();
    }

    private void initUI() {
        button = (Button) v.findViewById(R.id.button);
        if (field.getDataObject() != null)
            button.setText(field.getDataObject());
        button.setOnClickListener(this);
        if(field.isVisibility())
            button.setVisibility(View.VISIBLE);
        else
            button.setVisibility(View.GONE);
    }


    public View getButton() {
        return v;
    }

    @Override
    public void onClick(View v) {
        listener.onClick(v);
    }
}
