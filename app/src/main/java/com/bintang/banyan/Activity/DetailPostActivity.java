package com.bintang.banyan.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bintang.banyan.Activity.Main.MainActivity;
import com.bintang.banyan.R;
import com.squareup.picasso.Picasso;

public class DetailPostActivity extends AppCompatActivity {

    int id;
    String user_id, judul, deskripsi, gambar, tanggal, user_image;
    Toolbar toolbar;
    ImageView ivImagePostDetail, ivUserImage;
    TextView tvDeskripsi, tvTanggal, tvUserName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_post);

        CollapsingToolbarLayout toolbarLayout;
        toolbarLayout = findViewById(R.id.toolbar_post_collapsing);
        toolbar = findViewById(R.id.toolbar_post);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        ivImagePostDetail = findViewById(R.id.iv_post_detail);
        tvDeskripsi = findViewById(R.id.tv_deskripsi_detail);
        tvTanggal = findViewById(R.id.tv_tanggal_detail);
        ivUserImage = findViewById(R.id.iv_user_image_detail);
        tvUserName = findViewById(R.id.tv_user_name_detail);

        Intent intent = getIntent();
        id = intent.getIntExtra("id", 0);
        user_id = intent.getStringExtra("user_id");
        judul = intent.getStringExtra("judul");
        deskripsi = intent.getStringExtra("deskripsi");
        gambar = intent.getStringExtra("gambar");
        tanggal = intent.getStringExtra("tanggal");
        user_image = intent.getStringExtra("user_image");

        toolbar.setTitle(judul);
        toolbarLayout.setTitle(judul);

        tvUserName.setText(user_id);
        tvDeskripsi.setText(deskripsi);
        tvTanggal.setText(tanggal);
        try {
            Picasso.get().load(gambar)
                    .error(R.drawable.ic_person_black_100dp)
                    .into(ivImagePostDetail);

            Picasso.get().load(user_image)
                    .error(R.drawable.ic_person_black_100dp)
                    .into(ivUserImage);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // kembali ke Main Menu

                Intent intent = new Intent(DetailPostActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
