package com.example.noticebook;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface NoticeDao {
    @Insert
    void insert(Notice notice);
}
