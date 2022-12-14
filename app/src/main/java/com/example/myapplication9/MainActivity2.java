package com.example.myapplication9;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;



public class MainActivity2 extends AppCompatActivity {
    private EditText productID, productType, productName;
    private Button sendDatabtn;

    FirebaseDatabase firebaseDatabase;

    DatabaseReference databaseReference;

    newProduct newProduct;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        productID = findViewById(R.id.productID);
        productType = findViewById(R.id.productType);
        productName = findViewById(R.id.productName);


        firebaseDatabase = FirebaseDatabase.getInstance();

        databaseReference = firebaseDatabase.getReference("newProduct");

        newProduct = new newProduct();

        sendDatabtn = findViewById(R.id.sendData);

        sendDatabtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Type = productType.getText().toString();
                String Name = productName.getText().toString();
                String ID = productID.getText().toString();
                // below line is for checking whether the
                // edittext fields are empty or not.
                if (TextUtils.isEmpty(Type) && TextUtils.isEmpty(Name) && TextUtils.isEmpty(ID)) {
                    // if the text fields are empty
                    // then show the below message.
                    Toast.makeText(MainActivity2.this, "Please add some data.", Toast.LENGTH_SHORT).show();
                } else {
                    // else call the method to add
                    // data to our database.
                    addDatatoFirebase(Type, Name, ID);
//                    System.out.println("All good");
                }
            }
        });
    }

    private void addDatatoFirebase(String Type, String Name, String ID) {
        newProduct.setProductType(Type);
        newProduct.setProductName(Name);
        newProduct.setProductID(Integer.parseInt(ID));
        // we are use add value event listener method
        // which is called with database reference.
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // inside the method of on Data change we are setting
                // our object class to our database reference.
                // data base reference will sends data to firebase.
                databaseReference.setValue(newProduct);

                // after adding this data we are showing toast message.
                Toast.makeText(MainActivity2.this, "data added", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // if the data is not added or it is cancelled then
                // we are displaying a failure toast message.
                Toast.makeText(MainActivity2.this, "Fail to add data " + error, Toast.LENGTH_SHORT).show();
            }
        });
    }
}