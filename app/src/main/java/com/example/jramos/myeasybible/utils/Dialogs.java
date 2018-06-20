package com.example.jramos.myeasybible.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.jramos.myeasybible.BookDetailActivity;
import com.example.jramos.myeasybible.R;

public class Dialogs {

    public static class SearchBookDialog extends Dialog{

        private RadioGroup rgroup;
        Activity activity;

        String[] books = new String[9];

        public SearchBookDialog(Activity _activity) {

            super(_activity);
            activity = _activity;

            books[0] = "James";
            books[1] = "Daniel";
            books[2] = "Joel";
            books[3] = "Genesis";
            books[4] = "Job";
            books[5] = "Esther";
            books[6] = "Romans";
            books[7] = "Titus";
            books[8] = "Revelation";
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.search_book_dialog);

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(activity, android.R.layout.simple_list_item_1, books);
            ListView lview = findViewById(R.id.lvBooksList);
            lview.setAdapter(adapter);

            lview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent i = new Intent(activity, BookDetailActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("book", books[position]);
                    i.putExtras(bundle);
                    activity.startActivity(i);
                    cancel();
                }
            });
        }
    }

    public static class QuestionDialog extends Dialog {
        Activity activity;
        ImageView imgYes, imgCancel;
        String title;

        View.OnClickListener positiveClickListener, negativeCLickListener;

        public QuestionDialog(Activity _activity, String _title) {
            super(_activity);

            activity = _activity;
            title = _title;
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.question_dialog);

            TextView tvTitle = findViewById(R.id.tvQuestion_title);
            imgYes = findViewById(R.id.imgQuestion_yes);
            imgCancel = findViewById(R.id.imgQuestion_cancel);
            tvTitle.setText(this.title);

            if (positiveClickListener != null)
                imgYes.setOnClickListener(positiveClickListener);

            if(negativeCLickListener != null)
                imgCancel.setOnClickListener(negativeCLickListener);

        }

        public void setOnPositiveAction(View.OnClickListener _clickListener){
            positiveClickListener = _clickListener;
        }

        public void setOnNegativeAction(View.OnClickListener _clickListener){
            negativeCLickListener = _clickListener;
        }
    }

    public static class SearchInChapterDialog extends Dialog {

        SearchView searchfield;
        SearchView.OnQueryTextListener queryTextListener;

        public SearchInChapterDialog(@NonNull Activity _activity) {
            super(_activity);
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.search_in_chapter);

            searchfield = findViewById(R.id.svTerm_to_search);

            if(queryTextListener != null)
                searchfield.setOnQueryTextListener(queryTextListener);
        }

        @Override
        protected void onStart() {
            super.onStart();

            searchfield.isEnabled();
        }

        public void setQueryTextListener(SearchView.OnQueryTextListener _listener){
            queryTextListener = _listener;
        }

        public String getText(){
            return searchfield.getQuery().toString();
        }
    }


}
