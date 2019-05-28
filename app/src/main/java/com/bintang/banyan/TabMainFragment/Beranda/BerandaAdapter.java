package com.bintang.banyan.TabMainFragment.Beranda;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bintang.banyan.Model.Posting;
import com.bintang.banyan.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class BerandaAdapter extends RecyclerView.Adapter<BerandaAdapter.RecyclerViewAdapter> {
    private Context context;
    private List<Posting> post;
    private ItemClickListener itemClickListener;

    public BerandaAdapter(Context context, List<Posting> post, ItemClickListener itemClickListener) {
        this.context = context;
        this.post = post;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public RecyclerViewAdapter onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_post, viewGroup, false);

        return new RecyclerViewAdapter(view, itemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter recyclerViewAdapter, int i) {
        Posting postt = this.post.get(i);

        recyclerViewAdapter.tv_id.setText(postt.getUser_id());
        recyclerViewAdapter.tv_judul.setText(postt.getJudul());
        recyclerViewAdapter.tv_deskripsi.setText(postt.getDeskripsi());
        recyclerViewAdapter.tv_date.setText(postt.getTanggal());

        try {
            Picasso.get().load(postt.getGambar())
                    .error(R.drawable.ic_person_black_100dp)
                    .into(recyclerViewAdapter.iv_post);

            Picasso.get().load(postt.getUserImage())
                    .error(R.drawable.ic_person_black_100dp)
                    .into(recyclerViewAdapter.iv_user_image);

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

        TextView tv_id, tv_judul, tv_deskripsi, tv_date;
        ImageView iv_post, iv_user_image;

        CardView card_item;
        ItemClickListener itemClickListener;

        RecyclerViewAdapter(View itemView, ItemClickListener itemClickListener) {
            super(itemView);

            tv_id = itemView.findViewById(R.id.item_user_id);
            tv_judul = itemView.findViewById(R.id.item_judul);
            tv_deskripsi = itemView.findViewById(R.id.item_deskripsi);
            tv_date = itemView.findViewById(R.id.item_date);
            iv_post = itemView.findViewById(R.id.iv_item_post);
            iv_user_image = itemView.findViewById(R.id.item_user_image);
            card_item = itemView.findViewById(R.id.card_item);

            this.itemClickListener = itemClickListener;
            card_item.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            itemClickListener.onItemClick(view, getAdapterPosition());
        }
    }

}
