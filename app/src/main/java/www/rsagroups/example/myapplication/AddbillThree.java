package www.rsagroups.example.myapplication;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;
import java.util.Set;

/**
 * Created by RSA on 11-02-2016.
 */
public class AddbillThree extends AppCompatActivity
{
    //*************gmail sender
    GMailSender sender;



    Button mConnectBtn;
    Spinner mDeviceSp;
    String ss;

    Button add_item, mail, print;
    FloatingSearchView prduct_name;
    String qty, O_old_Query, O_new_Query;
    EditText product_qtyz;
    TextView Total_amount;
    TextView Billno;
    TextView toatl;
    public int numval;
    String join,date,storeval;
    ProgressDialog dialog;
    Double pric,qtye;
    String totalvalue,sprice,value,prics;
    String a,b,c,get_customername;
    EditText editText_cname;

    int aa,bb,cc;


    private ProgressDialog mProgressDlg;
    private ProgressDialog mConnectingDlg;

    private BluetoothAdapter mBluetoothAdapter;

    private P25Connector mConnector;

    private ArrayList<BluetoothDevice> mDeviceList = new ArrayList<BluetoothDevice>();

    private AlertDialog.Builder build;
    final Context context = this;

    private ArrayList<String> sl_no = new ArrayList<String>();
    private ArrayList<String> product_name1 = new ArrayList<String>();
    private ArrayList<String> product_price = new ArrayList<String>();
    private ArrayList<String> product_qty = new ArrayList<String>();



    private ListView userList;

    public DbHelper mHelper;
    private SQLiteDatabase dataBase;
    public static String pn, pp, billno, tot, price;
    private boolean isUpdate;

    @SuppressLint("NewApi")
    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addbill2);



            if (!mBluetoothAdapter.isEnabled()) {
               // Toast.makeText(getApplicationContext(),"Please enable ur bluetooth setting",Toast.LENGTH_LONG).show();



            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("An app wants to turn on Bluetooth. Do you want to enable it?")
                    .setCancelable(false)
                    .setPositiveButton("Allow", new DialogInterface.OnClickListener() {
                        public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                            startActivity(new Intent(Settings.ACTION_BLUETOOTH_SETTINGS));

                        }
                    })
                    .setNegativeButton("Deny", new DialogInterface.OnClickListener() {
                        public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                            dialog.cancel();
                            finish();
                        }
                    });
            final AlertDialog alert = builder.create();
            alert.show();
            } else {


                Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();

                if (pairedDevices != null) {
                    mDeviceList.addAll(pairedDevices);

                    updateDeviceList();
                }
            }
        mConnector 		= new P25Connector(new P25Connector.P25ConnectionListener() {

            @Override
            public void onStartConnecting() {
                mConnectingDlg.show();
            }

            @Override
            public void onConnectionCancelled() {

                {
                    mConnectingDlg.dismiss();
                }

            }

            @Override
            public void onConnectionSuccess() {

                mConnectingDlg.dismiss();

                //showConnected();

            }

            @Override
            public void onConnectionFailed(String error) {

                mConnectingDlg.dismiss();

            }

            @Override
            public void onDisconnected() {

                //showDisonnected();
            }

        });
        mConnectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                connect();

            }
        });

            Random rand = new Random();
        final int pickedNumber = rand.nextInt(100000);
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat month_date = new SimpleDateFormat("dMM");
        SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yyyy  hh:mm:ss ");
        String month_name = month_date.format(cal.getTime());
        editText_cname=(EditText)findViewById(R.id.customername);
        String strDate = sdf.format(cal.getTime());
        int i = 0;
        join = month_name + pickedNumber;
        Log.e("TAG", "yyyyyyyyyyyyyyyyyyyyyy" + join);
        date = strDate;
        Log.e("TAG", "dateeeeeeeeeeeeeeeee" + date);


        SimpleDateFormat sd = new SimpleDateFormat("dd/MM/yyyy");
        final String c_date = sd.format(new Date());


        mHelper = new DbHelper(this);
        add_item = (Button) findViewById(R.id.add_bill);
        mail = (Button) findViewById(R.id.sendmail);
        print = (Button) findViewById(R.id.print);
        Billno = (TextView) findViewById(R.id.textbillval);
        toatl = (TextView) findViewById(R.id.grand_tot);
        prduct_name = (FloatingSearchView) findViewById(R.id.product_name);
        product_qtyz = (EditText) findViewById(R.id.product_qty);
        //toatl = (TextView) findViewById(R.id.total_amount);
        sender = new GMailSender("way2mailsuganya@gmail.com", "myannamani1");

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.
                Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);
        prduct_name.setTag("");

        //Billno.setText(String.valueOf(myRandom.nextInt()));


        //bill no value
        Billno.setText(join);
        Log.e("RANDOM_VALUE", "yyyyyyyyyyyyyyyyyyyyy" + join);
