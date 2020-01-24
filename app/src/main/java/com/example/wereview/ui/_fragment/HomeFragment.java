package com.example.wereview.ui._fragment;

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

import com.example.wereview.MainActivity;
import com.example.wereview.R;
import com.example.wereview.ui._fragment.adapter.genre;
import com.example.wereview.ui._fragment.adapter.genreAdapter;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private List <genre> genreList;
    private com.example.wereview.ui._fragment.adapter.genreAdapter genreAdapter;

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

        mRecyclerView = view.findViewById(R.id.rvGenre);
        if (mRecyclerView != null){
            mRecyclerView.setHasFixedSize(true);

        }

        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        genreList = new ArrayList<>();

        for (int i = 0; i < genre_names.length; i++){
            genre genre = new genre(genre_names[i], i+1 + "", pics[i]);
            genreList.add(genre);
        }

        genreAdapter = new genreAdapter(getContext(),genreList);

        mRecyclerView.setAdapter(genreAdapter);
        genreAdapter.notifyDataSetChanged();


        return view;
    }

}



