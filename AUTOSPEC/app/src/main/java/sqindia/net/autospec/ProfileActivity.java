package sqindia.net.autospec;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sloop.fonts.FontsManager;

/**
 * Created by Admin on 01-06-2016.
 */
public class ProfileActivity extends Activity {

    EditText name_view,email_view;
    String get_name,get_email;
    Button btn_back,submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.profile_custom_dialog);

        FontsManager.initFormAssets(this, "_SENINE.TTF");       //initialization
        FontsManager.changeFonts(this);

        name_view = (EditText) findViewById(R.id.name);
        email_view = (EditText)findViewById(R.id.email);
        btn_back = (Button) findViewById(R.id.btn_back) ;
        submit = (Button) findViewById(R.id.submit) ;

        Typeface tf = Typeface.createFromAsset(getApplicationContext().getAssets(), "_SENINE.TTF");
        name_view.setTypeface(tf);
        email_view.setTypeface(tf);
        SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(ProfileActivity.this);
        get_name = sh.getString("n", "");
        get_email =sh.getString("m", "");
        Log.e("tag","hel"+get_name);

        name_view.setText(get_name);
        email_view.setText(get_email);


        //*******************btn_back onclicklistener***************
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_back = new Intent(getApplicationContext(), Dashboard.class);
                startActivity(intent_back);
            }
        });

        //*******************Submit onclicklistener***************
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"under Process",Toast.LENGTH_SHORT).show();
                Intent intent_submit = new Intent(getApplicationContext(), Dashboard.class);
                startActivity(intent_submit);
            }
        });
    }
}