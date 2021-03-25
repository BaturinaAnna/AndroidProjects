package com.example.noticebook;

public class Notice {
    private String title;
    private String info;
    private Priority priority;

    public Notice(String title, String info, Priority priority) {
        this.title = title;
        this.info = info;
        this.priority = priority;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }
}
