package com.example.homeworksorterapp.presenter;


import android.content.Context;

import androidx.room.Room;

import com.example.homeworksorterapp.database.HomeworkDatabase;
import com.example.homeworksorterapp.database.HomeworkEntity;

import java.util.ArrayList;

public class HomeworkPresenter implements Contract.HomeworkPresenter {

    private Contract.HomeworkView view;
    private HomeworkDatabase homedb;

    public HomeworkPresenter(Context context, Contract.HomeworkView v) {
        view = v;
        homedb = Room.databaseBuilder(context, HomeworkDatabase.class, "homeworks.db")
                .allowMainThreadQueries()
                .build();
        //homedb.clearAllTables();
    }

    @Override
    public void getCategories() {
        ArrayList<HomeworkEntity.Day> days = new ArrayList<>();
        for(HomeworkEntity.Day d : HomeworkEntity.Day.values()) {
            days.add(d);
        }
        view.receiveCategories(days);
    }

    @Override
    public void getHomeworks() {
        view.receiveHomeworks(homedb.homeworkDAO().getAllHomeworks());
    }

    @Override
    public void getHomeworkSelection(HomeworkEntity.Day day) {
        view.receiveHomeworks(homedb.homeworkDAO().getDayHomeworks(day));
    }

    @Override
    public void getHomework(int id) {
        view.receiveHomework(homedb.homeworkDAO().getHomework(id));
    }

    @Override
    public void insertHomework(HomeworkEntity homework) {
        homedb.homeworkDAO().insertHomework(homework);
        view.success(0);
    }

    @Override
    public void updateHomework(HomeworkEntity homework) {
        homedb.homeworkDAO().updateHomework(homework);
        view.success(1);
    }

    @Override
    public void deleteHomework(HomeworkEntity homework) {
        homedb.homeworkDAO().deleteHomework(homework);
        view.success(2);
    }
}
