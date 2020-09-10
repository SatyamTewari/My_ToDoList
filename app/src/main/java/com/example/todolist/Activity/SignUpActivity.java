package com.example.todolist.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.todolist.R;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    TextView login, register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        login = findViewById(R.id.tv_signup_login);
        register = findViewById(R.id.tv_signup_continue);

        login.setOnClickListener(this);
        register.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch(v.getId()){
            case R.id.tv_signup_login: startActivity(new Intent(this, LoginActivity.class));
                finish();
                break;
        }
    }
}