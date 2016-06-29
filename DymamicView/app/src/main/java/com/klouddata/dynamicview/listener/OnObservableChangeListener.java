package com.klouddata.dynamicview.listener;


import android.view.View;

import com.klouddata.dynamicview.entitytypes_form.Field;

/**
 * Created by vivekm on 04-Apr-16.
 */
public interface OnObservableChangeListener {

    void onDependentVisible(Field field,View v, int rowId, int visibility);
//    void onDependentHide(Field field, int rowId);

    void onDependentInitialized(Field field, View v, int rowId, int visibility);
}
