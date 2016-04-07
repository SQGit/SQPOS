package www.rsagroups.example.myapplication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;

/**
 * Created by RSA on 11-02-2016.
 */
public class BillHistory extends AppCompatActivity {
    private AlertDialog.Builder build;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.billhistory);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#ffffff'><big><b>HABITAT</b></big> </font>"));
         
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back_arrow);

        build = new AlertDialog.Builder(BillHistory.this);
        build.setTitle("ON GOING PROCESS");
        build.setMessage("UPDATE IN FEATURE");
        build.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog,
                                        int which) {


                        Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(intent);
                    }
                });


        AlertDialog alert = build.create();
        alert.show();
    }
    @Override
    public void onBackPressed() {

    }
}
