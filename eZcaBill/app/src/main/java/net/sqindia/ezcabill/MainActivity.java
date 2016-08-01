package net.sqindia.ezcabill;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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
import java.util.Calendar;
import java.util.List;


public class MainActivity extends ActionBarActivity implements View.OnClickListener {

    private EditText editTextName;
    private EditText editTextAdd;
    private EditText editTextPhone;
    private EditText editTextEmail;
    private Button btnAdd;
    private Button btnView;
    private LinearLayout ib;
    RadioGroup radioGroup1;
    RadioButton paid,unpaid;

    private TextView amount;
    private TextView txt_currency;
    TextView txtDate,txt_newregistration,txt_amount,txt_paid;

    String asd,name;
    String aadsf;
    private SQLiteDatabase db;

    private static final String SELECT_SQL = "SELECT * FROM plan ";
    private Cursor cursor;
    String sql = "SELECT type FROM plan";
    String sql1 = "SELECT amount FROM plan";
    String b3;

    String str="0",str1="100",str2="200",str3="300",str4="400",str5="500";

    private Spinner spn;
    List<String>list;
    private int year;
    private int month;
    private int day;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

      //  createDatabase();


//--- Getting current date------//
        final Calendar c = Calendar.getInstance();
        year  = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day   = c.get(Calendar.DAY_OF_MONTH);

//***Method to Open Database **//
        db=openOrCreateDatabase("PersonDB", Context.MODE_PRIVATE, null);
        getType(sql);


        //------Back Button---//
        ib = (LinearLayout) findViewById(R.id.layout_back);
        ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this,DashBoard.class);
                startActivity(i);
                finish();
            }
        });

      //  createDatabase();

      //  txtDate = (TextView) findViewById(R.id.txt_date);


        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextAdd = (EditText) findViewById(R.id.editTextAddress);
        editTextPhone = (EditText) findViewById(R.id.editTextPhone);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        amount = (TextView) findViewById(R.id.plan_amount);
        txt_newregistration = (TextView) findViewById(R.id.tv_newregistration);

        txt_amount = (TextView) findViewById(R.id.tv_amnt);
        txt_paid = (TextView) findViewById(R.id.tv_paid);
        txt_currency = (TextView) findViewById(R.id.currency);
        paid = (RadioButton) findViewById(R.id.radio3);
        unpaid = (RadioButton) findViewById(R.id.radio4);

        btnAdd = (Button) findViewById(R.id.btnAdd);
     //   btnView = (Button) findViewById(R.id.btnView);
        spn = (Spinner) findViewById(R.id.spinner);

         radioGroup1 = (RadioGroup) findViewById(R.id.radioGroup2);

        Typeface tf = Typeface.createFromAsset(getApplicationContext().getAssets(), "reg.TTF");
        txt_newregistration.setTypeface(tf);
        editTextName.setTypeface(tf);
        editTextPhone.setTypeface(tf);
        editTextEmail.setTypeface(tf);
        editTextAdd.setTypeface(tf);
        amount.setTypeface(tf);
        txt_amount.setTypeface(tf);
        txt_paid.setTypeface(tf);
        paid.setTypeface(tf);
        unpaid.setTypeface(tf);
        btnAdd.setTypeface(tf);
        txt_currency.setTypeface(tf);


        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        b3 = sharedPreferences.getString("s3", "");
        Log.e("tag", "billone" + b3);



        btnAdd.setOnClickListener(this);
       // btnView.setOnClickListener(this);


        //-----Method to call Plan values from another table and store in Spinner ---//
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, list);

      //  ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                //R.array.InternetPlan, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spn.setAdapter(adapter);

     spn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
           @Override
           public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

               String item = adapterView.getItemAtPosition(i).toString();
               getAmount(item);


             /*  if (item.equals("Select Internet Plan"))
               {
                   amount.setText(str);
               }else if (item.equals("Plan 1"))
               {
                   amount.setText(str1);
               }else if (item.equals("Plan 2"))
               {
                   amount.setText(str2);
               }else if (item.equals("Plan 3"))
               {
                   amount.setText(str3);
               }else if (item.equals("Plan 4"))
               {
                   amount.setText(str4);
               }else if (item.equals("Plan 5"))
               {
                   amount.setText(str5);
               }

               // Showing selected spinner item
               Toast.makeText(adapterView.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();*//**//**/
           }

           @Override
           public void onNothingSelected(AdapterView<?> adapterView) {

           }
       });  //****Closing Spinner Method*******///


        radioGroup1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radio3) {
                    asd = "paid";
                } else if (checkedId == R.id.radio4) {
                    asd = "Unpaid";
                }
            }

        });



//Displaying Current Date //
        aadsf =  String.valueOf(new StringBuilder().append(day).append("/").append(month + 1).append("/").append(year).append(""));
        Log.e("TAg", "2s4a25s" + aadsf);

       txt_currency.setText(b3);
        Log.e("tag", "symbol " + txt_currency);



    }



    protected void createDatabase(){
        db=openOrCreateDatabase("PersonDB", Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS persons(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, name VARCHAR,phone INTEGER,email VARCHAR,address VARCHAR,plan VARCHAR,payment VARCHAR);");
    }

//**********Method to insert data in Sqlite Data base ***********//
    protected void insertIntoDB() {

            String name = editTextName.getText().toString().trim();
            String add = editTextAdd.getText().toString().trim();
            String phone = editTextPhone.getText().toString().trim();
            String email = editTextEmail.getText().toString().trim();
            String plan = spn.getSelectedItem().toString();
            String amt = amount.getText().toString();



            if (name.equals("") || add.equals("") || phone.equals("") || email.equals("") || plan.equals("Select Internet Plan") || asd == null) {
                Toast.makeText(getApplicationContext(), "Please fill all fields", Toast.LENGTH_LONG).show();
                return;
            }

            String query = "INSERT INTO persons (name,address,phone,email,plan,payment,amount,date,update_date) VALUES( '" + name + "', '" + add + "','" + phone + "','" + email + "','" + plan + "','" + asd + "','" + amt + "','" + aadsf + "','" + aadsf + "');";
            db.execSQL(query);
            Toast.makeText(getApplicationContext(), "Saved Successfully", Toast.LENGTH_LONG).show();

    }//Ending//




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    /*private void showPeoples(){
        Intent intent = new Intent(this,ViewPeople.class);
        startActivity(intent);
        finish();
    }*/


    @Override
    public void onClick(View v) {
        if(v == btnAdd){

            insertIntoDB();
            editTextName.setText("");
            editTextPhone.setText("");
            editTextEmail.setText("");
            editTextAdd.setText("");
            spn.setSelection(0);
            paid.setChecked(false);
            unpaid.setChecked(false);
            editTextName.requestFocus();
        }

            /*if (v == btnView) {
                showPeoples();
            }*/

    }

    @Override
    public void onBackPressed() {

        Intent i = new Intent(MainActivity.this,DashBoard.class);
        startActivity(i);
        finish();

        // super.onBackPressed();
    }


    public String getAggnum(String storedId) {


        Cursor cursor = db.query("plan", null, " ID=?", new String[]{storedId}, null, null, null);
        if (cursor.getCount() < 1) // UserName Not Exist
        {
            cursor.close();
            return "Unit No";
        }
        cursor.moveToFirst();
        String id = cursor.getString(cursor.getColumnIndex("RENTALNO"));
        cursor.close();
        return id;
    }

//Opening another table and getting Plan type ///
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


    //***Above Plan type mathing with that row and getting  amount ***//
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
        amount.setText(name);

    }

}