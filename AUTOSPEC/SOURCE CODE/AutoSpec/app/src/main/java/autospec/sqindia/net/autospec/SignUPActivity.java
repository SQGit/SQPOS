package autospec.sqindia.net.autospec;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class SignUPActivity extends Activity {

    EditText editTextUserName, editTextEmail, editTextPassword, editTextConfirmPassword;
    Button btnCreateAccount, btn_back;
    TextView textView_head;
    LoginDataBaseAdapter loginDataBaseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_activity);

        // get Instance of Database Adapter
        loginDataBaseAdapter = new LoginDataBaseAdapter(this);
        loginDataBaseAdapter = loginDataBaseAdapter.open();

        // Get Refferences of Views
        editTextUserName = (EditText) findViewById(R.id.editText_name);
        editTextEmail = (EditText) findViewById(R.id.editText_email);
        editTextPassword = (EditText) findViewById(R.id.editText_pwd);
        editTextConfirmPassword = (EditText) findViewById(R.id.editText_repwd);
        btnCreateAccount = (Button) findViewById(R.id.btn_signin);
        textView_head = (TextView) findViewById(R.id.textView_head);
        btn_back = (Button) findViewById(R.id.btn_back);

        //*****************change font using Typeface**************
        Typeface tf = Typeface.createFromAsset(getApplicationContext().getAssets(), "ROBOTO-LIGHT.TTF");
        editTextUserName.setTypeface(tf);
        editTextEmail.setTypeface(tf);
        editTextPassword.setTypeface(tf);
        editTextConfirmPassword.setTypeface(tf);
        textView_head.setTypeface(tf);
        btn_back.setTypeface(tf);


        //*******************Login onclicklistener***************
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_back = new Intent(getApplicationContext(), SignInActivity.class);
                startActivity(intent_back);
                finish();
            }
        });

        editTextConfirmPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_NEXT) {
                    editTextConfirmPassword.performClick();
                    return true;
                }
                return false;
            }
        });

        btnCreateAccount.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub


                String userName = editTextUserName.getText().toString();
                String email = editTextEmail.getText().toString();
                String password = editTextPassword.getText().toString();
                String confirmPassword = editTextConfirmPassword.getText().toString();


                // check if any of the fields are vaccant
                if (userName.equals("") || email.equals("") || password.equals("") || confirmPassword.equals("")) {
                    message1();
                    return;
                }


                if (!password.equals(confirmPassword)) {
                    message2();
                    return;
                }

                if ((email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches())) {
                    message3();
                } else {
                    // Save the Data in Database
                    loginDataBaseAdapter.insertEntry(userName, email, password);
                    Toast.makeText(getApplicationContext(), "Account Successfully Created ", Toast.LENGTH_LONG).show();
                    Intent i = new Intent(getApplicationContext(), SignInActivity.class);
                    startActivity(i);
                    editTextUserName.setText("");
                    editTextEmail.setText("");
                    editTextPassword.setText("");
                    editTextConfirmPassword.setText("");
                    finish();

                }
            }
        });
    }



    private void message3() {

        final Dialog dialog = new Dialog(SignUPActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_dialog);
        //adding text dynamically
        TextView txt_head2 = (TextView) dialog.findViewById(R.id.txt_head2);
        TextView txt_msg = (TextView) dialog.findViewById(R.id.txt_msg);
        txt_head2.setText("Message Received");
        txt_msg.setText("Invalid Email Address. Please enter Valid Email Address...");
        Button btn_ok2 = (Button) dialog.findViewById(R.id.btn_ok2);

        Typeface tt = Typeface.createFromAsset(getApplicationContext().getAssets(), "ROBOTO-LIGHT.TTF");
        txt_head2.setTypeface(tt);
        txt_msg.setTypeface(tt);


        btn_ok2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

            }
        });
        dialog.show();
    }

    private void message2() {
        final Dialog dialog = new Dialog(SignUPActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_dialog);
        //adding text dynamically
        TextView txt_head2 = (TextView) dialog.findViewById(R.id.txt_head2);
        TextView txt_msg = (TextView) dialog.findViewById(R.id.txt_msg);
        txt_head2.setText("Message Received");
        txt_msg.setText("Password does not match...");
        Button btn_ok2 = (Button) dialog.findViewById(R.id.btn_ok2);

        Typeface tt = Typeface.createFromAsset(getApplicationContext().getAssets(), "ROBOTO-LIGHT.TTF");
        txt_head2.setTypeface(tt);
        txt_msg.setTypeface(tt);


        btn_ok2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

            }
        });
        dialog.show();
    }

    private void message1() {

        final Dialog dialog = new Dialog(SignUPActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_dialog);
        //adding text dynamically
        TextView txt_head2 = (TextView) dialog.findViewById(R.id.txt_head2);
        TextView txt_msg = (TextView) dialog.findViewById(R.id.txt_msg);
        txt_head2.setText("Message Received");
        txt_msg.setText("Fields Are Vacant...");
        Button btn_ok2 = (Button) dialog.findViewById(R.id.btn_ok2);

        Typeface tt = Typeface.createFromAsset(getApplicationContext().getAssets(), "ROBOTO-LIGHT.TTF");
        txt_head2.setTypeface(tt);
        txt_msg.setTypeface(tt);


        btn_ok2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

            }
        });
        dialog.show();
    }


    @Override
    public void onBackPressed() {
        Intent non = new Intent(getApplicationContext(), SignInActivity.class);
        startActivity(non);
        finish();
    }

   /* @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
*/
    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();

        loginDataBaseAdapter.close();
    }


}