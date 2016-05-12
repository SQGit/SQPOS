package www.rsagroups.example.myapplication;

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
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by RSA on 15-02-2016.
 */
public class Bill_display_adpter extends BaseAdapter {
    private Context mContext;
    private ArrayList<String> sl_no;
    private ArrayList<String> product_name;
    private ArrayList<String> pricp;
    private ArrayList<String> product_qty;
    private ArrayList<String> product_price;

    String currency,pn,pp;
    int i;


    public Bill_display_adpter(Context c, String pn,String pp, ArrayList<String> product_name, ArrayList<String> pric, ArrayList<String> product_qty, ArrayList<String> product_price) {
        this.mContext = c;
        this.pn = pn;
        this.pp = pp;
        this.sl_no = sl_no;
        this.product_name = product_name;
        this.product_name = product_name;
        this.pricp = pric;
        this.product_qty = product_qty;
        this.product_price = product_price;
    }

    public int getCount() {
        // TODO Auto-generated method stub
        return product_name.size();
    }

    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return null;
    }

    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    public View getView(int pos, View child, ViewGroup parent) {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        currency = sharedPreferences.getString("send2", "");
        Holder mHolder;
        LayoutInflater layoutInflater;
        if (child == null) {
            layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            child = layoutInflater.inflate(R.layout.listcell_bill, null);
            Typeface tf = Typeface.createFromAsset(mContext.getAssets(), "appfont.OTF");
            mHolder = new Holder();


            mHolder.tx_product_name = (TextView) child.findViewById(R.id.tx_product_name);
            mHolder.tx_perprice = (TextView) child.findViewById(R.id.tx_perprice);
            mHolder.tx_product_qty = (TextView) child.findViewById(R.id.tx_product_qty);
            mHolder.txt_price = (TextView) child.findViewById(R.id.txt_price);


            mHolder.tx_product_name.setTypeface(tf);
            mHolder.tx_perprice.setTypeface(tf);
            mHolder.tx_product_qty.setTypeface(tf);
            mHolder.txt_price.setTypeface(tf);

            if (pos % 2 == 0) {

                child.setBackground(mContext.getResources().getDrawable(R.color.par));

            } else {

                child.setBackground(mContext.getResources().getDrawable(R.color.par2));

            }

            child.setTag(mHolder);

        } else {
            mHolder = (Holder) child.getTag();
        }



          /*  for (int i=0; i<product_name.size(); i++) {


                if (pn.equals(product_name.get(i))) {

                    // Toast.makeText(mContext.getApplicationContext(),"vhsvdhvh",Toast.LENGTH_SHORT).show();
                    Log.e("tag", "0000");
                } else {
                    // Toast.makeText(mContext.getApplicationContext(),"tttttt",Toast.LENGTH_SHORT).show();
                    Log.e("tag", "1111");

                }
            }*/



        mHolder.tx_product_name.setText(product_name.get(pos));
        mHolder.tx_perprice.setText(pricp.get(pos));
        mHolder.tx_product_qty.setText(product_qty.get(pos));
        mHolder.txt_price.setText(product_price.get(pos));
        return child;
    }

    public class Holder {
        TextView tx_product_name;
        TextView tx_perprice;
        TextView tx_product_qty;
        TextView txt_price;
    }

}
