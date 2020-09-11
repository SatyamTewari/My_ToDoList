package com.example.todolist.Class;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.todolist.Activity.ListScreenActivity;
import com.example.todolist.Activity.TaskScreenActivity;
import com.example.todolist.Model.CreateNewList.CreateList;
import com.example.todolist.Model.CreateNewList.PostList;
import com.example.todolist.Model.CreateNewTask.CreateTask;
import com.example.todolist.Model.CreateNewTask.PostTask;
import com.example.todolist.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CustomDialogAddNewTask extends Dialog implements android.view.View.OnClickListener {

    public Activity c;
    String caller;
    public Dialog d;
    EditText title;
    TextView continu, cancel;
    Retrofit retrofit = RetrofitAdapter.getinstance(ApiRequest.BASE_URL, c);
    private ApiRequest retrofitCall = retrofit.create(ApiRequest.class);

    public CustomDialogAddNewTask(Activity a) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.add_new_list_popup);

        title = findViewById(R.id.et_add_list);
        continu = findViewById(R.id.tv_add_list_continue);
        cancel = findViewById(R.id.tv_add_list_cancel);

        setCancelable(false);

        continu.setOnClickListener(this);
        cancel.setOnClickListener(this);


    }

    private void createTask(){
        CreateTask createTask = new CreateTask(((TaskScreenActivity)c).ID ,title.getText().toString());
        try {
            Call<PostTask> call = retrofitCall.createTask(createTask);
            call.enqueue(new Callback<PostTask>() {
                @Override
                public void onResponse(Call<PostTask> call, Response<PostTask> response) {
                    if(response.isSuccessful()){
                        Toast.makeText(getContext(), "Successfully created New Task", Toast.LENGTH_LONG).show();
                        ((TaskScreenActivity)c).loadAgain();
                    }
                }

                @Override
                public void onFailure(Call<PostTask> call, Throwable t) {
                    Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_LONG).show();
                }
            });
        }catch(Exception e){
            e.printStackTrace();
            Toast.makeText(getContext(), "Alert !! Catch me aaya toh matlab garbar", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_add_list_continue:
                createTask();
                dismiss();
                break;
            case R.id.tv_add_list_cancel:
                dismiss();
                break;
            default:
                break;
        }
    }
}