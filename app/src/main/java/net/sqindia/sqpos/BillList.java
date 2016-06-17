package net.sqindia.sqpos;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Admin on 20-02-2016.
 */
public class BillList extends AppCompatActivity {
TextView head;
    TextView sno,dat,bill,cust,sel_table,itemname,itemprz,qty,total,copyright;
    ListView list;

    DbHelper dbh;
    Context context=this;
    String billno,token_footer,currency;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.billlist);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        dat=(TextView) findViewById(R.id.t2);
        bill=(TextView) findViewById(R.id.t3);
        cust=(TextView) findViewById(R.id.t9);
        sel_table=(TextView) findViewById(R.id.t10);
        itemname=(TextView) findViewById(R.id.t4);
        itemprz=(TextView) findViewById(R.id.t5);
        qty=(TextView) findViewById(R.id.t6);
        total=(TextView) findViewById(R.id.t7);
        copyright=(TextView)findViewById(R.id.textrights);
        list = (ListView) findViewById(R.id.listViewtab2);

        Typeface tf = Typeface.createFromAsset(getAssets(), "appfont.OTF");
        dat.setTypeface(tf);
        bill.setTypeface(tf);
        cust.setTypeface(tf);
        sel_table.setTypeface(tf);
        itemname.setTypeface(tf);
        itemprz.setTypeface(tf);
        qty.setTypeface(tf);
        total.setTypeface(tf);
        copyright.setTypeface(tf);

        dbh = new DbHelper(context);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(BillList.this);
        billno = getIntent().getStringExtra("bill");
        token_footer = sharedPreferences.getString("footer", "");
        Log.e("tag", "getvalue" + token_footer);
        copyright.setText(token_footer);

        getList();
    }

    private void getList() {
       String QRY3 = "SELECT * FROM bill Where bno = "+ billno;
        Log.e("TAG","good");
        ArrayList<Java11> lv2 = new ArrayList<Java11>();
        lv2.clear();
        Cursor c2 = dbh.fetchdata(QRY3);

        if(c2 != null) {
            if (c2.moveToFirst()) {
                do {


                    Java11 jvv = new Java11();
                    jvv.set_DATE1(c2.getString(c2.getColumnIndex(dbh.DATE)));
                    jvv.set_BILLNO1(c2.getString(c2.getColumnIndex(dbh.BILL_NO)));
                    jvv.set_CUSTOMER(c2.getString(c2.getColumnIndex(dbh.CUSTOMER_NAME)));
                    jvv.set_TABLE(c2.getString(c2.getColumnIndex(dbh.SET_TABLE)));
                    jvv.set_ITEMNAME1(c2.getString(c2.getColumnIndex(dbh.KEY_FNAME)));
                    jvv.set_ITEMPRICE1(c2.getString(c2.getColumnIndex(dbh.KEY_LNAME)));
                    jvv.set_QTY1(c2.getString(c2.getColumnIndex(dbh.QTY)));
                    jvv.set_TOTAL1(c2.getString(c2.getColumnIndex(dbh.TOTAL)));


                    lv2.add(jvv);
                }
                while (c2.moveToNext());
            }

        }
        Java22 adapter1 = new Java22(BillList.this,lv2);
        list.setAdapter(adapter1);
    }


}