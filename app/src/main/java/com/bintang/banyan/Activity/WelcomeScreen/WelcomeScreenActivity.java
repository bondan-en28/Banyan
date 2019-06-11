package com.bintang.banyan.Activity.WelcomeScreen;

import android.animation.ArgbEvaluator;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.bintang.banyan.Activity.LoginActivity;
import com.bintang.banyan.Model.FiturWelcomeScreen;
import com.bintang.banyan.R;

import java.util.ArrayList;
import java.util.List;

public class WelcomeScreenActivity extends AppCompatActivity {

    ViewPager viewPager;
    WelcomeScreenAdapter adapter;
    List<FiturWelcomeScreen> fitur;
    Integer[] colors = null;
    ArgbEvaluator argbEvaluator = new ArgbEvaluator();
    Button btn_ok;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);

        fitur = new ArrayList<>();
        fitur.add(new FiturWelcomeScreen(R.drawable.fitur_1, "Judu Pertama", "Deskripsi Pertama"));
        fitur.add(new FiturWelcomeScreen(R.drawable.fitur_2, "Judu Kedua", "Deskripsi Kedua"));
        fitur.add(new FiturWelcomeScreen(R.drawable.fitur_3, "Judu Ketiga", "Deskripsi Ketiga"));
        fitur.add(new FiturWelcomeScreen(R.drawable.fitur_4, "Judu Terakhir", "Deskripsi Terakhir"));

        adapter = new WelcomeScreenAdapter(fitur, this);

        viewPager = findViewById(R.id.viewPagerWelcome);
        viewPager.setAdapter(adapter);
        viewPager.setClipToPadding(false);
        viewPager.setPadding(130, 300, 130, 400);
        viewPager.setClipToPadding(false);

        Integer[] colors_temp = {
                getResources().getColor(R.color.color1),
                getResources().getColor(R.color.color2),
                getResources().getColor(R.color.color3),
                getResources().getColor(R.color.color4),
        };

        btn_ok = findViewById(R.id.btn_welcome);

        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(WelcomeScreenActivity.this, LoginActivity.class));
                finish();
            }
        });

        colors = colors_temp;
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (position < (adapter.getCount() - 1) && position < (colors.length - 1)) {
                    viewPager.setBackgroundColor(
                            (Integer) argbEvaluator.evaluate(
                                    positionOffset,
                                    colors[position],
                                    colors[position + 1]
                            )
                    );
                } else {
                    viewPager.setBackgroundColor(colors[colors.length - 1]);
                }
                if (position != 3) {
                    btn_ok.setVisibility(View.GONE);
                } else {
                    btn_ok.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onPageSelected(int i) {

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }
}
