package net.sqindia.sqpos;





	import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Date;


public class AddActivity extends AppCompatActivity implements OnClickListener {

		private Button btn_save,cancel;
	    TextView bottom;
		private EditText edit_first,edit_last;
		private DbHelper mHelper;
		private SQLiteDatabase dataBase;
		private String id,fname,lname;
		private boolean isUpdate;
		String ss,token_footer;
		Typeface tf;

				@Override
				public void onCreate(Bundle savedInstanceState) {
				super.onCreate(savedInstanceState);
				setContentView(R.layout.add_activity);
					getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
							WindowManager.LayoutParams.FLAG_FULLSCREEN);

					//***********change typeface************
					FontChangeCrawler fontChanger = new FontChangeCrawler(getAssets(), "appfont.OTF");
					fontChanger.replaceFonts((ViewGroup) this.findViewById(android.R.id.content));
					String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
					ss = currentDateTimeString;
					Log.e("TAg", "sssss" + ss);

					tf = Typeface.createFromAsset(getAssets(), "appfont.OTF");
					SpannableStringBuilder SS = new SpannableStringBuilder("ADD ITEM");
					SS.setSpan(new CustomTypefaceSpan("ADD ITEM", tf), 0, SS.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
					getSupportActionBar().setTitle(SS);

					//***********findview************
					btn_save=(Button)findViewById(R.id.btn1);
					cancel=(Button)findViewById(R.id.btn2);
					edit_first=(EditText)findViewById(R.id.ed1);
					edit_last=(EditText)findViewById(R.id.ed2);
					bottom=(TextView)findViewById(R.id.textrights);

						Typeface tf = Typeface.createFromAsset(getAssets(), "appfont.OTF");
						edit_first.setTypeface(tf);
						edit_last.setTypeface(tf);
						btn_save.setTypeface(tf);
						cancel.setTypeface(tf);
						bottom.setTypeface(tf);

					cancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
					Intent i1=new Intent(getApplicationContext(),DisplayActivity.class);
					startActivity(i1);
					}
			});

       isUpdate=getIntent().getExtras().getBoolean("update");
        if(isUpdate)
        {
        	id=getIntent().getExtras().getString("ID");
        	fname=getIntent().getExtras().getString("Fname");
        	lname=getIntent().getExtras().getString("Lname");
        	edit_first.setText(fname);
        	edit_last.setText(lname);
        }
         
         btn_save.setOnClickListener(this);
         mHelper=new DbHelper(this);
         }

		// saveButton click event
		public void onClick(View v) {
			fname = edit_first.getText().toString().trim();
			lname = edit_last.getText().toString().trim();

			if (fname.matches("[0-9]+")) {
				Toast.makeText(this, "Please Check The product Item", Toast.LENGTH_SHORT).show();
			} else if (fname
					.matches("")) {
				Toast.makeText(this, "You did not enter a Item Name", Toast.LENGTH_SHORT).show();

			} else if (lname.matches("")) {
				Toast.makeText(this, "You did not enter a Item Price", Toast.LENGTH_SHORT).show();
			} else if (fname.matches("") & (lname.matches(""))) {
				Toast.makeText(this, "You did not enter any value", Toast.LENGTH_SHORT).show();
			} else {
				saveData();
			}

		}
			private void saveData(){
				dataBase=mHelper.getWritableDatabase();
				ContentValues values=new ContentValues();
				values.put(DbHelper.KEY_FNAME,fname);
				values.put(DbHelper.KEY_LNAME,lname );

				System.out.println("");
				if(isUpdate)
				{
					//update database with new data
					dataBase.update(DbHelper.TABLE_NAME, values, DbHelper.KEY_ID+"="+id, null);
				}
				else {
					//insert data into database
					dataBase.insert(DbHelper.TABLE_NAME, null, values);
				}
				//close database
				dataBase.close();
				Intent inte = new Intent(getApplicationContext(),DisplayActivity.class);
				startActivity(inte);
				finish();
			}
		}
