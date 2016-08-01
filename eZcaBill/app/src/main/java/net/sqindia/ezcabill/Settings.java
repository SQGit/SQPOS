package net.sqindia.ezcabill;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.test.suitebuilder.TestMethod;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by $Krishna on 26-07-2016.
 */
public class Settings extends Activity {

    EditText header,contact;
    TextView settings;
    Spinner spn_currency;
    Typeface tf;
    Button submit;
    LinearLayout back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);

        settings = (TextView) findViewById(R.id.settings);
        header = (EditText) findViewById(R.id.edit_title);
        contact = (EditText) findViewById(R.id.edit_number);
        spn_currency = (Spinner) findViewById(R.id.spin_currency);
        back = (LinearLayout) findViewById(R.id.layout_back);
        submit = (Button) findViewById(R.id.btnsub);

        tf = Typeface.createFromAsset(getApplicationContext().getAssets(),"reg.TTF");

        settings.setTypeface(tf);
        header.setTypeface(tf);
        contact.setTypeface(tf);
        submit.setTypeface(tf);


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(Settings.this,Plan_List.class);
                Log.e("TAG","zxcvbnn");
                startActivity(i);
                finish();
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String bill_title = header.getText().toString().trim();
                String bill_contact = contact.getText().toString().trim();
                String currency = spn_currency.getSelectedItem().toString();

                if (bill_title.equals("")||bill_contact.equals("")||currency.equals("Choose Currency")){
                    Toast.makeText(getApplicationContext(), "Can't Save Blank Values", Toast.LENGTH_LONG).show();
                    return;
                }

                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(Settings.this);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                editor.putString("s1",bill_title);
                editor.putString("s2",bill_contact);
                editor.putString("s3",currency);
                Log.e("TAG","qwer"+currency);
                editor.commit();

                Intent i = new Intent(Settings.this,Plan_List.class);
                startActivity(i);
                finish();
            }
        });


        String[] plants = new String[]{"Choose Currency",
                "Rs.",
                "$",
                "€ ",
                "¥ "

        };


        final List<String> plantsList = new ArrayList<>(Arrays.asList(plants));

        // Initializing an ArrayAdapter
        final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                this,android.R.layout.simple_spinner_item, plantsList) {
            @Override
            public boolean isEnabled(int position) {
                if (position == 0) {
                    // Disable the first item from Spinner
                    // First item will be use for hint
                    return false;
                } else {
                    return true;
                }
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {

                 View view = super.getView(position, convertView, parent);

                TextView tv = (TextView) view;
                tv.setTypeface(tf, 0);
              //  tv.setTextColor(getApplicationContext().getResources().getColor(android.R.color.white));
                return view;
            }

            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;

                tv.setTypeface(tf, 0);

             //   view.setBackgroundColor(Color.BLACK);

                if (position == 0) {
                    // Set the hint text color gray
                   // tv.setTextColor(Color.WHITE);
                } else {
                   // tv.setTextColor(getApplicationContext().getResources().getColor(android.R.color.white));
                }
                return view;
            }
        };

        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn_currency.setAdapter(spinnerArrayAdapter);



        spn_currency.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItemText = (String) parent.getItemAtPosition(position);
                // If user change the default selection
                // First item is disable and it is used for hint
                if (position > 0) {
                    // Notify the selected item text
                    Toast.makeText(getApplicationContext(), "Selected : " + selectedItemText, Toast.LENGTH_SHORT)
                            .show();

                   /* Intent goEntry = new Intent(activity,StaffAddEntry.class);
                    activity.startActivity(goEntry);*/

                    spn_currency.setSelection(position);

                    // activity.finish();
                    // dismiss();

                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    @Override
    public void onBackPressed() {

        Intent i = new Intent(Settings.this,Plan_List.class);
        startActivity(i);
        finish();

        // super.onBackPressed();
    }
}
