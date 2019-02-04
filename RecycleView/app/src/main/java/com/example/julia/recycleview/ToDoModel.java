package com.example.julia.recycleview;

/**
 * Created by julia on 3/22/2018.
 */

public class ToDoModel {
    private String title;
    private int priority;
    private Boolean complete;

    public ToDoModel(String title, int priority, Boolean complete) {
        this.title = title;
        this.priority = priority;
        this.complete = complete;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public Boolean getComplete() {
        return complete;
    }

    public void setComplete(Boolean complete) {
        this.complete = complete;
    }
}
