package com.bintang.banyan.Activity.Main.TabMainFragment.Kebun.HasilRekomendasi;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bintang.banyan.Model.RekomendasiTanaman;
import com.bintang.banyan.R;

import java.util.List;

public class HasilRekomendasiAdapter extends RecyclerView.Adapter<HasilRekomendasiAdapter.RecyclerViewAdapter> {
    private Context context;
    private List<RekomendasiTanaman> rekomendasiTanaman;
    private ItemClickListener rekomendasiItemClickListener;

    public HasilRekomendasiAdapter(Context context, List<RekomendasiTanaman> rekomendasiTanaman, ItemClickListener rekomendasiItemClickListener) {
        this.context = context;
        this.rekomendasiTanaman = rekomendasiTanaman;
        this.rekomendasiItemClickListener = rekomendasiItemClickListener;
    }

    @NonNull
    @Override
    public RecyclerViewAdapter onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_tanaman, viewGroup, false);

        return new RecyclerViewAdapter(view, rekomendasiItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter recyclerViewAdapter, int i) {
        RekomendasiTanaman rekomendasiTanaman = this.rekomendasiTanaman.get(i);

        recyclerViewAdapter.tvNama.setText(rekomendasiTanaman.getNama());
        recyclerViewAdapter.tvNamaLatin.setText(rekomendasiTanaman.getNama_latin());
        recyclerViewAdapter.tvJenis.setText(rekomendasiTanaman.getJenis());

    }

    @Override
    public int getItemCount() {
        return rekomendasiTanaman.size();
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

    public class RecyclerViewAdapter extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tvNama, tvJenis, tvNamaLatin;

        CardView card_item;
        ItemClickListener itemClickListener;

        RecyclerViewAdapter(View itemView, ItemClickListener itemClickListener) {
            super(itemView);

            tvNama = itemView.findViewById(R.id.item_nama_tanaman);
            tvJenis = itemView.findViewById(R.id.item_jenis_tanaman);
            tvNamaLatin = itemView.findViewById(R.id.item_nama_latin_tanaman);
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
