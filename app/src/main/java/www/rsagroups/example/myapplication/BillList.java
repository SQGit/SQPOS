package www.rsagroups.example.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Admin on 20-02-2016.
 */
public class BillList extends Activity {
TextView head;
    TextView sno,dat,bill,cust,sel_table,itemname,itemprz,qty,total;
    ListView list;
    Button back;
    DbHelper dbh;
    Context context=this;
    String billno;
   //String QRY3 = "SELECT * FROM "+dbh.TABLE_NAME1;
    // String QRY4 = "SELECT b.KEY_ID,b.DATE,b.BILL_NO,b.KEY_FNAME,b.KEY_LNAME,b.QTY,b.TOTAL from" +dbh.TABLE_NAME1 +"inner join" +dbh.TABLE_NAME2 +"a on a.BILL_NO = B.BILLNO where b.BILL_NO=" +listview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.billll);

        head=(TextView) findViewById(R.id.textView7);
        //sno=(TextView) findViewById(R.id.t1);
        dat=(TextView) findViewById(R.id.t2);
        bill=(TextView) findViewById(R.id.t3);
        cust=(TextView) findViewById(R.id.t9);
        sel_table=(TextView) findViewById(R.id.t10);
        itemname=(TextView) findViewById(R.id.t4);
        itemprz=(TextView) findViewById(R.id.t5);
        qty=(TextView) findViewById(R.id.t6);
        total=(TextView) findViewById(R.id.t7);

        back=(Button) findViewById(R.id.back_icon);


        list = (ListView) findViewById(R.id.listViewtab2);

        Typeface tf = Typeface.createFromAsset(getAssets(), "appfont.OTF");

//        sno.setTypeface(tf);
        dat.setTypeface(tf);
        bill.setTypeface(tf);
        cust.setTypeface(tf);
        sel_table.setTypeface(tf);
        itemname.setTypeface(tf);
        itemprz.setTypeface(tf);
        qty.setTypeface(tf);
        total.setTypeface(tf);
        head.setTypeface(tf);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent back=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(back);
            }
        });


        dbh = new DbHelper(context);



        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(BillList.this);

        //billno= sharedPreferences.getString("bill", "");
        billno = getIntent().getStringExtra("bill");


        Log.e("tag","billno"+billno);





                getList();
                //Toast.makeText(getApplicationContext(), "hello hai world", Toast.LENGTH_LONG).show();

               /* Intent move=new Intent(getApplicationContext(),BillList.class);
                startActivity(move);
*/

    }

    private void getList() {

       String QRY3 = "SELECT * FROM bill Where bno = "+ billno;

        Log.e("TAG","good");

        //Toast.makeText(getApplicationContext(), "show array list", Toast.LENGTH_LONG).show();

        ArrayList<Java11> lv2 = new ArrayList<Java11>();
        lv2.clear();


        Cursor c2 = dbh.fetchdata(QRY3);

        if(c2 != null) {
            if (c2.moveToFirst()) {
                do {

                    //Toast.makeText(getApplicationContext(),"jhhjhjhjhjhjhjhjhh",Toast.LENGTH_LONG).show();
                    Java11 jvv = new Java11();

                   /* jvv.set_KEYID1("1");
                    Log.e("Tag", "Rbbbbbbbbb" + jvv.get_KEYID1());*/

                    jvv.set_DATE1(c2.getString(c2.getColumnIndex(dbh.DATE)));
                    Log.e("Tag", "Rcccccccccc" + jvv.get_DATE1());

                    jvv.set_BILLNO1(c2.getString(c2.getColumnIndex(dbh.BILL_NO)));
                    Log.e("Tag", "REEEEEEEEEEE" + jvv.get_BILLNO1());



                    jvv.set_CUSTOMER(c2.getString(c2.getColumnIndex(dbh.CUSTOMER_NAME)));
                    Log.e("Tag", "CUSTOMER NAME" + jvv.get_CUSTOMER());

                    jvv.set_TABLE(c2.getString(c2.getColumnIndex(dbh.SET_TABLE)));

                    jvv.set_ITEMNAME1(c2.getString(c2.getColumnIndex(dbh.KEY_FNAME)));
                    Log.e("Tag", "REEEEEEEEEEE" + jvv.get_ITEMNAME1());

                    jvv.set_ITEMPRICE1(c2.getString(c2.getColumnIndex(dbh.KEY_LNAME)));
                    Log.e("Tag", "Per price" + jvv.get__ITEMPRICE1());

                    jvv.set_QTY1(c2.getString(c2.getColumnIndex(dbh.QTY)));
                    Log.e("Tag", "REEEEEEEEEEE" + jvv.get__QTY1());


                    jvv.set_TOTAL1(c2.getString(c2.getColumnIndex(dbh.TOTAL)));
                    Log.e("Tag", "Rfffffffff" + jvv.get_TOTAL1());

                    lv2.add(jvv);
                }
                while (c2.moveToNext());





            }

        }
        Java22 adapter1 = new Java22(BillList.this,lv2);
        list.setAdapter(adapter1);
    }


}