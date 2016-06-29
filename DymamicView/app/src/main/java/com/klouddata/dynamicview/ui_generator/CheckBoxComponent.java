package com.klouddata.dynamicview.ui_generator;

import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.klouddata.dymamicview.R;
import com.klouddata.dynamicview.activity.BaseActivity;
import com.klouddata.dynamicview.entitytypes_form.Field;
import com.klouddata.dynamicview.listener.CheckBoxChangeListener;


/**
 * Created by vivekm on 06-Nov-15.
 */
public class CheckBoxComponent {

    private final LayoutInflater inflater;
    private final BaseActivity mAct;
    private final Field field;
    private RadioGroup radioGroup;
    private LinearLayout parentLayout;
    private TextView secondary;
    private TextView primary;
    private String[] arrBtns;
    private CheckBoxChangeListener listener;

    public CheckBoxComponent(LayoutInflater inflater, Field field, BaseActivity mAct, CheckBoxChangeListener listener) {
        this.inflater = inflater;
        this.mAct = mAct;
        this.field = field;
        this.listener = listener;
    }

    public View getCheckBoxView() {

        ViewGroup mCheckBoxGrpView = (ViewGroup) (inflater.inflate(R.layout.component_checkbox, null));
        parentLayout = (LinearLayout) mCheckBoxGrpView.findViewById(R.id.ll_parent);
        primary = (TextView) mCheckBoxGrpView.findViewById(R.id.tv_radio_grp_label_primary);
        secondary = (TextView) mCheckBoxGrpView.findViewById(R.id.tv_radio_grp_label_secondary);


        primary.setText(field.getPrimaryLabel());

       /* if (field.getSecounderyLabel().isEmpty())
            secondary.setVisibility(View.GONE);
        else {
            secondary.setText(field.getSecounderyLabel());
            secondary.setVisibility(View.VISIBLE);
        }*/

        secondary.setText(field.getSecounderyLabel());
        if (field.isVisibility())
            mCheckBoxGrpView.setVisibility(View.VISIBLE);
        else
             mCheckBoxGrpView.setVisibility(View.GONE);

        TextView mandatory = (TextView) mCheckBoxGrpView.findViewById(R.id.tv_is_mandatory_camera);
        if (!field.isMandatory())
            mandatory.setVisibility(View.INVISIBLE);


        addCheckBoxes(parentLayout);
        setSelectedVal(parentLayout);
        if (field.isFoundOnLoadData()) {
            for (int i = 0; i < radioGroup.getChildCount(); i++) {
                parentLayout.getChildAt(i).setEnabled(field.isOnLoadEnable());

            }
        }
        if (arrBtns.length > 2) {
            parentLayout.setOrientation(RadioGroup.VERTICAL);
        }

        return mCheckBoxGrpView;
    }

    private void setSelectedVal(LinearLayout parentLayout) {
        int length = arrBtns.length;
        if (length > 0 && field.getDataObject() != null) {
            for (int i = 0; i < length; i++) {
                if (arrBtns[i].equalsIgnoreCase(field.getDataObject())) {
                    CheckBox checkBox  = (CheckBox) parentLayout.getChildAt(i);
                    checkBox.setChecked(true);
                }
            }
        }
    }

    private void addCheckBoxes(LinearLayout parentLayout) {
        arrBtns = field.getHint().split(",");
        int length = arrBtns.length;
        if (length > 0) {
            for (int i = 0; i < length; i++) {
                float density = mAct.getResources().getDisplayMetrics().density;
                final CheckBox checkBox = new CheckBox(mAct);
                checkBox.setButtonDrawable(R.drawable.checkbox_button_bg);
               /* btn.setTextColor(mAct.getResources().getColor(R.color.black));
                btn.setTextSize(14 * density);*/
                // btn.setTypeface(null, Typeface.BOLD);
                int dp2 = (int) (2 * density);
                int dp4 = (int) (4 * density);

                checkBox.setPadding(dp2, dp2, dp2, dp2);
                if (arrBtns.length > 2) {
                    checkBox.setPadding(dp2, dp4, dp2, dp2);
                } else {
                    checkBox.setPadding(dp2, dp2, dp2, dp2);
                }


                checkBox.setId(i + 1);
                checkBox.setText("  " + arrBtns[i]);

                RadioGroup.LayoutParams params_rb = new RadioGroup.LayoutParams(RadioGroup.LayoutParams.WRAP_CONTENT, RadioGroup.LayoutParams.WRAP_CONTENT);
                params_rb.setMargins((int) (8 * density), 4, (int) (8 * density), 0);

                Typeface font = Typeface.createFromAsset(mAct.getAssets(), "RobotoSlab-Regular.ttf");
                checkBox.setTypeface(font);
                checkBox.setTag(i);

                checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            int i = (int) buttonView.getTag();
                            String s = arrBtns[i];
                            field.setDataObject(s);
                            listener.getSelectedValue(field, secondary);
                        }
                    }
                });

                parentLayout.addView(checkBox, params_rb);

            }
        }
    }


}
