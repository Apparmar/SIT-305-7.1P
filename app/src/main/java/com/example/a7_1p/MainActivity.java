package com.example.a7_1p;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.a7_1p.data.DatabaseHelper;

public class MainActivity extends AppCompatActivity {
        DatabaseHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        EditText userName = findViewById(R.id.userName);
        EditText password = findViewById(R.id.password);
        Button login = findViewById(R.id.login);
        Button signup = findViewById(R.id.signup);

        db= new DatabaseHelper(this);

        login.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick (View view)
            {
                boolean result = db.fetchUser(userName.getText().toString(), password.getText().toString());
                if (result)
                {
                    Intent homeIntent = new Intent(MainActivity.this, HomeActivity.class);
                    startActivity(homeIntent);
                }
                else
                {
                    Toast.makeText(MainActivity.this, "The user does not exist.", Toast.LENGTH_SHORT).show();
                }
            }
        }
        );

        signup.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent signupIntent = new Intent(MainActivity.this, SignupActivity.class);
                startActivity(signupIntent);
            }
        });
    }
}