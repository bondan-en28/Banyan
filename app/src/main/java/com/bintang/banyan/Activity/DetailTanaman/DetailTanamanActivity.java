package com.bintang.banyan.Activity.DetailTanaman;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bintang.banyan.Activity.DetailRekomendasiTanaman.DetailRekomendasiTanamanActivity;
import com.bintang.banyan.Activity.DetailTanaman.Catatan.CatatanAdapter;
import com.bintang.banyan.Activity.DetailTanaman.Catatan.CatatanPresenter;
import com.bintang.banyan.Activity.DetailTanaman.Catatan.CatatanView;
import com.bintang.banyan.Activity.DetailTanaman.IotData.IotDataAdapter;
import com.bintang.banyan.Activity.DetailTanaman.IotData.ReqDataPresenter;
import com.bintang.banyan.Activity.DetailTanaman.IotData.ReqDataView;
import com.bintang.banyan.Model.Catatan;
import com.bintang.banyan.Model.IotData;
import com.bintang.banyan.R;

import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.bintang.banyan.Activity.Main.MainActivity.getId;

public class DetailTanamanActivity extends AppCompatActivity implements TambahCatatanView, CatatanView, ReqDataView {
    public static Toolbar toolbar;
    RecyclerView recyclerViewCatatan;
    CatatanPresenter catatanPresenter;
    CatatanAdapter catatanAdapter;
    private static String URL_READ = "https://bonbon28.000webhostapp.com/banyan/relay.php";
    List<Catatan> catatans;
    public RequestQueue queue;
    SwipeRefreshLayout swipeRefreshCatatan;
    CardView container_catatan;
    IotDataAdapter iotDataAdapter;
    TambahCatatanPresenter tambahCatatanPresenter;
    EditText edtCatatan;
    List<IotData> iotData;
    Button btnSiram, btnPupuk, btnDetail, btnCatatan, btnSendCatatan, btnLog;
    ReqDataPresenter reqDataPresenter;
    ProgressDialog progressDialog;
    TextView tvNama, tvNamaLatin, tvUsia, tvCahaya, tvSuhu, tvLembabUdara, tvLembabTanah;
    int id;
    String nama, nama_latin, deskripsi, jenis, ketinggian, tanah, suhu, ph, kelembapan, tekanan, lahan, air, gambar, tanggal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_tanaman);

        toolbar = findViewById(R.id.toolbar_tanaman);
        toolbar.setTitleMarginStart(20);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        catatanPresenter = new CatatanPresenter(this);
        reqDataPresenter = new ReqDataPresenter(this);
        tambahCatatanPresenter = new TambahCatatanPresenter(this);

        swipeRefreshCatatan = findViewById(R.id.swipe_refresh_catatan);
        progressDialog = new ProgressDialog(DetailTanamanActivity.this);

        tvNama = findViewById(R.id.tv_nama_tanaman);
        tvNamaLatin = findViewById(R.id.tv_nama_latin_tanaman);
        tvUsia = findViewById(R.id.tv_usia_tanaman);
        tvCahaya = findViewById(R.id.tv_cahaya);
        tvSuhu = findViewById(R.id.tv_suhu);
        tvLembabUdara = findViewById(R.id.tv_lembab_udara);
        tvLembabTanah = findViewById(R.id.tv_lembab_tanah);

        btnSiram = findViewById(R.id.btn_siram);
        btnPupuk = findViewById(R.id.btn_pupuk);
        btnDetail = findViewById(R.id.btn_detail);
        btnLog = findViewById(R.id.btn_log);
        btnCatatan = findViewById(R.id.btn_catatan);
        btnSendCatatan = findViewById(R.id.btn_send_catatan);
        container_catatan = findViewById(R.id.container_catatan_tanaman);
        edtCatatan = findViewById(R.id.edt_catatan);

        Intent intent = getIntent();
        id = intent.getIntExtra("id", 0);
        nama = intent.getStringExtra("nama");
        nama_latin = intent.getStringExtra("nama_latin");
        deskripsi = intent.getStringExtra("deskripsi");
        jenis = intent.getStringExtra("jenis");
        ketinggian = intent.getStringExtra("ketinggian");
        tanah = intent.getStringExtra("tanah");
        suhu = intent.getStringExtra("suhu");
        ph = intent.getStringExtra("ph");
        kelembapan = intent.getStringExtra("kelembapan");
        tekanan = intent.getStringExtra("tekanan");
        lahan = intent.getStringExtra("lahan");
        air = intent.getStringExtra("air");
        gambar = intent.getStringExtra("gambar");
        tanggal = intent.getStringExtra("date");

        tvNama.setText(nama);
        tvNamaLatin.setText(nama_latin);
        String tgl = tanggal;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date d = null;
        try {
            d = sdf.parse(tgl);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH) + 1;
        int date = c.get(Calendar.DATE);

