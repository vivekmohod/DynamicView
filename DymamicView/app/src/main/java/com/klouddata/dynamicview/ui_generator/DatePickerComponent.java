package com.klouddata.dynamicview.ui_generator;

import android.app.DatePickerDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.klouddata.dymamicview.R;
import com.klouddata.dynamicview.activity.BaseActivity;
import com.klouddata.dynamicview.entitytypes_form.Field;
import com.klouddata.dynamicview.listener.DatePickerListener;

import java.util.Calendar;

/**
 * Created by vivekm on 6/16/2016.
 */
public class DatePickerComponent implements View.OnClickListener {
    private final LayoutInflater inflater;
    private final BaseActivity mAct;
    private final Field field;
    private Context context;
    private View view;
    private EditText etDatePicker;
    private int year, month, day;
    private Calendar calendar;
    private ImageView imgCalender;
    private TextView tvSecondaryLabel;
    private TextView tvPrimaryLabel;
    private TextView tvMandatoryLabel;
    private DatePickerListener listener;

    public DatePickerComponent(LayoutInflater inflater, BaseActivity mAct, Field field, DatePickerListener listener) {
        this.mAct = mAct;
        this.field = field;
        this.inflater = inflater;
        this.context = inflater.getContext();
        this.listener = listener;
        view = inflater.inflate(R.layout.component_date_picker, null);
        initUI();

    }

    private void initUI() {
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        etDatePicker = (EditText) view.findViewById(R.id.date_picker);
        tvPrimaryLabel = (TextView) view.findViewById(R.id.tv_primary_label);
        tvSecondaryLabel = (TextView) view.findViewById(R.id.tv_secoundery_label);
        tvMandatoryLabel = (TextView) view.findViewById(R.id.tv_is_mandatory);
        imgCalender = (ImageView) view.findViewById(R.id.calender);
        tvPrimaryLabel.setText(field.getPrimaryLabel());
        /*if (field.getSecounderyLabel() != null && field.getSecounderyLabel().length() > 0) {
            tvSecondaryLabel.setText(field.getSecounderyLabel());
        } else tvSecondaryLabel.setVisibility(View.GONE);*/
        tvSecondaryLabel.setText(field.getSecounderyLabel());
        if (!field.isMandatory())
            tvMandatoryLabel.setVisibility(View.INVISIBLE);

        imgCalender.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.calender:
                DatePickerDialog datePicker = new DatePickerDialog(mAct, myDateListener, year, month, day);
                datePicker.setCancelable(false);
                datePicker.setTitle("Select the date");
                datePicker.show();
                break;
        }
    }


    private DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
            // TODO Auto-generated method stub
            year = arg1;
            month = arg2;
            day = arg3;
            showDate(arg1, arg2 + 1, arg3);
        }
    };

    private void showDate(int year, int month, int day) {
        etDatePicker.setText(new StringBuilder().append(day).append("/")
                .append(month).append("/").append(year));
        field.setDataObject((new StringBuilder().append(day).append("/")
                .append(month).append("/").append(year)).toString());
        listener.getDate(field);
    }



    public View getView() {
        return view;
    }

}
