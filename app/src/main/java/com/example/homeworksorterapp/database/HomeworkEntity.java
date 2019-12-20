package com.example.homeworksorterapp.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "homeworks")
public class HomeworkEntity {

    public enum Day {
        day1,
        day2,
        day3,
        day4,
        weekend
    }

    public enum Week {
        week1,
        week2,
        week3,
        week4,
        week5,
        week6,
        week7,
        week8
    }

    @PrimaryKey(autoGenerate = true)
    private int ID;

    @ColumnInfo
    private String title;

    @ColumnInfo
    private String description;

    @ColumnInfo
    private String day;

    @ColumnInfo
    private String week;

    @ColumnInfo
    private boolean complete;

    public HomeworkEntity(int ID, String title, String description, String day, String week, boolean complete) {
        this.ID = ID;
        this.title = title;
        this.description = description;
        this.day = day;
        this.week = week;
        this.complete = complete;
    }

    @Ignore
    public HomeworkEntity(String title, String description, Day day, Week week, boolean complete) {
        this.title = title;
        this.description = description;
        this.day = day.name();
        this.week = week.name();
        this.complete = complete;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Day getDay() {
        return Day.valueOf(day);
    }

    public void setDay(Day day) {
        this.day = day.name();
    }

    public Week getWeek() {
        return Week.valueOf(week);
    }

    public void setWeek(Week week) {
        this.week = week.name();
    }

    public boolean isComplete() {
        return complete;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }
}
