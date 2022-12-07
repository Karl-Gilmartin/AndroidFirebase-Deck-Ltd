package com.example.myapplication9;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity3 extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // ...
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);

//        ArrayList<newProduct> products = newProduct.generateProductListFromFirebase();
//        ArrayList<newProduct> products = newProduct.generateProductList(10);
//        ArrayList<newProduct> firebaseProducts = newProduct.generateProductListFromFirebase();

//        for(newProduct product: products) {
//            System.out.println("\n\nNon-Firebase...");
//            product.printProduct();
//        }
//        for(newProduct product: firebaseProducts) {
//            System.out.println("\n\nFIREBASE...");
//            product.printProduct();
//        }

        RecyclerView rvDetails = (RecyclerView) findViewById(R.id.recyclerView);

        // Lookup the recyclerview in activity layout

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("Products");

        ArrayList<newProduct> products = new ArrayList<newProduct>();

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> snapshots = dataSnapshot.getChildren();

                for (DataSnapshot snapshot : snapshots) {
                    Map<String, Object> results = (HashMap<String, Object>) snapshot.getValue();
                    int quantity = 0;
                    if(results.get("productQuantity") != null) {
                        quantity = Integer.parseInt((String) results.get("productQuantity"));
                    }
                    newProduct product = new newProduct("name not found", "id not found", "type not found", 0);

                    product.setProductName((String) results.get("productName"));
                    product.setProductID((String) Long.toString((Long) results.get("productID")));
                    product.setProductType((String) results.get("productType"));
                    product.setProductQuantity(quantity);

                    products.add(product);

                    DetailsAdapter adapter = new DetailsAdapter(products);

                    rvDetails.setAdapter(adapter);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
        rvDetails.setLayoutManager(new LinearLayoutManager(this));
    }

}
