package com.example.jramos.myeasybible;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class BaseActivity extends AppCompatActivity {

    final static String BASE_URL = "http://getbible.net/json?";
    //final static String BASE_URL = "http://www.esvapi.org/v2/rest/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
    }
}
