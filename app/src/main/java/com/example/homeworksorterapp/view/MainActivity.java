package com.example.homeworksorterapp.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.homeworksorterapp.HomeworkRecycler;
import com.example.homeworksorterapp.R;
import com.example.homeworksorterapp.database.HomeworkEntity;
import com.example.homeworksorterapp.presenter.Contract;
import com.example.homeworksorterapp.presenter.HomeworkPresenter;

import java.util.ArrayList;
import java.util.List;

/*
Create an application in MVP architecture used to keep track of training homework's.
        HomeWork(week, day, subject, boolean complete)
        Application will be using a room DB
        The UI should categorize the homeworks by the day of the week. (d1, d2...d3, wkend)
        - use MVP
        - use ROOM
        - RV
        - Use fragments
*/

public class MainActivity extends AppCompatActivity
        implements Contract.HomeworkView, HomeworkRecycler.HomeworkDelegate, InsertHomeworkFragment.SubmitListener {

    public static final int requestNewHomework = 500;
    public static final int requestUpdateHomework = 501;

    RecyclerView recycler;
    HomeworkRecycler homeworkAdapter;

    InsertHomeworkFragment homeworkDetails;

    Spinner daySpinner;
    Button newHomework;

    Contract.HomeworkPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences spref = getSharedPreferences("homework.sp", Context.MODE_PRIVATE);
        Boolean first = spref.getBoolean("first", true);

        presenter = new HomeworkPresenter(this, this);


        if(first) {
            SharedPreferences.Editor edit = spref.edit();
            edit.putBoolean("first", false);
            edit.commit();
            presenter.insertHomework(new HomeworkEntity("Room Database", "Create a room database", HomeworkEntity.Day.day2, HomeworkEntity.Week.week3, false));
            presenter.insertHomework(new HomeworkEntity("EventBus", "Implement an EventBus", HomeworkEntity.Day.day3, HomeworkEntity.Week.week3, false));
            presenter.insertHomework(new HomeworkEntity("Layout Design", "Design project layout", HomeworkEntity.Day.day4, HomeworkEntity.Week.week3, false));
            presenter.insertHomework(new HomeworkEntity("BroadcastReceiver", "Receive a broadcast", HomeworkEntity.Day.weekend, HomeworkEntity.Week.week3, false));
            presenter.insertHomework(new HomeworkEntity("MVP", "Implement MVP in your list", HomeworkEntity.Day.day1, HomeworkEntity.Week.week2, true));
            presenter.insertHomework(new HomeworkEntity("SQLite Database", "Use SQLite to store data", HomeworkEntity.Day.day2, HomeworkEntity.Week.week2, true));
        }

        recycler = findViewById(R.id.main_homework_rv);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        homeworkAdapter = new HomeworkRecycler();
        homeworkAdapter.setDelegate(this);
        homeworkAdapter.setHomeworks(new ArrayList<HomeworkEntity>());

        homeworkDetails = new InsertHomeworkFragment();

        daySpinner = findViewById(R.id.main_category_sp);
        newHomework = findViewById(R.id.main_newHomework_bt);
        newHomework.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle send = new Bundle();
                send.putInt("requestCode", requestNewHomework);
                homeworkDetails.setArguments(send);
                getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.main_fragment_fl, homeworkDetails)
                        .addToBackStack(homeworkDetails.getTag())
                        .commit();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.getCategories();
        presenter.getHomeworkSelection(HomeworkEntity.Day.day1);
    }

    @Override
    public void receiveCategories(List<HomeworkEntity.Day> days) {
        daySpinner.setAdapter(new ArrayAdapter<HomeworkEntity.Day>(this,
                R.layout.support_simple_spinner_dropdown_item, days));
        daySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                presenter.getHomeworkSelection(HomeworkEntity.Day.values()[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                presenter.getHomeworks();
            }
        });
    }

    @Override
    public void receiveHomeworks(List<HomeworkEntity> homeworks) {
        Log.d("TAG_R", "receiveHomeworks: received: " + homeworks.size());
        homeworkAdapter = new HomeworkRecycler();
        homeworkAdapter.setHomeworks(homeworks);
        homeworkAdapter.setDelegate(this);
        recycler.setAdapter(homeworkAdapter);
    }

    @Override
    public void receiveHomework(HomeworkEntity homework) {
        //TODO: Start the fragment.
    }

    @Override
    public void success(int operation) {
        switch (operation) {
            case 0:
                Log.d("TAG_S", "success: Insert succeeded");
                break;
            case 1:
                Log.d("TAG_S", "success: Update succeeded");
                break;
            case 2:
                Log.d("TAG_S", "success: Delete succeeded");
                break;
            default:
                Log.d("TAG_S", "success: Nothing happened here");
                break;
        }
    }

    @Override
    public void onHomeworkClick(HomeworkEntity homework) {
        Bundle send = new Bundle();
        send.putParcelable("homework", homework);
        send.putInt("requestCode", requestUpdateHomework);
        homeworkDetails.setArguments(send);
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.main_fragment_fl, homeworkDetails)
                .addToBackStack(homeworkDetails.getTag())
                .commit();
    }


    @Override
    public void onSubmit(HomeworkEntity homework, int requestCode) {
        switch (requestCode) {
            case requestNewHomework:
                presenter.insertHomework(homework);
                homeworkAdapter.notifyDataSetChanged();
                Log.d("TAG_M", "onSubmit: inserted new");
                getSupportFragmentManager().popBackStack();
                break;
            case requestUpdateHomework:
                presenter.updateHomework(homework);
                homeworkAdapter.notifyDataSetChanged();
                Log.d("TAG_M", "onSubmit: updated old");
                getSupportFragmentManager().popBackStack();
                break;
            default:
                //Add any other request codes I desire.
                break;
        }
    }
}
