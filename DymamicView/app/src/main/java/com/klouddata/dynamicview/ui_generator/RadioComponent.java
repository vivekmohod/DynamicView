package com.klouddata.dynamicview.ui_generator;

import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.klouddata.dymamicview.R;
import com.klouddata.dynamicview.activity.BaseActivity;
import com.klouddata.dynamicview.entitytypes_form.Field;
import com.klouddata.dynamicview.listener.CheckBoxChangeListener;


/**
 * Created by vivekm on 06-Nov-15.
 */
public class RadioComponent {

    private final LayoutInflater inflater;
    private final BaseActivity mAct;
    private final Field field;
    private RadioGroup radioGroup;
    private TextView secondary;
    private TextView primary;
    private String[] arrBtns;
    private CheckBoxChangeListener listener;

    public RadioComponent(LayoutInflater inflater, Field field, BaseActivity mAct, CheckBoxChangeListener listener) {
        this.inflater = inflater;
        this.mAct = mAct;
        this.field = field;
        this.listener = listener;
    }

    public View getRadioView() {

        ViewGroup mRadioGrpView = (ViewGroup) (inflater.inflate(R.layout.component_radio_group, null));
        radioGroup = (RadioGroup) mRadioGrpView.findViewById(R.id.rg_common_1);
        primary = (TextView) mRadioGrpView.findViewById(R.id.tv_radio_grp_label_primary);
        secondary = (TextView) mRadioGrpView.findViewById(R.id.tv_radio_grp_label_secondary);


        primary.setText(field.getPrimaryLabel());

        /*if (field.getSecounderyLabel().isEmpty())
            secondary.setVisibility(View.GONE);
        else {
            secondary.setText(field.getSecounderyLabel());
            secondary.setVisibility(View.VISIBLE);
        }*/
        secondary.setText(field.getSecounderyLabel());
        if (field.isVisibility())
            mRadioGrpView.setVisibility(View.VISIBLE);
        else
            mRadioGrpView.setVisibility(View.GONE);

        TextView mandatory = (TextView) mRadioGrpView.findViewById(R.id.tv_is_mandatory_camera);
        if (!field.isMandatory())
            mandatory.setVisibility(View.INVISIBLE);


        addRadioButtons(radioGroup);
        setSelectedVal(radioGroup);
        if (field.isFoundOnLoadData()) {
            for (int i = 0; i < radioGroup.getChildCount(); i++) {
                radioGroup.getChildAt(i).setEnabled(field.isOnLoadEnable());

            }
        }
        if (arrBtns.length > 2) {
            radioGroup.setOrientation(RadioGroup.VERTICAL);
        }
        return mRadioGrpView;
    }

    private void setSelectedVal(RadioGroup radioGroup) {
        int length = arrBtns.length;
        if (length > 0 && field.getDataObject() != null) {
            for (int i = 0; i < length; i++) {
                if (arrBtns[i].equalsIgnoreCase(field.getDataObject())) {
                    radioGroup.check(radioGroup.getChildAt(i).getId());
                }
            }
        }

    }

    private void addRadioButtons(ViewGroup mRadioGrpView) {

        arrBtns = field.getHint().split(",");
        int length = arrBtns.length;
        if (length > 0) {
            for (int i = 0; i < length; i++) {
                float density = mAct.getResources().getDisplayMetrics().density;
//                RadioButton rBtn = (RadioButton) inflater.inflate(R.layout.component_radio_button_layout, null).findViewById(R.id.rbtn_common);
                final RadioButton btn = new RadioButton(mAct);
                btn.setButtonDrawable(R.drawable.radio_button_bg);
               /* btn.setTextColor(mAct.getResources().getColor(R.color.black));
                btn.setTextSize(14 * density);*/

               // btn.setTypeface(null, Typeface.BOLD);
                int dp2 = (int) (2 * density);
                int dp4 = (int) (4 * density);

                btn.setPadding(dp2, dp2, dp2, dp2);
                if (arrBtns.length > 2) {
                    btn.setPadding(dp2, dp4, dp2, dp2);
                }else
                {
                    btn.setPadding(dp2, dp2, dp2, dp2);
                }


                btn.setId(i + 1);
                btn.setText("  " + arrBtns[i]);

                RadioGroup.LayoutParams params_rb = new RadioGroup.LayoutParams(RadioGroup.LayoutParams.WRAP_CONTENT, RadioGroup.LayoutParams.WRAP_CONTENT);
                params_rb.setMargins((int) (8 * density), 4, (int) (8 * density), 0);

                Typeface font = Typeface.createFromAsset(mAct.getAssets(), "RobotoSlab-Regular.ttf");
                btn.setTypeface(font);
                btn.setTag(i);

                btn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
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

                mRadioGrpView.addView(btn, params_rb);

            }
        }

    }
}
