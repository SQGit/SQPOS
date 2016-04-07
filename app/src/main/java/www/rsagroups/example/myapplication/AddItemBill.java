package www.rsagroups.example.myapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;
import com.ngx.BluetoothPrinter;
import com.ngx.BtpCommands;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by Admin on 16-03-2016.
 */
public class AddItemBill extends Activity {
    SQLiteDatabase sqd;
    DbHelper helper;
    ArrayList<HashMap<String, String>> orderList = new ArrayList<HashMap<String, String>>();
    HashMap<String, String> map = new HashMap<String, String>();
    private AlertDialog.Builder build;
    Button add_item, mail, print, dot, back;
    FloatingSearchView prduct_name;
    String O_old_Query, O_new_Query;
    EditText product_qtyz;
    TextView Total_amount, head;
    TextView Billno, bill_txt;
    TextView toatl, bottom;
    TextView sno, in, ip, qt, am, or1, or2;
    String join, date, storeval;
    ProgressDialog dialog;
    Double pric, qtye;
    String totalvalue, sprice, value, prics, device_num, device_id, time;
    String a, b, c;
    GMailSender sender;
    int aa, bb, cc;
    String currentTitle = "";
    String currentAuthor = "";
    Context context = this;
    String currentDateTime = DateFormat.getDateTimeInstance().format(new Date());
    private ProgressDialog mProgressDlg;
    private ProgressDialog mConnectingDlg;
    private BluetoothAdapter mBluetoothAdapter;
    private P25Connector mConnector;
    private ArrayList<BluetoothDevice> mDeviceList = new ArrayList<BluetoothDevice>();

