package www.rsagroups.example.myapplication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ngx.BluetoothPrinter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class MainActivity extends AppCompatActivity {

    Button addproduct, addbill, billhistory, printerconfig;
    File sd,data;
    TextView dashboard, reserved;
    public static BluetoothPrinter mBtp = BluetoothPrinter.INSTANCE;
    BluetoothPrinterMain bmp = new BluetoothPrinterMain();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       /* getSupportActionBar().setHomeButtonEnabled(true);

        getSupportActionBar().setTitle(Html.fromHtml("<font color='#ffffff'><big><b>DASHBOARD</b></big> </font>"));


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back_arrow);*/
        reserved = (TextView) findViewById(R.id.textrights);
        dashboard = (TextView) findViewById(R.id.textView7);

        addproduct = (Button) findViewById(R.id.add_product);
        addbill = (Button) findViewById(R.id.add_bill);
        billhistory = (Button) findViewById(R.id.bill_history);
        printerconfig = (Button) findViewById(R.id.printer_config);

        Typeface tf = Typeface.createFromAsset(getAssets(), "appfont.OTF");
        dashboard.setTypeface(tf);
        reserved.setTypeface(tf);


        addproduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1 = new Intent(getApplicationContext(), DisplayActivity.class);
                startActivity(i1);

            }
        });
        addbill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i2 = new Intent(getApplicationContext(), BluetoothPrinterMain.class);
                startActivity(i2);

            }
        });
        billhistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i3 = new Intent(getApplicationContext(), BillHistoryTwo.class);
                startActivity(i3);

            }
        });

        printerconfig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i4 = new Intent(getApplicationContext(), Order.class);
                startActivity(i4);
                exportDB();

            }
        });

    }



    private void exportDB() {
        sd = new File(Environment.getExternalStorageDirectory() + "/exported/");
        data = Environment.getDataDirectory();
        //File mydir = new File(Environment.getExternalStorageDirectory() + "/NewsApp/");


        if (!sd.exists()) {
            sd.mkdirs();
        }


        FileChannel source = null;
        FileChannel destination = null;
        String currentDBPath = "/data/" + "www.rsagroups.example.myapplication" + "/databases/" + "userdata.db";
        String backupDBPath = "exportfile.db";


        File currentDB = new File(data, currentDBPath);
        File backupDB = new File(sd, backupDBPath);


        try {
            source = new FileInputStream(currentDB).getChannel();
            destination = new FileOutputStream(backupDB).getChannel();
            destination.transferFrom(source, 0, source.size());
            source.close();
            destination.close();
           //Toast.makeText(this, "DB Exported!", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onRestart() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
        super.onRestart();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.bluetooth_printer_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_clear_device:
                // deletes the last used printer, will avoid auto connect
                AlertDialog.Builder d = new AlertDialog.Builder(this);
                d.setTitle("NGX Bluetooth Printer");
                // d.setIcon(R.drawable.ic_launcher);
                d.setMessage("Are you sure you want to delete your preferred Bluetooth printer ?");
                d.setPositiveButton(android.R.string.yes,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                mBtp.clearPreferredPrinter();
                                Toast.makeText(getApplicationContext(),
                                        "Preferred Bluetooth printer cleared",
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
                d.setNegativeButton(android.R.string.no,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        });
                d.show();
                return true;
            case R.id.action_connect_device:
                // show a dialog to select from the list of available printers
                mBtp.showDeviceList(this);
                return true;
            case R.id.action_unpair_device:
                AlertDialog.Builder u = new AlertDialog.Builder(this);
                u.setTitle("Bluetooth Printer unpair");
                // d.setIcon(R.drawable.ic_launcher);
                u.setMessage("Are you sure you want to unpair all Bluetooth printers ?");
                u.setPositiveButton(android.R.string.yes,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                if (mBtp.unPairBluetoothPrinters()) {
                                    Toast.makeText(
                                            getApplicationContext(),
                                            "All NGX Bluetooth printer(s) unpaired",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                u.setNegativeButton(android.R.string.no,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        });
                u.show();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

        builder.setMessage("Do you want to exit the Application?")
                .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        Intent i1 = new Intent(Intent.ACTION_MAIN);
                        //i1.setAction(Intent.ACTION_MAIN);
                        i1.addCategory(Intent.CATEGORY_HOME);
                        i1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        i1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        i1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(i1);
                        finish();


                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {


                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });

        builder.create();
        builder.show();

    }
}