package com.ankitkumar.app.todoapp.karya;

public class Task {

    String title;
    String date;
    String description;
    boolean status;
    int index;


    public Task() {
    }

    public Task(String title ,String date,
    String description,Boolean status ,int index ) {
        this.title = title;
        this.date = date;
        this.description = description;
        this.status = status;
        this.index = index;


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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
