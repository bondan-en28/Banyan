package com.bintang.banyan.Activity.Main.TabMainFragment.Kebun;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bintang.banyan.Model.MyTanaman;
import com.bintang.banyan.R;

import java.util.List;

public class TanamanAdapter extends RecyclerView.Adapter<TanamanAdapter.RecyclerViewAdapter> {
    private Context context;
    private List<MyTanaman> myTanaman;
    private ItemClickListener tanamanItemClickListener;

    public TanamanAdapter(Context context, List<MyTanaman> myTanaman, ItemClickListener tanamanItemClickListener) {
        this.context = context;
        this.myTanaman = myTanaman;
        this.tanamanItemClickListener = tanamanItemClickListener;
    }

    @NonNull
    @Override
    public RecyclerViewAdapter onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_tanaman, viewGroup, false);

        return new RecyclerViewAdapter(view, tanamanItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter recyclerViewAdapter, int i) {
        MyTanaman myTanaman = this.myTanaman.get(i);

        recyclerViewAdapter.tv_nama.setText(myTanaman.getNama());
        recyclerViewAdapter.tv_jenis.setText(myTanaman.getJenis());

    }

    @Override
    public int getItemCount() {
        return myTanaman.size();
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

    public class RecyclerViewAdapter extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tv_nama, tv_jenis;

        CardView card_item;
        ItemClickListener itemClickListener;

        RecyclerViewAdapter(View itemView, ItemClickListener itemClickListener) {
            super(itemView);

            tv_nama = itemView.findViewById(R.id.item_nama_tanaman);
            tv_jenis = itemView.findViewById(R.id.item_jenis_tanaman);
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
