package autospec.sqindia.net.autospec;

    import android.app.Activity;
    import android.content.Intent;
    import android.os.Bundle;
    import android.view.View;
    import android.widget.Button;
    import android.widget.ImageView;
    import android.widget.TextView;

    import com.sloop.fonts.FontsManager;


public class DashboardActivity extends Activity {
        ImageView imageView_new,imageView_modify,imageView_vehicle;
        TextView textView_newins,textView_modifyins,textView_report,textView_head,textView_username;
        Button navigation_left,navigation_right;

    @Override
        protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard_activity);

        //****************findviewbyid***********************
        textView_head=(TextView)findViewById(R.id.textView_head);
        textView_newins=(TextView)findViewById(R.id.textView_newins);
        textView_modifyins=(TextView)findViewById(R.id.textView_modifyins);
        textView_report=(TextView)findViewById(R.id.textView_report);
        textView_username=(TextView)findViewById(R.id.textView_username);
        imageView_new=(ImageView)findViewById(R.id.imageView_new);
        imageView_modify=(ImageView)findViewById(R.id.imageView_modify);
        imageView_vehicle=(ImageView)findViewById(R.id.imageView_vehicle);
        navigation_left=(Button)findViewById(R.id.navigation_left);
        //navigation_right=(Button)findViewById(R.id.navigation_right);

        //*****************change font using Typeface**************
        FontsManager.initFormAssets(this, "ROBOTO-LIGHT.TTF");
        FontsManager.changeFonts(this);

        //*************new Inspection onclicklistener************
        textView_newins.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),SignUPActivity.class);
                startActivity(i);
            }
        });
        }

    @Override
    public void onBackPressed()
    {

    }




       /* @Override
        protected void onStop()
        {
            super.onStop();
        }*/
        }