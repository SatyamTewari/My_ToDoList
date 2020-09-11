package com.example.todolist.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.example.todolist.Adapter.AllListAdapter;
import com.example.todolist.Class.ApiRequest;
import com.example.todolist.Class.RetrofitAdapter;
import com.example.todolist.Model.AllLists.AllLists;
import com.example.todolist.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SplashScreenActivity extends AppCompatActivity {

    Context context = this;
    String TAG = "SplashScreenActivity";

    Retrofit retrofit = RetrofitAdapter.getinstance(ApiRequest.BASE_URL, context);
    private ApiRequest retrofitCall = retrofit.create(ApiRequest.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        checkLogin();
    }

    private void checkLogin(){
        try{
            Call<AllLists> call = retrofitCall.getAllLists();
            call.enqueue(new Callback<AllLists>() {
                @Override
                public void onResponse(Call<AllLists> call, Response<AllLists> response) {
                    if(response.isSuccessful()){
                        Log.e(TAG, "sucessful Response - ");
                        startActivity(new Intent(SplashScreenActivity.this, ListScreenActivity.class));
                        finish();
                    }
                    else{
                        if(response.message().equals("BAD REQUEST")){
                            startActivity(new Intent(SplashScreenActivity.this, LoginActivity.class));
                            finish();
                        }
                        else
                            Toast.makeText(SplashScreenActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<AllLists> c, Throwable t) {
                    Toast.makeText(SplashScreenActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
                }
            });
        }
        catch (Exception e){
            Log.e(TAG,e.toString());
        }
    }
}