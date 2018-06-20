package com.example.jramos.myeasybible;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.example.jramos.myeasybible.adapters.DashboardAdapter;
import com.example.jramos.myeasybible.utils.Dialogs;

public class MainActivity extends BaseActivity {

    RecyclerView lvFreqBooks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final com.michaldrabik.tapbarmenulib.TapBarMenu tabBarMenu = findViewById(R.id.tabBarMenu);

        tabBarMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tabBarMenu.toggle();
            }
        });

        final Dialogs.SearchBookDialog searchdialog = new Dialogs.SearchBookDialog(this);
        findViewById(R.id.fabSearch).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchdialog.show();
            }
        });

        RecyclerView rvContainer = findViewById(R.id.rv_dashboar_container);
        rvContainer.setLayoutManager(new StaggeredGridLayoutManager(3, LinearLayoutManager.VERTICAL));
        rvContainer.setAdapter( new DashboardAdapter(this));
    }
}
