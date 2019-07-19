package com.example.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.zxing.Result;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.Date;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ScanCodeActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {
    DatabaseReference reff;

    ZXingScannerView ScannerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ScannerView = new ZXingScannerView(this);
        setContentView(ScannerView);



    }

    @Override
        public void handleResult (Result result) {

        SecondActivitymodded.resultTextView.setText(result.getText());
        String currentString = result.getText();
        String[] separated = currentString.split(":");
        String date = separated[0];
        String productName = separated[1]; // this will contain product name
        String price = separated[2];
        reff = FirebaseDatabase.getInstance().getReference().child(SecondActivitymodded.headerEmail.toString());
        reff.push();
        onBackPressed();

    }

    @Override
    protected void onPause() {
        super.onPause();

        ScannerView.stopCamera();
    }

    @Override
    protected void onResume() {
        super.onResume();

        ScannerView.setResultHandler(this);
        ScannerView.startCamera();
    }
}
