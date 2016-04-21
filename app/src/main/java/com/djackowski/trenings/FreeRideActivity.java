package com.djackowski.trenings;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.djackowski.R;
import com.djackowski.dbconnect.JSONParser;
import com.djackowski.dbconnect.LoginFragment;
import com.djackowski.gps_maps.GPSMap;
import com.djackowski.gps_maps.Stoper;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class FreeRideActivity extends AppCompatActivity {
    //testing from a real server:
    private static final String LOGIN_URL = "http://distancemaster.esy.es/send_score.php";
    //JSON element ids from repsonse of php script:
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    private static TextView details;
    private static Stoper gpsTime;
    private static boolean isActive = false;
    private Bundle extras;
    private Button start, stop;
    private TextView time, distanceTimeTV, isDone;
    private float speed, averageSpeed, calories, distance;
    private long distanceTarget;
    private Toolbar toolbar;
    private GPSMap gpsMap;
    private SharedPreferences sharedPreferences;
    private String username;
    private String level;
    private String competition;
    private ProgressDialog pDialog;
    private JSONParser jsonParser;

    public static Stoper instanceGpsTime() {
        return gpsTime;
    }

    public static TextView textViewDetails() {
        return details;
    }

    private static boolean isActivityActive() {
        return isActive;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_freeride_layout);
        isActive = true;
        gpsMap = new GPSMap();
        start = (Button) findViewById(R.id.buttonStartFromButtons);
        stop = (Button) findViewById(R.id.buttonStopFromButtons);
        time = (TextView) findViewById(R.id.textViewTimeFromButtons);
        //details = (TextView) findViewById(R.id.textViewDetails);
        //distanceTimeTV = (TextView) findViewById(R.id.textViewDistance);
        //isDone = (TextView) findViewById(R.id.textViewIsDoneYet);
        extras = getIntent().getExtras();
        distanceTarget = extras.getLong("DYSTANS");
        gpsTime = new Stoper(time, start, stop, speed, averageSpeed, calories, distance, distanceTarget);
        //distanceTimeTV.setText(String.valueOf(distanceTime));
        pDialog = new ProgressDialog(this);
        jsonParser = new JSONParser();
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        toolbar = (Toolbar) findViewById(R.id.include);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        if (LoginFragment.isLoggedIn()) {
            level = sharedPreferences.getString("level", "");
            // level = extras.getString("level", "");
            username = sharedPreferences.getString("username", "niezalogowany");
        } else {
            username = "niezalogowany";
        }
        competition = extras.getString("COMPETITION", "");
        gpsTime.setListener(new Listener() {
            @Override
            public void onListen(float dst) {

                Toast.makeText(getApplicationContext(), "Username: " + username + "Distance: "
                        + String.valueOf(dst) + "Level: " + level + "Zawody: " + competition, Toast.LENGTH_SHORT).show();
                new SendScoreToDataBase().execute(username, level, competition, String.valueOf(dst));
            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStop() {
        super.onStop();
        isActive = false;
    }

    class SendScoreToDataBase extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         */

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @SuppressLint("LongLogTag")
        @Override
        protected String doInBackground(String... args) {//zwraca wiadomosc, ktora odczytujemy w onPostExecute w parametrze
            // Check for success tag
            int success;

            try {

                // Building Parameters
                List<NameValuePair> params = new ArrayList<>();
                params.add(new BasicNameValuePair("username", args[0]));
                params.add(new BasicNameValuePair("level", args[1]));
                params.add(new BasicNameValuePair("competition", args[2]));
                params.add(new BasicNameValuePair("score", args[3]));

                Log.d("request!", "starting");
                // getting product details by making HTTP request

                Object jsonObject = JSONParser.makeHttpRequest(LOGIN_URL, "POST", params);
                if (jsonObject instanceof JSONObject) {
                    JSONObject json = (JSONObject) jsonObject;
                    // check your log for json response
                    Log.d("Sending score to DB attempt", json.toString());

                    // json success tag
                    success = json.getInt(TAG_SUCCESS);
                    if (success == 1) {
                        Log.d("Score sent do DB", json.toString());
                        return json.getString(TAG_MESSAGE);
                    } else {
                        //isLoggedIn = false;
                        Log.d("Score sending failed!", json.getString(TAG_MESSAGE));
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
                Toast.makeText(getApplicationContext(), file_url, Toast.LENGTH_LONG).show();
            }

        }

    }


}
