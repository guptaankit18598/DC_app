package com.assoftek.splashscreen.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.assoftek.splashscreen.HomeActivity.HomeActivity;
import com.assoftek.splashscreen.R;
import com.assoftek.splashscreen.SignUp.Otp_verify;
import com.assoftek.splashscreen.SignUp.SignUp;

public class login extends AppCompatActivity {

    Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login = (Button) findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(login.this, HomeActivity.class);
                startActivity(intent);
            }
        });

    }
}