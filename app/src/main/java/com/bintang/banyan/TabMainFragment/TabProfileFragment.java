package com.bintang.banyan.TabMainFragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bintang.banyan.MainActivity;
import com.bintang.banyan.R;
import com.bintang.banyan.SessionManager;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

import static android.app.Activity.RESULT_OK;
import static com.android.volley.VolleyLog.TAG;
import static com.bintang.banyan.MainActivity.getId;

public class TabProfileFragment extends Fragment implements View.OnClickListener {

    public static Bitmap imageprofilbitmap;
    private static String URL_EDIT = "https://bonbon28.000webhostapp.com/banyan/edit_detail.php";
    private static String URL_UPLOAD = "https://bonbon28.000webhostapp.com/banyan/upload.php";
    SessionManager sessionManager;
    Button btnLogout, btnSave;
    ImageView fotoProfil;
    private EditText edtNama, edtEmail;

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
        sessionManager = new SessionManager(getActivity());

        edtNama = view.findViewById(R.id.edt_profile_nama);
        edtEmail = view.findViewById(R.id.edt_profile_email);

        btnLogout = view.findViewById(R.id.btn_logout);
        btnSave = view.findViewById(R.id.btn_save);

        fotoProfil = view.findViewById(R.id.img_profile);

        btnLogout.setOnClickListener(this);
        btnSave.setOnClickListener(this);
        fotoProfil.setOnClickListener(this);

        edtNama.setText(MainActivity.name);
        edtEmail.setText(MainActivity.email);

        Toast.makeText(getActivity(), MainActivity.photo, Toast.LENGTH_SHORT).show();

        if (!MainActivity.photo.equals("0")) {
            try {
                Picasso.get().load(MainActivity.photo).memoryPolicy(MemoryPolicy.NO_CACHE).networkPolicy(NetworkPolicy.NO_CACHE).into(fotoProfil);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_logout:
                sessionManager.logout();
                break;
            case R.id.btn_save:
                saveEdit();
                break;
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

    private void uploadFoto(final String id, final String foto) {

        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Uploading...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_UPLOAD,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        Log.i(TAG, response);

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");

                            if (success.equals("1")) {
                                Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getActivity(), "Coba Lagi.", Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getActivity(), "Error." + e.toString(), Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), "Error." + error.toString(), Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id", id);
                params.put("foto", foto);

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);


    }

    private String getStringImage(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);

        byte[] imageByteArray = byteArrayOutputStream.toByteArray();
        String encodedImage = Base64.encodeToString(imageByteArray, Base64.DEFAULT);
        return encodedImage;
    }


    private void saveEdit() {

        try {
            uploadFoto(getId, getStringImage(imageprofilbitmap));

        } catch (Exception e) {
            e.printStackTrace();
        }

        final String name = edtNama.getText().toString().trim();
        final String email = edtEmail.getText().toString().trim();
        final String id = getId;

        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_EDIT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");

                            if (success.equals("1")) {
                                Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            } else {
                                Toast.makeText(getActivity(), "Error.", Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getActivity(), "Error Login." + e.toString(), Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), "Error Login." + error.toString(), Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("name", name);
                params.put("email", email);
                params.put("id", id);

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }

}
