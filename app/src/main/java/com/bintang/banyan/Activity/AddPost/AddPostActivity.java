package com.bintang.banyan.Activity.AddPost;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.bintang.banyan.R;

public class AddPostActivity extends AppCompatActivity {

    public static Bitmap imageprofilbitmap;
    ImageView imgPost;
    EditText edtJudul, edtDeskripsi;
    Button btnPost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);

        imgPost = findViewById(R.id.iv_post);
        edtJudul = findViewById(R.id.edt_judul);
        edtDeskripsi = findViewById(R.id.edt_deskripsi);
        btnPost = findViewById(R.id.btn_send_post);

        imgPost.setOnClickListener(view ->
                pilihFoto());
    }

    private void pilihFoto() {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        gallery.putExtra("crop", "true");
        gallery.putExtra("aspectX", 1);
        gallery.putExtra("aspectY", 1);
        gallery.putExtra("outputX", 500);
        gallery.putExtra("outputY", 500);
        gallery.putExtra("return-data", true);
        startActivityForResult(gallery, 2);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 2 && data != null) {
            Bundle extras = data.getExtras();
            imageprofilbitmap = extras.getParcelable("data");

            imgPost.setImageBitmap(imageprofilbitmap);
        }
    }

}
