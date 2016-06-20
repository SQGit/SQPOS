package sqindia.net.autospec;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by Admin on 14-05-2016.
 */
public class SignupActivity extends Activity {

    EditText editText_name,editText_uname,editText_pwd,editText_repwd;
    TextView textView_head;
    Button button_submit,btn_back;
    String name, uname, password, repassword;
    ProgressDialog pDialog;


    public static String REG_URL="http://sqindia01.cloudapp.net/autospec/api/v1/user/register";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_activity);

        //****************findviewbyid***********************
        editText_name=(EditText)findViewById(R.id.editText_name);
        editText_uname=(EditText)findViewById(R.id.editText_email);
        editText_pwd=(EditText)findViewById(R.id.editText_pwd);
        editText_repwd=(EditText)findViewById(R.id.editText_repwd);
        textView_head=(TextView)findViewById(R.id.textView_head);
        button_submit=(Button)findViewById(R.id.btn_signin);
        btn_back=(Button)findViewById(R.id.btn_back);

        //*****************change font using Typeface**************
        Typeface tf = Typeface.createFromAsset(getApplicationContext().getAssets(), "_SENINE.TTF");
        editText_name.setTypeface(tf);
        editText_uname.setTypeface(tf);
        editText_pwd.setTypeface(tf);
        editText_repwd.setTypeface(tf);
        textView_head.setTypeface(tf);
        btn_back.setTypeface(tf);
        //*******************Login onclicklistener***************
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_back=new Intent(getApplicationContext(),SignInActivity.class);
                startActivity(intent_back);
            }
        });

        button_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                name = editText_name.getText().toString();
                uname = editText_uname.getText().toString();
                password = editText_pwd.getText().toString();
                repassword = editText_repwd.getText().toString();

                if (Util.Operations.isOnline(SignupActivity.this)) {
                    new Register_Data(name, uname, password).execute();

                } else {
                    new SweetAlertDialog(SignupActivity.this, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("ALERT")
                            .setContentText("No Internet Connectivity")
                            .show();

                }


               /* Intent i=new Intent(getApplicationContext(),SignInActivity.class);
                startActivity(i);*/

            }
        });



    }

    private class Register_Data extends AsyncTask<String, Void, String> {

        String name, email, password;

        public Register_Data(String name, String email, String password) {
            this.name = name;
            this.email = email;
            this.password = password;

        }


        protected void onPreExecute() {

            super.onPreExecute();
            Log.e("tag","xxx1");

            pDialog = new ProgressDialog(SignupActivity.this);
            pDialog.setTitle("Registering..");
            pDialog.setMessage("Please wait");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();


        }

        @Override
        protected String doInBackground(String... params) {
            Log.e("tag","xxx2");

            String json = "", s = "";
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.accumulate("name", name);
                jsonObject.accumulate("email", uname);
                jsonObject.accumulate("password", password);

                json = jsonObject.toString();
                //JSONObject data = new JSONObject();
               // data.accumulate("data", jsonObject);

                return s=HttpUtils.makeRequest6(REG_URL, json);
            } catch (JSONException e) {
                Log.e("tag","xxx3"+e);
                e.printStackTrace();
            }
            return s;

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d("tag", "result" + s);

            try {
                JSONObject jo = new JSONObject(s);
                String status = jo.getString("status");
                String msg = jo.getString("message");

                Log.e("tag", "<-----Status----->" + status);
                Log.e("tag", "<-----Status----->" + msg);

                if (status.equals("SUCCESS")) {

                    new SweetAlertDialog(SignupActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                            .setTitleText("Message Alert")
                            .setContentText(msg)
                            .setConfirmText("OK")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {

                                    sweetAlertDialog.cancel();
                                    Intent goD = new Intent(getApplicationContext(), SignInActivity.class);
                                    startActivity(goD);
                                    finish();

                                }
                            })
                            .show();

                }
                else if (status.equals("FAILED")) {
                    {
                        new SweetAlertDialog(SignupActivity.this, SweetAlertDialog.WARNING_TYPE)
                                .setTitleText("Oops!")
                                .setContentText(msg)
                                .setConfirmText("OK")
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sweetAlertDialog) {

                                        editText_uname.requestFocus();
                                        editText_uname.setError("");
                                        finish();
                                    }
                                })
                                .show();

                    }
                }
            }

            catch (JSONException e) {
                e.printStackTrace();
            }


            pDialog.dismiss();
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
