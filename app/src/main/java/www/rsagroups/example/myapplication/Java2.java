package www.rsagroups.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by User on 19-11-2015.
 */
public class Java2 extends BaseAdapter {

    Context c1;
    ArrayList<Java1> arrayList;
    TextView t2,t3,t4,t5,t6;
    Button b1;





    public Java2(Context c1, ArrayList<Java1> list) {
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

        final Java1 java1 = arrayList.get(position);

        LayoutInflater inflat = (LayoutInflater) c1.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        v2 = inflat.inflate(R.layout.aaa,null);
        Typeface tf = Typeface.createFromAsset(c1.getAssets(), "appfont.OTF");


        if (position%2==0) {
            //itemView.setBackgroundResource(R.color.blue);
            //Toast.makeText(context,"Varun(9854752445) Takes responsibility of this person",Toast.LENGTH_LONG).show();
           // v2.setEnabled(false);
            v2.setBackground(c1.getResources().getDrawable(R.color.par));


            // lin.setBackground(context.getResources().getDrawable(R.color.sad));
        } else {

            v2.setBackground(c1.getResources().getDrawable(R.color.par2));


            //itemView.setBackgroundResource(R.color.navy);
        }

        t2 = (TextView) v2.findViewById(R.id.dtextView);
        t3 = (TextView) v2.findViewById(R.id.textView2);
        t5 = (TextView) v2.findViewById(R.id.textView4);
        t4 = (TextView) v2.findViewById(R.id.textView3);
        t6=(TextView) v2.findViewById(R.id.table);

        b1=(Button)v2.findViewById(R.id.view_icon);

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



        t2.setText(java1.get_DATE());
        t3.setText(java1.get_BILLNO());
        t5.setText(java1.get_CUSTOMERNAME());
        t6.setText(java1.get_TABLE());
        t4.setText(java1.get_GRANDTOTAL());

        String total=t4.getText().toString();

        Log.e("tag", "get total_amt" +total);



        v2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String bill = java1._BILLNO;


                Intent move = new Intent(c1, BillList.class);
                move.putExtra("bill", bill);
                c1.startActivity(move);
                //Toast.makeText(c1,"Varun(9854752445) Takes responsibility of this person"+bill, Toast.LENGTH_LONG).show();
            }
        });





        return v2;
    }
}
