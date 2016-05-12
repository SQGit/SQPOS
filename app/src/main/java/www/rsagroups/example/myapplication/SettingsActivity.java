package www.rsagroups.example.myapplication;


import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.Date;

public class SettingsActivity extends AppCompatActivity {
    TextView head1,head2,head3,head4,head5,bottom;
    Button back;
    String ss,token_footer,token_mail;
    Typeface tf;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        head1=(TextView)findViewById(R.id.txthead1);
        head2=(TextView)findViewById(R.id.txthead2);
        head3=(TextView)findViewById(R.id.txthead3);
        head4=(TextView)findViewById(R.id.txthead4);
        head5=(TextView)findViewById(R.id.txthead5);
        bottom=(TextView)findViewById(R.id.textrights);

        FontChangeCrawler fontChanger = new FontChangeCrawler(getAssets(), "appfont.OTF");
        fontChanger.replaceFonts((ViewGroup) this.findViewById(android.R.id.content));
        String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
        ss = currentDateTimeString;
        tf = Typeface.createFromAsset(getAssets(), "appfont.OTF");
        SpannableStringBuilder SS = new SpannableStringBuilder("POS SETTINGS");
        SS.setSpan(new CustomTypefaceSpan("POS SETTINGS", tf), 0, SS.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        getSupportActionBar().setTitle(SS);

        Typeface tf = Typeface.createFromAsset(getApplicationContext().getAssets(), "appfont.OTF");
        head1.setTypeface(tf);
        head2.setTypeface(tf);
        head3.setTypeface(tf);
        head4.setTypeface(tf);
        head5.setTypeface(tf);
        bottom.setTypeface(tf);



        head1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ii = new Intent(getApplicationContext(), ChangeRupees.class);
                startActivity(ii);
            }
        });


        head2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showInputDialogFooter();

            }
        });

        head3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInputDialog();
            }
        });


        head4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInputDialogBill();
            }
        });

        head5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInputDialogEmail();
            }
        });

    }

    private void showInputDialogEmail() {


            LayoutInflater layoutInflater = LayoutInflater.from(SettingsActivity.this);
        View promptView = layoutInflater.inflate(R.layout.input_dialogemail, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(SettingsActivity.this);
        alertDialogBuilder.setView(promptView);
        final TextView headmail = (TextView) promptView.findViewById(R.id.txthead);
        final EditText editTextmail = (EditText) promptView.findViewById(R.id.edtmail);
        Typeface tf = Typeface.createFromAsset(getApplicationContext().getAssets(), "appfont.OTF");
        editTextmail.setTypeface(tf);
        headmail.setTypeface(tf);

        // setup a dialog window
        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        String change_mail=editTextmail.getText().toString();
                        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(SettingsActivity.this);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("mail",change_mail);
                        editor.commit();

                        token_mail = sharedPreferences.getString("footer", "");
                        Log.e("tag","content"+token_footer);
                        bottom.setText(token_footer);
                        // head2.setText(token_footer);
                    }
                })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        // create an alert dialog
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();

    }


    private void showInputDialogBill() {
        LayoutInflater layoutInflater = LayoutInflater.from(SettingsActivity.this);
        View promptView = layoutInflater.inflate(R.layout.input_dialogbill, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(SettingsActivity.this);
        alertDialogBuilder.setView(promptView);

        final TextView txt_head = (TextView) promptView.findViewById(R.id.txt_head);
        final EditText edtapp = (EditText) promptView.findViewById(R.id.editText4);
        final EditText edthead = (EditText) promptView.findViewById(R.id.editText5);
        final EditText edtfssai = (EditText) promptView.findViewById(R.id.editText6);
        final EditText edtcontact = (EditText) promptView.findViewById(R.id.editText8);

        Typeface tf = Typeface.createFromAsset(getApplicationContext().getAssets(), "appfont.OTF");
        edtapp.setTypeface(tf);
        edthead.setTypeface(tf);
        edtfssai.setTypeface(tf);
        edtcontact.setTypeface(tf);
        txt_head.setTypeface(tf);
        // setup a dialog window
        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("SAVE", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        String app_name=edtapp.getText().toString();
                        String app_head=edthead.getText().toString();
                        String app_fssai=edtfssai.getText().toString();
                        String app_contact=edtcontact.getText().toString();

                        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(SettingsActivity.this);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("s1",app_name);
                        editor.putString("s2",app_head);
                        editor.putString("s3",app_fssai);
                        editor.putString("s4",app_contact);
                        editor.commit();


                    }
                })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        // create an alert dialog
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();

    }

    private void showInputDialogFooter() {

        LayoutInflater layoutInflater = LayoutInflater.from(SettingsActivity.this);
        View promptView = layoutInflater.inflate(R.layout.input_dialogfooter, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(SettingsActivity.this);
        alertDialogBuilder.setView(promptView);
        final TextView head = (TextView) promptView.findViewById(R.id.textView7);
        final EditText editText = (EditText) promptView.findViewById(R.id.edt_footer);
        Typeface tf = Typeface.createFromAsset(getApplicationContext().getAssets(), "appfont.OTF");
        editText.setTypeface(tf);
        head.setTypeface(tf);

        // setup a dialog window
        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        String change_footer=editText.getText().toString();
                        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(SettingsActivity.this);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("footer",change_footer);
                        editor.commit();

                        token_footer = sharedPreferences.getString("footer", "");
                        Log.e("tag","content"+token_footer);
                        bottom.setText(token_footer);
                       // head2.setText(token_footer);
                    }
                })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        // create an alert dialog
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }




    private void showInputDialog() {

        LayoutInflater layoutInflater = LayoutInflater.from(SettingsActivity.this);
        View promptView = layoutInflater.inflate(R.layout.input_dialog, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(SettingsActivity.this);
        alertDialogBuilder.setView(promptView);

        final TextView head = (TextView) promptView.findViewById(R.id.textView);
        final EditText editText = (EditText) promptView.findViewById(R.id.editText3);
        Typeface tf = Typeface.createFromAsset(getApplicationContext().getAssets(), "appfont.OTF");
        editText.setTypeface(tf);
        head.setTypeface(tf);
        // setup a dialog window
        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        String app_name=editText.getText().toString();
                        //head3.setText(app_name);


                    }
                })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        // create an alert dialog
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }
    }
