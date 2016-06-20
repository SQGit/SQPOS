package sqindia.net.autospec;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by Admin on 17-05-2016.
 */
public class ModifyInspectionActivity extends Activity {

    TextView textView_head, textView_unitno, textView_rentalid;
    ProgressDialog mProgressDialog;
    Button btn_back;
    String token;
    ArrayList<HashMap<String, String>> Viewinspectionlist;
    View_Inspection_Adapter view_details_inspection;
    static String id="id";
    static String unitNo="unitNo";
    static String agreementNo="agreementNo";
    static String userId="userId";
    static String inspectionDate="inspectionDate";
    static String imgUploadStaus="imgUploadStaus";

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);    // Removes title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);    // Removes notification bar

        setContentView(R.layout.modify_inspection);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(ModifyInspectionActivity.this);
        token = sharedPreferences.getString("token", "");

        //****************findviewbyid***********************
        textView_head = (TextView) findViewById(R.id.textView_head);
        textView_unitno = (TextView) findViewById(R.id.textView_unitno);
        textView_rentalid = (TextView) findViewById(R.id.textView_rentalid);
        btn_back = (Button) findViewById(R.id.btn_back);

        //*****************change font using Typeface**************
        Typeface tf = Typeface.createFromAsset(getApplicationContext().getAssets(), "_SENINE.TTF");
        textView_head.setTypeface(tf);
        textView_unitno.setTypeface(tf);
        textView_rentalid.setTypeface(tf);
        btn_back.setTypeface(tf);

        //*******************Login onclicklistener***************
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_back=new Intent(getApplicationContext(),Dashboard.class);
                startActivity(intent_back);
            }
        });

        Viewinspectionlist = new ArrayList<HashMap<String, String>>();
        if (Util.Operations.isOnline(ModifyInspectionActivity.this)) {

            new View_Inspectionasync().execute();

        } else {
            new SweetAlertDialog(ModifyInspectionActivity.this, SweetAlertDialog.WARNING_TYPE)
                    .setTitleText("ALERT")
                    .setContentText("No Internet Connectivity")
                    .show();
        }


    }

    private class View_Inspectionasync extends AsyncTask<String, Void, String> {

        protected void onPreExecute() {

            super.onPreExecute();
            mProgressDialog = new ProgressDialog(ModifyInspectionActivity.this);
            mProgressDialog.setTitle("View Inspection");
            mProgressDialog.setMessage("Please wait");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.setCancelable(false);
            mProgressDialog.show();


        }

        @Override
        protected String doInBackground(String... params) {

            String json = "", jsonstr = "";
            try {
                JSONObject jsonObject = new JSONObject();
                json = jsonObject.toString();
                JSONObject data = new JSONObject();
                data.put("data", jsonObject);

                return jsonstr = HttpUtils.makeRequest1("http://sqindia01.cloudapp.net/autospec/api/v1/inspection/listAll", json, token);
            } catch (JSONException e) {

                e.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPostExecute(String jsonstr) {
            Log.d("tag", "json result" + jsonstr);
            super.onPostExecute(jsonstr);
            mProgressDialog.dismiss();

            if (jsonstr.equals("")) {

                new SweetAlertDialog(ModifyInspectionActivity.this, SweetAlertDialog.ERROR_TYPE)
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




            } else {

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
                            map.put("imgUploadStaus", data.getString("imgUploadStaus"));

                            Viewinspectionlist.add(map);
                        }
                        ListView list = (ListView) findViewById(R.id.listView);
                        view_details_inspection = new View_Inspection_Adapter(ModifyInspectionActivity.this, Viewinspectionlist);
                        list.setAdapter(view_details_inspection);


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