package com.imcool.julian.carratingapp;

/**
 * Created by Julian on 2/21/2018.
 */

public class Cars {
    private  String name;
    private int selection;
    private String comments;
    private float rating;

    public Cars(String name, int selection, String comments, float rating) {
        this.name = name;
        this.selection = selection;
        this.comments = comments;
        this.rating = rating;
    }


    public String getNames() {
        return name;
    }

    public void setNames(String car) {
        name = car;
    }

    public int getSelection() {
        return selection;
    }

    public void setSelection(int selection) {
        this.selection = selection;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }
}
