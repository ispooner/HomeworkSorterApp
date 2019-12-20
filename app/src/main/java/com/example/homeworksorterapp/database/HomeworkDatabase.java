package com.example.homeworksorterapp.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {HomeworkEntity.class}, version = 1)
public abstract class HomeworkDatabase extends RoomDatabase {
    public abstract HomeworkDAO homeworkDAO();
}
