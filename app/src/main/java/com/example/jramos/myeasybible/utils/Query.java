package com.example.jramos.myeasybible.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class Query {

    public static Boolean isNetworkAvailable(Context context){
        ConnectivityManager cmanager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cmanager.getActiveNetworkInfo();

        return (networkInfo != null && networkInfo.isConnectedOrConnecting());
    }

    public static URL createURL(String _url){

        try {
            return new URL(_url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getResponseFromStream(InputStream istream){
        StringBuilder response = new StringBuilder();

        if(istream == null)
            return response.toString();

        InputStreamReader isReader = new InputStreamReader(istream);
        BufferedReader bufferedReader = new BufferedReader(isReader);

        try {
            String line = bufferedReader.readLine();

            while (line != null){
                response.append(line);
                line = bufferedReader.readLine();
            }

            return response.toString();


        } catch (IOException e) {
            e.printStackTrace();
            return response.toString();
        }



    }
}
