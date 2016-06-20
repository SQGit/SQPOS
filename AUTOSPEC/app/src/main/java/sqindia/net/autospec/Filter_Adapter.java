package sqindia.net.autospec;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by RSA on 27-05-2016.
 */
public class Filter_Adapter extends BaseAdapter {

    Context context;
    LayoutInflater inflater;
    ArrayList<HashMap<String, String>> data;
    ProgressDialog mProgressDialog;
    HashMap<String, String> resultp = new HashMap<String, String>();

    public Filter_Adapter(Context context, ArrayList<HashMap<String, String>> viewfilterlist) {
        this.context = context;
        data = viewfilterlist;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final TextView txt_Unit_no, txt_agreement_no,txt_inspection_date;
        String str_agreement,str_inspection_date,str_unit_no;

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.filter_adapter_view, parent, false);
        resultp = data.get(position);
        Typeface tf = Typeface.createFromAsset(context.getAssets(), "_SENINE.TTF");

        txt_agreement_no = (TextView) itemView.findViewById(R.id.txt_agreement_no);
        txt_Unit_no = (TextView) itemView.findViewById(R.id.txt_unit_no);
        txt_inspection_date = (TextView) itemView.findViewById(R.id.txt_date_of_inspection);


     txt_Unit_no.setTypeface(tf);
        txt_agreement_no.setTypeface(tf);
        txt_inspection_date.setTypeface(tf);

        txt_agreement_no.setText(resultp.get(FilterReports.agreementNo));
        txt_Unit_no.setText(resultp.get(FilterReports.unitNo));
        txt_inspection_date.setText(resultp.get(FilterReports.inspectionDate));


       str_agreement = resultp.get(FilterReports.agreementNo);
        str_unit_no=resultp.get(FilterReports.unitNo);
        str_inspection_date = resultp.get(FilterReports.inspectionDate);

        Log.d("tag","str_agreement= "+str_agreement);
        Log.d("tag","str_unit_no= "+str_unit_no);
        Log.d("tag","str_inspection_date= "+str_inspection_date);


        itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

            }
        });
        return itemView;
    }
}
