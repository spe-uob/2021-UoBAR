package com.example.uobar;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.uobar.databinding.ActivityMapsBinding;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);

        //temporary variable that has your location will get this value based on gps/user input later
        String curLoc = "RylFort";

        mMap.setMinZoomPreference(18.0f);
        if (curLoc.equals("Goldeney")) {
            // Add a marker in Goldeney and move the camera
            LatLng Goldeney = new LatLng(51.45249, -2.61391);
            mMap.addMarker(new MarkerOptions().position(Goldeney).title("Marker to Goldeney"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(Goldeney));
            //restrict location to Goldeney
            LatLngBounds goldeneyBounds = new LatLngBounds(
                    new LatLng(51.45134, -2.61484), // SW bounds
                    new LatLng(51.45303, -2.61340)  // NE bounds
            );
            mMap.setLatLngBoundsForCameraTarget(goldeneyBounds);


        } else if (curLoc.equals("RylFort")){
            LatLng RylFort = new LatLng(51.45817, -2.60172);
            mMap.addMarker(new MarkerOptions().position(RylFort).title("Marker to Royal Fort"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(RylFort));
            LatLngBounds rylFortBounds = new LatLngBounds(
                    new LatLng(51.45699, -2.60283),
                    new LatLng(51.45930, -2.60089)
            );
            mMap.setLatLngBoundsForCameraTarget(rylFortBounds);

            //temp markers for art
            mMap.addMarker(new MarkerOptions().position(new LatLng(51.45753891274606, -2.6025778327837195)).title("'Follow Me' - Jeppe Hein"));
        } else{
            Toast.makeText(getApplicationContext(),"Error fetching location", Toast.LENGTH_LONG).show();
        }


    }
}