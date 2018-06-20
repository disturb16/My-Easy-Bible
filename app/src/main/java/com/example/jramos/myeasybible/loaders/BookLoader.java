package com.example.jramos.myeasybible.loaders;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import com.example.jramos.myeasybible.R;
import com.example.jramos.myeasybible.models.Chapter;
import com.example.jramos.myeasybible.models.Verse;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.example.jramos.myeasybible.utils.Query.createURL;
import static com.example.jramos.myeasybible.utils.Query.getResponseFromStream;

public class BookLoader extends AsyncTaskLoader <List<Chapter>> {


    String urlString;

    public BookLoader(Context context, String _urlString) {
        super(context);
        urlString = _urlString.replace(" ", "%20");
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<Chapter> loadInBackground() {

        try {
            URL url = createURL(urlString);
            assert url != null;
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setConnectTimeout(15000);
            conn.setReadTimeout(10000);
            conn.setRequestMethod("GET");
            conn.connect();

            if(conn.getResponseCode() != 200){
                Log.e("Connection Error", "Response error" + conn.getResponseCode());
                return  null;
            }

            String response = getResponseFromStream(conn.getInputStream());


            if (response.charAt(0) == '('){
                response = response.substring(1);
            }
            if(response.endsWith(");")){
                int index = response.length() - 1;
                response = response.substring(0 ,index - 1);
            }



            JSONObject root = new JSONObject(response);
            JSONObject chapters = root.getJSONObject("book");


            List<Chapter> chapterList = new ArrayList<Chapter>();

            for(int i = 0; i < chapters.length(); i++){

                String strChapter = chapters.getString(""+(i+1));

                JSONObject jsonChapter = new JSONObject(strChapter);
                JSONObject verses = jsonChapter.getJSONObject("chapter");
                List<Verse> verseList = new ArrayList<Verse>();

                for (int j = 0; j < verses.length(); j++){
                    JSONObject jsonVerse = verses.getJSONObject((1+j)+"");
                    Verse verse = new Verse(
                            jsonVerse.getInt("verse_nr"),
                            jsonVerse.getString("verse")
                    );

                    verseList.add(verse);
                }

                Chapter chapter = new Chapter(jsonChapter.getInt("chapter_nr"), verseList);
                chapterList.add(chapter);

            }

            return chapterList;

        }catch (Exception e){
            Log.e("error connecting", e.getMessage());
            return null;
        }

    }

    @Override
    public void deliverResult(List<Chapter> data) {
        super.deliverResult(data);
    }
}
