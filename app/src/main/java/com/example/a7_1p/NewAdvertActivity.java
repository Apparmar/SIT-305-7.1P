package com.example.a7_1p;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a7_1p.data.DatabaseHelper;
import com.example.a7_1p.model.Item;
import com.example.a7_1p.model.User;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.List;

public class NewAdvertActivity extends AppCompatActivity {

    DatabaseHelper db;
    TextView itemLoc;
    Button getCurLocation;
    LocationManager locationManager;
    LocationListener locationListener;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
        {
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
            {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10, 10, locationListener);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_advert);
        db = new DatabaseHelper(this);

        TextView itemName = findViewById(R.id.itemName);
        TextView itemDesc = findViewById(R.id.itemDescription);
        TextView itemDate = findViewById(R.id.itemDate);
        itemLoc = findViewById(R.id.itemLocation);
        TextView itemPhone = findViewById(R.id.itemPhone);
        RadioButton itemLost = findViewById(R.id.Lost);
        RadioButton itemFound = findViewById(R.id.Found);
        Button itemSave = findViewById(R.id.itemSave);
        getCurLocation = findViewById(R.id.getCurLocation);

        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), BuildConfig.MAPS_API_KEY);
        }

        locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);


        itemSave.setOnClickListener(new View.OnClickListener()
        {   @Override
            public void onClick(View view)
            {
                String lItemName = itemName.getText().toString();
                String lItemDesc = itemDesc.getText().toString();
                String  lItemDate = itemDate.getText().toString();
                String lItemLoc = itemLoc.getText().toString();
                int lItemPhone = Integer.parseInt(itemPhone.getText().toString());
                boolean lItemLost = itemLost.isChecked();
                boolean lItemFound = itemFound.isChecked();

                Date ld = null;
                try {
                    //Log.d("****************Date:", lItemDate);
                    ld = new SimpleDateFormat("dd/MM/yyyy").parse(lItemDate);
                    //Log.d("****************Date:", ld.toString());
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }

                int itemLost = 0;
                if (lItemLost)
                    itemLost = 1;
                if (lItemFound)
                    itemLost = 0;

                //boolean lost,  String name, Integer phone, String description, String date, String location
                long result = db.insertItem(new Item(itemLost, lItemName,lItemPhone,lItemDesc,lItemDate,lItemLoc));
                if (result > 0)
                {
                    Toast.makeText(NewAdvertActivity.this, "Item registered successfully", Toast.LENGTH_LONG).show();

                    Intent homeIntent = new Intent(NewAdvertActivity.this, HomeActivity.class);
                    startActivity(homeIntent);
/*
                    itemName.setText("");
                    itemDesc.setText("");
                    itemDate.setText("");
                    itemLoc.setText("");
                    itemPhone.setText("");
 */
                }
                else
                {
                    Toast.makeText(NewAdvertActivity.this, "Registration error", Toast.LENGTH_SHORT).show();
                }
            }
        });

        itemLoc.setOnClickListener(new View.OnClickListener()
        {   @Override
        public void onClick(View view)
        {
            List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS, Place.Field.LAT_LNG);
            Intent intent = new Autocomplete.IntentBuilder(
                    AutocompleteActivityMode.FULLSCREEN, fields).build(NewAdvertActivity.this);
            startAutocomplete.launch(intent);
            //itemLoc.setText();
        }
        });

        getCurLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                locationListener = new LocationListener() {
                    @Override
                    public void onLocationChanged(@NonNull Location location) {

                        String currentLoc = location.getLongitude() + ":" + location.getLatitude();
                        //loc.replace("lat/lng: ","");
                        itemLoc.setText(currentLoc);
                        locationManager.removeUpdates(locationListener);
                    }
                };

                if (ActivityCompat.checkSelfPermission(NewAdvertActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(NewAdvertActivity.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    ActivityCompat.requestPermissions(NewAdvertActivity.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                } else
                {
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10, 10, locationListener);
                }
            }
        });

    }
    private final ActivityResultLauncher<Intent> startAutocomplete = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent intent = result.getData();
                    if (intent != null) {
                        Place place = Autocomplete.getPlaceFromIntent(intent);
                       // Log.i("TAG", "Place: ${place.getName()}, ${place.getId()}");
                        //Log.i("*****************",place.getLatLng().toString());
                        Double longitude = place.getLatLng().longitude;
                        Double latitude = place.getLatLng().latitude;
                        String currentLoc = longitude.toString() + ":" + latitude.toString();
                        //loc.replace("lat/lng: ","");
                        itemLoc.setText(currentLoc);

                    }
                } else if (result.getResultCode() == Activity.RESULT_CANCELED) {
                    // The user canceled the operation.
                    //Log.i("TAG", "User canceled autocomplete");
                }
            });
}