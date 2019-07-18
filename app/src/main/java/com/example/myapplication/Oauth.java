package com.example.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.Toast;

public class Oauth extends AppCompatActivity {
    private String clientId = "dd35d1dd-1c3a-4233-b25a-6ee6c5e9a70c";
    private String clientSecret = "57ac89db-ea47-4fc4-a122-0295063fd211";
    private String redirectUri = "http://www.example.com/restoreorbital111";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oauth);

       Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.dbs.com/sandbox/api/sg/v1/oauth/authorize" + "?client_id=" + clientId + "&redirect_uri=" + redirectUri + "&scope=Read&response_type=code&state=0399"));
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();

        Uri uri = getIntent().getData();

       if (uri != null && uri.toString().startsWith(redirectUri)){
//            String code = uri.getQueryParameter("code");
//
//
//
            Toast.makeText(this,"yay!", Toast.LENGTH_SHORT).show();
        }
    }
}
