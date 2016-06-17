package net.sqindia.sqpos;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Admin on 20-02-2016.
 */
public class BillHistoryTwo extends AppCompatActivity {

    ListView listview;
    TextView t1,t2,t3,t4,t5,head,copyright;
    Button search,btnCalendar,btnCalendar2;
    DbHelper dbh;
    File data,sd;
    String ss,token_footer;
    Typeface tf;
    String getfromdate,gettodate;
    TextView txtDate, txtDate2;

    public DbHelper mHelper;
    private SQLiteDatabase dataBase;

    private int year;
    private int month;
    private int day;

    static final int DATE_PICKER_ID1 = 1111;
    static final int DATE_PICKER_ID2 = 2222;

    Context context=this;
    String QRY1 = "SELECT * FROM "+dbh.TABLE_NAME2+ " ORDER BY "+ dbh.KEY_ID+ " DESC";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bill_history2);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        FontChangeCrawler fontChanger = new FontChangeCrawler(getAssets(), "appfont.OTF");
        fontChanger.replaceFonts((ViewGroup) this.findViewById(android.R.id.content));
        String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
        ss = currentDateTimeString;
        Log.e("TAg", "sssss" + ss);
        tf = Typeface.createFromAsset(getAssets(), "appfont.OTF");
        SpannableStringBuilder SS = new SpannableStringBuilder("BILL HISTORY");
        SS.setSpan(new CustomTypefaceSpan("BILL HISTORY", tf), 0, SS.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        getSupportActionBar().setTitle(SS);

        t1 = (TextView) findViewById(R.id.t1);
        t2 = (TextView) findViewById(R.id.t2);
        t3 = (TextView) findViewById(R.id.t3);
        t4 = (TextView) findViewById(R.id.t4);
        t5 = (TextView)findViewById(R.id.table);
        txtDate = (TextView) findViewById(R.id.startDate);
        txtDate2 = (TextView) findViewById(R.id.endDate);
        btnCalendar = (Button) findViewById(R.id.btnCalendar1);
        btnCalendar2 = (Button) findViewById(R.id.btnCalendar2);
        search=(Button)findViewById(R.id.search);
        copyright=(TextView)findViewById(R.id.textrights);

        Typeface tf = Typeface.createFromAsset(getAssets(), "appfont.OTF");
        t1.setTypeface(tf);
        t2.setTypeface(tf);
        t3.setTypeface(tf);
        t4.setTypeface(tf);
        t5.setTypeface(tf);
        txtDate.setTypeface(tf);
        txtDate2.setTypeface(tf);
        search.setTypeface(tf);
        copyright.setTypeface(tf);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(BillHistoryTwo.this);
        token_footer = sharedPreferences.getString("footer", "");
        Log.e("tag", "getvalue" + token_footer);
        copyright.setText(token_footer);

        final Calendar c = Calendar.getInstance();
        year  = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day   = c.get(Calendar.DAY_OF_MONTH);

        listview = (ListView) findViewById(R.id.listView);

                btnCalendar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        showDialog(DATE_PICKER_ID1);
                    }


                });

                btnCalendar2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v)
                    {


                        showDialog(DATE_PICKER_ID2);
                    }


                });

                search.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v)
                    {

                        getfromdate=txtDate.getText().toString();
                        gettodate=txtDate2.getText().toString();
                        Log.e("tag", "date from" + getfromdate);
                        Log.e("tag", "date to" + gettodate);
                        if(getfromdate.length()>0)
                        {
                            if (gettodate.length() > 0)
                            {
                                String QRY2 = "SELECT * FROM " + dbh.TABLE_NAME2 + " WHERE " + dbh.DATE + " BETWEEN \"" + getfromdate + "\" AND \"" + gettodate + "\"";
                                Log.e("tag", "QRY2" + QRY2);
                                goList(QRY2);
                            }
                            else
                            {
                                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(BillHistoryTwo.this);
                                alertBuilder.setTitle("Message");
                                alertBuilder.setMessage("Select the To date");
                                alertBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                });
                                alertBuilder.show();
                            }

                        }
                        else
                        {
                            AlertDialog.Builder alertBuilder = new AlertDialog.Builder(BillHistoryTwo.this);
                            alertBuilder.setTitle("Message");
                            alertBuilder.setMessage("Select the From date");
                            alertBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();

                                }
                            });
                            alertBuilder.show();

                        }

                    }
                });

                dbh = new DbHelper(context);
                goList(QRY1);
            }



            private void exportDB() {
                sd = new File(Environment.getExternalStorageDirectory() + "/exported/");
                data = Environment.getDataDirectory();
                if (!sd.exists()) {
                    sd.mkdirs();
                }


                FileChannel source = null;
                FileChannel destination = null;
                String currentDBPath = "/data/" + "www.rsagroups.example.myapplication" + "/databases/" + "userdata.db";
                String backupDBPath = "exportfil.db";
                File currentDB = new File(data, currentDBPath);
                File backupDB = new File(sd, backupDBPath);

                try {
                    source = new FileInputStream(currentDB).getChannel();
                    destination = new FileOutputStream(backupDB).getChannel();
                    destination.transferFrom(source, 0, source.size());
                    source.close();
                    destination.close();
                    Toast.makeText(this, "DB Exported!", Toast.LENGTH_LONG).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }



    private void goList(String query) {
        ArrayList<Java1> lv1 = new ArrayList<Java1>();

        Cursor c1 = dbh.fetchdata(query);
        Log.e("tag","<---lv111111111111---->"+c1);
        if(c1 != null) {
            if (c1.moveToFirst()) {
                do {
                    Java1 jv = new Java1();
                    jv.set_BILLNO(c1.getString(c1.getColumnIndex(dbh.BILL_NO)));
                    String s1=c1.getString(c1.getColumnIndex(dbh.BILL_NO));
                    jv.set_DATE(c1.getString(c1.getColumnIndex(dbh.DATE)));
                    String s = c1.getString(c1.getColumnIndex(dbh.DATE));

                    jv.set_CUSTOMERNAME(c1.getString(c1.getColumnIndex(dbh.CUSTOMER_NAME)));

                    jv.set_TABLE(c1.getString(c1.getColumnIndex(dbh.SET_TABLE)));
                    jv.set_GRANDTOTAL(c1.getString(c1.getColumnIndex(dbh.GRAND_TOTAL)));
                    String me_tot = c1.getString(c1.getColumnIndex(dbh.GRAND_TOTAL));
                    lv1.add(jv);

                }
                while (c1.moveToNext());
            }

        }

        Java2 adapter1 = new Java2(BillHistoryTwo.this, lv1);
        listview.setAdapter(adapter1);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Java1 jvv = new Java1();
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(BillHistoryTwo.this);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                String bill = jvv._BILLNO;
                editor.putString("bill", bill);
                editor.commit();

                Intent move = new Intent(getApplicationContext(), BillList.class);
                startActivity(move);
            }

        });

    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch(id){
            case  DATE_PICKER_ID1:
                return new DatePickerDialog(this,  pickerListener1, year, month,day);
            case DATE_PICKER_ID2:
                return new DatePickerDialog(this,  pickerListener2, year, month,day);
        }
        return null;
    }


    private DatePickerDialog.OnDateSetListener pickerListener1 = new DatePickerDialog.OnDateSetListener() {

        // when dialog box is closed, below method will be called.
        @Override
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {

            year  = selectedYear;
            month = selectedMonth;
            day   = selectedDay;
            txtDate.setText(new StringBuilder().append(day).append("/").append(month + 1).append("/").append(year).append(""));
        }
    };


    private DatePickerDialog.OnDateSetListener pickerListener2 = new DatePickerDialog.OnDateSetListener() {

        // when dialog box is closed, below method will be called.
        @Override
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {

            year  = selectedYear;
            month = selectedMonth;
            day   = selectedDay;
            txtDate2.setText(new StringBuilder().append(day).append("/").append(month + 1).append("/").append(year).append(""));
        }
    };


   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
               getMenuInflater().inflate(R.menu.opt_menus, menu);

        for (int i = 0; i < menu.size(); i++) {
            MenuItem item = menu.getItem(i);
            SpannableString spanString = new SpannableString(menu.getItem(i).getTitle().toString());
            int end = spanString.length();
            spanString.setSpan(new CustomTypefaceSpan("", tf), 0, spanString.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            item.setTitle(spanString);
            applyFontToMenuItem(item);
        }
        return true;
    }*/

