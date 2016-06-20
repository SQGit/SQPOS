package sqindia.net.autospec;

import android.app.ProgressDialog;
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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by RSA on 22-05-2016.
 */
public class View_Inspection_Adapter extends BaseAdapter {


    Context context;
    LayoutInflater inflater;
    ArrayList<HashMap<String, String>> data;
    ProgressDialog mProgressDialog;
    HashMap<String, String> resultp = new HashMap<String, String>();

    public View_Inspection_Adapter(Context context, ArrayList<HashMap<String, String>> arraylist) {
        this.context = context;
        data = arraylist;


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

    public View getView(final int position, View convertView, ViewGroup parent) {
        final TextView txt_Unit_no, txt_agrreement_no;
        Button btn_modify, btn_email;
        final String user_id,id, unit_no, aggrement_no;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.modify_inspection_list, parent, false);
        resultp = data.get(position);
        Typeface tf = Typeface.createFromAsset(context.getAssets(), "_SENINE.TTF");
        txt_Unit_no = (TextView) itemView.findViewById(R.id.txt_Unit_no);
        txt_agrreement_no = (TextView) itemView.findViewById(R.id.txt_agrreement_no);
        btn_modify = (Button) itemView.findViewById(R.id.btn_modify);
        btn_email = (Button) itemView.findViewById(R.id.btn_email);
        txt_Unit_no.setTypeface(tf);
        txt_agrreement_no.setTypeface(tf);
        btn_modify.setTypeface(tf);
        btn_email.setTypeface(tf);



        id = resultp.get(ModifyInspectionActivity.id);
        unit_no = resultp.get(ModifyInspectionActivity.unitNo);
        aggrement_no = resultp.get(ModifyInspectionActivity.agreementNo);
        user_id=resultp.get(ModifyInspectionActivity.userId);

        Log.d("tag","id 1= "+id);
        Log.d("tag","str_unit_no1 = "+unit_no);
        Log.d("tag","str_agreement 1= "+aggrement_no);
        Log.d("tag","user_id 1= "+user_id);

        txt_Unit_no.setText(resultp.get(ModifyInspectionActivity.unitNo));
        txt_agrreement_no.setText(resultp.get(ModifyInspectionActivity.agreementNo));


        btn_modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Edit_Inspection.class);
                intent.putExtra("id", id);
                intent.putExtra("unit_no", txt_Unit_no.getText().toString());
                intent.putExtra("agreement_no", txt_agrreement_no.getText().toString());
                intent.putExtra("user_id", user_id);
                context.startActivity(intent);



            }
        });
        btn_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "under process", Toast.LENGTH_LONG).show();
            }
        });

        itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

            }
        });
        return itemView;
    }


}


