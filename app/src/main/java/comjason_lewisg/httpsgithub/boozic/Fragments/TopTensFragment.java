package comjason_lewisg.httpsgithub.boozic.Fragments;

import android.app.Activity;
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
import android.widget.LinearLayout;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import comjason_lewisg.httpsgithub.boozic.Handlers.AdapterHandler;
import comjason_lewisg.httpsgithub.boozic.Handlers.FilterActionButtonHandler;
import comjason_lewisg.httpsgithub.boozic.MainActivity;
import comjason_lewisg.httpsgithub.boozic.Models.TopTensModel;
import comjason_lewisg.httpsgithub.boozic.R;

public class TopTensFragment extends Fragment {
    private View rootView;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private FilterActionButtonHandler FBhandle;

    int colorPrimary;
    int colorPrimaryDark;
    int colorAccent;
    int colorAccentDark;

    OnColorPass dataPasser;

    private List<TopTensModel> DataSet = new ArrayList<TopTensModel>() {
        {
            //this is where we call backend connector
            //to populate Arraylist
            add(new TopTensModel(3, "Sky Vodka", "ABC liquor", "1.75L", 1.3, BigDecimal.valueOf(18.73), true));
            add(new TopTensModel(1, "Miller Light", "Publix liquor", "2.13L", 1.8, BigDecimal.valueOf(7.23), true));
            add(new TopTensModel(1, "Bud Light", "Publix liquor", "2.13L", 1.8, BigDecimal.valueOf(8.02), true));
            add(new TopTensModel(2, "Moscato", "ABC liquor", "0.75L", 1.3, BigDecimal.valueOf(11.46), true));
            add(new TopTensModel(3, "Fireball Whiskey", "ADC Liquor", "0.75L", 1.3, BigDecimal.valueOf(12.95), true));
            add(new TopTensModel(3, "Wyborowa Wodka Rye Grain Polish Vodka", "ABC liquor", "1.75L", 1.3, BigDecimal.valueOf(26.99), true));
            add(new TopTensModel(1, "Henninger", "ABC liquor", "0.47L", 1.8, BigDecimal.valueOf(1.59), true));
            add(new TopTensModel(1, "Erie Soleil Shandy ", "ABC liquor", "0.35L", 1.8, BigDecimal.valueOf(2.29), true));
            add(new TopTensModel(2, "Domaine Gavoty Provence Rose", "ABC liquor", "0.75L", 1.3, BigDecimal.valueOf(29.99), true));
            add(new TopTensModel(3, "Ciroc French Pineapple Vodka", "ADC Liquor", "1.75L", 1.3, BigDecimal.valueOf(27.99), true));
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

        mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView mRecyclerView, int dx, int dy) {
                super.onScrolled(mRecyclerView, dx, dy);
                if (Math.abs(dy) > 20) {
                    if (dy > 0)
                        ((MainActivity) getActivity()).FAB.menuButton.hide(true);
                    else
                        ((MainActivity) getActivity()).FAB.menuButton.show(true);
                }
            }
        });

        FBhandle = new FilterActionButtonHandler();
        FBhandle.setActivity(rootView);
    }

    public interface OnColorPass {
        int AskForColorPrimary();
        int AskForColorPrimaryDark();
        int AskForColorAccent();
        int AskForColorAccentDark();

    }

    public int askColorPrimary() { return dataPasser.AskForColorPrimary(); }

    public int askColorPrimaryDark() { return dataPasser.AskForColorPrimaryDark(); }

    public int askColorAccent() { return dataPasser.AskForColorAccent(); }

    public int askColorAccentDark() { return dataPasser.AskForColorAccentDark(); }

    @Override
    public void onAttach(Activity a) {
        super.onAttach(a);
        dataPasser = (OnColorPass) a;
    }

    @Override
    public void onStart(){
        super.onStart();
        FragmentManager manager = getActivity().getSupportFragmentManager();
        manager.popBackStack();
    }

    @Override
    public void onStop(){
        super.onStop();
        ((MainActivity)getActivity()).changeToolBarElev(12);
    }

    @Override
    public void onResume(){
        super.onResume();
        ((MainActivity)getActivity()).changeToolBarElev(0);

        colorPrimary = askColorPrimary();
        colorPrimaryDark = askColorPrimaryDark();
        colorAccent = askColorAccent();
        colorAccentDark = askColorAccentDark();

        FBhandle.setColor(colorAccent, colorPrimaryDark);

        LinearLayout view = (LinearLayout)getActivity().findViewById(R.id.filterback);
        view.setBackgroundColor(colorPrimary);
        FloatingActionMenu fab;
        fab = (FloatingActionMenu)getActivity().findViewById(R.id.fabtype);
        fab.setMenuButtonColorNormal(colorPrimary);
        fab.setMenuButtonColorPressed(colorPrimaryDark);

        fab = (FloatingActionMenu)getActivity().findViewById(R.id.fabdist);
        fab.setMenuButtonColorNormal(colorPrimary);
        fab.setMenuButtonColorPressed(colorPrimaryDark);

        fab = (FloatingActionMenu)getActivity().findViewById(R.id.fabprice);
        fab.setMenuButtonColorNormal(colorPrimary);
        fab.setMenuButtonColorPressed(colorPrimaryDark);

        fab = (FloatingActionMenu)getActivity().findViewById(R.id.fabcontent);
        fab.setMenuButtonColorNormal(colorPrimary);
        fab.setMenuButtonColorPressed(colorPrimaryDark);

        FloatingActionButton fabb;
        fabb = (FloatingActionButton)getActivity().findViewById(R.id.wines);
        fabb.setColorNormal(colorAccent);
        fabb.setColorPressed(colorAccentDark);
        fabb = (FloatingActionButton)getActivity().findViewById(R.id.beers);
        fabb.setColorNormal(colorAccent);
        fabb.setColorPressed(colorAccentDark);
        fabb = (FloatingActionButton)getActivity().findViewById(R.id.liquors);
        fabb.setColorNormal(colorAccent);
        fabb.setColorPressed(colorAccentDark);

        fabb = (FloatingActionButton)getActivity().findViewById(R.id.twomi);
        fabb.setColorNormal(colorAccent);
        fabb.setColorPressed(colorAccentDark);
        fabb = (FloatingActionButton)getActivity().findViewById(R.id.fivemi);
        fabb.setColorNormal(colorAccent);
        fabb.setColorPressed(colorAccentDark);
        fabb = (FloatingActionButton)getActivity().findViewById(R.id.tenmi);
        fabb.setColorNormal(colorAccent);
        fabb.setColorPressed(colorAccentDark);
        fabb = (FloatingActionButton)getActivity().findViewById(R.id.custommi);
        fabb.setColorNormal(colorAccent);
        fabb.setColorPressed(colorAccentDark);

        fabb = (FloatingActionButton)getActivity().findViewById(R.id.hilow);
        fabb.setColorNormal(colorAccent);
        fabb.setColorPressed(colorAccentDark);
        fabb = (FloatingActionButton)getActivity().findViewById(R.id.lowhi);
        fabb.setColorNormal(colorAccent);
        fabb.setColorPressed(colorAccentDark);
        fabb = (FloatingActionButton)getActivity().findViewById(R.id.range);
        fabb.setColorNormal(colorAccent);
        fabb.setColorPressed(colorAccentDark);

        fabb = (FloatingActionButton)getActivity().findViewById(R.id.hilowcontent);
        fabb.setColorNormal(colorAccent);
        fabb.setColorPressed(colorAccentDark);
        fabb = (FloatingActionButton)getActivity().findViewById(R.id.lowhicontent);
        fabb.setColorNormal(colorAccent);
        fabb.setColorPressed(colorAccentDark);
        fabb = (FloatingActionButton)getActivity().findViewById(R.id.rangecontent);
        fabb.setColorNormal(colorAccent);
        fabb.setColorPressed(colorAccentDark);
    }


}
