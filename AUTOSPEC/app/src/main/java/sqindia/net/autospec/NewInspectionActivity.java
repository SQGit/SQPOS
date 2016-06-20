package sqindia.net.autospec;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.sloop.fonts.FontsManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by Admin on 17-05-2016.
 */
public class NewInspectionActivity extends Activity {
    TextView textView_head, textView_finished;
    EditText editText_unitno, editText_aggrementno, editText_cus_sign;
    Button button_submit, btn_back;
    String str_unit_no, str_agreement_no, str_user_id, str_inspection_date, token;
    ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_inspection);

        FontsManager.initFormAssets(this, "_SENINE.TTF");       //initialization
        FontsManager.changeFonts(this);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(NewInspectionActivity.this);
        token = sharedPreferences.getString("token", "");
        str_user_id = sharedPreferences.getString("str_user_id", "");

        //****************findviewbyid***********************
        editText_unitno = (EditText) findViewById(R.id.editText_unitno);
        editText_aggrementno = (EditText) findViewById(R.id.editText_aggrementno);
        textView_head = (TextView) findViewById(R.id.textView_head);
        textView_finished = (TextView) findViewById(R.id.textView_finished);

        button_submit = (Button) findViewById(R.id.button_submit);
        btn_back = (Button) findViewById(R.id.btn_back);

        //*******************Login onclicklistener***************
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_back = new Intent(getApplicationContext(), Dashboard.class);
                startActivity(intent_back);
            }
        });

        str_inspection_date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
        Log.e("tag", "inspectiontime" + str_inspection_date);
        button_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                str_unit_no = editText_unitno.getText().toString();
                str_agreement_no = editText_aggrementno.getText().toString();


                if (Util.Operations.isOnline(NewInspectionActivity.this)) {

                    if ((!str_unit_no.isEmpty()) && (!str_agreement_no.isEmpty())) {


                        new Inspection_Async(str_unit_no, str_agreement_no, str_user_id, str_inspection_date).execute();


                    } else {
                        new SweetAlertDialog(NewInspectionActivity.this, SweetAlertDialog.WARNING_TYPE)
                                .setTitleText("ALERT")
                                .setContentText("Please Enter all the Fields")
                                .show();
                    }

                } else {
                    new SweetAlertDialog(NewInspectionActivity.this, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("ALERT")
                            .setContentText("No Internet Connectivity")
                            .show();
                }
            }
        });
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
        return super.dispatchTouchEvent(ev);
    }

    private void showInputDialogFooter() {
        LayoutInflater layoutInflater = LayoutInflater.from(NewInspectionActivity.this);
        View promptView = layoutInflater.inflate(R.layout.submit_custom_dialog, null);
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(NewInspectionActivity.this);
        alertDialogBuilder.setView(promptView);
        final TextView head1 = (TextView) promptView.findViewById(R.id.textView);
        final TextView head2 = (TextView) promptView.findViewById(R.id.textView1);
        final TextView head3 = (TextView) promptView.findViewById(R.id.txt_agrreement);
        final Button ok = (Button) promptView.findViewById(R.id.button);
        Typeface tf = Typeface.createFromAsset(getApplicationContext().getAssets(), "_SENINE.TTF");
        head1.setTypeface(tf);
        head2.setTypeface(tf);
        head3.setTypeface(tf);
        head3.setText(str_agreement_no);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent goD = new Intent(getApplicationContext(), Display_Inspection_details.class);
                startActivity(goD);
            }
        });
        // create an alert dialog
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }

    private class Inspection_Async extends AsyncTask<String, Void, String> {
        String str_unit_no, str_agreement_no, str_user_id, str_inspection_date;

        public Inspection_Async(String str_unit_no, String str_agreement_no, String str_user_id, String str_inspection_date) {

            this.str_unit_no = str_unit_no;
            this.str_agreement_no = str_agreement_no;
            this.str_user_id = str_user_id;
            this.str_inspection_date = str_inspection_date;
        }

        protected void onPreExecute() {

            super.onPreExecute();
            mProgressDialog = new ProgressDialog(NewInspectionActivity.this);
            mProgressDialog.setTitle("Please wait");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.setCancelable(false);
            mProgressDialog.show();


        }

        @Override
        protected String doInBackground(String... params) {
            String json = "", s = "";
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("unitNo", str_unit_no);
                jsonObject.put("agreementNo", str_agreement_no);
                jsonObject.put("userId", str_user_id);
                jsonObject.put("inspectionDate", str_inspection_date);
                json = jsonObject.toString();
                Log.d("tag", "json format" + json);
                return s = HttpUtils.makeRequest1("http://sqindia01.cloudapp.net/autospec/api/v1/inspection/add", json, token);
            } catch (JSONException e) {
                e.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d("tag", "json result" + s);
            mProgressDialog.dismiss();
            if (s.equals("")) {

                new SweetAlertDialog(NewInspectionActivity.this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Oops...")
                        .setContentText("Internet Connectivity very slow? try again")
                        .show();
            } else {
                JSONObject jo = null;
                try {
                    jo = new JSONObject(s);
                    String status = jo.getString("status");
                    String msg = jo.getString("message");
                    String inspectionId = jo.getString("data");

                    if (status.equals("SUCCESS")) {
                        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("str_agreement_no", str_agreement_no);
                        editor.putString("str_unit_no", str_unit_no);
                        editor.putString("inspectionId", inspectionId);
                        editor.apply();
                        showInputDialogFooter();
                    } else {
                        new SweetAlertDialog(NewInspectionActivity.this, SweetAlertDialog.WARNING_TYPE)
                                .setTitleText("ALERT")
                                .setContentText("Please try again")
                                .setConfirmText("OK")
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sweetAlertDialog) {

                                        Intent goD = new Intent(getApplicationContext(), NewInspectionActivity.class);
                                        startActivity(goD);

                                    }
                                })
                                .show();
                    }
                } catch (JSONException e) {
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
