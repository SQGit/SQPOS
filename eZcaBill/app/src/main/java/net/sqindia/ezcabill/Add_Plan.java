package net.sqindia.ezcabill;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by $Krishna on 23-06-2016.
 */
public class Add_Plan extends Activity{

    EditText edit_plan,edit_amount;
    TextView txt_addplan;
    Button save,cancel;
    LinearLayout iback;

    String b3;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.plan_add);

        db = openOrCreateDatabase("PersonDB", Context.MODE_PRIVATE, null);


        edit_plan = (EditText) findViewById(R.id.ed1);
        edit_amount = (EditText) findViewById(R.id.ed2);
        save = (Button) findViewById(R.id.btn1);
        cancel = (Button) findViewById(R.id.btn2);
        txt_addplan = (TextView) findViewById(R.id.addpln);

        iback = (LinearLayout) findViewById(R.id.layout_back);

        Typeface tf = Typeface.createFromAsset(getApplicationContext().getAssets(), "reg.TTF");

        edit_plan.setTypeface(tf);
        edit_amount.setTypeface(tf);
        save.setTypeface(tf);
        cancel.setTypeface(tf);
        txt_addplan.setTypeface(tf);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        b3 = sharedPreferences.getString("s3", "");
        Log.e("tag", "billone" + b3);

        iback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Add_Plan.this,Plan_List.class);
                startActivity(i);
                finish();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String type = edit_plan.getText().toString().trim();
                String amount = edit_amount.getText().toString().trim();


                if (type.equals("")||amount.equals("")){
                    Toast.makeText(getApplicationContext(), "Can't Save Blank Values", Toast.LENGTH_LONG).show();
                    return;
                }


                String query = "INSERT INTO plan (type,amount) VALUES( '" + type + "', '" +amount +  "');";
                db.execSQL(query);
                Toast.makeText(getApplicationContext(), "Saved Successfully", Toast.LENGTH_LONG).show();

                edit_plan.setText("");
                edit_amount.setText("");
            }
        });


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getApplicationContext(),Plan_List.class);
                startActivity(i);
                finish();
            }
        });
    }
    @Override
    public void onBackPressed() {

        Intent i = new Intent(Add_Plan.this,Plan_List.class);
        startActivity(i);
        finish();

        // super.onBackPressed();
    }

}
