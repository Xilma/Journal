package com.example.android.journal.presenters;

public class Journal {
    private String title, description;

    public Journal(){

    }

    public Journal(String title, String description){
        title = title;
        description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
