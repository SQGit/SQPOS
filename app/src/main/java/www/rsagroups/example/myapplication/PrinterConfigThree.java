package www.rsagroups.example.myapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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
import com.ngx.BluetoothPrinter;
import com.ngx.BtpCommands;

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
public class PrinterConfigThree extends Activity {
    private Button mConnectBtn;
    private Button mEnableBtn;
    private Spinner mDeviceSp;
    EditText editText_cname;
    private BluetoothPrinter mBtp = BluetoothPrinterMain.mBtp;

    //private Button mPrintBarcodeBtn;

    String ss;
    private com.rey.material.widget.Spinner spinner1;
    String[] table_no;

    Context context = this;
    private AlertDialog.Builder build;

    Button add_item, mail, print;
    FloatingSearchView prduct_name;
    String qty, O_old_Query, O_new_Query,get_customername;
    EditText product_qtyz;
    TextView Total_amount;
    TextView Billno;
    TextView toatl;
    public int numval;
    String join, date, storeval;
    ProgressDialog dialog;
    Double pric, qtye;
    String totalvalue, sprice, value, prics;
    String a, b, c;
    GMailSender sender;
    int aa, bb, cc;


    private ProgressDialog mProgressDlg;
    private ProgressDialog mConnectingDlg;

    private BluetoothAdapter mBluetoothAdapter;

    private P25Connector mConnector;

    private ArrayList<BluetoothDevice> mDeviceList = new ArrayList<BluetoothDevice>();


    private ArrayList<String> sl_no = new ArrayList<String>();
    private ArrayList<String> product_name1 = new ArrayList<String>();
    private ArrayList<String> product_price = new ArrayList<String>();
    private ArrayList<String> product_qty = new ArrayList<String>();


