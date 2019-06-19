package com.bintang.banyan.Activity.Main.TabMainFragment.Kebun;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bintang.banyan.R;

public class TabKebunFragment extends Fragment implements View.OnClickListener {

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
//        ImageView imagePost = view.findViewById(R.id.imagePost);
    }

    private void changeFragment() {
//        getFragmentManager().beginTransaction().replace(R.id.tabHome, new TabHomePost()).addToBackStack(null).commit();
    }

    @Override
    public void onClick(View view) {
/*        switch (view.getId()) {
            case R.id.imagePost:
                changeFragment();
                break;
        }
*/
    }

}