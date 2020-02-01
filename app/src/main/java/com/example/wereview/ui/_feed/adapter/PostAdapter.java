package com.example.wereview.ui._feed.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.wereview.R;
import com.example.wereview.ui._fragment.adapter.genreAdapter;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder>{
    private List<Post> postList;
    private LayoutInflater mInflater;
    private genreAdapter.ItemClickListener mClickListener;

    // data is passed into the constructor
    public PostAdapter(Context context, List<Post> data) {
        this.mInflater = LayoutInflater.from(context);
        this.postList = data;
    }

    // inflates the row layout from xml when needed
    @Override
    public PostAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_feed, parent, false);
        PostAdapter.ViewHolder viewHolder = new PostAdapter.ViewHolder(view);
        return viewHolder;
    }

    // binds the data to the textview in each row
    @Override
    public void onBindViewHolder(PostAdapter.ViewHolder holder, int position) {
        Post post= postList.get(position);
        holder.tvFeed.setText(post.getCaption());
        Picasso.get().load(post.getDownloadUrl()).into(holder.ivFeed);

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return postList.size();
    }

    public void addImage(Post post) {
        postList.add(0, post);
        notifyDataSetChanged();
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView tvFeed;
        public ImageView ivFeed;

        public ViewHolder(View itemView) {
            super(itemView);
            tvFeed = (TextView) itemView.findViewById(R.id.tv_feed_name);
            ivFeed = (ImageView) itemView.findViewById(R.id.iv_feed_photo);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // allows clicks events to be caught
    public void setClickListener(genreAdapter.ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
