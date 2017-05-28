package com.daman.farmify;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    double latitude,longitude;
    Marker marker1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        Intent rcv=getIntent();
        latitude=rcv.getDoubleExtra("latitude",0.0);
        longitude=rcv.getDoubleExtra("longitude",0.0);
        Toast.makeText(this,""+latitude+longitude,Toast.LENGTH_LONG).show();
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

        // Add a marker in Sydney and move the camera
        final LatLng ludhiana = new LatLng(latitude, longitude);
        LatLng DMC = new LatLng(30.9143803, 75.8181605);
        LatLng CMC = new LatLng(30.9105125, 75.8614644);
        LatLng Fortis = new LatLng(30.8892897, 75.9329564);
        LatLng Apollo = new LatLng(30.8717741, 75.8497614);
        LatLng civil = new LatLng(30.9079502, 75.8436508);

        mMap.addMarker(new MarkerOptions().position(ludhiana).title("Ludhiana"));

        mMap.addMarker(new MarkerOptions().position(DMC).title("Dayanand Medical College & Hospital"));
        mMap.addMarker(new MarkerOptions().position(CMC).title("Christian Medical College & Hospital"));
        mMap.addMarker(new MarkerOptions().position(Fortis).title("Fortis Hospital"));
        mMap.addMarker(new MarkerOptions().position(Apollo).title("Apollo Hospital"));
        mMap.addMarker(new MarkerOptions().position(civil).title("Civil Hospital"));

        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(ludhiana,1000),100,null);
       /* mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                LatLng latLng = marker.getPosition();
                double lat = latLng.latitude;
                double lon = latLng.longitude;
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse("http://maps.google.com/maps?saddr="+latitude+","+longitude+"&daddr="+lat+","+lon+""));
                startActivity(intent);
                Toast.makeText(MapsActivity.this,""+lat+lon,Toast.LENGTH_LONG).show();
                return false;
            }
        });*/
    }
}
