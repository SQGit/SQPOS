package net.sqindia.ezcabill;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by $Krishna on 24-06-2016.
 */
public class Plan_List extends Activity {

    SQLiteDatabase db;
    ListView listView;

    Cursor c;
    String b3;

    Java7 adapter1;
    private static final String SELECT_SQL = "SELECT * FROM plan";

    LinearLayout iback,settings;
    Button additem;
    TextView txt_planlist,txt_id,txt_type,txt_amount,txt_copyrights,txt_settings;
    ImageView importdb;

    int j;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //-----List Of Plans added-----//
        setContentView(R.layout.plan_list);

        openDatabase();//**Calling method to open Table in Database **//

        listView = (ListView) findViewById(R.id.planitem_listView);
        iback = (LinearLayout) findViewById(R.id.layout_back);
        additem = (Button) findViewById(R.id.btnAdditem);
        txt_planlist = (TextView) findViewById(R.id.tv_planlist);
        txt_id = (TextView) findViewById(R.id.t1);
        txt_type = (TextView) findViewById(R.id.t2);
        txt_amount = (TextView) findViewById(R.id.t3);
        txt_settings = (TextView) findViewById(R.id.text_settings);
        settings = (LinearLayout) findViewById(R.id.btn_settings);

        Typeface tf = Typeface.createFromAsset(getApplicationContext().getAssets(),"reg.TTF");

        additem.setTypeface(tf);
        txt_planlist.setTypeface(tf);
        txt_id.setTypeface(tf);
        txt_type.setTypeface(tf);
        txt_amount.setTypeface(tf);
        txt_settings.setTypeface(tf);


        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        b3 = sharedPreferences.getString("s3", "");


        c = db.rawQuery(SELECT_SQL, null);
        c.moveToFirst();
        showRecords();//**Calling method to open plans in listview **//



        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Plan_List.this,Settings.class);
                startActivity(i);
                finish();
            }
        });

        //--Back button--//
        iback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Plan_List.this,DashBoard.class);
                startActivity(i);
                finish();
            }
        });

        //*** Going to another activity to add ?Plans ***//
        additem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(b3.equals(""))
                {
                    Toast.makeText(getApplicationContext(), "Add Details in Settings", Toast.LENGTH_LONG).show();
                    return;
                }
                Intent i = new Intent(Plan_List.this,Add_Plan.class);
                startActivity(i);
                finish();
            }
        });


//-----Anything Modification in Plan Listview (like to delte unnecessary plan) --------//
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                TextView tvv = (TextView) view.findViewById(R.id.txview1);
                TextView tvv1 = (TextView) view.findViewById(R.id.txview2);

                final String id = tvv.getText().toString();
                Log.d("tag",id);
                final String type = tvv1.getText().toString();
                final String SELECT_SQL = "SELECT * FROM plan where id = "+id;

                c = db.rawQuery(SELECT_SQL, null);
                c.moveToFirst();



                if (c.moveToFirst()) {

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Plan_List.this);
                alertDialogBuilder.setMessage("Are you sure you want delete this Plan?");

                alertDialogBuilder.setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {

                                    String sql = "DELETE FROM plan WHERE id=" + id + ";";
                                    db.execSQL(sql);
                                    Toast.makeText(getApplicationContext(), "Record Deleted", Toast.LENGTH_LONG).show();
                                    c = db.rawQuery(SELECT_SQL, null);
                                    Intent i = new Intent(Plan_List.this, Plan_List.class);
                                    startActivity(i);
                                    finish();

                            }
                        });

                alertDialogBuilder.setNegativeButton("No",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {

                            }
                        });


                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();

                        }
                c.close();
                return false;
            }
        });


    }
    protected void openDatabase() {
        db = openOrCreateDatabase("PersonDB", Context.MODE_PRIVATE, null);
    }


    protected void showRecords() {

        ArrayList<Plan_Java1> lv2 = new ArrayList<Plan_Java1>();
        lv2.clear();


        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    Plan_Java1 jv = new Plan_Java1();

                    jv.set_IDTAG(c.getString(c.getColumnIndex("id")));
                    jv.set_NAME(c.getString(c.getColumnIndex("type")));
                    jv.set_AMOUNT(c.getString(c.getColumnIndex("amount")));


                    lv2.add(jv);
                } while (c.moveToNext());
            }
        }
       // c.close();


        adapter1 = new Java7(Plan_List.this, lv2);
        listView.setAdapter(adapter1);

//db.close();
    }
    @Override
    public void onBackPressed() {

        Intent i = new Intent(Plan_List.this,DashBoard.class);
        startActivity(i);
        finish();

        // super.onBackPressed();
    }



}
