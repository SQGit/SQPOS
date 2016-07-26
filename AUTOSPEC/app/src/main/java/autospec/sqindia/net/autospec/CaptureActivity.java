package autospec.sqindia.net.autospec;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.sloop.fonts.FontsManager;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import me.iwf.photopicker.PhotoPickerActivity;
import me.iwf.photopicker.utils.PhotoPickerIntent;

public class CaptureActivity extends Activity implements View.OnClickListener {
    ArrayList<String> selectedPhotos = new ArrayList<>();
    private static final int REQUEST_CODE = 1;
    private static final String TAG = "TedPicker";
    Button btn_back;
    TextView camera1, camera2, camera3, camera4, camera5, camera6, camera7;
    ImageView btn_instrument_cluster, btn_driver_side_front, btn_passenger_side_front, btn_front_view, btn_driver_side_rear, btn_passenger_side_rear, btn_rear_view;
    String autoparts_id;
    File data, sd;
    String storedid;
    LoginDataBaseAdapter loginDataBaseAdapter;
    String finished_value;
    String image_path;
    int imageval;
    String autoimgval;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    int index;
    String inc_index,xxx,storedCount;
    public int img_size;
    String s1,s2,s3,s4,s5,s6,s7;
    int img_count,cont;
    TextView textView_ic,textView_dsf,textView_psf,textView_frontv,textView_dsr,textView_psr,textView_rv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.capture_activity);

        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ using font mananger for whole class @@@@@@@@@@@@@@@@@@@
        FontsManager.initFormAssets(this, "ROBOTO-LIGHT.TTF");
        FontsManager.changeFonts(this);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(CaptureActivity.this);
        editor = sharedPreferences.edit();

        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ findviewbyid @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        btn_back = (Button) findViewById(R.id.btn_back);
        btn_instrument_cluster = (ImageView) findViewById(R.id.btn_instrument_cluster);
        btn_driver_side_front = (ImageView) findViewById(R.id.btn_driver_side_front);
        btn_passenger_side_front = (ImageView) findViewById(R.id.btn_passenger_side_front);
        btn_front_view = (ImageView) findViewById(R.id.btn_front_view);
        btn_driver_side_rear = (ImageView) findViewById(R.id.btn_driver_side_rear);
        btn_passenger_side_rear = (ImageView) findViewById(R.id.btn_passenger_side_rear);
        btn_rear_view = (ImageView) findViewById(R.id.btn_rear_view);
        camera1 = (TextView) findViewById(R.id.camera1);
        camera2 = (TextView) findViewById(R.id.camera2);
        camera3 = (TextView) findViewById(R.id.camera3);
        camera4 = (TextView) findViewById(R.id.camera4);
        camera5 = (TextView) findViewById(R.id.camera5);
        camera6 = (TextView) findViewById(R.id.camera6);
        camera7 = (TextView) findViewById(R.id.camera7);

        textView_ic= (TextView) findViewById(R.id.textView_ic);
        textView_dsf= (TextView) findViewById(R.id.textView_dsf);
        textView_psf= (TextView) findViewById(R.id.textView_psf);
        textView_frontv= (TextView) findViewById(R.id.textView_frontv);
        textView_dsr= (TextView) findViewById(R.id.textView_dsr);
        textView_dsf= (TextView) findViewById(R.id.textView_dsf);
        textView_psr= (TextView) findViewById(R.id.textView_psr);
        textView_rv= (TextView) findViewById(R.id.textView_rv);




        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@  onclicklistener @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        btn_instrument_cluster.setOnClickListener(this);
        btn_driver_side_front.setOnClickListener(this);
        btn_passenger_side_front.setOnClickListener(this);
        btn_front_view.setOnClickListener(this);
        btn_driver_side_rear.setOnClickListener(this);
        btn_passenger_side_rear.setOnClickListener(this);
        btn_rear_view.setOnClickListener(this);


        //@@@@@@@@@@@@@@@@@@@@@@@ create a instance of SQLite Database
        loginDataBaseAdapter = new LoginDataBaseAdapter(this);
        loginDataBaseAdapter = loginDataBaseAdapter.open();

        //@@@@@@@@@@@@@@@@@@@@@@ set & get value using shared
        editor.putString("finished", finished_value);
        editor.putString("activation","success");
        storedid = sharedPreferences.getString("id", "");
        Log.e("tag", "  11 " + storedid);
        editor.commit();


        //@@@@@@@@@@@@@@@@@@@@@@@@ back onclicklistener @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                s1=camera1.getText().toString();
                s2=camera2.getText().toString();
                s3=camera3.getText().toString();
                s4=camera4.getText().toString();
                s5=camera5.getText().toString();
                s6=camera6.getText().toString();
                s7=camera7.getText().toString();


                if (s1.equals("3") && s2.equals("3")&& s3.equals("3") && s4.equals("3")
                        && s5.equals("3") && s6.equals("3") && s7.equals("3")) {
                    Intent intent_back = new Intent(getApplicationContext(), Display_Inspection_details.class);
                    startActivity(intent_back);
                    finish();
                }
                else {
                    Toast.makeText(getApplicationContext(), "Please Take Pending Images", Toast.LENGTH_LONG).show();
                }
            }
        });

        int gd = sharedPreferences.getInt("imgv", 0);
        imageval = imageval + gd;
        Log.e("tag", "  22 " + imageval);
    }


    //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ camera activity @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
    private void getImages() {

        //############ button1
        if (autoparts_id.equals("1")) {

            cont = Integer.valueOf(camera1.getText().toString());
            if(cont ==0) {
                img_count =3;
                photo();
            }
            else if(cont ==1){
                img_count =2;
                photo();
            }
            else if (cont ==2){
                img_count =1;
                photo();
            }
            else{
                //btn_instrument_cluster.setImageResource(R.drawable.check);
            }
            Log.e("tag",""+cont +img_count);
        }


        //########## button 2
        else if (autoparts_id.equals("2")) {
            cont = Integer.valueOf(camera2.getText().toString());
            if(cont ==0) {
                img_count =3;
                photo();
            }
            else if(cont ==1){
                img_count =2;
                photo();
            }
            else if (cont ==2){
                img_count =1;
                photo();
            }
            else{
                btn_driver_side_front.setImageResource(R.drawable.check);
            }

            Log.e("tag",""+cont +img_count);
        }

        //############ button 3
        else if (autoparts_id.equals("3")) {
            cont = Integer.valueOf(camera3.getText().toString());
            if(cont ==0) {
                img_count =3;
                photo();
            }
            else if(cont ==1){
                img_count =2;
                photo();
            }
            else if (cont ==2){
                img_count =1;
                photo();
            }
            else{
                btn_passenger_side_front.setImageResource(R.drawable.check);
            }
            Log.e("tag",""+cont +img_count);
        }

        //########### button4
        else if (autoparts_id.equals("4")) {
            cont = Integer.valueOf(camera4.getText().toString());
            if(cont ==0) {
                img_count =3;
                photo();
            }
            else if(cont ==1){
                img_count =2;
                photo();
            }
            else if (cont ==2){
                img_count =1;
                photo();
            }
            else{
                btn_front_view.setImageResource(R.drawable.check);
            }
            Log.e("tag",""+cont +img_count);
        }

        //############ button5
        else if (autoparts_id.equals("5")) {
            cont = Integer.valueOf(camera5.getText().toString());
            if(cont ==0) {
                img_count =3;
                photo();
            }
            else if(cont ==1){
                img_count =2;
                photo();
            }
            else if (cont ==2){
                img_count =1;
                photo();
            }
            else{
                btn_driver_side_rear.setImageResource(R.drawable.check);
            }
            Log.e("tag",""+cont +img_count);
        }

        //############# button 6
        else if (autoparts_id.equals("6")) {
            cont = Integer.valueOf(camera6.getText().toString());
            if(cont ==0) {
                img_count =3;
                photo();
            }
            else if(cont ==1){
                img_count =2;
                photo();
            }
            else if (cont ==2){
                img_count =1;
                photo();
            }
            else{
                btn_passenger_side_rear.setImageResource(R.drawable.check);
            }
            Log.e("tag",""+cont +img_count);
        }

        //############ button 7
        else if (autoparts_id.equals("7")) {
            cont = Integer.valueOf(camera7.getText().toString());
            if(cont ==0) {
                img_count =3;
                photo();
            }
            else if(cont ==1){
                img_count =2;
                photo();
            }
            else if (cont ==2){
                img_count =1;
                photo();
            }
            else{
                btn_rear_view.setImageResource(R.drawable.check);
            }
            Log.e("tag",""+cont +img_count);
        }


        else {
            Toast.makeText(getApplicationContext(), "Click any Capture Image Button", Toast.LENGTH_LONG).show();
        }
    }


    //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ camera get image path @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        List<String> photos = null;
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            if (data != null) {
                photos = data.getStringArrayListExtra(PhotoPickerActivity.KEY_SELECTED_PHOTOS);
            }
            selectedPhotos.clear();
            if (photos != null) {
                selectedPhotos.addAll(photos);
            }

            for (int position = 0; position < selectedPhotos.size(); position++) {

                String sa = selectedPhotos.get(position);
                Log.e("tag", "size_value" + sa);
                index = position;


                image_path = selectedPhotos.get(position);

                inc_index = String.valueOf(selectedPhotos.size()+ cont);

                int path_id = position+cont;

                Log.e("tag", "counts" + position+"\n"+cont+"\n"+img_count+"\n"+path_id);
                loginDataBaseAdapter.insertImage(storedid, autoparts_id, String.valueOf(path_id), image_path, inc_index);
                method();
            }


            imageval = imageval + 1;
            autoimgval = String.valueOf(imageval);
            Log.e("tag", "autoimageval" + autoimgval);
            SharedPreferences shared = PreferenceManager.getDefaultSharedPreferences(CaptureActivity.this);
            SharedPreferences.Editor editor = shared.edit();
            editor.putString("auto", autoimgval);
            editor.putInt("imgv", imageval);
            editor.commit();
        }
    }

    public void photo(){
        PhotoPickerIntent intent = new PhotoPickerIntent(CaptureActivity.this);
        intent.setPhotoCount(img_count);
        intent.setColumn(4);
        intent.setShowCamera(true);
        startActivityForResult(intent, REQUEST_CODE);
    }

    private void method() {
        //camera 1 activity
        if (autoparts_id.equals("1")) {
            camera1.setText(String.valueOf(inc_index));
            Spannable wordtoSpan = new SpannableString("Instrument Cluster"+"("+String.valueOf(inc_index+")"));
            wordtoSpan.setSpan(new ForegroundColorSpan(Color.RED), 18, 21, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            textView_ic.setText(wordtoSpan);
        }

        //camera 2 activity
        if (autoparts_id.equals("2")) {
            camera2.setText(String.valueOf(inc_index));
            Spannable wordtoSpan = new SpannableString("Driver Side Front"+"("+String.valueOf(inc_index+")"));
            wordtoSpan.setSpan(new ForegroundColorSpan(Color.RED), 17, 20, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            textView_dsf.setText(wordtoSpan);
        }

        //camera 2 activity
        if (autoparts_id.equals("3")) {
            camera3.setText(String.valueOf(inc_index));
            Spannable wordtoSpan = new SpannableString("Passenger Side Front"+"("+String.valueOf(inc_index+")"));
            wordtoSpan.setSpan(new ForegroundColorSpan(Color.RED), 20, 23, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            textView_psf.setText(wordtoSpan);

        }

        //camera 2 activity
        if (autoparts_id.equals("4")) {
            camera4.setText(String.valueOf(inc_index));
            Spannable wordtoSpan = new SpannableString("Front View"+"("+String.valueOf(inc_index+")"));
            wordtoSpan.setSpan(new ForegroundColorSpan(Color.RED), 10, 13, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            textView_frontv.setText(wordtoSpan);
        }


        if (autoparts_id.equals("5")) {
            camera5.setText(String.valueOf(inc_index));
            Spannable wordtoSpan = new SpannableString("Driver Side Rear"+"("+String.valueOf(inc_index+")"));
            wordtoSpan.setSpan(new ForegroundColorSpan(Color.RED), 16, 19, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            textView_dsr.setText(wordtoSpan);


        }

        if (autoparts_id.equals("6")) {
            camera6.setText(String.valueOf(inc_index));
            Spannable wordtoSpan = new SpannableString("Passenger Side Rear"+"("+String.valueOf(inc_index+")"));
            wordtoSpan.setSpan(new ForegroundColorSpan(Color.RED), 19, 22, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            textView_psr.setText(wordtoSpan);


        }

        if (autoparts_id.equals("7")) {
            camera7.setText(String.valueOf(inc_index));
            Spannable wordtoSpan = new SpannableString("Rear View"+"("+String.valueOf(inc_index+")"));
            wordtoSpan.setSpan(new ForegroundColorSpan(Color.RED), 9, 12, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            textView_rv.setText(wordtoSpan);

        }
    }


    //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ onclick Listener for all Button Click @@@@@@@@@@@@@@@@@@@@@@@@
    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btn_instrument_cluster:
                autoparts_id = "1";
                getImages();
                btn_instrument_cluster.setImageResource(R.drawable.camera_icon);
                break;

            case R.id.btn_driver_side_front:
                autoparts_id = "2";
                getImages();
                btn_driver_side_front.setImageResource(R.drawable.camera_icon);
                break;


            case R.id.btn_passenger_side_front:
                autoparts_id = "3";
                getImages();
                btn_passenger_side_front.setImageResource(R.drawable.camera_icon);
                break;

            case R.id.btn_front_view:
                autoparts_id = "4";
                getImages();
                btn_front_view.setImageResource(R.drawable.camera_icon);
                break;

            case R.id.btn_driver_side_rear:
                autoparts_id = "5";
                Log.e("tag","7_id"+autoparts_id);
                getImages();
                btn_driver_side_rear.setImageResource(R.drawable.camera_icon);
                break;

            case R.id.btn_passenger_side_rear:
                Log.e("tag", "fixed6");
                autoparts_id = "6";
                Log.e("tag","7_id"+autoparts_id);
                getImages();
                btn_passenger_side_rear.setImageResource(R.drawable.camera_icon);
                break;

            case R.id.btn_rear_view:
                Log.e("tag", "fixed7");
                autoparts_id = "7";
                Log.e("tag","7_id"+autoparts_id);
                getImages();
                btn_rear_view.setImageResource(R.drawable.camera_icon);
                break;
        }
    }


    //@@@@@@@@@@@@@@@@@@@@@@@@@@ onbackpressed method @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
    @Override
    public void onBackPressed() {
        s1=camera1.getText().toString();
        s2=camera2.getText().toString();
        s3=camera3.getText().toString();
        s4=camera4.getText().toString();
        s5=camera5.getText().toString();
        s6=camera6.getText().toString();
        s7=camera7.getText().toString();

        if (s1.equals("3") && s2.equals("3")&& s3.equals("3") && s4.equals("3")
                && s5.equals("3") && s6.equals("3") && s7.equals("3")) {
            Intent intent_back = new Intent(getApplicationContext(), Display_Inspection_details.class);
            startActivity(intent_back);
            finish();
        }
        else {
            Toast.makeText(getApplicationContext(), "Please Take Pending Images", Toast.LENGTH_LONG).show();
        }
    }
}

