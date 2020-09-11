package com.example.todolist.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todolist.Activity.TaskScreenActivity;
import com.example.todolist.Class.ApiRequest;
import com.example.todolist.Class.RetrofitAdapter;
import com.example.todolist.Model.AllTasks.AllTasks;
import com.example.todolist.Model.DelTask.DelTask;
import com.example.todolist.Model.DelTask.DelTaskResponse;
import com.example.todolist.Model.TaskId;
import com.example.todolist.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AllTaskAdapter extends RecyclerView.Adapter<AllTaskAdapter.MyViewHolder> {

    Context context;
    AllTasks allTasks;

    public AllTaskAdapter(Context context, AllTasks allTasks){
        this.context = context;
        this.allTasks = allTasks;
    }

    Retrofit retrofit = RetrofitAdapter.getinstance(ApiRequest.BASE_URL, context);
    private ApiRequest retrofitCall = retrofit.create(ApiRequest.class);

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
        if(allTasks.getData().get(position).getCompleted())
            holder.done.setImageResource(R.drawable.checked);
        else
            holder.done.setImageResource(R.drawable.unchecked);
    }

    @Override
    public int getItemCount() {
        return allTasks.getData().size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView taskTitle;
        RelativeLayout taskItem;
        ImageView done, delete;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            taskTitle = itemView.findViewById(R.id.tv_task_item);
            done = itemView.findViewById(R.id.iv_task_item_done);
            delete = itemView.findViewById(R.id.iv_task_item_delete);
            taskItem = itemView.findViewById(R.id.rl_task_item);

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DelTask delList = new DelTask();
                    delList.setTaskId(allTasks.getData().get(getAdapterPosition()).getId());
                    try{
                        Call<DelTaskResponse> call = retrofitCall.deleteList(delList);
                        call.enqueue(new Callback<DelTaskResponse>() {
                            @Override
                            public void onResponse(Call<DelTaskResponse> call, Response<DelTaskResponse> response) {
                                if(response.isSuccessful()){
                                    Log.e("Response",""+response.isSuccessful());
                                    Toast.makeText(context, "Successfully deleted List", Toast.LENGTH_LONG).show();
                                    allTasks.getData().remove(getAdapterPosition());
                                    notifyDataSetChanged();
                                }
                            }

                            @Override
                            public void onFailure(Call<DelTaskResponse> call, Throwable t) {
                                Toast.makeText(context, "Something went wrong", Toast.LENGTH_LONG).show();
                            }
                        });
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            });

            done.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //((ListScreenActivity)context).getListPutCall(getAdapterPosition());
                    TaskId data = new TaskId(allTasks.getData().get(getAdapterPosition()).getId());
                    try {
                        Call<AllTasks> call = retrofitCall.getUpdatedTasks(data);
                        call.enqueue(new Callback<AllTasks>() {
                            @Override
                            public void onResponse(Call<AllTasks> call, Response<AllTasks> response) {
                                allTasks = response.body();
                                notifyDataSetChanged();
                            }

                            @Override
                            public void onFailure(Call<AllTasks> call, Throwable t) {
                                Toast.makeText(context, "Something went wrong", Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                }
            });
        }
    }
}
