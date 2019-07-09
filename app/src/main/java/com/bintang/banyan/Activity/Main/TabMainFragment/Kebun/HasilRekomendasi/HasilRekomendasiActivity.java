package com.bintang.banyan.Activity.Main.TabMainFragment.Kebun.HasilRekomendasi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import android.widget.Toast;

import com.bintang.banyan.Activity.DetailRekomendasiTanaman.DetailRekomendasiTanamanActivity;
import com.bintang.banyan.Model.RekomendasiTanaman;
import com.bintang.banyan.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class HasilRekomendasiActivity extends AppCompatActivity implements HasilRekomendasiView {
    public static Toolbar toolbar;
    public static RecyclerView rekomendasiRecyclerView;
    public static SwipeRefreshLayout rekomendasiSwipeRefresh;
    public static HasilRekomendasiAdapter.ItemClickListener rekomendasiItemClickListener;
    public String kota, provinsi, negara, zona, latitude, longitude, ketinggian, suhu, kelembapan, tekanan, tanah, lahan, air;
    public TextView tvTanggal, tvKota, tvProvinsiNegara, tvSuhu, tvLatLon, tvKetinggian, tvKelembapan, tvTekanan;
    HasilRekomendasiPresenter hasilRekomendasiPresenter;
    HasilRekomendasiAdapter hasilRekomendasiAdapter;

    List<RekomendasiTanaman> rekomendasiTanaman;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hasil_rekomendasi);

        toolbar = findViewById(R.id.toolbar_hasil_rekomendasi);
        toolbar.setTitleMarginStart(20);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        tvTanggal = findViewById(R.id.tv_tanggal_hasil);
        tvKota = findViewById(R.id.tv_kota_hasil);
        tvProvinsiNegara = findViewById(R.id.tv_provinsi_negara_hasil);
        tvSuhu = findViewById(R.id.tv_suhu_hasil);
        tvLatLon = findViewById(R.id.tv_lat_lon_hasil);
        tvKetinggian = findViewById(R.id.tv_ketinggian_hasil);
        tvKelembapan = findViewById(R.id.tv_kelembapan_hasil);
        tvTekanan = findViewById(R.id.tv_tekanan_hasil);


        Date today = Calendar.getInstance().getTime();//getting date
        SimpleDateFormat formatter = new SimpleDateFormat("E, dd MMM yyyy z");//formating according to my need
        String date = formatter.format(today);

        Intent intent = getIntent();
        kota = intent.getStringExtra("kota");
        provinsi = intent.getStringExtra("provinsi");
        negara = intent.getStringExtra("negara");
        zona = intent.getStringExtra("zona");
        latitude = intent.getStringExtra("latitude");
        longitude = intent.getStringExtra("longitude");

        latitude = latitude.substring(0, 10);
        longitude = longitude.substring(0, 10);

        ketinggian = intent.getStringExtra("ketinggian");
        suhu = intent.getStringExtra("suhu");

        kelembapan = intent.getStringExtra("kelembapan");
        tekanan = intent.getStringExtra("tekanan");
        tanah = intent.getStringExtra("tanah");
        lahan = intent.getStringExtra("lahan");
        air = intent.getStringExtra("air");

        tvTanggal.setText(date);
        tvKota.setText(kota);
        tvProvinsiNegara.setText(provinsi + ", " + negara);

        tvSuhu.setText(suhu + " C");
        tvLatLon.setText(latitude + ", " + longitude);
        tvKetinggian.setText(ketinggian);
        tvKelembapan.setText(kelembapan);
        tvTekanan.setText(tekanan);

        rekomendasiSwipeRefresh = findViewById(R.id.swipe_refresh_hasil);
        hasilRekomendasiPresenter = new HasilRekomendasiPresenter(this);
        hasilRekomendasiPresenter.getRekomendasiTanaman(suhu, ketinggian, tanah);
        rekomendasiRecyclerView = findViewById(R.id.recycler_view_tanaman_hasil);
        rekomendasiRecyclerView.setLayoutManager(new LinearLayoutManager(this));


        rekomendasiSwipeRefresh.setOnRefreshListener(() -> hasilRekomendasiPresenter.getRekomendasiTanaman(suhu, ketinggian, tanah));
        rekomendasiItemClickListener = ((view, position) ->
        {
            int id = rekomendasiTanaman.get(position).getId();
            String nama = rekomendasiTanaman.get(position).getNama();
            String nama_latin = rekomendasiTanaman.get(position).getNama_latin();
            String deskripsi = rekomendasiTanaman.get(position).getDeskripsi();
            String jenis = rekomendasiTanaman.get(position).getJenis();
            String ketinggian = rekomendasiTanaman.get(position).getKetinggian();
            String struktur_tanah = rekomendasiTanaman.get(position).getTanah();
            String suhu = rekomendasiTanaman.get(position).getSuhu();
            String ph = rekomendasiTanaman.get(position).getPh();
            String kelembapan = rekomendasiTanaman.get(position).getKelembapan();
            String tekanan = rekomendasiTanaman.get(position).getTekanan();
            String lahan = rekomendasiTanaman.get(position).getLahan();
            String air = rekomendasiTanaman.get(position).getAir();
            String gambar = rekomendasiTanaman.get(position).getGambar();

            Intent toDetailTanaman = new Intent(this, DetailRekomendasiTanamanActivity.class);
            toDetailTanaman.putExtra("id", id);
            toDetailTanaman.putExtra("nama", nama);
            toDetailTanaman.putExtra("nama_latin", nama_latin);
            toDetailTanaman.putExtra("deskripsi", deskripsi);
            toDetailTanaman.putExtra("jenis", jenis);
            toDetailTanaman.putExtra("ketinggian", ketinggian);
            toDetailTanaman.putExtra("tanah", struktur_tanah);
            toDetailTanaman.putExtra("suhu", suhu);
            toDetailTanaman.putExtra("ph", ph);
            toDetailTanaman.putExtra("kelembapan", kelembapan);
            toDetailTanaman.putExtra("tekanan", tekanan);
            toDetailTanaman.putExtra("lahan", lahan);
            toDetailTanaman.putExtra("air", air);
            toDetailTanaman.putExtra("gambar", gambar);
            startActivity(toDetailTanaman);
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void showLoading() {
        rekomendasiSwipeRefresh.setRefreshing(true);
    }

    @Override
    public void hideLoading() {
        rekomendasiSwipeRefresh.setRefreshing(false);
    }

    @Override
    public void onGetRekomendasiResult(List<RekomendasiTanaman> tanaman) {
        hasilRekomendasiAdapter = new HasilRekomendasiAdapter(this, tanaman, rekomendasiItemClickListener);

        hasilRekomendasiAdapter.notifyDataSetChanged();
        rekomendasiRecyclerView.setAdapter(hasilRekomendasiAdapter);
        this.rekomendasiTanaman = tanaman;

    }

    @Override
    public void onErrorLoading(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
