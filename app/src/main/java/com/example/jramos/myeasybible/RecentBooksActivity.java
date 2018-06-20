package com.example.jramos.myeasybible;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.jramos.myeasybible.utils.Preferences;

public class RecentBooksActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recent_books);

        String recent = Preferences.getStringValue(
                this,
                getResources().getString(R.string.recent_book_pref_key
                ));

        TextView t = findViewById(R.id.tv_test_recentbook);
        t.setText(recent);
    }
}
