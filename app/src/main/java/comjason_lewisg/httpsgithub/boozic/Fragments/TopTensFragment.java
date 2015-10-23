package comjason_lewisg.httpsgithub.boozic.Fragments;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import comjason_lewisg.httpsgithub.boozic.Handlers.AdapterHandler;
import comjason_lewisg.httpsgithub.boozic.MainActivity;
import comjason_lewisg.httpsgithub.boozic.Models.TopTensModel;
import comjason_lewisg.httpsgithub.boozic.R;

public class TopTensFragment extends Fragment {
    private View rootView;
    private FragmentManager manager;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    int colorPrimary;
    int colorPrimaryDark;
    int colorAccent;
    int colorAccentDark;

    OnPass dataPasser;

    private List<TopTensModel> DataSet = new ArrayList<TopTensModel>() {
        {
            //this is where we call backend connector
            //to populate Arraylist
            add(new TopTensModel("Skyy Vodka", "12/02/14", 3.5, "ABC liquor","Publix liquor",  1.3, 1.80, 18.73,
                    17.00, 3, true, "fifth",  40, 80, new int[] {251, 196, 75, 49, 21}));
            add(new TopTensModel("Miller Light", "06/11/15", 3.0, "Publix liquor", 1.8, 7.23, 1, true, "(6) bottle", 4.17, 8, new int[] {129, 62, 514, 486, 69}));
            add(new TopTensModel("Bud Light", "04/25/15", 4.0, "ABC liquor", "Walmart", 1.3, 2.34, 8.02,
                    6.73, 1, true, "(6) bottle",  4.20, 8, new int[] {251, 496, 765, 49, 128}));
            add(new TopTensModel("Moscato", "02/09/15", 3.5,"Publix liquor", 1.8, 11.46, 2, true, "fifth", 5.5, 11, new int[] {265, 468, 96, 135, 26}));
            add(new TopTensModel("Fireball Whiskey", "02/26/15", 3.5, "ABC liquor", "Publix liquor", 1.3, 1.8, 16.02,
                    15.56, 3, true, "handle",  33, 66, new int[] {361, 496, 210, 46, 12}));
            add(new TopTensModel("Wyborowa Wodka Rye Grain Polish Vodka", "07/25/15", 4.0, "ABC liquor", "Publix liquor", 1.3, 1.8, 23.61,
                    21.49, 3, true, "fifth",  40, 80, new int[] {465, 129, 76, 32, 15}));
            add(new TopTensModel("Henninger", "08/11/15", 3.5, "Publix liquor", 1.8, 8.26, 1, true, "(6) bottle", 4.80, 10, new int[] {162, 168, 35, 12, 1}));
            add(new TopTensModel("Domaine Gavoty Provence Rose", "02/26/15", 3.5, "ABC liquor", "Publix liquor", 1.3, 1.8, 29.99,
                    28.36, 2, true, "fifth",  13, 26, new int[] {612, 345, 61, 124, 10}));
            add(new TopTensModel("Ciroc French Pineapple Vodka", "10/11/15", 3.5, "Publix liquor", 1.8, 22.99, 3, true, "fifth", 35, 70, new int[] {236, 61, 96, 23, 9}));
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        rootView =  inflater.inflate(R.layout.fragment_toptens,container,false);
        viewSet(rootView);

        return rootView;
    }

    private void viewSet(View rootView) {
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.topTen_rv);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mRecyclerView.setHasFixedSize(true);

        mAdapter = new AdapterHandler(DataSet, (MainActivity) getActivity());
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView mRecyclerView, int dx, int dy) {
                super.onScrolled(mRecyclerView, dx, dy);
                if (Math.abs(dy) > 20) {
                    if (dy > 0) {
                        ((MainActivity) getActivity()).FAB.menuButton.hide(true);
                        if (isMenuOpened())
                            closeFilterbuttons();
                    }
                    else {
                        ((MainActivity) getActivity()).FAB.menuButton.show(true);
                        if (isMenuOpened())
                            closeFilterbuttons();
                    }
                }
            }
        });

        final SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setColorSchemeColors(rootView.getResources().getColor(R.color.ColorAccent),rootView.getResources().getColor(R.color.ColorAccent2),
                rootView.getResources().getColor(R.color.ColorAccent3), rootView.getResources().getColor(R.color.ColorAccent4), rootView.getResources().getColor(R.color.ColorAccent5));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        askShowGPS();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 1250);
            }
        });
    }

    public interface OnPass {
        int AskForColorPrimary();
        int AskForColorPrimaryDark();
        int AskForColorAccent();
        int AskForColorAccentDark();
        void AskToHideFilterButtons();
        void AskToShowFilterButtons();
        void CloseAllFilterButtons();
        void AskToShowGPS();
        boolean IsMenuOpened();
    }

    public int askColorPrimary() { return dataPasser.AskForColorPrimary(); }

    public int askColorPrimaryDark() { return dataPasser.AskForColorPrimaryDark(); }

    public int askColorAccent() { return dataPasser.AskForColorAccent(); }

    public int askColorAccentDark() { return dataPasser.AskForColorAccentDark(); }

    public void closeFilterbuttons() { dataPasser.CloseAllFilterButtons(); }

    public void askHideFilterButtons() { dataPasser.AskToHideFilterButtons(); }

    public void askShowFilterButtons() { dataPasser.AskToShowFilterButtons(); }

    public void askShowGPS() { dataPasser.AskToShowGPS(); }

    public boolean isMenuOpened() { return dataPasser.IsMenuOpened(); }

    @Override
    public void onAttach(Activity a) {
        super.onAttach(a);
        dataPasser = (OnPass) a;

        askShowFilterButtons();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        askHideFilterButtons();
    }

    @Override
    public void onStart() {
        super.onStart();
        manager = getActivity().getSupportFragmentManager();
        manager.popBackStack();
    }

    @Override
    public void onStop(){
        super.onStop();
    }

    @Override
    public void onResume(){
        super.onResume();
    }
}
