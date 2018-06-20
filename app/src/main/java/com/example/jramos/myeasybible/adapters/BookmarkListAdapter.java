package com.example.jramos.myeasybible.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.jramos.myeasybible.R;
import com.example.jramos.myeasybible.models.Book;
import com.example.jramos.myeasybible.models.Chapter;

import java.util.List;

public class BookmarkListAdapter extends RecyclerView.Adapter<BookmarkListAdapter.BookmarkListHolder> {

    List<Book> booksList;

    public BookmarkListAdapter(List<Book> _booksList){
        booksList = _booksList;
    }


    @NonNull
    @Override
    public BookmarkListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.bookmark_list_item, parent, false);
        return new BookmarkListHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull BookmarkListHolder holder, int position) {
        Book currentItem = booksList.get(position);

        holder.bookname.setText(currentItem.getName());

        Chapter chapter = currentItem.getChapterAt(0);
        String ChapterVerse = chapter.getChapterNumber() + " : " +chapter.getVerseAt(0).getVerseNumber();

        holder.chapterVerse.setText(ChapterVerse);
        holder.verseText.setText(chapter.getVerseAt(0).getText());


    }

    @Override
    public int getItemCount() {
        return booksList.size();
    }

    class BookmarkListHolder extends RecyclerView.ViewHolder{

        public TextView bookname, chapterVerse, verseText;

        public BookmarkListHolder(View itemView) {
            super(itemView);

            bookname = itemView.findViewById(R.id.tvBookName_bookmark);
            chapterVerse = itemView.findViewById(R.id.tvChapter_verse_bookmark);
            verseText = itemView.findViewById(R.id.tvVerse_text_bookmark);
        }
    }
}
