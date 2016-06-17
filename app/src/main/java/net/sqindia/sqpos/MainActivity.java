package net.sqindia.sqpos;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.ngx.BluetoothPrinter;

import java.io.File;

        public class MainActivity extends AppCompatActivity {
        Button addproduct, addbill, billhistory, printerconfig, action_settings;
        File sd, data;
        String token_footer;
        TextView dashboard, reserved,mTitle;
        Typeface tf;
        private DbHelper mHelper;
        public static BluetoothPrinter mBtp = BluetoothPrinter.INSTANCE;
        BluetoothPrinterMain bmp = new BluetoothPrinterMain();

            @Override
            protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

                getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                        WindowManager.LayoutParams.FLAG_FULLSCREEN);

                tf = Typeface.createFromAsset(getAssets(), "appfont.OTF");
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            FontsOverride.setDefaultFont(this, "DEFAULT", "appfont.OTF");
            FontsOverride.setDefaultFont(this, "MONOSPACE", "appfont.OTF");
            FontsOverride.setDefaultFont(this, "SERIF", "appfont.OTF");
            FontsOverride.setDefaultFont(this, "SANS_SERIF", "appfont.OTF");

            //*******find view
            reserved = (TextView) findViewById(R.id.textrights);
            mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
            addproduct = (Button) findViewById(R.id.add_product);
            addbill = (Button) findViewById(R.id.add_bill);
            billhistory = (Button) findViewById(R.id.bill_history);
            printerconfig = (Button) findViewById(R.id.printer_config);
            action_settings = (Button) findViewById(R.id.action_settings);
                mHelper = new DbHelper(this);

            //*******using shared preference
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
            token_footer = sharedPreferences.getString("footer", "");
            Log.e("tag", "getvalue" + token_footer);
            reserved.setText(token_footer);



                addproduct.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                Intent i1 = new Intent(getApplicationContext(), DisplayActivity.class);
                        startActivity(i1);
                    }
                });


                addbill.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i2 = new Intent(getApplicationContext(), Order.class);
                        startActivity(i2);
                    }
                });


                billhistory.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i3 = new Intent(getApplicationContext(), BillHistoryTwo.class);
                        startActivity(i3);
                    }
                });


                printerconfig.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i4 = new Intent(getApplicationContext(), BluetoothPrinterMain.class);
                        startActivity(i4);
                    }
                });
            }

                private void applyFontToMenuItem(MenuItem mi) {
                    Typeface font = Typeface.createFromAsset(getAssets(), "appfont.OTF");
                    SpannableString mNewTitle = new SpannableString(mi.getTitle());
                    mNewTitle.setSpan(new CustomTypefaceSpan("" , font), 0 , mNewTitle.length(),  Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                    mi.setTitle(mNewTitle);
                }


    @Override
        protected void onRestart() {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
            super.onRestart();
        }

            @Override
            public boolean onCreateOptionsMenu(Menu menu) {

                getMenuInflater().inflate(R.menu.opt_menu3, menu);

                for (int i = 0; i < menu.size(); i++) {
                    MenuItem item = menu.getItem(i);
                    SpannableString spanString = new SpannableString(menu.getItem(i).getTitle().toString());
                    int end = spanString.length();
                    spanString.setSpan(new CustomTypefaceSpan("", tf), 0, spanString.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                    item.setTitle(spanString);
                    applyFontToMenuItem(item);
                }
                return true;
            }

            @Override
            public boolean onOptionsItemSelected(MenuItem item) {
                int id = item.getItemId();
                switch (id) {
                    case R.id.action_settings:
                        Intent ii = new Intent(getApplicationContext(), SettingsActivity.class);
                        startActivity(ii);
                        return true;
                }
                return super.onOptionsItemSelected(item);
            }

            @Override
            public void onBackPressed() {
                final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                builder.setMessage("Do you want to exit the Application?")
                        .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent i1 = new Intent(Intent.ACTION_MAIN);
                                i1.setAction(Intent.ACTION_MAIN);
                                i1.addCategory(Intent.CATEGORY_HOME);
                                i1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                i1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                i1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(i1);
                                finish();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {


                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                            }
                        });
                builder.create();
                builder.show();

            }
        }