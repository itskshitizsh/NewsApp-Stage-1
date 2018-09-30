package com.itskshitizsh.newsapps1.classes;

import android.graphics.drawable.Drawable;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;

public class News {
    private String newsTitle;
    private String newsCategory;
    private String newsDate;
    private String newsUrl;
    private String newsAuthor;
    private Drawable drawableRound;
    private String newsAuthorUrl;

    private ColorGenerator generator = ColorGenerator.MATERIAL;

    public News(String newsTitle, String newsCategory, String newsDate, String newsUrl, String newsAuthor,String newsAuthorUrl) {
        this.newsTitle = newsTitle;
        this.newsCategory = newsCategory;
        this.newsDate = newsDate;
        this.newsUrl = newsUrl;
        this.newsAuthor = newsAuthor;
        this.newsAuthorUrl = newsAuthorUrl;
        String initials = newsAuthor.substring(0,1).concat(newsAuthor.substring(newsAuthor.indexOf(" ")+1,newsAuthor.indexOf(" ")+2));
        initializeImage(initials);
    }

    private void initializeImage(String initials) {
        drawableRound = TextDrawable.builder().buildRoundRect(initials,generator.getRandomColor(),12);
    }


    public String getNewsAuthorUrl() {
        return newsAuthorUrl;
    }

    public Drawable getDrawableRound() {
        return drawableRound;
    }

    public String getNewsTitle() {
        return newsTitle;
    }

    public String getNewsCategory() {
        return newsCategory;
    }

    public String getNewsDate() {
        return newsDate;
    }

    public String getNewsUrl() {
        return newsUrl;
    }

    public String getNewsAuthor() {
        return newsAuthor;
    }

}
