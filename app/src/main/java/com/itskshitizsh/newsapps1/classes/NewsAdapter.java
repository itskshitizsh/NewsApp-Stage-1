package com.itskshitizsh.newsapps1.classes;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.itskshitizsh.newsapps1.R;

import java.util.ArrayList;

public class NewsAdapter extends ArrayAdapter<News> {

    public NewsAdapter(Context context, ArrayList<News> news) {
        super(context, 0, news);
    }

    @NonNull
    @Override
    public View getView(int position,View convertView,@NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.menu_item, parent, false);
        }

        News currentNews = getItem(position);

        TextView newsTitleTextView = listItemView.findViewById(R.id.titleTextView);
        String title = currentNews != null ? currentNews.getNewsTitle() : null;
        newsTitleTextView.setText(title);

        TextView newsCategoryTextView = listItemView.findViewById(R.id.categoryTextView);
        String category = currentNews != null ? currentNews.getNewsCategory() : null;
        newsCategoryTextView.setText(category);

        TextView newsDateTextView = listItemView.findViewById(R.id.dateTextView);
        String date = currentNews != null ? currentNews.getNewsDate() : null;
        newsDateTextView.setText(date);

        TextView newsAuthorTextView = listItemView.findViewById(R.id.authorTextView);
        String author = currentNews != null ? currentNews.getNewsAuthor() : null;
        newsAuthorTextView.setText(author);

        ImageView textDrawable = listItemView.findViewById(R.id.newsImageView);
        Drawable drawable = currentNews != null ? currentNews.getDrawableRound() : null;
        textDrawable.setImageDrawable(drawable);

        return listItemView;
    }
}

