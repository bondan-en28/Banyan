package com.bintang.banyan.Activity.DetailTanaman.IotData;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bintang.banyan.Model.IotData;
import com.bintang.banyan.R;

import java.util.List;

public class IotDataAdapter extends RecyclerView.Adapter<IotDataAdapter.RecyclerViewAdapter> {

    private Context context;
    private List<IotData> iotData;

    public IotDataAdapter(Context context, List<IotData> iotData) {
        this.context = context;
        this.iotData = iotData;
    }

    @NonNull
    @Override
    public RecyclerViewAdapter onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_iot_data, viewGroup, false);

        return new RecyclerViewAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter recyclerViewAdapter, int i) {
        IotData iotDataa = iotData.get(i);

        recyclerViewAdapter.tv_cahaya.setText(iotDataa.getCahaya());
        recyclerViewAdapter.tv_suhu.setText(iotDataa.getSuhu());
        recyclerViewAdapter.tv_lembab_udara.setText(iotDataa.getLembab_udara());
        recyclerViewAdapter.tv_lembab_tanah.setText(iotDataa.getLembab_tanah());
        recyclerViewAdapter.tv_date.setText(iotDataa.getDate());
    }

    @Override
    public int getItemCount() {
        return iotData.size();
    }

    public class RecyclerViewAdapter extends RecyclerView.ViewHolder {

        TextView tv_cahaya, tv_suhu, tv_lembab_udara, tv_lembab_tanah, tv_date;

        RecyclerViewAdapter(View itemView) {
            super(itemView);

            tv_cahaya = itemView.findViewById(R.id.item_cahaya_iot);
            tv_suhu = itemView.findViewById(R.id.item_suhu_iot);
            tv_lembab_udara = itemView.findViewById(R.id.item_lembab_udara_iot);
            tv_lembab_tanah = itemView.findViewById(R.id.item_lembab_tanah_iot);
            tv_date = itemView.findViewById(R.id.item_date_iotdata);

        }

    }
}
