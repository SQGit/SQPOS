package www.rsagroups.example.myapplication;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

/**
 * Created by RSA on 11-02-2016.
 */
public class AddBillTwo extends AppCompatActivity {
    //*************gmail sender
    GMailSender sender;
    Button add_item, mail, print;
    FloatingSearchView prduct_name;
    String qty, O_old_Query, O_new_Query;
    EditText product_qtyz;
    TextView Total_amount;
    TextView Billno;
    TextView toatl;
    public int numval;
    String join,date,currentdate,storeval;
    ProgressDialog dialog;
    Double pric,qtye;
    String totalvalue,sprice,value;



    private AlertDialog.Builder build;
    final Context context = this;

    private ArrayList<String> sl_no = new ArrayList<String>();
    private ArrayList<String> product_name1 = new ArrayList<String>();
    private ArrayList<String> product_price = new ArrayList<String>();
    private ArrayList<String> product_qty = new ArrayList<String>();

    private ListView userList;

    private DbHelper mHelper;
    private SQLiteDatabase dataBase;
    public static String pn, pp;
    private boolean isUpdate;

    @SuppressLint("NewApi")
    @TargetApi(Build.VERSION_CODES.GINGERBREAD)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addbill2);

        Random rand = new Random();
        final int pickedNumber = rand.nextInt(100000);
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat month_date = new SimpleDateFormat("dMM");
        SimpleDateFormat sdf= new SimpleDateFormat("dd/mm/yyyy  hh:mm:ss ");  String month_name = month_date.format(cal.getTime());

        String strDate = sdf.format(cal.getTime());
        int i=0;
        join =  month_name + pickedNumber;
        Log.e("TAG","yyyyyyyyyyyyyyyyyyyyyy"+join);
        date = strDate;
        Log.e("TAG","dateeeeeeeeeeeeeeeee"+date);

        SimpleDateFormat sd = new SimpleDateFormat("dd/MM/yyyy");
        final String c_date = sd.format(new Date());



        storeval = "\n\n\nBill No:" + "\t" + join + "\nPRODUCT NAME:\t" + prduct_name + "\nPRODUCT PRICE:\t" + sprice + "\nQTY:\t" +
                qtye + "\nTOTAL:\t" + product_price;
        mHelper = new DbHelper(this);
        add_item = (Button) findViewById(R.id.add_bill);
        mail = (Button) findViewById(R.id.sendmail);
        print = (Button) findViewById(R.id.print);
        Billno = (TextView) findViewById(R.id.textbillval);
        prduct_name = (FloatingSearchView) findViewById(R.id.product_name);
        product_qtyz = (EditText) findViewById(R.id.product_qty);
        toatl = (TextView) findViewById(R.id.toatlamt);

        prduct_name.setTag("");

        //Billno.setText(String.valueOf(myRandom.nextInt()));



        //bill no value
        Billno.setText(join);

        Log.e("RANDOM_VALUE", "yyyyyyyyyyyyyyyyyyyyy" + join);
