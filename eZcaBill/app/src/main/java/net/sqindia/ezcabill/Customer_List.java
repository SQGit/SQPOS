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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by $Krishna on 30-05-2016.
 */
public class Customer_List extends Activity {

    ListView listview;
    private ImageButton close;
    LinearLayout ib;
    private Button btnDelete;

    private static final String SELECT_SQL = "SELECT * FROM persons";
    private static final String SELECT_SQL1 = "persons";

    private SQLiteDatabase db;

    private Cursor c;
    EditText edit_number;
    TextView txt_customerlist,txt_copyrights;
    TextView txtid,txtname,txtphone,txtplan,txtemail,txtpayment;
    Java2 adapter1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_list);
        openDatabase();


////////$$$$$$$------------Similar to Bill Payment Class-----------------$$$$$$$$$$$//////////


        edit_number = (EditText) findViewById(R.id.input_name);


        ib = (LinearLayout) findViewById(R.id.layout_back);
        close = (ImageButton) findViewById(R.id.imageButton1);
        txt_customerlist = (TextView) findViewById(R.id.customer);
        listview = (ListView) findViewById(R.id.customer_listView);
        txtid = (TextView) findViewById(R.id.t1);
        txtname = (TextView) findViewById(R.id.t2);
        txtphone = (TextView) findViewById(R.id.t3);
        txtplan = (TextView) findViewById(R.id.plan);
        txtemail = (TextView) findViewById(R.id.t4);
        txtpayment = (TextView) findViewById(R.id.t5);

        Typeface tf = Typeface.createFromAsset(getApplicationContext().getAssets(), "reg.TTF");

        edit_number.setTypeface(tf);
        txt_customerlist.setTypeface(tf);
        txtid.setTypeface(tf);
        txtname.setTypeface(tf);
        txtphone.setTypeface(tf);
        txtplan.setTypeface(tf);
        txtemail.setTypeface(tf);
        txtpayment.setTypeface(tf);

        c = db.rawQuery(SELECT_SQL, null);
        c.moveToFirst();
        showRecords();
        ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Customer_List.this,DashBoard.class);
                startActivity(i);
                finish();
            }
        });


        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit_number.setText("");
                //     listview.setVisibility(v.GONE);

            }
        });



        edit_number.addTextChangedListener(new TextWatcher() {

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





        listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {


                TextView t1 = (TextView) view.findViewById(R.id.textView1);
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

                    Intent in =new Intent(getApplicationContext(),Edit_Customer.class);
                    in.putExtra("id",s);
                    in.putExtra("name",s1);
                    in.putExtra("phone",s2);
                    in.putExtra("email",s3);
                    in.putExtra("plan",s4);
                    in.putExtra("address",s5);
                    in.putExtra("payment",s6);
                    in.putExtra("amount",s7);
                    startActivity(in);
                    finish();
                    Log.d("tag", s);
                    Log.d("tag",s1);
                    Log.d("tag",s2);

                }
                c.close();
                return false;
            }
        });

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

        adapter1 = new Java2(Customer_List.this,lv1);
        listview.setAdapter(adapter1);


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

    public Cursor fetchCountriesByName(String inputText) throws SQLException {
        if (inputText == null  ||  inputText.length () == 0)  {
            c = db.query(SELECT_SQL1, new String[] {"id",
                            "name", "phone", "plan", "email","payment"},
                    null, null, null, null, null,null);

            showRecords();

        }
        else {
            c = db.query(true, SELECT_SQL1, new String[] {"id",
                            "name", "phone", "plan", "email","payment"},
                    "phone" + " like '%" + inputText + "%'", null,
                    null, null, null, null,null);
            showRecords();

        }
        if (c != null) {
            c.moveToFirst();
        }
        return c;

    }

    @Override
    public void onBackPressed() {

        Intent i = new Intent(Customer_List.this,DashBoard.class);
        startActivity(i);
        finish();

        // super.onBackPressed();
    }

}