    String val,id;
    int data1,data2;
    private ArrayList<String> sl_no = new ArrayList<String>();
    private ArrayList<String> product_name1 = new ArrayList<String>();
    private ArrayList<String> product_price = new ArrayList<String>();
    private ArrayList<String> product_qty = new ArrayList<String>();
    private ArrayList<String> product_pprice = new ArrayList<String>();
    private ListView userList;
    public DbHelper mHelper;
    private SQLiteDatabase dataBase;
    public static String pn, pp, price;
    private boolean isUpdate;
    private BluetoothPrinter mBtp = BluetoothPrinterMain.mBtp;
    SimpleDateFormat sd = new SimpleDateFormat("dd/MM/yyyy");
    final String c_date = sd.format(new Date());

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {


        getMenuInflater().inflate(R.menu.bluetooth_printer_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_clear_device:
                // deletes the last used printer, will avoid auto conne


                AlertDialog.Builder d = new AlertDialog.Builder(this);
                d.setTitle("NGX Bluetooth Printer");
                // d.setIcon(R.drawable.ic_launcher);
                d.setMessage("Are you sure you want to delete your preferred Bluetooth printer ?");
                d.setPositiveButton(android.R.string.yes,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                mBtp.clearPreferredPrinter();
                                Toast.makeText(getApplicationContext(),
                                        "Preferred Bluetooth printer cleared",
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
                d.setNegativeButton(android.R.string.no,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        });
                d.show();
                return true;
            case R.id.action_connect_device:
                // show a dialog to select from the list of available printers
                mBtp.showDeviceList(this);
                return true;
            case R.id.action_unpair_device:
                AlertDialog.Builder u = new AlertDialog.Builder(this);
                u.setTitle("Bluetooth Printer unpair");
                // d.setIcon(R.drawable.ic_launcher);
                u.setMessage("Are you sure you want to unpair all Bluetooth printers ?");
                u.setPositiveButton(android.R.string.yes,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                if (mBtp.unPairBluetoothPrinters()) {
                                    Toast.makeText(
                                            getApplicationContext(),
                                            "All NGX Bluetooth printer(s) unpaired",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                u.setNegativeButton(android.R.string.no,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        });
                u.show();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.additem_bill);


        Intent i = getIntent();
        val = i.getStringExtra("idd");
        id= i.getStringExtra("idd");
        Log.e("tag", "12345"  +id );
        Log.e("tag", "%%%%%%%%%%%%%%%%" + val);



        head = (TextView) findViewById(R.id.textView7);
        add_item = (Button) findViewById(R.id.add_bill);
        mail = (Button) findViewById(R.id.sendmail);
        print = (Button) findViewById(R.id.print);
        Billno = (TextView) findViewById(R.id.textbillval);
        bill_txt = (TextView) findViewById(R.id.textBillno);
        bottom = (TextView) findViewById(R.id.textrights);
        //sno = (TextView) findViewById(R.id.txt_id);
        in = (TextView) findViewById(R.id.txt_fName);
        ip = (TextView) findViewById(R.id.txt_pprz);
        qt = (TextView) findViewById(R.id.txt_lName);
        am = (TextView) findViewById(R.id.txt_price);
        or1 = (TextView) findViewById(R.id.textView);
        or2 = (TextView) findViewById(R.id.textView2);
        userList = (ListView) findViewById(R.id.listview);
        Total_amount = (TextView) findViewById(R.id.toatlamt);
        toatl = (TextView) findViewById(R.id.grand_tot);
        prduct_name = (FloatingSearchView) findViewById(R.id.product_name);
        product_qtyz = (EditText) findViewById(R.id.product_qty);
        dot = (Button) findViewById(R.id.dot_icon);


        Typeface tf = Typeface.createFromAsset(getApplicationContext().getAssets(), "appfont.OTF");

        head.setTypeface(tf);
        add_item.setTypeface(tf);
        Billno.setTypeface(tf);
        bill_txt.setTypeface(tf);
//        sno.setTypeface(tf);
        in.setTypeface(tf);
        ip.setTypeface(tf);
        qt.setTypeface(tf);
        am.setTypeface(tf);
        Total_amount.setTypeface(tf);
        toatl.setTypeface(tf);
        bottom.setTypeface(tf);

        helper = new DbHelper(context);
        Billno.setText(val);

        getList();

        back = (Button) findViewById(R.id.back_icon);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent back = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(back);
            }
        });


        dot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                PopupMenu popup = new PopupMenu(getApplicationContext(), dot);









                popup.getMenuInflater().inflate(R.menu.opt_menu, popup.getMenu());

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {

                        switch (item.getItemId()) {
                            case R.id.item1:

                                AlertDialog.Builder d = new AlertDialog.Builder(AddItemBill.this);
                                d.setTitle("NGX Bluetooth Printer");

                                d.setMessage("Are you sure you want to delete your preferred Bluetooth printer ?");
                                d.setPositiveButton(android.R.string.yes,
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                mBtp.clearPreferredPrinter();
                                                Toast.makeText(getApplicationContext(),
                                                        "Preferred Bluetooth printer cleared",
                                                        Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                d.setNegativeButton(android.R.string.no,
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {

                                            }
                                        });
                                d.show();

                                return true;
                            case R.id.item2:


                                mBtp.showDeviceList(AddItemBill.this);

                                return true;
                            case R.id.item3:

                                AlertDialog.Builder u = new AlertDialog.Builder(AddItemBill.this);
                                u.setTitle("Bluetooth Printer unpair");
                                // d.setIcon(R.drawable.ic_launcher);
                                u.setMessage("Are you sure you want to unpair all Bluetooth printers ?");
                                u.setPositiveButton(android.R.string.yes,
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                mBtp.unPairBluetoothPrinters();
                                                Toast.makeText(getApplicationContext(),
                                                        "Preferred Bluetooth printer cleared",
                                                        Toast.LENGTH_SHORT).show();
                                            }

                                        });
                                u.setNegativeButton(android.R.string.no,
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                // do nothing
                                            }
                                        });
                                u.show();


                                return true;

