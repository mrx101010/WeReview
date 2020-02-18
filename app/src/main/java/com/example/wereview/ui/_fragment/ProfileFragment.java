package com.example.wereview.ui._fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.ArrayList;

public class ProfileFragment extends Fragment {

    TextView tvNamaProfil;
    regis user;
    DatabaseReference database;
    RecyclerView mrecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    PostAdapter mAdapter;
    ArrayList<Post> postArrayList = new ArrayList<>();
    FirebaseUser fbUser;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_profile, container, false);

        fbUser = FirebaseAuth.getInstance().getCurrentUser();

        tvNamaProfil = view.findViewById(R.id.tvNamaProfil);

        tvNamaProfil.setText(fbUser.getEmail());

        database = FirebaseDatabase.getInstance().getReference();
        // Setup the RecyclerView
        mrecyclerView = view.findViewById(R.id.rvData);
        mLayoutManager = new LinearLayoutManager(getContext());
        mrecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new PostAdapter(getContext(), postArrayList);
        mrecyclerView.setAdapter(mAdapter);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

    }
}


