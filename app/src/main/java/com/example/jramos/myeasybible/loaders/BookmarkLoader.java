package com.example.jramos.myeasybible.loaders;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;
import android.widget.ListView;

import com.example.jramos.myeasybible.models.Book;
import com.example.jramos.myeasybible.models.Chapter;
import com.example.jramos.myeasybible.models.Verse;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static com.example.jramos.myeasybible.utils.Query.createURL;
import static com.example.jramos.myeasybible.utils.Query.getResponseFromStream;

public class BookmarkLoader extends AsyncTaskLoader<Book> {

    private String url_string;
    JSONObject bookmark;

    public BookmarkLoader(Context context, String url, JSONObject _bookmark) {
        super(context);
        url_string = url;
        bookmark = _bookmark;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public Book loadInBackground() {

        try{

            URL url = createURL(url_string);
            assert url != null;
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("GET");
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(150000);
            conn.connect();

            if(conn.getResponseCode() != 200){
                Log.e("Getting bookmarks", "Error code: "+conn.getResponseCode());
                return null;
            }

            String response = getResponseFromStream(conn.getInputStream());

            if (response.charAt(0) == '('){
                response = response.substring(1);
            }
            if(response.endsWith(");")){
                int index = response.length() - 1;
                response = response.substring(0 ,index - 1);
            }

            String verse_nr = bookmark.getString("verseNumber");

            JSONObject root = new JSONObject(response);
            JSONArray arr = root.getJSONArray("book");
            JSONObject book = arr.getJSONObject(0);
            JSONObject chapter = book.getJSONObject("chapter");
            JSONObject jsonverse = chapter.optJSONObject(verse_nr); // for test

            List<Verse> verses = new ArrayList<>();
            verses.add(new Verse(Integer.parseInt(verse_nr), jsonverse.getString("verse")));

            List<Chapter> chapterList = new ArrayList<>();
            chapterList.add(new Chapter(bookmark.getInt("chapterNumber"), verses));


            return new Book(bookmark.getString("bookname"), chapterList);


        }catch (Exception e){
            Log.e("Getting bookmarks", e.getMessage());
            return null;
        }
    }

    @Override
    public void deliverResult(Book data) {
        super.deliverResult(data);
    }
}
