package com.example.jramos.myeasybible;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.jramos.myeasybible.utils.Preferences;

public class FavoriteBooksActivity extends AppCompatActivity {

    String[] listOfBooks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_books);

        String prefKey = getResources().getString(R.string.favorites_books_pref_key);
        String favoritebookslist = Preferences.getStringValue(this, prefKey);

        listOfBooks = favoritebookslist.split(",");

        ListView lvfavoriteBooksList = findViewById(R.id.lvFavorite_books_list);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, listOfBooks);
        lvfavoriteBooksList.setAdapter(adapter);


        lvfavoriteBooksList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                readBook(position);
            }
        });
    }

    private void readBook(int position){
        String book = listOfBooks[position];
        Intent i = new Intent(this, BookDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("book", book);
        i.putExtras(bundle);
        startActivity(i);
        finish();
    }
}
