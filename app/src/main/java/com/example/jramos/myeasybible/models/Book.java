package com.example.jramos.myeasybible.models;

import java.util.List;

public class Book {

    String name;
    List<Chapter> chapters;

    public Book(String _bookName, List<Chapter> _chapters){
        name = _bookName;
        chapters = _chapters;
    }

    public String getName() {
        return name;
    }

    public List<Chapter> getChapters() {
        return chapters;
    }

    public Chapter getChapter(Integer chapterNumber){

        Chapter chapterSelected = null;

        for(Chapter chapter : chapters){
            if (chapter.getChapterNumber() == chapterNumber){
                chapterSelected = chapter;
            }
        }

        return chapterSelected;

    }

    public Chapter getChapterAt(int index){
        return chapters.get(index);
    }
}
