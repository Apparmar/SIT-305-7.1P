package com.example.a7_1p;

import androidx.fragment.app.FragmentActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;

import com.example.a7_1p.data.DatabaseHelper;
import com.example.a7_1p.model.Item;
import com.example.a7_1p.util.Util;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.a7_1p.databinding.ActivityMapsBinding;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    DatabaseHelper db;
    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    List<Item> itemList = new ArrayList<>();

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

        // Add a marker in Sydney and move the camera

        db= new DatabaseHelper(this);
        itemList = db.fetchItem();
        String latLong, description = "";
        Double lat, lon;
        //Cursor cursor = db.query(Util.ITEM_TABLE_NAME, null,null, null, null, null, null);
        int numberOfrows = itemList.size();
        db.close();

        for (int i=0; i < numberOfrows; i++)
        {
            latLong = itemList.get(i).getLocation();
            lon = Double.parseDouble(latLong.substring(0, latLong.indexOf(":")));
            Log.i("**************** Lat",lon.toString());
            lat = Double.parseDouble(latLong.substring(latLong.indexOf(":") + 1));
            Log.i("**************** Lon",lat.toString());

            description = itemList.get(i).getDescription();
            LatLng locVal = new LatLng(lat, lon);
            mMap.addMarker(new MarkerOptions().position(locVal).title(description));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(locVal));
        }
    }
}