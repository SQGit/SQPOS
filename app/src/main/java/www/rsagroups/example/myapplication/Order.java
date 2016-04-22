package www.rsagroups.example.myapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Admin on 04-03-2016.
 */
public class Order extends Activity {
    AutoCompleteTextView at;
    ListView lv2;
    Button b1;
    TextView head,copyright;

    AlertDialog dialog;
    SQLiteDatabase sqd;
    ArrayAdapter<String> adapter;
    String[] products1;

    Cursor cursor,cursor1;
    String name;
    ArrayList<HashMap<String,String>> billnos;
    DbHelper helper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trialxml);

        head=(TextView)findViewById(R.id.textView7);
        at=(AutoCompleteTextView)findViewById(R.id.autoCompleteTextView);
        b1=(Button)findViewById(R.id.back_icon);
        copyright=(TextView)findViewById(R.id.textrights);
        Typeface tf = Typeface.createFromAsset(getAssets(), "appfont.OTF");
        head.setTypeface(tf);
        at.setTypeface(tf);
        copyright.setTypeface(tf);


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(i);

            }
        });

        sqd = openOrCreateDatabase("userdata.db", MODE_PRIVATE, null);
       // cursor = sqd.rawQuery("SELECT " + helper.BILL_NO +"||"+helper.GRAND_TOTAL+ " FROM " + helper.TABLE_NAME2, null);


        cursor = sqd.rawQuery("SELECT * " + " FROM " + helper.TABLE_NAME2, null);
      //  cursor1 = sqd.rawQuery("SELECT * " + " FROM " + helper.TABLE_NAME1, null);

        final String[] products = new String[cursor.getCount()];
        products1 = new String[cursor.getCount()];
        //String[] products1 = new String[];
        Log.e("products",":%%%%%%%%%%%Arraylist"+products.toString());
        billnos = new ArrayList<HashMap<String, String>>();
        for(int i=0;i<products.length;i++)
        {
            HashMap<String,String> map = new HashMap<String, String>();
            map.put("Billno",products[i]);
            billnos.add(map);
        }
        Log.e("Billno",":%%%%%%%%%%%Arraylist"+billnos.toString());
        at = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView);
        lv2 = (ListView) findViewById(R.id.list);
        lv2.setAdapter(new MyAdapter(getApplicationContext(), billnos));

        int i = 0;

        while (cursor.moveToNext()) {
            String cnum = cursor
                    .getString(cursor.getColumnIndex("bno"));

            String cnum2 = cursor
                    .getString(cursor.getColumnIndex("customername"));

            String cnum3 = cursor.getString(cursor.getColumnIndex("set_table"));

            String gettottt="TABLE"+cnum3+"-"+cnum2+"-"+cnum;

                products[i] = cnum;
                products1[i] = gettottt;


                //name = cursor.getString(cursor.getColumnIndex("email"));
                //namss[i] = nami;
                i++;
            }
            cursor.close();



            adapter = new ArrayAdapter<String>(this, R.layout.list_single, R.id.product_name, products1);
            //lv.setAdapter(adapter);

            at.setAdapter(adapter);

            at.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                // String ROLL,NAME,AGE,ADDR;

                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                int position, long id) {


                    String sr = products1[position];
                    // Log.e("tag","**********"+sr);
                    Log.e("tag","position val"+sr);
                    Intent intent = new Intent(getApplicationContext(), AddItemBill.class);
                    // intent.putExtra("val",sr);
                    intent.putExtra("idd",products[position]);
                    startActivity(intent);























                }
        });



    }


    private class MyAdapter extends BaseAdapter
    {
        LayoutInflater inflater;
        Context c;
        ArrayList<HashMap<String,String>> details;
        MyAdapter(Context c, ArrayList<HashMap<String,String>> details)
        {
            this.c = c;
            this.details= details;
        }
        @Override
        public int getCount() {
            return 0;
        }

        @Override
        public Object getItem(int position) {
            return details.size();
        }

        @Override
        public long getItemId(int position) {
            return details.size();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            convertView = inflater.inflate(R.layout.listview_item,null);

            final TextView billno = (TextView)convertView.findViewById(R.id.billno);
            billno.setId(position);
            String bno = details.get(position).get("Billno");
            Log.d("Billno",":%%%%%%%%%%%5"+bno);

            billno.setText(bno);

            billno.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String bno = billno.getText().toString();
                    Log.d("Billno",":%%%%%%%%%%%inside click event"+bno);

                }
            });
            return null;
        }
    }
}