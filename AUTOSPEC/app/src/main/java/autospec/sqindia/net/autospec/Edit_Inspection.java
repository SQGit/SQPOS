package autospec.sqindia.net.autospec;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.sloop.fonts.FontsManager;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Admin on 08-06-2016.
 */
public class Edit_Inspection extends Activity {
    String str_id, str_unit_no, str_agreement, cur_date, str_date, token,str_inspection_date,id;
    EditText edt_unitno, edt_agrementno;
    Button btn_submit,btn_back;
    TextView txt_head;
    ProgressDialog mProgressDialog;
    LoginDataBaseAdapter loginDataBaseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_inspection);

        //*********************set Typeface**************
        Typeface tf = Typeface.createFromAsset(getApplicationContext().getAssets(), "ROBOTO-LIGHT.TTF");
        edt_unitno = (EditText) findViewById(R.id.edt_unitno);
        edt_agrementno = (EditText) findViewById(R.id.edt_agrementno);
        btn_submit = (Button) findViewById(R.id.btn_submit);
        txt_head = (TextView) findViewById(R.id.textView_head);
        btn_back=(Button)findViewById(R.id.btn_back);


        //*****************change font using Typeface**************
        FontsManager.initFormAssets(this, "ROBOTO-LIGHT.TTF");
        FontsManager.changeFonts(this);


        //*******************Login onclicklistener***************
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gg=new Intent(getApplicationContext(),ModifyInspectionActivity.class);
                startActivity(gg);
                finish();
            }
        });

        // create a instance of SQLite Database
        loginDataBaseAdapter=new LoginDataBaseAdapter(getApplicationContext());
        loginDataBaseAdapter=loginDataBaseAdapter.open();

        str_inspection_date = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(Calendar.getInstance().getTime());

        //**************get All value from new inspection using getIntent method
        Intent intent = getIntent();
        if (null != intent)
        {
            str_id=intent.getStringExtra("user_id#");
            str_unit_no = intent.getStringExtra("unit_no#");
            str_agreement = intent.getStringExtra("agreement_no#");
            str_date=intent.getStringExtra("inspection_date#");
        }





        edt_unitno.setText(str_unit_no);
        edt_agrementno.setText(str_agreement);
        cur_date = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(Calendar.getInstance().getTime());




        //**********submit button onClick Listener**************
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String e1 = edt_unitno.getText().toString().trim();
                String e2 = edt_agrementno.getText().toString().trim();
                if(e1.equals("")||e2.equals(""))
                {
                    message2();
                }
                else {

                    loginDataBaseAdapter.updateInspection(str_id, e1, e2, str_inspection_date);
                    Log.e("tag", "val" + str_id + str_unit_no + str_agreement + str_inspection_date);
                    Intent update = new Intent(getApplicationContext(), ModifyInspectionActivity.class);
                    startActivity(update);
                    finish();
                    }
            }
        });
    }

    private void message2() {
        final Dialog dialog = new Dialog(Edit_Inspection.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_dialog);
        //adding text dynamically
        TextView txt_head2 = (TextView) dialog.findViewById(R.id.txt_head2);
        TextView txt_msg = (TextView) dialog.findViewById(R.id.txt_msg);
        txt_head2.setText("Alert Message");
        txt_msg.setText("Fields are Vacant....");
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

    @Override
    public void onBackPressed() {

        Intent i = new Intent(Edit_Inspection.this,Dashboard.class);
        startActivity(i);
        finish();
    }


}
