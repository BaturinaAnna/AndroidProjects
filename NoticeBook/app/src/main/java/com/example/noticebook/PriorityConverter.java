package com.example.noticebook;

import androidx.room.TypeConverter;

public class PriorityConverter {
    @TypeConverter
    public int fromPriority(Priority priority) {
        return priority.getValue();
    }

    @TypeConverter
    public Priority toPriority(int number) {
        return Priority.valueOf(number);
    }
}
