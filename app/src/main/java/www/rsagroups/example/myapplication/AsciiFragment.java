/**
 * Copyright 2014 Looped Labs pvt. Ltd. http://loopedlabs.com
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package www.rsagroups.example.myapplication;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;
import com.ngx.BluetoothPrinter;
import com.ngx.BtpCommands;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Set;

import cn.pedant.SweetAlert.SweetAlertDialog;


public class AsciiFragment extends Fragment {



    public Integer number;
    EditText editText_cname;
    ArrayList<HashMap<String, String>> orderList = new ArrayList<HashMap<String, String>>();
    HashMap<String, String> map = new HashMap<String, String>();
    private AlertDialog.Builder build;
    Button add_item, mail, print, dot, back,save;
    FloatingSearchView prduct_name;
    String O_old_Query, O_new_Query;
    ImageView backic;
    EditText product_qtyz;
    TextView Total_amount, head;
    TextView Billno, bill_txt;
    TextView toatl, bottom;
    TextView  in, ip, qt, am, or1, or2;
    String join, date, storeval,get_customername;
    ProgressDialog dialog;
    Double pric, qtye;
    String totalvalue, sprice, value, prics, device_num, device_id, time;
    String a, b, c;
    GMailSender sender;
    String cus_name,g_total;
    int aa, bb, cc;

    String ss;

    private com.rey.material.widget.Spinner spinner1;
    String[] table_no;


    String currentDateTime = DateFormat.getDateTimeInstance().format(new Date());
    private ProgressDialog mProgressDlg;
    private ProgressDialog mConnectingDlg;
    private BluetoothAdapter mBluetoothAdapter;
    private P25Connector mConnector;
    private ArrayList<BluetoothDevice> mDeviceList = new ArrayList<BluetoothDevice>();


    private ArrayList<String> sl_no = new ArrayList<String>();
    private ArrayList<String> product_name1 = new ArrayList<String>();
    private ArrayList<String> product_price = new ArrayList<String>();
    private ArrayList<String> product_qty = new ArrayList<String>();
    private ArrayList<String> product_pprice = new ArrayList<String>();
    private ListView userList;
    public DbHelper mHelper;
    private SQLiteDatabase dataBase;
    public static String pn, pp,price;
    private boolean isUpdate;
    private BluetoothPrinter mBtp = BluetoothPrinterMain.mBtp;
    SimpleDateFormat sd = new SimpleDateFormat("dd/MM/yyyy");
    final String c_date = sd.format(new Date());
     @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.printerconfig3, container, false);
        // textView is the TextView view that should display it
        //save = (Button) view.findViewById(R.id.save_btn);


        spinner1 = (com.rey.material.widget.Spinner) view.findViewById(R.id.spinner1);
        table_no= new String[]{"TABLE 1","TABLE 2","TABLE 3","TABLE 4","TABLE 5","TABLE 6","TABLE 7","TABLE 8","TABLE 9","TABLE 10"};

        editText_cname=(EditText) view.findViewById(R.id.editText_customer);
        head = (TextView) view.findViewById(R.id.textView7);
        add_item = (Button) view.findViewById(R.id.add_bill);
        mail = (Button) view.findViewById(R.id.sendmail);
        print = (Button) view.findViewById(R.id.print);
        save=(Button)view.findViewById(R.id.save);
        Billno = (TextView) view.findViewById(R.id.textbillval);
        bill_txt = (TextView) view.findViewById(R.id.textBillno);
        bottom = (TextView) view.findViewById(R.id.textrights);
        //sno = (TextView) view.findViewById(R.id.txt_id);
        in = (TextView) view.findViewById(R.id.txt_fName);
        ip = (TextView) view.findViewById(R.id.txt_pprz);
        qt = (TextView) view.findViewById(R.id.txt_lName);
        am = (TextView) view.findViewById(R.id.txt_price);
        or1 = (TextView) view.findViewById(R.id.textView);
        or2 = (TextView) view.findViewById(R.id.textView2);
        userList = (ListView) view.findViewById(R.id.Bill_list);
        Total_amount = (TextView) view.findViewById(R.id.toatlamt);
        toatl = (TextView) view.findViewById(R.id.grand_tot);
        //backic=(ImageView)view.findViewById(R.id.imageView3);
        prduct_name = (FloatingSearchView) view.findViewById(R.id.product_name);
        product_qtyz = (EditText) view.findViewById(R.id.product_qty);
        Typeface tf = Typeface.createFromAsset(getActivity().getAssets(), "appfont.OTF");

//        save.setTypeface(tf);
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


        //////////////////////////////// S A V E O R D E R ////////////////////////////////////////////////////////////////
        //////////////// S A V E O R D E R ////////////////////////////////////////////////////////////////



        back = (Button) view.findViewById(R.id.back_icon);
        back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent back = new Intent(getActivity(), MainActivity.class);
                startActivity(back);
            }
        });


     /*  spinner1.setOnItemClickListener(new com.rey.material.widget.Spinner.OnItemClickListener() {
            @Override
            public boolean onItemClick(com.rey.material.widget.Spinner parent, View view, int position, long id) {

                spinner1.getSelectedItem().toString();
                int uu=spinner1.getSelectedItemPosition();
                String ss="Table"+uu;
                String tab_name=spinner1.getSelectedItem().toString();
                Log.e("tag","spin value"+ss);
             return  true;
            }
        });*/


        spinner1.setOnItemSelectedListener(new com.rey.material.widget.Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(com.rey.material.widget.Spinner parent, View view, int position, long id) {
                spinner1.getSelectedItem().toString();
                int uu=spinner1.getSelectedItemPosition();
                 ss="Table"+uu;

                AsciiFragment.this.number = spinner1
                        .getSelectedItemPosition() + 1;
                Log.e("tag","position"+number);
            }
        });


        ArrayAdapter<String> adapt =new ArrayAdapter<String>(getActivity(),R.layout.spin_alloc,R.id.textView8,table_no);
        spinner1.setAdapter(adapt);





        TelephonyManager telephony_manager = (TelephonyManager) getActivity().getSystemService(Context.TELEPHONY_SERVICE);
        String mPhoneNumber = telephony_manager.getLine1Number();
        dot = (Button) view.findViewById(R.id.dot_icon);
        dot.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {


                PopupMenu popup = new PopupMenu(getActivity(), dot);
                popup.getMenuInflater().inflate(R.menu.opt_menu, popup.getMenu());

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {

                        switch (item.getItemId()) {
                            case R.id.item1:

                                AlertDialog.Builder d = new AlertDialog.Builder(getActivity());
                                d.setTitle("NGX Bluetooth Printer");

                                d.setMessage("Are you sure you want to delete your preferred Bluetooth printer ?");
                                d.setPositiveButton(android.R.string.yes,
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                mBtp.clearPreferredPrinter();
                                                Toast.makeText(getActivity(),
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
                            case R.id.item2:


                                mBtp.showDeviceList(getActivity());

                                return true;
                            case R.id.item3:

                                AlertDialog.Builder u = new AlertDialog.Builder(getActivity());
                                u.setTitle("Bluetooth Printer unpair");
                                // d.setIcon(R.drawable.ic_launcher);
                                u.setMessage("Are you sure you want to unpair all Bluetooth printers ?");
                                u.setPositiveButton(android.R.string.yes,
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                if (mBtp.unPairBluetoothPrinters()) {
                                                    Toast.makeText(
                                                            getActivity(),
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

                            default:
                                return true;
                        }

                    }
                });

                popup.show();
            }
        });
        Random rand = new Random();
        final int pickedNumber = rand.nextInt(100000);
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat month_date = new SimpleDateFormat("dMM");
        SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yyyy  hh:mm:ss ");
        String month_name = month_date.format(cal.getTime());
        Calendar c = Calendar.getInstance();
        int seconds = c.get(Calendar.SECOND);
        time = String.valueOf(seconds);
        final String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
        String strDate = sdf.format(cal.getTime());
        int i = 0;
        join = month_name + pickedNumber;
        Log.e("TAG", "yyyyyyyyyyyyyyyyyyyyyy" + join);
        date = strDate;
        Log.e("TAG", "dateeeeeeeeeeeeeeeee" + date);
        mHelper = new DbHelper(getActivity());
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            //showUnsupported();
        } else {
            if (!mBluetoothAdapter.isEnabled()) {
                //showDisabled();
            } else {
                //showEnabled();

                Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();

                if (pairedDevices != null) {
                    mDeviceList.addAll(pairedDevices);

                    //updateDeviceList();
                }
            }

            mProgressDlg = new ProgressDialog(getActivity());
            mProgressDlg.setMessage("Scanning...");
            mProgressDlg.setCancelable(false);
            mProgressDlg.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();

                    mBluetoothAdapter.cancelDiscovery();
                }
            });

            mConnectingDlg = new ProgressDialog(getActivity());
            mConnectingDlg.setMessage("Connecting...");
            mConnectingDlg.setCancelable(false);
            mConnector = new P25Connector(new P25Connector.P25ConnectionListener() {
                @Override
                public void onStartConnecting() {
                    //mConnectingDlg.show();
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
                public void onConnectionCancelled() {
                    //mConnectingDlg.dismiss();
                }

                @Override
                public void onDisconnected() {
                    //showDisonnected();
                }
            });
            prduct_name.setTag("");
            Billno.setText(join);
            Log.e("RANDOM_VALUE", "yyyyyyyyyyyyyyyyyyyyy" + join);



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


            add_item.setOnClickListener(new OnClickListener() {
                                            @Override
                                            public void onClick(View v) {

                                                Log.e("tag","grand total"+price);
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
                                                        product_pprice.add(sprice);
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
                                                        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(getActivity());
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
                                                    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(getActivity());
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

                                                    build = new AlertDialog.Builder(getActivity());
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
                                                                public void onClick(DialogInterface dialog, int which) {
                                                                    dialog.cancel();
                                                                }
                                                            });
                                                    AlertDialog alert = build.create();
                                                    alert.show();
                                                }
                                            }

            );
        }
        IntentFilter filter = new IntentFilter();
        filter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
        filter.addAction(BluetoothDevice.ACTION_FOUND);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        filter.addAction(BluetoothDevice.ACTION_BOND_STATE_CHANGED);


        mail.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {


                String billno = Billno.getText().toString();

                String Query = "SELECT * FROM " + DbHelper.TABLE_NAME2 + " WHERE " + DbHelper.BILL_NO + "=" + billno;

                Cursor c3 = mHelper.fetchdata1(Query);

                if (c3 != null) {
                    if (c3.moveToFirst()) {
                        do {


                            sendMail();
                        }
                        while (c3.moveToNext());
                    } else {
                        saveData();
                        sendMail();


                    }
                }
            }
        });

        save.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                String billno = Billno.getText().toString();

                String Query = "SELECT * FROM " + DbHelper.TABLE_NAME2 + " WHERE " + DbHelper.BILL_NO + "=" + billno;

                Cursor c3 = mHelper.fetchdata1(Query);

                if (c3 != null) {
                    if (c3.moveToFirst()) {
                        do {



                            Toast.makeText(getActivity(),"data stored",Toast.LENGTH_LONG).show();
                        }
                        while (c3.moveToNext());
                    } else {
                        saveData();



                    }
                }
            }
        });

        print.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String billno = Billno.getText().toString();

                String Query = "SELECT * FROM " + DbHelper.TABLE_NAME2 + " WHERE " + DbHelper.BILL_NO + "=" + billno;

                Cursor c3 = mHelper.fetchdata1(Query);

               /* if (c3 == null) {
                    Toast.makeText(getActivity(),"hello",Toast.LENGTH_LONG).show();

                }*/

                if (c3 != null) {
                    if (c3.moveToFirst()) {
                        do {

                            printstmt();

                        }
                        while (c3.moveToNext());
                    } else {
                        saveData();
                        printstmt();





                    }
                }
            }

        });
        return view;
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






    private void displayData() {

        Bill_display_adpter disadpt = new Bill_display_adpter(getActivity(), product_name1, product_pprice, product_qty, product_price);
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

    }





    public void saveData() {
        g_total = toatl.getText().toString();
        cus_name = editText_cname.getText().toString();
        Log.e("tag", "ccc" + cus_name);
        String tab_pos;
        tab_pos = String.valueOf(number);
        dataBase = mHelper.getWritableDatabase();

        Log.d("tag", sl_no.get(0));

        Log.d("tag", product_price.get(0));
        Log.d("tag", product_qty.get(0));
        Log.d("tag", product_pprice.get(0));
        ArrayList<HashMap<String, String>> orderList = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> map = new HashMap<String, String>();


        DbHelper dbh = new DbHelper(getActivity());
        for (int i = 0; i < sl_no.size(); i++) {
            String no = sl_no.get(i);
            String p_name = product_name1.get(i);
            Log.e("tag_qqqq", product_name1.get(i));
            String p_price = product_price.get(i);
            String per_prz = product_pprice.get(i);
            String p_qty = product_qty.get(i);


            dbh.insert1(dbh, c_date, join, cus_name, tab_pos, p_name, per_prz, p_qty, p_price);
            //dbh.insert3(dbh, c_date, join, cus_name, tab_pos, g_total);
            Log.e("tag", "Choose Table" + tab_pos);
            Log.e("tag", "Customer Name" + cus_name);
            Log.e("tag", "Grand Total" + g_total);
            map.put("No", no);
            map.put("P_Name", p_name);
            map.put("P_Price", p_price);
            map.put("P_Qty", p_qty);
            orderList.add(map);


        }

        ContentValues value = new ContentValues();
        value.put(DbHelper.DATE, c_date);
        value.put(DbHelper.BILL_NO, join);
        value.put(DbHelper.CUSTOMER_NAME,cus_name);
        value.put(DbHelper.SET_TABLE,tab_pos);
        value.put(DbHelper.GRAND_TOTAL, g_total);
        Log.e("RANDOM_VALUE", "totalvalue" + prics);


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
            userList.setEnabled(false);

        }

    public void printstmt()
    {
        Toast.makeText(getActivity(),"Printing..",Toast.LENGTH_LONG).show();
        TelephonyManager tm = (TelephonyManager) getActivity().getSystemService(Context.TELEPHONY_SERVICE);
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
        mBtp.printTextLine(currentDateTime + "  BillNo:" + join);
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
        mBtp.printTextLine("Total                          " + prics);
        mBtp.printTextLine("------------------------------------------");
        mBtp.printLineFeed();
        mBtp.printTextLine("    Designed & Developed by SQIndia.net");
        mBtp.printTextLine("           Contact:91 8526571169");
        mBtp.printLineFeed();
        mBtp.printTextLine("Bill received from:" + device_id);
        mBtp.printLineFeed();
        mBtp.printTextLine("****************************************");
        mBtp.printLineFeed();

        userList.setEnabled(false);



    }

}
