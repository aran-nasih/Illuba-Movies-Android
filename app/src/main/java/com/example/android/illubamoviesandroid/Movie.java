package com.example.android.illubamoviesandroid;

import java.io.Serializable;

public class Movie implements Serializable {

    private String title;
    private String date;
    private String poster;
    private double vote;
    private String description;

    public Movie(String title, String date, String poster, double vote, String description) {
        this.title = title;
        this.date = date;
        this.poster = poster;
        this.vote = vote;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public double getVote() {
        return vote;
    }

    public void setVote(double vote) {
        this.vote = vote;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
