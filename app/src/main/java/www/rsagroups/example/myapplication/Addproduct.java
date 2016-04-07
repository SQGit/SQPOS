package www.rsagroups.example.myapplication;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;


/**
 * Created by RSA on 11-02-2016.
 */
public class Addproduct extends Activity {

    TableLayout table_layout;
	EditText firstname_et, lastname_et;
	Button addmem_btn;

	SQLController sqlcon;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_product);

		sqlcon = new SQLController(this);

		firstname_et = (EditText) findViewById(R.id.product_name);
		lastname_et = (EditText) findViewById(R.id.product_price);
		addmem_btn = (Button) findViewById(R.id.view);
		table_layout = (TableLayout) findViewById(R.id.tableLayout1);

		BuildTable();

		addmem_btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				table_layout.removeAllViews();
				String firstname = firstname_et.getText().toString();
				String lastname = lastname_et.getText().toString();

				firstname_et.setText("");
				lastname_et.setText("");

				// inserting data
				sqlcon.open();
				if (!firstname.isEmpty() && !lastname.isEmpty() ) {
					sqlcon.insertData(firstname, lastname);
					BuildTable();

				} else {
					Toast.makeText(getApplicationContext(),"PLZ ENTER THE FOOD NAME AND PRICE ",Toast.LENGTH_LONG).show();

				}


			}
		});

	}

	private void BuildTable() {

		sqlcon.open();
		Cursor c = sqlcon.readEntry();

		int rows = c.getCount();
		int cols = c.getColumnCount();

		c.moveToFirst();

        // outer for loop
		for (int i = 0; i < rows; i++) {

			TableRow row = new TableRow(this);
			row.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
					LayoutParams.WRAP_CONTENT));

			// inner for loop
			for (int j = 0; j < cols; j++) {

				TextView tv = new TextView(this);
				tv.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
						LayoutParams.WRAP_CONTENT));
				tv.setBackgroundResource(R.drawable.cell_shape);
				tv.setGravity(Gravity.CENTER);
				tv.setTextSize(18);
				tv.setPadding(0, 5, 0, 5);

				tv.setText(c.getString(j));

				row.addView(tv);

			}

			c.moveToNext();

			table_layout.addView(row);

		}
		sqlcon.close();
	}

}
