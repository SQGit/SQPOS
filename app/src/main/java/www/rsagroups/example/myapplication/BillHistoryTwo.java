package www.rsagroups.example.myapplication;

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
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
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
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Admin on 20-02-2016.
 */
public class BillHistoryTwo extends Activity {


    ListView listview;
    TextView t1,t2,t3,t4,t5,head,copyright;
    Button back,dot,search,btnCalendar,btnCalendar2;
    DbHelper dbh;
    File data,sd;
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

        t1 = (TextView) findViewById(R.id.t1);
        t2 = (TextView) findViewById(R.id.t2);
        t3 = (TextView) findViewById(R.id.t3);
        t4 = (TextView) findViewById(R.id.t4);
        t5 = (TextView)findViewById(R.id.table);

        dot=(Button)findViewById(R.id.dot_icon);
        txtDate = (TextView) findViewById(R.id.startDate);
        txtDate2 = (TextView) findViewById(R.id.endDate);
        btnCalendar = (Button) findViewById(R.id.btnCalendar1);
        btnCalendar2 = (Button) findViewById(R.id.btnCalendar2);
        search=(Button)findViewById(R.id.search);
        copyright=(TextView)findViewById(R.id.textrights);
        head = (TextView) findViewById(R.id.textView7);

        Typeface tf = Typeface.createFromAsset(getAssets(), "appfont.OTF");
        t1.setTypeface(tf);
        t2.setTypeface(tf);
        t3.setTypeface(tf);
        t4.setTypeface(tf);
        t5.setTypeface(tf);

        head.setTypeface(tf);
        txtDate.setTypeface(tf);
        txtDate2.setTypeface(tf);
        search.setTypeface(tf);
        copyright.setTypeface(tf);


        final Calendar c = Calendar.getInstance();
        year  = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day   = c.get(Calendar.DAY_OF_MONTH);

        listview = (ListView) findViewById(R.id.listView);
        back=(Button)findViewById(R.id.back_icon);



        txtDate.setText(new StringBuilder()

                    .append(day).append("-").append(month +1).append("-").append(year).append(" "));

        txtDate2.setText(new StringBuilder()

        .append(day).append("-").append(month +1).append("-").append(year).append(" "));
        btnCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showDialog(DATE_PICKER_ID1);
            }


        });


        btnCalendar2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showDialog(DATE_PICKER_ID2);
            }


        });




        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getfromdate=txtDate.getText().toString();
                gettodate=txtDate2.getText().toString();
                Log.e("tag", "date from" + getfromdate);
                Log.e("tag", "date to" + gettodate);

            String QRY2 = "SELECT * FROM " + dbh.TABLE_NAME2 + " WHERE " + dbh.DATE + " BETWEEN \"" + getfromdate + "\" AND \"" + gettodate + "\"";
            Log.e("tag", "QRY2" + QRY2);


            goList(QRY2);
            }
        });




        dot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                PopupMenu popup = new PopupMenu(getApplicationContext(), dot);
                popup.getMenuInflater().inflate(R.menu.opt_menus, popup.getMenu());

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {

                        switch (item.getItemId()) {
                            case R.id.option1:

                                AlertDialog.Builder u = new AlertDialog.Builder(BillHistoryTwo.this);
                                u.setTitle("Export Data");

                                u.setMessage("Are you sure you want export Db?");
                                u.setPositiveButton(android.R.string.yes,
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                try {
                                                    exportDB();
                                                    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(BillHistoryTwo.this);
                                                    alertBuilder.setTitle("Export DB");
                                                    alertBuilder.setMessage("Do you want to Export Db ");
                                                    alertBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                                                        public void onClick(DialogInterface dialog, int which) {
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

                            default:
                                return true;

                            case R.id.option2:

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

                            case R.id.option3:
                                Intent ii = new Intent(getApplicationContext(), SalesReport.class);
                                startActivity(ii);
                                break;

                        }
                        return true;
                    }
                });

                popup.show();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ii=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(ii);
            }
        });

        dbh = new DbHelper(context);


        goList(QRY1);



    }



    private void exportDB() {


        if (!sd.exists()) {
            sd.mkdirs();
        }


        FileChannel source = null;
        FileChannel destination = null;
        String currentDBPath = "/data/" + "www.rsagroups.example.myapplication" + "/databases/" + "userdata.db";
        String backupDBPath = "habitatdata.db";
        Log.e("tag", "exported file" + backupDBPath);


        File currentDB = new File(data, currentDBPath);
        File backupDB = new File(sd, backupDBPath);





        try {
            source = new FileInputStream(currentDB).getChannel();
            destination = new FileOutputStream(backupDB).getChannel();
            destination.transferFrom(source, 0, source.size());
            source.close();
            destination.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }



    private void goList(String query) {


        ArrayList<Java1> lv1 = new ArrayList<Java1>();
        //lv1.clear();


        Cursor c1 = dbh.fetchdata(query);
        Log.e("tag","<---lv111111111111---->"+c1);


        if(c1 != null) {
            if (c1.moveToFirst()) {
                do {
                    Java1 jv = new Java1();

                    jv.set_BILLNO(c1.getString(c1.getColumnIndex(dbh.BILL_NO)));
                    String s1=c1.getString(c1.getColumnIndex(dbh.BILL_NO));

                    Log.e("tag", "<---ssssssssssssss12345---->" + s1);
                    jv.set_DATE(c1.getString(c1.getColumnIndex(dbh.DATE)));
                    String s = c1.getString(c1.getColumnIndex(dbh.DATE));
                    Log.e("tag", "<---ssssssssssssss12345---->" + s);


                    jv.set_CUSTOMERNAME(c1.getString(c1.getColumnIndex(dbh.CUSTOMER_NAME)));


                    jv.set_TABLE(c1.getString(c1.getColumnIndex(dbh.SET_TABLE)));


                    jv.set_GRANDTOTAL(c1.getString(c1.getColumnIndex(dbh.GRAND_TOTAL)));
                    String me_tot = c1.getString(c1.getColumnIndex(dbh.GRAND_TOTAL));
                    Log.e("tag","total value for all customer"+me_tot);
                    lv1.add(jv);

                }
                while (c1.moveToNext());

                Log.e("tag","<---lv111111111111---->"+lv1);

            }

        }
        Log.e("tag", "<---lv2222222222---->" + lv1);
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

            txtDate.setText(new StringBuilder().append(day).append("-").append(month + 1).append("-").append(year).append(""));

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

            txtDate2.setText(new StringBuilder().append(day).append("-").append(month + 1).append("-").append(year).append(""));


        }
    };
}
