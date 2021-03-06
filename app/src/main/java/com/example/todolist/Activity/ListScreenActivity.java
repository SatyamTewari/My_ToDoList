package com.example.todolist.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.todolist.Adapter.AllListAdapter;
import com.example.todolist.Class.ApiRequest;

import com.example.todolist.Class.CustomDialogAddNewList;
import com.example.todolist.Class.RetrofitAdapter;
import com.example.todolist.Model.AllLists.AllLists;
import com.example.todolist.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ListScreenActivity extends AppCompatActivity {

    private String TAG = "ListScreenActivity";
    RecyclerView recyclerView;
    ImageView imageView;
    LinearLayout progressBar;
    AllLists allLists;
    AllListAdapter allListAdapter;
    Context context = this;
    Retrofit retrofit = RetrofitAdapter.getinstance(ApiRequest.BASE_URL, context);
    private ApiRequest retrofitCall = retrofit.create(ApiRequest.class);
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_screen);

        recyclerView = findViewById(R.id.rv_list_screen);
        imageView = findViewById(R.id.iv_list_screen);
        progressBar = findViewById(R.id.list_screen_progress_bar);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        setImageViewListener();
        getFeedDetails();
    }

    private void getFeedDetails(){
        try{
            visibleProgressBar();
            Call<AllLists> call = retrofitCall.getAllLists();
            call.enqueue(new Callback<AllLists>() {
                @Override
                public void onResponse(Call<AllLists> call, Response<AllLists> response) {
                    if(response.isSuccessful()){
                        allLists = response.body();
                        Log.e(TAG, "sucessful Response - "+allLists.getData().size());

                        allListAdapter = new AllListAdapter(context, allLists);
                        recyclerView.setAdapter(allListAdapter);
                    }
                    else{
                        Toast.makeText(ListScreenActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<AllLists> c, Throwable t) {
                    Toast.makeText(ListScreenActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
                }
            });
        }
        catch (Exception e){
            Log.e(TAG,e.toString());
        }
        finally {
            hideProgresBar();
        }
    }

    private void setImageViewListener(){
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CustomDialogAddNewList dialog = new CustomDialogAddNewList(ListScreenActivity.this);
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

    public void hideProgresBar(){
        progressBar.setVisibility(View.GONE);
    }

}