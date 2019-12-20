package com.example.homeworksorterapp.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import static android.icu.text.MessagePattern.ArgType.SELECT;


@Dao
public interface HomeworkDAO {

    @Query("SELECT * FROM homeworks")
    List<HomeworkEntity> getAllHomeworks();

    @Query("SELECT * FROM homeworks WHERE day = :day")
    List<HomeworkEntity> getDayHomeworks(HomeworkEntity.Day day);

    @Insert
    void insertHomework(HomeworkEntity homework);

    @Update
    void updateHomework(HomeworkEntity homework);

    @Delete
    void deleteHomework(HomeworkEntity homework);

}
