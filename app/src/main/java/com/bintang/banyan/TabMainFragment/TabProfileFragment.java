package com.bintang.banyan.TabMainFragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bintang.banyan.MainActivity;
import com.bintang.banyan.R;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import static android.app.Activity.RESULT_OK;

public class TabProfileFragment extends Fragment implements View.OnClickListener {

    public static Bitmap imageprofilbitmap;
    ImageView fotoProfil;
    public static EditText edtNama, edtEmail;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View ProfileView = inflater.inflate(R.layout.fragment_tab_profile, container, false);
        // Inflate the layout for this fragment
        return ProfileView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }

    private void initView(View view) {

        edtNama = view.findViewById(R.id.edt_profile_nama);
        edtEmail = view.findViewById(R.id.edt_profile_email);
        edtNama.setEnabled(false);
        edtNama.setFocusable(false);
        edtNama.setFocusableInTouchMode(false);
        edtEmail.setEnabled(false);
        edtEmail.setFocusable(false);
        edtEmail.setFocusableInTouchMode(false);

        fotoProfil = view.findViewById(R.id.img_profile);
        fotoProfil.setOnClickListener(this);

        try {
            edtNama.setText(MainActivity.name);
            edtEmail.setText(MainActivity.email);

            if (!MainActivity.photo.equals("0")) {
                try {
                    Picasso.get().load(MainActivity.photo)
                            .memoryPolicy(MemoryPolicy.NO_CACHE)
                            .networkPolicy(NetworkPolicy.NO_CACHE)
                            .into(fotoProfil);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getContext(), e.toString(), Toast.LENGTH_SHORT).show();
        }

    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_profile:
                pilihFoto();
                break;
        }
    }

    private void pilihFoto() {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        gallery.putExtra("crop", "true");
        gallery.putExtra("aspectX", 1);
        gallery.putExtra("aspectY", 1);
        gallery.putExtra("outputX", 200);
        gallery.putExtra("outputY", 200);
        gallery.putExtra("return-data", true);
        startActivityForResult(gallery, 2);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 2 && data != null) {
            Bundle extras = data.getExtras();
            imageprofilbitmap = extras.getParcelable("data");

            fotoProfil.setImageBitmap(imageprofilbitmap);
        }
    }

}
