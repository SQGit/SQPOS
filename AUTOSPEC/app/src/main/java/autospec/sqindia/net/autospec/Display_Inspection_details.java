package autospec.sqindia.net.autospec;

    import android.app.Activity;
    import android.app.Dialog;
    import android.content.Context;
    import android.content.Intent;
    import android.content.SharedPreferences;
    import android.graphics.Bitmap;
    import android.graphics.Canvas;
    import android.graphics.Color;
    import android.graphics.Typeface;
    import android.net.Uri;
    import android.os.Bundle;
    import android.os.Environment;
    import android.preference.PreferenceManager;
    import android.support.annotation.Nullable;
    import android.util.Log;
    import android.view.MotionEvent;
    import android.view.View;
    import android.view.Window;
    import android.view.WindowManager;
    import android.view.inputmethod.InputMethodManager;
    import android.widget.Button;
    import android.widget.EditText;
    import android.widget.TextView;
    import android.widget.Toast;
    import com.github.gcacace.signaturepad.views.SignaturePad;
    import com.sloop.fonts.FontsManager;

    import java.io.File;
    import java.io.FileInputStream;
    import java.io.FileOutputStream;
    import java.io.IOException;
    import java.io.OutputStream;
    import java.nio.channels.FileChannel;
    import java.text.SimpleDateFormat;
    import java.util.Calendar;

        public class Display_Inspection_details extends Activity {
        LoginDataBaseAdapter loginDataBaseAdapter;
        TextView textView_head;
        EditText editText_unitno, editText_aggrementno;
        private SignaturePad mSignaturePad;
        private TextView mClearButton, textView_finished, mSaveButton;
        Button button_captureimage, button_submit, btn_back;
        String str_unit_no, str_agreement_no, str_inspection_date, id,imageval;
        String str_instrument_cluster, str_driver_side_front, str_passenger_side_front, str_front_view, str_driver_side_rear, str_passenger_side_rear, str_rear_view;
        String imagepath,fin_val,activation;
        File data, sd;
            int gd;


        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.inspectionid_view);

            //*************get value from shared pref**********
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(Display_Inspection_details.this);
            str_unit_no = sharedPreferences.getString("unit_no", "");
            str_agreement_no = sharedPreferences.getString("rental_no", "");
            id=sharedPreferences.getString("id","");

            //***************findview by id*************
            textView_head = (TextView) findViewById(R.id.textView_head);
            editText_unitno = (EditText) findViewById(R.id.editText_unitno);
            editText_aggrementno = (EditText) findViewById(R.id.editText_aggrementno);
            textView_finished = (TextView) findViewById(R.id.textView_finished);
            button_captureimage = (Button) findViewById(R.id.textView_captureimage);
            button_submit = (Button) findViewById(R.id.button_submit);
            btn_back = (Button) findViewById(R.id.btn_back);
            mSignaturePad = (SignaturePad) findViewById(R.id.signature_pad);
            mClearButton = (TextView) findViewById(R.id.clear_button);
            mSaveButton = (TextView) findViewById(R.id.save_button);

            //*****************change font using Typeface**************
            FontsManager.initFormAssets(this, "ROBOTO-LIGHT.TTF");
            FontsManager.changeFonts(this);

            // create a instance of SQLite Database
            loginDataBaseAdapter=new LoginDataBaseAdapter(this);
            loginDataBaseAdapter=loginDataBaseAdapter.open();

            textView_finished.setText("");
            editText_unitno.setText(str_unit_no);
            editText_aggrementno.setText(str_agreement_no);

            activation=sharedPreferences.getString("activation","");
            Log.e("tag"," 123456 "+imageval);
            str_instrument_cluster = sharedPreferences.getString("instrument_cluster", "");
            str_driver_side_front = sharedPreferences.getString("driver_side_front", "");
            str_passenger_side_front = sharedPreferences.getString("passenger_side_front", "");
            str_front_view = sharedPreferences.getString("front_view", "");
            str_driver_side_rear = sharedPreferences.getString("driver_side_rear", "");
            str_passenger_side_rear = sharedPreferences.getString("passenger_side_rear", "");
            str_rear_view = sharedPreferences.getString("rear_view", "");
            fin_val=sharedPreferences.getString("finished","");


            if(activation.equals("success"))
            {

                textView_finished.setText("finished");
            }
            else
            {
                textView_finished.setText("");
            }

            mSaveButton.setVisibility(View.GONE);

            if ((!str_instrument_cluster.equals("")) && (!str_driver_side_front.equals("")) && (!str_passenger_side_front.equals("")) && (!str_front_view.equals("")) && (!str_driver_side_rear.equals("")) && (!str_passenger_side_front.equals("")) && (!str_rear_view.equals(""))) {
                textView_finished.setEnabled(false);
                textView_finished.setTextColor(getResources().getColor(R.color.error_color));
            } else {

                textView_finished.setEnabled(true);
                textView_finished.setTextColor(getResources().getColor(R.color.colorPrimary));

            }

            str_inspection_date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());

            //***************back on click listener*****
            btn_back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!mSignaturePad.isEmpty()) {
                        if (gd >= 7)
                        {
                            Intent intent_back = new Intent(getApplicationContext(), Dashboard.class);
                            startActivity(intent_back);
                            finish();
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(), "You should Capture All the images", Toast.LENGTH_LONG).show();
                        }
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(), "Please Put Signature here", Toast.LENGTH_LONG).show();

                    }

                }
            });

            //***************signature pad active function
            mSaveButton.setEnabled(false);
            mSignaturePad.setOnSignedListener(new SignaturePad.OnSignedListener() {
                @Override
                public void onStartSigning() {}

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

            //***************capture function onclicklistener
            button_captureimage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(activation.equals("success"))
                    {
                        button_captureimage.setEnabled(false);
                        textView_finished.setText("finished");
                    }
                    else
                    {
                        Intent intent_camera = new Intent(getApplicationContext(), CaptureActivity.class);
                        startActivity(intent_camera);
                        finish();
                    }



                }
            });

            //***************submit function onclicklistener
            button_submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {



                    Bitmap signatureBitmap = mSignaturePad.getSignatureBitmap();
                    if (addJpgSignatureToGallery(signatureBitmap))
                    {
                    } else
                    {
                    }

                    SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(Display_Inspection_details.this);
                    SharedPreferences.Editor editor = sh.edit();

                    imageval=sh.getString("auto","");
                    Log.e("tag"," ##### "+imageval);


                    gd = sh.getInt("imgv",0);
                    Log.e("tag","nnn"+gd);


                 if(!mSignaturePad.isEmpty()) {
                     if (gd >= 7)
                     {
                         loginDataBaseAdapter.inserSignature(id, imagepath);
                         editor.remove("activation");
                         editor.commit();
                         Log.e("tag", "sigid" + id);
                         exportDB();

                         editor.remove("id1");
                         editor.remove("id2");
                         editor.remove("id3");
                         editor.remove("id4");
                         editor.remove("id5");
                         editor.remove("id6");
                         editor.remove("id7");
                         editor.remove("imgv");
                         editor.commit();

                         Intent j = new Intent(getApplicationContext(), Dashboard.class);
                         startActivity(j);
                         finish();
                     }
                     else
                     {
                         message2();
                         //Toast.makeText(getApplicationContext(), "You should Capture All the images", Toast.LENGTH_LONG).show();
                     }
                 }
                    else
                 {
                     message1();
                     //Toast.makeText(getApplicationContext(), "Please Put Signature here", Toast.LENGTH_LONG).show();

                 }

                }
            });

    }

            private void message2() {

                final Dialog dialog = new Dialog(Display_Inspection_details.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.custom_dialog);
                //adding text dynamically
                TextView txt_head2 = (TextView) dialog.findViewById(R.id.txt_head2);
                TextView txt_msg = (TextView) dialog.findViewById(R.id.txt_msg);
                txt_head2.setText("Alert Message");
                txt_msg.setText("Please Capture All the Images....");
                Button btn_ok2 = (Button) dialog.findViewById(R.id.btn_ok2);

                Typeface tt = Typeface.createFromAsset(getApplicationContext().getAssets(), "ROBOTO-LIGHT.TTF");
                txt_head2.setTypeface(tt);
                txt_msg.setTypeface(tt);


                btn_ok2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();

                    }
                });
                dialog.show();
            }

            private void message1()
            {
                final Dialog dialog = new Dialog(Display_Inspection_details.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.custom_dialog);
                //adding text dynamically
                TextView txt_head2 = (TextView) dialog.findViewById(R.id.txt_head2);
                TextView txt_msg = (TextView) dialog.findViewById(R.id.txt_msg);
                txt_head2.setText("Alert Message");
                txt_msg.setText("Please Put Signature here...");
                Button btn_ok2 = (Button) dialog.findViewById(R.id.btn_ok2);

                Typeface tt = Typeface.createFromAsset(getApplicationContext().getAssets(), "ROBOTO-LIGHT.TTF");
                txt_head2.setTypeface(tt);
                txt_msg.setTypeface(tt);


                btn_ok2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();

                    }
                });
                dialog.show();
            }

            //**************export db
    private void exportDB() {

                sd = new File(Environment.getExternalStorageDirectory() + "/exported/");
                data = Environment.getDataDirectory();
                if (!sd.exists()) {
                    sd.mkdirs();
                }

                FileChannel source = null;
                FileChannel destination = null;
                String currentDBPath = "/data/" + "autospec.sqindia.net.autospec" + "/databases/" + "login.db";
                String backupDBPath = "ex.db";
                File currentDB = new File(data, currentDBPath);
                File backupDB = new File(sd, backupDBPath);

                try {
                    source = new FileInputStream(currentDB).getChannel();
                    destination = new FileOutputStream(backupDB).getChannel();
                    destination.transferFrom(source, 0, source.size());
                    source.close();
                    destination.close();
                           } catch (IOException e) {
                    e.printStackTrace();
                }
            }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);}
        return super.dispatchTouchEvent(ev);
    }

    //************get bitmap image for signature pad
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
            Log.e("tag", "%%%%" + photo);
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
        imagepath = contentUri.toString();
        Log.e("tag", "contentUri*" + contentUri);
        //Toast.makeText(Display_Inspection_details.this, "Signature submitted sucessfully", Toast.LENGTH_SHORT).show();
        Display_Inspection_details.this.sendBroadcast(mediaScanIntent);
    }


    @Override
    public void onBackPressed() {

        if(!mSignaturePad.isEmpty()) {
            if (gd >= 7)
            {
                Intent intent_back = new Intent(getApplicationContext(), Dashboard.class);
                startActivity(intent_back);
                finish();
            }
            else
            {
                Toast.makeText(getApplicationContext(), "You should Capture All the images", Toast.LENGTH_LONG).show();
            }
        }
        else
        {
            Toast.makeText(getApplicationContext(), "Please Put Signature here", Toast.LENGTH_LONG).show();

        }

    }


         /*   @Override
            protected void onStop()
            {
                super.onStop();
                finish();
            }*/
}
