package net.sqindia.ezcabill;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by $Krishna on 28-05-2016.
 */
public class Sales_Report extends AppCompatActivity {

    SQLiteDatabase db;
    String ss;

    LinearLayout iback;

    Button submit;
    String currentDateTime = DateFormat.getDateTimeInstance().format(new Date());
    private int year;
    private int month;
    private int day;
    TextView txtDate,report,report_amt;
    ListView listView;
Button btnn;

    String b3;

    private Cursor c;

    Java6 adapter1;

    TextView txt_report,txt_id,txt_name,txt_phone,txt_plan,txt_email,txt_payment,txt_currency;

    String dt;
    Date cal = (Date) Calendar.getInstance().getTime();
    static final int DATE_PICKER_ID1 = 1111;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sales_report);
        //createDatabase();


        openDatabase();



        btnn = (Button) findViewById(R.id.btncalender);
        txtDate = (TextView) findViewById(R.id.tv_date);
        submit = (Button) findViewById(R.id.btnsubmit);
        listView = (ListView) findViewById(R.id.slsrpt_listView);
        report = (TextView) findViewById(R.id.salesrpt);
        report_amt = (TextView) findViewById(R.id.todayreport);
        txt_report = (TextView) findViewById(R.id.text_report);
        txt_id = (TextView) findViewById(R.id.t1);
        txt_name = (TextView) findViewById(R.id.t2);
        txt_phone = (TextView) findViewById(R.id.t3);
        txt_email = (TextView) findViewById(R.id.t4);
        txt_plan = (TextView) findViewById(R.id.plan);
        txt_payment = (TextView) findViewById(R.id.t5);
        txt_currency = (TextView) findViewById(R.id.currency);

        // Back Button //
        iback = (LinearLayout) findViewById(R.id.layout_back);

        Typeface tf = Typeface.createFromAsset(getApplicationContext().getAssets(),"reg.TTF");
        txt_report.setTypeface(tf);
        txtDate.setTypeface(tf);
        submit.setTypeface(tf);
        report.setTypeface(tf);
        report_amt.setTypeface(tf);
        txt_id.setTypeface(tf);
        txt_name.setTypeface(tf);
        txt_phone.setTypeface(tf);
        txt_email.setTypeface(tf);
        txt_plan.setTypeface(tf);
        txt_payment.setTypeface(tf);
        txt_currency.setTypeface(tf);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        b3 = sharedPreferences.getString("s3", "");
        Log.e("tag", "billone" + b3);

        txt_currency.setText(b3);
        Log.e("tag", "symbol " + txt_currency);

        iback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Sales_Report.this,DashBoard.class);
                startActivity(i);
                finish();
            }
        });

        //Another method to get current and Time //
        String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
        ss = currentDateTimeString;
        Log.e("TAg", "sssss" + ss);


//Getting current date//
        final Calendar c1 = Calendar.getInstance();
        year = c1.get(Calendar.YEAR);
        month = c1.get(Calendar.MONTH);
        day = c1.get(Calendar.DAY_OF_MONTH);
       // dt = cal.toLocaleString();
      //  txtDate.setText(dt.toString());


//Showing calender//
btnn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {

        showDialog(DATE_PICKER_ID1);
    }
});





//Selecting particular date in calender//
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                report_amt.setText("");
                String date = txtDate.getText().toString();

                Log.e("TAg", "ddfds" + date);

                final String SELECT_SQL = "SELECT * FROM persons where billing_date = \"" + date + "\"";

                c = db.rawQuery(SELECT_SQL, null);
                Log.e("TAg", "selgdih" + SELECT_SQL);

                c.moveToFirst();
                showRecords();

            }
        });

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



    protected void openDatabase() {
        db = openOrCreateDatabase("PersonDB", Context.MODE_PRIVATE, null);
    }


//---Showing the listview of money collected members ----///
    protected void showRecords() {

        ArrayList<Java1> lv1 = new ArrayList<Java1>();
        lv1.clear();


        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    Java1 jv = new Java1();

                    jv.set_IDTAG(c.getString(c.getColumnIndex("id")));
                    jv.set_NAME(c.getString(c.getColumnIndex("name")));
                    jv.set_NUMBER(c.getString(c.getColumnIndex("phone")));
                    jv.set_PLAN(c.getString(c.getColumnIndex("plan")));
                    jv.set_MAIL(c.getString(c.getColumnIndex("email")));
                    jv.set_PAYMENT(c.getString(c.getColumnIndex("payment")));
                    jv.set_GRANDTOTAL(c.getInt(c.getColumnIndex("amount")));
                    lv1.add(jv);
                } while (c.moveToNext());
            }
        }
        //c2.close();
//Method calculating total amount collected on that day //
       int[] asd = new int[lv1.size()];
        int k=0;
        Log.d("tag",""+lv1.size());

        for (int i =0; i<lv1.size();i++){
            Java1 java1 = lv1.get(i);
            k = k+java1.get_GRANDTOTAL();
            Log.d("tag",""+k);
            report_amt.setText(Integer.toString(k));
        }


        adapter1 = new Java6(Sales_Report.this, lv1);
        listView.setAdapter(adapter1);
    }

    @Override
    public void onBackPressed() {

        Intent i = new Intent(Sales_Report.this,DashBoard.class);
        startActivity(i);
        finish();

        // super.onBackPressed();
    }

    }
