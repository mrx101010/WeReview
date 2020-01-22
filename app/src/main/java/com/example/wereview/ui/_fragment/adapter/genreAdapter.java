package com.example.wereview.ui._fragment.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.wereview.R;

import java.util.List;

public class genreAdapter extends RecyclerView.Adapter<genreAdapter.ViewHolder> {

    private List<genre> genreList;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    // data is passed into the constructor
    public genreAdapter(Context context, List<genre> data) {
        this.mInflater = LayoutInflater.from(context);
        this.genreList = data;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_genre, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    // binds the data to the textview in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        genre genre = genreList.get(position);
        holder.tvGenre.setText(genre.getGenre_name());
        holder.ivGenre.setImageResource(genre.getPic());
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return genreList.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView tvGenre;
        public ImageView ivGenre;

        public ViewHolder(View itemView) {
            super(itemView);
            tvGenre = (TextView) itemView.findViewById(R.id.tv_genre_name);
            ivGenre = (ImageView) itemView.findViewById(R.id.iv_genre_photo);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}