package autospec.sqindia.net.autospec;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


import com.github.chaossss.widget.view.CircleBadgedView;
import com.sloop.fonts.FontsManager;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

public class ViewImage extends Activity {

    Button btn_back;
    TextView txt_userid, txt_unitno, txt_rentno, txt_date, txt_cluster, txt_driversidefront, txt_passsidefront, txt_frontview, txt_driversiderear, txt_passsiderear, txt_rearview,txt_cussign;
    TextView unitid, unitno, aggno, inspectiondate;
    ImageView instrument_clusters, driverside_front, passenger_side_front, front_view, driver_side_front, passenger_side_rear, rear_view, sign;
    ListView listView;
    String storedid, stored_unit_no, aggrement_no, inspection_date, zoom_sign,ssig,img_id, img_path, userid;
    LoginDataBaseAdapter loginDataBaseAdapter;

    ArrayList<String> aaas = new ArrayList<>();
    ArrayList<String> imagelist = new ArrayList<>();
    HashMap<String, String> datas = new HashMap<>();
    String imagepath, imageid,image;
    PhotoViewAttacher attacher;
    String getimage,image_id,img,i_Path,s_path,sig,signature,signaturepath,sigimagepath;

    ArrayList<String> bbbb = new ArrayList<>();
    ArrayList<String> cccc = new ArrayList<>();
    Context context = this;
    String imapgePath;
    private CircleBadgedView circleBadgedView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.view_all_images);

        FontsManager.initFormAssets(this, "ROBOTO-LIGHT.TTF");       //initialization
        FontsManager.changeFonts(this);


        txt_unitno = (TextView) findViewById(R.id.unitno);
        txt_rentno = (TextView) findViewById(R.id.aggno);
        txt_date = (TextView) findViewById(R.id.txt_date);
        txt_cluster = (TextView) findViewById(R.id.txt_cluster);
        txt_driversidefront = (TextView) findViewById(R.id.txt_driversidefront);
        txt_passsidefront = (TextView) findViewById(R.id.txt_passsidefront);
        txt_frontview = (TextView) findViewById(R.id.txt_frontview);
        txt_driversiderear = (TextView) findViewById(R.id.txt_driversiderear);
        txt_passsiderear = (TextView) findViewById(R.id.txt_passsiderear);
        txt_rearview = (TextView) findViewById(R.id.txt_rearview);
        txt_cussign=(TextView) findViewById(R.id.txt_cussign);
        btn_back = (Button) findViewById(R.id.btn_back);
        unitno = (TextView) findViewById(R.id.unitno);
        aggno = (TextView) findViewById(R.id.aggno);
        inspectiondate = (TextView) findViewById(R.id.inspectiondate);


        //imageview
        instrument_clusters = (ImageView) findViewById(R.id.instrument_clusters);
        driverside_front = (ImageView) findViewById(R.id.driverside_front);
        passenger_side_front = (ImageView) findViewById(R.id.passenger_side_front);
        front_view = (ImageView) findViewById(R.id.front_view);
        driver_side_front = (ImageView) findViewById(R.id.driver_side_rear);
        passenger_side_rear = (ImageView) findViewById(R.id.passenger_side_rea);
        rear_view = (ImageView) findViewById(R.id.rear_views);
        sign = (ImageView) findViewById(R.id.sign);


        //*******************Login onclicklistener***************
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_back = new Intent(getApplicationContext(), FilterReports.class);
                startActivity(intent_back);
                finish();
            }
        });

        listView = (ListView) findViewById(R.id.listView);

        // get Instance of Database Adapter
        loginDataBaseAdapter = new LoginDataBaseAdapter(this);
        loginDataBaseAdapter = loginDataBaseAdapter.open();

        Intent intent = getIntent();
        if (null != intent) {

            storedid = intent.getStringExtra("user_id#");
            //unitid.setText(storedid);
            stored_unit_no = intent.getStringExtra("unit_no#");
            unitno.setText(stored_unit_no);
            aggrement_no = intent.getStringExtra("agreement_no#");
            aggno.setText(aggrement_no);
            inspection_date = intent.getStringExtra("inspection_date#");
            inspectiondate.setText(inspection_date);
        }


        getimage = "SELECT * FROM IMAGEUPLOAD WHERE USERID='" + storedid + "'";
        String signature_query = "SELECT * FROM SIGNATUREPAD WHERE USERID='" + storedid + "'";

        getFilterData(getimage);

       //Condition
        Log.e("tag","siz"+imagelist.size());
        try {
            for (int image = 0; image < imagelist.size(); image++) {

                String str_instrument_cluster = imagelist.get(image);
                if (image == 0) {
                    Picasso.with(ViewImage.this)
                            .load(new File(aaas.get(image)))
                            .resize(80, 80)
                            .centerCrop()
                            .into(instrument_clusters);
                    Log.e("tag", "REPORT 1" + aaas.get(image));
                }

                if (image == 3) {
                    Picasso.with(ViewImage.this)
                            .load(new File(aaas.get(image)))
                            .resize(80, 80)
                            .centerCrop()
                            .into(driverside_front);
                    Log.e("tag", "REPORT 2" + aaas.get(image));

                }
                if (image == 6) {
                    Picasso.with(ViewImage.this)
                            .load(new File(aaas.get(image)))
                            .resize(80, 80)
                            .centerCrop()
                            .into(passenger_side_front);
                    Log.e("tag", "REPORT 3" + aaas.get(image));

                }
                if (image == 9) {
                    Picasso.with(ViewImage.this)
                            .load(new File(aaas.get(image)))
                            .resize(80, 80)
                            .centerCrop()
                            .into(front_view);
                    Log.e("tag", "REPORT 4" + aaas.get(image));
                }
                if (image == 12) {
                    Picasso.with(ViewImage.this)
                            .load(new File(aaas.get(image)))
                            .resize(80, 80)
                            .centerCrop()
                            .into(driver_side_front);
                    Log.e("tag", "REPORT 5" + aaas.get(image));
                }
                if (image == 15) {
                    Picasso.with(ViewImage.this)
                            .load(new File(aaas.get(image)))
                            .resize(80, 80)
                            .centerCrop()
                            .into(passenger_side_rear);
                    Log.e("tag", "REPORT 6" + aaas.get(image));
                }
                if (image == 18) {
                    Picasso.with(ViewImage.this)
                            .load(new File(aaas.get(image)))
                            .resize(80, 80)
                            .centerCrop()
                            .into(rear_view);
                    Log.e("tag", "REPORT 7" + aaas.get(image));
                }
            }
        } catch (Exception IllegalArgumentException) {

        }












        getSignature(signature_query);
        //set image to imageview using picasso
        Picasso.with(ViewImage.this)
                .load(signature)
                .resize(75,75)
                .into(sign);

        instrument_clusters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                image_id="1";

                operation();

            }
        });

        driverside_front.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                image_id="2";
                operation();

            }
        });

        passenger_side_front.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                image_id="3";
                operation();
            }
        });

        front_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                image_id="4";
                operation();
            }
        });

        driver_side_front.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                image_id="5";
                operation();
            }
        });

        passenger_side_rear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                image_id="6";
                operation();
            }
        });

        rear_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                image_id="7";
                operation();
            }
        });

        sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // operation2();
                zoom();
            }
        });

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(ViewImage.this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("imageID",image_id);
    }

    private void operation2() {
   /* s_path="SELECT SIGNATURE FROM SIGNATUREPAD WHERE USERID=" +storedid;
        Data2(s_path);
*/



    }

    private void zoom() {

        zoom_sign="SELECT SIGNATURE FROM SIGNATUREPAD WHERE USERID=" +storedid;

        String imgs = getFilterData10(zoom_sign);
        LayoutInflater layoutInflater = LayoutInflater.from(ViewImage.this);
        View promptView = layoutInflater.inflate(R.layout.zoom_layout1, null);
        final AlertDialog alertD = new AlertDialog.Builder(ViewImage.this).create();
        Window window = alertD.getWindow();
        alertD.setCancelable(true);
        window.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);


        PhotoView iv_photo;
        iv_photo = (PhotoView) promptView.findViewById(R.id.iv_photo_sign);

        Picasso.with(ViewImage.this)
                .load(imgs)
                .fit()
                .into(iv_photo);


        alertD.setView(promptView);
        alertD.show();
    }

    private String getFilterData10(String zoom_sign) {


        ArrayList<ImageGetterSetter> listvalg = new ArrayList<ImageGetterSetter>();
        Cursor c1 = loginDataBaseAdapter.getFilterData1(zoom_sign);
        Log.e("tag", "<---lv1111********---->" + c1);
        c1.moveToFirst();
        if (c1 != null) {
            if (c1.moveToFirst()) {
                do {
                    ssig = c1.getString(c1.getColumnIndex("SIGNATURE"));
                    Log.e("tag", "rrrrr" + ssig);

                } while (c1.moveToNext());
            }
        }

        return ssig;
    }

    private String Data2(String s_path) {
        ArrayList<ImageGetterSetter> listvalg = new ArrayList<ImageGetterSetter>();

        bbbb.clear();

        Cursor c1 = loginDataBaseAdapter.getSignatureImage(s_path);
        Log.e("tag", "<---lv1111********---->" + c1);
        c1.moveToFirst();
        if (c1 != null) {
            if (c1.moveToFirst()) {
                do {
                    sig = c1.getString(c1.getColumnIndex("SIGNATURE"));
                    // Log.e("tag", "5555" + image);
                    cccc.add(sig);
                    Log.e("tag", "image_paths" + cccc);
                } while (c1.moveToNext());
            }
        }
        return sig;

    }


    private void operation() {
        i_Path="SELECT IMAGE FROM IMAGEUPLOAD WHERE IMAGE_ID="+image_id+ " and USERID="+storedid;
        Data1(i_Path);

        zoomImage1(0);
    }

    private String Data1(String i_path) {
        ArrayList<ImageGetterSetter> listvalg = new ArrayList<ImageGetterSetter>();

        bbbb.clear();

        Cursor c1 = loginDataBaseAdapter.getFilterData1(i_path);
        Log.e("tag", "<---lv1111********---->" + c1);
        c1.moveToFirst();
        if (c1 != null) {
            if (c1.moveToFirst()) {
                do {
                    image = c1.getString(c1.getColumnIndex("IMAGE"));
                    // Log.e("tag", "5555" + image);
                    bbbb.add(image);
                    Log.e("tag", "image_paths" + bbbb);
                } while (c1.moveToNext());
            }
        }
        return image;

    }


    private String getFilterData1(String query) {

        ArrayList<ImageGetterSetter> listvalg = new ArrayList<ImageGetterSetter>();
        Cursor c1 = loginDataBaseAdapter.getFilterData1(query);
        Log.e("tag", "<---lv1111********---->" + c1);
        c1.moveToFirst();
        if (c1 != null) {
            if (c1.moveToFirst()) {
                do {
                    img = c1.getString(c1.getColumnIndex("IMAGE"));
                    Log.e("tag", "1111111" + imagepath);

                } while (c1.moveToNext());
            }
        }

        return img;
    }


    //***********Zoom image
    private void zoomImage(int i) {

        imapgePath="SELECT IMAGE FROM IMAGEUPLOAD WHERE IMAGE_ID="+image_id+ " and USERID="+storedid;


        getFilterData1(imapgePath);


        LayoutInflater layoutInflater = LayoutInflater.from(ViewImage.this);
        View promptView = layoutInflater.inflate(R.layout.zoom_layout, null);
        final AlertDialog alertD = new AlertDialog.Builder(ViewImage.this).create();
        Window window = alertD.getWindow();
        alertD.setCancelable(true);
        window.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        PhotoView iv_photo;
        iv_photo = (PhotoView) promptView.findViewById(R.id.iv_photo);


        Picasso.with(context)
                .load(new File(img))
                .fit()
                .into(iv_photo);

        attacher = new PhotoViewAttacher(iv_photo);

        attacher.update();

        alertD.setView(promptView);
        alertD.show();
    }



    private void zoomImage1(int i) {

        imapgePath="SELECT IMAGE FROM IMAGEUPLOAD WHERE IMAGE_ID="+image_id+ " and USERID="+storedid;
        getFilterData1(imapgePath);


        LayoutInflater layoutInflater = LayoutInflater.from(ViewImage.this);
        View promptView = layoutInflater.inflate(R.layout.zoom_layout, null);
        final AlertDialog alertD = new AlertDialog.Builder(ViewImage.this).create();
        Window window = alertD.getWindow();
        alertD.setCancelable(true);
        window.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);


        // PhotoView iv_photo;
        //  iv_photo = (PhotoView) promptView.findViewById(R.id.iv_photo);

        ViewPager mViewPager;

        CustomPagerAdapter mCustomPagerAdapter = new CustomPagerAdapter(this,bbbb);
        mViewPager = (ViewPager) promptView.findViewById(R.id.pager);
        mViewPager.setAdapter(mCustomPagerAdapter);
        mCustomPagerAdapter.notifyDataSetChanged();
        alertD.setView(promptView);
        alertD.show();
    }


    private void getFilterData(String query) {

        ArrayList<ImageGetterSetter> listvalg = new ArrayList<ImageGetterSetter>();
        Cursor c1 = loginDataBaseAdapter.getFilterData1(query);
        Log.e("tag", "<---lv1111********---->" + c1);

        aaas.clear();

        c1.moveToFirst();
        if (c1 != null) {
            if (c1.moveToFirst()) {
                do {
                    String imagepath = c1.getString(c1.getColumnIndex("IMAGE"));
                    String imageid = c1.getString(c1.getColumnIndex("IMAGE_ID"));
                    Log.e("tag", "gg_imagepath_val" + imagepath);
                    Log.e("tag", "gg_imageid_val" + imageid);
                    aaas.add(imagepath);
                    imagelist.add(imageid);
                    datas.put(imageid, imagepath);

                } while (c1.moveToNext());
            }
        }

        for (int img = 0; img < aaas.size(); img++) {
            Log.d("tag", aaas.size() + "  " + aaas.get(img));
        }
    }



    //***************get signature method
    private void getSignature(String sig_query) {
        ArrayList<SignatureGetterSetter> listvalg = new ArrayList<SignatureGetterSetter>();
        Cursor c1 = loginDataBaseAdapter.getSignatureImage(sig_query);
        Log.e("tag", "<---lv1111********---->" + c1);
        if (c1 != null) {
            if (c1.moveToFirst()) {
                do {
                    SignatureGetterSetter jvs = new SignatureGetterSetter();
                    imageid = c1.getString(c1.getColumnIndex("USERID"));
                    signature = c1.getString(c1.getColumnIndex("SIGNATURE"));

                    Log.e("tag", "gg_image_signature" + imageid);
                    Log.e("tag", "gg_path_signature***" + imagepath);
                }
                while (c1.moveToNext());
            }
        }
    }


    @Override
    public void onBackPressed() {
        Intent i = new Intent(ViewImage.this, FilterReports.class);
        startActivity(i);
        finish();
    }
}
