package www.rsagroups.example.myapplication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * activity to display all records from SQLite database
 * @author ketan(Visit my <a
 *         href="http://androidsolution4u.blogspot.in/">blog</a>)
 */
public class DisplayActivity extends AppCompatActivity {
	TextView additem,copyright,txt_id,txt_fName,txt_lName;
	Button addbutton,back;
	String ss;
	private DbHelper mHelper;
	public AlertDialog.Builder alert;
	private SQLiteDatabase dataBase;
	int itemPosition;
	private ArrayList<String> userId = new ArrayList<String>();
	private ArrayList<String> user_fName = new ArrayList<String>();
	private ArrayList<String> user_lName = new ArrayList<String>();

	private ListView userList;
	private AlertDialog.Builder build;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.display_activity);

		String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
		ss=currentDateTimeString;
		Log.e("TAg", "sssss" + ss);


		additem=(TextView)findViewById(R.id.textView7);
		txt_id=(TextView)findViewById(R.id.txt_id);
		txt_fName=(TextView)findViewById(R.id.txt_fName);
		txt_lName=(TextView)findViewById(R.id.txt_lName);
		addbutton=(Button)findViewById(R.id.btnAdd);
		copyright=(TextView)findViewById(R.id.textrights);
		userList = (ListView) findViewById(R.id.List);

		Typeface tf = Typeface.createFromAsset(getAssets(), "appfont.OTF");
		additem.setTypeface(tf);
		addbutton.setTypeface(tf);
		copyright.setTypeface(tf);
		mHelper = new DbHelper(this);

		//add new record
		findViewById(R.id.btnAdd).setOnClickListener(new OnClickListener() {

			public void onClick(View v) {

				Intent i = new Intent(getApplicationContext(),
						AddActivity.class);
				i.putExtra("update", false);
				startActivity(i);

			}
		});


		back=(Button)findViewById(R.id.back_icon);
		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent backop = new Intent(getApplicationContext(), MainActivity.class);
				startActivity(backop);

			}
		});


		userList.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, final int position, long id)
			{

				Log.d("tag","selected id ==>"+id);
				Log.d("tag","selected position ==>"+position);
				Log.d("tag","selected view ==>"+view);
				build = new AlertDialog.Builder(DisplayActivity.this);
				build.setTitle("Delete " + user_fName.get(position) + ""
						+ user_lName.get(position));
				build.setMessage("Do you want to delete ?");
				build.setPositiveButton("Yes",
						new DialogInterface.OnClickListener() {

							public void onClick(DialogInterface dialog,
												int which) {

								 /*
								TextView ITEM_ID = (TextView)findViewById(R.id.txt_id);
								String text = ITEM_ID.getText().toString();
								int selected_id = Integer.parseInt(text);
								mHelper.delete_item(mHelper,selected_id);
								displayData();*/
								dataBase.delete(mHelper.TABLE_NAME,mHelper.KEY_ID + "="+ userId.get(position), null);
								displayData();
								dialog.cancel();
							}
						});

				build.setNegativeButton("No",
						new DialogInterface.OnClickListener() {

							public void onClick(DialogInterface dialog,
												int which) {
								dialog.cancel();
							}
						});
				AlertDialog alert = build.create();
				alert.show();

				//return true;


			}
		});




		userList.setOnItemLongClickListener(new OnItemLongClickListener() {

			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
										   final int arg2, long arg3) {


				build = new AlertDialog.Builder(DisplayActivity.this);
				build.setTitle("Delete " + user_fName.get(arg2) + " "
						+ user_lName.get(arg2));
				build.setMessage("Do you want to delete ?");
				build.setPositiveButton("Yes",
						new DialogInterface.OnClickListener() {

							public void onClick(DialogInterface dialog,
												int which) {





								/*Toast.makeText(
										getApplicationContext(),
										user_fName.get(arg2) + " "
												+ user_lName.get(arg2)
												+ " is deleted.", 3000).show();

								dataBase.delete(
										DbHelper.TABLE_NAME,
										DbHelper.KEY_ID + "="
												+ userId.get(arg2), null);*/
								displayData();
								dialog.cancel();
							}
						});

				build.setNegativeButton("No",
						new DialogInterface.OnClickListener() {

							public void onClick(DialogInterface dialog,
												int which) {
								dialog.cancel();
							}
						});
				AlertDialog alert = build.create();
				alert.show();

				return true;
			}
		});



	}


	@Override
	protected void onResume() {
		displayData();
		super.onResume();
	}


	private void displayData() {
		dataBase = mHelper.getWritableDatabase();
		Cursor mCursor = dataBase.rawQuery("SELECT * FROM "+ DbHelper.TABLE_NAME, null);

		userId.clear();
		user_fName.clear();
		user_lName.clear();
		if (mCursor.moveToFirst()) {
			do {



				userId.add(mCursor.getString(mCursor.getColumnIndex(DbHelper.KEY_ID)));
				user_fName.add(mCursor.getString(mCursor.getColumnIndex(DbHelper.KEY_FNAME)));
				user_lName.add(mCursor.getString(mCursor.getColumnIndex(DbHelper.KEY_LNAME)));


			} while (mCursor.moveToNext());
		}
		DisplayAdapter disadpt = new DisplayAdapter(DisplayActivity.this,userId, user_fName, user_lName);
		userList.setAdapter(disadpt);

		mCursor.close();
	}


	@Override
	public void onBackPressed() {
		Intent dash=new Intent(getApplicationContext(),MainActivity.class);
		startActivity(dash);

	}
}
