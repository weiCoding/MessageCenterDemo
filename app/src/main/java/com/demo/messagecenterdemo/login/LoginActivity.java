package com.demo.messagecenterdemo.login;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.demo.messagecenterdemo.LocalCacheUtils;
import com.demo.messagecenterdemo.R;


public class LoginActivity extends AppCompatActivity {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText usernameEditText = findViewById(R.id.username);
        final Button loginButton = findViewById(R.id.login);

        usernameEditText.setText(LocalCacheUtils.getInstance().getPin());


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_OK);
                LoginActivity.this.finish();
                LocalCacheUtils.getInstance().setPin(usernameEditText.getText().toString());
//                loadingProgressBar.setVisibility(View.VISIBLE);
//                loginViewModel.login(usernameEditText.getText().toString(),
//                        passwordEditText.getText().toString());
            }
        });
    }

}