package com.bintang.banyan.Activity.Main;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bintang.banyan.Activity.AboutActivity;
import com.bintang.banyan.Activity.AddPost.AddPostActivity;
import com.bintang.banyan.Activity.DetailPost.DetailPostActivity;
import com.bintang.banyan.Activity.Main.TabMainFragment.Beranda.BerandaAdapter;
import com.bintang.banyan.Activity.Main.TabMainFragment.Beranda.BerandaPresenter;
import com.bintang.banyan.Activity.Main.TabMainFragment.Beranda.BerandaView;
import com.bintang.banyan.Activity.Main.TabMainFragment.Beranda.TabBerandaFragment;
import com.bintang.banyan.Activity.Main.TabMainFragment.Kebun.TabKebunFragment;
import com.bintang.banyan.Activity.Main.TabMainFragment.Profile.ProfileAdapter;
import com.bintang.banyan.Activity.Main.TabMainFragment.Profile.ProfilePresenter;
import com.bintang.banyan.Activity.Main.TabMainFragment.Profile.ProfileView;
import com.bintang.banyan.Activity.Main.TabMainFragment.Profile.TabProfileFragment;
import com.bintang.banyan.Model.MyPosting;
import com.bintang.banyan.Model.Posting;
import com.bintang.banyan.R;
import com.bintang.banyan.SessionManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.bintang.banyan.Activity.Main.TabMainFragment.Beranda.TabBerandaFragment.berandaRecyclerView;
import static com.bintang.banyan.Activity.Main.TabMainFragment.Beranda.TabBerandaFragment.berandaSwipeRefresh;
import static com.bintang.banyan.Activity.Main.TabMainFragment.Profile.TabProfileFragment.btnGantiFoto;
import static com.bintang.banyan.Activity.Main.TabMainFragment.Profile.TabProfileFragment.edtAlamat;
import static com.bintang.banyan.Activity.Main.TabMainFragment.Profile.TabProfileFragment.edtEmail;
import static com.bintang.banyan.Activity.Main.TabMainFragment.Profile.TabProfileFragment.edtNama;
import static com.bintang.banyan.Activity.Main.TabMainFragment.Profile.TabProfileFragment.edtNoTelp;
import static com.bintang.banyan.Activity.Main.TabMainFragment.Profile.TabProfileFragment.edtTtl;
import static com.bintang.banyan.Activity.Main.TabMainFragment.Profile.TabProfileFragment.imageprofilbitmap;
import static com.bintang.banyan.Activity.Main.TabMainFragment.Profile.TabProfileFragment.profileRecyclerView;

public class MainActivity extends AppCompatActivity implements BerandaView, ProfileView {

    private static final String TAG = MainActivity.class.getSimpleName(); //get info
    public static String name, email, photo, ttl, alamat, notelp, getId;
    public static Toolbar toolbar;
    public static BerandaPresenter berandaPresenter;
    public static BerandaAdapter berandaAdapter;
    public static ProfilePresenter profilePresenter;
    public static ProfileAdapter profileAdapter;
    public static int jumlah_tanaman = 0;
    private static String URL_READ = "https://bonbon28.000webhostapp.com/banyan/read_detail.php";
    private static String URL_EDIT = "https://bonbon28.000webhostapp.com/banyan/edit_detail.php";
    private static String URL_UPLOAD = "https://bonbon28.000webhostapp.com/banyan/upload.php";
    public int currrentFragment = 1;
    SessionManager sessionManager;
    Menu toolbarMenu;
    MenuItem menu_add, menu_settings, menu_done, menu_about, menu_share, menu_logout;
    boolean edit = false;
    BerandaAdapter.ItemClickListener berandaItemClickListener;
    ProfileAdapter.ItemClickListener profileItemClickListener;

