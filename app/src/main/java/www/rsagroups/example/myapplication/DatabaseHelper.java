package www.rsagroups.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by User on 17-11-2015.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    public static final int DB_VER = 1;
    public static final String DB_NAME = "Habitat.db";
    public static final String TABLE1 = "bill";
    public static final String _ID = "_billid";
    public static final String PRODUCTNAME = "feedback";
    public static final String QTY = "date";
    public static final String PRICE = "name";
    public static final String QUERY1 = " CREATE TABLE " + TABLE1 + "(" + _ID + " INTEGER AUTO INCREMENT,   " + PRODUCTNAME + " TEXT PRIMARY KEY, "+  QTY + " TEXT, " + PRICE + " TEXT);";


    //SQLiteDatabase sdb;
    // DatabaseHelper dbh1;


    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VER);
        Log.d("tag", "dbcreated");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(QUERY1);
        Log.d("tag", "table 1 created");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public void insert1(DatabaseHelper db, String product,String qty,String price) {
        SQLiteDatabase sdb = db.getWritableDatabase();
        ContentValues contentValue = new ContentValues();
        contentValue.put(PRODUCTNAME, product);
        contentValue.put(QTY, qty);
        contentValue.put(PRICE, price);
        sdb.insert(TABLE1, null, contentValue);
        Log.d("tag", "data inserted");
        sdb.close();
    }

    public Cursor get2(DatabaseHelper dbh) {

        SQLiteDatabase sqd = dbh.getReadableDatabase();
        String selecQuery = "SELECT * FROM " + TABLE1;
        Cursor cursor = sqd.rawQuery(selecQuery, null);
        return cursor;
    }

    public DatabaseHelper opendb() {
        // sdb = dbh1.getReadableDatabase();
        return this;
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
}
