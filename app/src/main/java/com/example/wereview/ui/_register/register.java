package com.example.wereview.ui._register;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.wereview.MainActivity;
import com.example.wereview.R;
import com.example.wereview.ui._login.login;
import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONException;
import org.json.JSONObject;

public class register extends AppCompatActivity {

    DatabaseReference databaseUser;
    EditText etFormNama, etFormUsername, etFormEmail, etFormPassword;
    Button btDaftar;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();
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

    private void AddRegister() {
        final String nama = etFormNama.getText().toString().trim();
        final String username = etFormUsername.getText().toString().trim();
        final String email = etFormEmail.getText().toString().trim();
        final String password = etFormPassword.getText().toString().trim();
        final String level = "Beginner";
        final String poin = "0";

        if (nama != null && username!= null && email != null && password!=null) {

            final String id = databaseUser.push().getKey();

            final ProgressDialog pd = new ProgressDialog(register.this);
            pd.setMessage("Loading...");
            pd.show();

            String url = "https://wereview-d7bc3.firebaseio.com/regis.json";

            FirebaseUser fbuser = mAuth.getCurrentUser();

            final String fbuid = fbuser.getUid();

            StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String s) {
                    if (s.equals("null")) {
                        String token = FirebaseInstanceId.getInstance().getToken();

                        regis regis = new regis(id, nama, username, email, password, level, poin, token, fbuid);

                        databaseUser.child(id).setValue(regis);

                        Toast.makeText(register.this, "Registrasi berhasil", Toast.LENGTH_LONG).show();
                    } else {
                        try {
                            JSONObject obj = new JSONObject(s);

                            if (!obj.get("email").equals(email) && !obj.get("username").equals(username)) {
                                String token = FirebaseInstanceId.getInstance().getToken();

                                regis regis = new regis(id, nama, username, email, password, level, poin, token, fbuid);

                                databaseUser.child(id).setValue(regis);

                                Toast.makeText(register.this, "Registrasi berhasil", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(register.this, "User tidak tersedia", Toast.LENGTH_LONG).show();
                                pd.dismiss();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    pd.dismiss();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    System.out.println("" + volleyError);
                    pd.dismiss();
                }
            });

            RequestQueue requestQueue = Volley.newRequestQueue(register.this);
            requestQueue.add(request);

        } else {
            Toast.makeText(this, "Jangan Sampai Kosong!", Toast.LENGTH_LONG).show();
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(register.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Daftar sukses, masuk ke Main Activity
                            FirebaseUser user = mAuth.getCurrentUser();
                            Intent intent = new Intent(register.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            // Jika daftar gagal, memberikan pesan
                            Toast.makeText(register.this, "Proses Pendaftaran gagal : " + task.getException(),
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    public void toLogin(View view) {
        Intent intent = new Intent(register.this, login.class);
        startActivity(intent);
    }
}
