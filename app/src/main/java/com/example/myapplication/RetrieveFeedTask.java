package com.example.myapplication;

import android.os.AsyncTask;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPostHC4;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.xml.sax.XMLReader;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

class RetrieveFeedTask extends AsyncTask<String, Void,String> {

    private Exception exception;

    protected String doInBackground(String... urls) {
        try {
            HttpClient client = HttpClientBuilder.create().build();
            HttpPostHC4 request = new HttpPostHC4("https://www.dbs.com/sandbox/api/sg/v1/oauth/tokens");
            String auth = "Basic " + urls[0];
            auth = auth.replaceAll("\n", "");
            request.addHeader("Authorization", auth);
            request.addHeader("Content-Type", "application/x-www-form-urlencoded");

            List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
            urlParameters.add(new BasicNameValuePair("code", urls[1]));
            urlParameters.add(new BasicNameValuePair("redirect_uri", urls[2]));
            urlParameters.add(new BasicNameValuePair("grant_type", "token"));

            request.setEntity(new UrlEncodedFormEntity(urlParameters));
            HttpResponse response = client.execute(request);
        return response.toString();
        }
        catch( Exception e){
            System.out.println(e.getMessage());
            return null;
        }

    }

    protected void onPostExecute() {
        // TODO: check this.exception
        // TODO: do something with the feed
    }
}