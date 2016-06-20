package sqindia.net.autospec;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by RSA on 24-05-2016.
 */
public class Edit_Inspection extends Activity {
    String str_id, str_unit_no, str_agreement, str_user_id, str_date, token;
    EditText edt_unitno, edt_agrementno;
    Button btn_submit;
    TextView txt_head;
    ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_inspection);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(Edit_Inspection.this);
        token = sharedPreferences.getString("token", "");
        Typeface tf = Typeface.createFromAsset(getApplicationContext().getAssets(), "_SENINE.TTF");
        edt_unitno = (EditText) findViewById(R.id.edt_unitno);
        edt_agrementno = (EditText) findViewById(R.id.edt_agrementno);
        btn_submit = (Button) findViewById(R.id.btn_submit);
        txt_head = (TextView) findViewById(R.id.textView_head);

        Intent intent = getIntent();
        if (null != intent) {
            str_id = intent.getStringExtra("id");
            str_unit_no = intent.getStringExtra("unit_no");
            str_agreement = intent.getStringExtra("agreement_no");
            str_user_id = intent.getStringExtra("user_id");


        }
        edt_unitno.setTypeface(tf);
        edt_agrementno.setTypeface(tf);
        btn_submit.setTypeface(tf);
        txt_head.setTypeface(tf);
        edt_unitno.setText(str_unit_no);
        edt_agrementno.setText(str_agreement);
        str_date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
        Log.e("tag", "inspectiontime" + str_date);

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (Util.Operations.isOnline(Edit_Inspection.this)) {

                    str_unit_no=edt_unitno.getText().toString();
                    str_agreement=edt_agrementno.getText().toString();
                    new updateasync(str_id, str_unit_no, str_agreement, str_user_id, str_date).execute();

                } else {
                    new SweetAlertDialog(Edit_Inspection.this, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("ALERT")
                            .setContentText("No Internet Connectivity")
                            .show();
                }
            }
        });

    }


    private class updateasync extends AsyncTask<String, Void, String> {
        String str_id, str_unit_no, str_agreement, str_user_id, str_date;

        public updateasync(String str_id, String str_unit_no, String str_agreement, String str_user_id, String str_date) {

            this.str_id = str_id;
            this.str_unit_no = str_unit_no;
            this.str_agreement = str_agreement;
            this.str_user_id = str_user_id;
            this.str_date = str_date;
        }

        protected void onPreExecute() {

            super.onPreExecute();
            mProgressDialog = new ProgressDialog(Edit_Inspection.this);
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
                jsonObject.put("id", str_id);
                jsonObject.put("unitNo", str_unit_no);
                jsonObject.put("agreementNo", str_agreement);
                jsonObject.put("userId", str_user_id);
                jsonObject.put("inspectionDate", str_date);
                json = jsonObject.toString();
                Log.d("tag", "json format" + json);
                return s = HttpUtils.makeRequest1("http://sqindia01.cloudapp.net/autospec/api/v1/inspection/update", json, token);
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


            } else {
                try {
                    JSONObject jo = new JSONObject(s);
                    String status = jo.getString("status");
                    String msg = jo.getString("message");

                    if (status.equals("SUCCESS")) {
                        showInputDialogFooter();
                    } else {
                        new SweetAlertDialog(Edit_Inspection.this, SweetAlertDialog.WARNING_TYPE)
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

    private void showInputDialogFooter() {
        LayoutInflater layoutInflater = LayoutInflater.from(Edit_Inspection.this);
        View promptView = layoutInflater.inflate(R.layout.update_custom_dialog, null);
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Edit_Inspection.this);
        alertDialogBuilder.setView(promptView);
        alertDialogBuilder.setCancelable(false);
        final TextView head1 = (TextView) promptView.findViewById(R.id.textView);
        final TextView head2 = (TextView) promptView.findViewById(R.id.textView1);
        final TextView head3 = (TextView) promptView.findViewById(R.id.txt_agrreement);

        final Button ok = (Button) promptView.findViewById(R.id.button);
        Typeface tf = Typeface.createFromAsset(getApplicationContext().getAssets(), "_SENINE.TTF");
        head1.setTypeface(tf);
        head2.setTypeface(tf);
        head3.setTypeface(tf);
        head2.setText(str_unit_no);
        head3.setText(str_agreement);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent goD = new Intent(getApplicationContext(), ModifyInspectionActivity.class);
                startActivity(goD);
            }
        });
        // create an alert dialog
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
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
