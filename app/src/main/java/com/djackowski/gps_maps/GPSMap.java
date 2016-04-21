package com.djackowski.gps_maps;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
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
import android.widget.ImageButton;
import android.widget.TextView;

import com.djackowski.R;
import com.djackowski.trenings.FreeRideActivity;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GPSMap extends Fragment implements
        LocationListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, View.OnClickListener {


    private static final String TAG = "LocationActivity";
    private static final long INTERVAL = 100 * 10;//1000 * 10
    private static final long FASTEST_INTERVAL = 100 * 5;//1000 * 5
    boolean isProgressOn = false;
    boolean isMapSelected = true;
    boolean isPositionMoving = true;
    TextView textView;
    ImageButton sateliteMap;
    Button btnFusedLocation;
    TextView tvLocation;
    LocationRequest mLocationRequest;
    GoogleApiClient mGoogleApiClient;
    Location mCurrentLocation;
    String mLastUpdateTime;
    TextView textViewTime;
    Button start, reset;
    GoogleMap map;
    private LatLng lastCoordinate;
    private float speed;
    private ProgressDialog progress;
    private Polyline line;
    private LatLng coordinate;
    private Marker marker;
    private List<LatLng> coordinateList;
    private List<Location> locationList;
    private int countUpdates;
    private List<Float> speeds;
    private float avgSpeed;
    private List<Marker> markerList;
    private Stoper gpsTime;
    private Calories calories;
    private float burnedCalories;
    private float weight;
    private static float distance;
    private View view;
    private boolean isStop;
    private boolean isFirstUpdate = true;
    private int i;
    private int j = 0;

    private OnDistanceReachedListener onDistanceReachedListener;

    private TextView dlugoscSzerokosc, spaloneKalorie, przebytyDystans;
    private Bundle bundle;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return view = inflater.inflate(R.layout.fragment_gps_map_layout, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        bundle = new Bundle();
        Log.d(TAG, "onCreate ...............................");
        //show error dialog if GoolglePlayServices not available
        if (!isGooglePlayServicesAvailable()) {
            getActivity().finish();
        }
        createLocationRequest();
        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();


        sateliteMap = (ImageButton) view.findViewById(R.id.sateliteMap);
        map = ((SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map)).getMap();
        map.setMapType(GoogleMap.MAP_TYPE_TERRAIN);//hybryda
        map.getUiSettings().setZoomControlsEnabled(false);//przyciski zooma
        map.setMyLocationEnabled(false);//layer wlaczony, zeby byl przycisk aktualnej pozycji
        map.getUiSettings().setMyLocationButtonEnabled(true);// przycisk aktualnej pozycji
        markerList = new ArrayList<>();
        coordinateList = new ArrayList<>();
        locationList = new ArrayList<>();
        textViewTime = (TextView) view.findViewById(R.id.textViewTime);
        speeds = new ArrayList<>();
        start = (Button) view.findViewById(R.id.buttonStart);
        reset = (Button) view.findViewById(R.id.buttonReset);

        //dlugoscSzerokosc = (TextView)getActivity().findViewById(R.id.textViewDlugoscSzerokosc);
        spaloneKalorie = (TextView) getActivity().findViewById(R.id.textViewSpaloneKalorie);
        przebytyDystans = (TextView) getActivity().findViewById(R.id.textViewPrzebytyDystans);

        sateliteMap.setOnClickListener(this);
        map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                isPositionMoving = false;
                Log.d("KLIKU: ", "KLIK");
            }
        });

        textView = FreeRideActivity.textViewDetails();
        gpsTime = FreeRideActivity.instanceGpsTime();

        // gpsTime = new Stoper(textViewTime, start, reset, speed, avgSpeed, burnedCalories, distance);
        gpsTime.calculate();

        calories = new Calories();

        weight = 75;

        progressBar();

        //dlugoscSzerokosc.setText("Brak połączenia z GPS'em");
        spaloneKalorie.setText("Brak połączenia z GPS'em");
        przebytyDystans.setText("Brak połączenia z GPS'em");


        checkEnableOfServices();

    }

    public static float getDistance(){
        return distance;
    }

    public void setOnDistanceReachedListener(OnDistanceReachedListener onDistanceReachedListener) {
        this.onDistanceReachedListener = onDistanceReachedListener;
    }

    public interface OnDistanceReachedListener{
        void distanceReached(float distance);
    }

    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    public void progressBar() {
        progress = new ProgressDialog(getActivity());
        progress.setMessage("Wczytywanie...");
        progress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progress.setIndeterminate(false);// w przypadku horizontal false = ladowanie na ekranie
        progress.setProgress(0);
        progress.show();
        isProgressOn = true;

        final int totalProgressTime = 100;
        /*final Thread t = new Thread() {
            @Override
            public void run() {
                int i = 0;
                //while (countUpdates * 20 < totalProgressTime)
                while ()
                {
                    try {
                        sleep(2000);
                        Log.d("MARKER:  ", String.valueOf(countUpdates));
                        while (i < countUpdates * 20 + 1) {
                            sleep(300);
                            progress.setProgress(i);
                            i++;
                        }

                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        };
        t.start();*/


    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart fired ..............");
        mGoogleApiClient.connect();

    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop fired ..............");
        mGoogleApiClient.disconnect();
        Log.d(TAG, "isConnected ...............: " + mGoogleApiClient.isConnected());
    }

    private boolean isGooglePlayServicesAvailable() {
        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getActivity());
        if (ConnectionResult.SUCCESS == status) {
            return true;
        } else {
            GooglePlayServicesUtil.getErrorDialog(status, getActivity(), 0).show();
            return false;
        }
    }

    @Override
    public void onConnected(Bundle bundle) {
        Log.d(TAG, "onConnected - isConnected ...............: " + mGoogleApiClient.isConnected());
        stopLocationUpdates();
        startLocationUpdates();
    }

    protected void startLocationUpdates() {
        PendingResult<Status> pendingResult = LocationServices.FusedLocationApi.requestLocationUpdates(
                mGoogleApiClient, mLocationRequest, this);
        Log.d(TAG, "Location update started ..............: ");
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.d(TAG, "Connection failed: " + connectionResult.toString());
    }

    public void progressMarker(LatLng coordinate) {

        if (isProgressOn) {
            isProgressOn = false;
            progress.dismiss();
        }

        if (marker != null)  //jezeli istnieje juz marker
            marker.remove();
        marker = map.addMarker(new MarkerOptions()
                .position(coordinate)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.position)));
        if (isPositionMoving)
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(coordinate, 17)); //to wysrodku kamere na aktualna pozycje

        if (gpsTime.isStartClicked()) { //po nacisnieciu przycisku start
            coordinateList.add(coordinate);
            line = map.addPolyline(new PolylineOptions()
                    .add(coordinateList.get(i), coordinateList.get(i + 1))
                    .width(25)
                    .color(Color.YELLOW));
            i++;
        }

        lastCoordinate = coordinate;
    }

    public float averageSpeed() {
        float avSpeed = 0;
        for (float speed : speeds) {
            avSpeed += speed;
        }
        return (avSpeed / speeds.size());
    }

    public void checkEnableOfServices() {
        // Get Location Manager and check for GPS & Network location services
        LocationManager lm = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        if (!lm.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            // Build the alert dialog
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("GPS jest nieaktywny!");
            builder.setMessage("Proszę włączyć lokalizację");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogInterface, int i) {
                    // Show location settings when the user acknowledges the alert dialog
                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(intent);
                }
            });
            Dialog alertDialog = builder.create();
            alertDialog.setCanceledOnTouchOutside(false);
            alertDialog.show();
        }

    }
