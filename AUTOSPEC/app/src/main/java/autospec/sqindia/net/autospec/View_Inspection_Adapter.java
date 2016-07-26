package autospec.sqindia.net.autospec;

        import android.app.Activity;
        import android.content.Context;
        import android.content.Intent;
        import android.content.SharedPreferences;
        import android.graphics.Typeface;
        import android.net.Uri;
        import android.preference.PreferenceManager;
        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.BaseAdapter;
        import android.widget.Button;
        import android.widget.TextView;
        import java.text.SimpleDateFormat;
        import java.util.ArrayList;
        import java.util.Calendar;


        public class View_Inspection_Adapter extends BaseAdapter {

        Context c1;
        ArrayList<Modify_Inspection> arrayList;
        TextView unitno,rentno;
        Button modify,mail;
        String str_inspection_date,id,jj,storeval,inspection_date,edit_rental_no,edit_unit_no,user_id;
        LoginDataBaseAdapter loginDataBaseAdapter;
        Modify_Inspection data;
        String sendemailaddress;

        public View_Inspection_Adapter(Context c1, ArrayList<Modify_Inspection> list) {
                this.c1 = c1;
                arrayList = list;
        }

        @Override
        public int getCount() {
                return arrayList.size();
        }

        @Override
        public Object getItem(int position) {

                return arrayList.get(position);
        }

        @Override
        public long getItemId(int position) {
                return position;
        }

        @Override
        public View getView(final int position, View v2, ViewGroup parent) {
                  data = arrayList.get(position);
                LayoutInflater inflat = (LayoutInflater) c1.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v2 = inflat.inflate(R.layout.modify_inspection_list,null);

                Typeface tf = Typeface.createFromAsset(c1.getAssets(), "ROBOTO-LIGHT.TTF");
                unitno=(TextView) v2.findViewById(R.id.txt_Unit_no);
                rentno=(TextView) v2.findViewById(R.id.txt_agrreement_no);
                modify=(Button) v2.findViewById(R.id.btn_modify);
                mail=(Button) v2.findViewById(R.id.btn_email);

                unitno.setTypeface(tf);
                rentno.setTypeface(tf);
                modify.setTypeface(tf);
                mail.setTypeface(tf);

                str_inspection_date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
                Log.e("tag", "inspectiontime" + str_inspection_date);


                // create a instance of SQLite Database
                loginDataBaseAdapter=new LoginDataBaseAdapter(this.c1);
                loginDataBaseAdapter=loginDataBaseAdapter.open();

                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this.c1);
                id=sharedPreferences.getString("id","");
                Log.e("tag","a"+id);
                unitno.setText(data.get_UNIT_NO());
                Log.e("tag","b"+unitno);
                rentno.setText(data.get_RENTAL_NO());
                Log.e("tag","c"+rentno);

                //***********modify onclick listener*****************
                  modify.setOnClickListener(new View.OnClickListener() {
                          @Override
                          public void onClick(View v) {


                                  user_id=arrayList.get(position).get_ID();
                                  edit_unit_no = arrayList.get(position).get_UNIT_NO();
                                  edit_rental_no=arrayList.get(position).get_RENTAL_NO();
                                  inspection_date=arrayList.get(position).get_DATE();

                                  Intent intent = new Intent(c1, Edit_Inspection.class);
                                  intent.putExtra("user_id#",user_id);
                                  intent.putExtra("unit_no#",edit_unit_no);
                                  intent.putExtra("agreement_no#",edit_rental_no);
                                  intent.putExtra("inspection_date#",inspection_date);
                                  c1.startActivity(intent);
                                  ((Activity)c1).finish();
                          }
                  });


                mail.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(c1);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                sendemailaddress = sharedPreferences.getString("useremail", "");

                                user_id=arrayList.get(position).get_ID();
                                edit_unit_no = arrayList.get(position).get_UNIT_NO();
                                edit_rental_no=arrayList.get(position).get_RENTAL_NO();
                                inspection_date=arrayList.get(position).get_DATE();

                                storeval = "AUTOSPEC DETAILS:" + "\n\n\nInspection Date:" + "\t" + inspection_date + "\nUser Id:\t" + user_id + "\nUnit No:\t" + edit_unit_no + "\nRental No:\t" +
                                        edit_rental_no;
                                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                                        "mailto", sendemailaddress, null));

                                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject");
                                emailIntent.putExtra(Intent.EXTRA_TEXT, storeval);
                                c1.startActivity(emailIntent);

                        }
                });

                return v2;

        }




}




