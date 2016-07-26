package autospec.sqindia.net.autospec;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;


/**
 * Created by Admin on 07-06-2016.
 */
public class NewInspectionActivity extends Activity {

    TextView textView_head, textView_finished;
    EditText editText_unitno, editText_aggrementno, editText_cus_sign;
    Button button_submit, btn_back;
    String str_unit_no, str_agreement_no, str_user_id, str_inspection_date, token,dash_enable,enable;
    ProgressDialog mProgressDialog;
    LoginDataBaseAdapter loginDataBaseAdapter;
    public int count = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_inspection);

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


        //*****************change font using Typeface**************
        Typeface tf = Typeface.createFromAsset(getApplicationContext().getAssets(), "ROBOTO-LIGHT.TTF");
        textView_head.setTypeface(tf);
        editText_unitno.setTypeface(tf);
        editText_aggrementno.setTypeface(tf);
        textView_finished.setTypeface(tf);
        button_submit.setTypeface(tf);
        btn_back.setTypeface(tf);


        // get Instance of Database Adapter
        loginDataBaseAdapter = new LoginDataBaseAdapter(this);
        loginDataBaseAdapter = loginDataBaseAdapter.open();


        //*******************Login onclicklistener***************
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_back = new Intent(getApplicationContext(), Dashboard.class);
                startActivity(intent_back);
                finish();
            }
        });


        str_inspection_date = new SimpleDateFormat("dd/MM/yyyy  HH:mm").format(Calendar.getInstance().getTime());

        //*******************submit button onclicklistener***************
        button_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                str_unit_no = editText_unitno.getText().toString();
                str_agreement_no = editText_aggrementno.getText().toString();

                if(str_unit_no.equals("")&&(str_agreement_no.equals("")))
                {
                   dash_enable="0";
                    Log.e("tag","empty"+dash_enable);
                }
                else
                {
                    dash_enable="1";
                    Log.e("tag","full"+dash_enable);
                }

                boolean b = loginDataBaseAdapter.checkEvent(str_unit_no, str_agreement_no);
                Log.e("tag", "boolean" + b);

                if ((!str_unit_no.isEmpty()) && (!str_agreement_no.isEmpty())) {
                    if (b == true)
                    {
                        message1();
                        editText_unitno.setText("");
                        editText_aggrementno.setText("");
                    } else {
                        loginDataBaseAdapter.inspection_details(str_unit_no, str_agreement_no, str_inspection_date);


                        String storedId = loginDataBaseAdapter.getId(str_unit_no);


                        //*********************put all value from shared preference***********
                        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(NewInspectionActivity.this);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("unit_no", str_unit_no);
                        editor.putString("rental_no", str_agreement_no);
                        editor.putString("date", str_inspection_date);
                        editor.putString("id", storedId);
                        editor.putString("empty_check",dash_enable);
                        editor.commit();
                        showInputDialogFooter();
                    }
                } else {
                    message2();

                }

            }

        });
    }

    private void message2() {

        final Dialog dialog = new Dialog(NewInspectionActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_dialog);
        //adding text dynamically
        TextView txt_head2 = (TextView) dialog.findViewById(R.id.txt_head2);
        TextView txt_msg = (TextView) dialog.findViewById(R.id.txt_msg);
        txt_head2.setText("Alert Message");
        txt_msg.setText("Fields are Vacant....");
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
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
        return super.dispatchTouchEvent(ev);
    }

    //show register user method
    private void showInputDialogFooter() {

        LayoutInflater layoutInflater = LayoutInflater.from(NewInspectionActivity.this);
        View promptView = layoutInflater.inflate(R.layout.submit_custom_dialog, null);
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(NewInspectionActivity.this);
        alertDialogBuilder.setView(promptView);
        alertDialogBuilder.setCancelable(false);
        final TextView head1 = (TextView) promptView.findViewById(R.id.textView);
        final TextView head2 = (TextView) promptView.findViewById(R.id.textView1);
        final TextView head3 = (TextView) promptView.findViewById(R.id.txt_agrreement);
        final Button ok = (Button) promptView.findViewById(R.id.button);
        Typeface tf = Typeface.createFromAsset(getApplicationContext().getAssets(), "ROBOTO-LIGHT.TTF");
        head1.setTypeface(tf);
        head2.setTypeface(tf);
        head3.setTypeface(tf);
        head3.setText(str_agreement_no);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goD = new Intent(getApplicationContext(), Display_Inspection_details.class);
                startActivity(goD);
                finish();

            }
        });

        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }

    private void message1() {

        final Dialog dialog = new Dialog(NewInspectionActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_dialog);
        //adding text dynamically
        TextView txt_head2 = (TextView) dialog.findViewById(R.id.txt_head2);
        TextView txt_msg = (TextView) dialog.findViewById(R.id.txt_msg);
        txt_head2.setText("Alert Message");
        txt_msg.setText("Unit No Already Exists...");
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
        Intent intent_back = new Intent(getApplicationContext(), Dashboard.class);
        startActivity(intent_back);
        finish();

    }
}
