package comjason_lewisg.httpsgithub.boozic.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Fade;
import android.transition.Slide;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import comjason_lewisg.httpsgithub.boozic.Handlers.AdapterHandler;
import comjason_lewisg.httpsgithub.boozic.Models.TopTensModel;
import comjason_lewisg.httpsgithub.boozic.R;

public class TopTensFragment extends Fragment{
    private View rootView;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private List<TopTensModel> DataSet = new ArrayList<TopTensModel>() {
        {
            add(new TopTensModel(3, "Sky Vodka", "ABC liquor", "1.75L", 1.3, BigDecimal.valueOf(18.73), true));
            add(new TopTensModel(1, "Miller Light", "Publix liquor", "6*355ml", 1.8, BigDecimal.valueOf(7.23), true));
            add(new TopTensModel(1, "Bud Light", "Publix liquor", "6*355ml", 1.8, BigDecimal.valueOf(8.02), true));
            add(new TopTensModel(2, "Moscato", "ABC liquor", "750ml", 1.3, BigDecimal.valueOf(11.46), true));
            add(new TopTensModel(3, "Fireball Whiskey", "ADC Liquor", "750ml", 1.3, BigDecimal.valueOf(12.95), true));
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

        mAdapter = new AdapterHandler(DataSet);
        mRecyclerView.setAdapter(mAdapter);

    }

    @Override
    public void onStart(){
        super.onStart();
        FragmentManager manager = getActivity().getSupportFragmentManager();
        manager.popBackStack();
    }

}
