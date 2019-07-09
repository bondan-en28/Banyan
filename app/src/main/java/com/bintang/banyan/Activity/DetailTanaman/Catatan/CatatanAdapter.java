package com.bintang.banyan.Activity.DetailTanaman.Catatan;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bintang.banyan.Model.Catatan;
import com.bintang.banyan.R;

import java.util.List;

public class CatatanAdapter extends RecyclerView.Adapter<CatatanAdapter.RecyclerViewAdapter> {

    private Context context;
    private List<Catatan> catatans;

    public CatatanAdapter(Context context, List<Catatan> catatans) {
        this.context = context;
        this.catatans = catatans;
    }

    @NonNull
    @Override
    public RecyclerViewAdapter onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_catatan, viewGroup, false);

        return new RecyclerViewAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter recyclerViewAdapter, int i) {
        Catatan catatan = catatans.get(i);

        recyclerViewAdapter.tv_username.setText(catatan.getUser_id());
        recyclerViewAdapter.tv_catatan.setText(catatan.getCatatan());
        recyclerViewAdapter.tv_date.setText(catatan.getDate());
    }

    @Override
    public int getItemCount() {
        return catatans.size();
    }

    public class RecyclerViewAdapter extends RecyclerView.ViewHolder {

        TextView tv_username, tv_catatan, tv_date;

        RecyclerViewAdapter(View itemView) {
            super(itemView);

            tv_username = itemView.findViewById(R.id.item_user_name_catatan);
            tv_catatan = itemView.findViewById(R.id.item_catatan);
            tv_date = itemView.findViewById(R.id.item_date_catatan);

        }

    }
}
