package com.example.homeworksorterapp.presenter;

import com.example.homeworksorterapp.database.HomeworkEntity;

import java.util.List;

public interface Contract {

    interface HomeworkPresenter {
        public void getCategories();

        public void getHomeworks();

        public void getHomeworkSelection(HomeworkEntity.Day day);

        public void getHomework(int id);

        public void insertHomework(HomeworkEntity homework);

        public void updateHomework(HomeworkEntity homework);

        public void deleteHomework(HomeworkEntity homework);
    }

    interface HomeworkView {
        public void receiveCategories(List<HomeworkEntity.Day> days);

        public void receiveHomeworks(List<HomeworkEntity> homeworks);

        public void receiveHomework(HomeworkEntity homework);

        public void success(int operation);
    }

}
