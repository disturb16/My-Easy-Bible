package com.example.jramos.myeasybible.models;

public class Verse {

    private String text;
    private Integer verseNumber;

    public Verse(Integer _verseNumber, String _text){
        text = _text;
        verseNumber = _verseNumber;
    }

    public Integer getVerseNumber() {
        return verseNumber;
    }

    public String getText() {
        return text;
    }
}
