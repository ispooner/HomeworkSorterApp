package com.example.homeworksorterapp;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.homeworksorterapp.database.HomeworkEntity;

import java.util.List;

public class HomeworkRecycler extends RecyclerView.Adapter<HomeworkRecycler.HomeworkHolder> {
    List<HomeworkEntity> homeworks;

    HomeworkDelegate delegate;

    public interface HomeworkDelegate {
        public void onHomeworkClick(HomeworkEntity homework);
    }

    public void setDelegate(HomeworkDelegate delegate) {
        this.delegate = delegate;
    }

    public void setHomeworks(List<HomeworkEntity> works) {
        homeworks = works;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public HomeworkHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.homework_entry, parent, false);
        return new HomeworkHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeworkHolder holder, final int position) {
        Log.d("TAG_H", "onBindViewHolder: Binding view");
        HomeworkEntity entity = homeworks.get(position);
        holder.entryTitle.setText(entity.getTitle());
        holder.entryDate.setText(entity.getDate());
        holder.entryComplete.setText("Completed: " + entity.isComplete());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delegate.onHomeworkClick(homeworks.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return homeworks.size();
    }

    class HomeworkHolder extends RecyclerView.ViewHolder {

        TextView entryTitle;
        TextView entryDate;
        TextView entryComplete;

        public HomeworkHolder(@NonNull View itemView) {
            super(itemView);

            entryTitle = itemView.findViewById(R.id.entry_title_tv);
            entryDate = itemView.findViewById(R.id.entry_weekday_tv);
            entryComplete = itemView.findViewById(R.id.entry_complete_tv);

        }
    }
}
