package com.example.todolist.Class;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.todolist.Activity.ListScreenActivity;
import com.example.todolist.Model.CreateNewList.CreateList;
import com.example.todolist.Model.CreateNewList.PostList;
import com.example.todolist.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CustomDialogAddNewList extends Dialog implements android.view.View.OnClickListener {

    public Activity c;
    public Dialog d;
    EditText title;
    TextView continu, cancel;
    Retrofit retrofit = new Retrofit.Builder().baseUrl(ApiRequest.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
    private ApiRequest retrofitCall = retrofit.create(ApiRequest.class);

    public CustomDialogAddNewList(Activity a) {
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

    private void createList(){
        CreateList createList = new CreateList(title.getText().toString());
        try {
            Call<PostList> call = retrofitCall.createList(createList);
            call.enqueue(new Callback<PostList>() {
                @Override
                public void onResponse(Call<PostList> call, Response<PostList> response) {
                    if(response.isSuccessful()){
                        Toast.makeText(getContext(), "Successfully created New List", Toast.LENGTH_LONG).show();
                        ((ListScreenActivity)c).loadAgain();
                    }
                }

                @Override
                public void onFailure(Call<PostList> call, Throwable t) {
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
                createList();
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