package com.klouddata.dynamicview.ui_generator;

import android.app.TimePickerDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.klouddata.dymamicview.R;
import com.klouddata.dynamicview.activity.BaseActivity;
import com.klouddata.dynamicview.entitytypes_form.Field;
import com.klouddata.dynamicview.listener.TimePickerListener;

import java.util.Calendar;

/**
 * Created by vivekm on 6/16/2016.
 */
public class TimePickerComponent implements View.OnClickListener {
    private final LayoutInflater inflater;
    private final BaseActivity mAct;
    private final Field field;
    private Context context;
    private View view;
    private EditText etTimePicker;
    private int hour, minute;
    private Calendar calendar;
    private ImageView imgTime;
    private TextView tvSecondaryLabel;
    private TextView tvPrimaryLabel;
    private TextView tvMandatoryLabel;
    private TimePickerListener listener;

    public TimePickerComponent(LayoutInflater inflater, BaseActivity mAct, Field field, TimePickerListener listener) {
        this.mAct = mAct;
        this.field = field;
        this.inflater = inflater;
        this.context = inflater.getContext();
        this.listener = listener;
        view = inflater.inflate(R.layout.component_time_picker, null);
        initUI();

    }

    private void initUI() {
        calendar = Calendar.getInstance();
        hour = calendar.get(Calendar.HOUR);
        minute = calendar.get(Calendar.MINUTE);
        etTimePicker = (EditText) view.findViewById(R.id.time_picker);
        tvPrimaryLabel = (TextView) view.findViewById(R.id.tv_primary_label);
        tvSecondaryLabel = (TextView) view.findViewById(R.id.tv_secoundery_label);
        tvMandatoryLabel = (TextView) view.findViewById(R.id.tv_is_mandatory);
        imgTime = (ImageView) view.findViewById(R.id.time_image);
        tvPrimaryLabel.setText(field.getPrimaryLabel());
        /*if (field.getSecounderyLabel() != null && field.getSecounderyLabel().length() > 0) {
            tvSecondaryLabel.setText(field.getSecounderyLabel());
        } else tvSecondaryLabel.setVisibility(View.GONE);*/
        tvSecondaryLabel.setText(field.getSecounderyLabel());
        if (!field.isMandatory())
            tvMandatoryLabel.setVisibility(View.INVISIBLE);

        imgTime.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.time_image:
                new TimePickerDialog(context, timeListener, hour, minute, false).show();
                break;
        }
    }


  private TimePickerDialog.OnTimeSetListener timeListener = new TimePickerDialog.OnTimeSetListener() {

      @Override
      public void onTimeSet(TimePicker timePicker, int i, int i1) {
            hour = i;
            minute = i1;
            showTime(hour, minute);
      }
  };


    private void showTime(int hour, int minute) {
        etTimePicker.setText(new StringBuilder().append(hour).append(":")
                .append(minute));
        field.setDataObject((new StringBuilder().append(hour).append(":")
                .append(minute)).toString());
        listener.getTime(field);
    }



    public View getView() {
        return view;
    }

}
