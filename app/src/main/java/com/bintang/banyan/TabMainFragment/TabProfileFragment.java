package com.bintang.banyan.TabMainFragment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bintang.banyan.Activity.Main.MainActivity;
import com.bintang.banyan.R;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

import static android.app.Activity.RESULT_OK;

public class TabProfileFragment extends Fragment implements View.OnClickListener {

    public static Bitmap imageprofilbitmap;
    public static ImageView fotoProfil;
    public static Button btnGantiFoto;
    public static EditText edtNama, edtEmail, edtTtl, edtAlamat, edtNoTelp;
    DatePickerDialog.OnDateSetListener date;
    Calendar myCalendar;

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
        edtTtl = view.findViewById(R.id.edt_profile_ttl);
        edtAlamat = view.findViewById(R.id.edt_profile_alamat);
        edtNoTelp = view.findViewById(R.id.edt_profile_notelp);
        fotoProfil = view.findViewById(R.id.img_profile);
        btnGantiFoto = view.findViewById(R.id.btn_ganti_foto);
        LinearLayout txtLengkapi = view.findViewById(R.id.container_lengkapi);
        myCalendar = Calendar.getInstance();

        btnGantiFoto.setOnClickListener(this);
        edtTtl.setOnClickListener(this);

        initContent(false);

        try {
            edtNama.setText(MainActivity.name);
            edtEmail.setText(MainActivity.email);
            edtTtl.setText(MainActivity.ttl);
            edtAlamat.setText(MainActivity.alamat);
            edtNoTelp.setText(MainActivity.notelp);

            try {
                Picasso.get().load(MainActivity.photo)
                        .memoryPolicy(MemoryPolicy.NO_CACHE)
                        .networkPolicy(NetworkPolicy.NO_CACHE)
                        .error(R.drawable.ic_person_black_100dp)
                        .into(fotoProfil);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getContext(), e.toString(), Toast.LENGTH_SHORT).show();
        }


        if (edtNama.getText().toString().isEmpty() ||
                edtEmail.getText().toString().isEmpty() ||
                edtTtl.getText().toString().isEmpty() ||
                edtAlamat.getText().toString().isEmpty() ||
                edtNoTelp.getText().toString().isEmpty() ||
                edtNama.getText().toString().equals("null") ||
                edtEmail.getText().toString().equals("null") ||
                edtTtl.getText().toString().equals("null") ||
                edtAlamat.getText().toString().equals("null") ||
                edtNoTelp.getText().toString().equals("null")
        ) {
            txtLengkapi.setVisibility(View.VISIBLE);
        } else {
            txtLengkapi.setVisibility(View.GONE);
        }

    }

    private void updateLabel() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        edtTtl.setText(sdf.format(myCalendar.getTime()));
    }

    private void initContent(boolean kondisi) {
        edtNama.setEnabled(kondisi);
        edtNama.setFocusable(kondisi);
        edtNama.setFocusableInTouchMode(kondisi);

        edtEmail.setEnabled(kondisi);
        edtEmail.setFocusable(kondisi);
        edtEmail.setFocusableInTouchMode(kondisi);

        edtTtl.setEnabled(kondisi);
        edtTtl.setFocusable(kondisi);
        edtTtl.setFocusableInTouchMode(kondisi);

        edtAlamat.setEnabled(kondisi);
        edtAlamat.setFocusable(kondisi);
        edtAlamat.setFocusableInTouchMode(kondisi);

        edtNoTelp.setEnabled(kondisi);
        edtNoTelp.setFocusable(kondisi);
        edtNoTelp.setFocusableInTouchMode(kondisi);

        btnGantiFoto.setEnabled(kondisi);
        btnGantiFoto.setClickable(kondisi);
        if (kondisi) {
            btnGantiFoto.setVisibility(View.VISIBLE);
        } else {
            btnGantiFoto.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_ganti_foto:
                pilihFoto();
                break;

            case R.id.edt_profile_ttl:

                new DatePickerDialog(Objects.requireNonNull(getActivity()), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();

                date = new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear,
                                          int dayOfMonth) {
                        // TODO myCalendar AmBIL TGL
                        myCalendar.set(Calendar.YEAR, year);
                        myCalendar.set(Calendar.MONTH, monthOfYear);
                        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        updateLabel();
                    }

                };

                break;
        }
    }

    private void pilihFoto() {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        gallery.putExtra("crop", "true");
        gallery.putExtra("aspectX", 1);
        gallery.putExtra("aspectY", 1);
        gallery.putExtra("outputX", 100);
        gallery.putExtra("outputY", 100);
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
