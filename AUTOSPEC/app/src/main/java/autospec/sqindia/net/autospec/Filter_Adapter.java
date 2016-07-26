package autospec.sqindia.net.autospec;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class Filter_Adapter extends BaseAdapter {

    Context c1;
    ArrayList<Filter_Getter_Setter> arrayList;
    TextView unitno,rentno,date,txt_id;
    ImageView click;
    String str_inspection_date,id,jj;
    LoginDataBaseAdapter loginDataBaseAdapter;
    Filter_Getter_Setter data;

    public Filter_Adapter(Context c1, ArrayList<Filter_Getter_Setter> list) {
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

    //********************get view method**************
    @Override
    public View getView(final int position, View v2, ViewGroup parent) {
        data = arrayList.get(position);
        LayoutInflater inflat = (LayoutInflater) c1.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v2 = inflat.inflate(R.layout.filter_adapter,null);

        Typeface tf = Typeface.createFromAsset(c1.getAssets(), "ROBOTO-LIGHT.TTF");
        unitno=(TextView) v2.findViewById(R.id.txt_Unit_no);
        rentno=(TextView) v2.findViewById(R.id.txt_agrreement_no);
        date=(TextView) v2.findViewById(R.id.txt_date);
        click=(ImageView) v2.findViewById(R.id.button2) ;
        unitno.setTypeface(tf);
        rentno.setTypeface(tf);
        date.setTypeface(tf);

        str_inspection_date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());

        loginDataBaseAdapter=new LoginDataBaseAdapter(this.c1);
        loginDataBaseAdapter=loginDataBaseAdapter.open();

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this.c1);
        id=sharedPreferences.getString("id","");
        unitno.setText(data.get_UNIT_NO());
        rentno.setText(data.get_RENTAL_NO());
        date.setText(data.get_DATE());
        Log.e("tag","c"+rentno);

        //******************click event onclick listener********
        click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user_id=arrayList.get(position).get_ID();
                String edit_unit_no = arrayList.get(position).get_UNIT_NO();
                String edit_rental_no=arrayList.get(position).get_RENTAL_NO();
                String inspection_date=arrayList.get(position).get_DATE();

                Intent intent = new Intent(c1, ViewImage.class);
                intent.putExtra("user_id#",user_id);
                intent.putExtra("unit_no#",edit_unit_no);
                intent.putExtra("agreement_no#",edit_rental_no);
                intent.putExtra("inspection_date#",inspection_date);
                c1.startActivity(intent);
                ((Activity)c1).finish();
            }
        });
        return v2;
    }
}

