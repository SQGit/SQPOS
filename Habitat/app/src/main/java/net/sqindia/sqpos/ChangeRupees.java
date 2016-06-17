package net.sqindia.sqpos;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.Date;

public class ChangeRupees  extends AppCompatActivity {
    Spinner spinner1;
    Button ok;
    String token_footer;
    TextView tv;
    String ss,countryname,country_sym;
    public Integer number;
    Typeface tf;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rupee);

        FontChangeCrawler fontChanger = new FontChangeCrawler(getAssets(), "appfont.OTF");
        fontChanger.replaceFonts((ViewGroup) this.findViewById(android.R.id.content));
        String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
        ss = currentDateTimeString;
        tf = Typeface.createFromAsset(getAssets(), "appfont.OTF");
        SpannableStringBuilder SS = new SpannableStringBuilder("CHANGE CURRENCY");
        SS.setSpan(new CustomTypefaceSpan("CHANGE CURRENCY", tf), 0, SS.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        getSupportActionBar().setTitle(SS);
        final String[] rupee = {" Select Currency","  $","  ₹","  €",""};

        spinner1 = (Spinner) findViewById(R.id.spinner1);
        ok=(Button) findViewById(R.id.button2);
        tv=(TextView)findViewById(R.id.textrights);

        tf = Typeface.createFromAsset(getApplicationContext().getAssets(), "appfont.OTF");
        ok.setTypeface(tf);
        tv.setTypeface(tf);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(ChangeRupees.this);
        token_footer = sharedPreferences.getString("footer", "");

        tv.setText(token_footer);

        final CustomAdapter arrayAdapter = new CustomAdapter(getApplicationContext(), R.layout.spinner_item, rupee) {
            public boolean isEnabled(int position) {
                if (position == 0) {

                    return false;
                } else {
                    return true;
                }
            }

            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv1 = (TextView) view;
                tv1.setTypeface(tf);
                return view;
            }
        };

        spinner1.setAdapter(arrayAdapter);
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                spinner1.getSelectedItem().toString();

                int uu = spinner1.getSelectedItemPosition();
                ss = "Table" + uu;
                Log.e("tag","country"+ss);
                ChangeRupees.this.number = spinner1.getSelectedItemPosition() + 1;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String symbol = spinner1.getSelectedItem().toString();
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(ChangeRupees.this);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("send2",symbol);
                editor.commit();

                Intent inte = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(inte);
            }
        });


    }
}