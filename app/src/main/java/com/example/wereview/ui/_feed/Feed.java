package com.example.wereview.ui._feed;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.wereview.R;
import com.example.wereview.ui._detail_feed.DetailActivity;
import com.example.wereview.ui._fragment.adapter.RecyclerItemClickListener;
import com.example.wereview.ui._feed.adapter.Post;
import com.example.wereview.ui._feed.adapter.PostAdapter;
import com.example.wereview.ui._register.regis;
import com.example.wereview.ui._subpost.SubGenre;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Feed extends AppCompatActivity{

    static final int RC_PERMISSION_READ_EXTERNAL_STORAGE = 1;
    static final int RC_IMAGE_GALLERY = 2;
    FirebaseUser fbUser;
    regis user;
    DatabaseReference database;
    RecyclerView mrecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    PostAdapter mAdapter;
    ArrayList<Post> postArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        fbUser = FirebaseAuth.getInstance().getCurrentUser();
        if (fbUser == null) {
            finish();
        }

        database = FirebaseDatabase.getInstance().getReference().child("posts");
        // Setup the RecyclerView
        mrecyclerView = findViewById(R.id.rvFeed);
        mLayoutManager = new LinearLayoutManager(this);
        mrecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new PostAdapter(this, postArrayList);
        mrecyclerView.setAdapter(mAdapter);


    }

    @Override
    protected void onStart() {
        super.onStart();

        Intent intentfeed = getIntent();
        Bundle b = intentfeed.getExtras();
        int subgenre = 0;
        int genre = 0;
        if (b != null) {
            subgenre = (int) b.get("Subgenre");
            genre = (int) b.get("Genre");
            Toast.makeText(Feed.this, subgenre + "-" + genre, Toast.LENGTH_SHORT).show();
        }

        database.orderByChild("subgenreParent").equalTo("sg" + subgenre + "-" + genre).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                postArrayList.clear();

                Post post = new Post();
                String id = null;
                for (DataSnapshot artistSnapshot : dataSnapshot.getChildren()) {
                    post = artistSnapshot.getValue(Post.class);
                    id = post.getKey();
                    postArrayList.add(post);
                }

                PostAdapter adapter = new PostAdapter(Feed.this, postArrayList);
                mrecyclerView.setAdapter(adapter);

                final String finalId = id;
                mrecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(Feed.this, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        position += 1;


                        Toast.makeText(Feed.this, "Card at " + position + " is clicked", Toast.LENGTH_SHORT).show();



                        Intent intent = new Intent(Feed.this, DetailActivity.class);
                        intent.putExtra("PostID", finalId);
                        startActivity(intent);
                        finish();

                    }

                }));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void uploadImage(View view){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, RC_PERMISSION_READ_EXTERNAL_STORAGE);
        } else {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            startActivityForResult(intent, RC_IMAGE_GALLERY);
        }
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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_IMAGE_GALLERY && resultCode == RESULT_OK) {
            Uri uri = data.getData();

            StorageReference storageRef = FirebaseStorage.getInstance().getReference();
            StorageReference imagesRef = storageRef.child("posts");
            StorageReference userRef = imagesRef.child(fbUser.getUid());
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String filename = fbUser.getUid() + "_" + timeStamp;
            StorageReference fileRef = userRef.child(filename);

            UploadTask uploadTask = fileRef.putFile(uri);
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle unsuccessful uploads
                    Toast.makeText(Feed.this, "Upload failed!\n" + exception.getMessage(), Toast.LENGTH_LONG).show();
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Task<Uri> task = taskSnapshot.getMetadata().getReference().getDownloadUrl();
                    task.addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String photolink = uri.toString();
                            String key = database.child("posts").push().getKey();
                            Post image = new Post(key, "" , "", user.getId(), photolink, "", "");
                            database.child("posts").child(key).setValue(image);
                        }
                    });
                    Uri downloadUrl = taskSnapshot.getUploadSessionUri();
                    // save image to database

                    Toast.makeText(Feed.this, "Upload finished!", Toast.LENGTH_SHORT).show();
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
