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
        String CREATE_TABLE2="CREATE TABLE "+TABLE_NAME2+" ("+KEY_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+DATE+" TEXT, "+BILL_NO+" TEXT, "+CUSTOMER_NAME+ " TEXT, "+GRAND_TOTAL+" TEXT)";
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



    public void insert3(DbHelper db, String date,String billno,String cusname, String total) {
        SQLiteDatabase sdb = db.getWritableDatabase();
        ContentValues contentValue = new ContentValues();
        contentValue.put(DATE, date);
        contentValue.put(BILL_NO, billno);
        contentValue.put(CUSTOMER_NAME, cusname);
        contentValue.put(GRAND_TOTAL, total);


        sdb.insert(TABLE_NAME2, null, contentValue);
        Log.d("tag", "data inserted");
        sdb.close();
    }

    public void update(DbHelper db, String date,String billno,String name, String perprice, String qty, String total) {
        SQLiteDatabase sdb = db.getWritableDatabase();
        ContentValues contentValue = new ContentValues();
        contentValue.put(DATE, date);
        contentValue.put(BILL_NO, billno);
        contentValue.put(KEY_FNAME, name);
        contentValue.put(KEY_LNAME, perprice);
        contentValue.put(QTY, qty);
        contentValue.put(TOTAL, total);


        sdb.update(TABLE_NAME1, contentValue, "bno="+billno, null);
        Log.d("tag", "data updated");
        sdb.close();
    }

    public void delete_item(DbHelper database, int item_id)
    {
        SQLiteDatabase sqLiteDatabase = database.getWritableDatabase();
        Log.d("item_id:","%%%%%%%%%%%%% in helper class"+item_id);
        sqLiteDatabase.delete(TABLE_NAME, KEY_ID + "=" + item_id, null);
    }

    public Cursor fetchdata(String qry2) {
        Cursor c2 = null;
        try{
            SQLiteDatabase sdb1;
            sdb1= getReadableDatabase();
            c2 = sdb1.rawQuery(qry2,null);
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



}
