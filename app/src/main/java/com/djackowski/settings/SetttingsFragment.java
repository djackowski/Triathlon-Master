package com.djackowski.settings;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.djackowski.R;
import com.djackowski.dbconnect.JSONParser;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Damcios on 2016-02-15.
 */
public class SetttingsFragment extends Fragment {


    private static final String REGISTER_URL = "http://distancemaster.esy.es/change.php";
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    private static int RESULT_LOAD_IMAGE = 1;
    private static int CHANGE_BACKGROUND = 2;
    private View view;
    private Button changeAvatar, changeBackground;
    //private CircleImageView imageView;
    private ImageView imageView;
    private String picturePath;
    private int number;
    private boolean avatarChanged = false;
    private boolean backgroundChanged = false;
    private ProgressDialog pDialog;
    private JSONParser jsonParser = new JSONParser();
    private SharedPreferences sp;


    private EditText pass;

    private String username;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return view = inflater.inflate(R.layout.fragment_settings_navigation_drawer, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        changeAvatar = (Button) getActivity().findViewById(R.id.buttonChangeAvatar);
        changeBackground = (Button) getActivity().findViewById(R.id.buttonChangeBackground);

        pass = (EditText) getActivity().findViewById(R.id.editTextNewPassword);

        changeAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                avatarChanged = true;

                startActivityForResult(i, RESULT_LOAD_IMAGE);


            }
        });

        changeBackground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                backgroundChanged = true;

                startActivityForResult(i, CHANGE_BACKGROUND);

            }
        });


        sp = PreferenceManager.getDefaultSharedPreferences(getActivity());//this
        username = sp.getString("username", null);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            getActivity().finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == Activity.RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getActivity().getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            picturePath = cursor.getString(columnIndex);
            cursor.close();


            // imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
            number = 1; //avatar zmiana
            pathOfPicture(picturePath, number);

        } else if (requestCode == CHANGE_BACKGROUND && resultCode == Activity.RESULT_OK && null != data) {

            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getActivity().getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            picturePath = cursor.getString(columnIndex);
            cursor.close();

            number = 2; //background zmiana
            // imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));

            pathOfPicture(picturePath, number);

        }
    }

    public void pathOfPicture(String picturePath, int number) {
        Intent i = new Intent();
        i.putExtra("PATH", picturePath);
        i.putExtra("NUMBER", number);
        getActivity().setResult(Activity.RESULT_OK, i);
        //startActivity(i);
    }

    public void onClick(View view) {
        String user = username;
        String password = pass.getText().toString();
        new ChangePassword().execute(username, password);
    }

    class ChangePassword extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         */
        boolean failure = false;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Zmiana hasła...");
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
                        Log.d("Password changed!", json.toString());
                        return json.getString(TAG_MESSAGE);
                    } else {
                        Log.d("Failure!", json.getString(TAG_MESSAGE));
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
                Toast.makeText(getActivity().getApplicationContext(), file_url, Toast.LENGTH_LONG).show();
            }

        }

    }
}
