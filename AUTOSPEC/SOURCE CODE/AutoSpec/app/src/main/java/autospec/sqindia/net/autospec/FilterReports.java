package autospec.sqindia.net.autospec;

    import android.app.Activity;
    import android.content.Intent;
    import android.database.Cursor;
    import android.graphics.Color;
    import android.graphics.Typeface;
    import android.os.Bundle;
    import android.util.Log;
    import android.view.View;
    import android.view.ViewGroup;
    import android.view.WindowManager;
    import android.widget.AdapterView;
    import android.widget.ArrayAdapter;
    import android.widget.Button;
    import android.widget.ListView;
    import android.widget.Spinner;
    import android.widget.TextView;

    import com.google.android.gms.appindexing.AppIndex;
    import com.google.android.gms.common.api.GoogleApiClient;
    import com.sloop.fonts.FontsManager;
    import java.util.ArrayList;
    import java.util.List;

    public class FilterReports extends Activity {
        LoginDataBaseAdapter loginDataBaseAdapter;
        TextView rental;
        Button btn_back, btn_filter;
        ListView list;
        Spinner spin1, spin2;
        String spin1_val, spin2_val;
        static String id = "id";
        ArrayList<String> unitno, agg;
        public Integer number;
        List<String> list1;
        String storedId, Stored_aggno, stored_unitno, filterdata;
        String QRY1 = "SELECT * FROM INSPECTION";
        String filter_aggrementno = "SELECT " + loginDataBaseAdapter.UNITNO + " FROM INSPECTION";
        String filter_rentalno = "SELECT " + loginDataBaseAdapter.RENTALNO + " FROM INSPECTION";
        private GoogleApiClient client;
        ArrayAdapter<String> dataAdapter;
        ArrayAdapter<String> dataAdapter1;
        Typeface tf;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            setContentView(R.layout.filter_reports);

            //******************using font manager******
            FontsManager.initFormAssets(this, "ROBOTO-LIGHT.TTF");       //initialization
            FontsManager.changeFonts(this);

            // get Instance of Database Adapter
            loginDataBaseAdapter = new LoginDataBaseAdapter(this);
            loginDataBaseAdapter = loginDataBaseAdapter.open();

            //****************findviewbyid***********************
            btn_back = (Button) findViewById(R.id.btn_back);
            btn_filter = (Button) findViewById(R.id.btn_filters);
            list = (ListView) findViewById(R.id.listView);
            spin1 = (Spinner) findViewById(R.id.spinner_agreementno);
            spin2 = (Spinner) findViewById(R.id.spinner_unitno);
            rental = (TextView) findViewById(R.id.rental);
            tf = Typeface.createFromAsset(getApplicationContext().getAssets(), "ROBOTO-LIGHT.TTF");
            //************using font mananger for whole class*************
            FontsManager.initFormAssets(this, "ROBOTO-LIGHT.TTF");
            FontsManager.changeFonts(this);


            getAggrementNo(filter_aggrementno);
            getUnitNo(filter_rentalno);


            //*******************Login onclicklistener***************
            btn_back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent_back = new Intent(getApplicationContext(), Dashboard.class);
                    startActivity(intent_back);
                    finish();
                }
            });

            //*****************spinner for unit no****************
            spin1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    FilterReports.this.number = spin1.getSelectedItemPosition();
                    spin1_val = spin1.getSelectedItem().toString();
                    storedId = loginDataBaseAdapter.getId(spin1_val);
                    int ii = spin1.getSelectedItemPosition();
                    spin2.setSelection(ii);
                    Stored_aggno = loginDataBaseAdapter.getAggnum(storedId);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });

            //*****************spinner for rental no****************
            spin2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    FilterReports.this.number = spin2
                            .getSelectedItemPosition();

                    int iii = spin2.getSelectedItemPosition();
                    spin1.setSelection(iii);
                    spin2_val = spin2.getSelectedItem().toString();
                    storedId = loginDataBaseAdapter.getId1(spin2_val);
                    stored_unitno = loginDataBaseAdapter.getUnitNo(storedId);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });

            //*****************filter button onclick listener****************
            btn_filter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   /* spin2.setVisibility(View.VISIBLE);
                    tv_spin2.setVisibility(View.GONE);
                    tv_spin1.setVisibility(View.GONE);*/
                    filterdata = loginDataBaseAdapter.getId(spin1_val);
                    String query = "SELECT * FROM INSPECTION WHERE ID='" + filterdata + "'";
                    getFilterData(query);
                }
            });


            goList(QRY1);
            client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

        //*************get value from getter setter****************
        private void getFilterData(String query) {
            ArrayList<Filter_Getter_Setter> listvalg = new ArrayList<Filter_Getter_Setter>();
            Cursor c1 = loginDataBaseAdapter.getFilterData(query);
            Log.e("tag", "<---lv1111********---->" + c1);
            if (c1 != null) {
                if (c1.moveToFirst()) {
                    do {
                        Filter_Getter_Setter jv0 = new Filter_Getter_Setter();
                        jv0.set_ID(c1.getString(c1.getColumnIndex(loginDataBaseAdapter.ID)));
                        jv0.set_UNIT_NO(c1.getString(c1.getColumnIndex(loginDataBaseAdapter.UNITNO)));
                        jv0.set_RENTAL_NO(c1.getString(c1.getColumnIndex(loginDataBaseAdapter.RENTALNO)));
                        jv0.set_DATE(c1.getString(c1.getColumnIndex(loginDataBaseAdapter.DATE)));
                        listvalg.add(jv0);
                    }
                    while (c1.moveToNext());
                }
            }

            Filter_Adapter adapter1 = new Filter_Adapter(FilterReports.this, listvalg);
            list.setAdapter(adapter1);
            adapter1.notifyDataSetChanged();


    }


    //***************get unit no from spinner(filter)*********
    private void getAggrementNo(String filter_aggrementno) {
        unitno = new ArrayList<String>();
        list1 = new ArrayList<String>();
        Cursor c1 = loginDataBaseAdapter.readDataagg(filter_aggrementno);
        if (c1.moveToFirst()) {
            do {
                list1.add(c1.getString(0));
            } while (c1.moveToNext());
        }

        setSpinner1();

    }

        private void setSpinner1()
        {

            final CustomAdapter arrayAdapter = new CustomAdapter(this, android.R.layout.simple_spinner_item, list1) {
                @Override
                public boolean isEnabled(int position) {
                    if (position == 0) {
                        // Disable the first item from Spinner
                        // First item will be use for hint
                        return false;
                    } else {
                        return true;
                    }
                }

                @Override
                public View getDropDownView(int position, View convertView,
                                            ViewGroup parent) {
                    View view = super.getDropDownView(position, convertView, parent);
                    TextView tv = (TextView) view;
                     tv.setTypeface(tf);

                    if (position == 0) {
                        tv.setTextColor(Color.BLACK);
                    } else {
                        tv.setTextColor(Color.BLACK);
                    }
                    return view;
                }
            };
            spin1.setAdapter(arrayAdapter);
        }


    //***************get rental no from spinner(filter)*********
    private void getUnitNo(String filter_unitno) {

        agg = new ArrayList<String>();
        list1 = new ArrayList<String>();
        Cursor c1 = loginDataBaseAdapter.readDatarental(filter_unitno);
        if (c1.moveToFirst()) {
            do {
                list1.add(c1.getString(0));
            } while (c1.moveToNext());
        }

        setSpinner2();
    }

        private void setSpinner2() {

            final CustomAdapter arrayAdapter = new CustomAdapter(this, android.R.layout.simple_spinner_item, list1) {
                @Override
                public boolean isEnabled(int position) {
                    if (position == 0) {
                        // Disable the first item from Spinner
                        // First item will be use for hint
                        return false;
                    } else {
                        return true;
                    }
                }

                @Override
                public View getDropDownView(int position, View convertView,
                                            ViewGroup parent) {
                    View view = super.getDropDownView(position, convertView, parent);
                    TextView tv = (TextView) view;
                    tv.setTypeface(tf);
                    if (position == 0) {
                        tv.setTextColor(Color.BLACK);
                    } else {
                        tv.setTextColor(Color.BLACK);
                    }
                    return view;
                }
            };
            spin2.setAdapter(arrayAdapter);
        }

        //*************view all default data using listview
        private void goList(String qry1) {
            ArrayList<Filter_Getter_Setter> lv1 = new ArrayList<Filter_Getter_Setter>();

            Cursor c1 = loginDataBaseAdapter.fetchdata(qry1);
            Log.e("tag", "<---lv111111111111---->" + c1);
            if (c1 != null) {
                if (c1.moveToFirst()) {
                    do {
                        Filter_Getter_Setter jv3 = new Filter_Getter_Setter();
                        jv3.set_ID(c1.getString(c1.getColumnIndex(loginDataBaseAdapter.ID)));
                        jv3.set_UNIT_NO(c1.getString(c1.getColumnIndex(loginDataBaseAdapter.UNITNO)));
                        jv3.set_RENTAL_NO(c1.getString(c1.getColumnIndex(loginDataBaseAdapter.RENTALNO)));
                        jv3.set_DATE(c1.getString(c1.getColumnIndex(loginDataBaseAdapter.DATE)));
                        lv1.add(jv3);
                        Log.e("tag", "aaa" + lv1);
                    }
                    while (c1.moveToNext());
                }
            }

            Filter_Adapter adapter1 = new Filter_Adapter(FilterReports.this, lv1);
            list.setAdapter(adapter1);
        }


        @Override
        public void onBackPressed() {
            Intent i = new Intent(FilterReports.this, Dashboard.class);
            startActivity(i);
            finish();
        }

}

