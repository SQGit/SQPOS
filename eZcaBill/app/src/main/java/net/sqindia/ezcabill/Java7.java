package net.sqindia.ezcabill;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by User on 19-11-2015.
 */
public class Java7 extends BaseAdapter {

    Context c1;
    ArrayList<Plan_Java1> arrayList;
    TextView t1,t2,t3;
    String b3;
    int j;


    //Typeface tf = Typeface.createFromAsset(getApplicationContext().getAssets(),"Fonts/Montserrat-Regular.ttf");
   // Typeface tf = Typeface.createFromFile("fonts/montserrat.ttf");



    public Java7(Context c1, ArrayList<Plan_Java1> list) {
        this.c1 = c1;
        arrayList = list;

    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View v2, ViewGroup parent) {

        Plan_Java1 java1 = arrayList.get(position);

        LayoutInflater inflat = (LayoutInflater) c1.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        v2 = inflat.inflate(R.layout.java7,null);

        t1 = (TextView) v2.findViewById(R.id.txview1);
        t2 = (TextView) v2.findViewById(R.id.txview2);
        t3 = (TextView) v2.findViewById(R.id.txview3);

        Typeface tf = Typeface.createFromAsset(c1.getAssets(), "reg.TTF");
        t1.setTypeface(tf);
        t2.setTypeface(tf);
        t3.setTypeface(tf);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(c1.getApplicationContext());

        b3 = sharedPreferences.getString("s3", "");
        Log.e("tag", "jav7" + b3);

  /*      t1.setTypeface(tf, Typeface.NORMAL);
        t2.setTypeface(tf, Typeface.NORMAL);
        t3.setTypeface(tf, Typeface.NORMAL);
        t4.setTypeface(tf, Typeface.NORMAL);
        t5.setTypeface(tf, Typeface.NORMAL);
        t6.setTypeface(tf, Typeface.NORMAL);
        t7.setTypeface(tf, Typeface.NORMAL);*/




        for(int i = 0; i< arrayList.size(); i++){
            j=i+1;
            Log.e("TAG !@","sdf" +j);
           // id1.add(String.valueOf(j));

        }

        t1.setText(java1.get_IDTAG());
        t2.setText(java1.get_NAME());
        t3.setText(b3+java1.get_AMOUNT());





        return v2;
    }
}
