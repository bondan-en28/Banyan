package com.bintang.banyan.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.bintang.banyan.R;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

public class DetailPostActivity extends AppCompatActivity {

    int id;
    String user_id, judul, deskripsi, gambar, tanggal;
    Toolbar toolbar;
    ImageView ivImagePostDetail;
    TextView tvDeskripsi, tvTanggal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_post);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitleMarginStart(50);
        setSupportActionBar(toolbar);

        ivImagePostDetail = findViewById(R.id.iv_post_detail);
        tvDeskripsi = findViewById(R.id.tv_deskripsi_detail);
        tvTanggal = findViewById(R.id.tv_tanggal_detail);

        Intent intent = getIntent();
        id = intent.getIntExtra("id", 0);
        user_id = intent.getStringExtra("user_id");
        judul = intent.getStringExtra("judul");
        deskripsi = intent.getStringExtra("deskripsi");
        gambar = intent.getStringExtra("gambar");
        tanggal = intent.getStringExtra("tanggal");

        toolbar.setTitle(judul);
        try {
            Picasso.get().load(gambar)
                    .memoryPolicy(MemoryPolicy.NO_CACHE)
                    .networkPolicy(NetworkPolicy.NO_CACHE)
                    .error(R.drawable.ic_person_black_100dp)
                    .into(ivImagePostDetail);
        } catch (Exception e) {
            e.printStackTrace();
        }
        tvDeskripsi.setText(deskripsi);
        tvTanggal.setText(tanggal);

    }
}
