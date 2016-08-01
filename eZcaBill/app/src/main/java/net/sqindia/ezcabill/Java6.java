package net.sqindia.ezcabill;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by User on 19-11-2015.
 */
public class Java6 extends BaseAdapter {

    Context c1;
    ArrayList<Java1> arrayList;
    TextView t1,t2,t3,t4,t5,t6;

    //Typeface tf = Typeface.createFromAsset(getApplicationContext().getAssets(),"Fonts/Montserrat-Regular.ttf");
   // Typeface tf = Typeface.createFromFile("fonts/montserrat.ttf");



    public Java6(Context c1, ArrayList<Java1> list) {
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

        Java1 java1 = arrayList.get(position);

        LayoutInflater inflat = (LayoutInflater) c1.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        v2 = inflat.inflate(R.layout.java6,null);

        t1 = (TextView) v2.findViewById(R.id.txtView1);
        t2 = (TextView) v2.findViewById(R.id.txtView2);
        t3 = (TextView) v2.findViewById(R.id.txtView3);
        t4 = (TextView) v2.findViewById(R.id.txtView4);
        t5 = (TextView) v2.findViewById(R.id.txtView5);
        t6 = (TextView) v2.findViewById(R.id.txtView6);

        Typeface tf = Typeface.createFromAsset(c1.getAssets(), "reg.TTF");
        t1.setTypeface(tf);
        t2.setTypeface(tf);
        t3.setTypeface(tf);
        t4.setTypeface(tf);
        t5.setTypeface(tf);
        t6.setTypeface(tf);

  /*      t1.setTypeface(tf, Typeface.NORMAL);
        t2.setTypeface(tf, Typeface.NORMAL);
        t3.setTypeface(tf, Typeface.NORMAL);
        t4.setTypeface(tf, Typeface.NORMAL);
        t5.setTypeface(tf, Typeface.NORMAL);
        t6.setTypeface(tf, Typeface.NORMAL);
        t7.setTypeface(tf, Typeface.NORMAL);*/


        t1.setText(java1.get_IDTAG());
        t2.setText(java1.get_NAME());
        t3.setText(java1.get_NUMBER());
        t4.setText(java1.get_PLAN());
        t5.setText(java1.get_MAIL());
        t6.setText(java1.get_PAYMENT());




        return v2;
    }
}