                            default:
                                return true;
                        }

                    }
                });

                popup.show();
            }
        });

        print.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                printstmt();
                saveData();


            }
        });



        mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMail();
                Toast.makeText(getApplicationContext(),"Mail Sent",Toast.LENGTH_LONG).show();
            }
        });


        mHelper = new DbHelper(context);
        prduct_name.setTag("");

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
                sqd = mHelper.getWritableDatabase();
                Cursor resultSet = sqd.rawQuery("Select fname from user where fname LIKE '%" + O_new_Query + "%'", null);
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
                                            /*Log.d("product name", "" + pn);
                                            Log.d("product qty", "" + pp);*/
                                            pn = prduct_name.getTag().toString();
                                            //Log.e("RANDOM_VALUE", "1111111111111111111111" + prduct_name);
                                            pp = product_qtyz.getText().toString();
                                            //Log.e("RANDOM_VALUE", "2222222222222222222222" + pp);

                                            if (!pn.equals("")) {


                                                if (!pp.equals("")) {

                                                    sl_no.add("" + (sl_no.size() + 1));
                                                    product_name1.add(pn);
                                                    product_qty.add(pp);
                                                    dataBase = mHelper.getWritableDatabase();
                                                    Cursor resultSet = dataBase.rawQuery("Select lname from user where fname='" + pn + "'", null);
                                                    resultSet.moveToFirst();
                                                    sprice = resultSet.getString(0);
                                                    product_pprice.add(sprice);
                                                    Log.e("tag", "product name"+product_name1);
                                                    Log.e("TAG_RESPONSE", "per price" + sprice);
                                                    pric = Double.parseDouble(sprice);
                                                    qtye = Double.parseDouble(pp);
                                                    Log.e("TAG_RESPONSE", "quantity" + qtye);
                                                    product_price.add("" + (pric * qtye));
                                                    double val = pric * qtye;
                                                    value = String.valueOf(val);
                                                    Log.e("TAG_RESPONSE", "3333333333333" + product_price);
                                                    displayData();
                                                    //getList2();
                                                    prduct_name.setSearchText("");
                                                    product_qtyz.setText("");
                                                    prduct_name.setTag("");
                                                } else {
                                                    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(getApplicationContext());
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
                                                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(getApplicationContext());
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




    }

    private void updateData() {



    }

    private void displayData() {




        Bill_display_adpter disadpt = new Bill_display_adpter(getApplicationContext(),product_name1, product_pprice, product_qty, product_price);

        // disadpt.notifyDataSetChanged();
        userList.setAdapter(disadpt);

        double price = 0;
        for (String itemprice : product_price) {
            price += Double.parseDouble(itemprice);
        }
        prics = String.valueOf(price);
        a = String.valueOf(sl_no.size());

        aa = sl_no.size();
        bb = aa;
        toatl.setText("" + price);
        Log.e("tag", "TOTALvalue" + price);




    }



    public void getList() {

        String QRY3 = "SELECT * FROM bill Where bno= " + val;

        Log.e("TAG", "good");

        //Toast.makeText(getApplicationContext(), "show array list", Toast.LENGTH_LONG).show();

        ArrayList<Java11> lv2 = new ArrayList<Java11>();
        lv2.clear();


        Cursor c2 = helper.fetchdata(QRY3);

        if (c2 != null) {
            if (c2.moveToFirst()) {
                do {

                    //Toast.makeText(getApplicationContext(),"jhhjhjhjhjhjhjhjhh",Toast.LENGTH_LONG).show();
                    Java11 jvv = new Java11();

                   /* jvv.set_KEYID1(c2.getString(c2.getColumnIndex(helper.KEY_ID)));
                    Log.e("Tag", "Rbbbbbbbbb" + jvv.get_KEYID1());

                    jvv.set_DATE1(c2.getString(c2.getColumnIndex(helper.DATE)));
                    Log.e("Tag", "Rcccccccccc" + jvv.get_DATE1());

                    jvv.set_BILLNO1(c2.getString(c2.getColumnIndex(helper.BILL_NO)));
                    Log.e("Tag", "REEEEEEEEEEE" + jvv.get_BILLNO1());*/

                    jvv.set_ITEMNAME1(c2.getString(c2.getColumnIndex(helper.KEY_FNAME)));
                   // Log.e("Tag", "Item...Name" + jvv.get_ITEMNAME1());

                    jvv.set_ITEMPRICE1(c2.getString(c2.getColumnIndex(helper.KEY_LNAME)));
                   // Log.e("Tag", "Item...Price" + jvv.get__ITEMPRICE1());

                    jvv.set_QTY1(c2.getString(c2.getColumnIndex(helper.QTY)));
                   // Log.e("Tag", "Item_Qty" + jvv.get__QTY1());


                   jvv.set_TOTAL1(c2.getString(c2.getColumnIndex(helper.TOTAL)));



                /*  gett_tot =jvv.get_TOTAL1();
                    Log.e("tag","ttt"+gett_tot);
                    data1=Integer.valueOf(gett_tot);
                    data2 = data2 + data1;
*/


                    this.product_name1.add(jvv.get_ITEMNAME1());
                    this.product_qty.add(jvv.get__QTY1());
                    this.product_pprice.add(jvv.get__ITEMPRICE1());
                    this.product_price.add(jvv.get_TOTAL1());




                    Bill_display_adpter disadpt = new Bill_display_adpter(getApplicationContext() ,product_name1, product_pprice, product_qty, product_price);
                    Log.e("tag","$$$$$$$");
                    // disadpt.notifyDataSetChanged();
                    userList.setAdapter(disadpt);





                    lv2.add(jvv);
                }
                while (c2.moveToNext());


            }
        }





        double price = 0;
        for (String itemprice : product_price) {
            price += Double.parseDouble(itemprice);
        }
        prics = String.valueOf(price);

        toatl.setText("" + price);
        Log.e("tag","TOTALvalue"+price);



        for (int i = 0; i < product_name1.size(); i++)
        {
            Log.e("tag","%%"+product_name1.get(i));
            //Log.e("tag","******"+sl_no.get(i));
        }

        // displayData();

       /* Java33 adapter1 = new Java33(AddItemBill.this,lv2);
        userList.setAdapter(adapter1);*/
    }

    public String formatToPrint(String itms, String qtys, String perprice, String pris) {

        char item[] = new char[17];

        itms = itms.length() > 15 ? itms.substring(0, 15) + ".." : itms;

        char[] titms = itms.toCharArray();

        int size = titms.length;

        for (int i = 0; i < item.length; i++)

        {

            item[i] = i < size ? titms[i] : ' ';

        }

        itms = new String(item);


        char qty[] = new char[5];

        qtys = qtys.length() > 5 ? qtys.substring(0, 5) : qtys;

        char[] tqtys = qtys.toCharArray();

        size = tqtys.length;

        int diff = qty.length - tqtys.length;

        for (int i = qty.length - 1; i >= 0; i--)

        {

            qty[i] = i >= diff ? tqtys[i - diff] : ' ';

        }

        qtys = new String(qty);

        /////////////////////////////////////////  P E R P R I C E /////////////////////////////////////////////////////////////////
        char qtyp[] = new char[4];

        perprice = perprice.length() > 4 ? perprice.substring(0, 4) : perprice;

        char[] ptqtys = perprice.toCharArray();

        size = ptqtys.length;

        diff = qtyp.length - ptqtys.length;

        for (int i = qtyp.length - 1; i >= 0; i--)

        {

            qtyp[i] = i >= diff ? ptqtys[i - diff] : ' ';

        }

        perprice = new String(qtyp);


        /////////////////////////////////////////  P E R P R I C E /////////////////////////////////////////////////////////////////


        char pri[] = new char[8];

        pris = pris.length() > 8 ? pris.substring(0, 8) : pris;

        char[] tpris = pris.toCharArray();

        size = tpris.length;

        diff = pri.length - tpris.length;

        for (int i = pri.length - 1; i >= 0; i--)

        {

            pri[i] = i >= diff ? tpris[i - diff] : ' ';

        }

        pris = new String(pri);


        return (itms + "  " + qtys + "   " + perprice + "  " + pris);
    }


    public void printstmt()
    {

        String gettot=toatl.getText().toString();
        Log.e("tag","&*^"+gettot);
        Toast.makeText(getApplicationContext(),"Printing..",Toast.LENGTH_LONG).show();
        TelephonyManager tm = (TelephonyManager) getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
        String mPhoneNumber = tm.getLine1Number();
        String mDeviceId = tm.getDeviceId();
        device_num = mPhoneNumber;
        Log.e("TAG", "phone number" + mPhoneNumber);
        device_id = mDeviceId;
        Log.e("TAG", "phone id" + mDeviceId);
        // mBtp.showDeviceList(getActivity());
        mBtp.setPrintFontStyle(BtpCommands.FONT_STYLE_REGULAR);
        mBtp.setPrintFontSize(BtpCommands.FONT_SIZE_NORMAL);
        mBtp.printTextLine("                  HABITAT");
        mBtp.printTextLine("          Mahindra World City Canopy");
        mBtp.printTextLine("FSSAI:12415008001811");
        mBtp.printLineFeed();
        mBtp.printTextLine(currentDateTime + "  BillNo:" + val);
        //mBtp.printTextLine("time:"+time);
        mBtp.printTextLine("------------------------------------------");
        mBtp.printTextLine(" Item                Qty   Price   Amount");
        mBtp.printTextLine("------------------------------------------");
        for (int i = 0; i < product_name1.size(); i++) {


            mBtp.printTextLine(formatToPrint(product_name1.get(i), product_qty.get(i), product_pprice.get(i), product_price.get(i)));
            mBtp.printTextLine("");
            Log.d("tag", "product price ===>" + product_pprice);
        }


        mBtp.printLineFeed();
        mBtp.printTextLine("------------------------------------------");
        /////////////////
        char pri[] = new char[10];


        prics = prics.length() > 10 ? prics.substring(0, 10) : prics;

        char[] tpris = prics.toCharArray();

        int size = tpris.length;

        int diff = pri.length - tpris.length;

        for (int i = pri.length - 1; i >= 0; i--)

        {

            pri[i] = i >= diff ? tpris[i - diff] : ' ';

        }

        prics = new String(pri);
        /////////////////
        mBtp.printTextLine("Total                          " + gettot);
        Log.e("tag","total price"+price);
        mBtp.printTextLine("------------------------------------------");
        mBtp.printLineFeed();
        mBtp.printTextLine("    Designed & Developed by SQIndia.net");
        mBtp.printTextLine("           Contact:91 8526571169");
        mBtp.printLineFeed();
        mBtp.printTextLine("Bill received from:" + device_id);
        mBtp.printLineFeed();
        mBtp.printTextLine("****************************************");
        mBtp.printLineFeed();


    }



    private String[] getArray(ArrayList<BluetoothDevice> data) {
        String[] list = new String[0];

        if (data == null) return list;

        int size = data.size();
        list = new String[size];

        for (int i = 0; i < size; i++) {
            list[i] = data.get(i).getName();
        }

        return list;
    }



    public void saveData()
    {
        dataBase = mHelper.getWritableDatabase();

        Log.d("tag", sl_no.get(0));

        Log.d("tag", product_price.get(0));
        Log.d("tag", product_qty.get(0));
        Log.d("tag", product_pprice.get(0));
        ArrayList<HashMap<String, String>> orderList = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> map = new HashMap<String, String>();


        DbHelper dbh = new DbHelper(getApplicationContext());
        for (int i = 0; i < sl_no.size(); i++) {
            String no = sl_no.get(i);
            String p_name = product_name1.get(i);
            Log.e("tag_qqqq", product_name1.get(i));
            String p_price = product_price.get(i);
            String per_prz = product_pprice.get(i);
            String p_qty = product_qty.get(i);
            Log.e("tag", "+++tot+++prz" + per_prz);


            dbh.update(dbh, c_date, join, p_name, per_prz, p_qty, p_price);


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
        Log.e("RANDOM_VALUE", "totalvalue" + join);
        dataBase.insert(DbHelper.TABLE_NAME2, null, value);
        dataBase.close();
    }




    public void sendMail()
    {
        storeval = "Mahindra World City" + "\n\t\t\t\t\t\t\tcanopy" + "\n\nHABITAT DETAILS:" + "\n\n\nDate:" + "\t" + c_date + "\nBillNo:\t" + join + "\nItem Name:\t" + product_name1 + "\nItem Price:\t" +
                product_pprice + "\nQuantity:\t" + product_qty + "\nTotal Amount:\t" + product_price + "\nGrand Total:\t" + prics;
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto", "gopinath@sqindia.net", null));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject");
        emailIntent.putExtra(Intent.EXTRA_TEXT, storeval);
        startActivity(Intent.createChooser(emailIntent, "Send email..."));

    }


}