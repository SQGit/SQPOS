package sqindia.net.autospec;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by Admin on 14-05-2016.
 */
public class SignInActivity extends Activity {

    TextView textView_login_head, btn_signup;
    Button btn_signin,btn_back;
    EditText editText_email, editText_password;
    String str_email, str_pass, token,str_user_id,name,email;
    ProgressDialog dialog;

    public static String SIGNIN_URL = "http://sqindia01.cloudapp.net/autospec/api/v1/user/login";

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);	// Removes title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);	// Removes notification bar

        setContentView(R.layout.signin_activity);

        //******************findViewById*******************
        textView_login_head = (TextView) findViewById(R.id.textView_head);
        btn_signin = (Button) findViewById(R.id.btn_signin);
        btn_back = (Button) findViewById(R.id.btn_back);
        btn_signup = (TextView) findViewById(R.id.textView_signup);
        editText_email = (EditText) findViewById(R.id.editText_email);
        editText_password = (EditText) findViewById(R.id.editText_pwd);
       //*****************change font using Typeface**************
        Typeface tf = Typeface.createFromAsset(getApplicationContext().getAssets(), "_SENINE.TTF");
        textView_login_head.setTypeface(tf);
        btn_signup.setTypeface(tf);
        editText_email.setTypeface(tf);
        editText_password.setTypeface(tf);
        btn_back.setTypeface(tf);





        //*******************Login onclicklistener***************
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_back=new Intent(getApplicationContext(),SignupActivity.class);
                startActivity(intent_back);
            }
        });
        //*******************Login onclicklistener***************
        btn_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                str_email = editText_email.getText().toString();
                str_pass = editText_password.getText().toString();


                if (!(str_email.isEmpty() || str_email.length() < 3)) {
                    if (!(str_pass.isEmpty() || str_pass.length() < 4 || str_pass.length() > 10)) {
                        new MyActivityAsync(str_email, str_pass).execute();
                    } else {
                        editText_password.setError("between 4 and 10 alphanumeric characters");
                        editText_password.requestFocus();
                    }
                } else {
                    editText_email.setError("Enter valid UserName");
                    editText_password.requestFocus();
                }


            }
               /* Intent i=new Intent(getApplicationContext(),Dashboard.class);
                startActivity(i);*/

        });

        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), SignupActivity.class);
                startActivity(i);

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

     class MyActivityAsync extends AsyncTask<String, Void, String> {

        String email, pass_word;

        public MyActivityAsync(String email, String pass) {
        this.email = email;
            this.pass_word = pass;
             }


        protected void onPreExecute() {

            super.onPreExecute();
            dialog = new ProgressDialog(SignInActivity.this);
            dialog.setTitle("Login...");
            dialog.setMessage("Please wait");
            dialog.setIndeterminate(false);
            dialog.setCancelable(false);
            dialog.show();
             }
        @Override
        protected String doInBackground(String... params) {
            String json = "", s = "";
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("email", str_email);
                jsonObject.put("password", str_pass);
                json = jsonObject.toString();
                JSONObject data = new JSONObject();
                data.put("data", jsonObject);
                return s = HttpUtils.makeRequest1(SIGNIN_URL, json);
            } catch (JSONException e) {

                e.printStackTrace();
            }

            return null;

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.e("tag", "result" + s);
            dialog.dismiss();
            if (s == "") {

                new SweetAlertDialog(SignInActivity.this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Oops...")
                        .setContentText("Internet Connectivity very slow? try again")
                        .show();

            }else {

                try {
                    JSONObject jo = new JSONObject(s);
                    String status = jo.getString("status");
                    String msg = jo.getString("message");
                    String data = jo.getString("data");
                    JSONObject da = new JSONObject(data);
                    token = da.getString("authToken");
                    name = da.getString("name");
                    email = da.getString("email");
                    Log.e("tag","helo"+name);
                    str_user_id = da.getString("id");
                    Log.e("tag", "token_value" + token);
                    Log.e("tag", "str_user_id" + str_user_id);

                    if (status.equals("FAILED")){
                        Log.e("tag", "token_valuedagsgg" + token);
                    }

                    if (status.equals("SUCCESS")) {

                        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("token", token);
                        editor.putString("str_user_id", str_user_id);
                        editor.putString("n", name);
                        editor.putString("m",email);
                        Log.e("tag","n"+name);
                        editor.apply();
                        Intent goD = new Intent(getApplicationContext(), Dashboard.class);
                        startActivity(goD);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();

                    new SweetAlertDialog(SignInActivity.this, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("ALERT")
                            .setContentText("Username and password mismatch")
                            .setConfirmText("OK")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {

                                    Intent goD = new Intent(getApplicationContext(), SignInActivity.class);
                                    startActivity(goD);

                                }
                            })
                            .show();
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
