package com.bintang.banyan.Activity.WelcomeScreen;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bintang.banyan.Model.FiturWelcomeScreen;
import com.bintang.banyan.R;

import java.util.List;

public class WelcomeScreenAdapter extends PagerAdapter {

    private List<FiturWelcomeScreen> models;
    private LayoutInflater layoutInflater;
    private Context context;

    public WelcomeScreenAdapter(List<FiturWelcomeScreen> models, Context context) {
        this.models = models;
        this.context = context;
    }

    @Override
    public int getCount() {
        return models.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view.equals(o);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.item_welcome, container, false);

        ImageView imageView;
        TextView title, desc;

        imageView = view.findViewById(R.id.image_welcome);
        title = view.findViewById(R.id.title_welcome);
        desc = view.findViewById(R.id.desc_welcome);

        imageView.setImageResource(models.get(position).getImage());
        title.setText(models.get(position).getTitle());
        desc.setText(models.get(position).getDesc());

        container.addView(view, 0);
        return view;

    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