SharedPreferences sharedPreferences;
    @Override
    public void onLocationChanged(Location location) {
        Log.d(TAG, "Firing onLocationChanged..............................................");
        mCurrentLocation = location;
        mLastUpdateTime = DateFormat.getTimeInstance().format(new Date());
        double lat = (location.getLatitude());
        double lng = (location.getLongitude());
        coordinate = new LatLng(lat, lng);

        locationList.add(location);

        isStop = gpsTime.isStopClicked();

        if (isFirstUpdate) {
            coordinateList.add(coordinate);
            progressMarker(coordinate);
            isFirstUpdate = false;
        }
        if (countUpdates > 0) { //ilosc odswiezen lokacji wieksza od 0(kilka pierwszych odstaje od normy)
            if (gpsTime.isStartClicked()) {
                distance += locationList.get(j).distanceTo(locationList.get(j + 1));
                j++;
                speed = location.getSpeed() * 3.6f;//zamiana m/s na km/h
                speeds.add(speed);
                avgSpeed = averageSpeed();
                burnedCalories = calories.Calculate(weight, distance, isStop);
            } else if (isStop) {
               // onDistanceReachedListener.distanceReached(distance);
                sharedPreferences = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putFloat("distance", distance);
                editor.apply();
                burnedCalories = calories.Calculate(weight, distance, isStop);
                distance = 0;
            }
            //if(gpsTime.isStopClicked()) {
            // speed = gpsTime.getSpeed();
            // avgSpeed = gpsTime.getAverageSpeed();
            //burnedCalories = gpsTime.getCalories();
            //   burnedCalories = 0;
            // distance = gpsTime.getDistance();
            //  }
            progressMarker(coordinate);
        }

        /*textView.setText("Szerokość geogr.: " + lat + "\n" + "Długość geogr.: " + lng + "\n" +
                        "Przebyty dystans: " + distance + "\n"
                        + "Spalone kalorie: " + burnedCalories + "\n"
        );*/
        // dlugoscSzerokosc.setText(lat + "  " + lng);
        spaloneKalorie.setText(String.format("%.2f", burnedCalories));
        przebytyDystans.setText(String.format("%4f", distance / 1000) + "/" + gpsTime.getDistanceTime());
        Log.d("Wywolanie***", "Wywolanie***");
        countUpdates++;
    }


    protected void stopLocationUpdates() {
        LocationServices.FusedLocationApi.removeLocationUpdates(
                mGoogleApiClient, this);
        Log.d(TAG, "Location update stopped .......................");
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mGoogleApiClient.isConnected()) {
            //startLocationUpdates();
            Log.d(TAG, "Location update resumed .....................");
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sateliteMap:
                if (isMapSelected) {
                    map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                    sateliteMap.setImageResource(R.drawable.map);
                    isMapSelected = false;
                } else {
                    map.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                    sateliteMap.setImageResource(R.drawable.satellite_icon);
                    isMapSelected = true;
                }


                break;
            case R.id.positionButton:
                isPositionMoving = true;
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(lastCoordinate, 17));
                Log.d("Wywolanie: ", "Przycisk POSITION");
                break;
            default:
                break;
        }
    }
}