package com.bintang.banyan.Activity.Main.TabMainFragment.Beranda;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bintang.banyan.Activity.Main.MainActivity;
import com.bintang.banyan.Model.Posting;
import com.bintang.banyan.R;

import java.util.List;

import static com.bintang.banyan.Activity.Main.MainActivity.berandaAdapter;

public class TabBerandaFragment extends Fragment implements BerandaView {

    public static RecyclerView berandaRecyclerView;
    public static SwipeRefreshLayout berandaSwipeRefresh;

    public static BerandaAdapter.ItemClickListener berandaItemClickListener;

    List<Posting> posts;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View BerandaView = inflater.inflate(R.layout.fragment_tab_beranda, container, false);

        // Inflate the layout for this fragment
        return BerandaView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }

    private void initView(View view) {
        berandaSwipeRefresh = view.findViewById(R.id.swipe_refresh);
        berandaRecyclerView = view.findViewById(R.id.recycler_view);
        berandaRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        MainActivity.berandaPresenter.getData();

        berandaSwipeRefresh.setOnRefreshListener(
                () -> MainActivity.berandaPresenter.getData());

    }

    private void changeFragment() {
//        getFragmentManager().beginTransaction().replace(R.id.tabHome, new TabHomePost()).addToBackStack(null).commit();
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
    public void onGetResult(List<Posting> posts) {
        berandaAdapter = new BerandaAdapter(getActivity(), posts, berandaItemClickListener);
        berandaAdapter.notifyDataSetChanged();
        berandaRecyclerView.setAdapter(berandaAdapter);
        this.posts = posts;
    }

    @Override
    public void onErrorLoading(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }
}
