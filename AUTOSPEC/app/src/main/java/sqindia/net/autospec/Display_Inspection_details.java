package sqindia.net.autospec;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.github.gcacace.signaturepad.views.SignaturePad;
import com.sloop.fonts.FontsManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by RSA on 31-05-2016.
 */
public class Display_Inspection_details extends AppCompatActivity {

    EditText editText_unitno, editText_aggrementno, editText_cus_sign;
    private SignaturePad mSignaturePad;
    private TextView mClearButton, textView_finished, mSaveButton;
    Button button_captureimage, button_submit, btn_back;
    String str_unit_no, str_agreement_no, str_user_id, str_inspection_date, token;
    String str_instrument_cluster, str_driver_side_front, str_passenger_side_front, str_front_view, str_driver_side_rear, str_passenger_side_rear, str_rear_view;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inspectionid_view);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(Display_Inspection_details.this);
        token = sharedPreferences.getString("token", "");
        str_user_id = sharedPreferences.getString("str_user_id", "");
        str_unit_no = sharedPreferences.getString("str_unit_no", "");
        str_agreement_no = sharedPreferences.getString("str_agreement_no", "");
        FontsManager.initFormAssets(this, "_SENINE.TTF");       //initialization
        FontsManager.changeFonts(this);

        editText_unitno = (EditText) findViewById(R.id.editText_unitno);
        editText_aggrementno = (EditText) findViewById(R.id.editText_aggrementno);
        textView_finished = (TextView) findViewById(R.id.textView_finished);
        button_captureimage = (Button) findViewById(R.id.textView_captureimage);
        button_submit = (Button) findViewById(R.id.button_submit);
        btn_back = (Button) findViewById(R.id.btn_back);
        mSignaturePad = (SignaturePad) findViewById(R.id.signature_pad);
        mClearButton = (TextView) findViewById(R.id.clear_button);
        mSaveButton = (TextView) findViewById(R.id.save_button);
        editText_aggrementno.setText(str_agreement_no);
        editText_unitno.setText(str_unit_no);
        editText_aggrementno.setEnabled(false);
        editText_unitno.setEnabled(false);


        str_instrument_cluster = sharedPreferences.getString("instrument_cluster", "");
        str_driver_side_front = sharedPreferences.getString("driver_side_front", "");
        str_passenger_side_front = sharedPreferences.getString("passenger_side_front", "");
        str_front_view = sharedPreferences.getString("front_view", "");
        str_driver_side_rear = sharedPreferences.getString("driver_side_rear", "");
        str_passenger_side_rear = sharedPreferences.getString("passenger_side_rear", "");
        str_rear_view = sharedPreferences.getString("rear_view", "");

        if ((!str_instrument_cluster.equals("")) && (!str_driver_side_front.equals("")) && (!str_passenger_side_front.equals("")) && (!str_front_view.equals("")) && (!str_driver_side_rear.equals("")) && (!str_passenger_side_front.equals("")) && (!str_rear_view.equals(""))) {
            textView_finished.setEnabled(false);
            textView_finished.setTextColor(getResources().getColor(R.color.error_color));
        } else {

            textView_finished.setEnabled(true);
            textView_finished.setTextColor(getResources().getColor(R.color.green));

        }


        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_back = new Intent(getApplicationContext(), Dashboard.class);
                startActivity(intent_back);
            }
        });
        str_inspection_date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
        Log.e("tag", "inspectiontime" + str_inspection_date);
        mSaveButton.setEnabled(false);
        mSignaturePad.setOnSignedListener(new SignaturePad.OnSignedListener() {
            @Override
            public void onStartSigning() {

            }

            @Override
            public void onSigned() {
                mSaveButton.setEnabled(true);
                mClearButton.setEnabled(true);
            }

            @Override
            public void onClear() {
                mSaveButton.setEnabled(false);
                mClearButton.setEnabled(false);
            }
        });
        mClearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSignaturePad.clear();
            }
        });

        button_captureimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_camera = new Intent(getApplicationContext(), CaptureImageActivity.class);
                startActivity(intent_camera);
            }
        });
        button_submit.setOnClickListener(new View.OnClickListener() {
                                             @Override
                                             public void onClick(View v) {
                                                 if (Util.Operations.isOnline(Display_Inspection_details.this)) {
                                                 } else {
                                                     new SweetAlertDialog(Display_Inspection_details.this, SweetAlertDialog.WARNING_TYPE)
                                                             .setTitleText("ALERT")
                                                             .setContentText("No Internet Connectivity")
                                                             .show();
                                                 }
                                             }
                                         }

        );
        mSaveButton.setOnClickListener(new View.OnClickListener()

                                       {
                                           @Override
                                           public void onClick(View view) {
                                               Bitmap signatureBitmap = mSignaturePad.getSignatureBitmap();
                                               if (addJpgSignatureToGallery(signatureBitmap)) {
                                                   // Toast.makeText(Display_Inspection_details.this, "Signature saved into the Gallery", Toast.LENGTH_LONG).show();
                                               } else {
                                                   //Toast.makeText(Display_Inspection_details.this, "Unable to store the signature", Toast.LENGTH_LONG).show();
                                               }
                                               if (addSvgSignatureToGallery(mSignaturePad.getSignatureSvg())) {
                                                   // Toast.makeText(Display_Inspection_details.this, "SVG Signature saved into the Gallery", Toast.LENGTH_LONG).show();
                                               } else {
                                                   //Toast.makeText(Display_Inspection_details.this, "Unable to store the SVG signature", Toast.LENGTH_LONG).show();
                                               }
                                           }
                                       }

        );

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
        return super.dispatchTouchEvent(ev);
    }

    private boolean addSvgSignatureToGallery(String signatureSvg) {

        boolean result = false;
        try {
            File svgFile = new File(getAlbumStorageDir("SignaturePad"), String.format("Signature_%d.svg", System.currentTimeMillis()));
            OutputStream stream = new FileOutputStream(svgFile);
            OutputStreamWriter writer = new OutputStreamWriter(stream);
            writer.write(signatureSvg);
            writer.close();
            stream.flush();
            stream.close();
            scanMediaFile(svgFile);
            result = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;

    }

    public void saveBitmapToJPG(Bitmap bitmap, File photo) throws IOException {
        Bitmap newBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(newBitmap);
        canvas.drawColor(Color.WHITE);
        canvas.drawBitmap(bitmap, 0, 0, null);
        OutputStream stream = new FileOutputStream(photo);
        newBitmap.compress(Bitmap.CompressFormat.JPEG, 80, stream);
        stream.close();
    }

    private boolean addJpgSignatureToGallery(Bitmap signatureBitmap) {

        boolean result = false;
        try {
            File photo = new File(getAlbumStorageDir("SignaturePad"), String.format("Signature_%d.jpg", System.currentTimeMillis()));
            saveBitmapToJPG(signatureBitmap, photo);
            scanMediaFile(photo);
            Log.d("tag", "" + photo);
            result = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    private File getAlbumStorageDir(String signaturePad) {

        File file = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), signaturePad);
        Log.d("tag", "file" + file);
        if (!file.mkdirs()) {
            Log.e("SignaturePad", "Directory not created");
        }
        return file;
    }

    private void scanMediaFile(File photo) {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri contentUri = Uri.fromFile(photo);
        mediaScanIntent.setData(contentUri);
        Log.d("tag", "contentUri" + contentUri);
        Toast.makeText(Display_Inspection_details.this, "Signature submitted sucessfully", Toast.LENGTH_LONG).show();
        Display_Inspection_details.this.sendBroadcast(mediaScanIntent);
    }
}
