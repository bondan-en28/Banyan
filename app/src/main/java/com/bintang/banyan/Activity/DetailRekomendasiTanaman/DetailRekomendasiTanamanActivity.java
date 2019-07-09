package com.bintang.banyan.Activity.DetailRekomendasiTanaman;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bintang.banyan.Activity.Main.MainActivity;
import com.bintang.banyan.R;
import com.squareup.picasso.Picasso;

public class DetailRekomendasiTanamanActivity extends AppCompatActivity implements AddTanamanView {

    TextView tvNamaLatin, tvDeskripsi, tvJenis, tvKetinggian, tvTanah, tvSuhu, tvPh, tvKelembapan, tvTekanan, tvLahan, tvAir;
    ImageView ivGambar;
    Button btnTanam;
    int id;
    String nama, nama_latin, deskripsi, jenis, ketinggian, tanah, suhu, ph, kelembapan, tekanan, lahan, air, gambar;
    AddTanamanPresenter tanamanPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_rekomendasi_tanaman);
        Toolbar rekomendasiToolbar = findViewById(R.id.detail_rekomendasi_toolbar);
        setSupportActionBar(rekomendasiToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        tvNamaLatin = findViewById(R.id.tv_nama_latin_rekomendasi_tanaman);
        tvDeskripsi = findViewById(R.id.tv_deskripsi_rekomendasi_tanaman);
        tvJenis = findViewById(R.id.tv_jenis_rekomendasi_tanaman);
        tvKetinggian = findViewById(R.id.tv_ketinggian_rekomendasi_tanaman);
        tvTanah = findViewById(R.id.tv_tanah_rekomendasi_tanaman);
        tvSuhu = findViewById(R.id.tv_suhu_rekomendasi_tanaman);
        tvPh = findViewById(R.id.tv_ph_rekomendasi_tanaman);
        tvKelembapan = findViewById(R.id.tv_kelembapan_rekomendasi_tanaman);
        tvTekanan = findViewById(R.id.tv_tekanan_rekomendasi_tanaman);
        tvLahan = findViewById(R.id.tv_lahan_rekomendasi_tanaman);
        tvAir = findViewById(R.id.tv_air_rekomendasi_tanaman);
        ivGambar = findViewById(R.id.iv_gambar_rekomendasi_tanaman);
        btnTanam = findViewById(R.id.btn_tanam_rekomendasi_tanaman);

        Intent intent = getIntent();
        id = intent.getIntExtra("id", 0);
        nama = intent.getStringExtra("nama");
        nama_latin = intent.getStringExtra("nama_latin");
        deskripsi = intent.getStringExtra("deskripsi");
        jenis = intent.getStringExtra("jenis");
        ketinggian = intent.getStringExtra("ketinggian");
        tanah = intent.getStringExtra("tanah");
        suhu = intent.getStringExtra("suhu");
        ph = intent.getStringExtra("ph");
        kelembapan = intent.getStringExtra("kelembapan");
        tekanan = intent.getStringExtra("tekanan");
        lahan = intent.getStringExtra("lahan");
        air = intent.getStringExtra("air");
        gambar = intent.getStringExtra("gambar");

        rekomendasiToolbar.setTitle(nama);
        getSupportActionBar().setTitle(nama);
        tvNamaLatin.setText(nama_latin);
        tvDeskripsi.setText(deskripsi);
        tvJenis.setText(jenis);
        tvKetinggian.setText(ketinggian);
        tvTanah.setText(tanah);
        tvSuhu.setText(suhu);
        tvPh.setText(ph);
        tvKelembapan.setText(kelembapan);
        tvTekanan.setText(tekanan);
        tvLahan.setText(lahan);
        tvAir.setText(air);
        try {
            Picasso.get().load(gambar)
                    .error(R.drawable.ic_plant)
                    .into(ivGambar);
        } catch (Exception e) {
            e.printStackTrace();
        }
        tanamanPresenter = new AddTanamanPresenter(this);


        btnTanam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tanamanPresenter.tambahTanaman(MainActivity.getId, id);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // kembali ke Main Menu
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onRequestAddTanamanSuccess(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        // kembali ke Main Menu

        Intent intent = new Intent(DetailRekomendasiTanamanActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    public void onRequestError(String message) {
        Toast.makeText(this, "Error Tambah Tanaman:" + message, Toast.LENGTH_SHORT).show();
    }
}
