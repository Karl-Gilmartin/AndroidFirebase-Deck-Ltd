package com.example.myapplication9;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class LogInActivity extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;

    DatabaseReference databaseReference, reference;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        Button loginBtn = (Button)findViewById(R.id.loginBtn);
        EditText username = (EditText)findViewById(R.id.inUsername);
        EditText password = (EditText)findViewById(R.id.inPassword);
        TextView passwordFirebase1 = (TextView) findViewById(R.id.textView5);





        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String usernameIn = username.getText().toString();
                String passwordIn = password.getText().toString();
                firebaseDatabase = FirebaseDatabase.getInstance();
                databaseReference = firebaseDatabase.getReference("Users");
                passwordFirebase1.setText(passwordIn);
                databaseReference.child(usernameIn).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if (task.isSuccessful()) {
                            if (task.getResult().exists()) {

                                DataSnapshot dataSnapshot = task.getResult();
                                String passwordFirebase = (String) dataSnapshot.child("password").getValue();
                                passwordFirebase1.setText(passwordFirebase);
                                if (passwordFirebase.equals(passwordIn)) {
                                    Intent showDetailActivity = new Intent(getApplicationContext(), MainActivity.class);
                                    startActivity(showDetailActivity);

                                } else {
                                    Toast.makeText(LogInActivity.this, "Password is not a match", Toast.LENGTH_LONG).show();
                                }
                            }
                            else {
                                Toast.makeText(LogInActivity.this, "Invalid", Toast.LENGTH_LONG).show();
                            }
                            }

                        else{
                            Toast.makeText(LogInActivity.this, "No such Username", Toast.LENGTH_LONG).show();
                        }

                    }
                });



            }
        });
    }
}