package comjason_lewisg.httpsgithub.boozic.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import comjason_lewisg.httpsgithub.boozic.Handlers.FavoritesAdapterHandler;
import comjason_lewisg.httpsgithub.boozic.Handlers.TouchHelpers.OnStartDragListener;
import comjason_lewisg.httpsgithub.boozic.Handlers.TouchHelpers.SimpleItemTouchHelperCallback;
import comjason_lewisg.httpsgithub.boozic.MainActivity;
import comjason_lewisg.httpsgithub.boozic.R;

public class FavoritesFragment extends Fragment implements OnStartDragListener {
    private View rootView;
    FragmentManager manager;
    MainActivity m;

    RecyclerView mRecyclerView;
    public FavoritesAdapterHandler mAdapter;
    RecyclerView.LayoutManager mLayoutManager;
    SwipeRefreshLayout swipeRefreshLayout;

    private ItemTouchHelper mItemTouchHelper;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView =  inflater.inflate(R.layout.fragment_favorites,container,false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.fav_rv);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        m = (MainActivity)getActivity();

        mRecyclerView.setHasFixedSize(true);
        mAdapter = new FavoritesAdapterHandler(m.FLcon.favoritesList, m, this);
        mRecyclerView.setAdapter(mAdapter);

        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(mAdapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(mRecyclerView);



        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView mRecyclerView, int dx, int dy) {
                super.onScrolled(mRecyclerView, dx, dy);
                if (Math.abs(dy) > 20) {
                    if (dy > 0) {
                        m.FAB.menuButton.hide(true);
                    } else {
                        m.FAB.menuButton.show(true);
                    }
                }
            }
        });

        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setColorSchemeColors(ContextCompat.getColor(getActivity().getApplicationContext(), R.color.ColorAccent),
                ContextCompat.getColor(getActivity().getApplicationContext(), R.color.ColorAccent2),
                ContextCompat.getColor(getActivity().getApplicationContext(), R.color.ColorAccent3),
                ContextCompat.getColor(getActivity().getApplicationContext(), R.color.ColorAccent4),
                ContextCompat.getColor(getActivity().getApplicationContext(), R.color.ColorAccent5));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (!mAdapter.getRemovedList().isEmpty()) {
                    m.RFFcon.removeFavoritesList(mAdapter);
                }
                m.FLcon.getFavorites(mAdapter, swipeRefreshLayout);
            }
        });
    }

    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        mItemTouchHelper.startDrag(viewHolder);
    }

    public interface OnPassFav {

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
        manager = getActivity().getSupportFragmentManager();
        manager.popBackStack();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (!mAdapter.getRemovedList().isEmpty()) m.RFFcon.removeFavoritesList(mAdapter);
    }
}

