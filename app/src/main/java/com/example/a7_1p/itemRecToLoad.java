package com.example.a7_1p;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

public class itemRecToLoad extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_rec_to_load);

        Fragment fragment = new items();
        FragmentManager fgm = getSupportFragmentManager();
        FragmentTransaction fgt = fgm.beginTransaction();
        fgt.replace(R.id.mainFrame,fragment);
        fgt.commit();
    }
}