package com.example.myapplication9;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Stream;

public class newProduct  {
    private String productName, productID,productQuantity, productType;


    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(String productQuantity) {
        this.productQuantity = productQuantity;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public newProduct(String name, String id, String type, String quantity) {
        productName = name;
        productID = id;
        productType = type;
        productQuantity = quantity;
    }

    public void printProduct() {
        System.out.println("Product Name: " + productName + "\nProduct ID: " + productID + "\nProduct Type: " + productType + "\nProduct Quantity: " + productQuantity);
    }

//    ArrayList<newProduct> products = new ArrayList<>();

    public static ArrayList<newProduct> generateProductListFromFirebase() {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("Products");

        ArrayList<newProduct> products = new ArrayList<newProduct>();

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> snapshots = dataSnapshot.getChildren();

                for (DataSnapshot snapshot : snapshots) {
                    Map<String, Object> results = (HashMap<String, Object>) snapshot.getValue();
                    int quantity = 1;
                    if(results.get("productQuantity") != null) {
                        quantity = Integer.parseInt((String) results.get("productQuantity"));
                    }
                    newProduct product = new newProduct("name not found", "id not found", "type not found", "0");

                    product.setProductName((String) results.get("productName"));
                    product.setProductID((String) Long.toString((Long) results.get("productID")));
                    product.setProductType((String) results.get("productType"));
                    product.setProductQuantity(String.valueOf(quantity));

                    products.add(product);

                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }

        });

        System.out.println(products.size());
        return products;
    }

    public static ArrayList<newProduct> generateProductList(int numOfItems) {
        ArrayList<newProduct> products = new ArrayList<newProduct>();

        Random random = new Random();
        for (int i = 0; i < numOfItems; i++) {
            newProduct product = new newProduct("Item name", Integer.toString(random.nextInt(1000)), "Chair", "6");
            products.add(product);
        }

        return products;
    }

}
