package com.bintang.banyan.Activity.DetailPost.Komentar;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bintang.banyan.Model.Komentar;
import com.bintang.banyan.R;

import java.util.List;

public class KomentarAdapter extends RecyclerView.Adapter<KomentarAdapter.RecyclerViewAdapter> {

    private Context context;
    private List<Komentar> komentars;

    public KomentarAdapter(Context context, List<Komentar> komentar) {
        this.context = context;
        this.komentars = komentar;
    }

    @NonNull
    @Override
    public RecyclerViewAdapter onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_komentar, viewGroup, false);

        return new RecyclerViewAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter recyclerViewAdapter, int i) {
        Komentar komentar = komentars.get(i);
        recyclerViewAdapter.tv_username.setText(komentar.getUser_id());
        recyclerViewAdapter.tv_komentar.setText(komentar.getKomentar());
        recyclerViewAdapter.tv_date.setText(komentar.getDate());
    }

    @Override
    public int getItemCount() {
        return komentars.size();
    }

    public class RecyclerViewAdapter extends RecyclerView.ViewHolder {

        TextView tv_username, tv_komentar, tv_date;

        RecyclerViewAdapter(View itemView) {
            super(itemView);

            tv_username = itemView.findViewById(R.id.user_name_komentar);
            tv_komentar = itemView.findViewById(R.id.item_komentar);
            tv_date = itemView.findViewById(R.id.item_date_komentar);

        }

    }
}
