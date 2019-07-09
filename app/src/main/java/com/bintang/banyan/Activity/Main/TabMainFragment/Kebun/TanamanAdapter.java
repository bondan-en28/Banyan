package com.bintang.banyan.Activity.Main.TabMainFragment.Kebun;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bintang.banyan.Model.MyTanaman;
import com.bintang.banyan.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.Calendar;
import java.util.Date;
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
        View view = LayoutInflater.from(context).inflate(R.layout.item_mytanaman, viewGroup, false);

        return new RecyclerViewAdapter(view, tanamanItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter recyclerViewAdapter, int i) {
        MyTanaman myTanaman = this.myTanaman.get(i);

        recyclerViewAdapter.tv_nama.setText(myTanaman.getNama());
        recyclerViewAdapter.tv_nama_latin.setText(myTanaman.getNama_latin());

        String tanggal = myTanaman.getDate();
        String s = "2019-05-08 08:23:11";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date d = null;
        try {
            d = sdf.parse(tanggal);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH) + 1;
        int date = c.get(Calendar.DATE);

//        recyclerViewAdapter.tv_usia.setText(String.valueOf(year)+String.valueOf(month)+String.valueOf(date));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            LocalDate l1 = LocalDate.of(year, month, date);
            LocalDate now = LocalDate.now();
            Period diff1 = Period.between(l1, now);
            String usia = null;
            if (diff1.getMonths() == 0 && diff1.getYears() == 0) {
                usia = diff1.getDays() + " Hari";
            } else if (diff1.getMonths() > 0 && diff1.getYears() == 0) {
                usia = diff1.getMonths() + " Bulan, " +
                        diff1.getDays() + " Hari";
            } else if (diff1.getYears() > 0 && diff1.getMonths() == 0) {
                usia = diff1.getYears() + " Tahun, " +
                        diff1.getDays() + " Hari";
            } else if (diff1.getYears() > 0 && diff1.getDays() == 0) {
                usia = diff1.getYears() + " Tahun, " +
                        diff1.getMonths() + " Bulan";
            } else {
                usia = diff1.getYears() + " Tahun, " +
                        diff1.getMonths() + " Bulan, " +
                        diff1.getDays() + " Hari";
            }
            recyclerViewAdapter.tv_usia.setText(usia);
        }

//            recyclerViewAdapter.tv_usia.setText(diff1.getDays());
    }

    @Override
    public int getItemCount() {
        return myTanaman.size();
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

    public class RecyclerViewAdapter extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tv_nama, tv_nama_latin, tv_usia;

        CardView card_item;
        ItemClickListener itemClickListener;

        RecyclerViewAdapter(View itemView, ItemClickListener itemClickListener) {
            super(itemView);

            tv_nama = itemView.findViewById(R.id.item_nama_mytanaman);
            tv_nama_latin = itemView.findViewById(R.id.item_nama_latin_mytanaman);
            tv_usia = itemView.findViewById(R.id.item_usia_mytanaman);
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
