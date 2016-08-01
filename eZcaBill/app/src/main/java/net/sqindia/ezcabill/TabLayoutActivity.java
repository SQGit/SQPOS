package net.sqindia.ezcabill;

import android.app.TabActivity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.renderscript.Type;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;


public class TabLayoutActivity extends TabActivity  {



    LinearLayout ib;

    TextView txt_unpaidpaid,txt_copyrights;
    TabHost tabHost1;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.paid_unpaid);

        ib = (LinearLayout) findViewById(R.id.layout_back);
        txt_unpaidpaid = (TextView) findViewById(R.id.tv_unpaidpaid);

        Typeface tf = Typeface.createFromAsset(getApplicationContext().getAssets(),"reg.TTF");
        txt_unpaidpaid.setTypeface(tf);

        //Back button//
        ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(TabLayoutActivity.this,DashBoard.class);
                startActivity(i);
                finish();
            }
        });



        TabHost tabHost = getTabHost();
      // tabHost1 = (TabHost) findViewById(R.id.tabhost);

        // Tab for Photos
        TabSpec paid = tabHost.newTabSpec("Paid");
       // paid.setIndicator("Photos", getResources().getDrawable(R.drawable.icon_photos_tab));
        paid.setIndicator("PAID");

        Intent paidIntent = new Intent(this, Paid.class);
        paid.setContent(paidIntent);
       // paid.setTabTextColors(ContextCompat.getColorStateList(this, R.color.tab_selector));

        // Tab for Songs
        TabSpec unpaid = tabHost.newTabSpec("Unpaid");
        // setting Title and Icon for the Tab
     //   unpaid.setIndicator("Songs", getResources().getDrawable(R.drawable.icon_songs_tab));
        unpaid.setIndicator("UNPAID");
        Intent unpaidIntent = new Intent(this, Unpaid.class);
        unpaid.setContent(unpaidIntent);

        for(int i=0;i<tabHost.getTabWidget().getChildCount();i++)
        {
            TextView tv = (TextView) tabHost.getTabWidget().getChildAt(i).findViewById(android.R.id.title);
            tv.setTextColor(Color.parseColor("#ffffff"));
        }
        // Adding all TabSpec to TabHost
        tabHost.addTab(paid); // Adding photos tab
        tabHost.addTab(unpaid); // Adding songs tab

    }
    @Override
    public void onBackPressed() {

        Intent i = new Intent(TabLayoutActivity.this,DashBoard.class);
        startActivity(i);
        finish();

        // super.onBackPressed();
    }
}