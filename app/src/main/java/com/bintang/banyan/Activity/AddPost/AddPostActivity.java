package com.bintang.banyan.Activity.AddPost;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bintang.banyan.Activity.Main.MainActivity;
import com.bintang.banyan.R;

import java.io.ByteArrayOutputStream;

public class AddPostActivity extends AppCompatActivity implements AddPostView {

    Bitmap imageprofilbitmap;
    AddPostPresenter presenter;
    ImageView imgPost;
    EditText edtJudul, edtDeskripsi;
    Button btnPost;
    ProgressDialog progressDialog;
    String user_id = MainActivity.getId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);

        imgPost = findViewById(R.id.iv_post);
        edtJudul = findViewById(R.id.edt_judul);
        edtDeskripsi = findViewById(R.id.edt_deskripsi);
        btnPost = findViewById(R.id.btn_send_post);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Mohon Tunggu...");
        presenter = new AddPostPresenter(this);

        imgPost.setOnClickListener(view ->
                pilihFoto());
        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadPost();
            }
        });
    }

    private void pilihFoto() {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        gallery.putExtra("crop", "true");
        gallery.putExtra("aspectX", 1);
        gallery.putExtra("aspectY", 1);
        gallery.putExtra("outputX", 500);
        gallery.putExtra("outputY", 500);
        gallery.putExtra("scale", true);
        gallery.putExtra("scaleUpIfNeeded", true);
        gallery.putExtra("return-data", true);

        startActivityForResult(gallery, 2);
/*
        //Create an Intent with action as ACTION_PICK
        Intent intent=new Intent(Intent.ACTION_PICK);
        // Sets the type as image/*. This ensures only components of type image are selected
        intent.setType("image/*");
        //We pass an extra array with the accepted mime types. This will ensure only components with these MIME types as targeted.
        String[] mimeTypes = {"image/jpeg", "image/png"};
        //getExtras
        intent.putExtra(Intent.EXTRA_MIME_TYPES,mimeTypes);
        // Launching the Intent
        startActivityForResult(intent,2);
*/
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 2 && data != null) {
            Bundle extras = data.getExtras();
            imageprofilbitmap = extras.getParcelable("data");

            imgPost.setImageBitmap(imageprofilbitmap);
            imgPost.setImageURI(data.getData());
/*
            //data.getData return the content URI for the selected Image
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };
            // Get the cursor
            Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            // Move to first row
            cursor.moveToFirst();
            //Get the column index of MediaStore.Images.Media.DATA
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            //Gets the String value in the column
            String imgDecodableString = cursor.getString(columnIndex);
            cursor.close();
            // Set the Image in ImageView after decoding the String
            imgPost.setImageBitmap(BitmapFactory.decodeFile(imgDecodableString));
*/
        }
    }

    private void uploadPost() {
        presenter.postKonten(user_id,
                edtJudul.getText().toString().trim(),
                edtDeskripsi.getText().toString().trim(),
                getStringImage(imageprofilbitmap)
        );
    }

    private String getStringImage(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);

        byte[] imageByteArray = byteArrayOutputStream.toByteArray();
        String encodedImage = Base64.encodeToString(imageByteArray, Base64.DEFAULT);
        return encodedImage;
    }


    @Override
    public void showProgress() {
        progressDialog.show();
    }

    @Override
    public void hideProgress() {
        progressDialog.hide();
    }

    @Override
    public void onRequestSuccess(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void onRequestError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
    }

}
