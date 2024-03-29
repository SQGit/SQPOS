package net.sqindia.sqpos;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
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

public class AddItemBill extends AppCompatActivity {
    SQLiteDatabase sqd;
    DbHelper helper;
    String g_total;
    ArrayList<HashMap<String, String>> orderList = new ArrayList<HashMap<String, String>>();
    HashMap<String, String> map = new HashMap<String, String>();
    private AlertDialog.Builder build;
    Button add_item, mail, print;
    FloatingSearchView prduct_name;
    String O_old_Query, O_new_Query;
    EditText product_qtyz;
    TextView Total_amount;
    TextView Billno, bill_txt;
    TextView toatl, bottom;
    TextView sno, in, ip, qt, am, or1, or2;
    String join, date, storeval, name_cus, table_cus,token_footer,currency,token_mail;
    ProgressDialog dialog;
    Double pric, qtye;
    String sprice, value, prics, device_num, device_id, time;
    String a, b, c;
    String gettot, ss;
    Typeface tf;
    String b1,b2,b3,b4,b5;

    int aa, bb;
    Context context = this;
    String currentDateTime = DateFormat.getDateTimeInstance().format(new Date());
    String val, id, name_customer;
    String me_name, me_table;

    private ArrayList<String> sl_no = new ArrayList<String>();
    private ArrayList<String> product_name1 = new ArrayList<String>();
    private ArrayList<String> product_price = new ArrayList<String>();
    private ArrayList<String> product_qty = new ArrayList<String>();
    private ArrayList<String> product_pprice = new ArrayList<String>();
    private ListView userList;
    public DbHelper mHelper;
    private SQLiteDatabase dataBase;
    public static String pn, pp, price;
    private BluetoothPrinter mBtp = BluetoothPrinterMain.mBtp;
    SimpleDateFormat sd = new SimpleDateFormat("dd/M/yyyy");
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
                mBtp.showDeviceList(this);
                return true;

            case R.id.action_unpair_device:
                AlertDialog.Builder u = new AlertDialog.Builder(this);
                u.setTitle("Bluetooth Printer unpair");
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
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        FontChangeCrawler fontChanger = new FontChangeCrawler(getAssets(), "appfont.OTF");
        fontChanger.replaceFonts((ViewGroup) this.findViewById(android.R.id.content));
        String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
        ss = currentDateTimeString;
        Log.e("TAg", "sssss" + ss);

