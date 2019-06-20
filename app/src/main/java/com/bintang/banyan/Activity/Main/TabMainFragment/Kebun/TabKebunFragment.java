package com.bintang.banyan.Activity.Main.TabMainFragment.Kebun;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.bintang.banyan.Activity.Main.MainActivity;
import com.bintang.banyan.R;

public class TabKebunFragment extends Fragment {

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
        Button btnTanam = view.findViewById(R.id.btn_tanam);

        btnTanam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeFragment();
            }
        });
    }

    private void changeFragment() {
        MainActivity.toolbar.setTitle("Rekomendasi");
        getFragmentManager().beginTransaction().replace(R.id.fragment_container, new TambahTanamanFragment()).addToBackStack(null).commit();
    }

}