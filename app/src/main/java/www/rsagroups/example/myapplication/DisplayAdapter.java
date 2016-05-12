package www.rsagroups.example.myapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class DisplayAdapter extends BaseAdapter {
	private Context mContext;
	private ArrayList<String> id;
	private ArrayList<String> firstName;
	private ArrayList<String> lastName;
	String token;
	int j;

		public DisplayAdapter(Context c, ArrayList<String> id,ArrayList<String> fname, ArrayList<String> lname) {
			this.mContext = c;
			this.id = id;
			this.firstName = fname;
			this.lastName = lname;
		}

		public int getCount() {
			// TODO Auto-generated method stub
			Log.d("tag",String.valueOf(id.size()));
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

		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
		token = sharedPreferences.getString("send2", "");

		Holder mHolder;
		LayoutInflater layoutInflater;
		ArrayList<String> id1 = new ArrayList<String>();
		if (child == null) {
			layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			child = layoutInflater.inflate(R.layout.listcell, null);
			mHolder = new Holder();
			Typeface tf = Typeface.createFromAsset(mContext.getAssets(), "appfont.OTF");

			if (pos%2==0) {

				child.setEnabled(false);
				child.setBackground(mContext.getResources().getDrawable(R.color.par));
			} else {

				child.setBackground(mContext.getResources().getDrawable(R.color.par2));
			}

			mHolder.txt_id = (TextView) child.findViewById(R.id.txt_id);
			mHolder.txt_fName = (TextView) child.findViewById(R.id.txt_fName);
			mHolder.txt_rate=(TextView) child.findViewById(R.id.txt_rate);
			mHolder.txt_lName = (TextView) child.findViewById(R.id.txt_lName);

			mHolder.txt_id.setTypeface(tf);
			mHolder.txt_fName.setTypeface(tf);
			mHolder.txt_rate.setTypeface(tf);
			mHolder.txt_lName.setTypeface(tf);
			child.setTag(mHolder);
		} else {
			mHolder = (Holder) child.getTag();
		}

		for(int i=0;i<firstName.size();i++){
			j=i+1;
			id1.add(String.valueOf(j));
		}

		mHolder.txt_id.setText(id1.get(pos));
		mHolder.txt_rate.setText(token);
		mHolder.txt_fName.setText(firstName.get(pos));
		mHolder.txt_lName.setText(lastName.get(pos));
		return child;
	}

			public class Holder {
			TextView txt_id;
			TextView txt_fName;
			TextView txt_rate;
			TextView txt_lName;
		}

}
