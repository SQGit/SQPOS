package sqindia.net.autospec;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.sloop.fonts.FontsManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by RSA on 24-05-2016.
 */
public class FilterReports extends Activity {
    Button btn_back;
    String token, str_unit_id, str_agreement_id;
    ArrayList<unitList> cl;
    ArrayList<UnitspinnerList> agreementlist;
    ArrayList<String> Unit_list, agreementlist1;
    ArrayList<HashMap<String, String>> Viewfilterlist;
    Filter_Adapter view_filter_details;
    static String id="id";
    static String unitNo="unitNo";
    static String agreementNo="agreementNo";
    static String userId="userId";
    static String inspectionDate="inspectionDate";
    Spinner spinner_unitno, spinner_agreementno;
    Button btn_filter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.filter_reports);
        FontsManager.initFormAssets(this, "_SENINE.TTF");       //initialization
        FontsManager.changeFonts(this);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(FilterReports.this);
        token = sharedPreferences.getString("token", "");
        //*******************Login onclicklistener***************
        btn_back = (Button) findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_back=new Intent(getApplicationContext(),Dashboard.class);
                startActivity(intent_back);
            }
        });
        spinner_agreementno = (Spinner) findViewById(R.id.spinner_agreementno);
        spinner_unitno = (Spinner) findViewById(R.id.spinner_unitno);
        btn_filter = (Button) findViewById(R.id.btn_filter);
        Viewfilterlist = new ArrayList<HashMap<String, String>>();
        if (Util.Operations.isOnline(FilterReports.this)) {
            new View_Inspectionasync().execute();
            new dropdown_agreementasync().execute();


        } else {
            new SweetAlertDialog(FilterReports.this, SweetAlertDialog.WARNING_TYPE)
                    .setTitleText("ALERT")
                    .setContentText("No Internet Connectivity")
                    .show();

        }
        btn_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Util.Operations.isOnline(FilterReports.this)) {
                    if(!str_unit_id.equals(str_agreement_id))
                    {
                        new filterlistasync(str_unit_id,str_agreement_id).execute();
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(),"Please select the curresponding Aggrement No",Toast.LENGTH_LONG).show();
                    }


                }else {
                    new SweetAlertDialog(FilterReports.this, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("ALERT")
                            .setContentText("No Internet Connectivity")
                            .show();
                }

            }
        });

    }
    class View_Inspectionasync extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            cl = new ArrayList<unitList>();
            Unit_list = new ArrayList<String>();
            String json = "", jsonstr = "";
            try {
                JSONObject jsonObject = new JSONObject();
                json = jsonObject.toString();
                JSONObject data = new JSONObject();
                data.put("data", jsonObject);
                return jsonstr = HttpUtils.makeRequest1("http://sqindia01.cloudapp.net/autospec/api/v1/inspection/lookupUnitNo", json, token);
            } catch (JSONException e) {

                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d("tag", " json result spinner " + s);
            try {
                JSONObject jo = new JSONObject(s);
                String status = jo.getString("status");
                String msg = jo.getString("message");
                if (status.equals("SUCCESS")) {
                    JSONArray data1 = jo.getJSONArray("data");
                    for (int i = 0; i < data1.length(); i++) {
                        JSONObject data = data1.getJSONObject(i);
                        //   unitList unitspinnerList = new unitList();
                        unitList unit_no = new unitList();
                        unit_no.setunitId(data.optString("id"));
                        unit_no.setunitName(data.optString("unit_no"));
                        cl.add(unit_no);
                        Unit_list.add(data.optString("unitNo"));

                    }
                }
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            spinner_unitno.setAdapter(new ArrayAdapter<String>(FilterReports.this, android.R.layout.simple_spinner_dropdown_item, Unit_list));
            spinner_unitno.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                @Override
                public void onItemSelected(AdapterView<?> arg0,
                                           View arg1, int position, long arg3) {
                    // TODO Auto-generated method stub
                    if (position > -1) {
                        str_unit_id = cl.get(position).getunitId();
                        Log.d("tag", "<----str_unit_id----->" + str_unit_id);
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> arg0) {
                    // TODO Auto-generated method stub
                }
            });

        }
    }

    class dropdown_agreementasync extends AsyncTask<String, Void, String> {
    String json = "", jsonstr = "";
     @Override
        protected String doInBackground(String... params) {
            agreementlist = new ArrayList<UnitspinnerList>();
            agreementlist1 = new ArrayList<String>();
            try {
                JSONObject jsonObject = new JSONObject();
                json = jsonObject.toString();
                JSONObject data = new JSONObject();
                data.put("data", jsonObject);
                return jsonstr = HttpUtils.makeRequest1("http://sqindia01.cloudapp.net/autospec/api/v1/inspection/lookupAgreementNo", json, token);
            } catch (JSONException e) {

                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            Log.d("tag", " json result agreement spinner " + s);

            try {
                JSONObject jo = new JSONObject(s);
                String status = jo.getString("status");
                String msg = jo.getString("message");
                if (status.equals("SUCCESS")) {
                    JSONArray data1 = jo.getJSONArray("data");
                    for (int i = 0; i < data1.length(); i++) {
                        JSONObject data = data1.getJSONObject(i);
                        UnitspinnerList unitspinnerList = new UnitspinnerList();
                        //  unitList unit_no = new unitList();
                        unitspinnerList.setagreementid(data.optString("id"));
                        unitspinnerList.setagreementName(data.optString("agreementNo"));
                        agreementlist.add(unitspinnerList);
                        agreementlist1.add(data.optString("agreementNo"));

                    }
                }
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
             spinner_agreementno.setAdapter(new ArrayAdapter<String>(FilterReports.this, android.R.layout.simple_spinner_dropdown_item, agreementlist1));
            spinner_agreementno.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                @Override
                public void onItemSelected(AdapterView<?> arg0,
                                           View arg1, int position, long arg3) {
                    // TODO Auto-generated method stub
                    if (position > -1) {
                        str_agreement_id = agreementlist.get(position).getagreementid();
                        Log.d("tag", "<----str_agreement_id----->" + str_agreement_id);
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> arg0) {
                    // TODO Auto-generated method stub
                }
            });


        }
    }
    class filterlistasync extends AsyncTask<String, Void, String> {
        String json = "", jsonstr = "",str_unit_id,str_agreement_id;
            public filterlistasync(String str_unit_id, String str_agreement_id) {
            this.str_unit_id=str_unit_id;
            this.str_agreement_id=str_agreement_id;
         }
         @Override
        protected String doInBackground(String... params) {
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id", str_unit_id);
                jsonObject.put("id", str_agreement_id);
                json = jsonObject.toString();
                JSONObject data = new JSONObject();
                data.put("data", jsonObject);
                return jsonstr = HttpUtils.makeRequest1("http://sqindia01.cloudapp.net/autospec/api/v1/inspection/filter", json,token);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d("tag","filterresult"+s);
            if (jsonstr.equals("")) {

                new SweetAlertDialog(FilterReports.this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Oops...")
                        .setContentText("Internet Connectivity very slow? try again")
                        .setConfirmText("Try again")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                Intent i=new Intent(getApplicationContext(),ModifyInspectionActivity.class);
                                startActivity(i);
                            }
                        })
                        .show();




            }else {

                try {
                    JSONObject jo = new JSONObject(jsonstr);
                    String status = jo.getString("status");
                    String msg = jo.getString("message");
                    if (status.equals("SUCCESS")) {
                        JSONArray data1 = jo.getJSONArray("data");
                        for (int i = 0; i < data1.length(); i++) {
                            JSONObject data = data1.getJSONObject(i);
                            HashMap<String, String> map = new HashMap<String, String>();

                            map.put("id", data.getString("id"));
                            map.put("unitNo", data.getString("unitNo"));
                            map.put("agreementNo", data.getString("agreementNo"));
                            map.put("userId", data.getString("userId"));
                            map.put("inspectionDate", data.getString("inspectionDate"));


                            Viewfilterlist.add(map);


                        }
                        ListView list = (ListView) findViewById(R.id.listView);
                        view_filter_details = new Filter_Adapter(FilterReports.this, Viewfilterlist);
                        list.setAdapter(view_filter_details);
                        Log.d("tag","view_filter_details"+list);


                    }
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }
    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

}