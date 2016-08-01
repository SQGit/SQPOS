package net.sqindia.ezcabill;

import android.app.Activity;
import android.app.AlertDialog;
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
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by $Krishna on 07-06-2016.
 */
public class Edit_Customer extends Activity {


    EditText edit_id, edit_name, edit_phone, edit_email, edit_plan, edit_address;
    TextView tv_amount,plantv;
    String s, s1, s2, s3, s4, s5, s6,s7,item;
    String str="0",str1="100",str2="200",str3="300",str4="400",str5="500";

    String b3;
    LinearLayout iback;
    RadioGroup payment;
    RadioButton paid, unpaid;
    String asd,plan,ss;
    Button edit, delete_customer;
    int i;
    TextView txt_viewcustomer,txt_id,txt_name,txt_phone,txt_email,txt_address,txt_currency,txt_amount,txt_amountpaid,txt_selectplan;

    SQLiteDatabase db;
    Cursor c;
    static final String SELECT_SQL = "SELECT * FROM persons";
    String sql = "SELECT type FROM plan";
    Spinner spn;
    List<String> list;
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_customer_list);

        edit_id = (EditText) findViewById(R.id.editTextId);
        edit_name = (EditText) findViewById(R.id.editTextName);
        edit_phone = (EditText) findViewById(R.id.editTextPhone);
        edit_email = (EditText) findViewById(R.id.editTextEmail);
        edit_address = (EditText) findViewById(R.id.editTextAddress);
        tv_amount = (TextView) findViewById(R.id.plan_amount);
        plantv = (TextView) findViewById(R.id.plantv);
        txt_currency = (TextView) findViewById(R.id.currency);

        txt_viewcustomer = (TextView) findViewById(R.id.tv_viewcustomer);
        txt_id = (TextView) findViewById(R.id.textViewId);
        txt_name = (TextView) findViewById(R.id.textViewName);
        txt_phone = (TextView) findViewById(R.id.textViewPhone);
        txt_email = (TextView) findViewById(R.id.textViewEmail);
        txt_address = (TextView) findViewById(R.id.textViewAddress);
        txt_amount = (TextView) findViewById(R.id.textamount);
        txt_amountpaid = (TextView) findViewById(R.id.textamountpaid);
        txt_selectplan = (TextView) findViewById(R.id.textviewinternetplan);

        // edit_plan = (EditText) findViewById(R.id.editinternetplan);
        payment = (RadioGroup) findViewById(R.id.radioGroup2);
        paid = (RadioButton) findViewById(R.id.radio3);
        unpaid = (RadioButton) findViewById(R.id.radio4);

        edit = (Button) findViewById(R.id.btnEdit);
        delete_customer = (Button) findViewById(R.id.btndelete);

        spn = (Spinner) findViewById(R.id.spinner);
        iback = (LinearLayout) findViewById(R.id.layout_back);

        Typeface tf = Typeface.createFromAsset(getApplicationContext().getAssets(), "reg.TTF");
        txt_viewcustomer.setTypeface(tf);
        txt_id.setTypeface(tf);
        txt_name.setTypeface(tf);
        txt_phone.setTypeface(tf);
        txt_email.setTypeface(tf);
        txt_address.setTypeface(tf);
        tv_amount.setTypeface(tf);
        plantv.setTypeface(tf);
        txt_amount.setTypeface(tf);
        txt_amountpaid.setTypeface(tf);
        paid.setTypeface(tf);
        unpaid.setTypeface(tf);
        edit.setTypeface(tf);
        delete_customer.setTypeface(tf);
        txt_selectplan.setTypeface(tf);
        txt_currency.setTypeface(tf);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        b3 = sharedPreferences.getString("s3", "");
        Log.e("tag", "billone" + b3);

        txt_currency.setText(b3);
        Log.e("tag", "symbol " + txt_currency);


        db=openOrCreateDatabase("PersonDB", Context.MODE_PRIVATE, null);

        //showing textview above spinner//
        plantv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                plantv.setText("");
                getType(sql);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(Edit_Customer.this,android.R.layout.simple_spinner_item, list);

                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                spn.setAdapter(adapter);

            }
        });

        //  ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, list);







        payment.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radio3) {
                    asd = "paid";
                } else if (checkedId == R.id.radio4) {
                    asd = "Unpaid";
                }
            }

        });


        db = openOrCreateDatabase("PersonDB", Context.MODE_PRIVATE, null);
        // getType(sql);

        c = db.rawQuery(SELECT_SQL, null);
        c.moveToFirst();


        edit_name.setEnabled(false);
        edit_phone.setEnabled(false);
        edit_email.setEnabled(false);
        edit_address.setEnabled(false);
        //  edit_plan.setEnabled(false);
        spn.setEnabled(false);
        paid.setEnabled(false);
        unpaid.setEnabled(false);
        plantv.setEnabled(false);


        edit.setText("Edit");

