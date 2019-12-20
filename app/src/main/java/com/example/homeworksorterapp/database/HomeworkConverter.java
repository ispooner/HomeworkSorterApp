package com.example.homeworksorterapp.database;

import androidx.room.TypeConverter;

public class HomeworkConverter {

    @TypeConverter
    public static HomeworkEntity.Day toDay(String day) {
        return HomeworkEntity.Day.valueOf(day);
    }

    @TypeConverter
    public static String toString(HomeworkEntity.Day day) {
        return day.name();
    }

    @TypeConverter
    public static HomeworkEntity.Week toWeek(String week) {
        return HomeworkEntity.Week.valueOf(week);
    }

    @TypeConverter
    public static String toString(HomeworkEntity.Week week) {
        return week.name();
    }

}
