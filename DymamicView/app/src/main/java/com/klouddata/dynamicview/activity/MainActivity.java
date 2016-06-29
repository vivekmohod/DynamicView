package com.klouddata.dynamicview.activity;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.klouddata.dymamicview.R;
import com.klouddata.dynamicview.entitytypes_data.ProductDetails;
import com.klouddata.dynamicview.entitytypes_form.DataType;
import com.klouddata.dynamicview.entitytypes_form.Field;
import com.klouddata.dynamicview.entitytypes_form.FieldType;
import com.klouddata.dynamicview.listener.ButtonClickListener;
import com.klouddata.dynamicview.listener.CheckBoxChangeListener;
import com.klouddata.dynamicview.listener.DatePickerListener;
import com.klouddata.dynamicview.listener.EditTextChangeListener;
import com.klouddata.dynamicview.listener.OnObservableChangeListener;
import com.klouddata.dynamicview.listener.TimePickerListener;
import com.klouddata.dynamicview.ui_generator.LoadableImageView;
import com.klouddata.dynamicview.ui_generator.UIBuilder;

import org.apache.olingo.client.api.communication.request.retrieve.ODataEntitySetRequest;
import org.apache.olingo.client.api.communication.response.ODataRetrieveResponse;
import org.apache.olingo.client.api.v4.ODataClient;
import org.apache.olingo.client.core.ODataClientFactory;
import org.apache.olingo.commons.api.domain.v4.ODataEntity;
import org.apache.olingo.commons.api.domain.v4.ODataEntitySet;
import org.apache.olingo.commons.api.format.ODataFormat;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static android.widget.Toast.makeText;


public class MainActivity extends BaseActivity {
    private OnObservableChangeListener listener;
    private LinearLayout layout;
    private LayoutInflater inflater;
    private ScrollView scrollView;
    private Context context;
    private LoadableImageView bannerLoadableImageView;
    private View editView;
    private EditText editText;
    private TextView tvPrimaryLabel;
    private TextView tvSecondaryLabel;
    private TextView dropDownSecondaryLabel;
    private TextView checkboxSecondaryLabel;
    private TextView radioGroupSecondaryLabel;
    private TextView datePickerSecondaryLabel;
    private TextView timePickerSecondaryLabel;
    private String dropDownSelectedString;
    private String checkboxSelectedString;
    private String radioButtonSelectedString;
    private String datePickerEnteredString;
    private String timePickerEnteredString;
    String serviceUri;

