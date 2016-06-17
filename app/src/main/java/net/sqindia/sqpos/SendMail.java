package net.sqindia.sqpos;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

/**
 * Created by Admin on 02-03-2016.
 */
public class SendMail extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mail);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        final Button send = (Button) this.findViewById(R.id.button);
        send.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub

                try {
                    GMailSender sender = new GMailSender("way2gunasundari@gmail.com", "mylaptopishp");
                    sender.sendMail("This is Subject",
                            "This is Body",
                            "way2gunasundari@gmail.com",
                            "mbrsalman@gmail.com");
                } catch (Exception e) {

                    Log.e("SendMail", e.getMessage(), e);
                }

            }
        });
    }
}