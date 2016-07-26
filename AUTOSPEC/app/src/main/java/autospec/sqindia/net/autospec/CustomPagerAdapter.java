package autospec.sqindia.net.autospec;


import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;

import uk.co.senab.photoview.PhotoView;

class CustomPagerAdapter extends PagerAdapter {

    Context mContext;
    LayoutInflater mLayoutInflater;

    ArrayList<String> image_data = new ArrayList<>();

    public CustomPagerAdapter(Context context, ArrayList<String> crc) {

        this.image_data = crc;
        mContext = context;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        Log.e("tag","size"+image_data.size());
    }

    @Override
    public int getCount() {
        return image_data.size();
    }


    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = mLayoutInflater.inflate(R.layout.pager_item, container, false);
      /*  PhotoView imageView;
        imageView = (PhotoView) itemView.findViewById(R.id.iv_photo);*/
        ImageView imageView = (ImageView) itemView.findViewById(R.id.asdf);
        //imageView.setImageResource(image_data.get(position));


        Log.e("tag","data"+image_data.get(position));

        Picasso.with(mContext)
                .load(new File(image_data.get(position)))
                .fit()
                .into(imageView);

        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }
}