    private ListView userList;
    public DbHelper mHelper;
    private SQLiteDatabase dataBase;
    public static String pn, pp, billno, tot, price;
    private boolean isUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.printerconfig3);

        spinner1 = (com.rey.material.widget.Spinner) findViewById(R.id.spinner1);
        table_no= new String[]{"TABLE 1","TABLE 2","TABLE 3","TABLE 4","TABLE 5","TABLE 6","TABLE 7","TABLE 8","TABLE 9","TABLE 10"};



        editText_cname=(EditText)findViewById(R.id.customername);
       /* mConnectBtn.setVisibility(View.GONE);
        mEnableBtn.setVisibility(View.GONE);
        mDeviceSp.setVisibility(View.GONE);*/

        //prduct_name.requestFocusFromTouch();
        Random rand = new Random();
        final int pickedNumber = rand.nextInt(100000);
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat month_date = new SimpleDateFormat("dMM");
        SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yyyy  hh:mm:ss ");
        String month_name = month_date.format(cal.getTime());
        get_customername=editText_cname.getText().toString();
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


        mConnectBtn = (Button) findViewById(R.id.btn_connect);
        mEnableBtn = (Button) findViewById(R.id.btn_enable);
        //mPrintDemoBtn = (Button) findViewById(R.id.btn_print_demo);
        //mPrintBarcodeBtn = (Button) findViewById(R.id.btn_print_barcode);
        //mPrintImageBtn = (Button) findViewById(R.id.btn_print_image);
        //mPrintReceiptBtn = (Button) findViewById(R.id.btn_print_receipt);
        //mPrintTextBtn = (Button) findViewById(R.id.btn_print_text);
        mDeviceSp = (Spinner) findViewById(R.id.sp_device);

        spinner1.setOnItemSelectedListener(new com.rey.material.widget.Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(com.rey.material.widget.Spinner parent, View view, int position, long id) {
                spinner1.getSelectedItem().toString();
                int uu=spinner1.getSelectedItemPosition();
                ss="Table"+uu;
                Log.e("tag","spin value"+ss);
            }
        });

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        if (mBluetoothAdapter == null) {
            showUnsupported();
        } else {
            if (!mBluetoothAdapter.isEnabled()) {
                showDisabled();
            } else {
                showEnabled();

                Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();

                if (pairedDevices != null) {
                    mDeviceList.addAll(pairedDevices);

                    updateDeviceList();
                }
            }

            mProgressDlg = new ProgressDialog(this);

            mProgressDlg.setMessage("Scanning...");
            mProgressDlg.setCancelable(false);
            mProgressDlg.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();

                    mBluetoothAdapter.cancelDiscovery();
                }
            });

            mConnectingDlg = new ProgressDialog(this);

            mConnectingDlg.setMessage("Connecting...");
            mConnectingDlg.setCancelable(false);

            mConnector = new P25Connector(new P25Connector.P25ConnectionListener() {

                @Override
                public void onStartConnecting() {
                    mConnectingDlg.show();
                }

                @Override
                public void onConnectionSuccess() {
                    mConnectingDlg.dismiss();

                    showConnected();
                }

                @Override
                public void onConnectionFailed(String error) {
                    mConnectingDlg.dismiss();
                }

                @Override
                public void onConnectionCancelled() {
                    mConnectingDlg.dismiss();
                }

                @Override
                public void onDisconnected() {
                    showDisonnected();
                }
            });

            //enable bluetooth
            mEnableBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);

                    startActivityForResult(intent, 1000);
                }
            });


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


                    final Dialog dialog = new Dialog(PrinterConfigThree.this);

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


                }
            });

            userList = (ListView) findViewById(R.id.Bill_list);

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
                                                        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(PrinterConfigThree.this);
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
                                                    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(PrinterConfigThree.this);
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

                                                    build = new AlertDialog.Builder(PrinterConfigThree.this);
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


            //connect/disconnect
            mConnectBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    connect();
                }
            });

            //print demo text
            print.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    print();
                    // printDemoContent();
                }
            });


        }

        IntentFilter filter = new IntentFilter();

        filter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
        filter.addAction(BluetoothDevice.ACTION_FOUND);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        filter.addAction(BluetoothDevice.ACTION_BOND_STATE_CHANGED);

        registerReceiver(mReceiver, filter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_scan) {
            mBluetoothAdapter.startDiscovery();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPause() {
        if (mBluetoothAdapter != null) {
            if (mBluetoothAdapter.isDiscovering()) {
                mBluetoothAdapter.cancelDiscovery();
            }
        }

        if (mConnector != null) {
            try {
                mConnector.disconnect();
            } catch (P25ConnectionException e) {
                e.printStackTrace();
            }
        }

        super.onPause();
    }

    @Override
    public void onDestroy() {
        unregisterReceiver(mReceiver);

        super.onDestroy();
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

    private void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    private void updateDeviceList() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.simple_spinner_item, getArray(mDeviceList));

        adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);

        mDeviceSp.setAdapter(adapter);
        mDeviceSp.setSelection(0);
    }

    private void showDisabled() {
        showToast("Bluetooth disabled");

        mEnableBtn.setVisibility(View.VISIBLE);
        mConnectBtn.setVisibility(View.GONE);
        mDeviceSp.setVisibility(View.GONE);
    }

    private void showEnabled() {
        showToast("Bluetooth enabled");

        mEnableBtn.setVisibility(View.GONE);
        mConnectBtn.setVisibility(View.VISIBLE);
        mDeviceSp.setVisibility(View.VISIBLE);
    }

    private void showUnsupported() {
        showToast("Bluetooth is unsupported by this device");

        mConnectBtn.setEnabled(false);

        mDeviceSp.setEnabled(false);
    }

    private void showConnected() {
        showToast("Connected");

        mConnectBtn.setText("Disconnect");


        mDeviceSp.setEnabled(false);
    }

    private void showDisonnected() {
        showToast("Disconnected");

        mConnectBtn.setText("Connect");


        mDeviceSp.setEnabled(true);
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
                showToast("Failed to pair device");

                return;
            }
        }

        try {
            if (!mConnector.isConnected()) {
                mConnector.connect(device);
            } else {
                mConnector.disconnect();

                showDisonnected();
            }
        } catch (P25ConnectionException e) {
            e.printStackTrace();
        }
    }

    private void createBond(BluetoothDevice device) throws Exception {

        try {
            Class<?> cl = Class.forName("android.bluetooth.BluetoothDevice");
            Class<?>[] par = {};

            Method method = cl.getMethod("createBond", par);

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

    private void print()

    {
        mBtp.setPrintFontStyle(BtpCommands.FONT_STYLE_REGULAR);
        mBtp.setPrintFontSize(BtpCommands.FONT_SIZE_NORMAL);
        mBtp.printLogo();
        mBtp.printLineFeed();
        mBtp.printTextLine("----------------------------------------");
        mBtp.printTextLine("Item              Quantity        Price");
        mBtp.printTextLine("----------------------------------------");
        mBtp.printTextLine("Item 1                1            1.00");
        mBtp.printTextLine("Some big item        10         7890.00");
        mBtp.printTextLine("Next Item           999        10000.00");
        mBtp.printLineFeed();
        mBtp.printTextLine("----------------------------------------");
        mBtp.printTextLine("Total                          17891.00");
        mBtp.printTextLine("----------------------------------------");
        mBtp.printLineFeed();
        mBtp.printTextLine("         Thank you ! Visit Again");
        mBtp.printLineFeed();
        mBtp.printTextLine("****************************************");
        mBtp.printLineFeed();

    }


        private void printDemoContent()
        {


        //heading
        String titleStr = "HABITAT" + "\n";

        //billno
        String content = "Bill NO:\t" + join;

        //title
        String titlehead = "SNO\t" + "ITEM NAME\t" + "QTY\t" + "PRICE";

        String grandtot = "GRAND TOT=" + totalvalue;

        String values = sl_no + "\n" + product_name1 + "\n" + product_qty + "\n" + product_price;


        byte[] formats = {(byte) 0x1d, (byte) 0x6b, (byte) 0x02, (byte) 0x0d};
        byte[] contents = Printer.printfont(content, FontDefine.FONT_24PX, FontDefine.Align_CENTER, (byte) 0x1A, PocketPos.LANGUAGE_ENGLISH);

        byte[] grantot = Printer.printfont(grandtot, FontDefine.FONT_24PX, FontDefine.Align_CENTER, (byte) 0x1A, PocketPos.LANGUAGE_ENGLISH);

        byte[] titleByte = Printer.printfont(titleStr, FontDefine.FONT_48PX_HEIGHT, FontDefine.Align_CENTER,
                (byte) 0x1A, PocketPos.LANGUAGE_ENGLISH);


        byte[] titleByte1 = Printer.printfont(titlehead, FontDefine.FONT_24PX, FontDefine.Align_LEFT,
                (byte) 0x1A, PocketPos.LANGUAGE_ENGLISH);


        byte[] titleByte2 = Printer.printfont(values, FontDefine.FONT_24PX, FontDefine.Align_LEFT,
                (byte) 0x1A, PocketPos.LANGUAGE_ENGLISH);


        byte[] totalByte = new byte[titleByte.length + contents.length + formats.length + titleByte1.length + titleByte2.length + grantot.length];

        int offset = 0;
        System.arraycopy(titleByte, 0, totalByte, offset, titleByte.length);
        offset += titleByte.length;

        System.arraycopy(titleByte1, 0, totalByte, offset, titleByte1.length);
        offset += titleByte1.length;

        System.arraycopy(titleByte2, 0, totalByte, offset, titleByte2.length);
        byte[] senddata = PocketPos.FramePack(PocketPos.FRAME_TOF_PRINT, totalByte, 0, totalByte.length);

        System.arraycopy(formats, 0, totalByte, 0, formats.length);
        System.arraycopy(contents, 0, totalByte, formats.length, contents.length);
        sendData(senddata);
    }

    private void printText(String text) {
        byte[] line = Printer.printfont(text + "\n\n", FontDefine.FONT_32PX, FontDefine.Align_CENTER, (byte) 0x1A,
                PocketPos.LANGUAGE_ENGLISH);
        byte[] senddata = PocketPos.FramePack(PocketPos.FRAME_TOF_PRINT, line, 0, line.length);

        sendData(senddata);
    }

    private void print1DBarcode() {
        String content = "6901234567892";

        //1D barcode format (hex): 1d 6b 02 0d + barcode data

        byte[] formats = {(byte) 0x1d, (byte) 0x6b, (byte) 0x02, (byte) 0x0d};
        byte[] contents = content.getBytes();

        byte[] bytes = new byte[formats.length + contents.length];

        System.arraycopy(formats, 0, bytes, 0, formats.length);
        System.arraycopy(contents, 0, bytes, formats.length, contents.length);

        sendData(bytes);

        byte[] newline = Printer.printfont("\n\n", FontDefine.FONT_32PX, FontDefine.Align_CENTER, (byte) 0x1A, PocketPos.LANGUAGE_ENGLISH);

        sendData(newline);
    }

    private void print2DBarcode() {
        String content = "Lorenz Blog - www.londatiga.net";

        //2D barcode format (hex) : 1d 6b 10 00 00 00 00 00 1f + barcode data

        byte[] formats = {(byte) 0x1d, (byte) 0x6b, (byte) 0x10, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
                (byte) 0x00, (byte) 0x1f};
        byte[] contents = content.getBytes();
        byte[] bytes = new byte[formats.length + contents.length];

        System.arraycopy(formats, 0, bytes, 0, formats.length);
        System.arraycopy(contents, 0, bytes, formats.length, contents.length);

        sendData(bytes);

        byte[] newline = Printer.printfont("\n\n", FontDefine.FONT_32PX, FontDefine.Align_CENTER, (byte) 0x1A, PocketPos.LANGUAGE_ENGLISH);

        sendData(newline);
    }


    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            if (BluetoothAdapter.ACTION_STATE_CHANGED.equals(action)) {
                final int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, BluetoothAdapter.ERROR);

                if (state == BluetoothAdapter.STATE_ON) {
                    showEnabled();
                } else if (state == BluetoothAdapter.STATE_OFF) {
                    showDisabled();
                }
            } else if (BluetoothAdapter.ACTION_DISCOVERY_STARTED.equals(action)) {
                mDeviceList = new ArrayList<BluetoothDevice>();

                mProgressDlg.show();
            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                mProgressDlg.dismiss();

                updateDeviceList();
            } else if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = (BluetoothDevice) intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

                mDeviceList.add(device);

                showToast("Found device " + device.getName());
            } else if (BluetoothDevice.ACTION_BOND_STATE_CHANGED.equals(action)) {
                final int state = intent.getIntExtra(BluetoothDevice.EXTRA_BOND_STATE, BluetoothDevice.ERROR);

                if (state == BluetoothDevice.BOND_BONDED) {
                    showToast("Paired");

                    connect();
                }
            }
        }
    };

    @Override
    protected void onResume() {
        displayData();
        super.onResume();
    }

    private void displayData() {

        //Bill_display_adpter disadpt = new Bill_display_adpter(PrinterConfigThree.this, sl_no, product_name1, product_qty, product_price);
        //userList.setAdapter(disadpt);
        double price = 0;
        for (String itemprice : product_price) {
            price += Double.parseDouble(itemprice);
        }
        prics = String.valueOf(price);
        a = String.valueOf(sl_no.size());

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
