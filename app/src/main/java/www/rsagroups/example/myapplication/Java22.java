package www.rsagroups.example.myapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * Created by Admin on 23-02-2016.
 */
public class Java22 extends BaseAdapter {

    Context c2;
    ArrayList<Java11> arrayList2;
    TextView t1, t, t2, t9,t10, t3, t4, t5, t6;
    String currency;

    public Java22(Context c1, ArrayList<Java11> lists) {
        this.c2 = c1;
        arrayList2 = lists;
    }

    @Override
    public int getCount() {
        return arrayList2.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList2.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View v3, ViewGroup parent) {


        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(c2);
        currency = sharedPreferences.getString("send2", "");

        Java11 java11 = arrayList2.get(position);
        LayoutInflater inflat = (LayoutInflater) c2.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v3 = inflat.inflate(R.layout.aaaaa, null);
        Typeface tf = Typeface.createFromAsset(c2.getAssets(), "appfont.OTF");

        if (position%2==0) {

            v3.setBackground(c2.getResources().getDrawable(R.color.par));

        } else {

            v3.setBackground(c2.getResources().getDrawable(R.color.par2));



        }

            t = (TextView) v3.findViewById(R.id.dtextView);
            t2 = (TextView) v3.findViewById(R.id.textView2);
            t9 = (TextView) v3.findViewById(R.id.textView9);
            t10 = (TextView) v3.findViewById(R.id.textView10);
            t3 = (TextView) v3.findViewById(R.id.textView3);
            t4 = (TextView) v3.findViewById(R.id.textView4);
            t5 = (TextView) v3.findViewById(R.id.textView5);
            t6 = (TextView) v3.findViewById(R.id.textView6);


            t.setTypeface(tf);
            t2.setTypeface(tf);
            t9.setTypeface(tf);
            t10.setTypeface(tf);
            t3.setTypeface(tf);
            t4.setTypeface(tf);
            t5.setTypeface(tf);
            t6.setTypeface(tf);


            t.setText(java11.get_DATE1());
            t2.setText(java11.get_BILLNO1());
            t9.setText(java11.get_CUSTOMER());
            t10.setText(java11.get_TABLE());
            t3.setText(java11.get_ITEMNAME1());
            t4.setText(java11.get__ITEMPRICE1());
            t5.setText(java11.get__QTY1());
            t6.setText(currency+java11.get_TOTAL1());
            return v3;
        }

}






