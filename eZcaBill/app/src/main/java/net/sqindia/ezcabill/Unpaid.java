package net.sqindia.ezcabill;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by $Krishna on 03-06-2016.
 */
public class Unpaid extends Activity {

    private static final String SELECT_SQL = "SELECT * FROM persons where payment = 'Unpaid'";

    private SQLiteDatabase db;

    private Cursor c;

    ListView listView;
    TextView txt_id,txt_name,txt_phone,txt_payment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.unpaid);
        openDatabase();


        listView = (ListView) findViewById(R.id.unpaid_listView);
        txt_id = (TextView) findViewById(R.id.t1);
        txt_name = (TextView) findViewById(R.id.t2);
        txt_phone = (TextView) findViewById(R.id.t3);
        txt_payment = (TextView) findViewById(R.id.t5);
        Typeface tf = Typeface.createFromAsset(getApplicationContext().getAssets(),"reg.TTF");
        txt_id.setTypeface(tf);
        txt_name.setTypeface(tf);
        txt_phone.setTypeface(tf);
        txt_payment.setTypeface(tf);

        c = db.rawQuery(SELECT_SQL, null);
        c.moveToFirst();
        showRecords();


    }

    protected void openDatabase() {
        db = openOrCreateDatabase("PersonDB", Context.MODE_PRIVATE, null);
    }

    protected void showRecords() {

        ArrayList<Java1> lv1 = new ArrayList<Java1>();
        lv1.clear();

        if(c != null) {
            if (c.moveToFirst()) {
                do {
                    Java1 jv = new Java1();

                    jv.set_IDTAG(c.getString(c.getColumnIndex("id")));
                    jv.set_NAME(c.getString(c.getColumnIndex("name")));
                    jv.set_NUMBER(c.getString(c.getColumnIndex("phone")));
                    jv.set_PLAN(c.getString(c.getColumnIndex("plan")));
                    jv.set_MAIL(c.getString(c.getColumnIndex("email")));
                    jv.set_PAYMENT(c.getString(c.getColumnIndex("payment")));
                    lv1.add(jv);
                } while (c.moveToNext());
            }
        }
        //c2.close();

        Java5 adapter1 = new Java5(Unpaid.this,lv1);
        listView.setAdapter(adapter1);


       /* String id = c.getString(0);
        String name = c.getString(1);
        String phone = c.getString(2);
        String plan = c.getString(3);
        String email = c.getString(4);
        textViewId.setText(id);
        textViewName.setText(name);
        textViewPhone.setText(phone);
        textViewPlan.setText(plan);
        textViewemail.setText(email);*/

    }
    @Override
    public void onBackPressed() {

        Intent i = new Intent(Unpaid.this,DashBoard.class);
        startActivity(i);
        finish();

        // super.onBackPressed();
    }
}

