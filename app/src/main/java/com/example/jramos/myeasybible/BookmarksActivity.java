package com.example.jramos.myeasybible;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.jramos.myeasybible.adapters.BookmarkListAdapter;
import com.example.jramos.myeasybible.loaders.BookmarkLoader;
import com.example.jramos.myeasybible.models.Book;
import com.example.jramos.myeasybible.utils.Preferences;
import com.example.jramos.myeasybible.utils.Query;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BookmarksActivity extends BaseActivity {

    RecyclerView rvBookmarksList;
    JSONObject bookmark;
    BookmarkListAdapter adapter;
    List<Book> dataAdapter = new ArrayList<>();
    JSONArray bookmarksArray;
    int jsonIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmarks);


        rvBookmarksList = findViewById(R.id.rvBookmarks_list);
        rvBookmarksList.setLayoutManager(new LinearLayoutManager(this));

        adapter = new BookmarkListAdapter(dataAdapter);
        rvBookmarksList.setAdapter(adapter);


        try{

            String key = getResources().getString(R.string.bookmarks_pref_key);
            String bookmark_list = Preferences.getStringValue(this, key);

            if(bookmark_list.isEmpty())
                return;

            JSONObject bookmarksRoot = new JSONObject(bookmark_list);
            String rootArray = bookmarksRoot.optString("bookmarks");
            bookmarksArray = new JSONArray(rootArray);

            bookmark = bookmarksArray.getJSONObject(jsonIndex);




        //set loader
        if(Query.isNetworkAvailable(getApplicationContext())){
            getLoaderManager().initLoader(R.id.loaderBookmarkId, null, bookmark_callback);
        }

        }catch (Exception e){
            Log.e("JSON Error", e.getMessage());
        }

    }

    LoaderManager.LoaderCallbacks<Book> bookmark_callback = new LoaderManager.LoaderCallbacks<Book>() {
        @Override
        public Loader<Book> onCreateLoader(int id, Bundle args) {

            String url = null;
            try {
                url = BASE_URL + "scrip="+bookmark.getString("bookname")+"%20"+bookmark.getString("chapterNumber")+":"+bookmark.getString("verseNumber") + "&v=valera";
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return new BookmarkLoader(getApplicationContext(), url, bookmark);
        }

        @Override
        public void onLoadFinished(Loader<Book> loader, Book data) {
            try{
                dataAdapter.add(data);
                adapter.notifyDataSetChanged();

                bookmark = bookmarksArray.getJSONObject(jsonIndex++);
                getLoaderManager().restartLoader(R.id.loaderBookmarkId, null, bookmark_callback);

            }catch (Exception e){
                Log.e("Reset Loader", e.getMessage());
            }

        }

        @Override
        public void onLoaderReset(Loader<Book> loader) {

        }
    };
}
