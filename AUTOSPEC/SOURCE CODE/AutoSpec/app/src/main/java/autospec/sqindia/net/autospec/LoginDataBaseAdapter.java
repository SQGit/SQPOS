package autospec.sqindia.net.autospec;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

/**
 * Created by Admin on 07-06-2016.
 */
public class LoginDataBaseAdapter {


    static final String DATABASE_NAME = "login.db";
    private static final String IMAGES_TABLE = "ImagesTable";
    static final int DATABASE_VERSION = 1;
    public static final int NAME_COLUMN = 1;
    public static final String UNITNO = "UNITNO";
    public static final String RENTALNO = "RENTALNO";
    public static final String ID = "ID";
    public static final String DATE = "DATE";
    public static final String USERID= "USERID";
    public static final String IMAGE_ID= "IMAGE_ID";
    public static final String IMAGE= "IMAGE";




    // SQL Statement to create a new table.
    static final String DATABASE_CREATE = "create table " + "LOGIN" +
            "( " + "ID" + " integer primary key autoincrement," + "USERNAME text," + "EMAIL text," + "PASSWORD text); ";


    static final String NEW_INSPECTION = "create table " + "INSPECTION" +
            "( " + "ID" + " integer primary key autoincrement," + "UNITNO text," + "RENTALNO text," + "DATE text); ";


    static final String IMAGE_TABLE = "create table " + "IMAGEUPLOAD" +
            "( " + "ID" + " integer primary key autoincrement," + "USERID text," + "IMAGE_ID text," + "PATH_ID text, "+ "IMAGE BLOB," + "COUNT_IMAGE text); ";

    static final String IMAGE_TABLE1 = "create table " + "SIGNATUREPAD" +
            "( " + "ID" + " integer primary key autoincrement," + "USERID text," + "SIGNATURE BLOB); ";



    public SQLiteDatabase db;

    // Context of the application using the database.
    private final Context context;


    // Database open/upgrade helpergg
    private DataBaseHelper dbHelper;

