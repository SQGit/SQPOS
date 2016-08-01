package net.sqindia.ezcabill;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by $Krishna on 28-05-2016.
 */
public class DashBoard extends Activity {

    LinearLayout newregistration, payment, list, report, paid_unpaidlist, btnexportDB, btnSEmail;

    File sd;
    File data;
    String path;

    int current_date, current_mnt, nxt_date, nxt_mnt, inc, db_date, db_mnt, cur_day, cur_month;


    int year;
    int month;
    int day;
    String aadsf, qwert;

    Cursor csd;

    TextView txt_dashboard,txt_addplan,txt_newreg,txt_billpayment,txt_customerlist,txt_report,txt_paidunpaid,txt_exportdb,txt_copyrights;
    int i;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dshb);
        createDatabase();//**** Calling method to create Sqlite Database **********//


        //------Open Database Table --///
        db = openOrCreateDatabase("PersonDB", Context.MODE_PRIVATE, null);


//--- Getting current date------//
        final Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);


      /*  // **** Checking tomorrows Date ******//*/
         c.add(Calendar.DAY_OF_YEAR,1);
        Date tomorrow = c.getTime();
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");


        String tomorrowAsString = dateFormat.format(tomorrow);
        Log.e("TAg", "Tomorrowdate" + tomorrowAsString);
*/
//  **** Another method to get current date ******//
        c.add(Calendar.DATE, 29);
        Date dd = c.getTime();
        //Log.e("TAG", "fasvd" + dd);
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String czxc = dateFormat.format(dd);
        //  Log.e("TAg", czxc);
        i = c.get(Calendar.DATE);
        //Log.d("TAG23", String.valueOf(i));


       // txt_dashboard = (TextView) findViewById(R.id.textiew4);
        txt_addplan = (TextView) findViewById(R.id.tv_addplan);
        txt_newreg = (TextView) findViewById(R.id.tv_newreg);
        txt_billpayment = (TextView) findViewById(R.id.tv_billpayment);
        txt_customerlist = (TextView) findViewById(R.id.tv_customerlist);
        txt_report = (TextView) findViewById(R.id.tv_report);
        txt_paidunpaid = (TextView) findViewById(R.id.tv_paidunpaid);
        txt_exportdb = (TextView) findViewById(R.id.tv_exportdb);


        newregistration = (LinearLayout) findViewById(R.id.btnRegistration);
        payment = (LinearLayout) findViewById(R.id.btnPayment);
        list = (LinearLayout) findViewById(R.id.btnCustomerlist);
        report = (LinearLayout) findViewById(R.id.btnSalesreport);
        paid_unpaidlist = (LinearLayout) findViewById(R.id.btnpaidlist);
        btnexportDB = (LinearLayout) findViewById(R.id.btnExport);
        btnSEmail = (LinearLayout) findViewById(R.id.btnsndmail);

        Typeface tf = Typeface.createFromAsset(getApplicationContext().getAssets(), "reg.TTF");

     //   txt_dashboard.setTypeface(tf);
        txt_addplan.setTypeface(tf);
        txt_newreg.setTypeface(tf);
        txt_billpayment.setTypeface(tf);
        txt_customerlist.setTypeface(tf);
        txt_report.setTypeface(tf);
        txt_paidunpaid.setTypeface(tf);
        txt_exportdb.setTypeface(tf);

//Displaying Current Date//
        aadsf = String.valueOf(new StringBuilder().append(day).append("/").append(month + 1).append("/").append(year).append(""));
        // Log.e("TAg", aadsf);

//In Database opening particular Table called persons and updatind in that Table particular column update_date////
        final String SELECT_SQL = "SELECT DISTINCT update_date FROM persons";

        csd = db.rawQuery(SELECT_SQL, null);

        ArrayList<String> date_values = new ArrayList<String>();
        date_values.clear();
//csd == Cursor   ///
        if (csd != null) {
            if (csd.moveToFirst()) {
                do {
                    String date = csd.getString(csd.getColumnIndex("update_date"));
                    date_values.add(date);
                } while (csd.moveToNext());
            }
        }


        Date today = new Date();
        Calendar cal = new GregorianCalendar();
        cal.setTime(today);
        current_date = cal.get(Calendar.DATE);
        current_mnt = cal.get(Calendar.MONTH) + 1;


        //---------Method to check current date and Database row created date and modifying the table----------//
        for (int i = 0; i < date_values.size(); i++) {
            //  Log.e("tag",""+date_values.get(i));

            String date = date_values.get(i);
            String[] seperate = date.split("/");

            cur_day = Integer.parseInt(seperate[0].trim());
            cur_month = Integer.parseInt(seperate[1].trim());
            Log.e("tag", "db_month" + cur_month + "," + "current_month" + current_mnt);
            if (cur_month != current_mnt) {
                Log.e("tag", "db_date" + cur_day + "," + "current_day" + current_date);
                if (cur_day == current_date) {
                    Log.e("tag", "" + current_date + " " + nxt_date + " " + db_date);
                    //  update payment = unpaid where date = date_values.get(i);


                    String bdate = aadsf.toString().trim();


                    String sq = "Update persons set payment ='Unpaid',update_date=" + "'" + bdate + "'where update_date = " + "'" + date_values.get(i) + "'";
                    Log.e("tag", sq);
                    //  cur_day = current_date;
                    //  cur_month = current_mnt;
                    db.execSQL(sq);

                } else {
                    Log.e("tag", "" + current_date + " " + current_mnt + " " + cur_day + " " + cur_month);
                }

            }
        }



       /* for ( int i =1; i< csd.getCount();i++){


            String date = csd.getString(csd.getColumnIndex("date"));
            Log.e("tag",date);

        }*/


        // int count = csd.getCount();
        // Log.d("tag1",""+csd.getCount() +""+csd.getInt(0));

        //  int count = getCount(csd);

        //Log.e("tag", "" + count);






