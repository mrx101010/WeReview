package com.example.wereview.ui._fragment.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.wereview.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class GenreAdapter extends RecyclerView.Adapter<GenreAdapter.ViewHolder>{
    private List<Genre> genreList;
    private LayoutInflater mInflater;
    private GenreAdapter.ItemClickListener mClickListener;

    // data is passed into the constructor
    public GenreAdapter(Context context, List<Genre> data) {
        this.mInflater = LayoutInflater.from(context);
        this.genreList = data;
    }

    // inflates the row layout from xml when needed
    @Override
    public GenreAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_genre, parent, false);
        GenreAdapter.ViewHolder viewHolder = new GenreAdapter.ViewHolder(view);
        return viewHolder;
    }

    // binds the data to the textview in each row
    @Override
    public void onBindViewHolder(GenreAdapter.ViewHolder holder, int position) {
        Genre genre = genreList.get(position);
        holder.tvGenre.setText(genre.getGenreName());
        Picasso.get().load(genre.getGenrePhoto()).into(holder.ivGenre);

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
    public void setClickListener(GenreAdapter.ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
