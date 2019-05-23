package com.bintang.banyan;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    private static String URL_REGIST = "https://bonbon28.000webhostapp.com/banyan/register.php";
    private EditText edtNama, edtEmail, edtPassword, edtCPassword;
    private Button btnRegist;
    private ProgressBar progressBarLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        progressBarLoading = findViewById(R.id.loading);
        edtNama = findViewById(R.id.edt_register_name);
        edtEmail = findViewById(R.id.edt_register_email);
        edtPassword = findViewById(R.id.edt_register_password);
        edtCPassword = findViewById(R.id.edt_register_password_confirm);
        btnRegist = findViewById(R.id.btn_register);

        btnRegist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edtNama.getText().toString().isEmpty()) {
                    edtNama.requestFocus();
                    edtNama.setError("Masukkan Nama!");
                } else if (edtEmail.getText().toString().isEmpty()) {
                    edtEmail.requestFocus();
                    edtEmail.setError("Masukkan Email!");
                } else if (edtPassword.getText().toString().isEmpty()) {
                    edtPassword.requestFocus();
                    edtPassword.setError("Masukkan Password!");
                } else if (edtCPassword.getText().toString().isEmpty()) {
                    edtCPassword.requestFocus();
                    edtCPassword.setError("Konfirmasi Password!");
                } else if (!edtPassword.getText().toString().equals(edtCPassword.getText().toString())) {
                    edtCPassword.requestFocus();
                    edtCPassword.setError("Password tidak sama!");
                } else {
                    Regist();
                }

            }
        });
    }

    private void Regist() {
        progressBarLoading.setVisibility(View.VISIBLE);
        btnRegist.setVisibility(View.GONE);

        final String nama = this.edtNama.getText().toString().trim();
        final String email = this.edtEmail.getText().toString().trim();
        final String password = this.edtPassword.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_REGIST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");

                            if (success.equals("1")) {
                                showButton();
                                Toast.makeText(RegisterActivity.this, "Register Success!", Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                showButton();
                                Toast.makeText(RegisterActivity.this, "Register Error! ", Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(RegisterActivity.this, "Register Error! " + e.toString(), Toast.LENGTH_SHORT).show();
                            showButton();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(RegisterActivity.this, "Register Error! " + error.toString(), Toast.LENGTH_SHORT).show();
                        showButton();

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("name", nama);
                params.put("email", email);
                params.put("password", password);

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    public void showButton() {
        progressBarLoading.setVisibility(View.GONE);
        btnRegist.setVisibility(View.VISIBLE);

    }
}