    List<Posting> posts;
    List<MyPosting> myPostings;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @SuppressLint("ResourceAsColor")
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment = null;
            toolbar.setBackgroundColor(getResources().getColor(R.color.putih));
            toolbar.setTitleTextColor(getResources().getColor(R.color.hitam));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                toolbar.setElevation(14);
            }

            switch (item.getItemId()) {
                case R.id.navigation_beranda:
                    currrentFragment = 1;
                    fragment = new TabBerandaFragment();
                    edit = false;
                    toolbar.setTitle(R.string.app_name);
                    break;
                case R.id.navigation_kebun:
                    edit = false;
                    currrentFragment = 2;
                    toolbar.setTitle("Garden");
                    toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                    toolbar.setTitleTextColor(getResources().getColor(R.color.putih));

                    fragment = new TabKebunFragment();
                    break;
                case R.id.navigation_profile:
                    currrentFragment = 3;
                    toolbar.setTitle("Me");
                    toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    toolbar.setTitleTextColor(getResources().getColor(R.color.putih));
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        toolbar.setElevation(0);
                    }

                    fragment = new TabProfileFragment();
                    break;
            }
            return loadFragment(fragment);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitleMarginStart(50);
        toolbar.setOverflowIcon(getResources().getDrawable(R.drawable.ic_more_vert_white_24dp));
        setSupportActionBar(toolbar);

        sessionManager = new SessionManager(this);
        sessionManager.checkLogin();

        HashMap<String, String> user = sessionManager.getUserDetail();

        getId = user.get(SessionManager.ID);

        loadFragment(new TabBerandaFragment());
        if (sessionManager.isLogin()) {
            getUserDetail();
        }
        berandaPresenter = new BerandaPresenter(this);
        profilePresenter = new ProfilePresenter(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel =
                    new NotificationChannel("Banyan Notification", "Banyan Notification", NotificationManager.IMPORTANCE_DEFAULT);

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "getInstanceId failed", task.getException());
                            return;
                        }

                        Toast.makeText(MainActivity.this, "Successfull", Toast.LENGTH_SHORT).show();
                    }
                });

        berandaItemClickListener = ((view, position) ->
        {
            int id = posts.get(position).getId();
            String user_id = posts.get(position).getUser_id();
            String judul = posts.get(position).getJudul();
            String deskripsi = posts.get(position).getDeskripsi();
            String gambar = posts.get(position).getGambar();
            String tanggal = posts.get(position).getTanggal();
            String user_image = posts.get(position).getUserImage();

            Intent intent = new Intent(this, DetailPostActivity.class);
            intent.putExtra("id", id);
            intent.putExtra("user_id", user_id);
            intent.putExtra("judul", judul);
            intent.putExtra("deskripsi", deskripsi);
            intent.putExtra("gambar", gambar);
            intent.putExtra("tanggal", tanggal);
            intent.putExtra("user_image", user_image);
            startActivity(intent);
        });

        profileItemClickListener = ((view, position) ->
        {
            int id = myPostings.get(position).getId();
            String user_id = myPostings.get(position).getUser_id();
            String judul = myPostings.get(position).getJudul();
            String deskripsi = myPostings.get(position).getDeskripsi();
            String gambar = myPostings.get(position).getGambar();
            String tanggal = myPostings.get(position).getTanggal();
            String user_image = myPostings.get(position).getUserImage();

            Intent intent = new Intent(this, DetailPostActivity.class);
            intent.putExtra("id", id);
            intent.putExtra("user_id", user_id);
            intent.putExtra("judul", judul);
            intent.putExtra("deskripsi", deskripsi);
            intent.putExtra("gambar", gambar);
            intent.putExtra("tanggal", tanggal);
            intent.putExtra("user_image", user_image);
            startActivity(intent);
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_main_menu, menu);

        toolbarMenu = menu;

        menu_add = toolbarMenu.findItem(R.id.menu_add);
        menu_settings = toolbarMenu.findItem(R.id.menu_settings);
        menu_done = toolbarMenu.findItem(R.id.menu_done);
        menu_about = toolbarMenu.findItem(R.id.menu_about);
        menu_share = toolbarMenu.findItem(R.id.menu_share);
        menu_logout = toolbarMenu.findItem(R.id.menu_exit);

        invalidateOptionsMenu();
        if (currrentFragment == 1) {
            menu_add.setVisible(true);
            menu_settings.setVisible(false);
            menu_done.setVisible(false);
            menu_about.setVisible(false);
            menu_share.setVisible(false);
            menu_logout.setVisible(false);
        } else if (currrentFragment == 2) {
            menu_add.setVisible(false);
            menu_settings.setVisible(false);
            menu_done.setVisible(false);
            menu_about.setVisible(false);
            menu_share.setVisible(false);
            menu_logout.setVisible(false);
        } else {
            menu_add.setVisible(false);
            if (!edit) {
                menu_settings.setVisible(true);
                menu_done.setVisible(false);
            } else {
                menu_settings.setVisible(false);
                menu_done.setVisible(true);
            }
            menu_about.setVisible(true);
            menu_share.setVisible(true);
            menu_logout.setVisible(true);
        }

        return true;
    }

    private void getUserDetail() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_READ,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        Log.i(TAG, response);

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("read");

                            if (success.equals("1")) {
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject object = jsonArray.getJSONObject(i);

                                    name = object.getString("name").trim();
                                    email = object.getString("email").trim();
                                    photo = object.getString("photo").trim();
                                    ttl = object.getString("ttl").trim();
                                    alamat = object.getString("alamat").trim();
                                    notelp = object.getString("notelp").trim();

                                }
                            } else {
                                Toast.makeText(MainActivity.this, "Error: Gagal mendapatkan detail", Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressDialog.dismiss();
                            Toast.makeText(MainActivity.this, "Error: " + e.toString(), Toast.LENGTH_SHORT).show();

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, "Error Connection DISINI: " + error.toString(), Toast.LENGTH_SHORT).show();

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id", getId);

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menu_add) {
            Intent intent = new Intent(MainActivity.this, AddPostActivity.class);
            intent.putExtra("id", getId);
            startActivity(intent);
        } else if (id == R.id.menu_settings) {
            initContent(true);
            toolbarMenu.findItem(R.id.menu_settings).setVisible(false);
            toolbarMenu.findItem(R.id.menu_done).setVisible(true);

        } else if (id == R.id.menu_done) {
            initContent(false);
            toolbarMenu.findItem(R.id.menu_settings).setVisible(true);
            toolbarMenu.findItem(R.id.menu_done).setVisible(false);
            saveEdit();

        } else if (id == R.id.menu_share) {
            Toast.makeText(this, "Share", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.menu_about) {
            startActivity(new Intent(MainActivity.this, AboutActivity.class));
        } else if (id == R.id.menu_exit) {
            sessionManager.logout();
        }

        return true;
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
        edit = kondisi;

    }

    public boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    private void uploadFoto(final String id, final String foto) {

        final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
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
                                Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(MainActivity.this, "Coba Lagi.", Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(MainActivity.this, "Error: " + e.toString(), Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, "Error: " + error.toString(), Toast.LENGTH_SHORT).show();
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

        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        requestQueue.add(stringRequest);


    }

    private String getStringImage(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);

        byte[] imageByteArray = byteArrayOutputStream.toByteArray();
        String encodedImage = Base64.encodeToString(imageByteArray, Base64.DEFAULT);
        return encodedImage;
    }

    public void saveEdit() {

        try {
            uploadFoto(getId, getStringImage(imageprofilbitmap));

        } catch (Exception e) {
            e.printStackTrace();
        }

        final String name = edtNama.getText().toString().trim();
        final String email = edtEmail.getText().toString().trim();
        final String ttl = edtTtl.getText().toString().trim();
        final String alamat = edtAlamat.getText().toString().trim();
        final String notelp = edtNoTelp.getText().toString().trim();
        final String id = getId;

        final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
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
                                Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            } else {
                                Toast.makeText(MainActivity.this, "Error.", Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(MainActivity.this, "Error: " + e.toString(), Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, "Error Connection." + error.toString(), Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("name", name);
                params.put("email", email);
                params.put("ttl", ttl);
                params.put("alamat", alamat);
                params.put("notelp", notelp);
                params.put("id", id);

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        requestQueue.add(stringRequest);

        this.sessionManager.clearSession();
        this.sessionManager.createSession(name, email, ttl, alamat, notelp, id);
        this.sessionManager.getUserDetail();

    }

    @Override
    public void showLoading() {
        berandaSwipeRefresh.setRefreshing(true);
    }

    @Override
    public void hideLoading() {
        berandaSwipeRefresh.setRefreshing(false);
    }

    @Override
    public void onGetMyPostResult(List<MyPosting> postings) {
        profileAdapter = new ProfileAdapter(this, postings, profileItemClickListener);
        profileAdapter.notifyDataSetChanged();
        profileRecyclerView.setAdapter(profileAdapter);
        this.myPostings = postings;

    }

    @Override
    public void onGetResult(List<Posting> posts) {
        berandaAdapter = new BerandaAdapter(this, posts, berandaItemClickListener);
        berandaAdapter.notifyDataSetChanged();
        berandaRecyclerView.setAdapter(berandaAdapter);
        this.posts = posts;
    }

    @Override
    public void onErrorLoading(String message) {
        Toast.makeText(this, "Error Loading" + message, Toast.LENGTH_SHORT).show();
    }
}
