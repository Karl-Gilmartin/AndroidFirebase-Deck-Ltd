package com.example.myapplication9;

import static com.example.myapplication9.R.id.productID;
import static com.example.myapplication9.R.id.txtProductID;
import static com.example.myapplication9.R.id.txtProductNameEdt;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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

public class ShowDetails extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;

    DatabaseReference databaseReference, reference;
    EditText txtQuantEdt;
    Button sendDatabtn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_details);
        TextView txtName = (TextView)findViewById(R.id.txtProductNameEdt);
        Button goBack = (Button)findViewById(R.id.goBack);
        TextView txtProductID = (TextView)findViewById(R.id.txtProductIDEdt);
        TextView txtType = (TextView)findViewById(R.id.txtProductTypeEdt);
        TextView txtQuant = (TextView)findViewById(R.id.txtProductQuant);
        txtQuantEdt = findViewById(R.id.editQuanNumber);


        sendDatabtn = findViewById(R.id.updateStockBtn);
        sendDatabtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int Quantity = Integer.parseInt(txtQuantEdt.getText().toString());

                addDatatoFirebase(Quantity );

//
                }
            });


        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Products");
        databaseReference.child(String.valueOf(MainActivity.findProductID)).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()){
                    if (task.getResult().exists()){
                        DataSnapshot dataSnapshot = task.getResult();
                        String productName = String.valueOf(dataSnapshot.child("productName").getValue());
                        String productID = String.valueOf(dataSnapshot.child("productID").getValue());
                        String productType = String.valueOf(dataSnapshot.child("productType").getValue());
                        String productQuant = String.valueOf(dataSnapshot.child("productQuantity").getValue());
                        String value = txtQuantEdt.getText().toString();
                        txtName.setText(productName);
                        txtProductID.setText(productID);
                        txtType.setText(productType);
                        txtQuant.setText(productQuant);
                    }
                    else{
                        System.out.println("Error");
                    }
                }

                else{
//                    Toast.makeText(ReadData.this, "user doesnt exist", Toast.LENGTH_SHORT).show();
                    System.out.println("Error");
                }
            }
        });
        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent activity2Intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(activity2Intent);
            }
        });

    }
    private void addDatatoFirebase(int Quantity) {
//        newProduct.setProductQuantity(Quantity);

        // we are use add value event listener method
        // which is called with database reference.
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // inside the method of on Data change we are setting
                // our object class to our database reference.
                // data base reference will sends data to firebase.
                databaseReference = firebaseDatabase.getReference("Products");
                databaseReference.child("12345").child("productQuantity").setValue(txtQuantEdt.getText().toString());

                // after adding this data we are showing toast message.
                Toast.makeText(ShowDetails.this, "data added", Toast.LENGTH_SHORT).show();
                Intent showDetailActivity = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(showDetailActivity);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // if the data is not added or it is cancelled then
                // we are displaying a failure toast message.
                Toast.makeText(ShowDetails.this, "Fail to add data " + error, Toast.LENGTH_SHORT).show();
            }
        });
    }




}