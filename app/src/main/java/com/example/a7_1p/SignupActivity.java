package com.example.a7_1p;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.camera2.TotalCaptureResult;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.a7_1p.data.DatabaseHelper;
import com.example.a7_1p.model.User;

public class SignupActivity extends AppCompatActivity {
    DatabaseHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        EditText sUserName = findViewById(R.id.sUserName);
        EditText sPassword = findViewById(R.id.sPassword);
        EditText scPassword = findViewById(R.id.scPassword);
        Button signUp = findViewById(R.id.signupSave);

        db = new DatabaseHelper(this);

        signUp.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                String uName = sUserName.getText().toString();
                String pw = sPassword.getText().toString();
                String scpw = scPassword.getText().toString();

                if (pw.equals(scpw))
                {
                    long result = db.insertUser(new User(uName, pw));
                    if (result > 0)
                    {
                        Toast.makeText(SignupActivity.this, "Registration successful", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(SignupActivity.this, "Registration error", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(SignupActivity.this, "Passwords don't match.", Toast.LENGTH_SHORT).show();
                }
            }
        }
        );
    }
}