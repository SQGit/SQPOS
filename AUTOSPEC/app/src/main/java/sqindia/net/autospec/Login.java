package sqindia.net.autospec;

/**
 * Created by Admin on 21-05-2016.
 */
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;

import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Ramya on 14-05-2016.
 */
public class Login extends Fragment {
    EditText uname, pwd;
    public static String URL_LOGIN = "http://sqindia01.cloudapp.net/clubhouse/api/v1/user/login";
    String user_Name, user_Password;
    Button submit;
    TextView reg_tv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.signin_activity, container, false);

        uname = (EditText) view.findViewById(R.id.editText_name);
        pwd = (EditText) view.findViewById(R.id.editText_email);
 /*= (EditText) view.findViewById(R.id.editText_pwd);*/

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Sign in");


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user_Name = uname.getText().toString();
                user_Password = pwd.getText().toString();
                new MyActivityAsync(user_Name, user_Password).execute();
               /* Intent i=new Intent(getActivity(),MemberDashboard.class);
                startActivity(i);*/

            }
        });
        return view;
    }

    class MyActivityAsync extends AsyncTask<String, Void, String> {

        String user_Name, user_Password;

        public MyActivityAsync(String user_Name, String user_Password)
        {
            String json = "", jsonStr = "";
            this.user_Name = user_Name;
            this.user_Password = user_Password;

        }

        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected String doInBackground(String... params) {
            Log.e("input Name", user_Name);
            Log.e("input password", user_Password);
            String json = "", jsonStr = "";
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.accumulate("userName", "mufeed");
                jsonObject.accumulate("password", "mufeed123");
                json = jsonObject.toString();
                JSONObject data = new JSONObject();
                data.accumulate("data", jsonObject);
                return jsonStr = HttpUtils.makeRequest(URL_LOGIN, json);
            } catch (Exception e) {
                Log.d("InputStream", e.getLocalizedMessage());
            }
            return null;

        }

        @Override
        protected void onPostExecute(String jsonStr)
        {
            Log.e("tag", "<-----result---->" + jsonStr);
            super.onPostExecute(jsonStr);

        /*    {"status":"SUCCESS","message":"Authentication Successful",
                    "data":{"id":"1","userName":"mufeed","name":"mufeed",
                    "email":"mufeedk@gmail.com","address":"chennai","positionBoard":"1",
                    "positionRole":"1","createdAt":null,"updatedAt":null,
                    "sessionToken":"23ce9fe46b486569a291582f03010a69","loginStatus":"Y"}}
*/
            try {
                JSONObject jo = new JSONObject(jsonStr);
                String status = jo.getString("status");
                String msg = jo.getString("message");
                Log.d("tag", "<-----Status----->" + status);



                if (status.equals("SUCCESS"))
                {
                    JSONObject data = jo.getJSONObject("data");
                    String userName = data.getString("userName");
                    String token = data.getString("authToken");
                    String name = data.getString("name");
                    String email = data.getString("email");
                    String address = data.getString("address");
                    Log.e("tag", "<-----Send sessionToken----->" + token);
                    SharedPreferences s_pref = PreferenceManager.getDefaultSharedPreferences(getActivity());
                    SharedPreferences.Editor edit = s_pref.edit();
                    edit.putString("token", token);
                    edit.putString("name", name);
                    edit.putString("email", email);
                    edit.putString("address", address);
                    edit.commit();
                    Intent i=new Intent(getActivity(),Dashboard.class);
                    startActivity(i);

                }

                else
                {
                    Toast.makeText(getActivity(),msg,Toast.LENGTH_LONG).show();

                }


            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

    }


}
