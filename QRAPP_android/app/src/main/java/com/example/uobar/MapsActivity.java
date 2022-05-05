package com.example.uobar;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.location.Location;
import android.location.LocationListener;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.example.uobar.databinding.ActivityMapsBinding;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, LocationListener {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    Marker usrLoc;
    String curLoc = "unknown";
    private ImageButton QRScanBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        QRScanBtn = findViewById(R.id.QRScanBtn);
        QRScanBtn.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), QRScanner.class)));
        Bundle extras = getIntent().getExtras();
        curLoc = extras.getString("area");
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     *put this in your local.properties file -> MAPS_API_KEY=AIzaSyALn7mJzUOAypvhHCOXV8GxznKFejixPdI
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(false);
        }


        mMap.setMinZoomPreference(18.0f);
        if (curLoc.equals("Goldeney")) {
            // Add a marker in Goldeney and move the camera
            LatLng Goldeney = new LatLng(51.45249, -2.61391);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(Goldeney));
            //restrict location to Goldeney
            LatLngBounds goldeneyBounds = new LatLngBounds(
                    new LatLng(51.45134, -2.61484), // SW bounds
                    new LatLng(51.45303, -2.61340)  // NE bounds
            );
            mMap.setLatLngBoundsForCameraTarget(goldeneyBounds);
            new JsonTask().execute("https://api.jsonbin.io/b/6272657d019db4679695bacb");


        } else if (curLoc.equals("RylFort")){
            LatLng RylFort = new LatLng(51.45817, -2.60172);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(RylFort));
            LatLngBounds rylFortBounds = new LatLngBounds(
                    new LatLng(51.45699, -2.60283),
                    new LatLng(51.45930, -2.60089)
            );
            mMap.setLatLngBoundsForCameraTarget(rylFortBounds);
            new JsonTask().execute("https://api.jsonbin.io/b/6272657d019db4679695bacb");


        } else{
            LatLng err = new LatLng(0, 0);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(err));
            Toast.makeText(getApplicationContext(),"Error fetching location", Toast.LENGTH_LONG).show();
        }


        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(@NonNull Marker marker) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse((String)marker.getTag()));
                startActivity(intent);
                return true;
            }
        });
    }

    public void populateMarkers(String location, JSONObject mkrsObj){

        //https://api.jsonbin.io/b/6272657d019db4679695bacb
        Toast.makeText(getApplicationContext(),mkrsObj.toString(), Toast.LENGTH_LONG).show();
        try {
            JSONArray jsonArry = mkrsObj.getJSONArray(location);
            for(int i=0;i<jsonArry.length();i++){
                JSONObject mkrobj = jsonArry.getJSONObject(i);
                Ion.with(this).load(mkrobj.getString("imagelink")).withBitmap().asBitmap()
                        .setCallback((e, result) -> {
                            Display display = getWindowManager().getDefaultDisplay();
                            Point size = new Point();
                            display.getSize(size);
                            int width = size.x;
                            int height = size.y;
                            //Toast.makeText(getApplicationContext(),"width : " + width + " height : " + height, Toast.LENGTH_LONG).show();
                            Bitmap bMap;
                            if (result != null){
                                bMap = addBorder(Bitmap.createScaledBitmap(result, width/9, height/22, false));
                            }else{
                                bMap = addBorder(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.defaultimg), height/20, width/20, false));
                            }
                            try {
                                mMap.addMarker(new MarkerOptions().position(new LatLng(mkrobj.getDouble("latitude"), mkrobj.getDouble("longitude"))).icon(BitmapDescriptorFactory.fromBitmap(bMap)).title(mkrobj.getString("title"))).setTag(mkrobj.getString("weblink"));
                            } catch (JSONException jsonException) {
                                jsonException.printStackTrace();
                            }
                        });
            }
        } catch (JSONException e) {
            Log.e("JsonParser Example","unexpected JSON exception", e);
        }
    }

    private class JsonTask extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();


                InputStream stream = connection.getInputStream();

                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();
                String line = "";
                JSONObject mkrsObj = new JSONObject(loadJSONFromAsset());
                while ((line = reader.readLine()) != null) {
                    buffer.append(line+"\n");
                    if (line.contains("rylmarkers")){
                        mkrsObj = new JSONObject(line);
                    }
                }

                if (curLoc.equals("Goldeney")){
                    populateMarkers("gldmarkers", mkrsObj);
                }else if (curLoc.equals("RylFort")){
                    populateMarkers("rylmarkers", mkrsObj);
                }else{
                    Toast.makeText(getApplicationContext(),"location error", Toast.LENGTH_LONG).show();
                }

                return buffer.toString();


            } catch (JSONException | IOException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }
    }

    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getAssets().open("markers.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    private Bitmap addBorder(Bitmap bMap) {
        int padding = 5;
        Bitmap bMapBorder = Bitmap.createBitmap(bMap.getWidth() + padding * 2, bMap.getHeight() + padding * 2, bMap.getConfig());
        Canvas canvas = new Canvas(bMapBorder);
        canvas.drawColor(Color.WHITE);
        canvas.drawBitmap(bMap, padding, padding, null);
        return bMapBorder;
    }

    @Override
    public void onLocationChanged(@NonNull Location location){
        if (usrLoc != null) {
            usrLoc.remove();
        }
        Toast.makeText(getApplicationContext(),"lat: " + location.getLatitude() + " long: " + location.getLongitude(), Toast.LENGTH_LONG).show();
        usrLoc = mMap.addMarker(new MarkerOptions().position(new LatLng(location.getLatitude(), location.getLongitude())));
    }
}