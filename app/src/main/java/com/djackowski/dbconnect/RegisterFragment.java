package com.djackowski.dbconnect;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
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

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Damcios on 2015-12-08.
 */
public class RegisterFragment extends Fragment implements View.OnClickListener {
    //testing from a real server:
    private static final String REGISTER_URL = "http://distancemaster.esy.es/registration.php";
    //ids
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";

    //php login script

    //localhost :
    //testing on your device
    //put your local ip instead,  on windows, run CMD > ipconfig
    //or in mac's terminal type ifconfig and look for the ip under en0 or en1
    // private static final String LOGIN_URL = "http://xxx.xxx.x.x:1234/webservice/register.php";

    //testing on Emulator:
    //private static final String LOGIN_URL = "http://10.0.2.2:1234/webservice/register.php";
    // JSON parser class
    JSONParser jsonParser = new JSONParser();
    private EditText user, pass;
    // Progress Dialog
    private ProgressDialog pDialog;
    private View view;
    private Button button;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_register, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        user = (EditText) getView().findViewById(R.id.editTextLoginRegister);
        pass = (EditText) getView().findViewById(R.id.editTextPasswordRegister);
        button = (Button) getView().findViewById(R.id.buttonRegister);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkEnableOfServices()) {
                    String username = user.getText().toString();
                    String password = pass.getText().toString();
                    new CreateUser().execute(username, password);
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

    @Override
    public void onClick(View v) {


    }

    class CreateUser extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         */
        boolean failure = false;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Creating User...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... args) {
            // TODO Auto-generated method stub
            // Check for success tag
            int success;

            try {
                // Building Parameters
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("username", args[0]));
                params.add(new BasicNameValuePair("password", args[1]));

                Log.d("request!", "starting");

                //Posting user data to script
                Object jsonObject = JSONParser.makeHttpRequest(
                        REGISTER_URL, "POST", params);
                if (jsonObject instanceof JSONObject) {
                    JSONObject json = (JSONObject) jsonObject;

                    // full json response
                    Log.d("Login attempt", json.toString());

                    // json success element
                    success = json.getInt(TAG_SUCCESS);
                    if (success == 1) {
                        Log.d("User Created!", json.toString());
                        return json.getString(TAG_MESSAGE);
                    } else {
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
                Toast.makeText(getActivity(), file_url, Toast.LENGTH_LONG).show();
            }

        }

    }

}
