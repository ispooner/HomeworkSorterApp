package com.example.homeworksorterapp.database;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "homeworks")
public class HomeworkEntity implements Parcelable {

    protected HomeworkEntity(Parcel in) {
        ID = in.readInt();
        title = in.readString();
        description = in.readString();
        day = Day.valueOf(in.readString());
        week = Week.valueOf(in.readString());
        complete = in.readByte() != 0;
    }

    public static final Creator<HomeworkEntity> CREATOR = new Creator<HomeworkEntity>() {
        @Override
        public HomeworkEntity createFromParcel(Parcel in) {
            return new HomeworkEntity(in);
        }

        @Override
        public HomeworkEntity[] newArray(int size) {
            return new HomeworkEntity[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(ID);
        dest.writeString(title);
        dest.writeString(description);
        dest.writeString(day.toString());
        dest.writeString(week.toString());
        dest.writeByte((byte) (complete ? 1 : 0));
    }

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
    private Day day;

    @ColumnInfo
    private Week week;

    @ColumnInfo
    private boolean complete;

    public HomeworkEntity(int ID, String title, String description, Day day, Week week, boolean complete) {
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
        this.day = day;
        this.week = week;
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
        return day;
    }

    public void setDay(Day day) {
        this.day = day;
    }

    public Week getWeek() {
        return week;
    }

    public void setWeek(Week week) {
        this.week = week;
    }

    public String getDate() {
        return week.name() + " " + day.name();
    }

    public boolean isComplete() {
        return complete;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }
}
