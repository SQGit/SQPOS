package www.rsagroups.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by RSA on 14-02-2016.
 */
public class DbHelper extends SQLiteOpenHelper {
    static String DATABASE_NAME="userdata.db";
    public static final String TABLE_NAME="user";
    public static final String TABLE_NAME1="bill";
    public static final String SET_TABLE="set_table";
    public static final String TABLE_NAME2="bill_history";
    public static final String CUSTOMER_NAME="customername";
    public static final String KEY_FNAME="fname";//product name
    public static final String KEY_LNAME="lname";//price
    public static final String BILL_NO="bno";
    public static final String QTY="bqty";
    public static final String TOTAL="btot";
    public static final String GRAND_TOTAL="gtot";
    public static final String KEY_ID="id";
    public static final String DATE="date";
    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE="CREATE TABLE "+TABLE_NAME+" ("+KEY_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+KEY_FNAME+" TEXT, "+KEY_LNAME+" TEXT)";
    String CREATE_TABLE1="CREATE TABLE "+TABLE_NAME1+" ("+KEY_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+DATE+" TEXT, "+BILL_NO+" TEXT,  "+CUSTOMER_NAME+" TEXT, "+SET_TABLE+" TEXT, "+KEY_FNAME+" TEXT, "+KEY_LNAME+" TEXT,  "+QTY+" TEXT, "+TOTAL+" TEXT)";
    String CREATE_TABLE2="CREATE TABLE "+TABLE_NAME2+" ("+KEY_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+DATE+" TEXT, "+BILL_NO+" TEXT, "+CUSTOMER_NAME+ " TEXT, "+SET_TABLE+ " TEXT, "+GRAND_TOTAL+" TEXT)";
    db.execSQL(CREATE_TABLE);
    db.execSQL(CREATE_TABLE1);
    db.execSQL(CREATE_TABLE2);

}

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME1);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME2);
        onCreate(db);

    }


    public void insert1(DbHelper db, String date,String billno,String cusname,String tablename,String name, String perprice, String qty, String total) {
        SQLiteDatabase sdb = db.getWritableDatabase();
        ContentValues contentValue = new ContentValues();
        contentValue.put(DATE, date);
        contentValue.put(BILL_NO, billno);
        contentValue.put(CUSTOMER_NAME, cusname);
        contentValue.put(SET_TABLE, tablename);
        contentValue.put(KEY_FNAME, name);
        contentValue.put(KEY_LNAME, perprice);
        contentValue.put(QTY, qty);
        contentValue.put(TOTAL, total);


        sdb.insert(TABLE_NAME1, null, contentValue);

        Log.d("tag", "data inserted");
        sdb.close();
    }













    public void update2(DbHelper db,String date,String billno,String name,String table,String itemname,String itemprz, String qty,String tot1) {
        SQLiteDatabase sdb = db.getWritableDatabase();
        ContentValues contentValue = new ContentValues();


        Log.e("tag", "vvd" + date + billno + itemprz + qty + tot1);




        contentValue.put(DATE, date);
        contentValue.put(BILL_NO, billno);
        contentValue.put(CUSTOMER_NAME,name);
        contentValue.put(SET_TABLE,table);
        contentValue.put(KEY_FNAME, itemname);
        contentValue.put(KEY_LNAME, tot1);
        contentValue.put(QTY, qty);
        contentValue.put(TOTAL, itemprz);


        sdb.insert(TABLE_NAME1, null, contentValue);



        Log.d("tag", "data updated");
        sdb.close();
    }




    public void delete_item(DbHelper database)
    {
        SQLiteDatabase sqLiteDatabase = database.getWritableDatabase();
        sqLiteDatabase.delete(TABLE_NAME2, null, null);
    }

    public void delete_item1(DbHelper database,String billno)
    {

        String qry = "DELETE FROM bill_history WHERE bno ="+billno;
        SQLiteDatabase sqLiteDatabase = database.getWritableDatabase();
        sqLiteDatabase.execSQL(qry);
    }



    public void delete_item2(DbHelper database,String billno)
    {

        String qry = "DELETE FROM bill WHERE bno ="+billno;
        SQLiteDatabase sqLiteDatabase = database.getWritableDatabase();
        sqLiteDatabase.execSQL(qry);
    }



    public Cursor fetchdata(String qry2) {
        Cursor c2 = null;
        try{
            SQLiteDatabase sdb1;
            sdb1= getReadableDatabase();
            c2 = sdb1.rawQuery(qry2, null);
            Log.e("tag","<-----cursor---->"+c2);
        }
        catch (Exception e)
        {
            System.out.println("DATABASE ERROR " + e);

        }

        return c2;
    }


    public Cursor fetchdata1(String query) {
       Cursor c3=null;
        try
        {
            SQLiteDatabase sdb1;
            sdb1= getReadableDatabase();
            c3 = sdb1.rawQuery(query,null);
        }
        catch (Exception e)
        {
            System.out.println("DATABASE ERROR " + e);

        }
        return c3;
    }


    public Cursor fetchdata3(String qry2) {
        Cursor c2 = null;
        try{
            SQLiteDatabase sdb1;
            sdb1= getReadableDatabase();
            c2 = sdb1.rawQuery(qry2, null);
            Log.e("tag","<-----cursor---->"+c2);
        }
        catch (Exception e)
        {
            System.out.println("DATABASE ERROR " + e);

        }

        return c2;
    }
    public Cursor me_name(String billno) {

        String qq ="SELECT customername,set_table  FROM bill_history WHERE bno =220426121";//+billno;
        Cursor c = null;
        try{


            SQLiteDatabase sdb;
            sdb= getReadableDatabase();
            c = sdb.rawQuery(qq, null);


        }

        catch (Exception e)
        {
            System.out.println("DATABASE ERROR " + e);

        }

        return c;
    }



    //String qq ="SELECT SUM(gtot) FROM bill_history WHERE date ="+date;


    public Cursor me_name3(String date) {

        String qq ="SELECT SUM(gtot) FROM bill_history WHERE date  ="+"\"22/04/2016\"";
        Cursor cc = null;
        try{


            SQLiteDatabase sdb;
            sdb= getReadableDatabase();
            cc = sdb.rawQuery(qq, null);




        }

        catch (Exception e)
        {
            System.out.println("DATABASE ERROR " + e);

        }

        if (cc != null) {
            cc.moveToFirst();
        }

        return cc;
    }





}
