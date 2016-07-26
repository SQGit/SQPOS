package autospec.sqindia.net.autospec;

    import android.app.Activity;
    import android.content.Intent;
    import android.database.Cursor;
    import android.graphics.Typeface;
    import android.os.Bundle;
    import android.util.Log;
    import android.view.View;
    import android.view.WindowManager;
    import android.widget.Button;
    import android.widget.ListView;
    import android.widget.TextView;
    import java.util.ArrayList;


public class ModifyInspectionActivity extends Activity {

    TextView textView_head, textView_unitno, textView_rentalid;
    Button btn_back;
    ListView list;
    static String id="id";

    String QRY1="SELECT * FROM INSPECTION";
    LoginDataBaseAdapter loginDataBaseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.modify_inspection);

        // get Instance of Database Adapter
        loginDataBaseAdapter=new LoginDataBaseAdapter(this);
        loginDataBaseAdapter=loginDataBaseAdapter.open();

        //****************findviewbyid***********************
        textView_head = (TextView) findViewById(R.id.textView_head);
        textView_unitno = (TextView) findViewById(R.id.textView_unitno);
        textView_rentalid = (TextView) findViewById(R.id.textView_rentalid);
        btn_back = (Button) findViewById(R.id.btn_back);
        list=(ListView) findViewById(R.id.listView);

        //*****************change font using Typeface**************
        Typeface tf = Typeface.createFromAsset(getApplicationContext().getAssets(), "ROBOTO-LIGHT.TTF");
        textView_head.setTypeface(tf);
        textView_unitno.setTypeface(tf);
        textView_rentalid.setTypeface(tf);
        btn_back.setTypeface(tf);

        //*******************Login onclicklistener***************
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_back=new Intent(getApplicationContext(),Dashboard.class);
                startActivity(intent_back);
                finish();
            }
        });
        goList(QRY1);
    }


        //get values gefault
    private void goList(String qry1) {

        ArrayList<Modify_Inspection> lv1 = new ArrayList<Modify_Inspection>();

        Cursor c1 = loginDataBaseAdapter.fetchdata(qry1);
        Log.e("tag","<---lv111111111111---->"+c1);
        if(c1 != null) {
            if (c1.moveToFirst()) {
                do {
                    Modify_Inspection jv = new Modify_Inspection();
                    jv.set_ID(c1.getString(c1.getColumnIndex(loginDataBaseAdapter.ID)));
                    jv.set_UNIT_NO(c1.getString(c1.getColumnIndex(loginDataBaseAdapter.UNITNO)));
                    jv.set_RENTAL_NO(c1.getString(c1.getColumnIndex(loginDataBaseAdapter.RENTALNO)));
                    jv.set_DATE(c1.getString(c1.getColumnIndex(loginDataBaseAdapter.DATE)));
                    lv1.add(jv);
                }
                while (c1.moveToNext());
            }
        }

        View_Inspection_Adapter adapter1 = new View_Inspection_Adapter(ModifyInspectionActivity.this, lv1);
        list.setAdapter(adapter1);


    }

    @Override
    public void onBackPressed() {

        Intent i = new Intent(ModifyInspectionActivity.this,Dashboard.class);
        startActivity(i);
        finish();

    }
}