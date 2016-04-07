package www.rsagroups.example.myapplication;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

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


     public Bill_display_adpter(Context c,ArrayList<String> product_name,ArrayList<String> pric,ArrayList<String> product_qty, ArrayList<String> product_price) {
            this.mContext = c;

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
        Holder mHolder;
        LayoutInflater layoutInflater;
        if (child == null) {
            layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            child = layoutInflater.inflate(R.layout.listcell_bill, null);
            Typeface tf = Typeface.createFromAsset(mContext.getAssets(), "appfont.OTF");



            mHolder = new Holder();

           // mHolder.txt_id = (TextView) child.findViewById(R.id.txt_id);
            mHolder.tx_product_name = (TextView) child.findViewById(R.id.tx_product_name);
            mHolder.tx_perprice = (TextView) child.findViewById(R.id.tx_perprice);
            mHolder.tx_product_qty = (TextView) child.findViewById(R.id.tx_product_qty);
            mHolder.txt_price = (TextView) child.findViewById(R.id.txt_price);

//            mHolder.txt_id.setTypeface(tf);
            mHolder.tx_product_name.setTypeface(tf);
            mHolder.tx_perprice.setTypeface(tf);
            mHolder.tx_product_qty.setTypeface(tf);
            mHolder.txt_price.setTypeface(tf);



            if (pos%2==0) {
                //itemView.setBackgroundResource(R.color.blue);
                //Toast.makeText(context,"Varun(9854752445) Takes responsibility of this person",Toast.LENGTH_LONG).show();
                //child.setEnabled(false);
                child.setBackground(mContext.getResources().getDrawable(R.color.par));


                // lin.setBackground(context.getResources().getDrawable(R.color.sad));
            } else {

                child.setBackground(mContext.getResources().getDrawable(R.color.par2));


                //itemView.setBackgroundResource(R.color.navy);
            }


            child.setTag(mHolder);
        } else {
            mHolder = (Holder) child.getTag();
        }
        //mHolder.txt_id.setText(sl_no.get(pos));
        mHolder.tx_product_name.setText(product_name.get(pos));
        mHolder.tx_perprice.setText(pricp.get(pos));
        mHolder.tx_product_qty.setText(product_qty.get(pos));
        mHolder.txt_price.setText(product_price
                .get(pos));

        return child;
    }

    public class Holder {
        //TextView txt_id;
        TextView tx_product_name;
        TextView tx_perprice;
        TextView tx_product_qty;
        TextView txt_price;
    }

}
