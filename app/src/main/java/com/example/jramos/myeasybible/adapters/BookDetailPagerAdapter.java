package com.example.jramos.myeasybible.adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.jramos.myeasybible.BookChapter;
import com.example.jramos.myeasybible.R;
import com.example.jramos.myeasybible.models.Chapter;

import java.util.List;

public class BookDetailPagerAdapter extends FragmentPagerAdapter{

    List<Chapter> chapters;
    Context context;
    String bookname;

    public BookDetailPagerAdapter(FragmentManager fm, List<Chapter> _chapters, Context _context, String _bookname) {
        super(fm);
        chapters = _chapters;
        context = _context;
        bookname = _bookname;
    }

    @Override
    public Fragment getItem(int position) {

        BookChapter bookChapter = BookChapter.newInstance(chapters.get(position), bookname);

        return bookChapter;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return context.getResources().getString(R.string.chapter_tab_title, chapters.get(position).getChapterNumber());
    }

    public Chapter getChapterAt(int index){
        return chapters.get(index);
    }

    @Override
    public int getCount() {
        return chapters.size();
    }
}