//************************send mail operation*****************
        sender = new GMailSender("way2mailsuganya@gmail.com", "myannamani1");

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.
                Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);



        Log.e("RANDOM_VALUE", "grand total value" + join);

        mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                dataBase=mHelper.getWritableDatabase();
                ContentValues values=new ContentValues();
                Log.d("tag",  sl_no.get(0));
                Log.d("tag",  product_name1.get(0));
                Log.d("tag",  product_price.get(0));
                Log.d("tag", product_qty.get(0));



                values.put(DbHelper.KEY_ID, sl_no.get(0));
                values.put(DbHelper.KEY_FNAME, product_name1.get(0));
                values.put(DbHelper.KEY_LNAME, product_price.get(0));
                values.put(DbHelper.BILL_NO, join);

                Log.e("TAG_DATE", "qqqqqqqqqqqqqqqqqqqq" + join);
                values.put(DbHelper.DATE, c_date);
                Log.e("TAG_BILL", "rrrrrrrrrrrrrrrrrrrr" + c_date);
                values.put(DbHelper.QTY, product_qty.get(0));


                dataBase.insert(DbHelper.TABLE_NAME1, null, values);

                dataBase.close();
                finish();

                final Dialog dialog = new Dialog(AddBillTwo.this);
                // Include dialog.xml file
                dialog.setContentView(R.layout.dialog);
                // Set dialog title
                dialog.setTitle("Enter the EmailId");

                // set values for custom dialog components - text, image and button
                EditText text = (EditText) dialog.findViewById(R.id.textDialog);
                text.setTextColor(Color.parseColor("#000000"));

                Button declineButton = (Button) dialog.findViewById(R.id.send);
                // if decline button is clicked, close the custom dialog
                declineButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Close dialog
                        dialog.dismiss();
                        try {
                            new MyAsyncClass1().execute();

                        } catch (Exception ex) {
                            Toast.makeText(getApplicationContext(), ex.toString(), Toast.LENGTH_LONG).show();
                        }
                        //Toast.makeText(getApplicationContext(), "Sucessfully Send Recepit to our Email", Toast.LENGTH_LONG).show();
                    }
                });


                dialog.show();


            }
        });


        userList = (ListView) findViewById(R.id.Bill_list);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#ffffff'><big><b>HABITAT</b></big> </font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back_arrow);
        Total_amount = (TextView) findViewById(R.id.total_amount);
        /// custom
        prduct_name.setBackgroundColor(Color.parseColor("#ECE7D5"));
        prduct_name.setViewTextColor(Color.parseColor("#000000"));
        prduct_name.setHintTextColor(Color.parseColor("#596D73"));
        prduct_name.setActionMenuOverflowColor(Color.parseColor("#FFFFFF"));
        prduct_name.setMenuItemIconColor(Color.parseColor("#2AA198"));
        prduct_name.setLeftActionIconColor(Color.parseColor("#657A81"));
        prduct_name.setClearBtnColor(Color.parseColor("#D30102"));
        prduct_name.setSuggestionRightIconColor(Color.parseColor("#BCADAD"));
        prduct_name.setDividerColor(Color.parseColor("#dfd7b9"));
        //custom

        prduct_name.setOnQueryChangeListener(new FloatingSearchView.OnQueryChangeListener() {
            @Override
            public void onSearchTextChanged(String old_Query, String new_Query) {

                Log.d("tag", "" + old_Query + "" + new_Query);
                O_old_Query = old_Query.toString();
                O_new_Query = new_Query.toString();

                ArrayList<Category_search> zz = new ArrayList<Category_search>();
                if (!O_old_Query.equals("") && O_new_Query.equals("")) {
                    prduct_name.clearSuggestions();
                    return;
                }
                dataBase = mHelper.getWritableDatabase();
                Cursor resultSet = dataBase.rawQuery("Select fname from user where fname LIKE '%" + O_new_Query + "%'", null);

                int i = 0;

                resultSet.moveToFirst();
                while (!resultSet.isAfterLast()) {
                    zz.add(new Category_search(resultSet.getString(0)));
                    //  Toast.makeText(getApplicationContext(), "test1" + resultSet.getString(0), Toast.LENGTH_LONG);
                    i++;
                    resultSet.moveToNext();
                }
                prduct_name.swapSuggestions(zz);
            }
        });

        prduct_name.setOnSearchListener(new FloatingSearchView.OnSearchListener() {
            @Override
            public void onSuggestionClicked(SearchSuggestion searchSuggestion) {
                Category_search clickSuggestion = (Category_search) searchSuggestion;
                //    Toast.makeText(getApplicationContext(), clickSuggestion.Category_name + " - ", Toast.LENGTH_SHORT).show();
                prduct_name.setSearchText(clickSuggestion.Category_name);
                prduct_name.setTag(clickSuggestion.Category_name);
            }

            @Override
            public void onSearchAction() {

            }
        });


      add_item.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {

                                            Log.d("product name", "" + pn);
                                            Log.d("product qty", "" + pp);
                                            pn = prduct_name.getTag().toString();
                                            Log.e("RANDOM_VALUE", "1111111111111111111111" + prduct_name);
                                            pp = product_qtyz.getText().toString();
                                            Log.e("RANDOM_VALUE", "2222222222222222222222" + pp);

                                            if (!pn.equals("")) {
                                                if (!pp.equals("")) {

                                                    sl_no.add("" + (sl_no.size() + 1));
                                                    product_name1.add(pn);

                                                    product_qty.add(pp);








                                                    dataBase = mHelper.getWritableDatabase();
                                                    Cursor resultSet = dataBase.rawQuery("Select lname from user where fname='" + pn + "'", null);
                                                    resultSet.moveToFirst();
                                                    sprice = resultSet.getString(0);
                                                    Log.e("TAG_RESPONSE","11111111111"+sprice);
                                                    pric = Double.parseDouble(sprice);
                                                    qtye = Double.parseDouble(pp);
                                                    Log.e("TAG_RESPONSE", "2222222222222" + qtye);
                                                    product_price.add("" + (pric * qtye));
                                                    double val=pric*qtye;
                                                    value=String.valueOf(val);
                                                    Log.e("TAG_RESPONSE", "3333333333333" + product_price);
                                                    displayData();
                                                    prduct_name.setSearchText("");
                                                    product_qtyz.setText("");
                                                    prduct_name.setTag("");


                                                } else {
                                                    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(AddBillTwo.this);
                                                    alertBuilder.setTitle("Invalid Data");
                                                    alertBuilder.setMessage("QTY ");
                                                    alertBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                                                        public void onClick(DialogInterface dialog, int which) {
                                                            dialog.cancel();

                                                        }
                                                    });
                                                    alertBuilder.show();
                                                }
                                            } else {
                                                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(AddBillTwo.this);
                                                alertBuilder.setTitle("Invalid Data");
                                                alertBuilder.setMessage("Please Enter product Item ");
                                                alertBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                                                    public void onClick(DialogInterface dialog, int which) {
                                                        dialog.cancel();

                                                    }
                                                });
                                                alertBuilder.show();
                                            }


                                        }
                                    }

        );




        userList.setOnItemClickListener(new AdapterView.OnItemClickListener()

                                        {
                                            @Override
                                            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                                                build = new AlertDialog.Builder(AddBillTwo.this);
                                                build.setTitle("Delete " + product_name1.get(position) + " "
                                                        + product_price.get(position));
                                                build.setMessage("Do you want to delete ?");
                                                build.setPositiveButton("Yes",
                                                        new DialogInterface.OnClickListener() {

                                                            public void onClick(DialogInterface dialog,
                                                                                int which) {


                                                                sl_no.remove(sl_no.size() - 1);
                                                                product_name1.remove(position);

                                                                product_price.remove(position);

                                                                product_qty.remove(position);

                                                                displayData();
                                                            }
                                                        });

                                                build.setNegativeButton("No",
                                                        new DialogInterface.OnClickListener() {

                                                            public void onClick(DialogInterface dialog,
                                                                                int which) {
                                                                dialog.cancel();
                                                            }
                                                        });
                                                AlertDialog alert = build.create();
                                                alert.show();


                                            }
                                        }

        );

    }

   /* private void saveData() {
        dataBase=mHelper.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(DbHelper.KEY_FNAME,pn);
        values.put(DbHelper.KEY_LNAME,price );
        values.put(DbHelper.BILL_NO,billno );
        values.put(DbHelper.QTY,pp );
        values.put(DbHelper.TOTAL,tot );
        dataBase.insert(DbHelper.TABLE_NAME1, null, values);

*//*
        System.out.println("");
        if(isUpdate)
        {
            //update database with new data
            dataBase.update(DbHelper.TABLE_NAME, values, DbHelper.KEY_ID+"="+id, null);
        }
        else
        {
            //insert data into database
            dataBase.insert(DbHelper.TABLE_NAME, null, values);
        }
        //close database*//*
        dataBase.close();
        finish();


    }*/



    @Override
    protected void onResume() {
        displayData();
        super.onResume();
    }

    private void displayData() {

     //  Bill_display_adpter disadpt = new Bill_display_adpter(AddBillTwo.this, sl_no, product_name1, product_qty, product_price);
      //  userList.setAdapter(disadpt);
        double price = 0;
        for (String itemprice : product_price) {
            price += Double.parseDouble(itemprice);
        }
        Total_amount.setText("" + price);
        // mCursor.close();
    }

    @Override
    public void onBackPressed() {

    }


     class MyAsyncClass1 extends AsyncTask<Void, Void, Void>
     {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                dialog = new ProgressDialog(AddBillTwo.this);
                dialog.setMessage("Uploading...");
                dialog.show();
            }


            protected Void doInBackground(Void... params) {
                try
                {
                    Log.d("TAG", "message");
                    sender.sendMail("hi", "BILL DETAILS"+storeval, "way2mailsuganya@gmail.com", "mbrsalman@gmail.com");
                    //dialog.setMessage(randval + "This customer bill details has been sended");

                    Toast.makeText(getApplicationContext(),"Successfully registered",Toast.LENGTH_LONG).show();

                } catch (Exception ex) {

                }
                return null;
            }

         @Override
         protected void onPostExecute(Void aVoid) {
             super.onPostExecute(aVoid);
            dialog.dismiss();
         }
     }
}
