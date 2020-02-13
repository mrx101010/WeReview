package com.example.wereview.ui._fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wereview.R;
import com.example.wereview.ui._feed.Like;
import com.example.wereview.ui._feed.adapter.Post;
import com.example.wereview.ui._feed.adapter.PostAdapter;
import com.example.wereview.ui._register.regis;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ProfileFragment extends Fragment {

    regis user;
    DatabaseReference database;
    RecyclerView mrecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    PostAdapter mAdapter;
    ArrayList<Post> postArrayList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_profile, container, false);

        database = FirebaseDatabase.getInstance().getReference();
        // Setup the RecyclerView
        mrecyclerView = view.findViewById(R.id.rvData);
        mLayoutManager = new LinearLayoutManager(getContext());
        mrecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new PostAdapter(getContext(), postArrayList);
        mrecyclerView.setAdapter(mAdapter);
        Query imagesQuery = database.child("post").limitToFirst(100);
        imagesQuery.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                final Post post =  dataSnapshot.getValue(Post.class);

                //get the image user
                database.child("regis/" + post.userId).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        regis user = dataSnapshot.getValue(regis.class);
                        post.user = user;
                        mAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                //get the image likes
                Query likesQuery = database.child("likes").orderByChild("imageId").equalTo(post.key);
                likesQuery.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        Like like = dataSnapshot.getValue(Like.class);
                        post.addLike();
                        if(like.userId.equals(user.getId())) {
                            post.hasLiked = true;
                            post.userLike = dataSnapshot.getKey();
                        }
                        mAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {
                        Like like = dataSnapshot.getValue(Like.class);
                        post.removeLike();
                        if(like.userId.equals(user.getId())) {
                            post.hasLiked = false;
                            post.userLike = null;
                        }
                        mAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                mAdapter.addImage(post);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

    }
}