/*

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.exportdb:

                AlertDialog.Builder u = new AlertDialog.Builder(BillHistoryTwo.this);
                u.setTitle("Export Data");

                u.setMessage("Are you sure you want export Db?");
                u.setPositiveButton(android.R.string.yes,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                try {

                                    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(BillHistoryTwo.this);
                                    alertBuilder.setTitle("Export DB");
                                    alertBuilder.setMessage("Do you want to Export Db ");
                                    alertBuilder.setPositiveButton("yes", new DialogInterface.OnClickListener() {

                                        public void onClick(DialogInterface dialog, int which) {
                                            exportDB();
                                            dialog.cancel();

                                        }
                                    });
                                    alertBuilder.show();
                                } catch (NullPointerException e) {
                                    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(BillHistoryTwo.this);
                                    alertBuilder.setTitle("No Data Available in Database");
                                    alertBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.cancel();

                                        }
                                    });
                                    alertBuilder.show();
                                }
                            }

                        });
                u.setNegativeButton(android.R.string.no,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        });
                u.show();


                return true;

            case R.id.deletedb:

                AlertDialog.Builder d = new AlertDialog.Builder(BillHistoryTwo.this);
                d.setTitle("Clear DataBase");

                d.setMessage("Are you sure you want to delete your databse ?");
                d.setPositiveButton(android.R.string.yes,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                //deleteDb();
                                DbHelper dbh = new DbHelper(getApplicationContext());
                                dbh.delete_item(dbh);
                                goList(QRY1);


                                Toast.makeText(getApplicationContext(),
                                        "DataBase Cleared Successfully",
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
                d.setNegativeButton(android.R.string.no,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                d.show();

                return true;

            case R.id.salesrt:

                Intent ii = new Intent(getApplicationContext(), SalesReport.class);
                startActivity(ii);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
*/

   /* private void applyFontToMenuItem(MenuItem mi) {
        SpannableString mNewTitle = new SpannableString(mi.getTitle());
        mNewTitle.setSpan(new CustomTypefaceSpan("" , tf), 0 , mNewTitle.length(),  Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        mi.setTitle(mNewTitle);
    }
*/

}
