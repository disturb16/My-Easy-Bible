package com.example.jramos.myeasybible.adapters;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jramos.myeasybible.BookmarksActivity;
import com.example.jramos.myeasybible.FavoriteBooksActivity;
import com.example.jramos.myeasybible.R;
import com.example.jramos.myeasybible.RecentBooksActivity;

import java.util.ArrayList;
import java.util.List;

public class DashboardAdapter extends RecyclerView.Adapter<DashboardAdapter.DashboardHolder> {

    List<DashboardItem> items = new ArrayList<>();
    Activity activity;

    private class DashboardItem{
        private String Header, description;
        private Integer icon_id, icon_backg_id;
        private Class<?> link;

        DashboardItem(String _header, String _description, Integer _iconImage, Integer _imgBackgroundId, Class<?> _link){
            Header = _header;
            description = _description;
            icon_id = _iconImage;
            icon_backg_id = _imgBackgroundId;
            link = _link;
        }

        public Integer getIcon_id() {
            return icon_id;
        }

        public String getDescription() {
            return description;
        }

        public String getHeader() {
            return Header;
        }

        public Integer getIcon_backg_id() {
            return icon_backg_id;
        }

        public Class<?> linkTo() {
            return link;
        }
    }

    public DashboardAdapter(Activity _activity){

        activity = _activity;

        items.add(
                new DashboardItem(
                        "Libros recientes",
                        "",
                        R.drawable.ic_books,
                        R.drawable.circle_purple,
                        RecentBooksActivity.class
                ));

        items.add(
                new DashboardItem(
                        "Continuar leyendo",
                        "",
                        R.drawable.ic_chill,
                        R.drawable.circle_orange,
                        null
                ));

        items.add(
                new DashboardItem(
                        "Categorias",
                        "",
                        R.drawable.ic_dashboard,
                        R.drawable.circle_dark_grey,
                        null
                ));   

        items.add(
                new DashboardItem(
                        "Mis Favoritos",
                        "",
                        R.drawable.ic_favorite,
                        R.drawable.circle_dark_red,
                        FavoriteBooksActivity.class
                ));

        items.add(
                new DashboardItem(
                        "Lectura del día",
                        "",
                        R.drawable.ic_chat,
                        R.drawable.circle_blue,
                        null
                ));

        items.add(
                new DashboardItem(
                        "Marcadores",
                        "",
                        R.drawable.ic_news,
                        R.drawable.circle_dark_green,
                        BookmarksActivity.class
                ));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @NonNull
    @Override
    public DashboardHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dashboard_item, parent, false);
        return new DashboardHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DashboardHolder holder, int position) {
        final DashboardItem currentItem = items.get(position);

        holder.header.setText(currentItem.getHeader());
        holder.imgBackg.setImageResource(currentItem.icon_backg_id);

        holder.imgIcon.setImageResource(currentItem.icon_id);

        holder.root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentItem.linkTo() == null)
                    return;

                Intent i = new Intent(activity, currentItem.linkTo());
                activity.startActivity(i);
            }
        });
    }

    class DashboardHolder extends RecyclerView.ViewHolder{

        TextView header, description;
        ImageView imgIcon, imgBackg;
        ConstraintLayout root;

        public DashboardHolder(View itemView) {
            super(itemView);

            header = itemView.findViewById(R.id.tvHeader);
            imgBackg = itemView.findViewById(R.id.img_ic_background);
            imgIcon = itemView.findViewById(R.id.img_ic);
            root = itemView.findViewById(R.id.dashboard_item_root);

        }
    }


}
