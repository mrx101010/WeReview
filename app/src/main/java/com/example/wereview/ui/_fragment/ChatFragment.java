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
import com.example.wereview.ui._chatroom.ActivityChatroom;
import com.example.wereview.ui._fragment.adapter.Chat;
import com.example.wereview.ui._fragment.adapter.ChatAdapter;
import com.google.firebase.database.DataSnapshot;
import com.example.wereview.ui._fragment.adapter.RecyclerItemClickListener;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ChatFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<Chat> chatList;
    private ChatAdapter chatAdapter;
    DatabaseReference databaseArtists;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_chat, container, false);

        databaseArtists = FirebaseDatabase.getInstance().getReference("regis");
        mRecyclerView = view.findViewById(R.id.rvChat);
        if (mRecyclerView != null){
            mRecyclerView.setHasFixedSize(true);

        }

        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        chatList = new ArrayList<>();



        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        databaseArtists.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                chatList.clear();

                for (DataSnapshot artistSnapshot : dataSnapshot.getChildren()){
                    Chat chat = artistSnapshot.getValue(Chat.class);
                    chatList.add(chat);




                }

                ChatAdapter adapter = new ChatAdapter(getContext(), chatList);
                mRecyclerView.setAdapter(adapter);

                mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        position += 1;
                        Toast.makeText(getContext(), "Card at " + position + " is clicked", Toast.LENGTH_SHORT).show();

//                        Intent intent = new Intent(getContext(), ActivityChatroom.class);
//                        intent.putExtra("Subchat", position);
//                        startActivity(intent);


                    }

                }));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}


