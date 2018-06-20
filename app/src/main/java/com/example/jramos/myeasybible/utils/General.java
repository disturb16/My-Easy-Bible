package com.example.jramos.myeasybible.utils;

import android.content.Context;
import android.widget.Toast;

public class General {

    public static void showToast(Context _context, String msg){
        Toast.makeText(_context, msg, Toast.LENGTH_SHORT).show();
    }
}
