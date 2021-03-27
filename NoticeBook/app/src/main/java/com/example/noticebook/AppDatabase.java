package com.example.noticebook;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Notice.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {
    public abstract NoticeDao noticeDao();
}
