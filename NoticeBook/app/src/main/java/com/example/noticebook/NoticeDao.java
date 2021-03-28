package com.example.noticebook;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface NoticeDao {
    @Query("SELECT id FROM notices WHERE info = :info AND title = :title AND priority = :priority")
    Long getId(String info, String title, int priority);
    @Query("SELECT * FROM notices")
    List<Notice> getNotices();
    @Insert
    void insert(Notice notice);
    @Delete
    void delete(Notice notice);
    @Update
    void update(Notice notice);
}
