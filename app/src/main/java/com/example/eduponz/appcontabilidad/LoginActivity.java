package com.example.eduponz.appcontabilidad;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {
    private EditText mPasswordEditText;
    private static final String correctPassword = "112358";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void accessApp(View view) {
        mPasswordEditText = (EditText) findViewById(R.id.login_password_editText);
        String password = mPasswordEditText.getText().toString();
        Intent intent = new Intent(this, MainContentActivity.class);
        startActivity(intent);
    }
        /*
        if (password.equals(correctPassword)) {
            Intent intent = new Intent(this, MainContentActivity.class);
            startActivity(intent);
        }
        */
    }