    ODataClient odata;
    private ProductDetails[] productDetails;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
       // lgCtx =  mApp.getLgCore().getLogonContext();
        scrollView = new ScrollView(this);
        scrollView.setLayoutParams(new ScrollView.LayoutParams(ScrollView.LayoutParams.MATCH_PARENT,
                ScrollView.LayoutParams.MATCH_PARENT));
        layout = new LinearLayout(this);
        layout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));
        layout.setOrientation(LinearLayout.VERTICAL);
        scrollView.addView(layout);
        inflater = (LayoutInflater)this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        new CallService().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
       // getServiceDocument();
        getFieldJSON();
        setContentView(scrollView);

    }

   /* @Override
    public void onError(IRequest arg0, IResponse res, IRequestStateElement arg2) {
        //Get String representation of the response
        String respBody = null;
        try {
            respBody = EntityUtils.toString(res.getEntity(), "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Get status code of the response
        int code = res.getStatusLine().getStatusCode();
        //Get a list of error messages from the IODataError object
        IODataError dataError = null;
        try {
            dataError = mApp.getParser().parseODataError(respBody);
        } catch (ParserException e) {
            e.printStackTrace();
        }
        List<String> mMessages = dataError.getValues(IODataError.ELEMENT_MESSAGE);

    }

    @Override
    public void onSuccess(IRequest req, IResponse res) {
        if (req.getRequestTAG().equalsIgnoreCase("Service_Doc")) {
            *//* Parses the service document *//*
            try {
                ioDataServiceDocument = mApp.getParser().parseODataServiceDocument(EntityUtils.toString(res.getEntity()));
                getMetaDataDocument();

            } catch (ParserException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (req.getRequestTAG().equalsIgnoreCase("Meta_Data_Doc")) {
            *//* Parses the service document *//*
            try {
                IODataSchema schema = mApp.getParser().parseODataSchema(EntityUtils.toString(res.getEntity()), ioDataServiceDocument);
            } catch (ParserException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
*/
    public void getData(String type)
    {
        // maximum number of top N items we want to display
        int sz= 20;

        serviceUri = "http://services.odata.org/OData/OData.svc/";

        //instanciate client object of specific version through factory class
        odata = ODataClientFactory.getV4();


        //set request data format as JSON


        odata.getConfiguration().setDefaultPubFormat(ODataFormat.APPLICATION_JSON);


        //use the helper function defined below
        final List<ODataEntity> entities = readEntities(odata,serviceUri,type);

       /* if(entities.size() < sz)
            sz =entities.size();*/

        productDetails =  new ProductDetails[entities.size()];

        for( int i=0 ; i < entities.size(); i++ )
        {
            ODataEntity alert = entities.get(i);
            productDetails[i] = new ProductDetails();
            productDetails[i].name = alert.getProperty("name").getValue().toString();
            productDetails[i].type = alert.getProperty("type").getValue().toString();
            productDetails[i].nullable =  Boolean.valueOf(alert.getProperty("nullable").getValue().toString());
          /*  alerts[i].locationName = alert.getProperty("LocationName").getValue().toString();
            alerts[i].alertType = alert.getProperty("AlertType").getValue().toString();
            alerts[i].alertSubType = alert.getProperty("AlertSubType").getValue().toString();
            alerts[i].payload = alert.getProperty("Payload").getValue().toString();*/
        }
    }

    List<ODataEntity> readEntities(ODataClient odata, String uri, String esetname) {

    //create entityset request with URI and entitySet Name using request factory

        final ODataEntitySetRequest<ODataEntitySet> request =
                odata.getRetrieveRequestFactory()
                        .getEntitySetRequest(
                                odata.newURIBuilder(uri).appendEntitySetSegment(esetname).build()
                        );
    //execute the request , get response

        final ODataRetrieveResponse<ODataEntitySet> response = request.execute();

    //get entityset and all entities for the entityset

        final ODataEntitySet entitySet = response.getBody();

        List<ODataEntity> entities = entitySet.getEntities();

        return entities;

    }


    public class CallService extends AsyncTask<Void, Void, Void> {

        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            // TODO Auto-generated method stub
          /*  ArrayList<String> listUI = new ArrayList<String>();
            try {
                ODataConsumer c = ODataConsumer.create("http://services.odata.org/OData/OData.svc/");
               // Enumeration<OEntity> enumeration = (Enumeration<OEntity>)((OQueryRequest<OEntity>) c.getEntities("Products"));

                Iterator<OEntity> listEntities = c.getEntities("Products").iterator();
              //  System.out.println("Size : " + listEntities.size());
               *//* if (listEntities.size() > 0) {
                    for (OEntity entity : listEntities) {*//*
                while(listEntities.hasNext()) {
                    OEntity entity = listEntities.next();
                    listUI.add(entity.getProperty("ID").getValue()
                            + " - "
                            + entity.getProperty("Name").getValue().toString()
                            + " - "
                            + entity.getProperty("Description").getValue().toString());
                }
                  //  }
               // }
            } catch(Exception e) {
                Toast.makeText(context, "Exception : " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
            return listUI;*/
            getData("ProductDetails");
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);

        }
    }


  /*  private void getServiceDocument() {
        IRequest request = new BaseRequest();
        request.setListener(this);
        request.setPriority(IRequest.PRIORITY_HIGH);
        request.setRequestTAG("Service_Doc");
        request.setRequestMethod(IRequest.REQUEST_METHOD_GET);
        String endPointURL = "http://services.odata.org/OData/OData.svc/" ;
       /// String endPointURL = "https://sapes1.sapdevcenter.com/sap/opu/odata/IWFND/RMTSAMPLEFLIGHT/" ;
      // String endPointURL = null;
        try {
            endPointURL = mApp.getmAppSettings().getApplicationEndPoint();
        } catch (SMPException e) {
            e.printStackTrace();
        }
        request.setRequestUrl(endPointURL);
        ////Set Application Connection ID Header
        Map<String, String> headers = new HashMap<String, String>();
        try {
            headers.put("X-SMP-APPCID", lgCtx.getConnId());
        } catch (LogonCoreException e) {
            e.printStackTrace();
        }
        headers.put("Accept", "application/json");
        request.setHeaders(headers);
        ////Make the Request
        mApp.getRequestManager().makeRequest(request);

    }*/


    /*private void getMetaDataDocument() {
        //Create a GET request
        IRequest request = new BaseRequest();
        request.setListener(this);
        request.setPriority(IRequest.PRIORITY_HIGH);
        request.setRequestTAG("Meta_Data_Doc");
        request.setRequestMethod(IRequest.REQUEST_METHOD_GET);
       // String endPointURL = "http://services.odata.org/OData/OData.svc/$metadata";
        String endPointURL = "https://sapes1.sapdevcenter.com/sap/opu/odata/IWFND/RMTSAMPLEFLIGHT/" ;
        try {
            endPointURL = mApp.getmAppSettings().getApplicationEndPoint() + "/$metadata";
        } catch (SMPException e) {
            e.printStackTrace();
        }
        request.setRequestUrl(endPointURL);
    //Set Application Connection ID Header
        Map<String, String> headers = new HashMap<String, String>();
        try {
            headers.put("X-SMP-APPCID", lgCtx.getConnId());
        } catch (LogonCoreException e) {
            e.printStackTrace();
        }
        headers.put("Accept", "application/json");
        request.setHeaders(headers);
        //Make the Request
        mApp.getRequestManager().makeRequest(request);
    }*/


    private void addDatePicker(Field field) {
        View view = null;
        DatePickerListener listener = new DatePickerListener() {
            @Override
            public void getDate(Field field) {
                Toast.makeText(context, "Date :" + field.getDataObject(), Toast.LENGTH_SHORT).show();
                datePickerEnteredString = field.getDataObject();
            }
        };
        view = UIBuilder.getDatePicker(inflater, this, field, listener);
        datePickerSecondaryLabel = (TextView) view.findViewById(R.id.tv_secoundery_label);
        layout.addView(view);
    }

    private void addTimePicker(Field field) {
        View view = null;
        TimePickerListener listener = new TimePickerListener() {
            @Override
            public void getTime(Field field) {
                Toast.makeText(context, "Time :" + field.getDataObject(), Toast.LENGTH_SHORT).show();
                timePickerEnteredString = field.getDataObject();
            }
        };
        view = UIBuilder.getTimePicker(inflater, this, field, listener);
        timePickerSecondaryLabel = (TextView) view.findViewById(R.id.tv_secoundery_label);
        layout.addView(view);
    }

    private void downloadImage(Field field) {
        bannerLoadableImageView = new LoadableImageView(context);
        bannerLoadableImageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 120));
        bannerLoadableImageView.loadImageFromURL(
                field.getImageUrl(), 0, false, false);
        layout.addView(bannerLoadableImageView);
    }

    private void addEditTextToLayout(Field field) {
        EditTextChangeListener listener = new EditTextChangeListener() {
            @Override
            public void getEnteredText(Field field1, View view) {
                Toast.makeText(context, ""+field1.getDataObject(), Toast.LENGTH_SHORT).show();
            }
        };
        View view = null;
        editView = UIBuilder.getEditText(field, inflater, this, listener);
        editText = (EditText) editView.findViewById(R.id.textfield);
        tvPrimaryLabel =  (TextView) editView.findViewById(R.id.tv_primary_label);
        tvSecondaryLabel =  (TextView) editView.findViewById(R.id.tv_secoundery_label);
        layout.addView(editView);
    }

    private void addButtonToLayout(Field field) {
        ButtonClickListener listener = new ButtonClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getBaseContext(), "Button is clicked", Toast.LENGTH_SHORT).show();
                tvSecondaryLabel.setVisibility(View.VISIBLE);
                if (TextUtils.isEmpty(dropDownSelectedString))
                    dropDownSecondaryLabel.setVisibility(View.VISIBLE);

                if (TextUtils.isEmpty(checkboxSelectedString))
                    checkboxSecondaryLabel.setVisibility(View.VISIBLE);

                if (TextUtils.isEmpty(radioButtonSelectedString))
                    radioGroupSecondaryLabel.setVisibility(View.VISIBLE);

                if(TextUtils.isEmpty(datePickerEnteredString))
                    datePickerSecondaryLabel.setVisibility(View.VISIBLE);

                if(TextUtils.isEmpty(timePickerEnteredString))
                    timePickerSecondaryLabel.setVisibility(View.VISIBLE);
            }
        };

        View view = null;
        view = UIBuilder.getButton(field, inflater, this, listener);
        layout.addView(view);
    }

    private void addLabelToLayout() {
        Field field = getFieldJSON();
        View view = null;
        view = UIBuilder.getLabel(field, inflater, this);
        layout.addView(view);
    }

    private void addCheckBoxToLayout(Field field) {
        CheckBoxChangeListener listener = new CheckBoxChangeListener() {
            @Override
            public void getSelectedValue(Field field, View v) {
               Toast.makeText(getBaseContext(), "value : " + field.getDataObject(), Toast.LENGTH_SHORT).show();
                checkboxSelectedString = field.getDataObject();
            }
        };
        View view = null;
        view = UIBuilder.getCheckBox(field, inflater, this, listener);
        checkboxSecondaryLabel = (TextView) view.findViewById(R.id.tv_radio_grp_label_secondary);
        layout.addView(view);
    }

    private void addRadioButtonToLayout(Field field) {
        CheckBoxChangeListener listener = new CheckBoxChangeListener() {
            @Override
            public void getSelectedValue(Field field, View v) {
                Toast.makeText(getBaseContext(), "value : " + field.getDataObject(), Toast.LENGTH_SHORT).show();
                radioButtonSelectedString = field.getDataObject();
            }
        };
        View view = null;
        view = UIBuilder.getRadio(field, inflater, this, listener);
        radioGroupSecondaryLabel = (TextView) view.findViewById(R.id.tv_radio_grp_label_secondary);
        layout.addView(view);
    }

    private Field getFieldJSON() {
        Field field = new Field();
        try {
            JSONObject obj = new JSONObject(loadJSONFromAsset());
            JSONArray jsonArray = obj.getJSONArray("view");

            for(int i =0; i < jsonArray.length(); i++) {

                JSONObject object = jsonArray.getJSONObject(i);

                field.setFieldType(FieldType.getFieldType(object.getString("fieldType")));
                field.setDataType(DataType.getDataType(object.getString("dataType")));
                field.setPrimaryLabel(object.getString("primaryLabel"));
                field.setSecounderyLabel(object.getString("secondaryLabel"));
                field.setIsMandatory(object.getBoolean("isMandatory"));
                //field.setDropDownSelectedItemPosition(object.getInt("dropDownSelectedItemPosition"));
                field.setHint(object.getString("hint"));
                field.setDependentDesc(object.getString("dependentDesc"));
                field.setDependentFields(object.getString("dependentFields"));
                field.setDataObject(object.getString("dataObject"));
                field.setVisibility(object.getBoolean("visibility"));
                field.setLines(object.getInt("lines"));
                field.setImageUrl(object.getString("imageUrl"));

                drawViews(field);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return field;
    }

    private void drawViews(Field field) {
        switch(field.getFieldType()) {
            case DROP_DOWN:
                addSpinnerToLayout(field);
                break;
            case CHECKBOX:
                addCheckBoxToLayout(field);
                break;
            case RADIO_BUTTON:
                addRadioButtonToLayout(field);
                break;
            case TEXTFIELD:
                addEditTextToLayout(field);
                break;
            case BUTTON:
                addButtonToLayout(field);
                break;
            case IMAGE_VIEW:
                downloadImage(field);
                break;
            case DATE_PICKER:
                addDatePicker(field);
                break;
            case TIME_PICKER:
                addTimePicker(field);
                break;
            default:
                break;
        }
    }


    private void addSpinnerToLayout(Field field) {
      //  Field field = getFieldJSON();
        View view = null;

        listener = new OnObservableChangeListener() {
            @Override
            public void onDependentVisible(Field field, View v, int rowId, int visibility) {
                makeText(MainActivity.this, " " + field.getDataObject() + ", " + rowId, Toast.LENGTH_LONG).show();
                if (field.getDataObject().equalsIgnoreCase(" Select")) {
                    v.setVisibility(View.VISIBLE);
                    ((TextView)v).setText("Enter Number");
                } else   v.setVisibility(View.GONE);
            }

            @Override
            public void onDependentInitialized(Field field, View v, int rowId, int visibility) {
                makeText(MainActivity.this, " " + field.getDataObject(), Toast.LENGTH_LONG).show();
                dropDownSelectedString = field.getDataObject();
            }
        };


        if (field.getFieldType().equals(FieldType.DROP_DOWN)) {
            view = UIBuilder.getDropDownView(inflater, this, field, listener, 5);
            dropDownSecondaryLabel = (TextView) view.findViewById(R.id.tv_secoundery_label_acv);
        }
        view.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        layout.addView(view);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = this.getAssets().open("json.txt");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

}
