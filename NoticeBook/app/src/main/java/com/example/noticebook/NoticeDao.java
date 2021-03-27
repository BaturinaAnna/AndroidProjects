package com.example.noticebook;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface NoticeDao {
    @Query("SELECT id FROM notices WHERE info = :info AND title = :title")
    Long getId(String info, String title);
    @Insert
    void insert(Notice notice);
    @Delete
    void delete(Notice notice);
}
