package com.example.wereview.ui._fragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.wereview.MainActivity;
import com.example.wereview.R;
import com.example.wereview.ui._feed.Like;
import com.example.wereview.ui._feed.adapter.Post;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ActivityAdd extends AppCompatActivity implements AdapterView.OnItemClickListener {

    Uri uri;
    ImageButton image_post;
    static final int RC_PERMISSION_READ_EXTERNAL_STORAGE = 1;
    static final int RC_IMAGE_GALLERY = 2;
    DatabaseReference database;
    FirebaseUser fbUser;
    Button btUpload;
    String namaProduk, caption, description, subgenreParent, genreParent;
    EditText etCaption, etDescription, etNamaProduk;
    Spinner spinnerGenre, spinnerSubGenre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        //find find byid
        btUpload = findViewById(R.id.btUpload);
        etDescription = findViewById(R.id.etDeskripsi);
        etNamaProduk = findViewById(R.id.etNamaProduk);
        spinnerGenre = findViewById(R.id.spinnerGenre);
        spinnerSubGenre = findViewById(R.id.spinnerJenis);

        description = etDescription.getText().toString();
        namaProduk = etNamaProduk.getText().toString();

        fbUser = FirebaseAuth.getInstance().getCurrentUser();
        if (fbUser == null) {
            finish();
        }

        database = FirebaseDatabase.getInstance().getReference();

        image_post = findViewById(R.id.ibFotoProduk);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.judulGenre, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.jenisGenre, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGenre.setAdapter(adapter);
        spinnerSubGenre.setAdapter(adapter1);


    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT).show();
    }

    public void toGallery(View view) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivityForResult(Intent.createChooser(intent,"select image"),2);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == RC_PERMISSION_READ_EXTERNAL_STORAGE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, RC_IMAGE_GALLERY);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_IMAGE_GALLERY && resultCode == RESULT_OK) {
            Uri uri = null;
            try {
                uri=data.getData();
                Bitmap bm= MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(),data.getData());
                image_post.setImageBitmap(bm);
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), ""+e, Toast.LENGTH_SHORT).show();
            }
            StorageReference storageRef = FirebaseStorage.getInstance().getReference();
            StorageReference imagesRef = storageRef.child("images");
            StorageReference userRef = imagesRef.child(fbUser.getUid());
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String filename = fbUser.getUid() + "_" + timeStamp;

            final StorageReference fileRef = userRef.child(filename);
            final Uri finalUri = uri;

            btUpload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Stringprep
                    description = etDescription.getText().toString();
                    namaProduk = etNamaProduk.getText().toString();
                    genreParent = spinnerGenre.getSelectedItem().toString();
                    subgenreParent = spinnerSubGenre.getSelectedItem().toString();

                    if (genreParent.equalsIgnoreCase("otomotif")){
                        genreParent = "genre2";
                    }else if (genreParent.equalsIgnoreCase("tech")){
                        genreParent = "genre1";
                    }

                    if (subgenreParent.equalsIgnoreCase("motor")){
                        subgenreParent = "sg1-2";
                    }else if (subgenreParent.equalsIgnoreCase("mobil")){
                        subgenreParent = "sg2-2";
                    }

                    UploadTask uploadTask = fileRef.putFile(finalUri);
                    uploadTask.addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            // Handle unsuccessful uploads
                            Toast.makeText(ActivityAdd.this, "Upload failed!\n" + exception.getMessage(), Toast.LENGTH_LONG).show(); }
                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Task<Uri> task = taskSnapshot.getMetadata().getReference().getDownloadUrl();
                            task.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    String photolink = uri.toString();
                                    String key = database.child("posts").push().getKey();
                                    Post image = new Post(key, namaProduk, description, fbUser.getUid(), photolink, subgenreParent, genreParent);
                                    database.child("posts").child(key).setValue(image);
                                }
                            });
                            Uri downloadUrl = taskSnapshot.getUploadSessionUri();
                            // save image to database

                            Toast.makeText(ActivityAdd.this, "Upload finished!", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });


        }
    }
    public void setLiked(Post image) {
        if(!image.hasLiked) {
            // add new Like
            image.hasLiked = true;
            Like like = new Like(image.key, fbUser.getUid());
            String key = database.child("likes").push().getKey();
            database.child("likes").child(key).setValue(like);
            image.userLike = key;
        } else {
            // remove Like
            image.hasLiked = false;
            if (image.userLike != null) {
                database.child("likes").child(image.userLike).removeValue();
            }
        }
    }
}
