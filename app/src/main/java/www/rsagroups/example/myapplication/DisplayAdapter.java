package www.rsagroups.example.myapplication;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
/**
 * adapter to populate listview with data
 * @author ketan(Visit my <a
 *         href="http://androidsolution4u.blogspot.in/">blog</a>)
 */
public class DisplayAdapter extends BaseAdapter {

	private Context mContext;
	private ArrayList<String> id;
	private ArrayList<String> firstName;
	private ArrayList<String> lastName;




		public DisplayAdapter(Context c, ArrayList<String> id,ArrayList<String> fname, ArrayList<String> lname) {
			this.mContext = c;

			this.id = id;
			this.firstName = fname;
			this.lastName = lname;
		}

		public int getCount() {
			// TODO Auto-generated method stub
			return id.size();
		}

		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

	public View getView(int pos, View child, ViewGroup parent) {

		Holder mHolder;
		LayoutInflater layoutInflater;
		if (child == null) {
			layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			child = layoutInflater.inflate(R.layout.listcell, null);
			mHolder = new Holder();
			Typeface tf = Typeface.createFromAsset(mContext.getAssets(), "appfont.OTF");

			if (pos%2==0) {
				//itemView.setBackgroundResource(R.color.blue);
				//Toast.makeText(context,"Varun(9854752445) Takes responsibility of this person",Toast.LENGTH_LONG).show();
				child.setEnabled(false);
				child.setBackground(mContext.getResources().getDrawable(R.color.par));
				

				// lin.setBackground(context.getResources().getDrawable(R.color.sad));
			} else {

				child.setBackground(mContext.getResources().getDrawable(R.color.par2));


				//itemView.setBackgroundResource(R.color.navy);
			}

			mHolder.txt_id = (TextView) child.findViewById(R.id.txt_id);
			mHolder.txt_fName = (TextView) child.findViewById(R.id.txt_fName);
			mHolder.txt_lName = (TextView) child.findViewById(R.id.txt_lName);
			mHolder.txt_id.setTypeface(tf);
			mHolder.txt_fName.setTypeface(tf);
			mHolder.txt_lName.setTypeface(tf);
			child.setTag(mHolder);
		} else {
			mHolder = (Holder) child.getTag();
		}
		mHolder.txt_id.setText(id.get(pos));
		mHolder.txt_fName.setText(firstName.get(pos));
		mHolder.txt_lName.setText("Rs  "+lastName.get(pos));

		return child;
	}

		public class Holder {
			TextView txt_id;
			TextView txt_fName;
			TextView txt_lName;
		}

}
