package com.example.myapplication9;

import static com.example.myapplication9.R.id.txtProductNameEdt;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class ShowDetails extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;

    DatabaseReference databaseReference;

//    ActivityReadDataBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_details);
        TextView txtName = (TextView)findViewById(R.id.txtProductNameEdt);
        firebaseDatabase = FirebaseDatabase.getInstance();

        databaseReference = firebaseDatabase.getReference("Products").child("1001");
        String name = String.valueOf(databaseReference.child("productName").get());
        System.out.println(name);
        txtName.setText(name);


    }
}