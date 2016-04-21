package com.djackowski.dbconnect;


import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.djackowski.R;
import com.djackowski.navigation_drawer.NavigationDrawer;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class LoginFragment extends Fragment {

    //testing from a real server:
    private static final String LOGIN_URL = "http://distancemaster.esy.es/login.php";
    //JSON element ids from repsonse of php script:
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";

    //php login script location:

    //localhost :
    //testing on your device
    //put your local ip instead,  on windows, run CMD > ipconfig
    //or in mac's terminal type ifconfig and look for the ip under en0 or en1
    // private static final String LOGIN_URL = "http://xxx.xxx.x.x:1234/webservice/login.php";

    //testing on Emulator:
    // private static final String LOGIN_URL = "http://10.0.2.2:1234/webservice/login.php";
    private static boolean isLoggedIn = false;
    // JSON parser class
    JSONParser jsonParser = new JSONParser();
    private EditText user, pass;
    // Progress Dialog
    private ProgressDialog pDialog;
    /* @Nullable
     @Override
     public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       View
         //setup input fields
         user = (EditText)view.findViewById(R.id.editTextLogin);
         pass = (EditText)view.findViewById(R.id.editTextPassword);

         return view;
     }*/
    private View view;
    private Button buttonLogin;

    public static boolean isLoggedIn() {
        return isLoggedIn;
    }

    public static boolean userLoggedIn() {
        if (isLoggedIn)
            return true;
        else
            return false;
    }

    public static void setIsLoggedIn(boolean isLoggedIn) {
        LoginFragment.isLoggedIn = isLoggedIn;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_login, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        user = (EditText) getView().findViewById(R.id.editTextLogin);
        pass = (EditText) getView().findViewById(R.id.editTextPassword);
        buttonLogin = (Button) getView().findViewById(R.id.buttonLogin);


        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(checkEnableOfServices()){
                    String username = user.getText().toString();
                    String password = pass.getText().toString();

                    new AttemptLogin().execute(username, password);
                }



            }
        });
    }

    public boolean checkEnableOfServices() {


        ConnectivityManager connectivityManager =
                (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork =
                connectivityManager.getActiveNetworkInfo();
        if (activeNetwork != null) { // connected to the internet
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                // connected to wifi
                // Toast.makeText(getActivity(), activeNetwork.getTypeName(), Toast.LENGTH_SHORT).show();
            } else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                // connected to the mobile provider's data plan
                // Toast.makeText(getActivity(), activeNetwork.getTypeName(), Toast.LENGTH_SHORT).show();
            }
            return true;
        } else {
            // not connected to the internet
            // Build the alert dialog
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("Internet jest nieaktywny!");
            builder.setMessage("Proszę włączyć sieć komórkową lub WiFi");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogInterface, int i) {
                    // Show location settings when the user acknowledges the alert dialog
                    Intent intent = new Intent(Settings.ACTION_SETTINGS);
                    startActivity(intent);
                }
            });
            Dialog alertDialog = builder.create();
            alertDialog.setCanceledOnTouchOutside(false);
            alertDialog.show();

            if (activeNetwork != null)
                return true;
            else
                return false;
        }

    }

    class AttemptLogin extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         */

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Attempting login...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... args) {//zwraca wiadomosc, ktora odczytujemy w onPostExecute w parametrze
            // TODO Auto-generated method stub
            // Check for success tag
            int success;

            try {

                // Building Parameters
                List<NameValuePair> params = new ArrayList<>();
                params.add(new BasicNameValuePair("username", args[0]));
                params.add(new BasicNameValuePair("password", args[1]));

                Log.d("request!", "starting");
                // getting product details by making HTTP request

                Object jsonObject = JSONParser.makeHttpRequest(LOGIN_URL, "POST", params);



                // json success tag
                if(jsonObject instanceof JSONObject){
                    JSONObject json = (JSONObject) jsonObject;
                    success = json.getInt(TAG_SUCCESS);
                    if (success == 1) {
                        isLoggedIn = true;
                        Log.d("Login Successful!", json.toString());
                        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
                        Editor edit = sp.edit();
                        edit.putString("username", args[0]);
                        edit.commit();
                        getActivity().finish();
                        startActivity(new Intent(getActivity(), NavigationDrawer.class));
                        return json.getString(TAG_MESSAGE);
                    } else {
                        isLoggedIn = false;
                        Log.d("Login Failure!", json.getString(TAG_MESSAGE));
                        return json.getString(TAG_MESSAGE);

                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;

        }

        /**
         * After completing background task Dismiss the progress dialog
         **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog once product deleted
            pDialog.dismiss();
            if (file_url != null) {
                Toast.makeText(getActivity(), file_url, Toast.LENGTH_LONG).show(); //zmiana z activity
                // Toast.makeText(getActivity(), file_url, Toast.LENGTH_LONG).show();
            }

        }

    }

}