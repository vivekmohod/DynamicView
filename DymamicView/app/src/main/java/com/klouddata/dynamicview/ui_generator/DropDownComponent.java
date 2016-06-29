package com.klouddata.dynamicview.ui_generator;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.klouddata.dymamicview.R;
import com.klouddata.dynamicview.activity.BaseActivity;
import com.klouddata.dynamicview.adapter.ComponentACVAdapter;
import com.klouddata.dynamicview.entitytypes_form.Field;
import com.klouddata.dynamicview.listener.OnObservableChangeListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vivekm on 19-Nov-15.
 */
public class DropDownComponent implements AdapterView.OnItemSelectedListener {

    private final BaseActivity mAct;
    private final OnObservableChangeListener onObservableChangeListener;
    private final int rowId;

    private View v;
    private TextView primaryLabel;
    private TextView secoundaryLabel;
    private Spinner acv;
    private ComponentACVAdapter acvAdapter;
    private List<String> list;
    private Field field;
    private Context context;

    public DropDownComponent(LayoutInflater inflater, BaseActivity mAct, Field field, OnObservableChangeListener onObservableChangeListener, int rowId) {
        this.mAct = mAct;
        this.field = field;
        this.context = inflater.getContext();
        this.onObservableChangeListener = onObservableChangeListener;
        this.rowId = rowId;
        v = inflater.inflate(R.layout.component_drop_down, null);
        list = new ArrayList<>();
        initUI();

    }


    private void initUI() {


        primaryLabel = (TextView) v.findViewById(R.id.tv_primary_label_acv);
        secoundaryLabel = (TextView) v.findViewById(R.id.tv_secoundery_label_acv);
//        acv = (CustomAutoCompleteView) v.findViewById(R.id.acv_component);
        acv = (Spinner) v.findViewById(R.id.acv_component);
        primaryLabel.setText(field.getPrimaryLabel());
        acv.setOnItemSelectedListener(DropDownComponent.this);

        if (field.getSecounderyLabel() != null && field.getSecounderyLabel().length() > 0) {
            secoundaryLabel.setText(field.getSecounderyLabel());
        }

        //else secoundaryLabel.setVisibility(View.GONE);*/


        if (field.getHint() != null && field.getHint().length() > 0) {
            String[] arr = field.getHint().split(",");
            for (int i = 0; i < arr.length; i++) {
                list.add(arr[i]);
            }
        } else {
            acv.setEnabled(false);
        }

//        acvAdapter = new ComponentACVAdapter(context, R.id.list_item, list);

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, list);
        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        acv.setAdapter(dataAdapter);

        TextView mandatory = (TextView) v.findViewById(R.id.tv_is_mandatory_camera);
        if (!field.isMandatory())
            mandatory.setVisibility(View.INVISIBLE);

        if (field.getDataObject() != null && field.getDataObject().length() > 0) {
            acv.setSelection(dataAdapter.getPosition(field.getDataObject()));
        }

        if (!field.getDependentDesc().isEmpty() && !field.getDependentFields().isEmpty() && acv.getSelectedItem()!=null) {
            handleDependentField();
        }

    }

    private void handleDependentField() {

        if (acv.getSelectedItem().toString().equalsIgnoreCase(field.getDependentDesc())) {
            onObservableChangeListener.onDependentInitialized(field, secoundaryLabel, rowId, View.VISIBLE);
        } else {
            onObservableChangeListener.onDependentInitialized(field, secoundaryLabel, rowId, View.GONE);
        }


    }

    public View getDropDownView(Field field, Context context) {

        return v;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        field.setDataObject(list.get(position));
        if (acv.getSelectedItem().toString().equalsIgnoreCase(field.getDependentDesc())) {
            onObservableChangeListener.onDependentVisible(field, secoundaryLabel, rowId, View.VISIBLE);
        } else {
            onObservableChangeListener.onDependentVisible(field, secoundaryLabel, rowId, View.GONE);
        }
      /*  if (field.getDataObject().equalsIgnoreCase(" Select")) {
            secoundaryLabel.setVisibility(View.VISIBLE);
            secoundaryLabel.setText("Enter Number");
        } else   secoundaryLabel.setVisibility(View.GONE);*/
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