//        recyclerViewAdapter.tv_usia.setText(String.valueOf(year)+String.valueOf(month)+String.valueOf(date));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            LocalDate l1 = LocalDate.of(year, month, date);
            LocalDate now = LocalDate.now();
            Period diff1 = Period.between(l1, now);
            String usia;
            if (diff1.getMonths() == 0 && diff1.getYears() == 0) {
                usia = String.valueOf(diff1.getDays());
            } else if (diff1.getMonths() > 0 && diff1.getYears() == 0) {
                usia = String.valueOf(diff1.getMonths() * 30 +
                        diff1.getDays());
            } else if (diff1.getYears() > 0 && diff1.getMonths() == 0) {
                usia = (diff1.getYears() * 12 * 30 +
                        diff1.getDays()) + " Hari";
            } else if (diff1.getYears() > 0 && diff1.getDays() == 0) {
                usia = String.valueOf(diff1.getYears() * 12 * 30);
            } else {
                usia = String.valueOf(diff1.getYears() * 12 * 30 +
                        (diff1.getMonths() * 30) +
                        diff1.getDays());
            }
            tvUsia.setText(usia);
        }

        int user_id = Integer.parseInt(getId);
        reqDataPresenter.reqDataIoT(user_id);
        catatanPresenter.getCatatan(id);

        swipeRefreshCatatan.setOnRefreshListener(() -> catatanPresenter.getCatatan(id));
        swipeRefreshCatatan.setOnRefreshListener(() -> reqDataPresenter.reqDataIoT(user_id));

        recyclerViewCatatan = findViewById(R.id.recycler_view_catatan);
        recyclerViewCatatan.setLayoutManager(new LinearLayoutManager(this));

        btnLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerViewCatatan.setAdapter(iotDataAdapter);
            }
        });

        btnCatatan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (container_catatan.getVisibility() == View.GONE) {
                    container_catatan.setVisibility(View.VISIBLE);
                } else {
                    container_catatan.setVisibility(View.GONE);
                }
            }
        });

        btnSendCatatan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kirimCatatan();
            }
        });

        queue = Volley.newRequestQueue(this);
        btnSiram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.setMessage("Watering...");
                progressDialog.show();
                relayRequest(1);

//=============================================================================================================================
//                StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_READ,
//                        new Response.Listener<String>() {
//                            @Override
//                            public void onResponse(String response) {
//                                progressDialog.dismiss();
//
//                                try {
//                                    JSONObject jsonObject = new JSONObject(response);
//                                    String success = jsonObject.getString("success");
//
//                                    if (success.equals("true")) {
//                                        Toast.makeText(DetailTanamanActivity.this, "Watering Ok!", Toast.LENGTH_SHORT).show();
//                                    } else {
//                                        Toast.makeText(DetailTanamanActivity.this, "Error. Gagal"+ success, Toast.LENGTH_SHORT).show();
//                                    }
//
//                                } catch (JSONException e) {
//                                    e.printStackTrace();
//                                    progressDialog.dismiss();
//                                    Toast.makeText(DetailTanamanActivity.this, "Error." + e.toString(), Toast.LENGTH_SHORT).show();
//
//                                }
//                            }
//                        },
//                        new Response.ErrorListener() {
//                            @Override
//                            public void onErrorResponse(VolleyError error) {
//                                Toast.makeText(DetailTanamanActivity.this, "Error Connection DISINI: " + error.toString(), Toast.LENGTH_SHORT).show();
//
//                            }
//                        }) {
//                    @Override
//                    protected Map<String, String> getParams() throws AuthFailureError {
//                        Map<String, String> params = new HashMap<>();
//                        params.put("user_id", getId);
//                        params.put("relay", "1");
//
//                        return params;
//                    }
//                };
//                RequestQueue requestQueue = Volley.newRequestQueue(DetailTanamanActivity.this);
//                requestQueue.add(stringRequest);

