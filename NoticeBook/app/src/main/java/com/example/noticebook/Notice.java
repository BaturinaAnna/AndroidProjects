package com.example.noticebook;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName="notices")
public class Notice {
    @PrimaryKey(autoGenerate = true)
    public Long id;
    @ColumnInfo(name="title")
    public String title;
    @ColumnInfo(name = "info")
    public String info;
    @Ignore
    public Priority priority = Priority.HIGH;

    public Notice() {
    }

    public Notice(Long id, String title, String info, Priority priority) {
        this.id = id;
        this.title = title;
        this.info = info;
        this.priority = Priority.HIGH;
    }

    public Notice(String title, String info, Priority priority) {
        this.title = title;
        this.info = info;
        this.priority = priority;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    @NonNull
    @Override
    public String toString() {
        return "info= " + info + "title = " + title;
    }
}
