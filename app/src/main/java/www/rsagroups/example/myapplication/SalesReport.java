package www.rsagroups.example.myapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.ngx.BluetoothPrinter;
import com.ngx.BtpCommands;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by RSA on 4/18/2016.
 */
public class SalesReport extends Activity {

    Button back,btnCalendar,submit,print, dot;
    TextView txtDate,date,head,report,bottom,copyright;
    String getdate,device_num, device_id,gtot;


    DbHelper dbh;
    String currentDateTime = DateFormat.getDateTimeInstance().format(new Date());
    Context context = this;
    private BluetoothPrinter mBtp = BluetoothPrinterMain.mBtp;

    private int year;
    private int month;
    private int day;

    static final int DATE_PICKER_ID1 = 1111;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.salesreport_activity);


        back=(Button)findViewById(R.id.back_icon);
        submit=(Button)findViewById(R.id.btn_submit);
        btnCalendar = (Button) findViewById(R.id.btnCalendar1);
        print = (Button) findViewById(R.id.print);
        txtDate = (TextView) findViewById(R.id.startDate);
        date = (TextView) findViewById(R.id.date);
        head = (TextView) findViewById(R.id.textView7);
        report = (TextView) findViewById(R.id.tot_result);
        dot = (Button) findViewById(R.id.dot_icon);
        bottom=(TextView)findViewById(R.id.textrights);
        copyright=(TextView)findViewById(R.id.textrights);

        Typeface tf = Typeface.createFromAsset(getAssets(), "appfont.OTF");
        txtDate.setTypeface(tf);
        date.setTypeface(tf);
        head.setTypeface(tf);
        submit.setTypeface(tf);
        report.setTypeface(tf);
        copyright.setTypeface(tf);

        final Calendar c = Calendar.getInstance();
        year  = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day   = c.get(Calendar.DAY_OF_MONTH);


        dbh = new DbHelper(context);



        txtDate.setText(new StringBuilder()

                .append(day).append("/").append(month +1).append("/").append(year).append(""));


        btnCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showDialog(DATE_PICKER_ID1);
            }


        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ii = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(ii);
            }
        });




        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                getdate = txtDate.getText().toString();
                Log.e("tag", "date" + getdate);

                date.setText(getdate + "\n REPORT");


                Log.e("tag", "helo one");

                Log.e("tag", "helo two");

                Cursor c1 = dbh.me_name3(getdate);

                Log.e("tag", "helo four");

                if(c1 != null) {
                    if (c1.moveToFirst()) {
                        do {


                            gtot=c1.getString(c1.getColumnIndex("SUM("+dbh.GRAND_TOTAL+")"));

                            Log.e("tag", "<---lv11---->" + gtot);
                          report.setText("TODAY SALE IS \n Rs."+gtot);

                        }
                        while (c1.moveToNext());


                    }

                }





            }

        });



        print.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                printstmt();
            }
        });




        dot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                PopupMenu popup = new PopupMenu(getApplicationContext(), dot);
                popup.getMenuInflater().inflate(R.menu.opt_menu, popup.getMenu());

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {

                        switch (item.getItemId()) {
                            case R.id.item1:

                                AlertDialog.Builder d = new AlertDialog.Builder(SalesReport.this);
                                d.setTitle("NGX Bluetooth Printer");

                                d.setMessage("Are you sure you want to delete your preferred Bluetooth printer ?");
                                d.setPositiveButton(android.R.string.yes,
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                mBtp.clearPreferredPrinter();
                                                Toast.makeText(getApplicationContext(),
                                                        "Preferred Bluetooth printer cleared",
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
                            case R.id.item2:


                                mBtp.showDeviceList(SalesReport.this);

                                return true;
                            case R.id.item3:

                                AlertDialog.Builder u = new AlertDialog.Builder(SalesReport.this);
                                u.setTitle("Bluetooth Printer unpair");
                                // d.setIcon(R.drawable.ic_launcher);
                                u.setMessage("Are you sure you want to unpair all Bluetooth printers ?");
                                u.setPositiveButton(android.R.string.yes,
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                mBtp.unPairBluetoothPrinters();
                                                Toast.makeText(getApplicationContext(),
                                                        "Preferred Bluetooth printer cleared",
                                                        Toast.LENGTH_SHORT).show();
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
                        }

                    }
                });

                popup.show();
            }
        });
    }

    private void goList(String qq) {


        Log.e("tag", "helo three");
        Cursor cursor = dbh.fetchdata3(qq);
        Log.e("tag", "helo four");
        Log.e("tag", "<---lv111111111111---->" + cursor);


        if (cursor != null) {

            if (cursor.moveToFirst()) {
                do {
                    Java1 jv = new Java1();


                    jv.set_GRANDTOTAL(cursor.getString(cursor.getColumnIndex(dbh.GRAND_TOTAL)));
                    String me_tot = cursor.getString(cursor.getColumnIndex(dbh.GRAND_TOTAL));
                    Log.e("tag", "total value for all customer" + me_tot);


                }
                while (cursor.moveToNext());



            }

        }
    }


    @Override
    protected Dialog onCreateDialog(int id) {


        switch(id){
            case  DATE_PICKER_ID1:
                return new DatePickerDialog(this,  pickerListener1, year, month,day);

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










    //*****************print format***********************
    public void printstmt()
    {



        Toast.makeText(getApplicationContext(), "Printing..", Toast.LENGTH_LONG).show();
        TelephonyManager tm = (TelephonyManager) getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
        String mPhoneNumber = tm.getLine1Number();
        String mDeviceId = tm.getDeviceId();
        device_num = mPhoneNumber;
        Log.e("TAG", "phone number" + mPhoneNumber);
        device_id = mDeviceId;
        Log.e("TAG", "phone id" + mDeviceId);
        // mBtp.showDeviceList(getActivity());
        mBtp.setPrintFontStyle(BtpCommands.FONT_STYLE_REGULAR);
        mBtp.setPrintFontSize(BtpCommands.FONT_SIZE_NORMAL);
        mBtp.printTextLine("                  HABITAT");
        mBtp.printTextLine("          Mahindra World City Canopy");
        mBtp.printTextLine("FSSAI:12415008001811");
        mBtp.printLineFeed();

        mBtp.printTextLine(currentDateTime);
        //mBtp.printTextLine("time:"+time);
        mBtp.printTextLine("------------------------------------------");
        mBtp.printTextLine("             TODAY SALES REPORT");
        mBtp.printTextLine("------------------------------------------");
        mBtp.printLineFeed();
        mBtp.printTextLine("          Today Sale Is Rs.17750");
        mBtp.printTextLine("------------------------------------------");
        mBtp.printTextLine("    Designed & Developed by SQIndia.net");
        mBtp.printTextLine("           Contact:91 8526571169");
        mBtp.printLineFeed();
        mBtp.printTextLine("Bill received from:" + device_id);
        mBtp.printLineFeed();
        mBtp.printTextLine("****************************************");
        mBtp.printLineFeed();


    }



    private String[] getArray(ArrayList<BluetoothDevice> data) {
        String[] list = new String[0];

        if (data == null) return list;

        int size = data.size();
        list = new String[size];

        for (int i = 0; i < size; i++) {
            list[i] = data.get(i).getName();
        }

        return list;
    }


}