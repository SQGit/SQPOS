package autospec.sqindia.net.autospec;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.sloop.fonts.FontsManager;


/**
 * Created by Admin on 14-05-2016.
 */
public class SignInActivity extends Activity {

    TextView textView_login_head, btn_signup;
    Button btn_signin;
    EditText editText_email, editText_password;
    String str_email, str_pass, token, str_user_id, name, email;
    ProgressDialog dialog;
    String storedname, storedemail, storedPass;
    LoginDataBaseAdapter loginDataBaseAdapter;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);    // Removes title bar
        setContentView(R.layout.signin_activity);

        //******************findViewById*******************
        textView_login_head = (TextView) findViewById(R.id.textView_head);
        btn_signin = (Button) findViewById(R.id.btn_sign);
        btn_signup = (TextView) findViewById(R.id.textView_signup);
        editText_email = (EditText) findViewById(R.id.editText_email);
        editText_password = (EditText) findViewById(R.id.editText_pw);


        //*****************change font using Typeface**************
        FontsManager.initFormAssets(this, "ROBOTO-LIGHT.TTF");
        FontsManager.changeFonts(this);


        // create a instance of SQLite Database
        loginDataBaseAdapter = new LoginDataBaseAdapter(this);
        loginDataBaseAdapter = loginDataBaseAdapter.open();


        //*******************Login onclicklistener***************
        btn_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String email = editText_email.getText().toString();
                String password = editText_password.getText().toString();

                // fetch the Password form database for respective user name
                String storedPassword = loginDataBaseAdapter.getSinlgeEntry(email);
                storedname = loginDataBaseAdapter.getName(email);
                storedemail = loginDataBaseAdapter.getEmail(email);
                storedPass = loginDataBaseAdapter.getPassword(email);
                Log.e("tag", "<***>" + storedPass);
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(SignInActivity.this);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("user", storedname);
                editor.putString("useremail", storedemail);
                editor.putString("pwd", storedPass);
                editor.apply();


                // condition
                if (email.matches("") && password.matches("")) {
                    customMsgDialog2();
                    //Toast.makeText(getApplicationContext(),"Fields are Vacant..",Toast.LENGTH_LONG).show();

                } else if (password.equals(storedPassword)) {

                    Intent intent_dashboard = new Intent(getApplicationContext(), Dashboard.class);
                    startActivity(intent_dashboard);

                    editText_email.setText("");
                    editText_password.setText("");
                    finish();

                } else {
                    //Toast.makeText(getApplicationContext(),"Username Password does not Match ..",Toast.LENGTH_LONG).show();
                    customMsgDialog1();
                }

            }

        });


        //*******************signup onclicklistener***************
        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), SignUPActivity.class);
                startActivity(i);
                finish();
            }
        });
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    private void customMsgDialog2() {
        final Dialog dialog = new Dialog(SignInActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_dialog2);
        TextView txt_head1 = (TextView) dialog.findViewById(R.id.txt_head1);
        TextView txt_msg1 = (TextView) dialog.findViewById(R.id.txt_msg1);
        Button btn_ok1 = (Button) dialog.findViewById(R.id.btn_ok1);
        Typeface tf = Typeface.createFromAsset(getApplicationContext().getAssets(), "ROBOTO-LIGHT.TTF");
        txt_head1.setTypeface(tf);
        txt_msg1.setTypeface(tf);
        btn_ok1.setTypeface(tf);

        btn_ok1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void customMsgDialog1() {

        final Dialog dialog = new Dialog(SignInActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_dialog);
        //adding text dynamically
        TextView txt_head2 = (TextView) dialog.findViewById(R.id.txt_head2);
        TextView txt_msg = (TextView) dialog.findViewById(R.id.txt_msg);
        Button btn_ok2 = (Button) dialog.findViewById(R.id.btn_ok2);

        Typeface tt = Typeface.createFromAsset(getApplicationContext().getAssets(), "ROBOTO-LIGHT.TTF");
        txt_head2.setTypeface(tt);
        txt_msg.setTypeface(tt);
        btn_ok2.setTypeface(tt);

        txt_msg.setText("User Name or Password \n does not match..");

        btn_ok2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

            }
        });
        dialog.show();
    }



    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
        return super.dispatchTouchEvent(ev);
    }


    @Override
    public void onBackPressed() {
        showExit();
    }


    private void showExit() {
        LayoutInflater layoutInflater = LayoutInflater.from(SignInActivity.this);
        View promptView = layoutInflater.inflate(R.layout.exitdialog, null);
        final android.app.AlertDialog alertD = new android.app.AlertDialog.Builder(SignInActivity.this).create();
        alertD.setCancelable(false);
        Window window = alertD.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        final TextView head1 = (TextView) promptView.findViewById(R.id.textView);
        final Button no = (Button) promptView.findViewById(R.id.btn_no);
        final Button yes = (Button) promptView.findViewById(R.id.btn_yes);

        Typeface tf = Typeface.createFromAsset(getApplicationContext().getAssets(), "ROBOTO-LIGHT.TTF");
        head1.setTypeface(tf);
        no.setTypeface(tf);
        yes.setTypeface(tf);

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1 = new Intent(Intent.ACTION_MAIN);
                i1.setAction(Intent.ACTION_MAIN);
                i1.addCategory(Intent.CATEGORY_HOME);
                i1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                i1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i1);
                finish();
            }
        });

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertD.dismiss();
            }
        });
        alertD.setView(promptView);
        alertD.show();
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Close The Database
        loginDataBaseAdapter.close();

    }



}



