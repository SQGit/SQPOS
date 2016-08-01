package net.sqindia.ezcabill;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by $Krishna on 30-05-2016.
 */
public class Bill_Payment extends Activity {

    ListView listview;
    private static final String SELECT_SQL = "SELECT * FROM persons";
    private static final String SELECT_SQL1 = "persons";

    private SQLiteDatabase db;
    private SimpleCursorAdapter dataAdapter;


    private Cursor c;
    ImageButton close;
    LinearLayout ib;
    EditText edit_name;
    TextView txt_billpayment,txt_copyrights;
     Java3 adapter1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bill_payment);
        //*****Opening Listview of Every Members of limited data********//
        openDatabase();

        listview = (ListView) findViewById(R.id.bill_listView);
        ib = (LinearLayout) findViewById(R.id.layout_back);
        close = (ImageButton) findViewById(R.id.imageButton1);
        edit_name = (EditText) findViewById(R.id.input_name);
        txt_billpayment = (TextView) findViewById(R.id.bilpaymnt);

        Typeface tf = Typeface.createFromAsset(getApplicationContext().getAssets(), "reg.TTF");

        edit_name.setTypeface(tf);
        txt_billpayment.setTypeface(tf);

/*
        listview.post(new Runnable() {

            @Override
            public void run() {
                listview.setSelected(true);
            //    listview.setBackgroundResource(R.drawable.bbil);
                listview.getChildAt(0).setBackgroundResource(R.drawable.bbil);
            }
        });*/


        c = db.rawQuery(SELECT_SQL, null);
        c.moveToFirst();
        showRecords();

        //Back button
        ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Bill_Payment.this,DashBoard.class);
                startActivity(i);
                finish();
            }
        });

        //Closing filter textview //
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit_name.setText("");
                //     listview.setVisibility(v.GONE);

            }
        });


        edit_name.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                Log.d("tag","<---code---->"+s);
                //adapter1.getFilter().filter(s.toString());

                fetchCountriesByName(s.toString());
            }
        });

       /* dataAdapter.setFilterQueryProvider(new FilterQueryProvider() {
            public Cursor runQuery(CharSequence constraint) {
                return fetchCountriesByName(constraint.toString());
            }
        });*/

//Calling Particular member in listview and viewing seperately//
listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


        TextView t1 = (TextView) view.findViewById(R.id.b);
        TextView t2 = (TextView) view.findViewById(R.id.textView2);
        TextView t3 = (TextView) view.findViewById(R.id.textView3);
        TextView t4 = (TextView) view.findViewById(R.id.textView4);
        TextView t5 = (TextView) view.findViewById(R.id.textView5);
        String id = t1.getText().toString();
        Log.d("tag",id);
        final String SELECT_SQL = "SELECT * FROM persons where id = "+id;

        c = db.rawQuery(SELECT_SQL, null);
        c.moveToFirst();
        if (c.moveToFirst())
        {
            String s = c.getString(c.getColumnIndex("id"));
            String s1 = c.getString(c.getColumnIndex("name"));
            String s2 = c.getString(c.getColumnIndex("phone"));
            String s3 = c.getString(c.getColumnIndex("email"));
            String s4 = c.getString(c.getColumnIndex("plan"));
            String s5 = c.getString(c.getColumnIndex("address"));
            String s6 = c.getString(c.getColumnIndex("payment"));
            String s7 = c.getString(c.getColumnIndex("amount"));

            Intent in =new Intent(getApplicationContext(),BluetoothPrinterMain.class);
           //finish();
            in.putExtra("id",s);
            in.putExtra("name",s1);
            in.putExtra("phone",s2);
            in.putExtra("email",s3);
            in.putExtra("plan",s4);
            in.putExtra("address",s5);
            in.putExtra("payment",s6);
            in.putExtra("amount",s7);
            startActivity(in);
            Log.d("tag", s);
            Log.d("tag",s1);
            Log.d("tag",s2);
        }
        c.close();
    }
});

    }


    protected void openDatabase() {
        db = openOrCreateDatabase("PersonDB", Context.MODE_PRIVATE, null);
    }

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
                    //   jv.set_PAYMENT(c.getString(c.getColumnIndex("payment")));
                    lv1.add(jv);
                } while (c.moveToNext());
            }
        }
        //c2.close();

       adapter1 = new Java3(Bill_Payment.this, lv1);
        listview.setAdapter(adapter1);


    }

    //----method of filtering in listview by Phone numbers ---------//
    public Cursor fetchCountriesByName(String inputText) throws SQLException {
        if (inputText == null  ||  inputText.length () == 0)  {
            c = db.query(SELECT_SQL1, new String[] {"id",
                            "name", "phone", "plan", "email"},
                    null, null, null, null, null);

            showRecords();

        }
        else {
            c = db.query(true, SELECT_SQL1, new String[] {"id",
                            "name", "phone", "plan", "email"},
                    "phone" + " like '%" + inputText + "%'", null,
                    null, null, null, null);
            showRecords();

        }
        if (c != null) {
            c.moveToFirst();
        }
        return c;

    }


    @Override
    public void onBackPressed() {

        Intent i = new Intent(Bill_Payment.this,DashBoard.class);
        startActivity(i);
        finish();

        // super.onBackPressed();
    }

}

