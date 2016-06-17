package net.sqindia.sqpos;

    import android.app.AlertDialog;
    import android.content.Context;
    import android.content.Intent;
    import android.content.SharedPreferences;
    import android.database.Cursor;
    import android.database.sqlite.SQLiteDatabase;
    import android.database.sqlite.SQLiteException;
    import android.graphics.Typeface;
    import android.os.Bundle;
    import android.preference.PreferenceManager;
    import android.support.v7.app.AppCompatActivity;
    import android.text.SpannableStringBuilder;
    import android.text.Spanned;
    import android.util.Log;
    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;
    import android.view.WindowManager;
    import android.widget.AdapterView;
    import android.widget.ArrayAdapter;
    import android.widget.AutoCompleteTextView;
    import android.widget.BaseAdapter;
    import android.widget.Button;
    import android.widget.ListView;
    import android.widget.TextView;
    import android.widget.Toast;

    import java.text.DateFormat;
    import java.util.ArrayList;
    import java.util.Date;
    import java.util.HashMap;


        public class Order extends AppCompatActivity {
            AutoCompleteTextView at;
            ListView lv2;
            TextView copyright;
            String ss,token_footer;
            Typeface tf;

            AlertDialog dialog;
            SQLiteDatabase sqd;
            ArrayAdapter<String> adapter;
            String[] products1;

            Cursor cursor;
            String name;
            ArrayList<HashMap<String,String>> billnos;
            DbHelper helper;

                @Override
                protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.order_activity);
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

                at=(AutoCompleteTextView)findViewById(R.id.autoCompleteTextView);
                copyright=(TextView)findViewById(R.id.textrights);
                Typeface tf = Typeface.createFromAsset(getAssets(), "appfont.OTF");
                at.setTypeface(tf);
                copyright.setTypeface(tf);

                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(Order.this);
                token_footer = sharedPreferences.getString("footer", "");
                Log.e("tag", "getvalue" + token_footer);
                copyright.setText(token_footer);



                    try {

                        sqd = openOrCreateDatabase("userdata.db", MODE_PRIVATE, null);
                        cursor = sqd.rawQuery("SELECT * " + " FROM " + helper.TABLE_NAME2 + " ORDER BY " + helper.KEY_ID + " DESC", null);
                    }
                    catch (NullPointerException e){
                        Toast.makeText(getApplicationContext(),"hello",Toast.LENGTH_LONG).show();
                        Log.d("tag","sqlie");
                    }
                    catch (SQLiteException e){
                        Log.d("tag","null");
                    }

                final String[] products = new String[cursor.getCount()];
                products1 = new String[cursor.getCount()];

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
                    i++;
                }
                cursor.close();



        adapter = new ArrayAdapter<String>(this, R.layout.list_single, R.id.product_name, products1);
        at.setAdapter(adapter);

        at.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            // String ROLL,NAME,AGE,ADDR;

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                String sr = products1[position];
                Log.e("tag","position val"+sr);
                Intent intent = new Intent(getApplicationContext(), AddItemBill.class);
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