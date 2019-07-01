package com.bintang.banyan.Activity.AddPost;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bintang.banyan.Activity.Main.MainActivity;
import com.bintang.banyan.R;
import com.bumptech.glide.Glide;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddPostActivity extends AppCompatActivity implements AddPostView {

    public static final int REQUEST_IMAGE = 100;
    private static final String TAG = AddPostActivity.class.getSimpleName();
    @BindView(R.id.iv_post)
    ImageView ivPost;

    Bitmap bitmap;
    AddPostPresenter presenter;
    EditText edtJudul, edtDeskripsi;
    Button btnPost;
    ProgressDialog progressDialog;
    String user_id = MainActivity.getId;
    String intentGambar, intentJudulPost, intentDeskripsi;
    int intentId;
    boolean isEditPost = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);

        ButterKnife.bind(this);

        ivPost = findViewById(R.id.iv_post);
        edtJudul = findViewById(R.id.edt_judul);
        edtDeskripsi = findViewById(R.id.edt_deskripsi);
        btnPost = findViewById(R.id.btn_send_post);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Mohon Tunggu...");
        presenter = new AddPostPresenter(this);

        ImagePickerActivity.clearCache(this);
        loadDefaultPic();

        Intent intent = getIntent();
        intentId = intent.getIntExtra("id", 0);
        intentJudulPost = intent.getStringExtra("judul");
        intentDeskripsi = intent.getStringExtra("deskripsi");
        intentGambar = intent.getStringExtra("gambar");

        setDataFromIntentExtra();


        //CLICK LISTENER
//        imgPost.setOnClickListener(view -> pilihFoto());
        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isEditPost) {
                    editPost();
                    Toast.makeText(AddPostActivity.this, "is Edit Post", Toast.LENGTH_SHORT).show();
                } else {
                    uploadPost();
                    Toast.makeText(AddPostActivity.this, "New Post", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }


    private void setDataFromIntentExtra() {
        if (intentId != 0) {
            edtJudul.setText(intentJudulPost);
            edtDeskripsi.setText(intentDeskripsi);
            Picasso.get().load(intentGambar)
                    .error(R.drawable.ic_person_black_100dp)
                    .into(ivPost);

            try {
                bitmap = ((BitmapDrawable) ivPost.getDrawable()).getBitmap();

            } catch (Exception e) {
                e.printStackTrace();
            }

            isEditPost = true;
        }

    }

    private void loadDefaultPic() {
        Glide.with(this).load(R.drawable.ic_banyan_496px_post)
                .into(ivPost);
    }

    //==================================================================================================================================
    private void loadProfile(String url) {
        Log.d(TAG, "Image cache path: " + url);

        Glide.with(this).load(url)
                .into(ivPost);
    }

    @OnClick(R.id.iv_post)
    void onProfileImageClick() {
        Dexter.withActivity(this)
                .withPermissions(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {
                            showImagePickerOptions();
                        }

                        if (report.isAnyPermissionPermanentlyDenied()) {
                            showSettingsDialog();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();
    }

    private void showImagePickerOptions() {
        ImagePickerActivity.showImagePickerOptions(this, new ImagePickerActivity.PickerOptionListener() {
            @Override
            public void onTakeCameraSelected() {
                launchCameraIntent();
            }

            @Override
            public void onChooseGallerySelected() {
                launchGalleryIntent();
            }
        });
    }

    private void launchCameraIntent() {
        Intent intent = new Intent(AddPostActivity.this, ImagePickerActivity.class);
        intent.putExtra(ImagePickerActivity.INTENT_IMAGE_PICKER_OPTION, ImagePickerActivity.REQUEST_IMAGE_CAPTURE);

        // setting aspect ratio
        intent.putExtra(ImagePickerActivity.INTENT_LOCK_ASPECT_RATIO, true);
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_X, 1); // 16x9, 1x1, 3:4, 3:2
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_Y, 1);

        // setting maximum bitmap width and height
        intent.putExtra(ImagePickerActivity.INTENT_SET_BITMAP_MAX_WIDTH_HEIGHT, true);
        intent.putExtra(ImagePickerActivity.INTENT_BITMAP_MAX_WIDTH, 1000);
        intent.putExtra(ImagePickerActivity.INTENT_BITMAP_MAX_HEIGHT, 1000);

        startActivityForResult(intent, REQUEST_IMAGE);
    }

    private void launchGalleryIntent() {
        Intent intent = new Intent(AddPostActivity.this, ImagePickerActivity.class);
        intent.putExtra(ImagePickerActivity.INTENT_IMAGE_PICKER_OPTION, ImagePickerActivity.REQUEST_GALLERY_IMAGE);

        // setting aspect ratio
        intent.putExtra(ImagePickerActivity.INTENT_LOCK_ASPECT_RATIO, true);
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_X, 1); // 16x9, 1x1, 3:4, 3:2
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_Y, 1);
        startActivityForResult(intent, REQUEST_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_IMAGE) {
            if (resultCode == Activity.RESULT_OK) {
                Uri uri = data.getParcelableExtra("path");
                try {
                    // You can update this bitmap to your server
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                    ivPost.setImageBitmap(bitmap);

                    // loading profile image from local cache
                    loadProfile(uri.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Showing Alert Dialog with Settings option
     * Navigates user to app settings
     * NOTE: Keep proper title and message depending on your app
     */
    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(AddPostActivity.this);
        builder.setTitle(getString(R.string.dialog_permission_title));
        builder.setMessage(getString(R.string.dialog_permission_message));
        builder.setPositiveButton(getString(R.string.go_to_settings), (dialog, which) -> {
            dialog.cancel();
            openSettings();
        });
        builder.setNegativeButton(getString(android.R.string.cancel), (dialog, which) -> dialog.cancel());
        builder.show();

    }

    // navigating user to app settings
    private void openSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, 101);
    }


//==================================================================================================================================


    private void uploadPost() {
        presenter.postKonten(user_id,
                edtJudul.getText().toString().trim(),
                edtDeskripsi.getText().toString().trim(),
                getStringImage(bitmap)
        );
    }

    private void editPost() {
        presenter.editKonten(
                intentId,
                edtJudul.getText().toString().trim(),
                edtDeskripsi.getText().toString().trim(),
                getStringImage(((BitmapDrawable) ivPost.getDrawable()).getBitmap())
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
    public void onRequestPostSuccess(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(AddPostActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    public void onRequestPostError(String message) {
        Toast.makeText(this, "Error" + message, Toast.LENGTH_SHORT).show();
    }

}