    public LoginDataBaseAdapter(Context _context) {
        context = _context;
        dbHelper = new DataBaseHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public LoginDataBaseAdapter open() throws SQLException {
        db = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        db.close();
    }




    //***************************login table*********************
    public void insertEntry(String userName,String email,String password)
    {
        ContentValues newValues = new ContentValues();
        // Assign values for each row.
        newValues.put("USERNAME", userName);
        newValues.put("EMAIL",email);
        newValues.put("PASSWORD",password);
        db.insert("LOGIN", null, newValues);
    }


    //********************************insert inspection table**************************
    public void inspection_details(String unitno, String rentalno, String date) {
        ContentValues newValues = new ContentValues();
        newValues.put("UNITNO", unitno);
        newValues.put("RENTALNO", rentalno);
        newValues.put("DATE", date);
        db.insert("INSPECTION", null, newValues);
    }


    //******************insert Images table*********************
    public void insertImage(String storedid, String image_id, String path_id,String image_path,String count_image)
    {
        ContentValues image = new ContentValues();
        image.put("USERID", storedid);
        image.put("IMAGE_ID", image_id);
        image.put("PATH_ID",path_id);
        image.put("IMAGE", image_path);
        image.put("COUNT_IMAGE",count_image);
        db.insert("IMAGEUPLOAD", null, image);
    }

    //******************insert Signature table*********************
    public void inserSignature(String str_user_id, String imagepath) {
        ContentValues signature = new ContentValues();
        signature.put("USERID", str_user_id);
        signature.put("SIGNATURE", imagepath);
        db.insert("SIGNATUREPAD", null, signature);
    }


    //*********************update inspection table***********************
    public void updateInspection(String storedId, String edit_unit_no, String edit_rental_no, String str_inspection_date) {
        // Define the updated row content.
        ContentValues cv = new ContentValues();
        cv.put("UNITNO", edit_unit_no); //These Fields should be your String values of actual column names
        cv.put("RENTALNO", edit_rental_no);
        cv.put("DATE", str_inspection_date);
        db.update("INSPECTION", cv, "ID=" + storedId, null);
    }


    //****************check unitno exists are not************
    public boolean checkEvent(String unitno, String rentalno)
    {
        db=dbHelper.getReadableDatabase();
        Cursor cursor = db.query("INSPECTION",
                new String[] { UNITNO,RENTALNO },
                UNITNO + " = ? and "+ RENTALNO + " = ?" ,
                new String[] {unitno,rentalno},
                null, null, null, null);

        if(cursor.moveToFirst())
            return true; //row exists
        else
            return false;
    }



    //*******************get password******************
    public String getSinlgeEntry(String email) {
        Cursor cursor = db.query("LOGIN", null, " EMAIL=?", new String[]{email}, null, null, null);
        if (cursor.getCount() < 1) // UserName Not Exist
        {
            cursor.close();
            return "NOT EXIST";
        }
        cursor.moveToFirst();
        String password = cursor.getString(cursor.getColumnIndex("PASSWORD"));
        cursor.close();
        return password;
    }



    //******************get name****************
    public String getName(String email) {

        Cursor cursor = db.query("LOGIN", null, " EMAIL=?", new String[]{email}, null, null, null);
        if (cursor.getCount() < 1) // UserName Not Exist
        {
            cursor.close();
            return "NOT EXIST";
        }
        cursor.moveToFirst();
        String name = cursor.getString(cursor.getColumnIndex("USERNAME"));
        cursor.close();
        return name;
    }


    //******************get email****************
    public String getEmail(String email) {

        Cursor cursor = db.query("LOGIN", null, " EMAIL=?", new String[]{email}, null, null, null);
        if (cursor.getCount() < 1) // UserName Not Exist
        {
            cursor.close();
            return "NOT EXIST";
        }
        cursor.moveToFirst();
        String name = cursor.getString(cursor.getColumnIndex("EMAIL"));
        cursor.close();
        return name;
    }

    //******************get id****************
    public String getId(String edit_unit_no) {

        Cursor cursor = db.query("INSPECTION", null, " UNITNO=?", new String[]{edit_unit_no}, null, null, null);
        if (cursor.getCount() < 1) // UserName Not Exist
        {
            cursor.close();
            return "Unit No";
        }
        cursor.moveToFirst();
        String id = cursor.getString(cursor.getColumnIndex("ID"));
        cursor.close();
        return id;
    }


    //******************get id****************
    public String getId1(String edit_unit_no) {

        Cursor cursor = db.query("INSPECTION", null, " RENTALNO=?", new String[]{edit_unit_no}, null, null, null);
        if (cursor.getCount() < 1) // UserName Not Exist
        {
            cursor.close();
            return "Unit No";
        }
        cursor.moveToFirst();
        String id = cursor.getString(cursor.getColumnIndex("ID"));
        cursor.close();
        return id;
    }

//********************getpassword*************
    public String getPassword(String email) {

        Cursor cursor = db.query("LOGIN", null, " EMAIL=?", new String[]{email}, null, null, null);
        if (cursor.getCount() < 1) // UserName Not Exist
        {
            cursor.close();
            return "NOT EXIST";
        }
        cursor.moveToFirst();
        String name = cursor.getString(cursor.getColumnIndex("PASSWORD"));
        cursor.close();
        return name;
    }


    //********************count*************
    public String count(String autoparts_id) {

        Cursor cursor = db.query("IMAGEUPLOAD", null, " COUNT_IMAGE=?", new String[]{autoparts_id}, null, null, null);
       if (cursor.getCount() < 1) // UserName Not Exist
        {
            cursor.close();
            return "NOT EXIST";
        }
        cursor.moveToFirst();
        String count = cursor.getString(cursor.getColumnIndex("IMAGE_ID"));
        cursor.close();
        return count;
    }

    //***************get values for filter class
    public Cursor fetchdata(String qry1) {
        Cursor c2 = null;
        try {
            // SQLiteDatabase sdb1;
            db = dbHelper.getReadableDatabase();
            c2 = db.rawQuery(qry1, null);
            Log.e("tag", "<-----cursor---->" + c2);
        } catch (Exception e) {
            System.out.println("DATABASE ERROR " + e);
        }
        return c2;
    }


    //*****************filter unitno read using spinner******
    public Cursor readDataagg(String qry2) {
        Cursor c2 = null;
        try {
            // SQLiteDatabase sdb1;
            db = dbHelper.getReadableDatabase();
            c2 = db.rawQuery(qry2, null);
            Log.e("tag", "<-----*********---->" + c2);
        } catch (Exception e) {
            System.out.println("DATABASE ERROR " + e);
        }
        return c2;
    }



    //*****************filter agreementno read using spinner******
    public Cursor readDatarental(String qry2) {
        Cursor c2 = null;
        try {
            // SQLiteDatabase sdb1;
            db = dbHelper.getReadableDatabase();
            c2 = db.rawQuery(qry2, null);
            Log.e("tag", "<-----*********---->" + c2);
        } catch (Exception e) {
            System.out.println("DATABASE ERROR " + e);
        }
        return c2;
    }


    //*****************filter unit data read using spinner******
    public Cursor readDataunit(String qry3) {
        Cursor c2 = null;
        try {
            // SQLiteDatabase sdb1;
            db = dbHelper.getReadableDatabase();
            c2 = db.rawQuery(qry3, null);
            Log.e("tag", "<-----*********---->" + c2);
        } catch (Exception e) {
            System.out.println("DATABASE ERROR " + e);}
        return c2;
    }




    //get Unit Number using for spin1
    public String getAggnum(String storedId) {


        Cursor cursor = db.query("INSPECTION", null, " ID=?", new String[]{storedId}, null, null, null);
        if (cursor.getCount() < 1) // UserName Not Exist
        {
            cursor.close();
            return "Unit No";
        }
        cursor.moveToFirst();
        String id = cursor.getString(cursor.getColumnIndex("RENTALNO"));
        cursor.close();
        return id;
    }


    //get Aggrement Number using for spin2
    public String getUnitNo(String storedId)
    {
        Cursor cursor = db.query("INSPECTION", null, " ID=?", new String[]{storedId}, null, null, null);
        if (cursor.getCount() < 1) // UserName Not Exist
        {
            cursor.close();
            return "Unit No";
        }
        cursor.moveToFirst();
        String id = cursor.getString(cursor.getColumnIndex("UNITNO"));
        cursor.close();
        return id;
    }


//***************get images for view image class
    public Cursor getFilterData(String query) {

        Cursor c2 = null;
        try {
            // SQLiteDatabase sdb1;
            db = dbHelper.getReadableDatabase();
            c2 = db.rawQuery(query, null);
            Log.e("tag", "<-----*********---->" + c2);
        } catch (Exception e) {
            System.out.println("DATABASE ERROR " + e);
        }
        return c2;

    }



    //***************get images for view image class
    public Cursor getCountQuery(String query) {

        Cursor vv = null;
        try {
            // SQLiteDatabase sdb1;
            db = dbHelper.getReadableDatabase();
            vv = db.rawQuery(query, null);
            Log.e("tag", "<-----*********---->" + query);
        } catch (Exception e) {
            System.out.println("DATABASE ERROR " + e);
        }
        return vv;

    }

    //***************get images for view image class
    public Cursor getFilterData1(String query) {

        Cursor c2 = null;
        try {
            // SQLiteDatabase sdb1;
            db = dbHelper.getReadableDatabase();
            c2 = db.rawQuery(query, null);
            Log.e("tag", "<-----*********---->" + c2);
        } catch (Exception e) {
            System.out.println("DATABASE ERROR " + e);
        }
        return c2;

    }

    //***************get images for signature
    public Cursor getSignatureImage(String query) {


        Cursor c2 = null;
        try {
            // SQLiteDatabase sdb1;
            db = dbHelper.getReadableDatabase();
            c2 = db.rawQuery(query, null);
            Log.e("tag", "<-----*********---->" + c2);
        } catch (Exception e) {
            System.out.println("DATABASE ERROR " + e);
        }
        return c2;
    }

    public Cursor getSignaturePath(String signaturepath) {

        Cursor c2 = null;
        try {
            // SQLiteDatabase sdb1;
            db = dbHelper.getReadableDatabase();
            c2 = db.rawQuery(signaturepath, null);
            Log.e("tag", "<-----*********---->" + c2);
        } catch (Exception e) {
            System.out.println("DATABASE ERROR " + e);
        }
        return c2;
    }


}