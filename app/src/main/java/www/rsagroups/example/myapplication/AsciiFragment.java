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
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
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
import java.util.Random;
import java.util.Set;

public class AsciiFragment extends Fragment {
    public static String pn, pp, price;
    public Integer number;
    public DbHelper mHelper;
    File sd1, data;
    EditText editText_cname;
    ArrayList<HashMap<String, String>> orderList = new ArrayList<HashMap<String, String>>();
    HashMap<String, String> map = new HashMap<String, String>();
    Button add_item, mail, print, save;
    FloatingSearchView prduct_name;
    String O_old_Query, O_new_Query;
    ImageView backic;
    EditText product_qtyz;
    TextView Total_amount;
    TextView Billno, bill_txt;
    TextView toatl, bottom;
    TextView in, ip, qt, am, or1, or2;
    String join, date, storeval, get_customername;
    ProgressDialog dialog;
    Double pric, qtye;
    String sprice, value, prics, device_num, device_id, time;
    String a, b, c;
    //  GMailSender sender;
    String cus_name, g_total, currency, token_mail;
    int aa, bb, cc;
    String ss;
    Typeface tf;
    Spinner spinner1;
    //String[] table_no;
    int ij, inc = 0;
    String currentDateTime = DateFormat.getDateTimeInstance().format(new Date());
    String sos, token_footer;
    SimpleDateFormat sd = new SimpleDateFormat("d/M/yyyy");
    final String c_date = sd.format(new Date());
    String b1, b2, b3, b4, b5;
    private AlertDialog.Builder build;
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
    private SQLiteDatabase dataBase;
    private boolean isUpdate;
    private BluetoothPrinter mBtp = BluetoothPrinterMain.mBtp;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.printerconfig3, container, false);
        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        editText_cname = (EditText) view.findViewById(R.id.editText_customer);
        add_item = (Button) view.findViewById(R.id.add_bill);
        mail = (Button) view.findViewById(R.id.sendmail);
        print = (Button) view.findViewById(R.id.print);
        save = (Button) view.findViewById(R.id.save);
        Billno = (TextView) view.findViewById(R.id.textbillval);
        bill_txt = (TextView) view.findViewById(R.id.textBillno);
        bottom = (TextView) view.findViewById(R.id.textrights);
        in = (TextView) view.findViewById(R.id.txt_fName);
        ip = (TextView) view.findViewById(R.id.txt_pprz);
        qt = (TextView) view.findViewById(R.id.txt_lName);
        am = (TextView) view.findViewById(R.id.txt_price);
        userList = (ListView) view.findViewById(R.id.Bill_list);
        Total_amount = (TextView) view.findViewById(R.id.toatlamt);
        toatl = (TextView) view.findViewById(R.id.grand_tot);
        prduct_name = (FloatingSearchView) view.findViewById(R.id.product_name);
        product_qtyz = (EditText) view.findViewById(R.id.product_qty);


       /* product_name1.add("first");
        product_qty.add("5");
        product_price.add("15");
        product_pprice.add("3");
        sl_no.add("" + (sl_no.size() + 1));*/


        tf = Typeface.createFromAsset(getActivity().getAssets(), "appfont.OTF");
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
        editText_cname.setTypeface(tf);
        product_qtyz.setTypeface(tf);


        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        token_footer = sharedPreferences.getString("footer", "");
        // Log.e("tag", "getvalue" + token_footer);
        bottom.setText(token_footer);
        token_mail = sharedPreferences.getString("mail", "");


        b1 = sharedPreferences.getString("s1", "");
        b2 = sharedPreferences.getString("s2", "");
        b3 = sharedPreferences.getString("s3", "");
        b4 = sharedPreferences.getString("s4", "");
        //  Log.e("tag", "billone" + b1);

        currency = sharedPreferences.getString("send2", "");

        String[] values = {" Select Table", "  Table 1", "  Table 2", "  Table 3", "  Table 4", "  Table 5", "  Table 6", "  Table 7", "  Table 8", "  Table 9", "  Table 10"};
        spinner1 = (Spinner) view.findViewById(R.id.spinner1);
        //ArrayAdapter<String> LTRadapter = new ArrayAdapter<String>(this.getActivity(), R.layout.spinner_item, values);
        final CustomAdapter arrayAdapter = new CustomAdapter(getActivity(), R.layout.spinner_item, values) {
            public boolean isEnabled(int position) {
                if (position == 0) {
                    return false;
                } else {
                    return true;
                }
            }

            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                tv.setTypeface(tf);
                return view;
            }
        };
        spinner1.setAdapter(arrayAdapter);


        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                spinner1.getSelectedItem().toString();

                int uu = spinner1.getSelectedItemPosition();
                ss = "Table" + uu;
                AsciiFragment.this.number = spinner1.getSelectedItemPosition();
                //   Log.e("tag", "position" + number);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }
        });


        TelephonyManager telephony_manager = (TelephonyManager) getActivity().getSystemService(Context.TELEPHONY_SERVICE);

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
        // Log.e("TAG", "yyyyyyyyyyyyyyyyyyyyyy" + join);
        date = strDate;
        //  Log.e("TAG", "dateeeeeeeeeeeeeeeee" + date);

        mHelper = new DbHelper(getActivity());
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            //showUnsupported();
        } else {
            if (!mBluetoothAdapter.isEnabled()) {

            } else {


                Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();

                if (pairedDevices != null) {
                    mDeviceList.addAll(pairedDevices);
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
                }

                @Override
                public void onConnectionSuccess() {
                    mConnectingDlg.dismiss();
                }

                @Override
                public void onConnectionFailed(String error) {
                    mConnectingDlg.dismiss();
                }

                @Override
                public void onConnectionCancelled() {
                }

                @Override
                public void onDisconnected() {

                }
            });
            prduct_name.setTag("");
            Billno.setText(join);
            //   Log.e("RANDOM_VALUE", "yyyyyyyyyyyyyyyyyyyyy" + join);




            editText_cname.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                    if (actionId == EditorInfo.IME_ACTION_NEXT) {

                        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);


                        return true;
                    }
                    return false;
                }
            });













            prduct_name.setOnQueryChangeListener(new FloatingSearchView.OnQueryChangeListener() {
                @Override
                public void onSearchTextChanged(String old_Query, String new_Query) {

                    //   Log.d("tag", "" + old_Query + "" + new_Query);
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

                                                if (v != null) {
                                                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                                                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                                                }
                                                String cmn = editText_cname.getText().toString();
                                                String spim = spinner1.getSelectedItem().toString();
                                                String editTextString = product_qtyz.getText().toString();
                                                pn = prduct_name.getTag().toString();
                                                pp = product_qtyz.getText().toString();


                                                if (!(cmn.equals("") || (spim.equals("position0")))) {
                                                    if (!(editTextString.equals("0"))) {
                                                        if (!pn.equals("")) {
                                                            if (!pp.equals("")) {
                                                                getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

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


                                                    } else {
                                                        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(getActivity());
                                                        alertBuilder.setTitle("Invalid Data");
                                                        alertBuilder.setMessage("Please order minimum one ");
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
                                                    alertBuilder.setMessage("Please enter All ");
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
                            try {
                                sendMail();
                            } catch (IndexOutOfBoundsException e) {
                                Toast.makeText(getActivity(), "Please enter all details", Toast.LENGTH_SHORT).show();
                            }


                        }
                        while (c3.moveToNext());
                    } else {
                        try {
                            saveData();
                            sendMail();
                        } catch (IndexOutOfBoundsException e) {
                            Toast.makeText(getActivity(), "Please enter all details", Toast.LENGTH_SHORT).show();
                        }


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
                            try {


                                Toast.makeText(getActivity(), "data stored", Toast.LENGTH_LONG).show();
                            } catch (IndexOutOfBoundsException e) {
                                Toast.makeText(getActivity(), "Please enter all details", Toast.LENGTH_SHORT).show();
                            }
                        }
                        while (c3.moveToNext());
                    } else {
                        try {
                            saveData();
                        } catch (IndexOutOfBoundsException e) {
                            Toast.makeText(getActivity(), "Please enter all details", Toast.LENGTH_SHORT).show();
                        }


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


                if (c3 != null) {
                    if (c3.moveToFirst()) {
                        do {
                            try {
                                printstmt();
                            } catch (IndexOutOfBoundsException e) {
                                Toast.makeText(getActivity(), "Please enter all details", Toast.LENGTH_SHORT).show();
                            }
                        }
                        while (c3.moveToNext());
                    } else {
                        try {
                            saveData();
                            printstmt();
                        } catch (IndexOutOfBoundsException e) {
                            Toast.makeText(getActivity(), "Please enter all details", Toast.LENGTH_SHORT).show();
                        }
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
        for (int i = 0; i < item.length; i++) {
            item[i] = i < size ? titms[i] : ' ';
        }

        itms = new String(item);
        char qty[] = new char[5];
        qtys = qtys.length() > 5 ? qtys.substring(0, 5) : qtys;
        char[] tqtys = qtys.toCharArray();
        size = tqtys.length;
        int diff = qty.length - tqtys.length;
        for (int i = qty.length - 1; i >= 0; i--) {
            qty[i] = i >= diff ? tqtys[i - diff] : ' ';
        }

        qtys = new String(qty);

        char qtyp[] = new char[4];
        perprice = perprice.length() > 4 ? perprice.substring(0, 4) : perprice;
        char[] ptqtys = perprice.toCharArray();
        size = ptqtys.length;
        diff = qtyp.length - ptqtys.length;
        for (int i = qtyp.length - 1; i >= 0; i--) {
            qtyp[i] = i >= diff ? ptqtys[i - diff] : ' ';
        }

        perprice = new String(qtyp);

        char pri[] = new char[8];
        pris = pris.length() > 8 ? pris.substring(0, 8) : pris;
        char[] tpris = pris.toCharArray();
        size = tpris.length;
        diff = pri.length - tpris.length;
        for (int i = pri.length - 1; i >= 0; i--) {
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

        Log.e("tag", "My Total Price" + price);
        Log.e("tag", "My Product Name" + product_name1);
        Log.e("tag", "My Product Price" + product_pprice);
        Log.e("tag", "My Product Qty" + product_qty);
        Log.e("tag", "My Product Total" + product_price);


        Bill_display_adpter disadpt = new Bill_display_adpter(getActivity(), pn, pp, product_name1, product_pprice, product_qty, product_price);
        userList.setAdapter(disadpt);
        disadpt.notifyDataSetChanged();

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

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("username", cus_name);
        editor.putString("password", tab_pos);

        editor.commit();
        editor.commit();

        Log.e("tag", "ccc" + cus_name);

        dataBase = mHelper.getWritableDatabase();

//        Log.d("tag", sl_no.get(0));

        Log.d("tag", product_price.get(0));
        Log.d("tag", product_qty.get(0));
        Log.d("tag", product_pprice.get(0));
        ArrayList<HashMap<String, String>> orderList = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> map = new HashMap<String, String>();


        DbHelper dbh = new DbHelper(getActivity());
        for (int i = 0; i < sl_no.size(); i++) {
            String no = sl_no.get(i);
            String p_name = product_name1.get(i);
            Log.e("tag", "A1" + product_name1.get(i));

            String p_price = product_price.get(i);
            Log.e("tag", "A2" + product_price.get(i));

            String per_prz = product_pprice.get(i);
            Log.e("tag", "A3" + product_pprice.get(i));

            String p_qty = product_qty.get(i);
            Log.e("tag", "A4" + product_qty.get(i));

            dbh.insert1(dbh, c_date, join, cus_name, tab_pos, p_name, per_prz, p_qty, p_price);
            //dbh.insert3(dbh, c_date, join, cus_name, tab_pos, g_total);
            Log.e("tag", "Choose Table" + tab_pos);
            Log.e("tag", "Customer Name" + cus_name);
            Log.e("tag", "Grand Total" + g_total);

            Log.e("tag", "B1" + p_name);
            Log.e("tag", "B2" + per_prz);
            Log.e("tag", "B3" + p_qty);
            Log.e("tag", "B4" + p_price);

            map.put("No", no);
            map.put("P_Name", p_name);
            map.put("P_Price", p_price);
            map.put("P_Qty", p_qty);
            orderList.add(map);


        }

        ContentValues value = new ContentValues();
        value.put(DbHelper.DATE, c_date);
        value.put(DbHelper.BILL_NO, join);
        value.put(DbHelper.CUSTOMER_NAME, cus_name);
        value.put(DbHelper.SET_TABLE, tab_pos);
        value.put(DbHelper.GRAND_TOTAL, g_total);
        Log.e("RANDOM_VALUE", "totalvalue" + prics);


        dataBase.insert(DbHelper.TABLE_NAME2, null, value);
        dataBase.close();

    }

    public void sendMail() {
        storeval = "Mahindra World City" + "\n\t\t\t\t\t\t\tcanopy" + "\n\nHABITAT DETAILS:" + "\n\n\nDate:" + "\t" + c_date + "\nBillNo:\t" + join + "\nItem Name:\t" + product_name1 + "\nItem Price:\t" +
                product_pprice + "\nQuantity:\t" + product_qty + "\nTotal Amount:\t" + product_price + "\nGrand Total:\t" + currency + prics;
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto", token_mail, null));
        Log.e("tag", "mm" + token_mail);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject");
        emailIntent.putExtra(Intent.EXTRA_TEXT, storeval);
        startActivity(Intent.createChooser(emailIntent, "Send email..."));
        userList.setEnabled(false);

        exportDB();


    }

    public void printstmt() {

        Log.e("tag", "billone" + b1);

        cus_name = editText_cname.getText().toString();
        TelephonyManager tm = (TelephonyManager) getActivity().getSystemService(Context.TELEPHONY_SERVICE);
        String mPhoneNumber = tm.getLine1Number();
        String mDeviceId = tm.getDeviceId();
        device_num = mPhoneNumber;
        Log.e("TAG", "phone number" + mPhoneNumber);
        device_id = mDeviceId;
        Log.e("TAG", "phone id" + mDeviceId);
        mBtp.setPrintFontStyle(BtpCommands.FONT_STYLE_REGULAR);
        mBtp.setPrintFontSize(BtpCommands.FONT_SIZE_NORMAL);
        mBtp.printTextLine("                  " + b1);
        mBtp.printTextLine("          " + b2);
        mBtp.printTextLine("FSSAI:" + b3);
        mBtp.printLineFeed();
        mBtp.printTextLine("          Welcome : " + cus_name);
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
        mBtp.printTextLine("Total:                       " + currency + prics);
        mBtp.printTextLine("------------------------------------------");
        mBtp.printTextLine("    Designed & Developed by SQIndia.net");
        mBtp.printTextLine("           Contact:" + b4);
        mBtp.printTextLine("Bill received from:" + device_id);
        mBtp.printLineFeed();
        mBtp.printTextLine("****************************************");
        mBtp.printLineFeed();


        userList.setEnabled(false);
        Intent gomain = new Intent(getActivity(), MainActivity.class);
        startActivity(gomain);


    }


    private void exportDB() {
        sd1 = new File(Environment.getExternalStorageDirectory() + "/exported/");
        data = Environment.getDataDirectory();

        if (!sd1.exists()) {
            sd1.mkdirs();
        }

        FileChannel source = null;
        FileChannel destination = null;
        String currentDBPath = "/data/" + "www.rsagroups.example.myapplication" + "/databases/" + "userdata.db";
        String backupDBPath = "exportfile.db";


        File currentDB = new File(data, currentDBPath);
        File backupDB = new File(sd1, backupDBPath);


        try {
            source = new FileInputStream(currentDB).getChannel();
            destination = new FileOutputStream(backupDB).getChannel();
            destination.transferFrom(source, 0, source.size());
            source.close();
            destination.close();
            //Toast.makeText(this, "DB Exported!", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}



