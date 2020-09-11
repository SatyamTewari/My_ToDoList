package com.example.todolist.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.todolist.Class.ApiRequest;
import com.example.todolist.Class.RetrofitAdapter;
import com.example.todolist.Model.Login.Login;
import com.example.todolist.Model.Login.LoginRes;
import com.example.todolist.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static com.example.todolist.Class.AddCookiesInterceptor.PREF_COOKIES;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    TextView login, register;
    EditText username, password1, password2;

    Retrofit retrofit = RetrofitAdapter.getinstance(ApiRequest.BASE_URL, this);
    private ApiRequest retrofitCall = retrofit.create(ApiRequest.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        SharedPreferences.Editor memes = PreferenceManager.getDefaultSharedPreferences(this).edit();
        memes.remove(PREF_COOKIES).apply();
        memes.commit();

        login = findViewById(R.id.tv_signup_login);
        register = findViewById(R.id.tv_signup_continue);
        username = findViewById(R.id.et_signup_username);
        password1 = findViewById(R.id.et_signup_password);
        password2 = findViewById(R.id.et_signup_confirm_pass);

        login.setOnClickListener(this);
        register.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch(v.getId()){
            case R.id.tv_signup_login: startActivity(new Intent(this, LoginActivity.class));
                finish();
                break;
            case R.id.tv_signup_continue:
                register();
                break;
        }
    }

    private void register(){
        String pass1 = password1.getText().toString();
        String pass2 = password2.getText().toString();
        if(pass1.equals(pass2)){
            Login data = new Login(username.getText().toString(), password1.getText().toString());
            Call<LoginRes> call = retrofitCall.register(data);
            call.enqueue(new Callback<LoginRes>() {
                @Override
                public void onResponse(Call<LoginRes> call, Response<LoginRes> response) {
                    if(response.isSuccessful()){
                        startActivity(new Intent(SignUpActivity.this, ListScreenActivity.class));
                        finish();
                    }
                    else{
                        Toast.makeText(SignUpActivity.this, "Confirm Password doesn't match", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<LoginRes> call, Throwable t) {
                    Toast.makeText(SignUpActivity.this, "Confirm Password doesn't match", Toast.LENGTH_LONG).show();
                }
            });
        }
        else{
            Toast.makeText(this, "Confirm Password doesn't match", Toast.LENGTH_LONG).show();
        }
    }
}