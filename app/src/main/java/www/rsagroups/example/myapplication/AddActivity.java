package www.rsagroups.example.myapplication;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * activity to get input from user and insert into SQLite database
 * @author ketan(Visit my <a
 *         href="http://androidsolution4u.blogspot.in/">blog</a>)
 */
		public class AddActivity extends AppCompatActivity implements OnClickListener {

		private Button btn_save,cancel,back;
			TextView head,bottom;
		private EditText edit_first,edit_last;
		private DbHelper mHelper;
		private SQLiteDatabase dataBase;
		private String id,fname,lname;
		private boolean isUpdate;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		/*getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);*/
        setContentView(R.layout.add_activity);

		/*getSupportActionBar().setHomeButtonEnabled(true);
		getSupportActionBar().setTitle(Html.fromHtml("<font color='#ffffff'><big><b>HABITAT</b></big> </font>"));
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeAsUpIndicator(R.drawable.back_arrow);*/

        btn_save=(Button)findViewById(R.id.btn1);
		cancel=(Button)findViewById(R.id.btn2);
		back=(Button)findViewById(R.id.back_icon);
        edit_first=(EditText)findViewById(R.id.ed1);
        edit_last=(EditText)findViewById(R.id.ed2);
		head=(TextView)findViewById(R.id.textView7);
		bottom=(TextView)findViewById(R.id.textrights);


		Typeface tf = Typeface.createFromAsset(getAssets(), "appfont.OTF");
		head.setTypeface(tf);
		edit_first.setTypeface(tf);
		edit_last.setTypeface(tf);
		btn_save.setTypeface(tf);
		cancel.setTypeface(tf);
		bottom.setTypeface(tf);


cancel.setOnClickListener(new OnClickListener() {
	@Override
	public void onClick(View v) {
		Intent i1=new Intent(getApplicationContext(),AsciiFragment.class);
		startActivity(i1);

	}
});


		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i2=new Intent(getApplicationContext(),MainActivity.class);
				startActivity(i2);

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

		fname=edit_first.getText().toString().trim();
		lname=edit_last.getText().toString().trim();
		if(fname.length()>0 && lname.length()>0)
		{
			saveData();
		}
		else if (fname
				.matches("")) {
			Toast.makeText(this, "You did not enter a Item Name", Toast.LENGTH_SHORT).show();
			return;
		}
		else if (lname.matches("")){
			Toast.makeText(this, "You did not enter a Item Price", Toast.LENGTH_SHORT).show();

		/*{
			AlertDialog.Builder alertBuilder=new AlertDialog.Builder(AddActivity.this);
			alertBuilder.setTitle("Invalid Data");
			alertBuilder.setMessage("Please, Enter valid data");
			alertBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
				
				public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();*/
					
				}
		else if(fname.matches("")& (lname.matches("")))
		{
			Toast.makeText(this, "You did not enter any value", Toast.LENGTH_SHORT).show();
		}

			//alertBuilder.create().show();

		
}


	/**
	 * save data into SQLite
	 */
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
		finish();
		
		
	}

}