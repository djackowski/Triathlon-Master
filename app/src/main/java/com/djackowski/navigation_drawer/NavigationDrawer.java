package com.djackowski.navigation_drawer;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.djackowski.R;
import com.djackowski.dbconnect.JSONParser;
import com.djackowski.dbconnect.LoginFragment;
import com.djackowski.dbconnect.LogoutFragment;
import com.djackowski.dbconnect.RegisterFragment;
import com.djackowski.gps_maps.Start;
import com.djackowski.others.Infromation.Diet.DietFragment;
import com.djackowski.others.Infromation.InformationFragment;
import com.djackowski.others.Infromation.StartDay.StartDayFragment;
import com.djackowski.settings.Settings;
import com.djackowski.statistics.Statistics;
import com.djackowski.trenings.TrainingChooseLvl;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class NavigationDrawer extends AppCompatActivity {

    private static final String REGISTER_URL = "http://distancemaster.esy.es/send_image.php";
    private static final String RETRIEVE_URL = "http://distancemaster.esy.es/retrieve_image.php";


    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    private static final String TAG_MESSAGEAVATAR = "messageAvatar";
    private static final String TAG_MESSAGEBACKGROUND = "messageBackground";
    private static NavigationView nvDrawer, addItemsNv;
    private DrawerLayout mDrawer;
    private Toolbar toolbar;
    private ActionBarDrawerToggle drawerToggle;

    private SharedPreferences sp;
    private String username = "";

    private TextView textView;
    private boolean isLoggedIn;
    private boolean isLoggedOut;
    private boolean logoutStartActivity = false;

    private FragmentManager fragmentManager;
    private Fragment fragment;
    private Class fragmentClass;

    private CircleImageView avatar;

    private View header;

    private ImageView headerBackground;

    private ProgressDialog pDialog;
    private JSONParser jsonParser = new JSONParser();

    private String urlFromDB;

    private boolean isSendImageOn = false;
    private boolean isRetrieveImageOn = false;
    private boolean clickedChangeAvatar = false;
    private boolean clickedChangeBackground = false;
    private boolean isRetrieveAvatar = false;
    private boolean isRetrieveBackground = false;

    private String urlBackground;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation_drawer_layout);

        // Set a Toolbar to replace the ActionBar.
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Find our drawer view
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        // drawerToggle = setupDrawerToggle();// zmiany
        drawerToggle = new ActionBarDrawerToggle(this, mDrawer, toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);

        // Find our drawer view
        nvDrawer = (NavigationView) findViewById(R.id.nvView);
        // Setup drawer view


        // isLoggedIn = new LoginFragment().isLoggedIn();
        isLoggedIn = LoginFragment.isLoggedIn();
        isLoggedOut = LogoutFragment.isLoggedOut();
        //isLoggedOut = new LogoutFragment().isLoggedOut();

        setLoginBoolean();
        setupDrawerContent(nvDrawer);

        if (isLoggedIn) {
            sp = PreferenceManager.getDefaultSharedPreferences(this);
            username = sp.getString("username", null);
        } else {
            username = "";
        }


        // username = "damcios";
        //setHeader();
        // if(isLoggedOut){ //otrzymywanie informacji z poprzedniego activity
        //Bundle b = getIntent().getExtras();
        //isLoggedIn = b.getBoolean("isLoggedIn");
        //isLoggedIn = false;
        //  }


        try {


            if (LoginFragment.userLoggedIn())
                fragmentClass = Start.class;
            else
                fragmentClass = LoginFragment.class;
            fragment = (Fragment) fragmentClass.newInstance();
            fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        header = nvDrawer.inflateHeaderView(R.layout.navigation_drawer_header);
        //header.findViewById(R.id.headerTV);
        textView = (TextView) header.findViewById(R.id.headerTV);
        headerBackground = (ImageView) header.findViewById(R.id.headerBackground);
        avatar = (CircleImageView) header.findViewById(R.id.headerAvatar);
        //textView.setText("Niezalogowany");
        setHeader();
        setImages();


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    private void sendToDB(String base64) {
        new ImagesInBackground().execute(username, base64);
    }

    private String bitmapToBase64(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 10, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }

    private Bitmap base64ToBitmap(String b64) {
        byte[] imageAsBytes = Base64.decode(b64.getBytes(), Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);
    }


    private void setImages() {
        if (isLoggedIn) {
            isRetrieveImageOn = true;
            if (!clickedChangeAvatar) {
                isRetrieveAvatar = true;

            }
            if (!clickedChangeBackground) {
                isRetrieveBackground = true;
            }
            new ImagesInBackground().execute(username);
        }
    }

    private void changeAvatar(String picturePath) {
        Bitmap bitmap = BitmapFactory.decodeFile(picturePath);
        //avatar.setImageBitmap(bitmap);
        avatar.setImageBitmap(Bitmap.createScaledBitmap(bitmap, 512, 512, false));
        // + wyslij na serwer avatar z przypisanym do niego nickiem

        String decodedAvatar = bitmapToBase64(Bitmap.createScaledBitmap(bitmap, 512, 512, false));


        // avatar.setImageBitmap(base64ToBitmap(decodedAvatar));
        clickedChangeAvatar = true;
        sendToDB(decodedAvatar);//wysyla na serwer nie poprawiona wersje bitmapy(oryginal, a nie 120x120)


    }

    private void changeBackground(String picturePath) {
        Bitmap bitmap = BitmapFactory.decodeFile(picturePath);
        headerBackground.setImageBitmap(Bitmap.createScaledBitmap(bitmap, 1280, 800, false));
        // + wyslij na serwer tlo z przypisanym do niego nickiem
        String decodedBackground = bitmapToBase64(Bitmap.createScaledBitmap(bitmap, 1280, 800, false));
        clickedChangeBackground = true;
        sendToDB(decodedBackground);
        // clickedChangeBackground = false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case (1):
                if (resultCode == Activity.RESULT_OK) {
                    String newText = data.getStringExtra("PATH");
                    int number = data.getIntExtra("NUMBER", 0);
                    if (number == 1)
                        changeAvatar(newText);
                    else if (number == 2)
                        changeBackground(newText);
                }
                break;
            default:
                break;
        }
    }

    private String getImagePath() {
        String newString;
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            newString = extras.getString("PATH");
            return newString;
        } else {
            return null;

        }
    }



   /* @Override
    protected void onPostResume() {
        super.onPostResume();
        Log.d("WZNAWIAM DZIALANIE", "WZNAWIAM DZIALANIE");
        String path;

        if(getImagePath()== null)
            path = "";
        else{
            path = getImagePath();
            changeAvatar(path);
        }



    }*/

    private void setLoginBoolean() {


    }

    private void setHeader() {
        if (isLoggedIn) {
            textView.setText(username);
        } else {
            textView.setText("Niezalogowany");
        }

    }

    private ActionBarDrawerToggle setupDrawerToggle() {


        return new ActionBarDrawerToggle(this, mDrawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
    }

    private void setupMenuItemsVisible(NavigationView navigationView) {
        if (!isLoggedIn) {
            navigationView.getMenu().findItem(R.id.login_fragment).setVisible(true);
            navigationView.getMenu().findItem(R.id.register_fragment).setVisible(true);
            navigationView.getMenu().findItem(R.id.gps_fragment).setVisible(false);
            navigationView.getMenu().findItem(R.id.logout_fragment).setVisible(false);
            navigationView.getMenu().findItem(R.id.navigation_drawer_settings).setVisible(false);
            navigationView.getMenu().findItem(R.id.navigation_drawer_exit).setVisible(true);
            navigationView.getMenu().findItem(R.id.dieta).setVisible(true);
            navigationView.getMenu().findItem(R.id.dzienzawodow).setVisible(true);
            navigationView.getMenu().findItem(R.id.statystyki).setVisible(false);
            navigationView.getMenu().findItem(R.id.informacje).setVisible(true);
            navigationView.getMenu().findItem(R.id.treningi).setVisible(true);
        } else {
            navigationView.getMenu().findItem(R.id.login_fragment).setVisible(false);
            navigationView.getMenu().findItem(R.id.register_fragment).setVisible(false);
            navigationView.getMenu().findItem(R.id.gps_fragment).setVisible(false);
            navigationView.getMenu().findItem(R.id.logout_fragment).setVisible(true);
            navigationView.getMenu().findItem(R.id.navigation_drawer_settings).setVisible(true);
            navigationView.getMenu().findItem(R.id.navigation_drawer_exit).setVisible(true);
            navigationView.getMenu().findItem(R.id.statystyki).setVisible(true);
            navigationView.getMenu().findItem(R.id.dieta).setVisible(true);
            navigationView.getMenu().findItem(R.id.dzienzawodow).setVisible(true);
            navigationView.getMenu().findItem(R.id.informacje).setVisible(true);
            navigationView.getMenu().findItem(R.id.treningi).setVisible(true);
        }


    }
   /* public void removeMenuItems(NavigationView navigationView){ // przechodzimy na ustawianie itemow w .xml i ustawianie visible
        final Menu menu = navigationView.getMenu();
        if(!isLoggedIn){
            menu.add("Zaloguj");
            menu.add("Rejestracja ");
        }
        else{
            menu.add("GPS ");
        }
    }*/
//   public void addItemsRunTime(NavigationView navigationView) { // przechodzimy na ustawianie itemow w .xml i ustawianie visible
//
//        //adding items run time
//        final Menu menu = navigationView.getMenu();
//       /* for (int i = 1; i <= 3; i++) {
//            menu.add("Runtime item "+ i);
//        }*/
//       if(!isLoggedIn){
//           menu.add("Zaloguj");
//           menu.add("Rejestracja ");
//       }
//       else{
//           menu.add("GPS ");
//       }
//
//        // adding a section and items into it
//        /*final SubMenu subMenu = menu.addSubMenu("SubMenu Title");
//        for (int i = 1; i <= 2; i++) {
//            subMenu.add("SubMenu Item " + i);
//        }*/
//
//        // refreshing navigation drawer adapter
//        for (int i = 0, count = nvDrawer.getChildCount(); i < count; i++) {
//            final View child = nvDrawer.getChildAt(i);
//            if (child != null && child instanceof ListView) {
//                final ListView menuView = (ListView) child;
//                final HeaderViewListAdapter adapter = (HeaderViewListAdapter) menuView.getAdapter();
//                final BaseAdapter wrapped = (BaseAdapter) adapter.getWrappedAdapter();
//                wrapped.notifyDataSetChanged();
//            }
//        }
//    }

    private void setupDrawerContent(final NavigationView navigationView) {

        setupMenuItemsVisible(navigationView);
        //addItemsRunTime(navigationView);

        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        selectDrawerItem(menuItem);

                        return true;
                    }
                });
    }

    public void selectDrawerItem(MenuItem menuItem) {
        // Create a new fragment and specify the planet to show based on
        // position
        fragment = null;

        fragmentClass = Start.class;
        switch (menuItem.getItemId()) {
            case R.id.login_fragment:
                fragmentClass = LoginFragment.class;
                //startActivity(new Intent("android.intent.action.TEST"));
                break;
            case R.id.register_fragment:
                fragmentClass = RegisterFragment.class;
                break;
            case R.id.logout_fragment:
                logoutStartActivity = true;
                fragmentClass = LogoutFragment.class;
                break;
            case R.id.navigation_drawer_settings:
                Intent intent = new Intent(this, Settings.class);
                startActivityForResult(intent, 1);
                // fragmentClass = SetttingsFragment.class;
                break;
            case R.id.dzienzawodow:
                fragmentClass = StartDayFragment.class;
                break;
            case R.id.dieta:
                fragmentClass = DietFragment.class;
                break;
            case R.id.statystyki:
                fragmentClass = Statistics.class;
                break;
            case R.id.informacje:
                fragmentClass = InformationFragment.class;
                break;
            case R.id.treningi:
                fragmentClass = TrainingChooseLvl.class;
                break;
            case R.id.navigation_drawer_exit:
                finish();
                break;
            default:
                fragmentClass = LoginFragment.class;
                break;
        }

        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Insert the fragment by replacing any existing fragment
        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();

        // Highlight the selected item, update the title, and close the drawer
        menuItem.setChecked(true);
        setTitle(menuItem.getTitle());
        mDrawer.closeDrawers();
        if (logoutStartActivity) {//przeladuj navigation drawer, aby odswiezyc go
            // test = 5;
            // isLoggedIn = false;
            // getIntent().putExtra("isLoggedIn", isLoggedIn); //zapisywanie informacji do nastepnego activity
            LoginFragment.setIsLoggedIn(false);
            finish();
            startActivity(getIntent());
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // The action bar home/up action should open or close the drawer.
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawer.openDrawer(GravityCompat.START);
                return true;
        }
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    // Make sure this is the method with just `Bundle` as the signature
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        drawerToggle.syncState();

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggles
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onStop() {
        super.onStop();

    }

    class ImagesInBackground extends AsyncTask<String, String, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pDialog = new ProgressDialog(NavigationDrawer.this);
            pDialog.setMessage("Wczytywanie...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... args) {
            // TODO Auto-generated method stub
            // Check for success tag
            int success, success2;

            try {
                // Building Parameters
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                if (isRetrieveImageOn) {
                    params.add(new BasicNameValuePair("username", args[0]));
                    Object jsonObjectFirst = JSONParser.makeHttpRequest(
                            RETRIEVE_URL, "POST", params);
                    Object jsonObjectSecond = JSONParser.makeHttpRequest(
                            RETRIEVE_URL, "POST", params);
                    if (jsonObjectFirst instanceof JSONObject && jsonObjectSecond instanceof JSONObject) {
                        JSONObject json = (JSONObject) jsonObjectFirst;
                        JSONObject json2 = (JSONObject) jsonObjectSecond;
                        // json success element
                        success = json.getInt(TAG_SUCCESS);
                        //success2 = json2.getInt(TAG_SUCCESS);

                        if (success == 1) {
                            Log.d("Retrieved image!", json.toString());
                            urlBackground = json2.getString(TAG_MESSAGEBACKGROUND);
                            return json.getString(TAG_MESSAGEAVATAR);
                        } else {
                            Log.d("Failure!", json.getString(TAG_MESSAGEBACKGROUND));
                            return json.getString(TAG_MESSAGEBACKGROUND);

                        }
                    }
                } else {
                    if (clickedChangeAvatar) {//avatar
                        params.add(new BasicNameValuePair("username", args[0]));
                        params.add(new BasicNameValuePair("avatar", args[1]));
                        clickedChangeAvatar = false;

                    } else if (clickedChangeBackground) {//background
                        params.add(new BasicNameValuePair("username", args[0]));
                        params.add(new BasicNameValuePair("background", args[1]));
                        clickedChangeBackground = false;
                    }

                    //Posting user data to script
                    Object jsonObject = JSONParser.makeHttpRequest(
                            REGISTER_URL, "POST", params);
                    if(jsonObject instanceof JSONObject) {
                        JSONObject json = (JSONObject) jsonObject;

                        // json success element
                        success = json.getInt(TAG_SUCCESS);
                        if (success == 1) {
                            Log.d("Image sent!", json.toString());

                            return json.getString(TAG_MESSAGE);
                        } else {
                            Log.d("Failure!", json.getString(TAG_MESSAGE));
                            return json.getString(TAG_MESSAGE);

                        }
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
                //Toast.makeText(getApplicationContext(), file_url, Toast.LENGTH_LONG).show();
                if (isRetrieveImageOn) {

                    if (urlBackground.equals("")) {
                        headerBackground.setImageResource(R.drawable.orange);
                        // headerBackground.setImageBitmap(Bitmap.createScaledBitmap(bitmap, 512, 512, false));
                    } else {
                        headerBackground.setImageBitmap(base64ToBitmap(urlBackground));

                    }


                    if (file_url.equals("")) {
                        avatar.setImageResource(R.drawable.avatar);
                    } else {
                        avatar.setImageBitmap(base64ToBitmap(file_url));

                    }
                    isRetrieveBackground = false;
                    isRetrieveAvatar = false;

                    isRetrieveImageOn = false;
                }

            }

        }

    }


}