//*****Adding New Member ******//

        newregistration.setOnClickListener(new View.OnClickListener()

                                           {
                                               @Override
                                               public void onClick(View view) {

                                                   final String SELECT_SQ = "SELECT * FROM plan";
                                                   Cursor cur = db.rawQuery(SELECT_SQ, null);
                                                   if (cur != null && cur.moveToFirst() && cur.getInt(0) > 0) {
                                                       Log.i(getClass().getName(), "table not empty");
                                                       Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                                       startActivity(intent);
                                                       finish();

                                                   } else {
                                                       Log.i(getClass().getName(), "table is empty");
                                                       Toast.makeText(DashBoard.this, "Add your own Plan Details!", Toast.LENGTH_SHORT).show();
                                                   }

                                               }

                                           }

        );



        //****** How much amount collected in a day ********//
        report.setOnClickListener(new View.OnClickListener()

                                  {
                                      @Override
                                      public void onClick(View view) {
                                          Intent i = new Intent(getApplicationContext(), Sales_Report.class);
                                          startActivity(i);
                                          finish();
                                      }
                                  }

        );



        ///****** List of Customers to pay bill ******//
        payment.setOnClickListener(new View.OnClickListener()

                                   {
                                       @Override
                                       public void onClick(View view) {
                                           Intent k = new Intent(getApplicationContext(), Bill_Payment.class);
                                           startActivity(k);
                                           finish();
                                       }
                                   }

        );

        // General Customer List view///
        list.setOnClickListener(new View.OnClickListener()

                                {
                                    @Override
                                    public void onClick(View view) {
                                        Intent j = new Intent(getApplicationContext(), Customer_List.class);
                                        startActivity(j);
                                        finish();
                                    }
                                }

        );

        //-------Seperating Paid and Unpaid Customers -----------//
        paid_unpaidlist.setOnClickListener(new View.OnClickListener()

                                           {
                                               @Override
                                               public void onClick(View view) {
                                                   Intent d = new Intent(DashBoard.this, TabLayoutActivity.class);
                                                   startActivity(d);
                                                   finish();
                                               }
                                           }

        );


        //---Exporting Sqlite database File --- && -----Converting Database to CSV file-------///
        btnexportDB.setOnClickListener(new View.OnClickListener()

                                       {
                                           @Override
                                           public void onClick(View view) {

                                               exportDB();
                                               // importDB();
                                               Context ctx = DashBoard.this; // for Activity, or Service. Otherwise simply get the context.
                                               String dbname = "exportfile.db";
                                               File dbpath = ctx.getDatabasePath(dbname);
                                               Log.e("tag", "qeer " + dbpath);
                                               //  /data/user/0/net.sqindia.jk/databases/exportfile.db

                                               ExportDatabaseCSVTask task = new ExportDatabaseCSVTask();
                                               task.execute();

                                           }
                                       }

        );

        //  Adding Their own Plan and Amount ///
        btnSEmail.setOnClickListener(new View.OnClickListener()

                                     {
                                         @Override
                                         public void onClick(View view) {
                                             //sendMail();
                                             Intent intent = new Intent(getApplicationContext(), Plan_List.class);
                                             startActivity(intent);
                                             finish();
                                         }
                                     }

        );


    }

    //************  Creating Sqlite Database ************//
    protected void createDatabase() {
        db = openOrCreateDatabase("PersonDB", Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS persons(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, name VARCHAR,phone INTEGER,email VARCHAR,address VARCHAR,plan VARCHAR,payment VARCHAR,amount INTEGER,date VARCHAR,billing_date VARCHAR,update_date VARCHAR);");
        db.execSQL("CREATE TABLE IF NOT EXISTS plan(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,type VARCHAR,amount INTEGER);");

    }





    private void exportDB() {

        Log.e("tagexpt", "qwertyu " );
        sd = new File(Environment.getExternalStorageDirectory() + "/exported/");
        data = Environment.getDataDirectory();
        //File mydir = new File(Environment.getExternalStorageDirectory() + "/NewsApp/");


        if (!sd.exists()) {
            sd.mkdirs();
        }


        FileChannel source = null;
        FileChannel destination = null;
        String currentDBPath = "/data/" + "net.sqindia.ezcabill" + "/databases/" + "PersonDB";
        String backupDBPath = "exportfile.db";


        File currentDB = new File(data, currentDBPath);
        File backupDB = new File(sd, backupDBPath);


        try {
            source = new FileInputStream(currentDB).getChannel();
            destination = new FileOutputStream(backupDB).getChannel();
            destination.transferFrom(source, 0, source.size());
            source.close();
            destination.close();


            Toast.makeText(this, "DB Exported!", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // changing db file in storage memory to cs file.......//
    public class ExportDatabaseCSVTask extends AsyncTask<String, Void, Boolean> {
        private final ProgressDialog dialog = new ProgressDialog(DashBoard.this);

        @Override
        protected void onPreExecute() {
            this.dialog.setMessage("Exporting database...");
            this.dialog.show();
        }

        protected Boolean doInBackground(final String... args) {
            String currentDBPath = "/data/" + "net.sqindia.ezcabill" + "/databases/exportfile.db";
            File dbFile = getDatabasePath("" + currentDBPath);
            System.out.println(dbFile);  // displays the data base path in your logcat
            File exportDir = new File(Environment.getExternalStorageDirectory(), "/exported/");

            if (!exportDir.exists()) {
                exportDir.mkdirs();
            }

            File file = new File(exportDir, "myfile.csv");
            try {
                file.createNewFile();
                CSVWriter csvWrite = new CSVWriter(new FileWriter(file));
                Cursor curCSV = db.rawQuery("select * from persons", null);
                csvWrite.writeNext(curCSV.getColumnNames());
                while (curCSV.moveToNext()) {
                    String arrStr[] = null;
                    String[] mySecondStringArray = new String[curCSV.getColumnNames().length];
                    for (int i = 0; i < curCSV.getColumnNames().length; i++) {
                        mySecondStringArray[i] = curCSV.getString(i);
                    }
                    csvWrite.writeNext(mySecondStringArray);
                }
                csvWrite.close();
                curCSV.close();
                return true;
            } catch (IOException e) {
                Log.e("MainActivity", e.getMessage(), e);
                return false;
            }
        }

        protected void onPostExecute(final Boolean success) {
            if (this.dialog.isShowing()) {
                this.dialog.dismiss();
            }
            if (success) {
                Toast.makeText(DashBoard.this, "Export successful!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(DashBoard.this, "Export failed", Toast.LENGTH_SHORT).show();
            }
        }
    }


  /*  public void sendMail() {


        // sd = new File(Environment.getDataDirectory(), "/data/net.sqindia.jk/databases/exportfile.db");
        sd = new File(Environment.getExternalStorageDirectory() + "/exported/");

        Intent i = new Intent(Intent.ACTION_SEND);
        i.putExtra(Intent.EXTRA_EMAIL, new String[]{"tulasi.ec10031@gmail.com"});
        i.putExtra(Intent.EXTRA_SUBJECT, "JK");
        i.putExtra(Intent.EXTRA_TEXT, "Jk Data base");
        i.setType("application/octet-stream");
        i.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File("/data/user/0/net.sqindia.jk/databases/exportfile.db")));
        startActivity(Intent.createChooser(i, "Send e-mail"));

        //  /data/"+"user"+"/0/"+"net.sqindia.jk"+"/databases/"+"exportfile.db

*/


/*  String[] mailto = {"tulasi.ec10031@gmail.com"};
        Uri uri = Uri.fromFile(new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/exported/","test" ));
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        emailIntent.putExtra(Intent.EXTRA_EMAIL, mailto);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Calc PDF Report");
        emailIntent.setType("application/pdf");
        emailIntent.putExtra(Intent.EXTRA_STREAM, uri);
        startActivity(Intent.createChooser(emailIntent, "Send email using:"));

*//*




    }
*/


    public int getCount(Cursor cs) {
        int count = 0;

        int k = cs.getCount();

        //Log.d("tag",""+k);

        while (cs.isLast()) {
            count = cs.getCount();
            cs.moveToNext();
            Log.d("tag0", "" + count);
        }

        cs.moveToLast();
        Log.d("tag1", "" + count);
/*
        while (cs.moveToFirst()) {
            count = cs.getInt(0);
            Log.e("tag",""+count);
        }
*/

        return count;
    }


    @Override
    public void onBackPressed() {

        new SweetAlertDialog(DashBoard.this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("EXIT")
                .setConfirmText("Yes,Exit")
                .setContentText("Do you want to Exit ?")
                .setCancelText("No,cancel")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {

                        finish();
                    }
                })
                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.cancel();
                    }
                })

                .show();


        // super.onBackPressed();
    }

}