        tf = Typeface.createFromAsset(getAssets(), "appfont.OTF");
        SpannableStringBuilder SS = new SpannableStringBuilder("BILLING");
        SS.setSpan(new CustomTypefaceSpan("BILLING", tf), 0, SS.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        getSupportActionBar().setTitle(SS);

        Intent i = getIntent();
        val = i.getStringExtra("idd");
        id = i.getStringExtra("idd");
        Log.e("tag", "12345" + id);
        Log.e("tag", "%%%%%%%%%%%%%%%%" + val);

        add_item = (Button) findViewById(R.id.add_bill);
        mail = (Button) findViewById(R.id.sendmail);
        print = (Button) findViewById(R.id.print);
        Billno = (TextView) findViewById(R.id.textbillval);
        bill_txt = (TextView) findViewById(R.id.textBillno);
        bottom = (TextView) findViewById(R.id.textrights);
        in = (TextView) findViewById(R.id.txt_fName);
        ip = (TextView) findViewById(R.id.txt_pprz);
        qt = (TextView) findViewById(R.id.txt_lName);
        am = (TextView) findViewById(R.id.txt_price);
       /* or1 = (TextView) findViewById(R.id.textView);
        or2 = (TextView) findViewById(R.id.textView2);*/
        userList = (ListView) findViewById(R.id.Bill_list);
        Total_amount = (TextView) findViewById(R.id.toatlamt);
        toatl = (TextView) findViewById(R.id.grand_tot);
        prduct_name = (FloatingSearchView) findViewById(R.id.product_name);
        product_qtyz = (EditText) findViewById(R.id.product_qty);

        Typeface tf = Typeface.createFromAsset(getApplicationContext().getAssets(), "appfont.OTF");

        add_item.setTypeface(tf);
        Billno.setTypeface(tf);
        bill_txt.setTypeface(tf);
        in.setTypeface(tf);
        ip.setTypeface(tf);
        qt.setTypeface(tf);
        am.setTypeface(tf);
        Total_amount.setTypeface(tf);
        toatl.setTypeface(tf);
        bottom.setTypeface(tf);
        helper = new DbHelper(context);
        Billno.setText(val);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(AddItemBill.this);
        name_customer = sharedPreferences.getString("username", "");
        Log.e("tag", "name" + name_customer);
        token_footer = sharedPreferences.getString("footer", "");
        Log.e("tag", "getvalue" + token_footer);
        bottom.setText(token_footer);
        currency = sharedPreferences.getString("send2", "");
        token_mail = sharedPreferences.getString("mail", "");

        b1 = sharedPreferences.getString("s1", "");
        b2 = sharedPreferences.getString("s2", "");
        b3 = sharedPreferences.getString("s3", "");
        b4 = sharedPreferences.getString("s4", "");

        getList();
        print.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor c1 = helper.me_name(val);
                if (c1 != null) {
                    if (c1.moveToFirst()) {
                        do {
                            me_name = c1.getString(c1.getColumnIndex(helper.CUSTOMER_NAME));
                            Log.e("tag", "<---%%%%%1---->" + me_name);
                            me_table = c1.getString(c1.getColumnIndex(helper.SET_TABLE));
                            Log.e("tag", "<---%%%%%2---->" + me_table);
                        }
                        while (c1.moveToNext());
                    }
                }
                printstmt();
                updateData();
            }
        });


        mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMail();
                Toast.makeText(getApplicationContext(), "Mail Sent", Toast.LENGTH_LONG).show();
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
                                            if (v != null)
                                            {
                                                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                                            }
                                            String editTextString = product_qtyz.getText().toString();
                                            Log.e("tag","product name"+editTextString);

                                            pn = prduct_name.getTag().toString();
                                            pp = product_qtyz.getText().toString();

                                            if (!(editTextString.equals("0"))) {
                                            if (!pn.equals("")) {
                                                if (!pp.equals("")) {

                                                    sl_no.add("" + (sl_no.size() + 1));

                                                    if (product_name1.size() == 0) {
                                                        product_name1.add(pn);
                                                        product_qty.add(pp);

                                                        dataBase = mHelper.getWritableDatabase();
                                                        Cursor resultSet = dataBase.rawQuery("Select lname from user where fname='" + pn + "'", null);
                                                        resultSet.moveToFirst();
                                                        sprice = resultSet.getString(0);
                                                        product_pprice.add(sprice);
                                                        pric = Double.parseDouble(sprice);
                                                        qtye = Double.parseDouble(pp);
                                                        product_price.add("" + (pric * qtye));
                                                        double val = pric * qtye;
                                                        value = String.valueOf(val);
                                                    }

                                                    else if (product_name1.size() > 0) {
                                                        for (int i = 0; i < product_name1.size(); i++) {
                                                            if (pn.matches(product_name1.get(i))) {

                                                                product_qty.set(i, pp);
                                                                product_name1.set(i, pn);
                                                                dataBase = mHelper.getWritableDatabase();
                                                                Cursor resultSet = dataBase.rawQuery("Select lname from user where fname='" + pn + "'", null);
                                                                resultSet.moveToFirst();
                                                                sprice = resultSet.getString(0);
                                                                product_pprice.set(i,sprice);
                                                                pric = Double.parseDouble(sprice);
                                                                qtye = Double.parseDouble(pp);
                                                                product_price.set(i,"" + (pric * qtye));
                                                                double val = pric * qtye;
                                                                value = String.valueOf(val);

                                                            } else {

                                                                if (!product_name1.contains(pn)) {
                                                                    product_name1.add(pn);
                                                                    product_qty.add(pp);

                                                                    dataBase = mHelper.getWritableDatabase();
                                                                    Cursor resultSet = dataBase.rawQuery("Select lname from user where fname='" + pn + "'", null);
                                                                    resultSet.moveToFirst();
                                                                    sprice = resultSet.getString(0);
                                                                    product_pprice.add(sprice);
                                                                    pric = Double.parseDouble(sprice);
                                                                    qtye = Double.parseDouble(pp);
                                                                    product_price.add("" + (pric * qtye));
                                                                    double val = pric * qtye;
                                                                    value = String.valueOf(val);
                                                                }
                                                            }
                                                        }


                                                    } else {
                                                        product_name1.add(pn);
                                                        product_qty.add(pp);
                                                        dataBase = mHelper.getWritableDatabase();
                                                        Cursor resultSet = dataBase.rawQuery("Select lname from user where fname='" + pn + "'", null);
                                                        resultSet.moveToFirst();
                                                        sprice = resultSet.getString(0);
                                                        product_pprice.add(sprice);
                                                        pric = Double.parseDouble(sprice);
                                                        qtye = Double.parseDouble(pp);
                                                        product_price.add("" + (pric * qtye));
                                                        double val = pric * qtye;
                                                        value = String.valueOf(val);
                                                    }


                                                    displayData();
                                                    prduct_name.setSearchText("");
                                                    product_qtyz.setText("");
                                                    prduct_name.setTag("");

                                                } else {
                                                    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(AddItemBill.this);
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
                                                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(AddItemBill.this);
                                                alertBuilder.setTitle("Invalid Data");
                                                alertBuilder.setMessage("Please Enter product Item ");
                                                alertBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                                                    public void onClick(DialogInterface dialog, int which) {
                                                        dialog.cancel();

                                                    }
                                                });
                                                alertBuilder.show();
                                            }

                                            } else {
                                                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(AddItemBill.this);
                                                alertBuilder.setTitle("Invalid Data");
                                                alertBuilder.setMessage("Please order minimum one ");
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


    private void displayData() {
        Bill_display_adpter disadpt = new Bill_display_adpter(getApplicationContext(),"aa","bb",product_name1, product_pprice, product_qty, product_price);
        userList.setAdapter(disadpt);
        double price = 0;
        for (String itemprice : product_price) {
            price += Double.parseDouble(itemprice);
        }
        prics = String.valueOf(price);
        a = String.valueOf(sl_no.size());

        aa = sl_no.size();
        bb = aa;
        toatl.setText(currency + price);
    }


    public void getList() {
        String QRY3 = "SELECT * FROM bill Where bno= " + val;
        ArrayList<Java11> lv2 = new ArrayList<Java11>();
        lv2.clear();

        Cursor c2 = helper.fetchdata(QRY3);
        if (c2 != null) {
            if (c2.moveToFirst()) {
                do {

                    Java11 jvv = new Java11();
                    jvv.set_ITEMNAME1(c2.getString(c2.getColumnIndex(helper.KEY_FNAME)));
                    jvv.set_ITEMPRICE1(c2.getString(c2.getColumnIndex(helper.KEY_LNAME)));
                    jvv.set_QTY1(c2.getString(c2.getColumnIndex(helper.QTY)));
                    jvv.set_TOTAL1(c2.getString(c2.getColumnIndex(helper.TOTAL)));

                    this.product_name1.add(jvv.get_ITEMNAME1());
                    this.product_qty.add(jvv.get__QTY1());
                    this.product_pprice.add(jvv.get__ITEMPRICE1());
                    this.product_price.add(jvv.get_TOTAL1());

                    Bill_display_adpter disadpt = new Bill_display_adpter(getApplicationContext(),"aa","bb", product_name1, product_pprice, product_qty, product_price);
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
        toatl.setText(""+currency + price);

        for (int i = 0; i < product_name1.size(); i++) {
            Log.e("tag", "%%" + product_name1.get(i));
        }
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


    public void printstmt() {

        TelephonyManager tm = (TelephonyManager) getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
        String mPhoneNumber = tm.getLine1Number();
        String mDeviceId = tm.getDeviceId();
        device_num = mPhoneNumber;
        device_id = mDeviceId;
        mBtp.setPrintFontStyle(BtpCommands.FONT_STYLE_REGULAR);
        mBtp.setPrintFontSize(BtpCommands.FONT_SIZE_NORMAL);
        mBtp.printTextLine("                  "+b1);
        mBtp.printTextLine("          "+b2);
        mBtp.printTextLine("FSSAI:"+b3);
        mBtp.printLineFeed();
        mBtp.printTextLine("          Welcome : "+me_name);
        mBtp.printTextLine(currentDateTime + "  BillNo:" + val);
        mBtp.printTextLine("------------------------------------------");
        mBtp.printTextLine(" Item              Qty  Price   Amount");
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

        mBtp.printTextLine("Total:                      "+currency+prics);
        Log.e("tag", "total price" + prics);
        mBtp.printTextLine("------------------------------------------");
        mBtp.printTextLine("    Designed & Developed by SQIndia.net");
        mBtp.printTextLine("           Contact:"+b4);
        mBtp.printLineFeed();
        mBtp.printTextLine("Bill received from:" + device_id);
        mBtp.printLineFeed();
        mBtp.printTextLine("****************************************");

    }


    public void updateData() {
        g_total = toatl.getText().toString();
        dataBase = mHelper.getWritableDatabase();
        dataBase = mHelper.getWritableDatabase();
        ArrayList<HashMap<String, String>> orderList = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> map = new HashMap<String, String>();

        DbHelper dbh = new DbHelper(getApplicationContext());

        dbh.delete_item1(dbh, val);
        dbh.delete_item2(dbh, val);

        for (int i = 0; i < product_name1.size(); i++) {

            String p_name = product_name1.get(i);
            String p_price = product_price.get(i);
            String per_prz = product_pprice.get(i);
            String p_qty = product_qty.get(i);


            ContentValues value = new ContentValues();
            value.put(DbHelper.DATE, c_date);
            value.put(DbHelper.BILL_NO, val);
            value.put(DbHelper.CUSTOMER_NAME, me_name);
            value.put(DbHelper.SET_TABLE, me_table);
            value.put(DbHelper.KEY_FNAME, p_name);
            value.put(DbHelper.KEY_LNAME, per_prz);
            value.put(DbHelper.QTY, p_qty);
            value.put(DbHelper.TOTAL, p_price);
            dataBase.insert(DbHelper.TABLE_NAME1, null, value);
        }


        ContentValues value = new ContentValues();
        value.put(DbHelper.DATE, c_date);
        value.put(DbHelper.BILL_NO, val);
        value.put(DbHelper.CUSTOMER_NAME, me_name);
        value.put(DbHelper.SET_TABLE, me_table);
        value.put(DbHelper.GRAND_TOTAL, g_total);
        dataBase.insert(DbHelper.TABLE_NAME2, null, value);

    }


    public void sendMail() {
        storeval = "Mahindra World City" + "\n\t\t\t\t\t\t\tcanopy" + "\n\nHABITAT DETAILS:" + "\n\n\nDate:" + "\t" + c_date + "\nBillNo:\t" + join + "\nItem Name:\t" + product_name1 + "\nItem Price:\t" +
                product_pprice + "\nQuantity:\t" + product_qty + "\nTotal Amount:\t" + product_price + "\nGrand Total:\t" + prics;
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto", token_mail, null));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject");
        emailIntent.putExtra(Intent.EXTRA_TEXT, storeval);
        startActivity(Intent.createChooser(emailIntent, "Send email..."));

    }
}