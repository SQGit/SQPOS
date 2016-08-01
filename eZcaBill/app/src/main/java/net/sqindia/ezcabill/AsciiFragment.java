package net.sqindia.ezcabill;

import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.ngx.BluetoothPrinter;
import com.ngx.BtpCommands;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by $Krishna on 18-06-2016.
 */
public class AsciiFragment extends Fragment {
    EditText edit_id, edit_name, edit_phone, edit_email, edit_plan, edit_address;
    TextView tv_amount;
    String s, s1, s2, s3, s4, s5, s6, s7;
    LinearLayout iback;
    RadioGroup payment;
    RadioButton paid, unpaid;
    String asd;
    Button edit, print;
    LinearLayout dot;
    int i;

    String b1,b2,b3;
    TextView txt_billing,txt_id,txt_name,txt_phone,txt_email,txt_address,txt_plan,txt_amount,txt_payment,txt_currency;

    int year;
    int month;
    int day;

    String bill_date;

    String device_num,device_id;

    SQLiteDatabase db;
    Cursor c;
    static final String SELECT_SQL = "SELECT * FROM persons";

    private BluetoothPrinter mBtp = BluetoothPrinterMain.mBtp;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

     /*   FragmentManager fm = getFragmentManager();
        fm.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                if(getFragmentManager().getBackStackEntryCount() == 0) getActivity().this finish();
            }
        });
*/
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.billing, container, false);
        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        final Calendar c1 = Calendar.getInstance();
        year = c1.get(Calendar.YEAR);
        month = c1.get(Calendar.MONTH);
        day = c1.get(Calendar.DAY_OF_MONTH);


        db = getActivity().openOrCreateDatabase("PersonDB", Context.MODE_PRIVATE, null);
        edit_id = (EditText) view.findViewById(R.id.editTextId);
        edit_name = (EditText) view.findViewById(R.id.editTextName);
        edit_phone = (EditText) view.findViewById(R.id.editTextPhone);
        edit_email = (EditText) view.findViewById(R.id.editTextEmail);
        edit_address = (EditText) view.findViewById(R.id.editTextAddress);
        edit_plan = (EditText) view.findViewById(R.id.editinternetplan);
        dot= (LinearLayout) view.findViewById(R.id.lin_dots);

        tv_amount = (TextView) view.findViewById(R.id.plan_amount);
        txt_billing = (TextView) view.findViewById(R.id.text_billing);
        txt_id = (TextView) view.findViewById(R.id.textViewId);
        txt_name = (TextView) view.findViewById(R.id.textViewName);
        txt_phone = (TextView) view.findViewById(R.id.textViewPhone);
        txt_email = (TextView) view.findViewById(R.id.textViewEmail);
        txt_address = (TextView) view.findViewById(R.id.textViewAddress);
        txt_plan = (TextView) view.findViewById(R.id.textviewplan);
        txt_amount = (TextView) view.findViewById(R.id.textviewamount);
        txt_payment = (TextView) view.findViewById(R.id.textviewpayment);
        txt_currency = (TextView) view.findViewById(R.id.currency);

        payment = (RadioGroup) view.findViewById(R.id.radioGroup2);
        paid = (RadioButton) view.findViewById(R.id.radio3);
        unpaid = (RadioButton) view.findViewById(R.id.radio4);

       // edit = (Button) view.findViewById(R.id.btnEdit);
        print = (Button) view.findViewById(R.id.btnprin);

        iback = (LinearLayout) view.findViewById(R.id.layout_back);

        Typeface tf = Typeface.createFromAsset(getActivity().getAssets(), "reg.TTF");
        txt_billing.setTypeface(tf);
        txt_id.setTypeface(tf);
        edit_id.setTypeface(tf);
        txt_name.setTypeface(tf);
        edit_name.setTypeface(tf);
        txt_phone.setTypeface(tf);
        edit_phone.setTypeface(tf);
        txt_email.setTypeface(tf);
        edit_email.setTypeface(tf);
        txt_address.setTypeface(tf);
        edit_address.setTypeface(tf);
        txt_plan.setTypeface(tf);
        edit_plan.setTypeface(tf);
        txt_amount.setTypeface(tf);
        tv_amount.setTypeface(tf);
        txt_payment.setTypeface(tf);
        paid.setTypeface(tf);
        unpaid.setTypeface(tf);
        print.setTypeface(tf);
        txt_currency.setTypeface(tf);


        c = db.rawQuery(SELECT_SQL, null);
        c.moveToFirst();

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        b1 = sharedPreferences.getString("s1", "");
        b2 = sharedPreferences.getString("s2", "");

        b3 = sharedPreferences.getString("s3", "");
        Log.e("tag", "billone" + b3);

        txt_currency.setText(b3);
        Log.e("tag", "symbol " + txt_currency);

        edit_name.setEnabled(false);
        edit_phone.setEnabled(false);
        edit_email.setEnabled(false);
        edit_address.setEnabled(false);
        edit_plan.setEnabled(false);
        paid.setEnabled(false);
        unpaid.setEnabled(false);

        iback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), Bill_Payment.class);
                startActivity(i);
                getActivity().finish();
            }
        });



       /* dot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"hello",Toast.LENGTH_SHORT).show();
            }
        });*/

 dot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                final PopupMenu popup = new PopupMenu(getActivity(), dot);
                popup.getMenuInflater().inflate(R.menu.opt_menu, popup.getMenu());



                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {


                        switch (item.getItemId()) {

                            case R.id.item1:


                                AlertDialog.Builder d = new AlertDialog.Builder(getActivity());
                                TextView content = new TextView(getActivity());
                                content.setText("on another font");
                               // content.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "appfont.OTF"));

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

        print.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                bill_date = String.valueOf(new StringBuilder().append(day).append("/").append(month + 1).append("/").append(year).append(""));
                Log.e("TAg", "" + bill_date);


                String bdate = bill_date.toString().trim();
                String id = edit_id.getText().toString().trim();


                String sql = "UPDATE persons SET billing_date='" + bdate + "',payment ='paid' WHERE id=" + id + ";";
                Log.d("tag", sql);

                db.execSQL(sql);
                Toast.makeText(getActivity(), "Records Saved Successfully", Toast.LENGTH_LONG).show();
                c = db.rawQuery(SELECT_SQL, null);
                c.moveToPosition(Integer.parseInt(id));
printstmt();

            }
        });


        Bundle extras = getActivity().getIntent().getExtras();
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
        edit_plan.setText(s4);
        edit_address.setText(s5);
        tv_amount.setText(s7);

        if (s6.equals("paid")) {

            paid.setChecked(true);
            Log.d("tag", s6);
        } else if (s6.equals("Unpaid")) {
            unpaid.setChecked(true);

        }



        return view;



    }



    private void printstmt() {


        String intplan= edit_plan.getText().toString();
        String amountv =  tv_amount.getText().toString();
        String name1 = edit_name.getText().toString();

        TelephonyManager tm = (TelephonyManager) getActivity().getSystemService(Context.TELEPHONY_SERVICE);
        String mPhoneNumber = tm.getLine1Number();
        String mDeviceId = tm.getDeviceId();
        device_id = mDeviceId;
        Log.e("TAG", "phone id" + mDeviceId);
        device_num = mPhoneNumber;
        Log.e("TAG", "phone number" + mPhoneNumber);

        mBtp.setPrintFontStyle(BtpCommands.FONT_STYLE_BOLD);
        mBtp.setAlignment(BtpCommands.CENTER_ALIGN);
        mBtp.printTextLine(b1);
        mBtp.printTextLine("  Contact No : " + b2);

        mBtp.setPrintFontStyle(BtpCommands.FONT_STYLE_BOLD);
        mBtp.setPrintFontSize(BtpCommands.FONT_SIZE_DOUBLE_W_H);
        mBtp.setAlignment(BtpCommands.CENTER_ALIGN);
        mBtp.printTextLine(" eZcaBill " );

        mBtp.setPrintFontStyle(BtpCommands.FONT_STYLE_REGULAR);
        mBtp.setPrintFontSize(BtpCommands.FONT_SIZE_NORMAL);
        mBtp.setAlignment(BtpCommands.LEFT_ALIGN);



        //  mBtp.printLogo();
      //  mBtp.printLineFeed();
        mBtp.printTextLine("Date: "+bill_date+ "   Phone No.: "+device_num);
        mBtp.printTextLine("----------------------------------------");
        mBtp.printTextLine("Name            Plan          Amount");
        mBtp.printTextLine("----------------------------------------");
        mBtp.printTextLine(formatToPrint(name1,intplan,b3+amountv));
       // mBtp.printTextLine(" "+name1+ "          "+ "  "+intplan+ "          "+  "  "+amountv);
      //  mBtp.printTextLine("Some big item        10         7890.00");
      //  mBtp.printTextLine("Next Item           999        10000.00");
      //  mBtp.printLineFeed();
      //  mBtp.printTextLine("----------------------------------------");
        mBtp.printLineFeed();
        mBtp.printTextLine("   AMOUNT  : PAID ");
        mBtp.printTextLine("----------------------------------------");
      //  mBtp.printLineFeed();
     //   mBtp.printTextLine("In God we trust; all others must pay cash");
        mBtp.setPrintFontStyle(BtpCommands.FONT_STYLE_BOLD);
        mBtp.printTextLine("  Bill received from : " + device_id);
      //  mBtp.printTextLine("Phone Number : "+device_num );
        mBtp.setPrintFontStyle(BtpCommands.FONT_STYLE_REGULAR);
        mBtp.printTextLine("           In God we trust,            ");
        mBtp.printTextLine("       All others must pay cash        ");
        mBtp.setPrintFontStyle(BtpCommands.FONT_STYLE_BOLD);
        mBtp.printTextLine("  Designed & Developed by SQIndia.net  ");
        mBtp.setPrintFontStyle(BtpCommands.FONT_STYLE_REGULAR);
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


    public String formatToPrint(String itms, String qtys, String perprice) {
        char item[] = new char[13];
        itms = itms.length() > 11 ? itms.substring(0, 11) + ".." : itms;
        char[] titms = itms.toCharArray();
        int size = titms.length;
        for (int i = 0; i < item.length; i++) {
            item[i] = i < size ? titms[i] : ' ';
        }

        itms = new String(item);
        char qty[] = new char[7];
        qtys = qtys.length() > 6 ? qtys.substring(0, 6) : qtys;
        char[] tqtys = qtys.toCharArray();
        size = tqtys.length;
        int diff = qty.length - tqtys.length;
        for (int i = qty.length - 1; i >= 0; i--) {
            qty[i] = i >= diff ? tqtys[i - diff] : ' ';
        }

        qtys = new String(qty);

        char qtyp[] = new char[11];
        perprice = perprice.length() > 7 ? perprice.substring(0, 7) : perprice;
        char[] ptqtys = perprice.toCharArray();
        size = ptqtys.length;
        diff = qtyp.length - ptqtys.length;
        for (int i = qtyp.length - 1; i >= 0; i--) {
            qtyp[i] = i >= diff ? ptqtys[i - diff] : ' ';
        }

        perprice = new String(qtyp);


        return (itms + "  " + qtys + "   " + perprice );
    }
    @Override
    public void onResume() {
        super.onResume();

        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK){
                    // handle back button's click listener
                    return true;
                }
                return false;
            }
        });
    }

}