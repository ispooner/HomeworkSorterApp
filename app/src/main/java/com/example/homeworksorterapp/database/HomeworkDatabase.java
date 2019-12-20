package com.example.homeworksorterapp.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {HomeworkEntity.class}, version = 1)
@TypeConverters(HomeworkConverter.class)
public abstract class HomeworkDatabase extends RoomDatabase {
    public abstract HomeworkDAO homeworkDAO();
}
