package com.klouddata.dynamicview.ui_generator;

import android.view.LayoutInflater;
import android.view.View;

import com.klouddata.dynamicview.activity.BaseActivity;
import com.klouddata.dynamicview.entitytypes_form.Field;
import com.klouddata.dynamicview.listener.ButtonClickListener;
import com.klouddata.dynamicview.listener.CheckBoxChangeListener;
import com.klouddata.dynamicview.listener.DatePickerListener;
import com.klouddata.dynamicview.listener.EditTextChangeListener;
import com.klouddata.dynamicview.listener.OnObservableChangeListener;
import com.klouddata.dynamicview.listener.TimePickerListener;


/**
 * Created by vivekm on 19-Aug-15.
 */
public class UIBuilder {

    public static View getDropDownView(LayoutInflater inflater, BaseActivity activity, Field field, OnObservableChangeListener onChangeListener, int rowId) {

        DropDownComponent component = new DropDownComponent(inflater, activity, field, onChangeListener, rowId);
        return component.getDropDownView(field, activity);
    }

    public static View getRadio(final Field field, final LayoutInflater inflater, BaseActivity mAct, CheckBoxChangeListener listener) {

        RadioComponent radio = new RadioComponent(inflater, field, mAct, listener);
        View view = radio.getRadioView();

        /// CustomLogger.print("Radio Width : " + view.getWidth());
        return view;
    }

    public static View getCheckBox(final Field field, final LayoutInflater inflater, BaseActivity mAct, CheckBoxChangeListener listener) {

        CheckBoxComponent checkBoxComponent = new CheckBoxComponent(inflater, field, mAct, listener);
        View view = checkBoxComponent.getCheckBoxView();

        /// CustomLogger.print("Radio Width : " + view.getWidth());
        return view;
    }

    public static View getLabel(final Field field, final LayoutInflater inflater, BaseActivity mAct) {

        LabelComponent labelComponent = new LabelComponent(inflater, mAct, field);
        View view = labelComponent.getLabel();


        /// CustomLogger.print("Radio Width : " + view.getWidth());
        return view;
    }

    public static View getButton(final Field field, final LayoutInflater inflater, BaseActivity mAct, ButtonClickListener listener) {

        ButtonComponent buttonComponent = new ButtonComponent(inflater, mAct, field, listener);
        View view = buttonComponent.getButton();


        /// CustomLogger.print("Radio Width : " + view.getWidth());
        return view;
    }

    public static View getEditText(final Field field, final LayoutInflater inflater, BaseActivity mAct, EditTextChangeListener listener) {

        EditTextComponent textComponent = new EditTextComponent(inflater, mAct, field, listener);
        View view = textComponent.getEditText();


        /// CustomLogger.print("Radio Width : " + view.getWidth());
        return view;
    }


    public static View getDatePicker(final LayoutInflater inflater, BaseActivity mAct, final Field field, DatePickerListener listener) {

        DatePickerComponent datePickerComponent = new DatePickerComponent(inflater, mAct, field, listener);
        View view = datePickerComponent.getView();
        return view;
    }


    public static View getTimePicker(final LayoutInflater inflater, BaseActivity mAct, final Field field, TimePickerListener listener) {

        TimePickerComponent timePickerComponent = new TimePickerComponent(inflater, mAct, field, listener);
        View view = timePickerComponent.getView();
        return view;
    }

}
