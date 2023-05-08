package com.example.a7_1p;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        Button newAdvert = findViewById(R.id.newAdvert);
        Button showItems = findViewById(R.id.showItems);

        newAdvert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent newAdvert = new Intent(HomeActivity.this, NewAdvertActivity.class);
                startActivity(newAdvert);
            }
        }
        );


        showItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
                {
                    Intent viewAdvert = new Intent(HomeActivity.this, itemRecToLoad.class);
                    startActivity(viewAdvert);
                }
            }
        );

    }
}