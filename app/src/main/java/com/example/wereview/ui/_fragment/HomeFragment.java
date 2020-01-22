package com.example.wereview.ui._fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wereview.R;
import com.example.wereview.ui._fragment.adapter.genre;
import com.example.wereview.ui._fragment.adapter.genreAdapter;

import java.util.List;

public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
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
        return inflater.inflate(R.layout.fragment_home, container, false);



    }
}


