package com.example.jramos.myeasybible.models;

import java.util.List;

public class Chapter {

    private Integer chapterNumber;
    private List<Verse> verses;

    public Chapter(Integer _chapterNumber, List<Verse> _verses){
        chapterNumber = _chapterNumber;
        verses = _verses;
    }

    public Integer getChapterNumber() {
        return chapterNumber;
    }

    public List<Verse> getVerses() {
        return verses;
    }

    public Verse getVerseAt(int index){
        return verses.get(index);
    }
}
