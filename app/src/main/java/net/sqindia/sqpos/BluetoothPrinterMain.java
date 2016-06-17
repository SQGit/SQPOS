/**
 * Copyright 2014 Looped Labs pvt. Ltd. http://loopedlabs.com
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.sqindia.sqpos;

import android.annotation.SuppressLint;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.ngx.BluetoothPrinter;

import java.text.DateFormat;
import java.util.Date;


public class BluetoothPrinterMain extends ActionBarActivity {


    public static BluetoothPrinter mBtp = BluetoothPrinter.INSTANCE;
    private static FragmentManager fragMgr;
    private Fragment nm;
    private static final String cHomeStack = "home";
    private TextView tvStatus;
    String ss;
    Typeface tf;

    private String mConnectedDeviceName = "";
    public static final String title_connecting = "connecting...";
    public static final String title_connected_to = "connected: ";
    public static final String title_not_connected = "not connected";

    @SuppressLint("HandlerLeak")
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case BluetoothPrinter.MESSAGE_STATE_CHANGE:
                    switch (msg.arg1) {
                        case BluetoothPrinter.STATE_CONNECTED:
                            break;
                        case BluetoothPrinter.STATE_CONNECTING:
                            break;
                        case BluetoothPrinter.STATE_LISTEN:
                        case BluetoothPrinter.STATE_NONE:
                            break;
                    }
                    break;
                case BluetoothPrinter.MESSAGE_DEVICE_NAME:
                    mConnectedDeviceName = msg.getData().getString(
                            BluetoothPrinter.DEVICE_NAME);

                    break;
                case BluetoothPrinter.MESSAGE_STATUS:
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.btp_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        FontChangeCrawler fontChanger = new FontChangeCrawler(getAssets(), "appfont.OTF");
        fontChanger.replaceFonts((ViewGroup) this.findViewById(android.R.id.content));
        String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
        ss = currentDateTimeString;
        Log.e("TAg", "sssss" + ss);

        tf = Typeface.createFromAsset(getAssets(), "appfont.OTF");
        SpannableStringBuilder SS = new SpannableStringBuilder("ADD & SEARCH ITEM");
        SS.setSpan(new CustomTypefaceSpan("ADD & SEARCH ITEM", tf), 0, SS.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        getSupportActionBar().setTitle(SS);

        tvStatus = (TextView) findViewById(R.id.tvStatus);

        fragMgr = getSupportFragmentManager();
        mBtp.setDebugService(BuildConfig.DEBUG);

        try {
            mBtp.initService(this, mHandler);
        } catch (Exception e) {
            e.printStackTrace();
        }

        nm = new AsciiFragment();

        fragMgr.beginTransaction().replace(R.id.container, nm)
                .addToBackStack(cHomeStack).commit();
    }

    @Override
    public void onPause() {
        mBtp.onActivityPause();
        super.onPause();
    }

    @Override
    public void onResume() {
        mBtp.onActivityResume();
        super.onResume();
    }

    @Override
    public void onDestroy() {
        mBtp.onActivityDestroy();
        super.onDestroy();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mBtp.onActivityResult(requestCode, resultCode, data, this);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            switch (fragMgr.getBackStackEntryCount()) {
                case 0:
                case 1:
                    finish();
                    break;
                case 2:
                    fragMgr.popBackStack();
                    break;
                default:
                    fragMgr.popBackStack();
                    break;
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_clear_device:
                Builder d = new Builder(this);
                d.setTitle("NGX Bluetooth Printer");
                d.setMessage("Are you sure you want to delete your preferred Bluetooth printer ?");
                d.setPositiveButton(android.R.string.yes,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                mBtp.clearPreferredPrinter();
                                Toast.makeText(getApplicationContext(),
                                        "Preferred Bluetooth printer cleared",
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
                d.setNegativeButton(android.R.string.no,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        });
                d.show();
                return true;
            case R.id.action_connect_device:
                mBtp.showDeviceList(this);
                return true;
            case R.id.action_unpair_device:
                Builder u = new Builder(this);
                u.setTitle("Bluetooth Printer unpair");
                u.setMessage("Are you sure you want to unpair all Bluetooth printers ?");
                u.setPositiveButton(android.R.string.yes,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                if (mBtp.unPairBluetoothPrinters()) {
                                    Toast.makeText(
                                            getApplicationContext(),
                                            "All NGX Bluetooth printer(s) unpaired",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                u.setNegativeButton(android.R.string.no,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        });
                u.show();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.bluetooth_printer_main, menu);
        return true;
    }

    @SuppressLint("InlinedApi")
//	private static void lockOrientation(Activity activity) {
//		Display display = ((WindowManager) activity
//				.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
//		int rotation = display.getRotation();
//		int tempOrientation = activity.getResources().getConfiguration().orientation;
//		switch (tempOrientation) {
//			case Configuration.ORIENTATION_LANDSCAPE:
//				if (rotation == Surface.ROTATION_0
//						|| rotation == Surface.ROTATION_90)
//					activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
//				else
//					activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE);
//				break;
//			case Configuration.ORIENTATION_PORTRAIT:
//				if (rotation == Surface.ROTATION_0
//						|| rotation == Surface.ROTATION_270)
//					activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//				else
//					activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT);
//		}
//	}

    public static void changeFragment(int iContainerId, Fragment fragment,
                                      boolean addToBackStack) {
        FragmentTransaction t = fragMgr.beginTransaction();

        if (iContainerId == R.id.container) {
            t.hide(fragMgr.findFragmentById(R.id.container));
            t.add(iContainerId, fragment);

            if (addToBackStack) {
                t.addToBackStack(cHomeStack);
            }
        }
        // Commit the transaction
        t.commit();
    }
}