//************************send mail operation*****************



        totalvalue = toatl.getText().toString();
        mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                storeval = "\n\n\nBill No:" + "\t" + join + "\nDate:\t" + c_date + "\nItem Name:\t" + product_name1 + "\nItem Price:\t" +
                        product_price + "\nQuantity:\t" + qtye + "\nTotal Amount:\t" + "\nGrand Total:\t" + toatl;


                dataBase = mHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                Log.d("tag", sl_no.get(0));

                Log.d("tag", product_price.get(0));
                Log.d("tag", product_qty.get(0));
                ArrayList<HashMap<String, String>> orderList = new ArrayList<HashMap<String, String>>();
                HashMap<String, String> map = new HashMap<String, String>();


                DbHelper dbh = new DbHelper(context);
                for (int i = 0; i < sl_no.size(); i++) {
                    String no = sl_no.get(i);
                    String p_name = product_name1.get(i);
                    Log.e("tag_qqqq", product_name1.get(i));
                    String p_price = product_price.get(i);
                    String p_qty = product_qty.get(i);


                    dbh.insert1(dbh, c_date, join,get_customername,ss, p_name, sprice, p_qty, p_price);


                    map.put("No", no);
                    map.put("P_Name", p_name);
                    map.put("P_Price", p_price);
                    map.put("P_Qty", p_qty);
                    orderList.add(map);



                }

                ContentValues value = new ContentValues();
                value.put(DbHelper.DATE, c_date);
                value.put(DbHelper.BILL_NO, join);
                value.put(DbHelper.GRAND_TOTAL, prics);
                Log.e("RANDOM_VALUE", "totalvalue" + prics);


                dataBase.insert(DbHelper.TABLE_NAME2, null, value);


                final Dialog dialog = new Dialog(AddbillThree.this);

                dialog.setContentView(R.layout.dialog);

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
                            new MyAsyncClass().execute();

                        } catch (Exception ex) {
                            Toast.makeText(getApplicationContext(), ex.toString(), Toast.LENGTH_LONG).show();
                        }
                        //Toast.makeText(getApplicationContext(), "Sucessfully Send Recepit to our Email", Toast.LENGTH_LONG).show();
                    }
                });

                dialog.show();

                dataBase.close();
                finish();


             /*   final AlertDialog alertDialog = new AlertDialog.Builder(
                        AddbillThree.this).create();
                // Setting Dialog Message
                alertDialog.setMessage("Message: \n\n\nParticular bill details sended to mail successfully...");
                alertDialog.setCancelable(false);
                // Setting OK Button
                alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Write your code here to execute after dialog closed
                        // Toast.makeText(getApplicationContext(), "OK", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
                // Showing Alert Message
                alertDialog.show();
*/
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
        print.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);

                startActivityForResult(intent, 1000);

                printDemoContent();
               /*Intent printer=new Intent(getApplicationContext(),PrinterconfigTwo.class);
                startActivity(printer);*/

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
                                                    Log.e("TAG_RESPONSE", "per price" + sprice);
                                                    pric = Double.parseDouble(sprice);
                                                    qtye = Double.parseDouble(pp);
                                                    Log.e("TAG_RESPONSE", "quantity" + qtye);
                                                    product_price.add("" + (pric * qtye));
                                                    double val = pric * qtye;
                                                    value = String.valueOf(val);
                                                    Log.e("TAG_RESPONSE", "3333333333333" + product_price);
                                                    displayData();
                                                    prduct_name.setSearchText("");
                                                    product_qtyz.setText("");
                                                    prduct_name.setTag("");

                                                } else {
                                                    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(AddbillThree.this);
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
                                                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(AddbillThree.this);
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

        mConnectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                connect();
            }
        });

