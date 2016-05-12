package www.rsagroups.example.myapplication;

		import android.app.AlertDialog;
		import android.content.DialogInterface;
		import android.content.Intent;
		import android.content.SharedPreferences;
		import android.database.Cursor;
		import android.database.sqlite.SQLiteDatabase;
		import android.graphics.Typeface;
		import android.os.Bundle;
		import android.preference.PreferenceManager;
		import android.support.v7.app.AppCompatActivity;
		import android.text.SpannableStringBuilder;
		import android.text.Spanned;
		import android.util.Log;
		import android.view.View;
		import android.view.View.OnClickListener;
		import android.view.ViewGroup;
		import android.view.WindowManager;
		import android.widget.AdapterView;
		import android.widget.AdapterView.OnItemClickListener;
		import android.widget.Button;
		import android.widget.ListView;
		import android.widget.TextView;
		import java.text.DateFormat;
		import java.util.ArrayList;
		import java.util.Date;


		public class DisplayActivity extends AppCompatActivity {
		TextView copyright,txt_id,txt_fName,txt_lName;
		Button addbutton;
		String ss,token_footer;
		Typeface tf;
		private DbHelper mHelper;
		public AlertDialog.Builder alert;
		private SQLiteDatabase dataBase;
		int itemPosition;
		private ArrayList<String> userId = new ArrayList<String>();
		private ArrayList<String> user_fName = new ArrayList<String>();
		private ArrayList<String> user_lName = new ArrayList<String>();
		private ArrayList<String> userId1 = new ArrayList<String>();
		private ListView userList;
		private AlertDialog.Builder build;

			@Override
			public void onCreate(Bundle savedInstanceState) {
				super.onCreate(savedInstanceState);
				setContentView(R.layout.display_activity);
				getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
						WindowManager.LayoutParams.FLAG_FULLSCREEN);

				//***************change typeface*************
				FontChangeCrawler fontChanger = new FontChangeCrawler(getAssets(), "appfont.OTF");
				fontChanger.replaceFonts((ViewGroup) this.findViewById(android.R.id.content));
				String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
				ss = currentDateTimeString;
				Log.e("TAg", "sssss" + ss);
				tf = Typeface.createFromAsset(getAssets(), "appfont.OTF");
				SpannableStringBuilder SS = new SpannableStringBuilder("ADD ITEM");
				SS.setSpan(new CustomTypefaceSpan("ADD ITEM", tf), 0, SS.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
				getSupportActionBar().setTitle(SS);


				txt_id=(TextView)findViewById(R.id.txt_id);
				txt_fName=(TextView)findViewById(R.id.txt_fName);
				txt_lName=(TextView)findViewById(R.id.txt_lName);
				addbutton=(Button)findViewById(R.id.btnAdd);
				copyright=(TextView)findViewById(R.id.textrights);
				userList = (ListView) findViewById(R.id.List);


				Typeface tf = Typeface.createFromAsset(getAssets(), "appfont.OTF");
				addbutton.setTypeface(tf);
				copyright.setTypeface(tf);

				SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(DisplayActivity.this);
				token_footer = sharedPreferences.getString("footer", "");
				Log.e("tag", "getvalue" + token_footer);
				copyright.setText(token_footer);

				mHelper = new DbHelper(this);

				//add new record
				findViewById(R.id.btnAdd).setOnClickListener(new OnClickListener() {

					public void onClick(View v) {
						Intent i = new Intent(getApplicationContext(), AddActivity.class);
						i.putExtra("update", false);
						startActivity(i);
					}
				});


				userList.setOnItemClickListener(new OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> parent, View view, final int position, long id)
					{


						build = new AlertDialog.Builder(DisplayActivity.this);
						build.setTitle("Delete " + user_fName.get(position) + ""
								+ user_lName.get(position));
						build.setMessage("Do you want to delete ?");
						build.setPositiveButton("Yes",
								new DialogInterface.OnClickListener() {

									public void onClick(DialogInterface dialog,
														int which) {
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
