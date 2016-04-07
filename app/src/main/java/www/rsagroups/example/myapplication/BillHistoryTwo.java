package www.rsagroups.example.myapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Admin on 20-02-2016.
 */
public class BillHistoryTwo extends Activity {

    ListView listview;
    TextView t1,t2,t3,t4,t5,head;
    Button back,dot,search;
    DbHelper dbh;
    File data,sd;
    String getfromdate,gettodate;

    EditText txtDate, txtDate2;





    Context context=this;
    //String QRY2 = "SELECT * FROM "+dbh.TABLE_NAME2+ " ORDER BY "+ dbh.KEY_ID+ " DESC";

    //String QRY2 = "SELECT * FROM "+dbh.TABLE_NAME2+ " WHERE" +dbh.DATE+dbh.BILL_NO+dbh.CUSTOMER_NAME+dbh.SET_TABLE+ "BETWEEN" +dbh.DATE+ (getfromdate)+ "AND" +dbh.DATE+ (gettodate);
    String QRY2 = "SELECT " +dbh.DATE +dbh.BILL_NO +" FROM " +dbh.TABLE_NAME2+ " WHERE " +dbh.DATE+ " BETWEEN " +dbh.DATE+ ("01/04/2016")+ " AND " +dbh.DATE+ ("04/04/2016");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bill_history2);

        t1 = (TextView) findViewById(R.id.t1);
        t2 = (TextView) findViewById(R.id.t2);
        t3 = (TextView) findViewById(R.id.t3);
        t4 = (TextView) findViewById(R.id.t4);
        t5 = (TextView)findViewById(R.id.table);
        dot=(Button)findViewById(R.id.dot_icon);
        txtDate = (EditText) findViewById(R.id.startDate);
        txtDate2 = (EditText) findViewById(R.id.endDate);
        search=(Button)findViewById(R.id.search);




        head = (TextView) findViewById(R.id.textView7);

        Typeface tf = Typeface.createFromAsset(getAssets(), "appfont.OTF");
        t1.setTypeface(tf);
        t2.setTypeface(tf);
        t3.setTypeface(tf);
        t4.setTypeface(tf);
        t5.setTypeface(tf);
        head.setTypeface(tf);



        listview = (ListView) findViewById(R.id.listView);
        back=(Button)findViewById(R.id.back_icon);




            search.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getfromdate=txtDate.getText().toString();
                    gettodate=txtDate2.getText().toString();
                    Log.e("tag", "date from" + getfromdate);
                    Log.e("tag", "date to" + gettodate);
                    goList();
                }
            });




        dot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                PopupMenu popup = new PopupMenu(getApplicationContext(), dot);
                popup.getMenuInflater().inflate(R.menu.opt_menus, popup.getMenu());

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {

                        switch (item.getItemId()) {
                            case R.id.option1:

                                AlertDialog.Builder u = new AlertDialog.Builder(BillHistoryTwo.this);
                                u.setTitle("Export Data");

                                u.setMessage("Are you sure you want export Db?");
                                u.setPositiveButton(android.R.string.yes,
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                exportDB();
                                                Toast.makeText(getApplicationContext(),"Db Exported",Toast.LENGTH_LONG).show();
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

                            default:
                                return true;

                            case R.id.option2:

                                AlertDialog.Builder d = new AlertDialog.Builder(BillHistoryTwo.this);
                                d.setTitle("Clear DataBase");

                                d.setMessage("Are you sure you want to delete your databse ?");
                                d.setPositiveButton(android.R.string.yes,
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                deleteDb();
                                                Toast.makeText(getApplicationContext(),
                                                        "DataBase Cleared Successfully",
                                                        Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                d.setNegativeButton(android.R.string.no,
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                finish();
                                            }
                                        });
                                d.show();


                                return true;
                        }

                    }
                });

                popup.show();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ii=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(ii);
            }
        });

        dbh = new DbHelper(context);


       // goList();
       // Toast.makeText(getApplicationContext(), "hello hai world", Toast.LENGTH_LONG).show();
    }



        private void exportDB() {
            sd = new File(Environment.getExternalStorageDirectory() + "/habitat/");
            data = Environment.getDataDirectory();
            //File mydir = new File(Environment.getExternalStorageDirectory() + "/NewsApp/");


            if (!sd.exists()) {
                sd.mkdirs();
            }


            FileChannel source = null;
            FileChannel destination = null;
            String currentDBPath = "/data/" + "www.rsagroups.example.myapplication" + "/databases/" + "userdata.db";
            String backupDBPath = "habitatdata.db";
            Log.e("tag", "exported file" + backupDBPath);


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



    private void deleteDb() {
/*

        File root = Environment.getExternalStorageDirectory();
        String fileName = "habitatdata.txt";
        if (root.canWrite()) {
            attachment = new File(root, fileName);
        }

        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto", "guna@sqindia.net", null));
        emailIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(attachment));

        emailIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse("file:////attachment.txt"));

        startActivity(Intent.createChooser(emailIntent, "Send Email"));

*/






    }




    private void goList() {


        //Toast.makeText(getApplicationContext(),"hello",Toast.LENGTH_LONG).show();

        ArrayList<Java1> lv1 = new ArrayList<Java1>();
        lv1.clear();


        Cursor c1 = dbh.fetchdata(QRY2);

        if(c1 != null) {
            if (c1.moveToFirst()) {
                do {
                    Java1 jv = new Java1();


                    jv.set_DATE(c1.getString(c1.getColumnIndex(dbh.DATE)));


                    jv.set_BILLNO(c1.getString(c1.getColumnIndex(dbh.BILL_NO)));


                    jv.set_CUSTOMERNAME(c1.getString(c1.getColumnIndex(dbh.CUSTOMER_NAME)));


                    jv.set_TABLE(c1.getString(c1.getColumnIndex(dbh.SET_TABLE)));


                    jv.set_GRANDTOTAL(c1.getString(c1.getColumnIndex(dbh.GRAND_TOTAL)));

                    lv1.add(jv);
                }
                while (c1.moveToNext());





        }

    }
        Java2 adapter1 = new Java2(BillHistoryTwo.this,lv1);
        listview.setAdapter(adapter1);




        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //Toast.makeText(getApplicationContext(), "hello world", Toast.LENGTH_LONG).show();
                Java1 jvv = new Java1();
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(BillHistoryTwo.this);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                String bill=jvv._BILLNO;
                editor.putString("bill",bill);
                editor.commit();



                //Toast.makeText(getApplicationContext(), "hello hai world", Toast.LENGTH_LONG).show();

                Intent move=new Intent(getApplicationContext(),BillList.class);
                startActivity(move);

            }

        });

    }



    }
