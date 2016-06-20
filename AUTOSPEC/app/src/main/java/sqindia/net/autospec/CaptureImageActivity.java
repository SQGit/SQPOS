package sqindia.net.autospec;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.gun0912.tedpicker.Config;
import com.gun0912.tedpicker.ImagePickerActivity;
import com.sloop.fonts.FontsManager;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by Admin on 18-05-2016.
 */
public class CaptureImageActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int INTENT_REQUEST_GET_IMAGES = 13;
    private static final String TAG = "TedPicker";
    ArrayList<Uri> image_uris = new ArrayList<Uri>();
    private ViewGroup mSelectedImagesContainer;
    Button btn_back;
    ImageButton btn_instrument_cluster, btn_driver_side_front, btn_passenger_side_front, btn_front_view, btn_driver_side_rear, btn_passenger_side_rear, btn_rear_view;
    public static final String MyPREFERENCES = "MyPrefs";
    SharedPreferences sharedpreferences;
    private ProgressDialog dialog;
    String token, str, autoparts_id, inspectionId;

    String str_instrument_cluster, str_driver_side_front, str_passenger_side_front, str_front_view, str_driver_side_rear, str_passenger_side_rear, str_rear_view;
    String[] imagearray;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.capture_activity);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(CaptureImageActivity.this);
        token = sharedPreferences.getString("token", "");
        inspectionId = sharedPreferences.getString("inspectionId", "");
        Log.d("tag", "inspectionId" + inspectionId);
        FontsManager.initFormAssets(this, "_SENINE.TTF");       //initialization
        FontsManager.changeFonts(this);
        //****************findviewbyid***********************

        btn_back = (Button) findViewById(R.id.btn_back);
        btn_instrument_cluster = (ImageButton) findViewById(R.id.btn_instrument_cluster);
        btn_driver_side_front = (ImageButton) findViewById(R.id.btn_driver_side_front);
        btn_passenger_side_front = (ImageButton) findViewById(R.id.btn_passenger_side_front);
        btn_front_view = (ImageButton) findViewById(R.id.btn_front_view);
        btn_driver_side_rear = (ImageButton) findViewById(R.id.btn_driver_side_rear);
        btn_passenger_side_rear = (ImageButton) findViewById(R.id.btn_passenger_side_rear);
        btn_rear_view = (ImageButton) findViewById(R.id.btn_rear_view);
        btn_instrument_cluster.setOnClickListener(this);
        btn_driver_side_front.setOnClickListener(this);
        btn_passenger_side_front.setOnClickListener(this);
        btn_front_view.setOnClickListener(this);
        btn_driver_side_rear.setOnClickListener(this);
        btn_passenger_side_rear.setOnClickListener(this);
        btn_rear_view.setOnClickListener(this);


        //*******************Login onclicklistener***************
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_back = new Intent(getApplicationContext(), Display_Inspection_details.class);
                startActivity(intent_back);
            }
        });

        mSelectedImagesContainer = (ViewGroup) findViewById(R.id.selected_photos_container);


        onlastknown();

    }

    private void onlastknown() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(CaptureImageActivity.this);
        str_instrument_cluster = sharedPreferences.getString("instrument_cluster", "");
        str_driver_side_front = sharedPreferences.getString("driver_side_front", "");
        str_passenger_side_front = sharedPreferences.getString("passenger_side_front", "");
        str_front_view = sharedPreferences.getString("front_view", "");
        str_driver_side_rear = sharedPreferences.getString("driver_side_rear", "");
        str_passenger_side_rear = sharedPreferences.getString("passenger_side_rear", "");
        str_rear_view = sharedPreferences.getString("rear_view", "");

        if (str_instrument_cluster.equals("1")) {
            btn_instrument_cluster.setImageResource(R.drawable.check);
        }
        if (str_driver_side_front.equals("2")) {
            btn_driver_side_front.setImageResource(R.drawable.check);
        }
        if (str_passenger_side_front.equals("3")) {
            btn_passenger_side_front.setImageResource(R.drawable.check);
        }
        if (str_front_view.equals("4")) {
            btn_front_view.setImageResource(R.drawable.check);
        }
        if (str_driver_side_rear.equals("5")) {
            btn_driver_side_rear.setImageResource(R.drawable.check);
        }
        if (str_passenger_side_rear.equals("6")) {
            btn_passenger_side_rear.setImageResource(R.drawable.check);
        }
        if (str_rear_view.equals("7")) {
            btn_rear_view.setImageResource(R.drawable.check);
        }
    }



    private void getImages(Config config) {
        ImagePickerActivity.setConfig(config);

        Intent intent = new Intent(this, ImagePickerActivity.class);

        if (image_uris != null) {

            intent.putParcelableArrayListExtra(ImagePickerActivity.EXTRA_IMAGE_URIS, image_uris);
        }
        startActivityForResult(intent, INTENT_REQUEST_GET_IMAGES);
    }


    @Override
    protected void onActivityResult(int requestCode, int resuleCode, Intent intent) {
        super.onActivityResult(requestCode, resuleCode, intent);


        if (resuleCode == Activity.RESULT_OK) {
            if (requestCode == INTENT_REQUEST_GET_IMAGES) {
                image_uris = intent.getParcelableArrayListExtra(com.gun0912.tedpicker.ImagePickerActivity.EXTRA_IMAGE_URIS);
                if (image_uris != null) {
                    Log.d("tag", "choosed file" + image_uris);
                    StringBuilder builder = new StringBuilder();
                    for (Uri value : image_uris) {
                        builder.append(value + "#####");

                    }
                    String text = builder.toString();
                    Log.i(TAG, " urihole: " + text);
                    sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
                    SharedPreferences.Editor edit = sharedpreferences.edit();
                    edit.remove("selected");
                    edit.putString("selected", text);
                    edit.commit();
                    new UploadFileToServer().execute();
                    str = sharedpreferences.getString("selected", "");
                    imagearray = str.split("\\#\\#\\#\\#\\#");
                }
            }
        }
    }

    private void showMedia() {

        mSelectedImagesContainer.removeAllViews();
        if (image_uris.size() >= 1) {
            mSelectedImagesContainer.setVisibility(View.VISIBLE);
        }

        int wdpx = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100, getResources().getDisplayMetrics());
        int htpx = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100, getResources().getDisplayMetrics());


        for (Uri uri : image_uris) {

            View imageHolder = LayoutInflater.from(this).inflate(R.layout.image_item, null);
            ImageView thumbnail = (ImageView) imageHolder.findViewById(R.id.media_image);

            Glide.with(this)
                    .load(uri.toString())
                    .fitCenter()
                    .into(thumbnail);

            mSelectedImagesContainer.addView(imageHolder);

            thumbnail.setLayoutParams(new FrameLayout.LayoutParams(wdpx, htpx));
        }


    }

    @Override
    public void onClick(View v) {
        Config config = new Config();
        switch (v.getId()) {

            case R.id.btn_instrument_cluster:
                autoparts_id = "1";
                config.setToolbarTitleRes(R.string.custom_title);
                config.setSelectionMin(3);
                config.setSelectionLimit(3);
                getImages(config);
                break;
            case R.id.btn_driver_side_front:
                autoparts_id = "2";
                config.setToolbarTitleRes(R.string.custom_title);
                config.setSelectionMin(3);
                config.setSelectionLimit(3);
                getImages(config);
                break;
            case R.id.btn_passenger_side_front:
                autoparts_id = "3";
                config.setToolbarTitleRes(R.string.custom_title);
                config.setSelectionMin(3);
                config.setSelectionLimit(3);
                getImages(config);
                break;
            case R.id.btn_front_view:
                autoparts_id = "4";
                config.setToolbarTitleRes(R.string.custom_title);
                config.setSelectionMin(3);
                config.setSelectionLimit(3);
                getImages(config);
                break;
            case R.id.btn_driver_side_rear:
                autoparts_id = "5";

                config.setToolbarTitleRes(R.string.custom_title);
                config.setSelectionMin(3);
                config.setSelectionLimit(3);

                getImages(config);
                break;
            case R.id.btn_passenger_side_rear:
                autoparts_id = "6";

                config.setToolbarTitleRes(R.string.custom_title);
                config.setSelectionMin(3);
                config.setSelectionLimit(3);
                getImages(config);
                break;
            case R.id.btn_rear_view:
                autoparts_id = "7";

                config.setToolbarTitleRes(R.string.custom_title);
                config.setSelectionMin(3);
                config.setSelectionLimit(3);

                getImages(config);
                break;
        }

    }


    private class UploadFileToServer extends AsyncTask<Void, Integer, String> {

        public UploadFileToServer() {


        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(CaptureImageActivity.this);
            dialog.setMessage("Uploading...");
            dialog.setCancelable(false);
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {

        }

        @Override
        protected String doInBackground(Void... params) {

            return uploadFile();
        }

        @SuppressWarnings("deprecation")
        private String uploadFile() {
            String responseString = null;
            //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

            try {

                HttpClient client = new DefaultHttpClient();
                HttpPost postMethod = new HttpPost("http://sqindia01.cloudapp.net/autospec/api/v1/inspection/imageUpload");
                postMethod.addHeader("sessionToken ", token);
                postMethod.addHeader("inspectionId ", inspectionId);
                postMethod.addHeader("autopartId ", autoparts_id);
                MultipartEntity entity = new MultipartEntity();
                int i = 1;
                for (String zimg : imagearray) {
                    File file = new File(String.valueOf(zimg));
                    FileBody contentFile = new FileBody(file);
                    entity.addPart("fileUpload" + (i++), contentFile);
                }

                postMethod.setEntity(entity);
                HttpResponse response = client.execute(postMethod);
                HttpEntity r_entity = response.getEntity();
                int statusCode = response.getStatusLine().getStatusCode();
                if (statusCode == 200) {
                    responseString = EntityUtils.toString(r_entity);

                    JSONObject result1 = new JSONObject(responseString);
                    String status = result1.getString("status");

                    if (status.equals("success")) {

                    }
                } else {
                    responseString = "Error occurred! Http Status Code: " + statusCode;
                }

            } catch (Exception e) {
                responseString = e.toString();
            }
            return responseString;

        }

        @Override
        protected void onPostExecute(String responseString) {
            Log.e("TAG", "Response from server: " + responseString);
            super.onPostExecute(responseString);
            dialog.dismiss();
            if (responseString == "") {

                new SweetAlertDialog(CaptureImageActivity.this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Oops...")
                        .setContentText("Internet Connectivity very slow? try again")
                        .show();
            } else {

                try {
                    JSONObject result1 = new JSONObject(responseString);
                    String status = result1.getString("status");
                    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    SharedPreferences.Editor editor = sharedPreferences.edit();

                    Log.d("tag", "new Image_url " + status);
                    if (status.equals("SUCCESS")) {

                        switch (autoparts_id) {

                            case "1":

                                btn_instrument_cluster.setImageResource(R.drawable.check);
                                editor.putString("instrument_cluster", "1");
                                editor.apply();
                                break;
                            case "2":

                                btn_driver_side_front.setImageResource(R.drawable.check);
                                editor.putString("driver_side_front", "2");
                                editor.apply();
                                break;
                            case "3":

                                btn_passenger_side_front.setImageResource(R.drawable.check);
                                editor.putString("passenger_side_front", "3");
                                editor.apply();
                                break;
                            case "4":

                                btn_front_view.setImageResource(R.drawable.check);
                                editor.putString("front_view", "4");
                                editor.apply();
                                break;
                            case "5":

                                btn_driver_side_rear.setImageResource(R.drawable.check);
                                editor.putString("driver_side_rear", "5");
                                editor.apply();
                                break;
                            case "6":

                                btn_passenger_side_rear.setImageResource(R.drawable.check);
                                editor.putString("passenger_side_rear", "6");
                                editor.apply();
                                break;
                            case "7":

                                btn_rear_view.setImageResource(R.drawable.check);
                                editor.putString("rear_view", "7");
                                editor.apply();
                                break;

                        }
                       /* Intent i = new Intent(CaptureImageActivity.this, MainActivity.class);
                        startActivity(i);*/

                    } else {
                        Toast.makeText(getApplicationContext(), "Profile Image Uploaded but Something went wrong.. ", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        }

    }


}