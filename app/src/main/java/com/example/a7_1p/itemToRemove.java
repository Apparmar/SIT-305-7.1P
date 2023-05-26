package com.example.a7_1p;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.a7_1p.data.DatabaseHelper;

public class itemToRemove extends AppCompatActivity {
    DatabaseHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_to_remove);

        TextView itemToRemove = findViewById(R.id.dItemRemove);
        Button removeButton = findViewById(R.id.dRemoveButton);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        Integer itemId = extras.getInt("itemId");
        String itemDesc = extras.getString("itemDesc");
        String itemDate = extras.getString("itemDate");
        String itemLoc = extras.getString("itemLoc");
        String itemPhone = extras.getString("itemPhone");
        Integer itemLost = extras.getInt("itemLost");

        String lost = "Found";
        if (itemLost.equals(1))
            lost = "Lost";

        String info = itemDesc + "\n" + lost + " On: " + itemDate + "\nAt: " + itemLoc + "\nContact:" +
                itemPhone;

        itemToRemove.setText(info);

        removeButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                db= new DatabaseHelper(itemToRemove.this);
                db.deleteItem(itemId.toString());

                Intent homeIntent = new Intent(itemToRemove.this, HomeActivity.class);
                startActivity(homeIntent);

            }
        }
        );

    }
}