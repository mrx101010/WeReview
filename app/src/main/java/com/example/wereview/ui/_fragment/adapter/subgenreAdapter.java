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

public class subgenreAdapter extends RecyclerView.Adapter<subgenreAdapter.ViewHolder> {

    private List<subgenre> subgenreList;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    // data is passed into the constructor
    public subgenreAdapter(Context context, List<subgenre> data) {
        this.mInflater = LayoutInflater.from(context);
        this.subgenreList = data;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_subgenre, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    // binds the data to the textview in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        subgenre subgenre = subgenreList.get(position);
        holder.tvSubGenre.setText(subgenre.getSubgenreName());
        Picasso.get().load(subgenre.getSubgenrePhoto()).into(holder.ivSubGenre);

    }

    // total number of rows
    @Override
    public int getItemCount() {
        return subgenreList.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView tvSubGenre;
        public ImageView ivSubGenre;

        public ViewHolder(View itemView) {
            super(itemView);
            tvSubGenre = (TextView) itemView.findViewById(R.id.tv_subgenre_name);
            ivSubGenre = (ImageView) itemView.findViewById(R.id.iv_subgenre_photo);
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