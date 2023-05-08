package com.example.a7_1p;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

import java.text.ParseException;
import java.time.LocalDate;
import java.util.Date;
import java.text.SimpleDateFormat;
public class NewAdvertActivity extends AppCompatActivity {

    DatabaseHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_advert);
        db= new DatabaseHelper(this);

        TextView itemName = findViewById(R.id.itemName);
        TextView itemDesc = findViewById(R.id.itemDescription);
        TextView itemDate = findViewById(R.id.itemDate);
        TextView itemLoc = findViewById(R.id.itemLocation);
        TextView itemPhone = findViewById(R.id.itemPhone);
        RadioButton itemLost = findViewById(R.id.Lost);
        RadioButton itemFound = findViewById(R.id.Found);
        Button itemSave = findViewById(R.id.itemSave);

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
                    Log.d("****************Date:", lItemDate);
                    ld = new SimpleDateFormat("dd/MM/yyyy").parse(lItemDate);
                    Log.d("****************Date:", ld.toString());
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


    }
}