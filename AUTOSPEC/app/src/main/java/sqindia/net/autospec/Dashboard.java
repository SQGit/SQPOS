package sqindia.net.autospec;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.hardware.Camera;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Admin on 17-05-2016.
 */
public class Dashboard extends AppCompatActivity {

    private Toolbar toolbar;
    private NavigationView navigationView, nv;
    private DrawerLayout drawerLayout,drawerLayout1;
    ActionBarDrawerToggle actionBarDrawerToggle;

    ImageButton btnSwitch;

    private Camera camera;
    private boolean isFlashOn;
    private boolean hasFlash;
    Camera.Parameters params;
    MediaPlayer mp;

    ImageView imageView_new,imageView_modify,imageView_vehicle;
    TextView textView_newins,textView_modifyins,textView_report,textView_head,textView_username;
    TextView nav_settings,nav_flashlight,nav_profile,nav_logout,nav_location;
    Button navigation_left,navigation_right;

    String name,get_name,get_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.dashboard);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(Dashboard.this);
        name = sharedPreferences.getString("name", "");


        //****************findviewbyid**************
        textView_head=(TextView)findViewById(R.id.textView_head);
        textView_newins=(TextView)findViewById(R.id.textView_newins);
        textView_modifyins=(TextView)findViewById(R.id.textView_modifyins);
        textView_report=(TextView)findViewById(R.id.textView_report);
        textView_username=(TextView)findViewById(R.id.textView_username);
        nav_settings=(TextView)findViewById(R.id.nav_settings);
        nav_flashlight=(TextView)findViewById(R.id.nav_flashlight);
        nav_profile=(TextView)findViewById(R.id.nav_profile);
        nav_logout=(TextView)findViewById(R.id.nav_logout);
        nav_location=(TextView)findViewById(R.id.nav_location);
        imageView_new=(ImageView)findViewById(R.id.imageView_new);
        imageView_modify=(ImageView)findViewById(R.id.imageView_modify);
        imageView_vehicle=(ImageView)findViewById(R.id.imageView_vehicle);
        navigation_left=(Button)findViewById(R.id.navigation_left);
        navigation_right=(Button)findViewById(R.id.navigation_right);
        btnSwitch = (ImageButton) findViewById(R.id.btnSwitch);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        nv = (NavigationView) findViewById(R.id.navigation_view1);
        textView_username.setText("Hi "+name+" !");


        setSupportActionBar(toolbar);

        //*****************change font using Typeface**************
        Typeface tf = Typeface.createFromAsset(getApplicationContext().getAssets(), "_SENINE.TTF");
        textView_head.setTypeface(tf);
        textView_newins.setTypeface(tf);
        textView_modifyins.setTypeface(tf);
        textView_report.setTypeface(tf);
        textView_username.setTypeface(tf);
        nav_settings.setTypeface(tf);
        nav_flashlight.setTypeface(tf);
        nav_profile.setTypeface(tf);
        nav_logout.setTypeface(tf);
        nav_location.setTypeface(tf);






        //*************new Inspection onclicklistener************
        imageView_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_new=new Intent(getApplicationContext(),NewInspectionActivity.class);
                startActivity(intent_new);
            }
        });



        imageView_modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_modify=new Intent(getApplicationContext(),ModifyInspectionActivity.class);
                startActivity(intent_modify);
            }
        });

        nav_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCustomDialog();
            }
        });

        nav_flashlight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getApplicationContext(),"Under Process",Toast.LENGTH_SHORT).show();
                /*Intent i=new Intent(getApplicationContext(),SignupActivity.class);
                startActivity(i);*/
                //flashlight_custom_on_off();
            }
        });

        nav_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* Intent i=new Intent(getApplicationContext(),SignupActivity.class);
                startActivity(i);*/
                Toast.makeText(getApplicationContext(),"Under Process",Toast.LENGTH_SHORT).show();
            }
        });

        nav_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profile=new Intent(getApplicationContext(),ProfileActivity.class);
                startActivity(profile);
            }
        });

        //*************new Inspection onclicklistener************
        imageView_vehicle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent i=new Intent(getApplicationContext(),FilterReports.class);
                startActivity(i);

            }
        });


        navigation_left.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        drawerLayout.closeDrawers();
                    }
                });

      /*  navigation_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawers();
            }
        });*/



        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this,
                drawerLayout, toolbar, R.string.openDrawer, R.string.closeDrawer) {

            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank

                super.onDrawerOpened(drawerView);
            }
        };
        //Setting the actionbarToggle to drawer layout
        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        //calling sync state is necessay or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();
        drawerLayout1 = (DrawerLayout) findViewById(R.id.drawer);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout1, toolbar, R.string.openDrawer, R.string.closeDrawer) {

            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank

                super.onDrawerOpened(drawerView);
            }
        };
        //Setting the actionbarToggle to drawer layout
        drawerLayout1.setDrawerListener(actionBarDrawerToggle);
        //calling sync state is necessay or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();




    }



    private void flashlight_custom_on_off()
    {


        LayoutInflater layoutInflater = LayoutInflater.from(Dashboard.this);
        View promptView = layoutInflater.inflate(R.layout.flashlight_custom_on_off, null);
        final android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(Dashboard.this);
        alertDialogBuilder.setView(promptView);
        final TextView head1 = (TextView) promptView.findViewById(R.id.textView);


        btnSwitch = (ImageButton) promptView.findViewById(R.id.btnSwitch);

        Typeface tf = Typeface.createFromAsset(getApplicationContext().getAssets(), "_SENINE.TTF");
        head1.setTypeface(tf);



        btnSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFlashOn) {
                    // turn off flash
                    turnOffFlash();
                } else {
                    // turn on flash
                    turnOnFlash();
                }

            }
        });


    }

    private void showCustomDialog() {
        LayoutInflater layoutInflater = LayoutInflater.from(Dashboard.this);
        View promptView = layoutInflater.inflate(R.layout.logout_custom_dialog, null);
        final android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(Dashboard.this);
        alertDialogBuilder.setView(promptView);
        final TextView head1 = (TextView) promptView.findViewById(R.id.textView);


        final Button no = (Button) promptView.findViewById(R.id.btn_no);
        final Button yes = (Button) promptView.findViewById(R.id.btn_yes);
        Typeface tf = Typeface.createFromAsset(getApplicationContext().getAssets(), "_SENINE.TTF");
        head1.setTypeface(tf);
        no.setTypeface(tf);
        yes.setTypeface(tf);


        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent no=new Intent(getApplicationContext(),Dashboard.class);
                startActivity(no);
            }
        });

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1 = new Intent(Intent.ACTION_MAIN);
                i1.setAction(Intent.ACTION_MAIN);
                i1.addCategory(Intent.CATEGORY_HOME);
                i1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                i1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i1);
                finish();
            }
        });
        // create an alert dialog
        android.app.AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }
    private void getCamera() {
        if (camera == null) {
            try {
                camera = Camera.open();
                params = camera.getParameters();
            } catch (RuntimeException e) {
                //Log.e("Camera Error. Failed to Open. Error: ", e.getMessage());

            }
        }
    }
    private void playSound(){
        if(isFlashOn){
            mp = MediaPlayer.create(Dashboard.this, R.raw.light_switch_off);
        }else{
            mp = MediaPlayer.create(Dashboard.this, R.raw.light_switch_on);
        }
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

            @Override
            public void onCompletion(MediaPlayer mp) {
                // TODO Auto-generated method stub
                mp.release();
            }
        });
        mp.start();
    }



    private void turnOnFlash() {
        if (!isFlashOn) {
            if (camera == null || params == null) {
                return;
            }
            // play sound
            playSound();

            params = camera.getParameters();
            params.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
            camera.setParameters(params);
            camera.startPreview();
            isFlashOn = true;

            // changing button/switch image
            toggleButtonImage();
        }

    }
    private void toggleButtonImage(){
        if(isFlashOn){
            btnSwitch.setImageResource(R.drawable.btn_switch_on);
        }else{
            btnSwitch.setImageResource(R.drawable.btn_switch_off);
        }
    }
    private void turnOffFlash() {
        if (isFlashOn) {
            if (camera == null || params == null) {
                return;
            }
            // play sound
            playSound();

            params = camera.getParameters();
            params.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
            camera.setParameters(params);
            camera.stopPreview();
            isFlashOn = false;

            // changing button/switch image
            toggleButtonImage();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();

        // on pause turn off the flash
        turnOffFlash();
    }



    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onResume() {
        super.onResume();

        // on resume turn on the flash
        if(hasFlash)
            turnOnFlash();
    }

    @Override
    protected void onStart() {
        super.onStart();

        // on starting the app get the camera params
        getCamera();
    }

    @Override
    protected void onStop() {
        super.onStop();

        // on stop release the camera
        if (camera != null) {
            camera.release();
            camera = null;
        }
    }

}
