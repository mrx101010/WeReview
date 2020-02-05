package com.example.wereview.ui._login;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.wereview.MainActivity;
import com.example.wereview.R;
import com.example.wereview.ui._register.register;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

public class login extends AppCompatActivity {

    DatabaseReference databaseUser;
    EditText etFormEmail, etFormPassword;
    Button btMasuk;
    String email_txt,password_txt;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        etFormEmail = (EditText) findViewById(R.id.etFormEmail);
        etFormPassword = (EditText) findViewById(R.id.etFormPassword);
        btMasuk = (Button) findViewById(R.id.btMasuk);

        btMasuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email_txt = etFormEmail.getText().toString();
                password_txt = etFormPassword.getText().toString();
                if (TextUtils.isEmpty(email_txt)) {
                    Toast.makeText(getApplicationContext(), "Masukan Email !!!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(password_txt)) {
                    Toast.makeText(getApplicationContext(), "Masukan Password !!!", Toast.LENGTH_SHORT).show();
                    return;
                }

                mAuth.signInWithEmailAndPassword(email_txt, password_txt)
                        .addOnCompleteListener(login.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Login sukses, masuk ke Main Activity
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    Intent intent = new Intent(login.this, MainActivity.class);
                                    startActivity(intent);
                                    Toast.makeText(login.this, "Selamat Datang " + user, Toast.LENGTH_LONG).show();
                                    finish();
                                } else {
                                    // Jika Login gagal, memberikan pesan
                                    Toast.makeText(login.this, "Proses Login gagal : " +  task.getException(), Toast.LENGTH_LONG).show();
                                }
                            }
                        });
            }
        });

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
