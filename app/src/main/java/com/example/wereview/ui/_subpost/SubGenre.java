package com.example.wereview.ui._subpost;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.wereview.R;
import com.example.wereview.ui._fragment.adapter.GenreAdapter;
import com.example.wereview.ui._fragment.adapter.RecyclerItemClickListener;
import com.example.wereview.ui._subpost.adapter.Subgenre;
import com.example.wereview.ui._subpost.adapter.SubgenreAdapter;
import com.example.wereview.ui._feed.Feed;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Integer.parseInt;


public class SubGenre extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<Subgenre> subgenreList;
    private GenreAdapter genreAdapter;
    DatabaseReference databaseArtists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_genre);



        databaseArtists = FirebaseDatabase.getInstance().getReference("subgenre");
        mRecyclerView = findViewById(R.id.rvSubGenre);
        if (mRecyclerView != null) {
            mRecyclerView.setHasFixedSize(true);

        }

        mLayoutManager = new LinearLayoutManager(SubGenre.this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        subgenreList = new ArrayList<>();

    }

    @Override
    protected void onStart() {
        super.onStart();

        Intent intentsubgenre = getIntent();
        Bundle b = intentsubgenre.getExtras();
        int j = 0;
        if (b != null) {
            j = (int) b.get("Subgenre");
            Toast.makeText(SubGenre.this, j + "", Toast.LENGTH_SHORT).show();
        }


        final int finalJ = j;
        databaseArtists.orderByChild("parent").equalTo("genre" + j).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                subgenreList.clear();

                for (DataSnapshot artistSnapshot : dataSnapshot.getChildren()) {
                    Subgenre subgenres = artistSnapshot.getValue(Subgenre.class);
                    subgenreList.add(subgenres);
                }

                SubgenreAdapter adapter = new SubgenreAdapter(SubGenre.this, subgenreList);
                mRecyclerView.setAdapter(adapter);

                mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(SubGenre.this, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        position += 1;
                        Toast.makeText(SubGenre.this, "sg" + position + "-" + finalJ, Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(SubGenre.this, Feed.class);
                        intent.putExtra("Subgenre", position);
                        intent.putExtra("Genre", finalJ);
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
}
