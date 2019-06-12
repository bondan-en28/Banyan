package com.bintang.banyan.Activity.DetailPost;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bintang.banyan.Activity.AddPost.AddPostActivity;
import com.bintang.banyan.Activity.AddPost.AddPostPresenter;
import com.bintang.banyan.Activity.AddPost.AddPostView;
import com.bintang.banyan.Activity.DetailPost.Komentar.KomentarAdapter;
import com.bintang.banyan.Activity.DetailPost.Komentar.KomentarPresenter;
import com.bintang.banyan.Activity.DetailPost.Komentar.KomentarView;
import com.bintang.banyan.Activity.Main.MainActivity;
import com.bintang.banyan.Model.Komentar;
import com.bintang.banyan.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class DetailPostActivity extends AppCompatActivity implements AddCommentView, KomentarView, AddPostView {

    int id;
    String user_id, judul, deskripsi, gambar, tanggal, user_image;
    Toolbar toolbar;
    Menu toolbarMenu;
    MenuItem menuDelete, menuEdit;
    ImageView ivImagePostDetail, ivUserImage;
    TextView tvDeskripsi, tvTanggal, tvUserName;
    EditText edtComment;
    Button sendKomentar;
    AddCommentPresenter presenter;
    ProgressDialog progressDialog;
    RecyclerView recyclerViewKomen;

    KomentarPresenter komentarPresenter;
    KomentarAdapter komentarAdapter;

    AddPostPresenter addPostPresenter;

    List<Komentar> komentars;

    SwipeRefreshLayout swipeRefreshKomentar;

    FloatingActionButton fabShare;

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
        swipeRefreshKomentar = findViewById(R.id.swipe_refresh_komentar);

        ivImagePostDetail = findViewById(R.id.iv_post_detail);
        tvDeskripsi = findViewById(R.id.tv_deskripsi_detail);
        tvTanggal = findViewById(R.id.tv_tanggal_detail);
        ivUserImage = findViewById(R.id.iv_user_image_detail);
        tvUserName = findViewById(R.id.tv_user_name_detail);
        sendKomentar = findViewById(R.id.btn_send_komen);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Mohon Tunggu...");
        presenter = new AddCommentPresenter(this);
        edtComment = findViewById(R.id.edt_comment);
        fabShare = findViewById(R.id.fab_share);

        Intent intent = getIntent();
        id = intent.getIntExtra("id", 0);
        user_id = intent.getStringExtra("user_id");
        judul = intent.getStringExtra("judul");
        deskripsi = intent.getStringExtra("deskripsi");
        gambar = intent.getStringExtra("gambar");
        tanggal = intent.getStringExtra("tanggal");
        user_image = intent.getStringExtra("user_image");

        komentarPresenter = new KomentarPresenter(this);
        komentarPresenter.getKomentar(id);

        addPostPresenter = new AddPostPresenter(this);

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

        recyclerViewKomen = findViewById(R.id.recycler_view_komen);
        recyclerViewKomen.setLayoutManager(new LinearLayoutManager(this));

        sendKomentar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                kirimKomentar();
            }
        });

        swipeRefreshKomentar.setOnRefreshListener(() -> komentarPresenter.getKomentar(id));

        fabShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody = "Halo Gardeners! Ayo download Banyan, " + user_id + " berbagi pengalamannya lho!";
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Banyan ;)");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, "Share via"));
            }
        });

    }

    private void kirimKomentar() {
        presenter.postKomentar(id,
                MainActivity.getId,
                edtComment.getText().toString()
        );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_detailpost_menu, menu);

        toolbarMenu = menu;

        menuDelete = toolbarMenu.findItem(R.id.menu_delete);
        menuEdit = toolbarMenu.findItem(R.id.menu_edit);

        invalidateOptionsMenu();
        if (user_id.equals(MainActivity.name)) {
            menuDelete.setVisible(true);
            menuEdit.setVisible(true);
        } else {
            menuDelete.setVisible(false);
            menuEdit.setVisible(false);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent(DetailPostActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        switch (item.getItemId()) {
            case android.R.id.home:
                // kembali ke Main Menu

                startActivity(intent);
                finish();
                return true;

            case R.id.menu_delete:
                alertDialog.setTitle("Konfirmasi");
                alertDialog.setMessage("Anda yakin akan menghapus?");
                alertDialog.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        addPostPresenter.deleteKonten(id);
                    }
                });
                alertDialog.setNegativeButton("Batal",
                        (dialog, which) -> dialog.dismiss());

                alertDialog.show();
                return true;

            case R.id.menu_edit:
                alertDialog.setTitle("Konfirmasi");
                alertDialog.setMessage("Yakin untuk mengedit?");
                alertDialog.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent toEdit = new Intent(DetailPostActivity.this, AddPostActivity.class);
                        toEdit.putExtra("id", id);
                        toEdit.putExtra("judul", judul);
                        toEdit.putExtra("deskripsi", deskripsi);
                        toEdit.putExtra("gambar", gambar);
                        startActivity(toEdit);
                        finish();
                    }
                });
                alertDialog.setNegativeButton("Batal",
                        (dialog, which) -> dialog.dismiss());

                alertDialog.show();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public void showProgress() {
        swipeRefreshKomentar.setRefreshing(true);
    }

    @Override
    public void hideProgress() {
        swipeRefreshKomentar.setRefreshing(false);
    }

    @Override
    public void onRequestPostSuccess(String message) {
        Intent intent = new Intent(DetailPostActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    public void onRequestPostError(String message) {
        Toast.makeText(this, "Error Deleting Post", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestSuccess(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        edtComment.setText("");
        komentarPresenter.getKomentar(id);
    }

    @Override
    public void onRequestError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showKomentarProgress() {
        swipeRefreshKomentar.setRefreshing(true);
    }

    @Override
    public void hideKomentarProgress() {
        swipeRefreshKomentar.setRefreshing(false);
    }

    @Override
    public void onRequestKomentarSuccess(List<Komentar> komentar) {
        komentarAdapter = new KomentarAdapter(this, komentar);
        komentarAdapter.notifyDataSetChanged();
        recyclerViewKomen.setAdapter(komentarAdapter);
        this.komentars = komentar;
    }

    @Override
    public void onRequestKomentarError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}