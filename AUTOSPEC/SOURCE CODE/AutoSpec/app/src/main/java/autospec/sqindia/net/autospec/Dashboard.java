package autospec.sqindia.net.autospec;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.hardware.Camera;
import android.location.Location;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.sloop.fonts.FontsManager;


public class Dashboard extends AppCompatActivity {

    private Toolbar toolbar;
    private NavigationView navigationView, nv;
    private DrawerLayout drawerLayout, drawerLayout1;
    ActionBarDrawerToggle actionBarDrawerToggle;
    private Camera camera;
    private boolean isFlashOn;
    private boolean hasFlash;
    Camera.Parameters params;
    MediaPlayer mp;
    Switch flash, ok;
    ImageView imageView_new, imageView_modify, imageView_vehicle;
    TextView textView_newins, textView_modifyins, textView_report, textView_head, textView_username;
    TextView nav_settings, nav_flashlight, nav_profile,nav_mobile, nav_logout, nav_location;
    Button navigation_left, navigation_right;
    String name, showmail, showpwd,showenab,stored_mobile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.dashboard);


        //**************get shared pref*********
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(Dashboard.this);
        name = sharedPreferences.getString("user", "");
        showmail = sharedPreferences.getString("useremail", "");
        showpwd = sharedPreferences.getString("pwd", "");
        showenab = sharedPreferences.getString("empty_check","");
        Log.e("tag","checking"+showenab);


        //****************findviewbyid**************
        textView_head = (TextView) findViewById(R.id.textView_head);
        textView_newins = (TextView) findViewById(R.id.textView_newins);
        textView_modifyins = (TextView) findViewById(R.id.textView_modifyins);
        textView_report = (TextView) findViewById(R.id.textView_report);
        textView_username = (TextView) findViewById(R.id.textView_username);
        nav_settings = (TextView) findViewById(R.id.nav_settings);
        nav_flashlight = (TextView) findViewById(R.id.nav_flashlight);
        nav_profile = (TextView) findViewById(R.id.nav_profile);
        nav_mobile = (TextView) findViewById(R.id.nav_mobile);
        nav_logout = (TextView) findViewById(R.id.nav_logout);
        nav_location = (TextView) findViewById(R.id.nav_location);
        imageView_new = (ImageView) findViewById(R.id.imageView_new);
        imageView_modify = (ImageView) findViewById(R.id.imageView_modify);
        imageView_vehicle = (ImageView) findViewById(R.id.imageView_vehicle);
        navigation_left = (Button) findViewById(R.id.navigation_left);
        //navigation_right = (Button) findViewById(R.id.navigation_right);
        flash = (Switch) findViewById(R.id.mySwitchs);
        ok = (Switch) findViewById(R.id.mySwitch);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        nv = (NavigationView) findViewById(R.id.navigation_view1);


        //******setname for registered user
        textView_username.setText("Hi " + name + " !");


        setSupportActionBar(toolbar);


        //*****************change font using Typeface**************
        FontsManager.initFormAssets(this, "ROBOTO-LIGHT.TTF");
        FontsManager.changeFonts(this);

        //*************new Inspection onclicklistener************
        imageView_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_new = new Intent(getApplicationContext(), NewInspectionActivity.class);
                startActivity(intent_new);
                finish();
            }
        });


        //*************modify Inspection onclicklistener************
        imageView_modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(showenab.equals("1"))
                {
                    Intent intent_modify = new Intent(getApplicationContext(), ModifyInspectionActivity.class);
                    startActivity(intent_modify);
                    finish();
                }
                else
                {
                    enableMessage();
                }
            }
        });

        //*************navigation onclicklistener************
        nav_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutCustomDialog();
            }
        });


        //*************profileonclicklistener************
        nav_mobile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMobile();
            }
        });

        //*************profileonclicklistener************
        nav_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showProfileDetails();
            }
        });
        //*************flash onclicklistener************
        flash.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                getCamera();
                if (isChecked) {
                    turnOnFlash();
                } else {
                    turnOffFlash();
                }
            }
        });


        ok.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {

                if (isChecked) {
                } else {
                }
            }
        });


        //*************new Inspection onclicklistener************
        imageView_vehicle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(showenab.equals("1")){
                    Intent i = new Intent(getApplicationContext(), FilterReports.class);
                    startActivity(i);
                    finish();
                } else
                {
                    enableMessage();
                }

            }
        });


        navigation_left.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        drawerLayout.closeDrawers();
                    }
                });

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this,
                drawerLayout, toolbar, R.string.openDrawer, R.string.closeDrawer) {

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };


        drawerLayout.setDrawerListener(actionBarDrawerToggle);

        actionBarDrawerToggle.syncState();
        drawerLayout1 = (DrawerLayout) findViewById(R.id.drawer);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout1, toolbar, R.string.openDrawer, R.string.closeDrawer) {

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };


        drawerLayout1.setDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }

    private void showMobile() {
        LayoutInflater layoutInflater = LayoutInflater.from(Dashboard.this);
        View promptView = layoutInflater.inflate(R.layout.mobile_no, null);
        final AlertDialog alertD = new AlertDialog.Builder(Dashboard.this).create();
        alertD.setCancelable(false);
        Window window = alertD.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        final TextView head1 = (TextView) promptView.findViewById(R.id.textView);
        final EditText mobile = (EditText) promptView.findViewById(R.id.mobile);
        final Button btn_mobile = (Button) promptView.findViewById(R.id.btn_mobile);

        Typeface tf = Typeface.createFromAsset(getApplicationContext().getAssets(), "ROBOTO-LIGHT.TTF");
        head1.setTypeface(tf);
        mobile.setTypeface(tf);
        btn_mobile.setTypeface(tf);

        //set value
        btn_mobile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stored_mobile=mobile.getText().toString();
                alertD.dismiss();

            }
        });

        alertD.setView(promptView);
        alertD.show();
    }

    private void enableMessage() {

        final Dialog dialog = new Dialog(Dashboard.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_dialog);
        //adding text dynamically
        TextView txt_head2 = (TextView) dialog.findViewById(R.id.txt_head2);
        TextView txt_msg = (TextView) dialog.findViewById(R.id.txt_msg);
        Button btn_ok2 = (Button) dialog.findViewById(R.id.btn_ok2);

        Typeface tt = Typeface.createFromAsset(getApplicationContext().getAssets(), "ROBOTO-LIGHT.TTF");
        txt_head2.setTypeface(tt);
        txt_msg.setTypeface(tt);
        btn_ok2.setTypeface(tt);

        txt_msg.setText("Please Add Vechicle Details in New Inspection... ");

        btn_ok2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

            }
        });
        dialog.show();
    }


    //*******************show profile details method********
    private void showProfileDetails() {

        LayoutInflater layoutInflater = LayoutInflater.from(Dashboard.this);
        View promptView = layoutInflater.inflate(R.layout.profile_custom_dialog, null);
        final AlertDialog alertD = new AlertDialog.Builder(Dashboard.this).create();
        alertD.setCancelable(false);
        Window window = alertD.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        final TextView head1 = (TextView) promptView.findViewById(R.id.textView);
        final TextView pro_name = (TextView) promptView.findViewById(R.id.name);
        final TextView pro_email = (TextView) promptView.findViewById(R.id.email);
        final TextView pro_password = (TextView) promptView.findViewById(R.id.password);
        final TextView mob = (TextView) promptView.findViewById(R.id.mob);
        final Button yes = (Button) promptView.findViewById(R.id.btn_yes);

        Typeface tf = Typeface.createFromAsset(getApplicationContext().getAssets(), "ROBOTO-LIGHT.TTF");
        head1.setTypeface(tf);
        yes.setTypeface(tf);
        pro_name.setTypeface(tf);
        pro_email.setTypeface(tf);
        pro_password.setTypeface(tf);
        mob.setTypeface(tf);

        //set value
        pro_name.setText(name);
        pro_email.setText(showmail);
        pro_password.setText(showpwd);
        mob.setText(stored_mobile);
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               alertD.dismiss();


            }
        });

        alertD.setView(promptView);
        alertD.show();
    }


    //*******************show logout details method********
    private void logoutCustomDialog() {

        LayoutInflater layoutInflater = LayoutInflater.from(Dashboard.this);
        View promptView = layoutInflater.inflate(R.layout.logout_custom_dialog, null);
        final AlertDialog alertD = new AlertDialog.Builder(Dashboard.this).create();
        alertD.setCancelable(false);
        Window window = alertD.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        final TextView head1 = (TextView) promptView.findViewById(R.id.textView);
        final Button no = (Button) promptView.findViewById(R.id.btn_no);
        final Button yes = (Button) promptView.findViewById(R.id.btn_yes);

        Typeface tf = Typeface.createFromAsset(getApplicationContext().getAssets(), "ROBOTO-LIGHT.TTF");
        head1.setTypeface(tf);
        no.setTypeface(tf);
        yes.setTypeface(tf);

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent_modify = new Intent(getApplicationContext(), SignInActivity.class);
                startActivity(intent_modify);
                finish();
            }
        });

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertD.dismiss();
            }
        });

        alertD.setView(promptView);
        alertD.show();
    }


    //getcamera method
    private void getCamera() {
        if (camera == null) {
            try {
                camera = Camera.open();
                params = camera.getParameters();
            } catch (RuntimeException e) {
            }
        }
    }


    //playsound method
    private void playSound() {
        if (isFlashOn) {
            mp = MediaPlayer.create(Dashboard.this, R.raw.light_switch_off);
        } else {
            mp = MediaPlayer.create(Dashboard.this, R.raw.light_switch_on);
        }
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.release();
            }
        });
        mp.start();
    }


    //turnOnFlash method
    private void turnOnFlash() {
        if (!isFlashOn) {
            if (camera == null || params == null) {
                return;
            }
            playSound();
            params = camera.getParameters();
            params.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
            camera.setParameters(params);
            camera.startPreview();
            isFlashOn = true;
        }

    }


    //turnOffFlash method
    private void turnOffFlash() {
        if (isFlashOn) {
            if (camera == null || params == null) {
                return;
            }

            playSound();

            params = camera.getParameters();
            params.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
            camera.setParameters(params);
            camera.stopPreview();
            isFlashOn = false;
        }
    }


    private void showExit() {
        LayoutInflater layoutInflater = LayoutInflater.from(Dashboard.this);
        View promptView = layoutInflater.inflate(R.layout.exitdialog, null);
        final AlertDialog alertD = new AlertDialog.Builder(Dashboard.this).create();
        alertD.setCancelable(false);
        Window window = alertD.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        final TextView head1 = (TextView) promptView.findViewById(R.id.textView);
        final Button no = (Button) promptView.findViewById(R.id.btn_no);
        final Button yes = (Button) promptView.findViewById(R.id.btn_yes);

        Typeface tf = Typeface.createFromAsset(getApplicationContext().getAssets(), "ROBOTO-LIGHT.TTF");
        head1.setTypeface(tf);
        no.setTypeface(tf);
        yes.setTypeface(tf);

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dashboard.super.onBackPressed();
                onRestart();
                Intent i1 = new Intent(Intent.ACTION_MAIN);
                i1.setAction(Intent.ACTION_MAIN);
                i1.addCategory(Intent.CATEGORY_HOME);
                i1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                i1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i1);
                alertD.dismiss();
                finish();
            }
        });

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertD.dismiss();
            }
        });
        alertD.setView(promptView);
        alertD.show();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    @Override
    protected void onPause() {
        super.onPause();
        turnOffFlash();
    }


    @Override
    protected void onResume() {
        super.onResume();

        if (hasFlash)
            turnOnFlash();
    }

    @Override
    public void onBackPressed() {

        showExit();
    }


    @Override
    protected void onStop() {
        super.onStop();

    }
}