//Clicking edit button can modifyn any member data in sqlite database ///
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (i == 0) {


                    delete_customer.setEnabled(false);
                    edit.setText("Submit");

                    edit_name.setEnabled(true);
                    edit_phone.setEnabled(true);
                    edit_email.setEnabled(true);
                    edit_address.setEnabled(true);
                    //  edit_plan.setEnabled(true);
                    plantv.setEnabled(true);
                    spn.setEnabled(true);
                    paid.setEnabled(true);
                    unpaid.setEnabled(true);

                    spn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                             item = adapterView.getItemAtPosition(i).toString();
                            Log.e("tag","12345666"+item);
                            getAmount(item);



                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });


                    i = 1;

                }
                else
                {
                    edit.setText("Edit");
                    edit_name.setEnabled(false);
                    edit_phone.setEnabled(false);
                    edit_email.setEnabled(false);
                    edit_address.setEnabled(false);
                    //  edit_plan.setEnabled(false);
                    spn.setEnabled(false);
                    paid.setEnabled(false);
                    unpaid.setEnabled(false);

                    delete_customer.setEnabled(true);


                    i = 0;


                    String name = edit_name.getText().toString().trim();
                    String add = edit_address.getText().toString().trim();
                    String id = edit_id.getText().toString().trim();
                    String phone = edit_phone.getText().toString().trim();
                    String email = edit_email.getText().toString().trim();

                    String amt = tv_amount.getText().toString();

                    if(item == null)
                    {

                        Log.e("tag","&&&&&&&&&&&&&"+item);
                        ss=plantv.getText().toString();
                        Log.e("tag","taggggg"+ss);
                    }
                    else
                    {
                        plan=item;
                        Log.e("tag","check"+plan);
                       // ss=item.getText().toString();
                        ss=item;
                        Log.e("tag","tag8635"+ss);

                    }

                   /* ss=plantv.getText().toString();
                    Log.e("tag","pointyu"+ss);*/
                    //Log.e("tag","taggggg333"+plan);



                    ///**********Updating Every data of particular member in Sqlite database ***********///
                    String sql = "UPDATE persons SET name='" + name + "', address='" + add + "',phone='" + phone + "',email='" + email + "',payment='" + asd + "',plan='" + ss + "',amount='" + amt +"' WHERE id=" + id + ";";
                    Log.d("tag", sql);

                    if (name.equals("") || add.equals("") || phone.equals("") || email.equals("")) {
                        Toast.makeText(getApplicationContext(), "You cannot save blank values", Toast.LENGTH_LONG).show();
                        return;
                    }

                    db.execSQL(sql);
                    Toast.makeText(getApplicationContext(), "Records Saved Successfully", Toast.LENGTH_LONG).show();
                    c = db.rawQuery(SELECT_SQL, null);
                    c.moveToPosition(Integer.parseInt(id));

                }
            }


        });

        //$$$$$ Clicking delete button will delete that member from database permanentely $$$$$$$$///
        delete_customer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Edit_Customer.this);
                alertDialogBuilder.setMessage("Are you sure you want delete this person?");

                alertDialogBuilder.setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                String id = edit_id.getText().toString().trim();

                                String sql = "DELETE FROM persons WHERE id=" + id + ";";
                                db.execSQL(sql);
                                Toast.makeText(getApplicationContext(), "Record Deleted", Toast.LENGTH_LONG).show();
                                c = db.rawQuery(SELECT_SQL,null);
                                Intent i = new Intent(Edit_Customer.this, Customer_List.class);
                                startActivity(i);
                                finish();
                            }
                        });

                alertDialogBuilder.setNegativeButton("No",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {

                            }
                        });


                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();


            }
        });

        //Back button//
        iback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Edit_Customer.this, Customer_List.class);
                startActivity(i);
                finish();
            }
        });



        //Getiing this data and putting in previous class //
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            s = extras.getString("id");
            s1 = extras.getString("name");
            s2 = extras.getString("phone");
            s3 = extras.getString("email");
            s4 = extras.getString("plan");
            s5 = extras.getString("address");
            s6 = extras.getString("payment");
            s7 = extras.getString("amount");

        }
        edit_id.setText(s);
        edit_name.setText(s1);
        edit_phone.setText(s2);
        edit_email.setText(s3);
        plantv.setText(s4);
        //  spn.getSelectedItem(s4);

        /*if(s4.equals("Plan 1"))
        {
            spn.setSelection(1);
            tv_amount.setText(str1);

        }else if (s4.equals("Plan 2"))
        {
            spn.setSelection(2);
            tv_amount.setText(str2);
        }
        else if (s4.equals("Plan 3"))
        {
            spn.setSelection(3);
            tv_amount.setText(str3);
        }else if (s4.equals("Plan 4"))
        {
            spn.setSelection(4);
            tv_amount.setText(str4);
        }
        else if (s4.equals("Plan 5"))
        {
            spn.setSelection(5);
            tv_amount.setText(str5);
        }*/

        tv_amount.setText(s7);
        edit_address.setText(s5);

        if (s6.equals("paid")) {

            paid.setChecked(true);
            Log.d("tag", s6);

        } else if (s6.equals("Unpaid")) {
            unpaid.setChecked(true);

        }

    }


    private void getType(String sql)
    {
        list = new ArrayList<String>();

        //   SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);//selectQuery,selectedArguments

        // looping through all rows and adding to list
        if (cursor.moveToFirst())
        {
            do
            {
                list.add(cursor.getString(cursor.getColumnIndex("type")));//adding 2nd column data
                // list.add(cursor.getString(cursor.getColumnIndex("amount")));

            } while (cursor.moveToNext());
        }
        // closing connection
        //cursor.close();
        //   db.close();

        // returning lables
        //return list;
    }


    private void getAmount(String item)

    {

        Cursor cursor = db.query("plan", null, "type=?", new String[]{item}, null, null, null);
        if (cursor.getCount() < 1) // UserName Not Exist
        {
            cursor.close();
        }
        cursor.moveToFirst();
        name = cursor.getString(cursor.getColumnIndex("amount"));

        Log.e("tag","<----amount----->"+name);
        cursor.close();
        tv_amount.setText(name);

    }





    @Override
    public void onBackPressed() {

        Intent i = new Intent(Edit_Customer.this,Customer_List.class);
        startActivity(i);
        finish();
        // super.onBackPressed();
    }


}
