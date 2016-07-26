package autospec.sqindia.net.autospec;


import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Ramya on 16-02-2016.
 */
public class CustomAdapter extends ArrayAdapter<String> {

    private Context context;
    private int layout;
    List<String> data;

    public CustomAdapter(Context context,int layout ,List<String> source) {
        super(context,R.layout.spinner_item_list,source);
        this.context = context;
        this.data = source;
        this.layout = layout;

    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        View view = super.getView(position, convertView, parent);

        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "ROBOTO-LIGHT.TTF");

        TextView suggestion = (TextView) view.findViewById(R.id.text);

        suggestion.setTypeface(tf);

        return view;
    }
}