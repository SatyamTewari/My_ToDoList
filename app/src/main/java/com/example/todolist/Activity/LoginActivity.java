package com.example.todolist.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.todolist.Class.ApiRequest;
import com.example.todolist.Class.RetrofitAdapter;
import com.example.todolist.Model.Login.Login;
import com.example.todolist.Model.Login.LoginRes;
import com.example.todolist.R;

import java.util.HashSet;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.todolist.Class.AddCookiesInterceptor.PREF_COOKIES;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    EditText username, password;
    TextView checkLogin, signup;
    Context context = this;


    Retrofit retrofit = RetrofitAdapter.getinstance(ApiRequest.BASE_URL, context);
    private ApiRequest retrofitCall = retrofit.create(ApiRequest.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // checking if successful login exists before or not
        HashSet<String> preferences = (HashSet<String>) PreferenceManager.getDefaultSharedPreferences(context).getStringSet(PREF_COOKIES, new HashSet<String>());
        if(preferences != null){
            startActivity(new Intent(this, ListScreenActivity.class));
            finish();
        }

        username = findViewById(R.id.et_login_username);
        password = findViewById(R.id.et_login_password);
        checkLogin = findViewById(R.id.tv_login_continue);
        signup = findViewById(R.id.tv_login_signup);

        checkLogin.setOnClickListener(this);
        signup.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch(v.getId()){
            case R.id.tv_login_continue:
                checkLogin();
                break;
            case R.id.tv_login_signup:
                startActivity(new Intent(this, SignUpActivity.class));
                finish();
                break;
        }
    }

    private void checkLogin(){
        Login data = new Login(username.getText().toString(), password.getText().toString());
        Call<LoginRes> call = retrofitCall.login(data);
        call.enqueue(new Callback<LoginRes>() {
            @Override
            public void onResponse(Call<LoginRes> call, Response<LoginRes> response) {
                //Log.e("cookie test 1", response.body().getToken());
                if(response.isSuccessful()){
                    Log.e("cookie test 1", response.body().getToken());
                    startActivity(new Intent(context, ListScreenActivity.class));
                }
            }

            @Override
            public void onFailure(Call<LoginRes> call, Throwable t) {

            }
        });
    }
}