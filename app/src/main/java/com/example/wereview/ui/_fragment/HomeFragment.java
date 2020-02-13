package com.example.wereview.ui._fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wereview.R;
import com.example.wereview.ui._fragment.adapter.Genre;
import com.example.wereview.ui._fragment.adapter.GenreAdapter;
import com.example.wereview.ui._fragment.adapter.RecyclerItemClickListener;
import com.example.wereview.ui._subpost.SubGenre;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private List <Genre> genreList;
    private GenreAdapter genreAdapter;
    DatabaseReference databaseArtists;
    Genre genre;

    String [] genre_names = {
            "Otomotif", "Makanan"
    };
    int [] pics = {
            R.drawable.otomotif,
            R.drawable.motor
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        databaseArtists = FirebaseDatabase.getInstance().getReference("genre");
        mRecyclerView = view.findViewById(R.id.rvGenre);
        if (mRecyclerView != null){
            mRecyclerView.setHasFixedSize(true);

        }

        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        genreList = new ArrayList<>();



        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        databaseArtists.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                genreList.clear();

                for (DataSnapshot artistSnapshot : dataSnapshot.getChildren()){
                    Genre genre = artistSnapshot.getValue(Genre.class);
                    genreList.add(genre);




                }

                GenreAdapter adapter = new GenreAdapter(getContext(), genreList);
                mRecyclerView.setAdapter(adapter);

                mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        position += 1;
                        Toast.makeText(getContext(), "Card at " + position + " is clicked", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(getContext(), SubGenre.class);
                        intent.putExtra("Subgenre", position);
                        startActivity(intent);


                    }

                }));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}



