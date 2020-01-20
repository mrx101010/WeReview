package com.example.wereview.ui._register;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wereview.R;
import com.example.wereview.ui._login.login;

public class register extends AppCompatActivity {

    private TextView tvMasuk;
    private Button btDaftar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        tvMasuk = findViewById(R.id.textToLogin2);
        btDaftar = findViewById(R.id.btDaftar);

        tvMasuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), login.class);
                startActivity(intent);
                finish();
            }
        });

        btDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Nge Klik Coy!!!", Toast.LENGTH_LONG).show();
            }
        });
    }
}
