package com.example.wereview.ui._login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.wereview.MainActivity;
import com.example.wereview.R;
import com.example.wereview.ui._register.register;

public class login extends AppCompatActivity {

    Button btMasuk;
    TextView textToRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btMasuk = findViewById(R.id.btMasuk);
        textToRegister = findViewById(R.id.textToRegister2);


    }

    public void toHome(View view) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void toRegister(View view) {
        Intent intent = new Intent(getApplicationContext(), register.class);
        startActivity(intent);
        finish();
    }
}
