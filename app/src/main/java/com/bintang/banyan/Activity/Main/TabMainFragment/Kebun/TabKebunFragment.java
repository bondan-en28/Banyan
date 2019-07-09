package com.bintang.banyan.Activity.Main.TabMainFragment.Kebun;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bintang.banyan.Activity.DetailTanaman.DetailTanamanActivity;
import com.bintang.banyan.Activity.Main.MainActivity;
import com.bintang.banyan.Model.MyTanaman;
import com.bintang.banyan.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TabKebunFragment extends Fragment implements TanamanView {
    public static RecyclerView tanamanRecyclerView;
    public static SwipeRefreshLayout tanamanSwipeRefresh;
    public static TanamanAdapter.ItemClickListener tanamanItemClickListener;
    public String kota, provinsi, negara, zona_waktu, kelembapan, tekanan, suhu, tinggi, latitude, longitude;
    public TextView tvTanggal, tvKota, tvProvinsiNegara, tvSuhu, tvLatLon, tvKetinggian, tvKelembapan, tvTekanan;
    Double elevation;
    TanamanPresenter tanamanPresenter;
    TanamanAdapter tanamanAdapter;
    List<MyTanaman> myTanaman;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private Location mLastKnownLocation;
    private LocationCallback locationCallback;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View KebunView = inflater.inflate(R.layout.fragment_tab_kebun, container, false);

        // Inflate the layout for this fragment
        return KebunView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }

    private void initView(View view) {
        ImageView btnKelola = view.findViewById(R.id.btn_kelola);
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());
        tvTanggal = view.findViewById(R.id.tv_tanggal_hasil);
        tvKota = view.findViewById(R.id.tv_kota_hasil);
        tvProvinsiNegara = view.findViewById(R.id.tv_provinsi_negara_hasil);
        tvSuhu = view.findViewById(R.id.tv_suhu_hasil);
        tvLatLon = view.findViewById(R.id.tv_lat_lon_hasil);
        tvKetinggian = view.findViewById(R.id.tv_ketinggian_hasil);
        tvKelembapan = view.findViewById(R.id.tv_kelembapan_hasil);
        tvTekanan = view.findViewById(R.id.tv_tekanan_hasil);
        Date today = Calendar.getInstance().getTime();//getting date
        SimpleDateFormat formatter = new SimpleDateFormat("E, dd MMM yyyy z");//formating according to my need
        String date = formatter.format(today);
        tvTanggal.setText(date);

        tanamanRecyclerView = view.findViewById(R.id.recycler_view_tanaman);
        tanamanRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        tanamanSwipeRefresh = view.findViewById(R.id.swipe_refresh_tanaman);
        tanamanPresenter = new TanamanPresenter(this);
        tanamanPresenter.getTanamanku(Integer.valueOf(MainActivity.getId));

        Dexter.withActivity(getActivity())
                .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        // TODO: Get location
                        getDeviceLocation();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        if (response.isPermanentlyDenied()) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                            builder.setTitle("Permission Denied!")
                                    .setMessage("Izin untuk mengakses lokasi ditolak permanen, anda harus mengaktifkannya melalui pengaturan")
                                    .setNegativeButton("Cancel", null)
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            Intent intent = new Intent();
                                            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                            intent.setData(Uri.fromParts("package", getContext().getPackageName(), null));
                                            startActivity(intent);
                                        }
                                    }).show();
                        } else {
                            Toast.makeText(getContext(), "Permission Denied!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();


        btnKelola.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeFragment();
            }
        });


        tanamanSwipeRefresh.setOnRefreshListener(() -> tanamanPresenter.getTanamanku(Integer.valueOf(MainActivity.getId)));
        tanamanItemClickListener = (new TanamanAdapter.ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                int id = myTanaman.get(position).getId();
                String nama = myTanaman.get(position).getNama();
                String nama_latin = myTanaman.get(position).getNama_latin();
                String deskripsi = myTanaman.get(position).getDeskripsi();
                String jenis = myTanaman.get(position).getJenis();
                String ketinggian = myTanaman.get(position).getKetinggian();
                String struktur_tanah = myTanaman.get(position).getStruktur_tanah();
                String suhu = myTanaman.get(position).getSuhu();
                String ph = myTanaman.get(position).getPh();
                String kelembapan = myTanaman.get(position).getKelembapan();
                String tekanan = myTanaman.get(position).getTekanan();
                String lahan = myTanaman.get(position).getLahan();
                String air = myTanaman.get(position).getAir();
                String gambar = myTanaman.get(position).getGambar();

                Intent toTanamanDetail = new Intent(getActivity(), DetailTanamanActivity.class);
                toTanamanDetail.putExtra("id", id);
                toTanamanDetail.putExtra("nama", nama);
                toTanamanDetail.putExtra("nama_latin", nama_latin);
                toTanamanDetail.putExtra("deskripsi", deskripsi);
                toTanamanDetail.putExtra("jenis", jenis);
                toTanamanDetail.putExtra("ketinggian", ketinggian);
                toTanamanDetail.putExtra("tanah", struktur_tanah);
                toTanamanDetail.putExtra("suhu", suhu);
                toTanamanDetail.putExtra("ph", ph);
                toTanamanDetail.putExtra("kelembapan", kelembapan);
                toTanamanDetail.putExtra("tekanan", tekanan);
                toTanamanDetail.putExtra("lahan", lahan);
                toTanamanDetail.putExtra("air", air);
                toTanamanDetail.putExtra("gambar", gambar);

                startActivity(toTanamanDetail);

//                Toast.makeText(TabKebunFragment.this.getContext(), id + nama + jenis + ketinggian + struktur_tanah + suhu + ph, Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void changeFragment() {
        getFragmentManager().beginTransaction().replace(R.id.fragment_container, new TambahTanamanFragment()).addToBackStack(null).commit();
    }

    @SuppressLint("MissingPermission")
    private void getDeviceLocation() {
        mFusedLocationProviderClient.getLastLocation()
                .addOnCompleteListener(new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful()) {
                            mLastKnownLocation = task.getResult();
                            if (mLastKnownLocation != null) {
                                latitude = String.valueOf(mLastKnownLocation.getLatitude());
                                longitude = String.valueOf(mLastKnownLocation.getLongitude());
                                getElevation(latitude, longitude);
                                getWeather(String.valueOf(mLastKnownLocation.getLatitude()), String.valueOf(mLastKnownLocation.getLongitude()));
//                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(mLastKnownLocation.getLatitude(), mLastKnownLocation.getLongitude()), DEFAULT_ZOOM));
                            } else {
                                LocationRequest locationRequest = LocationRequest.create();
                                locationRequest.setInterval(10000);
                                locationRequest.setFastestInterval(5000);
                                locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
                                locationCallback = new LocationCallback() {
                                    @Override
                                    public void onLocationResult(LocationResult locationResult) {
                                        super.onLocationResult(locationResult);
                                        if (locationRequest == null) {
                                            return;
                                        }
                                        mLastKnownLocation = locationResult.getLastLocation();
//                                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(mLastKnownLocation.getLatitude(), mLastKnownLocation.getLongitude()), DEFAULT_ZOOM));
                                        latitude = String.valueOf(mLastKnownLocation.getLatitude());
                                        longitude = String.valueOf(mLastKnownLocation.getLongitude());
                                        getElevation(latitude, longitude);
                                        getWeather(String.valueOf(mLastKnownLocation.getLatitude()), String.valueOf(mLastKnownLocation.getLongitude()));
                                        mFusedLocationProviderClient.removeLocationUpdates(locationCallback);
                                    }
                                };
                                mFusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, null);
                            }
                        } else {
                            Toast.makeText(getContext(), "Unable to get last location", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private boolean getElevation(String lat, String lon) {
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Get elevation...");
        progressDialog.show();
        String URL_READ = "https://elevation-api.io/api/elevation?points=(" + lat + "," + lon + ")&key=0D30-ebzSu5JIeWaVoaQb1beM01KbC";

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

                                elevation = object.getDouble("elevation");
                            }

                            tinggi = elevation.toString();
                            tvKetinggian.setText(tinggi);

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
        return true;
    }

    private boolean getWeather(String lat, String lon) {
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Get Weather Information...");
        progressDialog.show();
        String URL_READ = "http://bonbon28.000webhostapp.com/tosca/getweather.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_READ,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONObject location = jsonObject.getJSONObject("location");

                            kota = location.getString("city");
                            provinsi = location.getString("region");
                            negara = location.getString("country");
                            zona_waktu = location.getString("timezone_id");

                            JSONObject currentObeservation = jsonObject.getJSONObject("current_observation");
                            JSONObject atmosphere = currentObeservation.getJSONObject("atmosphere");
                            JSONObject condition = currentObeservation.getJSONObject("condition");

                            kelembapan = atmosphere.getString("humidity");
                            tekanan = atmosphere.getString("pressure");
                            suhu = condition.getString("temperature");

                            tvKota.setText(kota);
                            tvProvinsiNegara.setText(provinsi + ", " + negara);

                            tvSuhu.setText(suhu + " C");
                            tvLatLon.setText(latitude + ", " + longitude);
                            tvKelembapan.setText(kelembapan);
                            tvTekanan.setText(tekanan);


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
                params.put("latitude", lat);
                params.put("longitude", lon);

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
        return true;
    }


    @Override
    public void showLoading() {
        tanamanSwipeRefresh.setRefreshing(true);
    }

    @Override
    public void hideLoading() {
        tanamanSwipeRefresh.setRefreshing(false);
    }

    @Override
    public void onGetTanamanResult(List<MyTanaman> myTanaman) {
        tanamanAdapter = new TanamanAdapter(getActivity(), myTanaman, tanamanItemClickListener);

        tanamanAdapter.notifyDataSetChanged();
        tanamanRecyclerView.setAdapter(tanamanAdapter);
        this.myTanaman = myTanaman;

    }

    @Override
    public void onErrorLoading(String message) {

    }
}