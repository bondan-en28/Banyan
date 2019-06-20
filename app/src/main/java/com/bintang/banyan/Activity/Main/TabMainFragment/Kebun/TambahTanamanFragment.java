package com.bintang.banyan.Activity.Main.TabMainFragment.Kebun;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bintang.banyan.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class TambahTanamanFragment extends Fragment {
    boolean formVisible = true;
    String lat, lon;
    Double elevation;
    TextView tvKetinggian;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View TambahTanamanView = inflater.inflate(R.layout.fragment_tambah_tanaman, container, false);

        // Inflate the layout for this fragment
        return TambahTanamanView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }

    private void initView(View view) {
        ImageView expand = view.findViewById(R.id.btn_expand);
        RelativeLayout containerForm = view.findViewById(R.id.container_form_rekomendasi);
        Button btnRekomendasi = view.findViewById(R.id.btn_rekomendasi);
        tvKetinggian = view.findViewById(R.id.tv_ketinggian);

        btnRekomendasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getElevation();
            }
        });

        expand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!formVisible) {
                    containerForm.setVisibility(View.VISIBLE);
                    expand.setImageResource(R.drawable.ic_expand_down_white_24dp);
                    formVisible = true;
                } else {
                    containerForm.setVisibility(View.GONE);
                    expand.setImageResource(R.drawable.ic_expand_up_white_24dp);
                    formVisible = false;
                }
            }
        });
    }

    private void changeFragment() {
//        getFragmentManager().beginTransaction().replace(R.id.tabHome, new TabHomePost()).addToBackStack(null).commit();
    }

    private void getElevation() {
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Get elevation...");
        progressDialog.show();
        String URL_READ = "https://elevation-api.io/api/elevation?points=(-6.9653418,110.4080114)&key=0D30-ebzSu5JIeWaVoaQb1beM01KbC";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_READ,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("elevations");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject object = jsonArray.getJSONObject(i);

                                lat = object.getString("lat").trim();
                                lon = object.getString("lon").trim();
                                elevation = object.getDouble("elevation");
                            }

                            String tinggi = elevation.toString() + " MDPL";

                            tvKetinggian.setText(tinggi);

                            Toast.makeText(getActivity(), "Latitude: " + lat + ", Longitude: " + lon + ", Elevation: " + elevation, Toast.LENGTH_SHORT).show();

                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressDialog.dismiss();
                            Toast.makeText(getActivity(), "Error: " + e.toString(), Toast.LENGTH_SHORT).show();

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), "Error Connection DISINI: " + error.toString(), Toast.LENGTH_SHORT).show();

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }


}