//**********************Show the listview name*************************
        userList.setOnItemClickListener(new AdapterView.OnItemClickListener()

                                        {
                                            @Override
                                            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                                                build = new AlertDialog.Builder(AddbillThree.this);
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

    private void updateDeviceList() {

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.simple_spinner_item, getArray(mDeviceList));

        adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);

        mDeviceSp.setAdapter(adapter);
        mDeviceSp.setSelection(0);
    }

    private String[] getArray(ArrayList<BluetoothDevice> data) {
        String[] list = new String[0];

        if (data == null) return list;

        int size	= data.size();
        list		= new String[size];

        for (int i = 0; i < size; i++) {
            list[i] = data.get(i).getName();
        }

        return list;
    }


    private void connect() {
        if (mDeviceList == null || mDeviceList.size() == 0) {
            return;
        }

        BluetoothDevice device = mDeviceList.get(mDeviceSp.getSelectedItemPosition());

        if (device.getBondState() == BluetoothDevice.BOND_NONE) {
            try {
                createBond(device);
            } catch (Exception e) {
                //showToast("Failed to pair device");

                return;
            }
        }

        try {
            if (!mConnector.isConnected()) {
                mConnector.connect(device);
            } else {
                mConnector.disconnect();

                //showDisonnected();
            }
        } catch (P25ConnectionException e) {
            e.printStackTrace();
        }
    }

    private void createBond(BluetoothDevice device) throws Exception {

        try {
            Class<?> cl 	= Class.forName("android.bluetooth.BluetoothDevice");
            Class<?>[] par 	= {};

            Method method 	= cl.getMethod("createBond", par);

            method.invoke(device);

        } catch (Exception e) {
            e.printStackTrace();

            throw e;
        }
    }
    private void sendData(byte[] bytes) {
        try {
            mConnector.sendData(bytes);
        } catch (P25ConnectionException e) {
            e.printStackTrace();
        }
    }

    private void printDemoContent() {

        /*********** print head*******/
        String titleStr	= "BILL NO  \t\t"+"ITEM NAME\t\t"+"QTY\t\t"+"TOTAL" + "\n\n";
        String titlehead	= "BILL NO  \t\t"+"ITEM NAME\t\t"+"QTY\t\t"+"TOTAL" + "\n\n";

        String values	= join+product_name1+product_qty+product_price;

        byte[] titleByte	= Printer.printfont(titleStr, FontDefine.FONT_48PX_HEIGHT,FontDefine.Align_CENTER,
                (byte)0x1A, PocketPos.LANGUAGE_ENGLISH);


        byte[] titleByte1	= Printer.printfont(titlehead, FontDefine.FONT_24PX,FontDefine.Align_LEFT,
                (byte)0x1A, PocketPos.LANGUAGE_ENGLISH);



        byte[] titleByte2	= Printer.printfont(values, FontDefine.FONT_24PX,FontDefine.Align_LEFT,
                (byte)0x1A, PocketPos.LANGUAGE_ENGLISH);




        byte[] totalByte	= new byte[titleByte.length+titleByte1.length+titleByte2.length];

        int offset = 0;
        System.arraycopy(titleByte, 0, totalByte, offset, titleByte.length);
        offset += titleByte.length;

        System.arraycopy(titleByte1, 0, totalByte, offset, titleByte1.length);
        offset += titleByte1.length;

        System.arraycopy(titleByte2, 0, totalByte, offset, titleByte2.length);
        byte[] senddata = PocketPos.FramePack(PocketPos.FRAME_TOF_PRINT, totalByte, 0, totalByte.length);

        sendData(senddata);
    }

    @Override
    protected void onResume() {
        displayData();
        super.onResume();
    }

    private void displayData() {

       // Bill_display_adpter disadpt = new Bill_display_adpter(AddbillThree.this, sl_no, product_name1, product_qty, product_price);
       // userList.setAdapter(disadpt);
        double price = 0;
        for (String itemprice : product_price) {
            price += Double.parseDouble(itemprice);
        }
        prics=String.valueOf(price);
        a=String.valueOf(sl_no.size());

        aa = sl_no.size();
        bb = aa;


        toatl.setText("" + price);
        // mCursor.close();
    }

    @Override
    public void onBackPressed() {

    }





    class MyAsyncClass extends AsyncTask<Void, Void, Void> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();


        }

        @Override
        protected Void doInBackground(Void... mApi) {
            try {


                sender.sendMail("Message Received", "Bill Details" + storeval, "way2mailsuganya@gmail.com", "rsa3043@gmail.com");


            } catch (Exception ex) {

            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            //Toast.makeText(getApplicationContext(),
            //     "Your Order is Registered Successfully", Toast.LENGTH_LONG).show();


        }
    }
}
