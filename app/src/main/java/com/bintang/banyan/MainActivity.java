package com.bintang.banyan;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
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
import com.bintang.banyan.TabMainFragment.TabBerandaFragment;
import com.bintang.banyan.TabMainFragment.TabKebunFragment;
import com.bintang.banyan.TabMainFragment.TabProfileFragment;
import com.bintang.banyan.TabMainFragment.TabSocialFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

import static com.bintang.banyan.TabMainFragment.TabProfileFragment.btnGantiFoto;
import static com.bintang.banyan.TabMainFragment.TabProfileFragment.edtAlamat;
import static com.bintang.banyan.TabMainFragment.TabProfileFragment.edtEmail;
import static com.bintang.banyan.TabMainFragment.TabProfileFragment.edtNama;
import static com.bintang.banyan.TabMainFragment.TabProfileFragment.edtNoTelp;
import static com.bintang.banyan.TabMainFragment.TabProfileFragment.edtTtl;
import static com.bintang.banyan.TabMainFragment.TabProfileFragment.imageprofilbitmap;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName(); //get info
    public static String name, email, photo, getId;

    private static String URL_READ = "https://bonbon28.000webhostapp.com/banyan/read_detail.php";
    private static String URL_EDIT = "https://bonbon28.000webhostapp.com/banyan/edit_detail.php";
    private static String URL_UPLOAD = "https://bonbon28.000webhostapp.com/banyan/upload.php";
    /*
    private static String URL_READ = "http://10.1.2.46/banyan/read_detail.php";
    private static String URL_EDIT = "http://10.1.2.46/banyan/edit_detail.php";
    private static String URL_UPLOAD = "http://10.1.2.46/banyan/upload.php";
    */

    public int currrentFragment = 1;
    SessionManager sessionManager;
    Toolbar toolbar;
    Menu toolbarMenu;
    MenuItem menu_add, menu_settings, menu_done, menu_about, menu_share, menu_logout;
    boolean edit = false;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment = null;

            switch (item.getItemId()) {
                case R.id.navigation_beranda:
                    currrentFragment = 1;
                    fragment = new TabBerandaFragment();
                    edit = false;
                    toolbar.setTitle("Banyan");
                    break;
                case R.id.navigation_social:
                    currrentFragment = 2;
                    edit = false;
                    toolbar.setTitle("Feed");
                    fragment = new TabSocialFragment();
                    break;
                case R.id.navigation_kebun:
                    edit = false;
                    currrentFragment = 3;
                    toolbar.setTitle("Progress");
                    fragment = new TabKebunFragment();
                    break;
                case R.id.navigation_profile:
                    currrentFragment = 4;
                    toolbar.setTitle("Me");
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
        setSupportActionBar(toolbar);

        sessionManager = new SessionManager(this);
        sessionManager.checkLogin();

        HashMap<String, String> user = sessionManager.getUserDetail();

        getId = user.get(SessionManager.ID);

        loadFragment(new TabBerandaFragment());
        getUserDetail();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);

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
        } else if (currrentFragment == 2 || currrentFragment == 3) {
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

                                }
                            } else {
                                Toast.makeText(MainActivity.this, "Error Login.", Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressDialog.dismiss();
                            Toast.makeText(MainActivity.this, "Error Login." + e.toString(), Toast.LENGTH_SHORT).show();

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, "Error Login." + error.toString(), Toast.LENGTH_SHORT).show();

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
    protected void onResume() {
        getUserDetail();
        super.onResume();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menu_add) {
            Toast.makeText(this, "Search", Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(MainActivity.this, "Error Login." + e.toString(), Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, "Error Login." + error.toString(), Toast.LENGTH_SHORT).show();
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
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        requestQueue.add(stringRequest);
    }

}
