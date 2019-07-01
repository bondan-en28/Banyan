package com.bintang.banyan.Activity.Main.TabMainFragment.Profile;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bintang.banyan.Model.MyPosting;
import com.bintang.banyan.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProfileAdapter extends RecyclerView.Adapter<ProfileAdapter.RecyclerViewAdapter> {
    private Context context;
    private List<MyPosting> post;
    private ItemClickListener itemClickListener;

    public ProfileAdapter(Context context, List<MyPosting> post, ItemClickListener itemClickListener) {
        this.context = context;
        this.post = post;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public RecyclerViewAdapter onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_mypost, viewGroup, false);

        return new RecyclerViewAdapter(view, itemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter recyclerViewAdapter, int i) {
        MyPosting postt = this.post.get(i);

        try {
            Picasso.get().load(postt.getGambar())
                    .error(R.drawable.ic_person_black_100dp)
                    .into(recyclerViewAdapter.iv_post);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return post.size();
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

    public class RecyclerViewAdapter extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView iv_post;

        CardView card_item;
        ItemClickListener itemClickListener;

        RecyclerViewAdapter(View itemView, ItemClickListener itemClickListener) {
            super(itemView);

            iv_post = itemView.findViewById(R.id.iv_item_mypost);
            card_item = itemView.findViewById(R.id.card_item_mypost);

            this.itemClickListener = itemClickListener;
            card_item.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            itemClickListener.onItemClick(view, getAdapterPosition());
        }
    }

}
