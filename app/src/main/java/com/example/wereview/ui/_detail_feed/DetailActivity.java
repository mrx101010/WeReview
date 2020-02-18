package com.example.wereview.ui._detail_feed;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.wereview.R;
import com.example.wereview.ui._feed.Feed;
import com.example.wereview.ui._detail_feed.adapter.Post;
import com.example.wereview.ui._detail_feed.adapter.PostAdapter;
import com.example.wereview.ui._fragment.adapter.RecyclerItemClickListener;
import com.example.wereview.ui._register.regis;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity {

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
        setContentView(R.layout.activity_detail);

        fbUser = FirebaseAuth.getInstance().getCurrentUser();
        if (fbUser == null) {
            finish();
        }

        database = FirebaseDatabase.getInstance().getReference().child("posts");
        // Setup the RecyclerView
        mrecyclerView = findViewById(R.id.rvDetailFeed);
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
        String postID = "";
        if (b != null) {
             postID = (String) b.get("PostID");
            Toast.makeText(DetailActivity.this, postID, Toast.LENGTH_SHORT).show();
        }
        database.orderByChild("key").equalTo(postID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                postArrayList.clear();

                for (DataSnapshot artistSnapshot : dataSnapshot.getChildren()) {
                    Post post = artistSnapshot.getValue(Post.class);
                    postArrayList.add(post);
                }

                PostAdapter adapter = new PostAdapter(DetailActivity.this, postArrayList);
                mrecyclerView.setAdapter(adapter);

                mrecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(DetailActivity.this, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        position += 1;
                        Toast.makeText(DetailActivity.this, "Card at " + position + " is clicked", Toast.LENGTH_SHORT).show();


                    }

                }));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
