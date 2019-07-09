package com.bintang.banyan.Activity.DetailTanaman;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bintang.banyan.Activity.DetailRekomendasiTanaman.DetailRekomendasiTanamanActivity;
import com.bintang.banyan.Activity.DetailTanaman.Catatan.CatatanAdapter;
import com.bintang.banyan.Activity.DetailTanaman.Catatan.CatatanPresenter;
import com.bintang.banyan.Activity.DetailTanaman.Catatan.CatatanView;
import com.bintang.banyan.Activity.Main.MainActivity;
import com.bintang.banyan.Model.Catatan;
import com.bintang.banyan.R;

import java.util.List;

public class DetailTanamanActivity extends AppCompatActivity implements TambahCatatanView, CatatanView {
    public static Toolbar toolbar;
    RecyclerView recyclerViewCatatan;
    CatatanPresenter catatanPresenter;
    CatatanAdapter catatanAdapter;
    List<Catatan> catatans;
    SwipeRefreshLayout swipeRefreshCatatan;
    CardView container_catatan;
    Button btnSiram, btnPestisida, btnCatatan, btnSendCatatan;
    TambahCatatanPresenter tambahCatatanPresenter;
    EditText edtCatatan;

    TextView tvNama, tvNamaLatin;
    int id;
    String nama, nama_latin, deskripsi, jenis, ketinggian, tanah, suhu, ph, kelembapan, tekanan, lahan, air, gambar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_tanaman);

        toolbar = findViewById(R.id.toolbar_tanaman);
        toolbar.setTitleMarginStart(20);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        catatanPresenter = new CatatanPresenter(this);
        tambahCatatanPresenter = new TambahCatatanPresenter(this);

        swipeRefreshCatatan = findViewById(R.id.swipe_refresh_catatan);

        tvNama = findViewById(R.id.tv_nama_tanaman);
        tvNamaLatin = findViewById(R.id.tv_nama_latin_tanaman);
        btnSiram = findViewById(R.id.btn_siram);
        btnPestisida = findViewById(R.id.btn_pestisida);
        btnCatatan = findViewById(R.id.btn_catatan);
        btnSendCatatan = findViewById(R.id.btn_send_catatan);
        container_catatan = findViewById(R.id.container_catatan_tanaman);
        edtCatatan = findViewById(R.id.edt_catatan);

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

        tvNama.setText(nama);
        tvNamaLatin.setText(nama_latin);

        catatanPresenter.getCatatan(id);

        swipeRefreshCatatan.setOnRefreshListener(() -> catatanPresenter.getCatatan(id));

        recyclerViewCatatan = findViewById(R.id.recycler_view_catatan);
        recyclerViewCatatan.setLayoutManager(new LinearLayoutManager(this));

        btnCatatan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (container_catatan.getVisibility() == View.GONE) {
                    container_catatan.setVisibility(View.VISIBLE);
                } else {
                    container_catatan.setVisibility(View.GONE);
                }
            }
        });

        btnSendCatatan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kirimCatatan();
            }
        });

        btnSiram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.bondanekanugraha28.PotKembang");
                if (launchIntent != null) {
                    startActivity(launchIntent);//cek null pointer
                }
            }
        });
        btnPestisida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent toDetailTanaman = new Intent(DetailTanamanActivity.this, DetailRekomendasiTanamanActivity.class);
                toDetailTanaman.putExtra("id", id);
                toDetailTanaman.putExtra("nama", nama);
                toDetailTanaman.putExtra("nama_latin", nama_latin);
                toDetailTanaman.putExtra("deskripsi", deskripsi);
                toDetailTanaman.putExtra("jenis", jenis);
                toDetailTanaman.putExtra("ketinggian", ketinggian);
                toDetailTanaman.putExtra("tanah", tanah);
                toDetailTanaman.putExtra("suhu", suhu);
                toDetailTanaman.putExtra("ph", ph);
                toDetailTanaman.putExtra("kelembapan", kelembapan);
                toDetailTanaman.putExtra("tekanan", tekanan);
                toDetailTanaman.putExtra("lahan", lahan);
                toDetailTanaman.putExtra("air", air);
                toDetailTanaman.putExtra("gambar", gambar);
                startActivity(toDetailTanaman);
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void kirimCatatan() {
        tambahCatatanPresenter.postCatatan(id,
                MainActivity.getId,
                edtCatatan.getText().toString()
        );
    }


    @Override
    public void showProgress() {
        swipeRefreshCatatan.setRefreshing(true);
    }

    @Override
    public void hideProgress() {
        swipeRefreshCatatan.setRefreshing(false);

    }

    @Override
    public void onRequestPostCatatanSuccess(String message) {
        edtCatatan.setText("");
        container_catatan.setVisibility(View.GONE);
    }

    @Override
    public void onRequestError(String message) {
        Toast.makeText(this, "Error: " + message, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void showCatatanProgress() {
        swipeRefreshCatatan.setRefreshing(true);
    }

    @Override
    public void hideCatatanProgress() {
        swipeRefreshCatatan.setRefreshing(false);
    }

    @Override
    public void onRequestCatatanSuccess(List<Catatan> catatan) {
        catatanAdapter = new CatatanAdapter(this, catatan);
        catatanAdapter.notifyDataSetChanged();
        recyclerViewCatatan.setAdapter(catatanAdapter);
        this.catatans = catatan;

    }

    @Override
    public void onRequestCatatanError(String message) {
        Toast.makeText(this, "Error" + message, Toast.LENGTH_SHORT).show();
    }
}
