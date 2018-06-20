package com.example.jramos.myeasybible;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.SpannableString;
import android.text.style.BackgroundColorSpan;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.jramos.myeasybible.adapters.BookDetailPagerAdapter;
import com.example.jramos.myeasybible.loaders.BookLoader;
import com.example.jramos.myeasybible.models.Chapter;
import com.example.jramos.myeasybible.models.Verse;
import com.example.jramos.myeasybible.utils.Dialogs;
import com.example.jramos.myeasybible.utils.General;
import com.example.jramos.myeasybible.utils.Preferences;
import com.michaldrabik.tapbarmenulib.TapBarMenu;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import static com.example.jramos.myeasybible.utils.General.showToast;
import static com.example.jramos.myeasybible.utils.Query.isNetworkAvailable;

public class BookDetailActivity extends BaseActivity implements BookChapter.OnFragmentInteractionListener{

    String bookToLoad;
    ViewPager pager;
    ProgressBar progress;
    Dialogs.SearchInChapterDialog searchInPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);

        bookToLoad = getIntent().getStringExtra("book");
        setTitle(bookToLoad);

        final Dialogs.QuestionDialog addToFavoriteDialog = new Dialogs.QuestionDialog(this, "¿Desea agregar este libro a favoritos?");
        addToFavoriteDialog.setOnPositiveAction(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToFavorites();
                addToFavoriteDialog.cancel();
            }
        });

        pager = findViewById(R.id.vpChapters_of_book);

        progress = findViewById(R.id.detail_book_progress);
        progress.setVisibility(View.VISIBLE);

        if (isNetworkAvailable(this)) {
            getLoaderManager().initLoader(R.id.loaderBookId, null, bookdetailCallback);
        }

        final TapBarMenu tapbar = findViewById(R.id.tapMenu_bookdetail);
        tapbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tapbar.toggle();
            }
        });

        findViewById(R.id.tapItem_add_to_favorites).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToFavoriteDialog.show();
            }
        });

        setUpSearch();

    }

    void addToFavorites(){

        String key = getResources().getString(R.string.favorites_books_pref_key);
        String favoriteList = Preferences.getStringValue(this, key);

        if (favoriteList.isEmpty()){
            Preferences.putString(
                    this,
                    getResources().getString(R.string.favorites_books_pref_key),
                    bookToLoad
            );

            return;
        }

        String[] listOfBooks = favoriteList.split(",");
        StringBuilder listToAdd = new StringBuilder();

        for(String item : listOfBooks){
            if(item.equals(bookToLoad))
                return;
            else{
                String b = item + ","; // to separate concatenation from string.append
                listToAdd.append(b);
            }
        }

        listToAdd.append(bookToLoad);
        Preferences.putString(this, key, listToAdd.toString());

    }

    private LoaderManager.LoaderCallbacks<List<Chapter>> bookdetailCallback = new LoaderManager.LoaderCallbacks<List<Chapter>>() {
        @Override
        public Loader<List<Chapter>> onCreateLoader(int id, Bundle args) {
            return new BookLoader(getApplicationContext(), BASE_URL + "p="+ bookToLoad +"&v=valera");
        }

        @Override
        public void onLoadFinished(Loader<List<Chapter>> loader, List<Chapter> data) {

            FragmentManager fmanager = getSupportFragmentManager();
            pager.setAdapter(new BookDetailPagerAdapter(fmanager, data, getApplicationContext(), bookToLoad));
            progress.setVisibility(View.GONE);

        }

        @Override
        public void onLoaderReset(Loader<List<Chapter>> loader) {

        }
    };

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    private void setUpSearch(){

        searchInPage  = new Dialogs.SearchInChapterDialog(this);

        findViewById(R.id.tapItem_find_in_page).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchInPage.show();
            }
        });

        searchInPage.setQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                /*int currentPage = pager.getCurrentItem();
                View root = pager.getChildAt(currentPage+1).getRootView();
                TextView tvChapterVerses = root.findViewById(R.id.tvCapter_verses);
                String chapterText = tvChapterVerses.getText().toString();

                if (chapterText.contains(query)){
                    StringBuilder fullText = new StringBuilder();

                    BookDetailPagerAdapter adapter = (BookDetailPagerAdapter) pager.getAdapter();
                    if (adapter == null)
                        return false;

                    Chapter chapter = adapter.getChapterAt(currentPage);

                    for(Verse verse : chapter.getVerses()){
                        String versestr = "<p>" +verse.getVerseNumber() +". " + verse.getText() + "</p>";

                        if(versestr.contains(query)){
                            String highlighted = getResources().getString(R.string.highlighted_text, query);
                            versestr = versestr.replace(query, highlighted);
                        }

                        fullText.append(versestr);
                    }

                    tvChapterVerses.setText(Html.fromHtml(fullText.toString()));
                }*/
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }


}
