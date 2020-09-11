package com.example.todolist.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.todolist.Adapter.AllTaskAdapter;
import com.example.todolist.Class.ApiRequest;
import com.example.todolist.Class.CustomDialogAddNewList;
import com.example.todolist.Class.CustomDialogAddNewTask;
import com.example.todolist.Class.RetrofitAdapter;
import com.example.todolist.Model.AllTasks.AllTasks;
import com.example.todolist.Model.ListId;
import com.example.todolist.R;

import java.io.ObjectStreamException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TaskScreenActivity extends AppCompatActivity {

    Context context = this;
    RecyclerView recyclerView;
    TextView pageTitle;
    ImageView addTask;
    LinearLayout progressBar;
    public Integer ID;
    Bundle extras;
    AllTasks allTasks;
    private String TAG = "TaskScreenActivity";

    Retrofit retrofit = RetrofitAdapter.getinstance(ApiRequest.BASE_URL, context);
    private ApiRequest retrofitCall = retrofit.create(ApiRequest.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_screen);

        recyclerView = findViewById(R.id.rv_task_screen);
        pageTitle = findViewById(R.id.tv_task_screen_heading);
        addTask = findViewById(R.id.iv_task_screen);
        progressBar = findViewById(R.id.task_screen_progress_bar);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        extras = getIntent().getExtras();

        if(extras != null){
            ID = extras.getInt("listId");
            pageTitle.setText(extras.getString("list_title"));
        }
        setImageViewListener();
        getFeedDetails();
    }

    private void getFeedDetails(){
        visibleProgressBar();
        try{
        Call<AllTasks> call = retrofitCall.getAllTasks(ID);
        call.enqueue(new Callback<AllTasks>() {
            @Override
            public void onResponse(Call<AllTasks> call, Response<AllTasks> response) {
                if(response.isSuccessful()){
                    allTasks = response.body();
                    AllTaskAdapter allTaskAdapter = new AllTaskAdapter(context, allTasks);
                    recyclerView.setAdapter(allTaskAdapter);
                }
                else{
                    Toast.makeText(TaskScreenActivity.this,"something went wrong", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<AllTasks> call, Throwable t) {
                Log.e(TAG, "failed response - "+t.getMessage());
                Toast.makeText(TaskScreenActivity.this,"something went wrong", Toast.LENGTH_LONG).show();
            }
        });
        }
        catch(Exception e){
            Log.e(TAG, "something went wrong");
            e.printStackTrace();
            progressBar.setVisibility(View.GONE);
            Toast.makeText(TaskScreenActivity.this,"something went wrong", Toast.LENGTH_LONG).show();
        }
        finally {
            hideProgressBar();
        }
    }

    private void setImageViewListener(){
        addTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CustomDialogAddNewTask dialog = new CustomDialogAddNewTask(TaskScreenActivity.this);
                dialog.show();
            }
        });
    }

    public void loadAgain(){
        getFeedDetails();
    }

    public void visibleProgressBar(){
        progressBar.setVisibility(View.VISIBLE);
    }

    public void hideProgressBar(){
        progressBar.setVisibility(View.GONE);
    }
}