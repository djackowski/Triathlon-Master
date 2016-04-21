package com.djackowski.statistics;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.djackowski.R;
import com.numetriclabz.numandroidcharts.BarChart;
import com.numetriclabz.numandroidcharts.ChartData;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by Damcios on 2015-12-11.
 */
public class Statistics extends Fragment implements ITaskEndHelper {
    private static final String RETRIEVE_URL = "http://distancemaster.esy.es/retrieve_score.php";
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    BarChart barChart;
    List<ChartData> values = new ArrayList<>();
    private HashMap<String, HashMap<String, String>> collection;
    private HashMap<String, String> particularScore;
    private List<String> list = new ArrayList<>();
    private String username;
    private String urlJsonObj = "http://distancemaster.esy.es/retrieve_score.php";
    private ITaskEndHelper listener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_statistics, container, false);
    }

    public void setListener(ITaskEndHelper listener) {
        this.listener = listener;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SharedPreferences sp;
        sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
        username = sp.getString("username", null);
        barChart = (BarChart) view.findViewById(R.id.barchart);
        setListener(this);


        try {
            new PostAsync().execute(username).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        String bieg, kolarstwo, plywanie;
        if (particularScore.containsKey("BIEG")) {
            bieg = particularScore.get("BIEG");
        } else {
            bieg = "0";
        }
        if (particularScore.containsKey("KOLARSTWO")) {
            kolarstwo = particularScore.get("KOLARSTWO");
        } else {
            kolarstwo = "0";
        }
        if (particularScore.containsKey("PLYWANIE")) {
            plywanie = particularScore.get("PLYWANIE");
        } else {
            plywanie = "0";
        }

        if (bieg != null) {
            values.add(new ChartData(Float.parseFloat(bieg), "BIEG"));
        }
        if (kolarstwo != null) {
            values.add(new ChartData(Float.parseFloat(kolarstwo), "KOLARSTWO"));
        }
        if (plywanie != null) {
            values.add(new ChartData(Float.parseFloat(plywanie), "PłYWANIE"));
        }
        barChart.setData(values);


    }

    @Override
    public void onEndASyncTaskListener() {


    }


    class PostAsync extends AsyncTask<String, String, JSONArray> {
        private static final String LOGIN_URL = "http://distancemaster.esy.es/retrieve_score.php";
        private ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Attempting retrieve score...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected JSONArray doInBackground(String... args) {

            collection = new HashMap<>();
            particularScore = new HashMap<>();
            try {
                List<NameValuePair> params = new ArrayList<>();
                params.add(new BasicNameValuePair("username", args[0]));
                String a, b, c;

                Object jsonObject = com.djackowski.dbconnect.JSONParser.makeHttpRequest(
                        LOGIN_URL, "POST", params);
                if (jsonObject instanceof JSONArray) {
                    JSONArray json = (JSONArray) jsonObject;
                    for (int i = 0; i < json.length(); i++) {//for arrays
                        try {
                            a = json.getJSONObject(i).getString("messageLevel");
                        } catch (NullPointerException e) {
                            e.printStackTrace();
                            a = "0";
                        }
                        try {
                            b = json.getJSONObject(i).getString("messageCompetition");
                        } catch (NullPointerException e) {
                            e.printStackTrace();
                            b = "0";
                        }
                        try {
                            c = json.getJSONObject(i).getString("messageScore");
                        } catch (NullPointerException e) {
                            e.printStackTrace();
                            c = "0";
                        }

                        particularScore.put(b, c);
                        collection.put(a, particularScore);

                    }


                    return json;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        protected void onPostExecute(JSONArray json) {
            if (pDialog != null && pDialog.isShowing()) {
                pDialog.dismiss();
            }
            // String bieg = particularScore.get("BIEG");

        }

    }


}
