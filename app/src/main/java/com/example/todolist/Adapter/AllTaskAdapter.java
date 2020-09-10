package com.example.todolist.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todolist.Model.AllTasks.AllTasks;
import com.example.todolist.R;

public class AllTaskAdapter extends RecyclerView.Adapter<AllTaskAdapter.MyViewHolder> {

    Context context;
    AllTasks allTasks;

    public AllTaskAdapter(Context context, AllTasks allTasks){
        this.context = context;
        this.allTasks = allTasks;
    }
    @NonNull
    @Override
    public AllTaskAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.task_item_design, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AllTaskAdapter.MyViewHolder holder, int position) {
        holder.taskTitle.setText(allTasks.getData().get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return allTasks.getData().size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView taskTitle;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            taskTitle = itemView.findViewById(R.id.tv_task_item);
        }
    }
}