//=============================================================================================================================
//                Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.bondanekanugraha28.PotKembang");
//                if (launchIntent != null) {
//                    startActivity(launchIntent);//cek null pointer
//                    Toast.makeText(DetailTanamanActivity.this, "Fitur ini perlu perangkat tambahan", Toast.LENGTH_SHORT).show();
//                }
            }
        });

        btnPupuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.setMessage("Ferilizing...");
                progressDialog.show();
                relayRequest(2);
            }
        });
        btnDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent toDetailTanaman = new Intent(DetailTanamanActivity.this, DetailRekomendasiTanamanActivity.class);
                toDetailTanaman.putExtra("id", id);
                toDetailTanaman.putExtra("nama", nama);
                toDetailTanaman.putExtra("nama_latin", nama_latin);
                toDetailTanaman.putExtra("deskripsi", deskripsi);
                toDetailTanaman.putExtra("jenis", jenis);
                toDetailTanaman.putExtra("ketinggian", ketinggian);
                toDetailTanaman.putExtra("tanah", tanah);
                toDetailTanaman.putExtra("suhu", suhu);
                toDetailTanaman.putExtra("ph", ph);
                toDetailTanaman.putExtra("kelembapan", kelembapan);
                toDetailTanaman.putExtra("tekanan", tekanan);
                toDetailTanaman.putExtra("lahan", lahan);
                toDetailTanaman.putExtra("air", air);
                toDetailTanaman.putExtra("gambar", gambar);
                startActivity(toDetailTanaman);
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void kirimCatatan() {
        tambahCatatanPresenter.postCatatan(id,
                getId,
                edtCatatan.getText().toString()
        );
    }

    public void relayRequest(int relay) {
        // prepare the Request
        String relayy = "&relay_a=0&relay_b=0";
        if (relay == 1) {
            relayy = "&relay_a=1&relay_b=0";
        } else if (relay == 2) {
            relayy = "&relay_a=0&relay_b=1";
        }

        String url_minta = URL_READ + "?user_id=" + getId + relayy;
        Toast.makeText(this, url_minta, Toast.LENGTH_SHORT).show();
        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url_minta, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(DetailTanamanActivity.this, response.toString(), Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(DetailTanamanActivity.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                }
        );

        // add it to the RequestQueue
        queue.add(getRequest);
        progressDialog.dismiss();

    }


    @Override
    public void showProgress() {
        swipeRefreshCatatan.setRefreshing(true);
    }

    @Override
    public void hideProgress() {
        swipeRefreshCatatan.setRefreshing(false);

    }

    @Override
    public void onRequestIotDataSuccess(List<IotData> iotData) {
        Toast.makeText(this, "Request OKE: " + iotData.size(), Toast.LENGTH_SHORT).show();
        iotDataAdapter = new IotDataAdapter(this, iotData);
        iotDataAdapter.notifyDataSetChanged();
        this.iotData = iotData;
        IotData iotDataa = iotData.get(0);
        tvCahaya.setText(iotDataa.getCahaya());
        tvSuhu.setText(iotDataa.getSuhu());
        tvLembabUdara.setText(iotDataa.getLembab_udara());
        tvLembabTanah.setText(iotDataa.getLembab_tanah());
    }

    @Override
    public void onRequestIotDataError(String message) {
        Toast.makeText(this, "Request ERROR" + message, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onRequestPostCatatanSuccess(String message) {
        edtCatatan.setText("");
        container_catatan.setVisibility(View.GONE);
    }

    @Override
    public void onRequestError(String message) {
        Toast.makeText(this, "Error: " + message, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void showCatatanProgress() {
        swipeRefreshCatatan.setRefreshing(true);
    }

    @Override
    public void hideCatatanProgress() {
        swipeRefreshCatatan.setRefreshing(false);
    }

    @Override
    public void onRequestCatatanSuccess(List<Catatan> catatan) {
        catatanAdapter = new CatatanAdapter(this, catatan);
        catatanAdapter.notifyDataSetChanged();
        recyclerViewCatatan.setAdapter(catatanAdapter);
        this.catatans = catatan;
    }

    @Override
    public void onRequestCatatanError(String message) {
        Toast.makeText(this, "Error" + message, Toast.LENGTH_SHORT).show();
    }
}
