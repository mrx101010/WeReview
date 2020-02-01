package com.example.wereview.ui._feed;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.wereview.R;
import com.example.wereview.ui._fragment.adapter.RecyclerItemClickListener;
import com.example.wereview.ui._feed.adapter.Post;
import com.example.wereview.ui._feed.adapter.PostAdapter;
import com.example.wereview.ui._subpost.SubGenre;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Feed extends AppCompatActivity{

    static final int RC_PERMISSION_READ_EXTERNAL_STORAGE = 1;
    static final int RC_IMAGE_GALLERY = 2;
    FirebaseUser fbUser;
    DatabaseReference database;
    RecyclerView mrecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    PostAdapter mAdapter;
    ArrayList<Post> postArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        database = FirebaseDatabase.getInstance().getReference("posts");
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
            subgenre = (int) b.get("subgenre");
            genre = (int) b.get("genre");
            Toast.makeText(Feed.this, subgenre + "-" + genre, Toast.LENGTH_SHORT).show();
        }

        database.orderByChild("subgenreParent").equalTo("sg" + subgenre + "-" + genre).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                postArrayList.clear();

                for (DataSnapshot artistSnapshot : dataSnapshot.getChildren()) {
                    Post post = artistSnapshot.getValue(Post.class);
                    postArrayList.add(post);
                }

                PostAdapter adapter = new PostAdapter(Feed.this, postArrayList);
                mrecyclerView.setAdapter(adapter);

                mrecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(Feed.this, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        position += 1;
                        Toast.makeText(Feed.this, "Card at " + position + " is clicked", Toast.LENGTH_SHORT).show();


                    }

                }));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
