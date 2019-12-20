package com.example.homeworksorterapp.view;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.homeworksorterapp.R;
import com.example.homeworksorterapp.database.HomeworkEntity;

import java.util.ArrayList;


public class InsertHomeworkFragment extends Fragment {

    interface SubmitListener {
        public void onSubmit(HomeworkEntity homework, int requestCode);
    }

    SubmitListener listener;

    Button submit;
    Spinner week;
    Spinner day;
    CheckBox completed;
    EditText title;
    EditText description;

    HomeworkEntity homework;
    int requestCode;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_insert_homework, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        title = view.findViewById(R.id.insert_title_tv);
        description = view.findViewById(R.id.insert_description_tv);
        week = view.findViewById(R.id.insert_week_sp);
        day = view.findViewById(R.id.insert_day_sp);
        completed = view.findViewById(R.id.insert_complete_cb);
        submit = view.findViewById(R.id.insert_submit_bt);

        ArrayList<HomeworkEntity.Day> days = new ArrayList<>();
        ArrayList<HomeworkEntity.Week> weeks = new ArrayList<>();

        for(HomeworkEntity.Day d : HomeworkEntity.Day.values()) {
            days.add(d);
        }
        for(HomeworkEntity.Week w : HomeworkEntity.Week.values()) {
            weeks.add(w);
        }

        day.setAdapter(new ArrayAdapter<HomeworkEntity.Day>(this.getContext(),
                R.layout.support_simple_spinner_dropdown_item, days));
        day.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                homework.setDay(HomeworkEntity.Day.values()[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        week.setAdapter(new ArrayAdapter<HomeworkEntity.Week>(getContext(),
                R.layout.support_simple_spinner_dropdown_item, weeks));
        week.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                homework.setWeek(HomeworkEntity.Week.values()[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        if((requestCode = getArguments().getInt("requestCode")) == MainActivity.requestUpdateHomework) {
            Log.d("TAG_F", "onViewCreated: request update: " + requestCode);
            homework = getArguments().getParcelable("homework");
        }
        else {
            Log.d("TAG_F", "onViewCreated: request new: " + requestCode);
            homework = new HomeworkEntity("", "", HomeworkEntity.Day.day1, HomeworkEntity.Week.week1, false);
        }

        setViews(homework);

    }

    public void setViews(final HomeworkEntity homework) {
        title.setText(homework.getTitle());
        description.setText(homework.getDescription());
        week.setSelection(homework.getWeek().ordinal());
        day.setSelection(homework.getDay().ordinal());
        completed.setChecked(homework.isComplete());
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                homework.setTitle(title.getText().toString().trim());
                homework.setDescription(description.getText().toString().trim());
                // set week done above.
                // set day done above in onItemSelectedListener
                homework.setComplete(completed.isChecked());
                listener.onSubmit(homework, requestCode);

            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof SubmitListener) {
            listener = (SubmitListener) context;
        }
    }
}
