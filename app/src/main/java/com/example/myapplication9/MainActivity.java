package com.example.myapplication9;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button newItemBtn = (Button)findViewById(R.id.NewProduct);
        Button scanItem = (Button) findViewById(R.id.scanItem);

        scanItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scanCode();
            }
        });
        newItemBtn.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                System.out.println("Button Clicked");
                Intent activity2Intent = new Intent(getApplicationContext(), MainActivity2.class);
                startActivity(activity2Intent);

            }
        });
    }
    public void scanCode(){
        ScanOptions options = new ScanOptions();
        options.setPrompt("Volume up to flash on");
        options.setBeepEnabled(true);
        options.setOrientationLocked(true);
        options.setCaptureActivity(CaptureAct.class);
        barLauncher.launch(options);
    }
ActivityResultLauncher<ScanOptions> barLauncher = registerForActivityResult(new ScanContract(), result -> {
    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
    builder.setTitle("Result");
    builder.setMessage(result.getContents());
    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {

            dialogInterface.dismiss();
            Intent showDetailActivity = new Intent(getApplicationContext(), ShowDetails.class);
            startActivity(showDetailActivity);

        }
    }).show();
});
}