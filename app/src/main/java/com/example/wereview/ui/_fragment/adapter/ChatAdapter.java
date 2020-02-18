package com.example.wereview.ui._fragment.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wereview.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {
    private List<Chat> chatList;
    private LayoutInflater mInflater;
    private ChatAdapter.ItemClickListener mClickListener;

    // data is passed into the constructor
    public ChatAdapter(Context context, List<Chat> data) {
        this.mInflater = LayoutInflater.from(context);
        this.chatList = data;
    }

    // inflates the row layout from xml when needed
    @Override
    public ChatAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_chat, parent, false);
        ChatAdapter.ViewHolder viewHolder = new ChatAdapter.ViewHolder(view);
        return viewHolder;
    }


    // binds the data to the textview in each row
    @Override
    public void onBindViewHolder(ChatAdapter.ViewHolder holder, int position) {
        Chat Chat = chatList.get(position);
        holder.tvChat.setText(Chat.getChatKe());
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return chatList.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView tvChat;

        public ViewHolder(View itemView) {
            super(itemView);
            tvChat = (TextView) itemView.findViewById(R.id.tv_chat_person);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // allows clicks events to be caught
    public void setClickListener(ChatAdapter.ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
