package com.example.wereview.ui._register;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wereview.R;
import com.example.wereview.ui._login.login;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class register extends AppCompatActivity {

    DatabaseReference databaseUser;
    EditText etFormNama, etFormUsername, etFormEmail, etFormPassword;
    Button btDaftar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        databaseUser = FirebaseDatabase.getInstance().getReference("regis");

        etFormNama = (EditText) findViewById(R.id.etFormNama);
        etFormUsername = (EditText) findViewById(R.id.etFormUsername);
        etFormEmail = (EditText) findViewById(R.id.etFormEmail);
        etFormPassword = (EditText) findViewById(R.id.etFormPassword);
        btDaftar = (Button) findViewById(R.id.btDaftar);

        btDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddRegister();
            }
        });
    }

    private void AddRegister(){
        String nama = etFormNama.getText().toString().trim();
        String username = etFormUsername.getText().toString().trim();
        String email = etFormEmail.getText().toString().trim();
        String password = etFormPassword.getText().toString().trim();
        String level = "Beginner";
        String poin = "0";

        if(!TextUtils.isEmpty(nama)){

            String id= databaseUser.push().getKey();

            regis regis = new regis(id, nama, username,email,password, level,poin);

            databaseUser.child(id).setValue(regis);

            Toast.makeText(this, "Registrasi berhasil", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(this,"isi nama!", Toast.LENGTH_LONG).show();
        }
    }

    public void toLogin(View view) {
        Intent intent = new Intent(register.this, login.class);
        startActivity(intent);
    }
}
