package net.sqindia.sqpos;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
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

public class SalesReport extends AppCompatActivity {

    Button btnCalendar, submit, print;
    TextView txtDate, date, report, bottom, copyright, report_amt;
    String getdate, device_num, device_id, gtot;
    DbHelper dbh;
    String currentDateTime = DateFormat.getDateTimeInstance().format(new Date());
    Context context = this;
    private BluetoothPrinter mBtp = BluetoothPrinterMain.mBtp;
    private int year;
    private int month;
    private int day;
    String ss, token_footer;
    Typeface tf;
    String b1,b2,b3,b4,b5;
    String dt,chk_val,currency;
    Date cal = (Date) Calendar.getInstance().getTime();
    static final int DATE_PICKER_ID1 = 1111;





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {


        getMenuInflater().inflate(R.menu.bluetooth_printer_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_clear_device:


                AlertDialog.Builder d = new AlertDialog.Builder(SalesReport.this);
                d.setTitle("NGX Bluetooth Printer");
                // d.setIcon(R.drawable.ic_launcher);
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
                                // do nothing
                            }
                        });
                d.show();
                return true;
            case R.id.action_connect_device:
                // show a dialog to select from the list of available printers
                mBtp.showDeviceList(this);
                return true;
            case R.id.action_unpair_device:
                AlertDialog.Builder u = new AlertDialog.Builder(SalesReport.this);
                u.setTitle("Bluetooth Printer unpair");
                // d.setIcon(R.drawable.ic_launcher);
                u.setMessage("Are you sure you want to unpair all Bluetooth printers ?");
                u.setPositiveButton(android.R.string.yes,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                if (mBtp.unPairBluetoothPrinters()) {
                                    Toast.makeText(
                                            getApplicationContext(),
                                            "All NGX Bluetooth printer(s) unpaired",
                                            Toast.LENGTH_SHORT).show();
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
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.salesreport_activity);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        submit = (Button) findViewById(R.id.btn_submit);
        btnCalendar = (Button) findViewById(R.id.btnCalendar1);
        print = (Button) findViewById(R.id.print);
        txtDate = (TextView) findViewById(R.id.startDate);
        date = (TextView) findViewById(R.id.date);
        report = (TextView) findViewById(R.id.tot_result);
        report_amt = (TextView) findViewById(R.id.tot_amt);
        bottom = (TextView) findViewById(R.id.textrights);
        copyright = (TextView) findViewById(R.id.textrights);

        FontChangeCrawler fontChanger = new FontChangeCrawler(getAssets(), "appfont.OTF");
        fontChanger.replaceFonts((ViewGroup) this.findViewById(android.R.id.content));
        String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
        ss = currentDateTimeString;
        Log.e("TAg", "sssss" + ss);
        tf = Typeface.createFromAsset(getAssets(), "appfont.OTF");
        SpannableStringBuilder SS = new SpannableStringBuilder("SALES REPORT");
        SS.setSpan(new CustomTypefaceSpan("SALES REPORT", tf), 0, SS.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        getSupportActionBar().setTitle(SS);

        Typeface tf = Typeface.createFromAsset(getAssets(), "appfont.OTF");
        txtDate.setTypeface(tf);
        date.setTypeface(tf);
        submit.setTypeface(tf);
        report.setTypeface(tf);
        copyright.setTypeface(tf);
        report_amt.setTypeface(tf);

        final Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);
        dt = cal.toLocaleString();
        date.setText(dt.toString());

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(SalesReport.this);
        token_footer = sharedPreferences.getString("footer", "");
        currency = sharedPreferences.getString("send2", "");

        Log.e("tag", "getvalue" + token_footer);
        bottom.setText(token_footer);
        dbh = new DbHelper(context);

        btnCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(DATE_PICKER_ID1);
            }
        });


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                chk_val=report_amt.getText().toString();
                getdate = txtDate.getText().toString();
                date.setText(getdate + "\n REPORT");
                String QRY = "SELECT sum(gtot) FROM " + dbh.TABLE_NAME2 + " WHERE " + dbh.DATE + " = \"" + getdate + "\"";
                Cursor c1 = dbh.fetchdata4(QRY);

                 if (c1 != null) {
                    if (c1.moveToFirst()) {
                        do {
                            gtot = c1.getString(c1.getColumnIndex("sum(gtot)"));
                            Log.e("tag","total"+gtot);
                            String s=gtot;
                            if(s==null)
                            {
                                report_amt.setText("Rs.0");
                            }
                            else
                            {
                                report.setText("TODAY SALE IS");
                                report_amt.setText(currency+gtot);
                                Log.e("tag","ddd"+currency);
                            }
                        }
                        while (c1.moveToNext());
                    }
                }
            }

        });


        print.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                b1 = sharedPreferences.getString("s1", "");
                b2 = sharedPreferences.getString("s2", "");
                b3 = sharedPreferences.getString("s3", "");
                b4 = sharedPreferences.getString("s4", "");

                String bill_amt=txtDate.getText().toString();
            if(bill_amt.equals(""))
            {
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(SalesReport.this);
                alertBuilder.setTitle("Invalid Data");
                alertBuilder.setMessage("Please Select Date");
                alertBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();

                    }
                });
                alertBuilder.show();
            }else
            {
                printstmt();
            }
            }
        });
    }

    private void goList(String qq) {

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

        switch (id) {
            case DATE_PICKER_ID1:
                return new DatePickerDialog(this, pickerListener1, year, month, day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener pickerListener1 = new DatePickerDialog.OnDateSetListener() {

        // when dialog box is closed, below method will be called.
        @Override
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {

            year = selectedYear;
            month = selectedMonth;
            day = selectedDay;
            txtDate.setText(new StringBuilder().append(day).append("/").append(month + 1).append("/").append(year).append(""));

        }
    };


    //*****************print format***********************
    public void printstmt() {


        TelephonyManager tm = (TelephonyManager) getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
        String mPhoneNumber = tm.getLine1Number();
        String mDeviceId = tm.getDeviceId();
        device_num = mPhoneNumber;
        Log.e("TAG", "phone number" + mPhoneNumber);
        device_id = mDeviceId;
        Log.e("TAG", "phone id" + mDeviceId);
        mBtp.setPrintFontStyle(BtpCommands.FONT_STYLE_REGULAR);
        mBtp.setPrintFontSize(BtpCommands.FONT_SIZE_NORMAL);
        mBtp.printTextLine("                  "+b1);
        mBtp.printTextLine("          "+b2);
        mBtp.printTextLine("FSSAI:"+b3);
        mBtp.printLineFeed();
        mBtp.printTextLine(currentDateTime);
        mBtp.printTextLine("------------------------------------------");
        mBtp.printTextLine("             TODAY SALES REPORT");
        mBtp.printTextLine("------------------------------------------");
        mBtp.printLineFeed();
        mBtp.printTextLine("          Today Sale Is"+currency+gtot);
        mBtp.printTextLine("------------------------------------------");
        mBtp.printTextLine("    Designed & Developed by SQIndia.net");
        mBtp.printTextLine("           Contact:"+b4);
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