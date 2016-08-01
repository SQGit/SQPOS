package net.sqindia.ezcabill;


import android.annotation.SuppressLint;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v4.app.Fragment;
import com.ngx.BluetoothPrinter;


/**
 * Created by $Krishna on 18-06-2016.
 */
public class BluetoothPrinterMain extends AppCompatActivity {


    public static BluetoothPrinter mBtp = BluetoothPrinter.INSTANCE;
    private static android.support.v4.app.FragmentManager fragMgr;
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







        fragMgr = getSupportFragmentManager();
        mBtp.setDebugService(BuildConfig.DEBUG);

        try {
            mBtp.initService(this, mHandler);
        } catch (Exception e) {
            e.printStackTrace();
        }

        nm = new AsciiFragment();
        Log.e("tag","checked");

      fragMgr.beginTransaction().replace(R.id.container,nm)
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



}
