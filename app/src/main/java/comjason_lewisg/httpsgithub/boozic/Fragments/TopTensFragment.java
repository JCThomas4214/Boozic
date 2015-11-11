package comjason_lewisg.httpsgithub.boozic.Fragments;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
    private AdapterHandler mAdapter;
    private LinearLayoutManager mLayoutManager;
    private SwipeRefreshLayout swipeRefreshLayout;

    int colorPrimary;
    int colorPrimaryDark;
    int colorAccent;
    int colorAccentDark;

    int pastVisiblesItems, visibleItemCount, totalItemCount;

    OnPass dataPasser;

    private List<TopTensModel> DataSet = new ArrayList<TopTensModel>() {
        {
            //this is where we call backend connector
            //to populate Arraylist
            add(new TopTensModel("Skyy Vodka", "12/02/14", 3.5, "ABC liquor","Publix liquor",  1.3, 1.80, 18.73,
                    17.00, 3, true, "fifth",  40, 80, new int[] {251, 196, 75, 49, 21}, 750));
            add(new TopTensModel("Miller Light", "06/11/15", 3.0, "Publix liquor", 1.8, 7.23, 1, true, "(6) bottle", 4.17, 8, new int[] {129, 62, 514, 486, 69}, 750));
            add(new TopTensModel("Bud Light", "04/25/15", 4.0, "ABC liquor", "Walmart", 1.3, 2.34, 8.02,
                    6.73, 1, true, "(6) bottle",  4.20, 8, new int[] {251, 496, 765, 49, 128}, 750));
            add(new TopTensModel("Moscato", "02/09/15", 3.5,"Publix liquor", 1.8, 11.46, 2, true, "fifth", 5.5, 11, new int[] {265, 468, 96, 135, 26}, 750));
            add(new TopTensModel("Fireball Whiskey", "02/26/15", 3.5, "ABC liquor", "Publix liquor", 1.3, 1.8, 16.02,
                    15.56, 3, true, "handle",  33, 66, new int[] {361, 496, 210, 46, 12}, 750));
            add(new TopTensModel("Wyborowa Wodka Rye Grain Polish Vodka", "07/25/15", 4.0, "ABC liquor", "Publix liquor", 1.3, 1.8, 23.61,
                    21.49, 3, true, "fifth",  40, 80, new int[] {465, 129, 76, 32, 15}, 750));
            add(new TopTensModel("Henninger", "08/11/15", 3.5, "Publix liquor", 1.8, 8.26, 1, true, "(6) bottle", 4.80, 10, new int[] {162, 168, 35, 12, 1}, 750));
            add(new TopTensModel("Domaine Gavoty Provence Rose", "02/26/15", 3.5, "ABC liquor", "Publix liquor", 1.3, 1.8, 29.99,
                    28.36, 2, true, "fifth",  13, 26, new int[] {612, 345, 61, 124, 10}, 750));
            add(new TopTensModel("Ciroc French Pineapple Vodka", "10/11/15", 3.5, "Publix liquor", 1.8, 22.99, 3, true, "fifth", 35, 70, new int[] {236, 61, 96, 23, 9}, 750));

            add(new TopTensModel("Skyy Vodka2", "12/02/14", 3.5, "ABC liquor","Publix liquor",  1.3, 1.80, 18.73,
                    17.00, 3, true, "fifth",  40, 80, new int[] {251, 196, 75, 49, 21}, 750));
            add(new TopTensModel("Miller Light", "06/11/15", 3.0, "Publix liquor", 1.8, 7.23, 1, true, "(6) bottle", 4.17, 8, new int[] {129, 62, 514, 486, 69}, 750));
            add(new TopTensModel("Bud Light", "04/25/15", 4.0, "ABC liquor", "Walmart", 1.3, 2.34, 8.02,
                    6.73, 1, true, "(6) bottle",  4.20, 8, new int[] {251, 496, 765, 49, 128}, 750));
            add(new TopTensModel("Moscato", "02/09/15", 3.5,"Publix liquor", 1.8, 11.46, 2, true, "fifth", 5.5, 11, new int[] {265, 468, 96, 135, 26}, 750));
            add(new TopTensModel("Fireball Whiskey", "02/26/15", 3.5, "ABC liquor", "Publix liquor", 1.3, 1.8, 16.02,
                    15.56, 3, true, "handle",  33, 66, new int[] {361, 496, 210, 46, 12}, 750));
            add(new TopTensModel("Wyborowa Wodka Rye Grain Polish Vodka", "07/25/15", 4.0, "ABC liquor", "Publix liquor", 1.3, 1.8, 23.61,
                    21.49, 3, true, "fifth",  40, 80, new int[] {465, 129, 76, 32, 15}, 750));
            add(new TopTensModel("Henninger", "08/11/15", 3.5, "Publix liquor", 1.8, 8.26, 1, true, "(6) bottle", 4.80, 10, new int[] {162, 168, 35, 12, 1}, 750));
            add(new TopTensModel("Domaine Gavoty Provence Rose", "02/26/15", 3.5, "ABC liquor", "Publix liquor", 1.3, 1.8, 29.99,
                    28.36, 2, true, "fifth",  13, 26, new int[] {612, 345, 61, 124, 10}, 750));
            add(new TopTensModel("Ciroc French Pineapple Vodka", "10/11/15", 3.5, "Publix liquor", 1.8, 22.99, 3, true, "fifth", 35, 70, new int[] {236, 61, 96, 23, 9}, 750));
            add(new TopTensModel("Skyy Vodk3a", "12/02/14", 3.5, "ABC liquor","Publix liquor",  1.3, 1.80, 18.73,
                    17.00, 3, true, "fifth",  40, 80, new int[] {251, 196, 75, 49, 21}, 750));
            add(new TopTensModel("Miller Light", "06/11/15", 3.0, "Publix liquor", 1.8, 7.23, 1, true, "(6) bottle", 4.17, 8, new int[] {129, 62, 514, 486, 69}, 750));
            add(new TopTensModel("Bud Light", "04/25/15", 4.0, "ABC liquor", "Walmart", 1.3, 2.34, 8.02,
                    6.73, 1, true, "(6) bottle",  4.20, 8, new int[] {251, 496, 765, 49, 128}, 750));
            add(new TopTensModel("Moscato", "02/09/15", 3.5,"Publix liquor", 1.8, 11.46, 2, true, "fifth", 5.5, 11, new int[] {265, 468, 96, 135, 26}, 750));
            add(new TopTensModel("Fireball Whiskey", "02/26/15", 3.5, "ABC liquor", "Publix liquor", 1.3, 1.8, 16.02,
                    15.56, 3, true, "handle",  33, 66, new int[] {361, 496, 210, 46, 12}, 750));
            add(new TopTensModel("Wyborowa Wodka Rye Grain Polish Vodka", "07/25/15", 4.0, "ABC liquor", "Publix liquor", 1.3, 1.8, 23.61,
                    21.49, 3, true, "fifth",  40, 80, new int[] {465, 129, 76, 32, 15}, 750));
            add(new TopTensModel("Henninger", "08/11/15", 3.5, "Publix liquor", 1.8, 8.26, 1, true, "(6) bottle", 4.80, 10, new int[] {162, 168, 35, 12, 1}, 750));
            add(new TopTensModel("Domaine Gavoty Provence Rose", "02/26/15", 3.5, "ABC liquor", "Publix liquor", 1.3, 1.8, 29.99,
                    28.36, 2, true, "fifth",  13, 26, new int[] {612, 345, 61, 124, 10}, 750));
            add(new TopTensModel("Ciroc French Pineapple Vodka", "10/11/15", 3.5, "Publix liquor", 1.8, 22.99, 3, true, "fifth", 35, 70, new int[] {236, 61, 96, 23, 9}, 750));
            add(new TopTensModel("Skyy Vodka4", "12/02/14", 3.5, "ABC liquor","Publix liquor",  1.3, 1.80, 18.73,
                    17.00, 3, true, "fifth",  40, 80, new int[] {251, 196, 75, 49, 21}, 750));
            add(new TopTensModel("Miller Light", "06/11/15", 3.0, "Publix liquor", 1.8, 7.23, 1, true, "(6) bottle", 4.17, 8, new int[] {129, 62, 514, 486, 69}, 750));
            add(new TopTensModel("Bud Light", "04/25/15", 4.0, "ABC liquor", "Walmart", 1.3, 2.34, 8.02,
                    6.73, 1, true, "(6) bottle",  4.20, 8, new int[] {251, 496, 765, 49, 128}, 750));
            add(new TopTensModel("Moscato", "02/09/15", 3.5,"Publix liquor", 1.8, 11.46, 2, true, "fifth", 5.5, 11, new int[] {265, 468, 96, 135, 26}, 750));
            add(new TopTensModel("Fireball Whiskey", "02/26/15", 3.5, "ABC liquor", "Publix liquor", 1.3, 1.8, 16.02,
                    15.56, 3, true, "handle",  33, 66, new int[] {361, 496, 210, 46, 12}, 750));
            add(new TopTensModel("Wyborowa Wodka Rye Grain Polish Vodka", "07/25/15", 4.0, "ABC liquor", "Publix liquor", 1.3, 1.8, 23.61,
                    21.49, 3, true, "fifth",  40, 80, new int[] {465, 129, 76, 32, 15}, 750));
            add(new TopTensModel("Henninger", "08/11/15", 3.5, "Publix liquor", 1.8, 8.26, 1, true, "(6) bottle", 4.80, 10, new int[] {162, 168, 35, 12, 1}, 750));
            add(new TopTensModel("Domaine Gavoty Provence Rose", "02/26/15", 3.5, "ABC liquor", "Publix liquor", 1.3, 1.8, 29.99,
                    28.36, 2, true, "fifth",  13, 26, new int[] {612, 345, 61, 124, 10}, 750));
            add(new TopTensModel("Ciroc French Pineapple Vodka", "10/11/15", 3.5, "Publix liquor", 1.8, 22.99, 3, true, "fifth", 35, 70, new int[] {236, 61, 96, 23, 9}, 750));
            add(new TopTensModel("Skyy Vodka5", "12/02/14", 3.5, "ABC liquor","Publix liquor",  1.3, 1.80, 18.73,
                    17.00, 3, true, "fifth",  40, 80, new int[] {251, 196, 75, 49, 21}, 750));
            add(new TopTensModel("Miller Light", "06/11/15", 3.0, "Publix liquor", 1.8, 7.23, 1, true, "(6) bottle", 4.17, 8, new int[] {129, 62, 514, 486, 69}, 750));
            add(new TopTensModel("Bud Light", "04/25/15", 4.0, "ABC liquor", "Walmart", 1.3, 2.34, 8.02,
                    6.73, 1, true, "(6) bottle",  4.20, 8, new int[] {251, 496, 765, 49, 128}, 750));
            add(new TopTensModel("Moscato", "02/09/15", 3.5,"Publix liquor", 1.8, 11.46, 2, true, "fifth", 5.5, 11, new int[] {265, 468, 96, 135, 26}, 750));
            add(new TopTensModel("Fireball Whiskey", "02/26/15", 3.5, "ABC liquor", "Publix liquor", 1.3, 1.8, 16.02,
                    15.56, 3, true, "handle",  33, 66, new int[] {361, 496, 210, 46, 12}, 750));
            add(new TopTensModel("Wyborowa Wodka Rye Grain Polish Vodka", "07/25/15", 4.0, "ABC liquor", "Publix liquor", 1.3, 1.8, 23.61,
                    21.49, 3, true, "fifth",  40, 80, new int[] {465, 129, 76, 32, 15}, 750));
            add(new TopTensModel("Henninger", "08/11/15", 3.5, "Publix liquor", 1.8, 8.26, 1, true, "(6) bottle", 4.80, 10, new int[] {162, 168, 35, 12, 1}, 750));
            add(new TopTensModel("Domaine Gavoty Provence Rose", "02/26/15", 3.5, "ABC liquor", "Publix liquor", 1.3, 1.8, 29.99,
                    28.36, 2, true, "fifth",  13, 26, new int[] {612, 345, 61, 124, 10}, 750));
            add(new TopTensModel("Ciroc French Pineapple Vodka", "10/11/15", 3.5, "Publix liquor", 1.8, 22.99, 3, true, "fifth", 35, 70, new int[]{236, 61, 96, 23, 9}, 750));
            add(new TopTensModel("Skyy Vodka6", "12/02/14", 3.5, "ABC liquor","Publix liquor",  1.3, 1.80, 18.73,
                17.00, 3, true, "fifth",  40, 80, new int[] {251, 196, 75, 49, 21}, 750));
            add(new TopTensModel("Miller Light", "06/11/15", 3.0, "Publix liquor", 1.8, 7.23, 1, true, "(6) bottle", 4.17, 8, new int[] {129, 62, 514, 486, 69}, 750));
            add(new TopTensModel("Bud Light", "04/25/15", 4.0, "ABC liquor", "Walmart", 1.3, 2.34, 8.02,
                    6.73, 1, true, "(6) bottle",  4.20, 8, new int[] {251, 496, 765, 49, 128}, 750));
            add(new TopTensModel("Moscato", "02/09/15", 3.5,"Publix liquor", 1.8, 11.46, 2, true, "fifth", 5.5, 11, new int[] {265, 468, 96, 135, 26}, 750));
            add(new TopTensModel("Fireball Whiskey", "02/26/15", 3.5, "ABC liquor", "Publix liquor", 1.3, 1.8, 16.02,
                    15.56, 3, true, "handle",  33, 66, new int[] {361, 496, 210, 46, 12}, 750));
            add(new TopTensModel("Wyborowa Wodka Rye Grain Polish Vodka", "07/25/15", 4.0, "ABC liquor", "Publix liquor", 1.3, 1.8, 23.61,
                    21.49, 3, true, "fifth",  40, 80, new int[] {465, 129, 76, 32, 15}, 750));
            add(new TopTensModel("Henninger", "08/11/15", 3.5, "Publix liquor", 1.8, 8.26, 1, true, "(6) bottle", 4.80, 10, new int[] {162, 168, 35, 12, 1}, 750));
            add(new TopTensModel("Domaine Gavoty Provence Rose", "02/26/15", 3.5, "ABC liquor", "Publix liquor", 1.3, 1.8, 29.99,
                    28.36, 2, true, "fifth",  13, 26, new int[] {612, 345, 61, 124, 10}, 750));
            add(new TopTensModel("Ciroc French Pineapple Vodka", "10/11/15", 3.5, "Publix liquor", 1.8, 22.99, 3, true, "fifth", 35, 70, new int[] {236, 61, 96, 23, 9}, 750));
            add(new TopTensModel("Skyy Vodka7", "12/02/14", 3.5, "ABC liquor","Publix liquor",  1.3, 1.80, 18.73,
                    17.00, 3, true, "fifth",  40, 80, new int[] {251, 196, 75, 49, 21}, 750));
            add(new TopTensModel("Miller Light", "06/11/15", 3.0, "Publix liquor", 1.8, 7.23, 1, true, "(6) bottle", 4.17, 8, new int[] {129, 62, 514, 486, 69}, 750));
            add(new TopTensModel("Bud Light", "04/25/15", 4.0, "ABC liquor", "Walmart", 1.3, 2.34, 8.02,
                    6.73, 1, true, "(6) bottle",  4.20, 8, new int[] {251, 496, 765, 49, 128}, 750));
            add(new TopTensModel("Moscato", "02/09/15", 3.5,"Publix liquor", 1.8, 11.46, 2, true, "fifth", 5.5, 11, new int[] {265, 468, 96, 135, 26}, 750));
            add(new TopTensModel("Fireball Whiskey", "02/26/15", 3.5, "ABC liquor", "Publix liquor", 1.3, 1.8, 16.02,
                    15.56, 3, true, "handle",  33, 66, new int[] {361, 496, 210, 46, 12}, 750));
            add(new TopTensModel("Wyborowa Wodka Rye Grain Polish Vodka", "07/25/15", 4.0, "ABC liquor", "Publix liquor", 1.3, 1.8, 23.61,
                    21.49, 3, true, "fifth",  40, 80, new int[] {465, 129, 76, 32, 15}, 750));
            add(new TopTensModel("Henninger", "08/11/15", 3.5, "Publix liquor", 1.8, 8.26, 1, true, "(6) bottle", 4.80, 10, new int[] {162, 168, 35, 12, 1}, 750));
            add(new TopTensModel("Domaine Gavoty Provence Rose", "02/26/15", 3.5, "ABC liquor", "Publix liquor", 1.3, 1.8, 29.99,
                    28.36, 2, true, "fifth",  13, 26, new int[] {612, 345, 61, 124, 10}, 750));
            add(new TopTensModel("Ciroc French Pineapple Vodka", "10/11/15", 3.5, "Publix liquor", 1.8, 22.99, 3, true, "fifth", 35, 70, new int[] {236, 61, 96, 23, 9}, 750));
            add(new TopTensModel("Skyy Vodka8", "12/02/14", 3.5, "ABC liquor","Publix liquor",  1.3, 1.80, 18.73,
                    17.00, 3, true, "fifth",  40, 80, new int[] {251, 196, 75, 49, 21}, 750));
            add(new TopTensModel("Miller Light", "06/11/15", 3.0, "Publix liquor", 1.8, 7.23, 1, true, "(6) bottle", 4.17, 8, new int[] {129, 62, 514, 486, 69}, 750));
            add(new TopTensModel("Bud Light", "04/25/15", 4.0, "ABC liquor", "Walmart", 1.3, 2.34, 8.02,
                    6.73, 1, true, "(6) bottle",  4.20, 8, new int[] {251, 496, 765, 49, 128}, 750));
            add(new TopTensModel("Moscato", "02/09/15", 3.5,"Publix liquor", 1.8, 11.46, 2, true, "fifth", 5.5, 11, new int[] {265, 468, 96, 135, 26}, 750));
            add(new TopTensModel("Fireball Whiskey", "02/26/15", 3.5, "ABC liquor", "Publix liquor", 1.3, 1.8, 16.02,
                    15.56, 3, true, "handle",  33, 66, new int[] {361, 496, 210, 46, 12}, 750));
            add(new TopTensModel("Wyborowa Wodka Rye Grain Polish Vodka", "07/25/15", 4.0, "ABC liquor", "Publix liquor", 1.3, 1.8, 23.61,
                    21.49, 3, true, "fifth",  40, 80, new int[] {465, 129, 76, 32, 15}, 750));
            add(new TopTensModel("Henninger", "08/11/15", 3.5, "Publix liquor", 1.8, 8.26, 1, true, "(6) bottle", 4.80, 10, new int[] {162, 168, 35, 12, 1}, 750));
            add(new TopTensModel("Domaine Gavoty Provence Rose", "02/26/15", 3.5, "ABC liquor", "Publix liquor", 1.3, 1.8, 29.99,
                    28.36, 2, true, "fifth",  13, 26, new int[] {612, 345, 61, 124, 10}, 750));
            add(new TopTensModel("Ciroc French Pineapple Vodka", "10/11/15", 3.5, "Publix liquor", 1.8, 22.99, 3, true, "fifth", 35, 70, new int[] {236, 61, 96, 23, 9}, 750));
            add(new TopTensModel("Skyy Vodka9", "12/02/14", 3.5, "ABC liquor","Publix liquor",  1.3, 1.80, 18.73,
                    17.00, 3, true, "fifth",  40, 80, new int[] {251, 196, 75, 49, 21}, 750));
            add(new TopTensModel("Miller Light", "06/11/15", 3.0, "Publix liquor", 1.8, 7.23, 1, true, "(6) bottle", 4.17, 8, new int[] {129, 62, 514, 486, 69}, 750));
            add(new TopTensModel("Bud Light", "04/25/15", 4.0, "ABC liquor", "Walmart", 1.3, 2.34, 8.02,
                    6.73, 1, true, "(6) bottle",  4.20, 8, new int[] {251, 496, 765, 49, 128}, 750));
            add(new TopTensModel("Moscato", "02/09/15", 3.5,"Publix liquor", 1.8, 11.46, 2, true, "fifth", 5.5, 11, new int[] {265, 468, 96, 135, 26}, 750));
            add(new TopTensModel("Fireball Whiskey", "02/26/15", 3.5, "ABC liquor", "Publix liquor", 1.3, 1.8, 16.02,
                    15.56, 3, true, "handle",  33, 66, new int[] {361, 496, 210, 46, 12}, 750));
            add(new TopTensModel("Wyborowa Wodka Rye Grain Polish Vodka", "07/25/15", 4.0, "ABC liquor", "Publix liquor", 1.3, 1.8, 23.61,
                    21.49, 3, true, "fifth",  40, 80, new int[] {465, 129, 76, 32, 15}, 750));
            add(new TopTensModel("Henninger", "08/11/15", 3.5, "Publix liquor", 1.8, 8.26, 1, true, "(6) bottle", 4.80, 10, new int[] {162, 168, 35, 12, 1}, 750));
            add(new TopTensModel("Domaine Gavoty Provence Rose", "02/26/15", 3.5, "ABC liquor", "Publix liquor", 1.3, 1.8, 29.99,
                    28.36, 2, true, "fifth",  13, 26, new int[] {612, 345, 61, 124, 10}, 750));
            add(new TopTensModel("Ciroc French Pineapple Vodka", "10/11/15", 3.5, "Publix liquor", 1.8, 22.99, 3, true, "fifth", 35, 70, new int[] {236, 61, 96, 23, 9}, 750));
            add(new TopTensModel("Skyy Vodka10", "12/02/14", 3.5, "ABC liquor","Publix liquor",  1.3, 1.80, 18.73,
                    17.00, 3, true, "fifth",  40, 80, new int[] {251, 196, 75, 49, 21}, 750));
            add(new TopTensModel("Miller Light", "06/11/15", 3.0, "Publix liquor", 1.8, 7.23, 1, true, "(6) bottle", 4.17, 8, new int[] {129, 62, 514, 486, 69}, 750));
            add(new TopTensModel("Bud Light", "04/25/15", 4.0, "ABC liquor", "Walmart", 1.3, 2.34, 8.02,
                    6.73, 1, true, "(6) bottle",  4.20, 8, new int[] {251, 496, 765, 49, 128}, 750));
            add(new TopTensModel("Moscato", "02/09/15", 3.5,"Publix liquor", 1.8, 11.46, 2, true, "fifth", 5.5, 11, new int[] {265, 468, 96, 135, 26}, 750));
            add(new TopTensModel("Fireball Whiskey", "02/26/15", 3.5, "ABC liquor", "Publix liquor", 1.3, 1.8, 16.02,
                    15.56, 3, true, "handle",  33, 66, new int[] {361, 496, 210, 46, 12}, 750));
            add(new TopTensModel("Wyborowa Wodka Rye Grain Polish Vodka", "07/25/15", 4.0, "ABC liquor", "Publix liquor", 1.3, 1.8, 23.61,
                    21.49, 3, true, "fifth",  40, 80, new int[] {465, 129, 76, 32, 15}, 750));
            add(new TopTensModel("Henninger", "08/11/15", 3.5, "Publix liquor", 1.8, 8.26, 1, true, "(6) bottle", 4.80, 10, new int[] {162, 168, 35, 12, 1}, 750));
            add(new TopTensModel("Domaine Gavoty Provence Rose", "02/26/15", 3.5, "ABC liquor", "Publix liquor", 1.3, 1.8, 29.99,
                    28.36, 2, true, "fifth",  13, 26, new int[] {612, 345, 61, 124, 10}, 750));
            add(new TopTensModel("Ciroc French Pineapple Vodka", "10/11/15", 3.5, "Publix liquor", 1.8, 22.99, 3, true, "fifth", 35, 70, new int[] {236, 61, 96, 23, 9}, 750));
            add(new TopTensModel("Skyy Vodka", "12/02/14", 3.5, "ABC liquor","Publix liquor",  1.3, 1.80, 18.73,
                    17.00, 3, true, "fifth",  40, 80, new int[] {251, 196, 75, 49, 21}, 750));
            add(new TopTensModel("Miller Light", "06/11/15", 3.0, "Publix liquor", 1.8, 7.23, 1, true, "(6) bottle", 4.17, 8, new int[] {129, 62, 514, 486, 69}, 750));
            add(new TopTensModel("Bud Light", "04/25/15", 4.0, "ABC liquor", "Walmart", 1.3, 2.34, 8.02,
                    6.73, 1, true, "(6) bottle",  4.20, 8, new int[] {251, 496, 765, 49, 128}, 750));
            add(new TopTensModel("Moscato", "02/09/15", 3.5,"Publix liquor", 1.8, 11.46, 2, true, "fifth", 5.5, 11, new int[] {265, 468, 96, 135, 26}, 750));
            add(new TopTensModel("Fireball Whiskey", "02/26/15", 3.5, "ABC liquor", "Publix liquor", 1.3, 1.8, 16.02,
                    15.56, 3, true, "handle",  33, 66, new int[] {361, 496, 210, 46, 12}, 750));
            add(new TopTensModel("Wyborowa Wodka Rye Grain Polish Vodka", "07/25/15", 4.0, "ABC liquor", "Publix liquor", 1.3, 1.8, 23.61,
                    21.49, 3, true, "fifth",  40, 80, new int[] {465, 129, 76, 32, 15}, 750));
            add(new TopTensModel("Henninger", "08/11/15", 3.5, "Publix liquor", 1.8, 8.26, 1, true, "(6) bottle", 4.80, 10, new int[] {162, 168, 35, 12, 1}, 750));
            add(new TopTensModel("Domaine Gavoty Provence Rose", "02/26/15", 3.5, "ABC liquor", "Publix liquor", 1.3, 1.8, 29.99,
                    28.36, 2, true, "fifth",  13, 26, new int[] {612, 345, 61, 124, 10}, 750));
            add(new TopTensModel("Ciroc French Pineapple Vodka", "10/11/15", 3.5, "Publix liquor", 1.8, 22.99, 3, true, "fifth", 35, 70, new int[] {236, 61, 96, 23, 9}, 750));

            add(new TopTensModel("Skyy Vodka2", "12/02/14", 3.5, "ABC liquor","Publix liquor",  1.3, 1.80, 18.73,
                    17.00, 3, true, "fifth",  40, 80, new int[] {251, 196, 75, 49, 21}, 750));
            add(new TopTensModel("Miller Light", "06/11/15", 3.0, "Publix liquor", 1.8, 7.23, 1, true, "(6) bottle", 4.17, 8, new int[] {129, 62, 514, 486, 69}, 750));
            add(new TopTensModel("Bud Light", "04/25/15", 4.0, "ABC liquor", "Walmart", 1.3, 2.34, 8.02,
                    6.73, 1, true, "(6) bottle",  4.20, 8, new int[] {251, 496, 765, 49, 128}, 750));
            add(new TopTensModel("Moscato", "02/09/15", 3.5,"Publix liquor", 1.8, 11.46, 2, true, "fifth", 5.5, 11, new int[] {265, 468, 96, 135, 26}, 750));
            add(new TopTensModel("Fireball Whiskey", "02/26/15", 3.5, "ABC liquor", "Publix liquor", 1.3, 1.8, 16.02,
                    15.56, 3, true, "handle",  33, 66, new int[] {361, 496, 210, 46, 12}, 750));
            add(new TopTensModel("Wyborowa Wodka Rye Grain Polish Vodka", "07/25/15", 4.0, "ABC liquor", "Publix liquor", 1.3, 1.8, 23.61,
                    21.49, 3, true, "fifth",  40, 80, new int[] {465, 129, 76, 32, 15}, 750));
            add(new TopTensModel("Henninger", "08/11/15", 3.5, "Publix liquor", 1.8, 8.26, 1, true, "(6) bottle", 4.80, 10, new int[] {162, 168, 35, 12, 1}, 750));
            add(new TopTensModel("Domaine Gavoty Provence Rose", "02/26/15", 3.5, "ABC liquor", "Publix liquor", 1.3, 1.8, 29.99,
                    28.36, 2, true, "fifth",  13, 26, new int[] {612, 345, 61, 124, 10}, 750));
            add(new TopTensModel("Ciroc French Pineapple Vodka", "10/11/15", 3.5, "Publix liquor", 1.8, 22.99, 3, true, "fifth", 35, 70, new int[] {236, 61, 96, 23, 9}, 750));
            add(new TopTensModel("Skyy Vodk3a", "12/02/14", 3.5, "ABC liquor","Publix liquor",  1.3, 1.80, 18.73,
                    17.00, 3, true, "fifth",  40, 80, new int[] {251, 196, 75, 49, 21}, 750));
            add(new TopTensModel("Miller Light", "06/11/15", 3.0, "Publix liquor", 1.8, 7.23, 1, true, "(6) bottle", 4.17, 8, new int[] {129, 62, 514, 486, 69}, 750));
            add(new TopTensModel("Bud Light", "04/25/15", 4.0, "ABC liquor", "Walmart", 1.3, 2.34, 8.02,
                    6.73, 1, true, "(6) bottle",  4.20, 8, new int[] {251, 496, 765, 49, 128}, 750));
            add(new TopTensModel("Moscato", "02/09/15", 3.5,"Publix liquor", 1.8, 11.46, 2, true, "fifth", 5.5, 11, new int[] {265, 468, 96, 135, 26}, 750));
            add(new TopTensModel("Fireball Whiskey", "02/26/15", 3.5, "ABC liquor", "Publix liquor", 1.3, 1.8, 16.02,
                    15.56, 3, true, "handle",  33, 66, new int[] {361, 496, 210, 46, 12}, 750));
            add(new TopTensModel("Wyborowa Wodka Rye Grain Polish Vodka", "07/25/15", 4.0, "ABC liquor", "Publix liquor", 1.3, 1.8, 23.61,
                    21.49, 3, true, "fifth",  40, 80, new int[] {465, 129, 76, 32, 15}, 750));
            add(new TopTensModel("Henninger", "08/11/15", 3.5, "Publix liquor", 1.8, 8.26, 1, true, "(6) bottle", 4.80, 10, new int[] {162, 168, 35, 12, 1}, 750));
            add(new TopTensModel("Domaine Gavoty Provence Rose", "02/26/15", 3.5, "ABC liquor", "Publix liquor", 1.3, 1.8, 29.99,
                    28.36, 2, true, "fifth",  13, 26, new int[] {612, 345, 61, 124, 10}, 750));
            add(new TopTensModel("Ciroc French Pineapple Vodka", "10/11/15", 3.5, "Publix liquor", 1.8, 22.99, 3, true, "fifth", 35, 70, new int[] {236, 61, 96, 23, 9}, 750));
            add(new TopTensModel("Skyy Vodka4", "12/02/14", 3.5, "ABC liquor","Publix liquor",  1.3, 1.80, 18.73,
                    17.00, 3, true, "fifth",  40, 80, new int[] {251, 196, 75, 49, 21}, 750));
            add(new TopTensModel("Miller Light", "06/11/15", 3.0, "Publix liquor", 1.8, 7.23, 1, true, "(6) bottle", 4.17, 8, new int[] {129, 62, 514, 486, 69}, 750));
            add(new TopTensModel("Bud Light", "04/25/15", 4.0, "ABC liquor", "Walmart", 1.3, 2.34, 8.02,
                    6.73, 1, true, "(6) bottle",  4.20, 8, new int[] {251, 496, 765, 49, 128}, 750));
            add(new TopTensModel("Moscato", "02/09/15", 3.5,"Publix liquor", 1.8, 11.46, 2, true, "fifth", 5.5, 11, new int[] {265, 468, 96, 135, 26}, 750));
            add(new TopTensModel("Fireball Whiskey", "02/26/15", 3.5, "ABC liquor", "Publix liquor", 1.3, 1.8, 16.02,
                    15.56, 3, true, "handle",  33, 66, new int[] {361, 496, 210, 46, 12}, 750));
            add(new TopTensModel("Wyborowa Wodka Rye Grain Polish Vodka", "07/25/15", 4.0, "ABC liquor", "Publix liquor", 1.3, 1.8, 23.61,
                    21.49, 3, true, "fifth",  40, 80, new int[] {465, 129, 76, 32, 15}, 750));
            add(new TopTensModel("Henninger", "08/11/15", 3.5, "Publix liquor", 1.8, 8.26, 1, true, "(6) bottle", 4.80, 10, new int[] {162, 168, 35, 12, 1}, 750));
            add(new TopTensModel("Domaine Gavoty Provence Rose", "02/26/15", 3.5, "ABC liquor", "Publix liquor", 1.3, 1.8, 29.99,
                    28.36, 2, true, "fifth",  13, 26, new int[] {612, 345, 61, 124, 10}, 750));
            add(new TopTensModel("Ciroc French Pineapple Vodka", "10/11/15", 3.5, "Publix liquor", 1.8, 22.99, 3, true, "fifth", 35, 70, new int[] {236, 61, 96, 23, 9}, 750));
            add(new TopTensModel("Skyy Vodka5", "12/02/14", 3.5, "ABC liquor","Publix liquor",  1.3, 1.80, 18.73,
                    17.00, 3, true, "fifth",  40, 80, new int[] {251, 196, 75, 49, 21}, 750));
            add(new TopTensModel("Miller Light", "06/11/15", 3.0, "Publix liquor", 1.8, 7.23, 1, true, "(6) bottle", 4.17, 8, new int[] {129, 62, 514, 486, 69}, 750));
            add(new TopTensModel("Bud Light", "04/25/15", 4.0, "ABC liquor", "Walmart", 1.3, 2.34, 8.02,
                    6.73, 1, true, "(6) bottle",  4.20, 8, new int[] {251, 496, 765, 49, 128}, 750));
            add(new TopTensModel("Moscato", "02/09/15", 3.5,"Publix liquor", 1.8, 11.46, 2, true, "fifth", 5.5, 11, new int[] {265, 468, 96, 135, 26}, 750));
            add(new TopTensModel("Fireball Whiskey", "02/26/15", 3.5, "ABC liquor", "Publix liquor", 1.3, 1.8, 16.02,
                    15.56, 3, true, "handle",  33, 66, new int[] {361, 496, 210, 46, 12}, 750));
            add(new TopTensModel("Wyborowa Wodka Rye Grain Polish Vodka", "07/25/15", 4.0, "ABC liquor", "Publix liquor", 1.3, 1.8, 23.61,
                    21.49, 3, true, "fifth",  40, 80, new int[] {465, 129, 76, 32, 15}, 750));
            add(new TopTensModel("Henninger", "08/11/15", 3.5, "Publix liquor", 1.8, 8.26, 1, true, "(6) bottle", 4.80, 10, new int[] {162, 168, 35, 12, 1}, 750));
            add(new TopTensModel("Domaine Gavoty Provence Rose", "02/26/15", 3.5, "ABC liquor", "Publix liquor", 1.3, 1.8, 29.99,
                    28.36, 2, true, "fifth",  13, 26, new int[] {612, 345, 61, 124, 10}, 750));
            add(new TopTensModel("Ciroc French Pineapple Vodka", "10/11/15", 3.5, "Publix liquor", 1.8, 22.99, 3, true, "fifth", 35, 70, new int[]{236, 61, 96, 23, 9}, 750));
            add(new TopTensModel("Skyy Vodka6", "12/02/14", 3.5, "ABC liquor","Publix liquor",  1.3, 1.80, 18.73,
                    17.00, 3, true, "fifth",  40, 80, new int[] {251, 196, 75, 49, 21}, 750));
            add(new TopTensModel("Miller Light", "06/11/15", 3.0, "Publix liquor", 1.8, 7.23, 1, true, "(6) bottle", 4.17, 8, new int[] {129, 62, 514, 486, 69}, 750));
            add(new TopTensModel("Bud Light", "04/25/15", 4.0, "ABC liquor", "Walmart", 1.3, 2.34, 8.02,
                    6.73, 1, true, "(6) bottle",  4.20, 8, new int[] {251, 496, 765, 49, 128}, 750));
            add(new TopTensModel("Moscato", "02/09/15", 3.5,"Publix liquor", 1.8, 11.46, 2, true, "fifth", 5.5, 11, new int[] {265, 468, 96, 135, 26}, 750));
            add(new TopTensModel("Fireball Whiskey", "02/26/15", 3.5, "ABC liquor", "Publix liquor", 1.3, 1.8, 16.02,
                    15.56, 3, true, "handle",  33, 66, new int[] {361, 496, 210, 46, 12}, 750));
            add(new TopTensModel("Wyborowa Wodka Rye Grain Polish Vodka", "07/25/15", 4.0, "ABC liquor", "Publix liquor", 1.3, 1.8, 23.61,
                    21.49, 3, true, "fifth",  40, 80, new int[] {465, 129, 76, 32, 15}, 750));
            add(new TopTensModel("Henninger", "08/11/15", 3.5, "Publix liquor", 1.8, 8.26, 1, true, "(6) bottle", 4.80, 10, new int[] {162, 168, 35, 12, 1}, 750));
            add(new TopTensModel("Domaine Gavoty Provence Rose", "02/26/15", 3.5, "ABC liquor", "Publix liquor", 1.3, 1.8, 29.99,
                    28.36, 2, true, "fifth",  13, 26, new int[] {612, 345, 61, 124, 10}, 750));
            add(new TopTensModel("Ciroc French Pineapple Vodka", "10/11/15", 3.5, "Publix liquor", 1.8, 22.99, 3, true, "fifth", 35, 70, new int[] {236, 61, 96, 23, 9}, 750));
            add(new TopTensModel("Skyy Vodka7", "12/02/14", 3.5, "ABC liquor","Publix liquor",  1.3, 1.80, 18.73,
                    17.00, 3, true, "fifth",  40, 80, new int[] {251, 196, 75, 49, 21}, 750));
            add(new TopTensModel("Miller Light", "06/11/15", 3.0, "Publix liquor", 1.8, 7.23, 1, true, "(6) bottle", 4.17, 8, new int[] {129, 62, 514, 486, 69}, 750));
            add(new TopTensModel("Bud Light", "04/25/15", 4.0, "ABC liquor", "Walmart", 1.3, 2.34, 8.02,
                    6.73, 1, true, "(6) bottle",  4.20, 8, new int[] {251, 496, 765, 49, 128}, 750));
            add(new TopTensModel("Moscato", "02/09/15", 3.5,"Publix liquor", 1.8, 11.46, 2, true, "fifth", 5.5, 11, new int[] {265, 468, 96, 135, 26}, 750));
            add(new TopTensModel("Fireball Whiskey", "02/26/15", 3.5, "ABC liquor", "Publix liquor", 1.3, 1.8, 16.02,
                    15.56, 3, true, "handle",  33, 66, new int[] {361, 496, 210, 46, 12}, 750));
            add(new TopTensModel("Wyborowa Wodka Rye Grain Polish Vodka", "07/25/15", 4.0, "ABC liquor", "Publix liquor", 1.3, 1.8, 23.61,
                    21.49, 3, true, "fifth",  40, 80, new int[] {465, 129, 76, 32, 15}, 750));
            add(new TopTensModel("Henninger", "08/11/15", 3.5, "Publix liquor", 1.8, 8.26, 1, true, "(6) bottle", 4.80, 10, new int[] {162, 168, 35, 12, 1}, 750));
            add(new TopTensModel("Domaine Gavoty Provence Rose", "02/26/15", 3.5, "ABC liquor", "Publix liquor", 1.3, 1.8, 29.99,
                    28.36, 2, true, "fifth",  13, 26, new int[] {612, 345, 61, 124, 10}, 750));
            add(new TopTensModel("Ciroc French Pineapple Vodka", "10/11/15", 3.5, "Publix liquor", 1.8, 22.99, 3, true, "fifth", 35, 70, new int[] {236, 61, 96, 23, 9}, 750));
            add(new TopTensModel("Skyy Vodka8", "12/02/14", 3.5, "ABC liquor","Publix liquor",  1.3, 1.80, 18.73,
                    17.00, 3, true, "fifth",  40, 80, new int[] {251, 196, 75, 49, 21}, 750));
            add(new TopTensModel("Miller Light", "06/11/15", 3.0, "Publix liquor", 1.8, 7.23, 1, true, "(6) bottle", 4.17, 8, new int[] {129, 62, 514, 486, 69}, 750));
            add(new TopTensModel("Bud Light", "04/25/15", 4.0, "ABC liquor", "Walmart", 1.3, 2.34, 8.02,
                    6.73, 1, true, "(6) bottle",  4.20, 8, new int[] {251, 496, 765, 49, 128}, 750));
            add(new TopTensModel("Moscato", "02/09/15", 3.5,"Publix liquor", 1.8, 11.46, 2, true, "fifth", 5.5, 11, new int[] {265, 468, 96, 135, 26}, 750));
            add(new TopTensModel("Fireball Whiskey", "02/26/15", 3.5, "ABC liquor", "Publix liquor", 1.3, 1.8, 16.02,
                    15.56, 3, true, "handle",  33, 66, new int[] {361, 496, 210, 46, 12}, 750));
            add(new TopTensModel("Wyborowa Wodka Rye Grain Polish Vodka", "07/25/15", 4.0, "ABC liquor", "Publix liquor", 1.3, 1.8, 23.61,
                    21.49, 3, true, "fifth",  40, 80, new int[] {465, 129, 76, 32, 15}, 750));
            add(new TopTensModel("Henninger", "08/11/15", 3.5, "Publix liquor", 1.8, 8.26, 1, true, "(6) bottle", 4.80, 10, new int[] {162, 168, 35, 12, 1}, 750));
            add(new TopTensModel("Domaine Gavoty Provence Rose", "02/26/15", 3.5, "ABC liquor", "Publix liquor", 1.3, 1.8, 29.99,
                    28.36, 2, true, "fifth",  13, 26, new int[] {612, 345, 61, 124, 10}, 750));
            add(new TopTensModel("Ciroc French Pineapple Vodka", "10/11/15", 3.5, "Publix liquor", 1.8, 22.99, 3, true, "fifth", 35, 70, new int[] {236, 61, 96, 23, 9}, 750));
            add(new TopTensModel("Skyy Vodka9", "12/02/14", 3.5, "ABC liquor","Publix liquor",  1.3, 1.80, 18.73,
                    17.00, 3, true, "fifth",  40, 80, new int[] {251, 196, 75, 49, 21}, 750));
            add(new TopTensModel("Miller Light", "06/11/15", 3.0, "Publix liquor", 1.8, 7.23, 1, true, "(6) bottle", 4.17, 8, new int[] {129, 62, 514, 486, 69}, 750));
            add(new TopTensModel("Bud Light", "04/25/15", 4.0, "ABC liquor", "Walmart", 1.3, 2.34, 8.02,
                    6.73, 1, true, "(6) bottle",  4.20, 8, new int[] {251, 496, 765, 49, 128}, 750));
            add(new TopTensModel("Moscato", "02/09/15", 3.5,"Publix liquor", 1.8, 11.46, 2, true, "fifth", 5.5, 11, new int[] {265, 468, 96, 135, 26}, 750));
            add(new TopTensModel("Fireball Whiskey", "02/26/15", 3.5, "ABC liquor", "Publix liquor", 1.3, 1.8, 16.02,
                    15.56, 3, true, "handle",  33, 66, new int[] {361, 496, 210, 46, 12}, 750));
            add(new TopTensModel("Wyborowa Wodka Rye Grain Polish Vodka", "07/25/15", 4.0, "ABC liquor", "Publix liquor", 1.3, 1.8, 23.61,
                    21.49, 3, true, "fifth",  40, 80, new int[] {465, 129, 76, 32, 15}, 750));
            add(new TopTensModel("Henninger", "08/11/15", 3.5, "Publix liquor", 1.8, 8.26, 1, true, "(6) bottle", 4.80, 10, new int[] {162, 168, 35, 12, 1}, 750));
            add(new TopTensModel("Domaine Gavoty Provence Rose", "02/26/15", 3.5, "ABC liquor", "Publix liquor", 1.3, 1.8, 29.99,
                    28.36, 2, true, "fifth",  13, 26, new int[] {612, 345, 61, 124, 10}, 750));
            add(new TopTensModel("Ciroc French Pineapple Vodka", "10/11/15", 3.5, "Publix liquor", 1.8, 22.99, 3, true, "fifth", 35, 70, new int[] {236, 61, 96, 23, 9}, 750));
            add(new TopTensModel("Skyy Vodka10", "12/02/14", 3.5, "ABC liquor","Publix liquor",  1.3, 1.80, 18.73,
                    17.00, 3, true, "fifth",  40, 80, new int[] {251, 196, 75, 49, 21}, 750));
            add(new TopTensModel("Miller Light", "06/11/15", 3.0, "Publix liquor", 1.8, 7.23, 1, true, "(6) bottle", 4.17, 8, new int[] {129, 62, 514, 486, 69}, 750));
            add(new TopTensModel("Bud Light", "04/25/15", 4.0, "ABC liquor", "Walmart", 1.3, 2.34, 8.02,
                    6.73, 1, true, "(6) bottle",  4.20, 8, new int[] {251, 496, 765, 49, 128}, 750));
            add(new TopTensModel("Moscato", "02/09/15", 3.5,"Publix liquor", 1.8, 11.46, 2, true, "fifth", 5.5, 11, new int[] {265, 468, 96, 135, 26}, 750));
            add(new TopTensModel("Fireball Whiskey", "02/26/15", 3.5, "ABC liquor", "Publix liquor", 1.3, 1.8, 16.02,
                    15.56, 3, true, "handle",  33, 66, new int[] {361, 496, 210, 46, 12}, 750));
            add(new TopTensModel("Wyborowa Wodka Rye Grain Polish Vodka", "07/25/15", 4.0, "ABC liquor", "Publix liquor", 1.3, 1.8, 23.61,
                    21.49, 3, true, "fifth",  40, 80, new int[] {465, 129, 76, 32, 15}, 750));
            add(new TopTensModel("Henninger", "08/11/15", 3.5, "Publix liquor", 1.8, 8.26, 1, true, "(6) bottle", 4.80, 10, new int[] {162, 168, 35, 12, 1}, 750));
            add(new TopTensModel("Domaine Gavoty Provence Rose", "02/26/15", 3.5, "ABC liquor", "Publix liquor", 1.3, 1.8, 29.99,
                    28.36, 2, true, "fifth",  13, 26, new int[] {612, 345, 61, 124, 10}, 750));
            add(new TopTensModel("Ciroc French Pineapple Vodka", "10/11/15", 3.5, "Publix liquor", 1.8, 22.99, 3, true, "fifth", 35, 70, new int[] {236, 61, 96, 23, 9}, 750));
            add(new TopTensModel("Skyy Vodka", "12/02/14", 3.5, "ABC liquor","Publix liquor",  1.3, 1.80, 18.73,
                    17.00, 3, true, "fifth",  40, 80, new int[] {251, 196, 75, 49, 21}, 750));
            add(new TopTensModel("Miller Light", "06/11/15", 3.0, "Publix liquor", 1.8, 7.23, 1, true, "(6) bottle", 4.17, 8, new int[] {129, 62, 514, 486, 69}, 750));
            add(new TopTensModel("Bud Light", "04/25/15", 4.0, "ABC liquor", "Walmart", 1.3, 2.34, 8.02,
                    6.73, 1, true, "(6) bottle",  4.20, 8, new int[] {251, 496, 765, 49, 128}, 750));
            add(new TopTensModel("Moscato", "02/09/15", 3.5,"Publix liquor", 1.8, 11.46, 2, true, "fifth", 5.5, 11, new int[] {265, 468, 96, 135, 26}, 750));
            add(new TopTensModel("Fireball Whiskey", "02/26/15", 3.5, "ABC liquor", "Publix liquor", 1.3, 1.8, 16.02,
                    15.56, 3, true, "handle",  33, 66, new int[] {361, 496, 210, 46, 12}, 750));
            add(new TopTensModel("Wyborowa Wodka Rye Grain Polish Vodka", "07/25/15", 4.0, "ABC liquor", "Publix liquor", 1.3, 1.8, 23.61,
                    21.49, 3, true, "fifth",  40, 80, new int[] {465, 129, 76, 32, 15}, 750));
            add(new TopTensModel("Henninger", "08/11/15", 3.5, "Publix liquor", 1.8, 8.26, 1, true, "(6) bottle", 4.80, 10, new int[] {162, 168, 35, 12, 1}, 750));
            add(new TopTensModel("Domaine Gavoty Provence Rose", "02/26/15", 3.5, "ABC liquor", "Publix liquor", 1.3, 1.8, 29.99,
                    28.36, 2, true, "fifth",  13, 26, new int[] {612, 345, 61, 124, 10}, 750));
            add(new TopTensModel("Ciroc French Pineapple Vodka", "10/11/15", 3.5, "Publix liquor", 1.8, 22.99, 3, true, "fifth", 35, 70, new int[] {236, 61, 96, 23, 9}, 750));

            add(new TopTensModel("Skyy Vodka2", "12/02/14", 3.5, "ABC liquor","Publix liquor",  1.3, 1.80, 18.73,
                    17.00, 3, true, "fifth",  40, 80, new int[] {251, 196, 75, 49, 21}, 750));
            add(new TopTensModel("Miller Light", "06/11/15", 3.0, "Publix liquor", 1.8, 7.23, 1, true, "(6) bottle", 4.17, 8, new int[] {129, 62, 514, 486, 69}, 750));
            add(new TopTensModel("Bud Light", "04/25/15", 4.0, "ABC liquor", "Walmart", 1.3, 2.34, 8.02,
                    6.73, 1, true, "(6) bottle",  4.20, 8, new int[] {251, 496, 765, 49, 128}, 750));
            add(new TopTensModel("Moscato", "02/09/15", 3.5,"Publix liquor", 1.8, 11.46, 2, true, "fifth", 5.5, 11, new int[] {265, 468, 96, 135, 26}, 750));
            add(new TopTensModel("Fireball Whiskey", "02/26/15", 3.5, "ABC liquor", "Publix liquor", 1.3, 1.8, 16.02,
                    15.56, 3, true, "handle",  33, 66, new int[] {361, 496, 210, 46, 12}, 750));
            add(new TopTensModel("Wyborowa Wodka Rye Grain Polish Vodka", "07/25/15", 4.0, "ABC liquor", "Publix liquor", 1.3, 1.8, 23.61,
                    21.49, 3, true, "fifth",  40, 80, new int[] {465, 129, 76, 32, 15}, 750));
            add(new TopTensModel("Henninger", "08/11/15", 3.5, "Publix liquor", 1.8, 8.26, 1, true, "(6) bottle", 4.80, 10, new int[] {162, 168, 35, 12, 1}, 750));
            add(new TopTensModel("Domaine Gavoty Provence Rose", "02/26/15", 3.5, "ABC liquor", "Publix liquor", 1.3, 1.8, 29.99,
                    28.36, 2, true, "fifth",  13, 26, new int[] {612, 345, 61, 124, 10}, 750));
            add(new TopTensModel("Ciroc French Pineapple Vodka", "10/11/15", 3.5, "Publix liquor", 1.8, 22.99, 3, true, "fifth", 35, 70, new int[] {236, 61, 96, 23, 9}, 750));
            add(new TopTensModel("Skyy Vodk3a", "12/02/14", 3.5, "ABC liquor","Publix liquor",  1.3, 1.80, 18.73,
                    17.00, 3, true, "fifth",  40, 80, new int[] {251, 196, 75, 49, 21}, 750));
            add(new TopTensModel("Miller Light", "06/11/15", 3.0, "Publix liquor", 1.8, 7.23, 1, true, "(6) bottle", 4.17, 8, new int[] {129, 62, 514, 486, 69}, 750));
            add(new TopTensModel("Bud Light", "04/25/15", 4.0, "ABC liquor", "Walmart", 1.3, 2.34, 8.02,
                    6.73, 1, true, "(6) bottle",  4.20, 8, new int[] {251, 496, 765, 49, 128}, 750));
            add(new TopTensModel("Moscato", "02/09/15", 3.5,"Publix liquor", 1.8, 11.46, 2, true, "fifth", 5.5, 11, new int[] {265, 468, 96, 135, 26}, 750));
            add(new TopTensModel("Fireball Whiskey", "02/26/15", 3.5, "ABC liquor", "Publix liquor", 1.3, 1.8, 16.02,
                    15.56, 3, true, "handle",  33, 66, new int[] {361, 496, 210, 46, 12}, 750));
            add(new TopTensModel("Wyborowa Wodka Rye Grain Polish Vodka", "07/25/15", 4.0, "ABC liquor", "Publix liquor", 1.3, 1.8, 23.61,
                    21.49, 3, true, "fifth",  40, 80, new int[] {465, 129, 76, 32, 15}, 750));
            add(new TopTensModel("Henninger", "08/11/15", 3.5, "Publix liquor", 1.8, 8.26, 1, true, "(6) bottle", 4.80, 10, new int[] {162, 168, 35, 12, 1}, 750));
            add(new TopTensModel("Domaine Gavoty Provence Rose", "02/26/15", 3.5, "ABC liquor", "Publix liquor", 1.3, 1.8, 29.99,
                    28.36, 2, true, "fifth",  13, 26, new int[] {612, 345, 61, 124, 10}, 750));
            add(new TopTensModel("Ciroc French Pineapple Vodka", "10/11/15", 3.5, "Publix liquor", 1.8, 22.99, 3, true, "fifth", 35, 70, new int[] {236, 61, 96, 23, 9}, 750));
            add(new TopTensModel("Skyy Vodka4", "12/02/14", 3.5, "ABC liquor","Publix liquor",  1.3, 1.80, 18.73,
                    17.00, 3, true, "fifth",  40, 80, new int[] {251, 196, 75, 49, 21}, 750));
            add(new TopTensModel("Miller Light", "06/11/15", 3.0, "Publix liquor", 1.8, 7.23, 1, true, "(6) bottle", 4.17, 8, new int[] {129, 62, 514, 486, 69}, 750));
            add(new TopTensModel("Bud Light", "04/25/15", 4.0, "ABC liquor", "Walmart", 1.3, 2.34, 8.02,
                    6.73, 1, true, "(6) bottle",  4.20, 8, new int[] {251, 496, 765, 49, 128}, 750));
            add(new TopTensModel("Moscato", "02/09/15", 3.5,"Publix liquor", 1.8, 11.46, 2, true, "fifth", 5.5, 11, new int[] {265, 468, 96, 135, 26}, 750));
            add(new TopTensModel("Fireball Whiskey", "02/26/15", 3.5, "ABC liquor", "Publix liquor", 1.3, 1.8, 16.02,
                    15.56, 3, true, "handle",  33, 66, new int[] {361, 496, 210, 46, 12}, 750));
            add(new TopTensModel("Wyborowa Wodka Rye Grain Polish Vodka", "07/25/15", 4.0, "ABC liquor", "Publix liquor", 1.3, 1.8, 23.61,
                    21.49, 3, true, "fifth",  40, 80, new int[] {465, 129, 76, 32, 15}, 750));
            add(new TopTensModel("Henninger", "08/11/15", 3.5, "Publix liquor", 1.8, 8.26, 1, true, "(6) bottle", 4.80, 10, new int[] {162, 168, 35, 12, 1}, 750));
            add(new TopTensModel("Domaine Gavoty Provence Rose", "02/26/15", 3.5, "ABC liquor", "Publix liquor", 1.3, 1.8, 29.99,
                    28.36, 2, true, "fifth",  13, 26, new int[] {612, 345, 61, 124, 10}, 750));
            add(new TopTensModel("Ciroc French Pineapple Vodka", "10/11/15", 3.5, "Publix liquor", 1.8, 22.99, 3, true, "fifth", 35, 70, new int[] {236, 61, 96, 23, 9}, 750));
            add(new TopTensModel("Skyy Vodka5", "12/02/14", 3.5, "ABC liquor","Publix liquor",  1.3, 1.80, 18.73,
                    17.00, 3, true, "fifth",  40, 80, new int[] {251, 196, 75, 49, 21}, 750));
            add(new TopTensModel("Miller Light", "06/11/15", 3.0, "Publix liquor", 1.8, 7.23, 1, true, "(6) bottle", 4.17, 8, new int[] {129, 62, 514, 486, 69}, 750));
            add(new TopTensModel("Bud Light", "04/25/15", 4.0, "ABC liquor", "Walmart", 1.3, 2.34, 8.02,
                    6.73, 1, true, "(6) bottle",  4.20, 8, new int[] {251, 496, 765, 49, 128}, 750));
            add(new TopTensModel("Moscato", "02/09/15", 3.5,"Publix liquor", 1.8, 11.46, 2, true, "fifth", 5.5, 11, new int[] {265, 468, 96, 135, 26}, 750));
            add(new TopTensModel("Fireball Whiskey", "02/26/15", 3.5, "ABC liquor", "Publix liquor", 1.3, 1.8, 16.02,
                    15.56, 3, true, "handle",  33, 66, new int[] {361, 496, 210, 46, 12}, 750));
            add(new TopTensModel("Wyborowa Wodka Rye Grain Polish Vodka", "07/25/15", 4.0, "ABC liquor", "Publix liquor", 1.3, 1.8, 23.61,
                    21.49, 3, true, "fifth",  40, 80, new int[] {465, 129, 76, 32, 15}, 750));
            add(new TopTensModel("Henninger", "08/11/15", 3.5, "Publix liquor", 1.8, 8.26, 1, true, "(6) bottle", 4.80, 10, new int[] {162, 168, 35, 12, 1}, 750));
            add(new TopTensModel("Domaine Gavoty Provence Rose", "02/26/15", 3.5, "ABC liquor", "Publix liquor", 1.3, 1.8, 29.99,
                    28.36, 2, true, "fifth",  13, 26, new int[] {612, 345, 61, 124, 10}, 750));
            add(new TopTensModel("Ciroc French Pineapple Vodka", "10/11/15", 3.5, "Publix liquor", 1.8, 22.99, 3, true, "fifth", 35, 70, new int[]{236, 61, 96, 23, 9}, 750));
            add(new TopTensModel("Skyy Vodka6", "12/02/14", 3.5, "ABC liquor","Publix liquor",  1.3, 1.80, 18.73,
                    17.00, 3, true, "fifth",  40, 80, new int[] {251, 196, 75, 49, 21}, 750));
            add(new TopTensModel("Miller Light", "06/11/15", 3.0, "Publix liquor", 1.8, 7.23, 1, true, "(6) bottle", 4.17, 8, new int[] {129, 62, 514, 486, 69}, 750));
            add(new TopTensModel("Bud Light", "04/25/15", 4.0, "ABC liquor", "Walmart", 1.3, 2.34, 8.02,
                    6.73, 1, true, "(6) bottle",  4.20, 8, new int[] {251, 496, 765, 49, 128}, 750));
            add(new TopTensModel("Moscato", "02/09/15", 3.5,"Publix liquor", 1.8, 11.46, 2, true, "fifth", 5.5, 11, new int[] {265, 468, 96, 135, 26}, 750));
            add(new TopTensModel("Fireball Whiskey", "02/26/15", 3.5, "ABC liquor", "Publix liquor", 1.3, 1.8, 16.02,
                    15.56, 3, true, "handle",  33, 66, new int[] {361, 496, 210, 46, 12}, 750));
            add(new TopTensModel("Wyborowa Wodka Rye Grain Polish Vodka", "07/25/15", 4.0, "ABC liquor", "Publix liquor", 1.3, 1.8, 23.61,
                    21.49, 3, true, "fifth",  40, 80, new int[] {465, 129, 76, 32, 15}, 750));
            add(new TopTensModel("Henninger", "08/11/15", 3.5, "Publix liquor", 1.8, 8.26, 1, true, "(6) bottle", 4.80, 10, new int[] {162, 168, 35, 12, 1}, 750));
            add(new TopTensModel("Domaine Gavoty Provence Rose", "02/26/15", 3.5, "ABC liquor", "Publix liquor", 1.3, 1.8, 29.99,
                    28.36, 2, true, "fifth",  13, 26, new int[] {612, 345, 61, 124, 10}, 750));
            add(new TopTensModel("Ciroc French Pineapple Vodka", "10/11/15", 3.5, "Publix liquor", 1.8, 22.99, 3, true, "fifth", 35, 70, new int[] {236, 61, 96, 23, 9}, 750));
            add(new TopTensModel("Skyy Vodka7", "12/02/14", 3.5, "ABC liquor","Publix liquor",  1.3, 1.80, 18.73,
                    17.00, 3, true, "fifth",  40, 80, new int[] {251, 196, 75, 49, 21}, 750));
            add(new TopTensModel("Miller Light", "06/11/15", 3.0, "Publix liquor", 1.8, 7.23, 1, true, "(6) bottle", 4.17, 8, new int[] {129, 62, 514, 486, 69}, 750));
            add(new TopTensModel("Bud Light", "04/25/15", 4.0, "ABC liquor", "Walmart", 1.3, 2.34, 8.02,
                    6.73, 1, true, "(6) bottle",  4.20, 8, new int[] {251, 496, 765, 49, 128}, 750));
            add(new TopTensModel("Moscato", "02/09/15", 3.5,"Publix liquor", 1.8, 11.46, 2, true, "fifth", 5.5, 11, new int[] {265, 468, 96, 135, 26}, 750));
            add(new TopTensModel("Fireball Whiskey", "02/26/15", 3.5, "ABC liquor", "Publix liquor", 1.3, 1.8, 16.02,
                    15.56, 3, true, "handle",  33, 66, new int[] {361, 496, 210, 46, 12}, 750));
            add(new TopTensModel("Wyborowa Wodka Rye Grain Polish Vodka", "07/25/15", 4.0, "ABC liquor", "Publix liquor", 1.3, 1.8, 23.61,
                    21.49, 3, true, "fifth",  40, 80, new int[] {465, 129, 76, 32, 15}, 750));
            add(new TopTensModel("Henninger", "08/11/15", 3.5, "Publix liquor", 1.8, 8.26, 1, true, "(6) bottle", 4.80, 10, new int[] {162, 168, 35, 12, 1}, 750));
            add(new TopTensModel("Domaine Gavoty Provence Rose", "02/26/15", 3.5, "ABC liquor", "Publix liquor", 1.3, 1.8, 29.99,
                    28.36, 2, true, "fifth",  13, 26, new int[] {612, 345, 61, 124, 10}, 750));
            add(new TopTensModel("Ciroc French Pineapple Vodka", "10/11/15", 3.5, "Publix liquor", 1.8, 22.99, 3, true, "fifth", 35, 70, new int[] {236, 61, 96, 23, 9}, 750));
            add(new TopTensModel("Skyy Vodka8", "12/02/14", 3.5, "ABC liquor","Publix liquor",  1.3, 1.80, 18.73,
                    17.00, 3, true, "fifth",  40, 80, new int[] {251, 196, 75, 49, 21}, 750));
            add(new TopTensModel("Miller Light", "06/11/15", 3.0, "Publix liquor", 1.8, 7.23, 1, true, "(6) bottle", 4.17, 8, new int[] {129, 62, 514, 486, 69}, 750));
            add(new TopTensModel("Bud Light", "04/25/15", 4.0, "ABC liquor", "Walmart", 1.3, 2.34, 8.02,
                    6.73, 1, true, "(6) bottle",  4.20, 8, new int[] {251, 496, 765, 49, 128}, 750));
            add(new TopTensModel("Moscato", "02/09/15", 3.5,"Publix liquor", 1.8, 11.46, 2, true, "fifth", 5.5, 11, new int[] {265, 468, 96, 135, 26}, 750));
            add(new TopTensModel("Fireball Whiskey", "02/26/15", 3.5, "ABC liquor", "Publix liquor", 1.3, 1.8, 16.02,
                    15.56, 3, true, "handle",  33, 66, new int[] {361, 496, 210, 46, 12}, 750));
            add(new TopTensModel("Wyborowa Wodka Rye Grain Polish Vodka", "07/25/15", 4.0, "ABC liquor", "Publix liquor", 1.3, 1.8, 23.61,
                    21.49, 3, true, "fifth",  40, 80, new int[] {465, 129, 76, 32, 15}, 750));
            add(new TopTensModel("Henninger", "08/11/15", 3.5, "Publix liquor", 1.8, 8.26, 1, true, "(6) bottle", 4.80, 10, new int[] {162, 168, 35, 12, 1}, 750));
            add(new TopTensModel("Domaine Gavoty Provence Rose", "02/26/15", 3.5, "ABC liquor", "Publix liquor", 1.3, 1.8, 29.99,
                    28.36, 2, true, "fifth",  13, 26, new int[] {612, 345, 61, 124, 10}, 750));
            add(new TopTensModel("Ciroc French Pineapple Vodka", "10/11/15", 3.5, "Publix liquor", 1.8, 22.99, 3, true, "fifth", 35, 70, new int[] {236, 61, 96, 23, 9}, 750));
            add(new TopTensModel("Skyy Vodka9", "12/02/14", 3.5, "ABC liquor","Publix liquor",  1.3, 1.80, 18.73,
                    17.00, 3, true, "fifth",  40, 80, new int[] {251, 196, 75, 49, 21}, 750));
            add(new TopTensModel("Miller Light", "06/11/15", 3.0, "Publix liquor", 1.8, 7.23, 1, true, "(6) bottle", 4.17, 8, new int[] {129, 62, 514, 486, 69}, 750));
            add(new TopTensModel("Bud Light", "04/25/15", 4.0, "ABC liquor", "Walmart", 1.3, 2.34, 8.02,
                    6.73, 1, true, "(6) bottle",  4.20, 8, new int[] {251, 496, 765, 49, 128}, 750));
            add(new TopTensModel("Moscato", "02/09/15", 3.5,"Publix liquor", 1.8, 11.46, 2, true, "fifth", 5.5, 11, new int[] {265, 468, 96, 135, 26}, 750));
            add(new TopTensModel("Fireball Whiskey", "02/26/15", 3.5, "ABC liquor", "Publix liquor", 1.3, 1.8, 16.02,
                    15.56, 3, true, "handle",  33, 66, new int[] {361, 496, 210, 46, 12}, 750));
            add(new TopTensModel("Wyborowa Wodka Rye Grain Polish Vodka", "07/25/15", 4.0, "ABC liquor", "Publix liquor", 1.3, 1.8, 23.61,
                    21.49, 3, true, "fifth",  40, 80, new int[] {465, 129, 76, 32, 15}, 750));
            add(new TopTensModel("Henninger", "08/11/15", 3.5, "Publix liquor", 1.8, 8.26, 1, true, "(6) bottle", 4.80, 10, new int[] {162, 168, 35, 12, 1}, 750));
            add(new TopTensModel("Domaine Gavoty Provence Rose", "02/26/15", 3.5, "ABC liquor", "Publix liquor", 1.3, 1.8, 29.99,
                    28.36, 2, true, "fifth",  13, 26, new int[] {612, 345, 61, 124, 10}, 750));
            add(new TopTensModel("Ciroc French Pineapple Vodka", "10/11/15", 3.5, "Publix liquor", 1.8, 22.99, 3, true, "fifth", 35, 70, new int[] {236, 61, 96, 23, 9}, 750));
            add(new TopTensModel("Skyy Vodka10", "12/02/14", 3.5, "ABC liquor","Publix liquor",  1.3, 1.80, 18.73,
                    17.00, 3, true, "fifth",  40, 80, new int[] {251, 196, 75, 49, 21}, 750));
            add(new TopTensModel("Miller Light", "06/11/15", 3.0, "Publix liquor", 1.8, 7.23, 1, true, "(6) bottle", 4.17, 8, new int[] {129, 62, 514, 486, 69}, 750));
            add(new TopTensModel("Bud Light", "04/25/15", 4.0, "ABC liquor", "Walmart", 1.3, 2.34, 8.02,
                    6.73, 1, true, "(6) bottle",  4.20, 8, new int[] {251, 496, 765, 49, 128}, 750));
            add(new TopTensModel("Moscato", "02/09/15", 3.5,"Publix liquor", 1.8, 11.46, 2, true, "fifth", 5.5, 11, new int[] {265, 468, 96, 135, 26}, 750));
            add(new TopTensModel("Fireball Whiskey", "02/26/15", 3.5, "ABC liquor", "Publix liquor", 1.3, 1.8, 16.02,
                    15.56, 3, true, "handle",  33, 66, new int[] {361, 496, 210, 46, 12}, 750));
            add(new TopTensModel("Wyborowa Wodka Rye Grain Polish Vodka", "07/25/15", 4.0, "ABC liquor", "Publix liquor", 1.3, 1.8, 23.61,
                    21.49, 3, true, "fifth",  40, 80, new int[] {465, 129, 76, 32, 15}, 750));
            add(new TopTensModel("Henninger", "08/11/15", 3.5, "Publix liquor", 1.8, 8.26, 1, true, "(6) bottle", 4.80, 10, new int[] {162, 168, 35, 12, 1}, 750));
            add(new TopTensModel("Domaine Gavoty Provence Rose", "02/26/15", 3.5, "ABC liquor", "Publix liquor", 1.3, 1.8, 29.99,
                    28.36, 2, true, "fifth",  13, 26, new int[] {612, 345, 61, 124, 10}, 750));
            add(new TopTensModel("Ciroc French Pineapple Vodka", "10/11/15", 3.5, "Publix liquor", 1.8, 22.99, 3, true, "fifth", 35, 70, new int[] {236, 61, 96, 23, 9}, 750));
            add(new TopTensModel("Skyy Vodka", "12/02/14", 3.5, "ABC liquor","Publix liquor",  1.3, 1.80, 18.73,
                    17.00, 3, true, "fifth",  40, 80, new int[] {251, 196, 75, 49, 21}, 750));
            add(new TopTensModel("Miller Light", "06/11/15", 3.0, "Publix liquor", 1.8, 7.23, 1, true, "(6) bottle", 4.17, 8, new int[] {129, 62, 514, 486, 69}, 750));
            add(new TopTensModel("Bud Light", "04/25/15", 4.0, "ABC liquor", "Walmart", 1.3, 2.34, 8.02,
                    6.73, 1, true, "(6) bottle",  4.20, 8, new int[] {251, 496, 765, 49, 128}, 750));
            add(new TopTensModel("Moscato", "02/09/15", 3.5,"Publix liquor", 1.8, 11.46, 2, true, "fifth", 5.5, 11, new int[] {265, 468, 96, 135, 26}, 750));
            add(new TopTensModel("Fireball Whiskey", "02/26/15", 3.5, "ABC liquor", "Publix liquor", 1.3, 1.8, 16.02,
                    15.56, 3, true, "handle",  33, 66, new int[] {361, 496, 210, 46, 12}, 750));
            add(new TopTensModel("Wyborowa Wodka Rye Grain Polish Vodka", "07/25/15", 4.0, "ABC liquor", "Publix liquor", 1.3, 1.8, 23.61,
                    21.49, 3, true, "fifth",  40, 80, new int[] {465, 129, 76, 32, 15}, 750));
            add(new TopTensModel("Henninger", "08/11/15", 3.5, "Publix liquor", 1.8, 8.26, 1, true, "(6) bottle", 4.80, 10, new int[] {162, 168, 35, 12, 1}, 750));
            add(new TopTensModel("Domaine Gavoty Provence Rose", "02/26/15", 3.5, "ABC liquor", "Publix liquor", 1.3, 1.8, 29.99,
                    28.36, 2, true, "fifth",  13, 26, new int[] {612, 345, 61, 124, 10}, 750));
            add(new TopTensModel("Ciroc French Pineapple Vodka", "10/11/15", 3.5, "Publix liquor", 1.8, 22.99, 3, true, "fifth", 35, 70, new int[] {236, 61, 96, 23, 9}, 750));

            add(new TopTensModel("Skyy Vodka2", "12/02/14", 3.5, "ABC liquor","Publix liquor",  1.3, 1.80, 18.73,
                    17.00, 3, true, "fifth",  40, 80, new int[] {251, 196, 75, 49, 21}, 750));
            add(new TopTensModel("Miller Light", "06/11/15", 3.0, "Publix liquor", 1.8, 7.23, 1, true, "(6) bottle", 4.17, 8, new int[] {129, 62, 514, 486, 69}, 750));
            add(new TopTensModel("Bud Light", "04/25/15", 4.0, "ABC liquor", "Walmart", 1.3, 2.34, 8.02,
                    6.73, 1, true, "(6) bottle",  4.20, 8, new int[] {251, 496, 765, 49, 128}, 750));
            add(new TopTensModel("Moscato", "02/09/15", 3.5,"Publix liquor", 1.8, 11.46, 2, true, "fifth", 5.5, 11, new int[] {265, 468, 96, 135, 26}, 750));
            add(new TopTensModel("Fireball Whiskey", "02/26/15", 3.5, "ABC liquor", "Publix liquor", 1.3, 1.8, 16.02,
                    15.56, 3, true, "handle",  33, 66, new int[] {361, 496, 210, 46, 12}, 750));
            add(new TopTensModel("Wyborowa Wodka Rye Grain Polish Vodka", "07/25/15", 4.0, "ABC liquor", "Publix liquor", 1.3, 1.8, 23.61,
                    21.49, 3, true, "fifth",  40, 80, new int[] {465, 129, 76, 32, 15}, 750));
            add(new TopTensModel("Henninger", "08/11/15", 3.5, "Publix liquor", 1.8, 8.26, 1, true, "(6) bottle", 4.80, 10, new int[] {162, 168, 35, 12, 1}, 750));
            add(new TopTensModel("Domaine Gavoty Provence Rose", "02/26/15", 3.5, "ABC liquor", "Publix liquor", 1.3, 1.8, 29.99,
                    28.36, 2, true, "fifth",  13, 26, new int[] {612, 345, 61, 124, 10}, 750));
            add(new TopTensModel("Ciroc French Pineapple Vodka", "10/11/15", 3.5, "Publix liquor", 1.8, 22.99, 3, true, "fifth", 35, 70, new int[] {236, 61, 96, 23, 9}, 750));
            add(new TopTensModel("Skyy Vodk3a", "12/02/14", 3.5, "ABC liquor","Publix liquor",  1.3, 1.80, 18.73,
                    17.00, 3, true, "fifth",  40, 80, new int[] {251, 196, 75, 49, 21}, 750));
            add(new TopTensModel("Miller Light", "06/11/15", 3.0, "Publix liquor", 1.8, 7.23, 1, true, "(6) bottle", 4.17, 8, new int[] {129, 62, 514, 486, 69}, 750));
            add(new TopTensModel("Bud Light", "04/25/15", 4.0, "ABC liquor", "Walmart", 1.3, 2.34, 8.02,
                    6.73, 1, true, "(6) bottle",  4.20, 8, new int[] {251, 496, 765, 49, 128}, 750));
            add(new TopTensModel("Moscato", "02/09/15", 3.5,"Publix liquor", 1.8, 11.46, 2, true, "fifth", 5.5, 11, new int[] {265, 468, 96, 135, 26}, 750));
            add(new TopTensModel("Fireball Whiskey", "02/26/15", 3.5, "ABC liquor", "Publix liquor", 1.3, 1.8, 16.02,
                    15.56, 3, true, "handle",  33, 66, new int[] {361, 496, 210, 46, 12}, 750));
            add(new TopTensModel("Wyborowa Wodka Rye Grain Polish Vodka", "07/25/15", 4.0, "ABC liquor", "Publix liquor", 1.3, 1.8, 23.61,
                    21.49, 3, true, "fifth",  40, 80, new int[] {465, 129, 76, 32, 15}, 750));
            add(new TopTensModel("Henninger", "08/11/15", 3.5, "Publix liquor", 1.8, 8.26, 1, true, "(6) bottle", 4.80, 10, new int[] {162, 168, 35, 12, 1}, 750));
            add(new TopTensModel("Domaine Gavoty Provence Rose", "02/26/15", 3.5, "ABC liquor", "Publix liquor", 1.3, 1.8, 29.99,
                    28.36, 2, true, "fifth",  13, 26, new int[] {612, 345, 61, 124, 10}, 750));
            add(new TopTensModel("Ciroc French Pineapple Vodka", "10/11/15", 3.5, "Publix liquor", 1.8, 22.99, 3, true, "fifth", 35, 70, new int[] {236, 61, 96, 23, 9}, 750));
            add(new TopTensModel("Skyy Vodka4", "12/02/14", 3.5, "ABC liquor","Publix liquor",  1.3, 1.80, 18.73,
                    17.00, 3, true, "fifth",  40, 80, new int[] {251, 196, 75, 49, 21}, 750));
            add(new TopTensModel("Miller Light", "06/11/15", 3.0, "Publix liquor", 1.8, 7.23, 1, true, "(6) bottle", 4.17, 8, new int[] {129, 62, 514, 486, 69}, 750));
            add(new TopTensModel("Bud Light", "04/25/15", 4.0, "ABC liquor", "Walmart", 1.3, 2.34, 8.02,
                    6.73, 1, true, "(6) bottle",  4.20, 8, new int[] {251, 496, 765, 49, 128}, 750));
            add(new TopTensModel("Moscato", "02/09/15", 3.5,"Publix liquor", 1.8, 11.46, 2, true, "fifth", 5.5, 11, new int[] {265, 468, 96, 135, 26}, 750));
            add(new TopTensModel("Fireball Whiskey", "02/26/15", 3.5, "ABC liquor", "Publix liquor", 1.3, 1.8, 16.02,
                    15.56, 3, true, "handle",  33, 66, new int[] {361, 496, 210, 46, 12}, 750));
            add(new TopTensModel("Wyborowa Wodka Rye Grain Polish Vodka", "07/25/15", 4.0, "ABC liquor", "Publix liquor", 1.3, 1.8, 23.61,
                    21.49, 3, true, "fifth",  40, 80, new int[] {465, 129, 76, 32, 15}, 750));
            add(new TopTensModel("Henninger", "08/11/15", 3.5, "Publix liquor", 1.8, 8.26, 1, true, "(6) bottle", 4.80, 10, new int[] {162, 168, 35, 12, 1}, 750));
            add(new TopTensModel("Domaine Gavoty Provence Rose", "02/26/15", 3.5, "ABC liquor", "Publix liquor", 1.3, 1.8, 29.99,
                    28.36, 2, true, "fifth",  13, 26, new int[] {612, 345, 61, 124, 10}, 750));
            add(new TopTensModel("Ciroc French Pineapple Vodka", "10/11/15", 3.5, "Publix liquor", 1.8, 22.99, 3, true, "fifth", 35, 70, new int[] {236, 61, 96, 23, 9}, 750));
            add(new TopTensModel("Skyy Vodka5", "12/02/14", 3.5, "ABC liquor","Publix liquor",  1.3, 1.80, 18.73,
                    17.00, 3, true, "fifth",  40, 80, new int[] {251, 196, 75, 49, 21}, 750));
            add(new TopTensModel("Miller Light", "06/11/15", 3.0, "Publix liquor", 1.8, 7.23, 1, true, "(6) bottle", 4.17, 8, new int[] {129, 62, 514, 486, 69}, 750));
            add(new TopTensModel("Bud Light", "04/25/15", 4.0, "ABC liquor", "Walmart", 1.3, 2.34, 8.02,
                    6.73, 1, true, "(6) bottle",  4.20, 8, new int[] {251, 496, 765, 49, 128}, 750));
            add(new TopTensModel("Moscato", "02/09/15", 3.5,"Publix liquor", 1.8, 11.46, 2, true, "fifth", 5.5, 11, new int[] {265, 468, 96, 135, 26}, 750));
            add(new TopTensModel("Fireball Whiskey", "02/26/15", 3.5, "ABC liquor", "Publix liquor", 1.3, 1.8, 16.02,
                    15.56, 3, true, "handle",  33, 66, new int[] {361, 496, 210, 46, 12}, 750));
            add(new TopTensModel("Wyborowa Wodka Rye Grain Polish Vodka", "07/25/15", 4.0, "ABC liquor", "Publix liquor", 1.3, 1.8, 23.61,
                    21.49, 3, true, "fifth",  40, 80, new int[] {465, 129, 76, 32, 15}, 750));
            add(new TopTensModel("Henninger", "08/11/15", 3.5, "Publix liquor", 1.8, 8.26, 1, true, "(6) bottle", 4.80, 10, new int[] {162, 168, 35, 12, 1}, 750));
            add(new TopTensModel("Domaine Gavoty Provence Rose", "02/26/15", 3.5, "ABC liquor", "Publix liquor", 1.3, 1.8, 29.99,
                    28.36, 2, true, "fifth",  13, 26, new int[] {612, 345, 61, 124, 10}, 750));
            add(new TopTensModel("Ciroc French Pineapple Vodka", "10/11/15", 3.5, "Publix liquor", 1.8, 22.99, 3, true, "fifth", 35, 70, new int[]{236, 61, 96, 23, 9}, 750));
            add(new TopTensModel("Skyy Vodka6", "12/02/14", 3.5, "ABC liquor","Publix liquor",  1.3, 1.80, 18.73,
                    17.00, 3, true, "fifth",  40, 80, new int[] {251, 196, 75, 49, 21}, 750));
            add(new TopTensModel("Miller Light", "06/11/15", 3.0, "Publix liquor", 1.8, 7.23, 1, true, "(6) bottle", 4.17, 8, new int[] {129, 62, 514, 486, 69}, 750));
            add(new TopTensModel("Bud Light", "04/25/15", 4.0, "ABC liquor", "Walmart", 1.3, 2.34, 8.02,
                    6.73, 1, true, "(6) bottle",  4.20, 8, new int[] {251, 496, 765, 49, 128}, 750));
            add(new TopTensModel("Moscato", "02/09/15", 3.5,"Publix liquor", 1.8, 11.46, 2, true, "fifth", 5.5, 11, new int[] {265, 468, 96, 135, 26}, 750));
            add(new TopTensModel("Fireball Whiskey", "02/26/15", 3.5, "ABC liquor", "Publix liquor", 1.3, 1.8, 16.02,
                    15.56, 3, true, "handle",  33, 66, new int[] {361, 496, 210, 46, 12}, 750));
            add(new TopTensModel("Wyborowa Wodka Rye Grain Polish Vodka", "07/25/15", 4.0, "ABC liquor", "Publix liquor", 1.3, 1.8, 23.61,
                    21.49, 3, true, "fifth",  40, 80, new int[] {465, 129, 76, 32, 15}, 750));
            add(new TopTensModel("Henninger", "08/11/15", 3.5, "Publix liquor", 1.8, 8.26, 1, true, "(6) bottle", 4.80, 10, new int[] {162, 168, 35, 12, 1}, 750));
            add(new TopTensModel("Domaine Gavoty Provence Rose", "02/26/15", 3.5, "ABC liquor", "Publix liquor", 1.3, 1.8, 29.99,
                    28.36, 2, true, "fifth",  13, 26, new int[] {612, 345, 61, 124, 10}, 750));
            add(new TopTensModel("Ciroc French Pineapple Vodka", "10/11/15", 3.5, "Publix liquor", 1.8, 22.99, 3, true, "fifth", 35, 70, new int[] {236, 61, 96, 23, 9}, 750));
            add(new TopTensModel("Skyy Vodka7", "12/02/14", 3.5, "ABC liquor","Publix liquor",  1.3, 1.80, 18.73,
                    17.00, 3, true, "fifth",  40, 80, new int[] {251, 196, 75, 49, 21}, 750));
            add(new TopTensModel("Miller Light", "06/11/15", 3.0, "Publix liquor", 1.8, 7.23, 1, true, "(6) bottle", 4.17, 8, new int[] {129, 62, 514, 486, 69}, 750));
            add(new TopTensModel("Bud Light", "04/25/15", 4.0, "ABC liquor", "Walmart", 1.3, 2.34, 8.02,
                    6.73, 1, true, "(6) bottle",  4.20, 8, new int[] {251, 496, 765, 49, 128}, 750));
            add(new TopTensModel("Moscato", "02/09/15", 3.5,"Publix liquor", 1.8, 11.46, 2, true, "fifth", 5.5, 11, new int[] {265, 468, 96, 135, 26}, 750));
            add(new TopTensModel("Fireball Whiskey", "02/26/15", 3.5, "ABC liquor", "Publix liquor", 1.3, 1.8, 16.02,
                    15.56, 3, true, "handle",  33, 66, new int[] {361, 496, 210, 46, 12}, 750));
            add(new TopTensModel("Wyborowa Wodka Rye Grain Polish Vodka", "07/25/15", 4.0, "ABC liquor", "Publix liquor", 1.3, 1.8, 23.61,
                    21.49, 3, true, "fifth",  40, 80, new int[] {465, 129, 76, 32, 15}, 750));
            add(new TopTensModel("Henninger", "08/11/15", 3.5, "Publix liquor", 1.8, 8.26, 1, true, "(6) bottle", 4.80, 10, new int[] {162, 168, 35, 12, 1}, 750));
            add(new TopTensModel("Domaine Gavoty Provence Rose", "02/26/15", 3.5, "ABC liquor", "Publix liquor", 1.3, 1.8, 29.99,
                    28.36, 2, true, "fifth",  13, 26, new int[] {612, 345, 61, 124, 10}, 750));
            add(new TopTensModel("Ciroc French Pineapple Vodka", "10/11/15", 3.5, "Publix liquor", 1.8, 22.99, 3, true, "fifth", 35, 70, new int[] {236, 61, 96, 23, 9}, 750));
            add(new TopTensModel("Skyy Vodka8", "12/02/14", 3.5, "ABC liquor","Publix liquor",  1.3, 1.80, 18.73,
                    17.00, 3, true, "fifth",  40, 80, new int[] {251, 196, 75, 49, 21}, 750));
            add(new TopTensModel("Miller Light", "06/11/15", 3.0, "Publix liquor", 1.8, 7.23, 1, true, "(6) bottle", 4.17, 8, new int[] {129, 62, 514, 486, 69}, 750));
            add(new TopTensModel("Bud Light", "04/25/15", 4.0, "ABC liquor", "Walmart", 1.3, 2.34, 8.02,
                    6.73, 1, true, "(6) bottle",  4.20, 8, new int[] {251, 496, 765, 49, 128}, 750));
            add(new TopTensModel("Moscato", "02/09/15", 3.5,"Publix liquor", 1.8, 11.46, 2, true, "fifth", 5.5, 11, new int[] {265, 468, 96, 135, 26}, 750));
            add(new TopTensModel("Fireball Whiskey", "02/26/15", 3.5, "ABC liquor", "Publix liquor", 1.3, 1.8, 16.02,
                    15.56, 3, true, "handle",  33, 66, new int[] {361, 496, 210, 46, 12}, 750));
            add(new TopTensModel("Wyborowa Wodka Rye Grain Polish Vodka", "07/25/15", 4.0, "ABC liquor", "Publix liquor", 1.3, 1.8, 23.61,
                    21.49, 3, true, "fifth",  40, 80, new int[] {465, 129, 76, 32, 15}, 750));
            add(new TopTensModel("Henninger", "08/11/15", 3.5, "Publix liquor", 1.8, 8.26, 1, true, "(6) bottle", 4.80, 10, new int[] {162, 168, 35, 12, 1}, 750));
            add(new TopTensModel("Domaine Gavoty Provence Rose", "02/26/15", 3.5, "ABC liquor", "Publix liquor", 1.3, 1.8, 29.99,
                    28.36, 2, true, "fifth",  13, 26, new int[] {612, 345, 61, 124, 10}, 750));
            add(new TopTensModel("Ciroc French Pineapple Vodka", "10/11/15", 3.5, "Publix liquor", 1.8, 22.99, 3, true, "fifth", 35, 70, new int[] {236, 61, 96, 23, 9}, 750));
            add(new TopTensModel("Skyy Vodka9", "12/02/14", 3.5, "ABC liquor","Publix liquor",  1.3, 1.80, 18.73,
                    17.00, 3, true, "fifth",  40, 80, new int[] {251, 196, 75, 49, 21}, 750));
            add(new TopTensModel("Miller Light", "06/11/15", 3.0, "Publix liquor", 1.8, 7.23, 1, true, "(6) bottle", 4.17, 8, new int[] {129, 62, 514, 486, 69}, 750));
            add(new TopTensModel("Bud Light", "04/25/15", 4.0, "ABC liquor", "Walmart", 1.3, 2.34, 8.02,
                    6.73, 1, true, "(6) bottle",  4.20, 8, new int[] {251, 496, 765, 49, 128}, 750));
            add(new TopTensModel("Moscato", "02/09/15", 3.5,"Publix liquor", 1.8, 11.46, 2, true, "fifth", 5.5, 11, new int[] {265, 468, 96, 135, 26}, 750));
            add(new TopTensModel("Fireball Whiskey", "02/26/15", 3.5, "ABC liquor", "Publix liquor", 1.3, 1.8, 16.02,
                    15.56, 3, true, "handle",  33, 66, new int[] {361, 496, 210, 46, 12}, 750));
            add(new TopTensModel("Wyborowa Wodka Rye Grain Polish Vodka", "07/25/15", 4.0, "ABC liquor", "Publix liquor", 1.3, 1.8, 23.61,
                    21.49, 3, true, "fifth",  40, 80, new int[] {465, 129, 76, 32, 15}, 750));
            add(new TopTensModel("Henninger", "08/11/15", 3.5, "Publix liquor", 1.8, 8.26, 1, true, "(6) bottle", 4.80, 10, new int[] {162, 168, 35, 12, 1}, 750));
            add(new TopTensModel("Domaine Gavoty Provence Rose", "02/26/15", 3.5, "ABC liquor", "Publix liquor", 1.3, 1.8, 29.99,
                    28.36, 2, true, "fifth",  13, 26, new int[] {612, 345, 61, 124, 10}, 750));
            add(new TopTensModel("Ciroc French Pineapple Vodka", "10/11/15", 3.5, "Publix liquor", 1.8, 22.99, 3, true, "fifth", 35, 70, new int[] {236, 61, 96, 23, 9}, 750));
            add(new TopTensModel("Skyy Vodka10", "12/02/14", 3.5, "ABC liquor","Publix liquor",  1.3, 1.80, 18.73,
                    17.00, 3, true, "fifth",  40, 80, new int[] {251, 196, 75, 49, 21}, 750));
            add(new TopTensModel("Miller Light", "06/11/15", 3.0, "Publix liquor", 1.8, 7.23, 1, true, "(6) bottle", 4.17, 8, new int[] {129, 62, 514, 486, 69}, 750));
            add(new TopTensModel("Bud Light", "04/25/15", 4.0, "ABC liquor", "Walmart", 1.3, 2.34, 8.02,
                    6.73, 1, true, "(6) bottle",  4.20, 8, new int[] {251, 496, 765, 49, 128}, 750));
            add(new TopTensModel("Moscato", "02/09/15", 3.5,"Publix liquor", 1.8, 11.46, 2, true, "fifth", 5.5, 11, new int[] {265, 468, 96, 135, 26}, 750));
            add(new TopTensModel("Fireball Whiskey", "02/26/15", 3.5, "ABC liquor", "Publix liquor", 1.3, 1.8, 16.02,
                    15.56, 3, true, "handle",  33, 66, new int[] {361, 496, 210, 46, 12}, 750));
            add(new TopTensModel("Wyborowa Wodka Rye Grain Polish Vodka", "07/25/15", 4.0, "ABC liquor", "Publix liquor", 1.3, 1.8, 23.61,
                    21.49, 3, true, "fifth",  40, 80, new int[] {465, 129, 76, 32, 15}, 750));
            add(new TopTensModel("Henninger", "08/11/15", 3.5, "Publix liquor", 1.8, 8.26, 1, true, "(6) bottle", 4.80, 10, new int[] {162, 168, 35, 12, 1}, 750));
            add(new TopTensModel("Domaine Gavoty Provence Rose", "02/26/15", 3.5, "ABC liquor", "Publix liquor", 1.3, 1.8, 29.99,
                    28.36, 2, true, "fifth",  13, 26, new int[] {612, 345, 61, 124, 10}, 750));
            add(new TopTensModel("Ciroc French Pineapple Vodka", "10/11/15", 3.5, "Publix liquor", 1.8, 22.99, 3, true, "fifth", 35, 70, new int[] {236, 61, 96, 23, 9}, 750));
            add(new TopTensModel("Skyy Vodka", "12/02/14", 3.5, "ABC liquor","Publix liquor",  1.3, 1.80, 18.73,
                    17.00, 3, true, "fifth",  40, 80, new int[] {251, 196, 75, 49, 21}, 750));
            add(new TopTensModel("Miller Light", "06/11/15", 3.0, "Publix liquor", 1.8, 7.23, 1, true, "(6) bottle", 4.17, 8, new int[] {129, 62, 514, 486, 69}, 750));
            add(new TopTensModel("Bud Light", "04/25/15", 4.0, "ABC liquor", "Walmart", 1.3, 2.34, 8.02,
                    6.73, 1, true, "(6) bottle",  4.20, 8, new int[] {251, 496, 765, 49, 128}, 750));
            add(new TopTensModel("Moscato", "02/09/15", 3.5,"Publix liquor", 1.8, 11.46, 2, true, "fifth", 5.5, 11, new int[] {265, 468, 96, 135, 26}, 750));
            add(new TopTensModel("Fireball Whiskey", "02/26/15", 3.5, "ABC liquor", "Publix liquor", 1.3, 1.8, 16.02,
                    15.56, 3, true, "handle",  33, 66, new int[] {361, 496, 210, 46, 12}, 750));
            add(new TopTensModel("Wyborowa Wodka Rye Grain Polish Vodka", "07/25/15", 4.0, "ABC liquor", "Publix liquor", 1.3, 1.8, 23.61,
                    21.49, 3, true, "fifth",  40, 80, new int[] {465, 129, 76, 32, 15}, 750));
            add(new TopTensModel("Henninger", "08/11/15", 3.5, "Publix liquor", 1.8, 8.26, 1, true, "(6) bottle", 4.80, 10, new int[] {162, 168, 35, 12, 1}, 750));
            add(new TopTensModel("Domaine Gavoty Provence Rose", "02/26/15", 3.5, "ABC liquor", "Publix liquor", 1.3, 1.8, 29.99,
                    28.36, 2, true, "fifth",  13, 26, new int[] {612, 345, 61, 124, 10}, 750));
            add(new TopTensModel("Ciroc French Pineapple Vodka", "10/11/15", 3.5, "Publix liquor", 1.8, 22.99, 3, true, "fifth", 35, 70, new int[] {236, 61, 96, 23, 9}, 750));

            add(new TopTensModel("Skyy Vodka2", "12/02/14", 3.5, "ABC liquor","Publix liquor",  1.3, 1.80, 18.73,
                    17.00, 3, true, "fifth",  40, 80, new int[] {251, 196, 75, 49, 21}, 750));
            add(new TopTensModel("Miller Light", "06/11/15", 3.0, "Publix liquor", 1.8, 7.23, 1, true, "(6) bottle", 4.17, 8, new int[] {129, 62, 514, 486, 69}, 750));
            add(new TopTensModel("Bud Light", "04/25/15", 4.0, "ABC liquor", "Walmart", 1.3, 2.34, 8.02,
                    6.73, 1, true, "(6) bottle",  4.20, 8, new int[] {251, 496, 765, 49, 128}, 750));
            add(new TopTensModel("Moscato", "02/09/15", 3.5,"Publix liquor", 1.8, 11.46, 2, true, "fifth", 5.5, 11, new int[] {265, 468, 96, 135, 26}, 750));
            add(new TopTensModel("Fireball Whiskey", "02/26/15", 3.5, "ABC liquor", "Publix liquor", 1.3, 1.8, 16.02,
                    15.56, 3, true, "handle",  33, 66, new int[] {361, 496, 210, 46, 12}, 750));
            add(new TopTensModel("Wyborowa Wodka Rye Grain Polish Vodka", "07/25/15", 4.0, "ABC liquor", "Publix liquor", 1.3, 1.8, 23.61,
                    21.49, 3, true, "fifth",  40, 80, new int[] {465, 129, 76, 32, 15}, 750));
            add(new TopTensModel("Henninger", "08/11/15", 3.5, "Publix liquor", 1.8, 8.26, 1, true, "(6) bottle", 4.80, 10, new int[] {162, 168, 35, 12, 1}, 750));
            add(new TopTensModel("Domaine Gavoty Provence Rose", "02/26/15", 3.5, "ABC liquor", "Publix liquor", 1.3, 1.8, 29.99,
                    28.36, 2, true, "fifth",  13, 26, new int[] {612, 345, 61, 124, 10}, 750));
            add(new TopTensModel("Ciroc French Pineapple Vodka", "10/11/15", 3.5, "Publix liquor", 1.8, 22.99, 3, true, "fifth", 35, 70, new int[] {236, 61, 96, 23, 9}, 750));
            add(new TopTensModel("Skyy Vodk3a", "12/02/14", 3.5, "ABC liquor","Publix liquor",  1.3, 1.80, 18.73,
                    17.00, 3, true, "fifth",  40, 80, new int[] {251, 196, 75, 49, 21}, 750));
            add(new TopTensModel("Miller Light", "06/11/15", 3.0, "Publix liquor", 1.8, 7.23, 1, true, "(6) bottle", 4.17, 8, new int[] {129, 62, 514, 486, 69}, 750));
            add(new TopTensModel("Bud Light", "04/25/15", 4.0, "ABC liquor", "Walmart", 1.3, 2.34, 8.02,
                    6.73, 1, true, "(6) bottle",  4.20, 8, new int[] {251, 496, 765, 49, 128}, 750));
            add(new TopTensModel("Moscato", "02/09/15", 3.5,"Publix liquor", 1.8, 11.46, 2, true, "fifth", 5.5, 11, new int[] {265, 468, 96, 135, 26}, 750));
            add(new TopTensModel("Fireball Whiskey", "02/26/15", 3.5, "ABC liquor", "Publix liquor", 1.3, 1.8, 16.02,
                    15.56, 3, true, "handle",  33, 66, new int[] {361, 496, 210, 46, 12}, 750));
            add(new TopTensModel("Wyborowa Wodka Rye Grain Polish Vodka", "07/25/15", 4.0, "ABC liquor", "Publix liquor", 1.3, 1.8, 23.61,
                    21.49, 3, true, "fifth",  40, 80, new int[] {465, 129, 76, 32, 15}, 750));
            add(new TopTensModel("Henninger", "08/11/15", 3.5, "Publix liquor", 1.8, 8.26, 1, true, "(6) bottle", 4.80, 10, new int[] {162, 168, 35, 12, 1}, 750));
            add(new TopTensModel("Domaine Gavoty Provence Rose", "02/26/15", 3.5, "ABC liquor", "Publix liquor", 1.3, 1.8, 29.99,
                    28.36, 2, true, "fifth",  13, 26, new int[] {612, 345, 61, 124, 10}, 750));
            add(new TopTensModel("Ciroc French Pineapple Vodka", "10/11/15", 3.5, "Publix liquor", 1.8, 22.99, 3, true, "fifth", 35, 70, new int[] {236, 61, 96, 23, 9}, 750));
            add(new TopTensModel("Skyy Vodka4", "12/02/14", 3.5, "ABC liquor","Publix liquor",  1.3, 1.80, 18.73,
                    17.00, 3, true, "fifth",  40, 80, new int[] {251, 196, 75, 49, 21}, 750));
            add(new TopTensModel("Miller Light", "06/11/15", 3.0, "Publix liquor", 1.8, 7.23, 1, true, "(6) bottle", 4.17, 8, new int[] {129, 62, 514, 486, 69}, 750));
            add(new TopTensModel("Bud Light", "04/25/15", 4.0, "ABC liquor", "Walmart", 1.3, 2.34, 8.02,
                    6.73, 1, true, "(6) bottle",  4.20, 8, new int[] {251, 496, 765, 49, 128}, 750));
            add(new TopTensModel("Moscato", "02/09/15", 3.5,"Publix liquor", 1.8, 11.46, 2, true, "fifth", 5.5, 11, new int[] {265, 468, 96, 135, 26}, 750));
            add(new TopTensModel("Fireball Whiskey", "02/26/15", 3.5, "ABC liquor", "Publix liquor", 1.3, 1.8, 16.02,
                    15.56, 3, true, "handle",  33, 66, new int[] {361, 496, 210, 46, 12}, 750));
            add(new TopTensModel("Wyborowa Wodka Rye Grain Polish Vodka", "07/25/15", 4.0, "ABC liquor", "Publix liquor", 1.3, 1.8, 23.61,
                    21.49, 3, true, "fifth",  40, 80, new int[] {465, 129, 76, 32, 15}, 750));
            add(new TopTensModel("Henninger", "08/11/15", 3.5, "Publix liquor", 1.8, 8.26, 1, true, "(6) bottle", 4.80, 10, new int[] {162, 168, 35, 12, 1}, 750));
            add(new TopTensModel("Domaine Gavoty Provence Rose", "02/26/15", 3.5, "ABC liquor", "Publix liquor", 1.3, 1.8, 29.99,
                    28.36, 2, true, "fifth",  13, 26, new int[] {612, 345, 61, 124, 10}, 750));
            add(new TopTensModel("Ciroc French Pineapple Vodka", "10/11/15", 3.5, "Publix liquor", 1.8, 22.99, 3, true, "fifth", 35, 70, new int[] {236, 61, 96, 23, 9}, 750));
            add(new TopTensModel("Skyy Vodka5", "12/02/14", 3.5, "ABC liquor","Publix liquor",  1.3, 1.80, 18.73,
                    17.00, 3, true, "fifth",  40, 80, new int[] {251, 196, 75, 49, 21}, 750));
            add(new TopTensModel("Miller Light", "06/11/15", 3.0, "Publix liquor", 1.8, 7.23, 1, true, "(6) bottle", 4.17, 8, new int[] {129, 62, 514, 486, 69}, 750));
            add(new TopTensModel("Bud Light", "04/25/15", 4.0, "ABC liquor", "Walmart", 1.3, 2.34, 8.02,
                    6.73, 1, true, "(6) bottle",  4.20, 8, new int[] {251, 496, 765, 49, 128}, 750));
            add(new TopTensModel("Moscato", "02/09/15", 3.5,"Publix liquor", 1.8, 11.46, 2, true, "fifth", 5.5, 11, new int[] {265, 468, 96, 135, 26}, 750));
            add(new TopTensModel("Fireball Whiskey", "02/26/15", 3.5, "ABC liquor", "Publix liquor", 1.3, 1.8, 16.02,
                    15.56, 3, true, "handle",  33, 66, new int[] {361, 496, 210, 46, 12}, 750));
            add(new TopTensModel("Wyborowa Wodka Rye Grain Polish Vodka", "07/25/15", 4.0, "ABC liquor", "Publix liquor", 1.3, 1.8, 23.61,
                    21.49, 3, true, "fifth",  40, 80, new int[] {465, 129, 76, 32, 15}, 750));
            add(new TopTensModel("Henninger", "08/11/15", 3.5, "Publix liquor", 1.8, 8.26, 1, true, "(6) bottle", 4.80, 10, new int[] {162, 168, 35, 12, 1}, 750));
            add(new TopTensModel("Domaine Gavoty Provence Rose", "02/26/15", 3.5, "ABC liquor", "Publix liquor", 1.3, 1.8, 29.99,
                    28.36, 2, true, "fifth",  13, 26, new int[] {612, 345, 61, 124, 10}, 750));
            add(new TopTensModel("Ciroc French Pineapple Vodka", "10/11/15", 3.5, "Publix liquor", 1.8, 22.99, 3, true, "fifth", 35, 70, new int[]{236, 61, 96, 23, 9}, 750));
            add(new TopTensModel("Skyy Vodka6", "12/02/14", 3.5, "ABC liquor","Publix liquor",  1.3, 1.80, 18.73,
                    17.00, 3, true, "fifth",  40, 80, new int[] {251, 196, 75, 49, 21}, 750));
            add(new TopTensModel("Miller Light", "06/11/15", 3.0, "Publix liquor", 1.8, 7.23, 1, true, "(6) bottle", 4.17, 8, new int[] {129, 62, 514, 486, 69}, 750));
            add(new TopTensModel("Bud Light", "04/25/15", 4.0, "ABC liquor", "Walmart", 1.3, 2.34, 8.02,
                    6.73, 1, true, "(6) bottle",  4.20, 8, new int[] {251, 496, 765, 49, 128}, 750));
            add(new TopTensModel("Moscato", "02/09/15", 3.5,"Publix liquor", 1.8, 11.46, 2, true, "fifth", 5.5, 11, new int[] {265, 468, 96, 135, 26}, 750));
            add(new TopTensModel("Fireball Whiskey", "02/26/15", 3.5, "ABC liquor", "Publix liquor", 1.3, 1.8, 16.02,
                    15.56, 3, true, "handle",  33, 66, new int[] {361, 496, 210, 46, 12}, 750));
            add(new TopTensModel("Wyborowa Wodka Rye Grain Polish Vodka", "07/25/15", 4.0, "ABC liquor", "Publix liquor", 1.3, 1.8, 23.61,
                    21.49, 3, true, "fifth",  40, 80, new int[] {465, 129, 76, 32, 15}, 750));
            add(new TopTensModel("Henninger", "08/11/15", 3.5, "Publix liquor", 1.8, 8.26, 1, true, "(6) bottle", 4.80, 10, new int[] {162, 168, 35, 12, 1}, 750));
            add(new TopTensModel("Domaine Gavoty Provence Rose", "02/26/15", 3.5, "ABC liquor", "Publix liquor", 1.3, 1.8, 29.99,
                    28.36, 2, true, "fifth",  13, 26, new int[] {612, 345, 61, 124, 10}, 750));
            add(new TopTensModel("Ciroc French Pineapple Vodka", "10/11/15", 3.5, "Publix liquor", 1.8, 22.99, 3, true, "fifth", 35, 70, new int[] {236, 61, 96, 23, 9}, 750));
            add(new TopTensModel("Skyy Vodka7", "12/02/14", 3.5, "ABC liquor","Publix liquor",  1.3, 1.80, 18.73,
                    17.00, 3, true, "fifth",  40, 80, new int[] {251, 196, 75, 49, 21}, 750));
            add(new TopTensModel("Miller Light", "06/11/15", 3.0, "Publix liquor", 1.8, 7.23, 1, true, "(6) bottle", 4.17, 8, new int[] {129, 62, 514, 486, 69}, 750));
            add(new TopTensModel("Bud Light", "04/25/15", 4.0, "ABC liquor", "Walmart", 1.3, 2.34, 8.02,
                    6.73, 1, true, "(6) bottle",  4.20, 8, new int[] {251, 496, 765, 49, 128}, 750));
            add(new TopTensModel("Moscato", "02/09/15", 3.5,"Publix liquor", 1.8, 11.46, 2, true, "fifth", 5.5, 11, new int[] {265, 468, 96, 135, 26}, 750));
            add(new TopTensModel("Fireball Whiskey", "02/26/15", 3.5, "ABC liquor", "Publix liquor", 1.3, 1.8, 16.02,
                    15.56, 3, true, "handle",  33, 66, new int[] {361, 496, 210, 46, 12}, 750));
            add(new TopTensModel("Wyborowa Wodka Rye Grain Polish Vodka", "07/25/15", 4.0, "ABC liquor", "Publix liquor", 1.3, 1.8, 23.61,
                    21.49, 3, true, "fifth",  40, 80, new int[] {465, 129, 76, 32, 15}, 750));
            add(new TopTensModel("Henninger", "08/11/15", 3.5, "Publix liquor", 1.8, 8.26, 1, true, "(6) bottle", 4.80, 10, new int[] {162, 168, 35, 12, 1}, 750));
            add(new TopTensModel("Domaine Gavoty Provence Rose", "02/26/15", 3.5, "ABC liquor", "Publix liquor", 1.3, 1.8, 29.99,
                    28.36, 2, true, "fifth",  13, 26, new int[] {612, 345, 61, 124, 10}, 750));
            add(new TopTensModel("Ciroc French Pineapple Vodka", "10/11/15", 3.5, "Publix liquor", 1.8, 22.99, 3, true, "fifth", 35, 70, new int[] {236, 61, 96, 23, 9}, 750));
            add(new TopTensModel("Skyy Vodka8", "12/02/14", 3.5, "ABC liquor","Publix liquor",  1.3, 1.80, 18.73,
                    17.00, 3, true, "fifth",  40, 80, new int[] {251, 196, 75, 49, 21}, 750));
            add(new TopTensModel("Miller Light", "06/11/15", 3.0, "Publix liquor", 1.8, 7.23, 1, true, "(6) bottle", 4.17, 8, new int[] {129, 62, 514, 486, 69}, 750));
            add(new TopTensModel("Bud Light", "04/25/15", 4.0, "ABC liquor", "Walmart", 1.3, 2.34, 8.02,
                    6.73, 1, true, "(6) bottle",  4.20, 8, new int[] {251, 496, 765, 49, 128}, 750));
            add(new TopTensModel("Moscato", "02/09/15", 3.5,"Publix liquor", 1.8, 11.46, 2, true, "fifth", 5.5, 11, new int[] {265, 468, 96, 135, 26}, 750));
            add(new TopTensModel("Fireball Whiskey", "02/26/15", 3.5, "ABC liquor", "Publix liquor", 1.3, 1.8, 16.02,
                    15.56, 3, true, "handle",  33, 66, new int[] {361, 496, 210, 46, 12}, 750));
            add(new TopTensModel("Wyborowa Wodka Rye Grain Polish Vodka", "07/25/15", 4.0, "ABC liquor", "Publix liquor", 1.3, 1.8, 23.61,
                    21.49, 3, true, "fifth",  40, 80, new int[] {465, 129, 76, 32, 15}, 750));
            add(new TopTensModel("Henninger", "08/11/15", 3.5, "Publix liquor", 1.8, 8.26, 1, true, "(6) bottle", 4.80, 10, new int[] {162, 168, 35, 12, 1}, 750));
            add(new TopTensModel("Domaine Gavoty Provence Rose", "02/26/15", 3.5, "ABC liquor", "Publix liquor", 1.3, 1.8, 29.99,
                    28.36, 2, true, "fifth",  13, 26, new int[] {612, 345, 61, 124, 10}, 750));
            add(new TopTensModel("Ciroc French Pineapple Vodka", "10/11/15", 3.5, "Publix liquor", 1.8, 22.99, 3, true, "fifth", 35, 70, new int[] {236, 61, 96, 23, 9}, 750));
            add(new TopTensModel("Skyy Vodka9", "12/02/14", 3.5, "ABC liquor","Publix liquor",  1.3, 1.80, 18.73,
                    17.00, 3, true, "fifth",  40, 80, new int[] {251, 196, 75, 49, 21}, 750));
            add(new TopTensModel("Miller Light", "06/11/15", 3.0, "Publix liquor", 1.8, 7.23, 1, true, "(6) bottle", 4.17, 8, new int[] {129, 62, 514, 486, 69}, 750));
            add(new TopTensModel("Bud Light", "04/25/15", 4.0, "ABC liquor", "Walmart", 1.3, 2.34, 8.02,
                    6.73, 1, true, "(6) bottle",  4.20, 8, new int[] {251, 496, 765, 49, 128}, 750));
            add(new TopTensModel("Moscato", "02/09/15", 3.5,"Publix liquor", 1.8, 11.46, 2, true, "fifth", 5.5, 11, new int[] {265, 468, 96, 135, 26}, 750));
            add(new TopTensModel("Fireball Whiskey", "02/26/15", 3.5, "ABC liquor", "Publix liquor", 1.3, 1.8, 16.02,
                    15.56, 3, true, "handle",  33, 66, new int[] {361, 496, 210, 46, 12}, 750));
            add(new TopTensModel("Wyborowa Wodka Rye Grain Polish Vodka", "07/25/15", 4.0, "ABC liquor", "Publix liquor", 1.3, 1.8, 23.61,
                    21.49, 3, true, "fifth",  40, 80, new int[] {465, 129, 76, 32, 15}, 750));
            add(new TopTensModel("Henninger", "08/11/15", 3.5, "Publix liquor", 1.8, 8.26, 1, true, "(6) bottle", 4.80, 10, new int[] {162, 168, 35, 12, 1}, 750));
            add(new TopTensModel("Domaine Gavoty Provence Rose", "02/26/15", 3.5, "ABC liquor", "Publix liquor", 1.3, 1.8, 29.99,
                    28.36, 2, true, "fifth",  13, 26, new int[] {612, 345, 61, 124, 10}, 750));
            add(new TopTensModel("Ciroc French Pineapple Vodka", "10/11/15", 3.5, "Publix liquor", 1.8, 22.99, 3, true, "fifth", 35, 70, new int[] {236, 61, 96, 23, 9}, 750));
            add(new TopTensModel("Skyy Vodka10", "12/02/14", 3.5, "ABC liquor","Publix liquor",  1.3, 1.80, 18.73,
                    17.00, 3, true, "fifth",  40, 80, new int[] {251, 196, 75, 49, 21}, 750));
            add(new TopTensModel("Miller Light", "06/11/15", 3.0, "Publix liquor", 1.8, 7.23, 1, true, "(6) bottle", 4.17, 8, new int[] {129, 62, 514, 486, 69}, 750));
            add(new TopTensModel("Bud Light", "04/25/15", 4.0, "ABC liquor", "Walmart", 1.3, 2.34, 8.02,
                    6.73, 1, true, "(6) bottle",  4.20, 8, new int[] {251, 496, 765, 49, 128}, 750));
            add(new TopTensModel("Moscato", "02/09/15", 3.5,"Publix liquor", 1.8, 11.46, 2, true, "fifth", 5.5, 11, new int[] {265, 468, 96, 135, 26}, 750));
            add(new TopTensModel("Fireball Whiskey", "02/26/15", 3.5, "ABC liquor", "Publix liquor", 1.3, 1.8, 16.02,
                    15.56, 3, true, "handle",  33, 66, new int[] {361, 496, 210, 46, 12}, 750));
            add(new TopTensModel("Wyborowa Wodka Rye Grain Polish Vodka", "07/25/15", 4.0, "ABC liquor", "Publix liquor", 1.3, 1.8, 23.61,
                    21.49, 3, true, "fifth",  40, 80, new int[] {465, 129, 76, 32, 15}, 750));
            add(new TopTensModel("Henninger", "08/11/15", 3.5, "Publix liquor", 1.8, 8.26, 1, true, "(6) bottle", 4.80, 10, new int[] {162, 168, 35, 12, 1}, 750));
            add(new TopTensModel("Domaine Gavoty Provence Rose", "02/26/15", 3.5, "ABC liquor", "Publix liquor", 1.3, 1.8, 29.99,
                    28.36, 2, true, "fifth",  13, 26, new int[] {612, 345, 61, 124, 10}, 750));
            add(new TopTensModel("Ciroc French Pineapple Vodka", "10/11/15", 3.5, "Publix liquor", 1.8, 22.99, 3, true, "fifth", 35, 70, new int[] {236, 61, 96, 23, 9}, 750));
            add(new TopTensModel("Skyy Vodka", "12/02/14", 3.5, "ABC liquor","Publix liquor",  1.3, 1.80, 18.73,
                    17.00, 3, true, "fifth",  40, 80, new int[] {251, 196, 75, 49, 21}, 750));
            add(new TopTensModel("Miller Light", "06/11/15", 3.0, "Publix liquor", 1.8, 7.23, 1, true, "(6) bottle", 4.17, 8, new int[] {129, 62, 514, 486, 69}, 750));
            add(new TopTensModel("Bud Light", "04/25/15", 4.0, "ABC liquor", "Walmart", 1.3, 2.34, 8.02,
                    6.73, 1, true, "(6) bottle",  4.20, 8, new int[] {251, 496, 765, 49, 128}, 750));
            add(new TopTensModel("Moscato", "02/09/15", 3.5,"Publix liquor", 1.8, 11.46, 2, true, "fifth", 5.5, 11, new int[] {265, 468, 96, 135, 26}, 750));
            add(new TopTensModel("Fireball Whiskey", "02/26/15", 3.5, "ABC liquor", "Publix liquor", 1.3, 1.8, 16.02,
                    15.56, 3, true, "handle",  33, 66, new int[] {361, 496, 210, 46, 12}, 750));
            add(new TopTensModel("Wyborowa Wodka Rye Grain Polish Vodka", "07/25/15", 4.0, "ABC liquor", "Publix liquor", 1.3, 1.8, 23.61,
                    21.49, 3, true, "fifth",  40, 80, new int[] {465, 129, 76, 32, 15}, 750));
            add(new TopTensModel("Henninger", "08/11/15", 3.5, "Publix liquor", 1.8, 8.26, 1, true, "(6) bottle", 4.80, 10, new int[] {162, 168, 35, 12, 1}, 750));
            add(new TopTensModel("Domaine Gavoty Provence Rose", "02/26/15", 3.5, "ABC liquor", "Publix liquor", 1.3, 1.8, 29.99,
                    28.36, 2, true, "fifth",  13, 26, new int[] {612, 345, 61, 124, 10}, 750));
            add(new TopTensModel("Ciroc French Pineapple Vodka", "10/11/15", 3.5, "Publix liquor", 1.8, 22.99, 3, true, "fifth", 35, 70, new int[] {236, 61, 96, 23, 9}, 750));

            add(new TopTensModel("Skyy Vodka2", "12/02/14", 3.5, "ABC liquor","Publix liquor",  1.3, 1.80, 18.73,
                    17.00, 3, true, "fifth",  40, 80, new int[] {251, 196, 75, 49, 21}, 750));
            add(new TopTensModel("Miller Light", "06/11/15", 3.0, "Publix liquor", 1.8, 7.23, 1, true, "(6) bottle", 4.17, 8, new int[] {129, 62, 514, 486, 69}, 750));
            add(new TopTensModel("Bud Light", "04/25/15", 4.0, "ABC liquor", "Walmart", 1.3, 2.34, 8.02,
                    6.73, 1, true, "(6) bottle",  4.20, 8, new int[] {251, 496, 765, 49, 128}, 750));
            add(new TopTensModel("Moscato", "02/09/15", 3.5,"Publix liquor", 1.8, 11.46, 2, true, "fifth", 5.5, 11, new int[] {265, 468, 96, 135, 26}, 750));
            add(new TopTensModel("Fireball Whiskey", "02/26/15", 3.5, "ABC liquor", "Publix liquor", 1.3, 1.8, 16.02,
                    15.56, 3, true, "handle",  33, 66, new int[] {361, 496, 210, 46, 12}, 750));
            add(new TopTensModel("Wyborowa Wodka Rye Grain Polish Vodka", "07/25/15", 4.0, "ABC liquor", "Publix liquor", 1.3, 1.8, 23.61,
                    21.49, 3, true, "fifth",  40, 80, new int[] {465, 129, 76, 32, 15}, 750));
            add(new TopTensModel("Henninger", "08/11/15", 3.5, "Publix liquor", 1.8, 8.26, 1, true, "(6) bottle", 4.80, 10, new int[] {162, 168, 35, 12, 1}, 750));
            add(new TopTensModel("Domaine Gavoty Provence Rose", "02/26/15", 3.5, "ABC liquor", "Publix liquor", 1.3, 1.8, 29.99,
                    28.36, 2, true, "fifth",  13, 26, new int[] {612, 345, 61, 124, 10}, 750));
            add(new TopTensModel("Ciroc French Pineapple Vodka", "10/11/15", 3.5, "Publix liquor", 1.8, 22.99, 3, true, "fifth", 35, 70, new int[] {236, 61, 96, 23, 9}, 750));
            add(new TopTensModel("Skyy Vodk3a", "12/02/14", 3.5, "ABC liquor","Publix liquor",  1.3, 1.80, 18.73,
                    17.00, 3, true, "fifth",  40, 80, new int[] {251, 196, 75, 49, 21}, 750));
            add(new TopTensModel("Miller Light", "06/11/15", 3.0, "Publix liquor", 1.8, 7.23, 1, true, "(6) bottle", 4.17, 8, new int[] {129, 62, 514, 486, 69}, 750));
            add(new TopTensModel("Bud Light", "04/25/15", 4.0, "ABC liquor", "Walmart", 1.3, 2.34, 8.02,
                    6.73, 1, true, "(6) bottle",  4.20, 8, new int[] {251, 496, 765, 49, 128}, 750));
            add(new TopTensModel("Moscato", "02/09/15", 3.5,"Publix liquor", 1.8, 11.46, 2, true, "fifth", 5.5, 11, new int[] {265, 468, 96, 135, 26}, 750));
            add(new TopTensModel("Fireball Whiskey", "02/26/15", 3.5, "ABC liquor", "Publix liquor", 1.3, 1.8, 16.02,
                    15.56, 3, true, "handle",  33, 66, new int[] {361, 496, 210, 46, 12}, 750));
            add(new TopTensModel("Wyborowa Wodka Rye Grain Polish Vodka", "07/25/15", 4.0, "ABC liquor", "Publix liquor", 1.3, 1.8, 23.61,
                    21.49, 3, true, "fifth",  40, 80, new int[] {465, 129, 76, 32, 15}, 750));
            add(new TopTensModel("Henninger", "08/11/15", 3.5, "Publix liquor", 1.8, 8.26, 1, true, "(6) bottle", 4.80, 10, new int[] {162, 168, 35, 12, 1}, 750));
            add(new TopTensModel("Domaine Gavoty Provence Rose", "02/26/15", 3.5, "ABC liquor", "Publix liquor", 1.3, 1.8, 29.99,
                    28.36, 2, true, "fifth",  13, 26, new int[] {612, 345, 61, 124, 10}, 750));
            add(new TopTensModel("Ciroc French Pineapple Vodka", "10/11/15", 3.5, "Publix liquor", 1.8, 22.99, 3, true, "fifth", 35, 70, new int[] {236, 61, 96, 23, 9}, 750));
            add(new TopTensModel("Skyy Vodka4", "12/02/14", 3.5, "ABC liquor","Publix liquor",  1.3, 1.80, 18.73,
                    17.00, 3, true, "fifth",  40, 80, new int[] {251, 196, 75, 49, 21}, 750));
            add(new TopTensModel("Miller Light", "06/11/15", 3.0, "Publix liquor", 1.8, 7.23, 1, true, "(6) bottle", 4.17, 8, new int[] {129, 62, 514, 486, 69}, 750));
            add(new TopTensModel("Bud Light", "04/25/15", 4.0, "ABC liquor", "Walmart", 1.3, 2.34, 8.02,
                    6.73, 1, true, "(6) bottle",  4.20, 8, new int[] {251, 496, 765, 49, 128}, 750));
            add(new TopTensModel("Moscato", "02/09/15", 3.5,"Publix liquor", 1.8, 11.46, 2, true, "fifth", 5.5, 11, new int[] {265, 468, 96, 135, 26}, 750));
            add(new TopTensModel("Fireball Whiskey", "02/26/15", 3.5, "ABC liquor", "Publix liquor", 1.3, 1.8, 16.02,
                    15.56, 3, true, "handle",  33, 66, new int[] {361, 496, 210, 46, 12}, 750));
            add(new TopTensModel("Wyborowa Wodka Rye Grain Polish Vodka", "07/25/15", 4.0, "ABC liquor", "Publix liquor", 1.3, 1.8, 23.61,
                    21.49, 3, true, "fifth",  40, 80, new int[] {465, 129, 76, 32, 15}, 750));
            add(new TopTensModel("Henninger", "08/11/15", 3.5, "Publix liquor", 1.8, 8.26, 1, true, "(6) bottle", 4.80, 10, new int[] {162, 168, 35, 12, 1}, 750));
            add(new TopTensModel("Domaine Gavoty Provence Rose", "02/26/15", 3.5, "ABC liquor", "Publix liquor", 1.3, 1.8, 29.99,
                    28.36, 2, true, "fifth",  13, 26, new int[] {612, 345, 61, 124, 10}, 750));
            add(new TopTensModel("Ciroc French Pineapple Vodka", "10/11/15", 3.5, "Publix liquor", 1.8, 22.99, 3, true, "fifth", 35, 70, new int[] {236, 61, 96, 23, 9}, 750));
            add(new TopTensModel("Skyy Vodka5", "12/02/14", 3.5, "ABC liquor","Publix liquor",  1.3, 1.80, 18.73,
                    17.00, 3, true, "fifth",  40, 80, new int[] {251, 196, 75, 49, 21}, 750));
            add(new TopTensModel("Miller Light", "06/11/15", 3.0, "Publix liquor", 1.8, 7.23, 1, true, "(6) bottle", 4.17, 8, new int[] {129, 62, 514, 486, 69}, 750));
            add(new TopTensModel("Bud Light", "04/25/15", 4.0, "ABC liquor", "Walmart", 1.3, 2.34, 8.02,
                    6.73, 1, true, "(6) bottle",  4.20, 8, new int[] {251, 496, 765, 49, 128}, 750));
            add(new TopTensModel("Moscato", "02/09/15", 3.5,"Publix liquor", 1.8, 11.46, 2, true, "fifth", 5.5, 11, new int[] {265, 468, 96, 135, 26}, 750));
            add(new TopTensModel("Fireball Whiskey", "02/26/15", 3.5, "ABC liquor", "Publix liquor", 1.3, 1.8, 16.02,
                    15.56, 3, true, "handle",  33, 66, new int[] {361, 496, 210, 46, 12}, 750));
            add(new TopTensModel("Wyborowa Wodka Rye Grain Polish Vodka", "07/25/15", 4.0, "ABC liquor", "Publix liquor", 1.3, 1.8, 23.61,
                    21.49, 3, true, "fifth",  40, 80, new int[] {465, 129, 76, 32, 15}, 750));
            add(new TopTensModel("Henninger", "08/11/15", 3.5, "Publix liquor", 1.8, 8.26, 1, true, "(6) bottle", 4.80, 10, new int[] {162, 168, 35, 12, 1}, 750));
            add(new TopTensModel("Domaine Gavoty Provence Rose", "02/26/15", 3.5, "ABC liquor", "Publix liquor", 1.3, 1.8, 29.99,
                    28.36, 2, true, "fifth",  13, 26, new int[] {612, 345, 61, 124, 10}, 750));
            add(new TopTensModel("Ciroc French Pineapple Vodka", "10/11/15", 3.5, "Publix liquor", 1.8, 22.99, 3, true, "fifth", 35, 70, new int[]{236, 61, 96, 23, 9}, 750));
            add(new TopTensModel("Skyy Vodka6", "12/02/14", 3.5, "ABC liquor","Publix liquor",  1.3, 1.80, 18.73,
                    17.00, 3, true, "fifth",  40, 80, new int[] {251, 196, 75, 49, 21}, 750));
            add(new TopTensModel("Miller Light", "06/11/15", 3.0, "Publix liquor", 1.8, 7.23, 1, true, "(6) bottle", 4.17, 8, new int[] {129, 62, 514, 486, 69}, 750));
            add(new TopTensModel("Bud Light", "04/25/15", 4.0, "ABC liquor", "Walmart", 1.3, 2.34, 8.02,
                    6.73, 1, true, "(6) bottle",  4.20, 8, new int[] {251, 496, 765, 49, 128}, 750));
            add(new TopTensModel("Moscato", "02/09/15", 3.5,"Publix liquor", 1.8, 11.46, 2, true, "fifth", 5.5, 11, new int[] {265, 468, 96, 135, 26}, 750));
            add(new TopTensModel("Fireball Whiskey", "02/26/15", 3.5, "ABC liquor", "Publix liquor", 1.3, 1.8, 16.02,
                    15.56, 3, true, "handle",  33, 66, new int[] {361, 496, 210, 46, 12}, 750));
            add(new TopTensModel("Wyborowa Wodka Rye Grain Polish Vodka", "07/25/15", 4.0, "ABC liquor", "Publix liquor", 1.3, 1.8, 23.61,
                    21.49, 3, true, "fifth",  40, 80, new int[] {465, 129, 76, 32, 15}, 750));
            add(new TopTensModel("Henninger", "08/11/15", 3.5, "Publix liquor", 1.8, 8.26, 1, true, "(6) bottle", 4.80, 10, new int[] {162, 168, 35, 12, 1}, 750));
            add(new TopTensModel("Domaine Gavoty Provence Rose", "02/26/15", 3.5, "ABC liquor", "Publix liquor", 1.3, 1.8, 29.99,
                    28.36, 2, true, "fifth",  13, 26, new int[] {612, 345, 61, 124, 10}, 750));
            add(new TopTensModel("Ciroc French Pineapple Vodka", "10/11/15", 3.5, "Publix liquor", 1.8, 22.99, 3, true, "fifth", 35, 70, new int[] {236, 61, 96, 23, 9}, 750));
            add(new TopTensModel("Skyy Vodka7", "12/02/14", 3.5, "ABC liquor","Publix liquor",  1.3, 1.80, 18.73,
                    17.00, 3, true, "fifth",  40, 80, new int[] {251, 196, 75, 49, 21}, 750));
            add(new TopTensModel("Miller Light", "06/11/15", 3.0, "Publix liquor", 1.8, 7.23, 1, true, "(6) bottle", 4.17, 8, new int[] {129, 62, 514, 486, 69}, 750));
            add(new TopTensModel("Bud Light", "04/25/15", 4.0, "ABC liquor", "Walmart", 1.3, 2.34, 8.02,
                    6.73, 1, true, "(6) bottle",  4.20, 8, new int[] {251, 496, 765, 49, 128}, 750));
            add(new TopTensModel("Moscato", "02/09/15", 3.5,"Publix liquor", 1.8, 11.46, 2, true, "fifth", 5.5, 11, new int[] {265, 468, 96, 135, 26}, 750));
            add(new TopTensModel("Fireball Whiskey", "02/26/15", 3.5, "ABC liquor", "Publix liquor", 1.3, 1.8, 16.02,
                    15.56, 3, true, "handle",  33, 66, new int[] {361, 496, 210, 46, 12}, 750));
            add(new TopTensModel("Wyborowa Wodka Rye Grain Polish Vodka", "07/25/15", 4.0, "ABC liquor", "Publix liquor", 1.3, 1.8, 23.61,
                    21.49, 3, true, "fifth",  40, 80, new int[] {465, 129, 76, 32, 15}, 750));
            add(new TopTensModel("Henninger", "08/11/15", 3.5, "Publix liquor", 1.8, 8.26, 1, true, "(6) bottle", 4.80, 10, new int[] {162, 168, 35, 12, 1}, 750));
            add(new TopTensModel("Domaine Gavoty Provence Rose", "02/26/15", 3.5, "ABC liquor", "Publix liquor", 1.3, 1.8, 29.99,
                    28.36, 2, true, "fifth",  13, 26, new int[] {612, 345, 61, 124, 10}, 750));
            add(new TopTensModel("Ciroc French Pineapple Vodka", "10/11/15", 3.5, "Publix liquor", 1.8, 22.99, 3, true, "fifth", 35, 70, new int[] {236, 61, 96, 23, 9}, 750));
            add(new TopTensModel("Skyy Vodka8", "12/02/14", 3.5, "ABC liquor","Publix liquor",  1.3, 1.80, 18.73,
                    17.00, 3, true, "fifth",  40, 80, new int[] {251, 196, 75, 49, 21}, 750));
            add(new TopTensModel("Miller Light", "06/11/15", 3.0, "Publix liquor", 1.8, 7.23, 1, true, "(6) bottle", 4.17, 8, new int[] {129, 62, 514, 486, 69}, 750));
            add(new TopTensModel("Bud Light", "04/25/15", 4.0, "ABC liquor", "Walmart", 1.3, 2.34, 8.02,
                    6.73, 1, true, "(6) bottle",  4.20, 8, new int[] {251, 496, 765, 49, 128}, 750));
            add(new TopTensModel("Moscato", "02/09/15", 3.5,"Publix liquor", 1.8, 11.46, 2, true, "fifth", 5.5, 11, new int[] {265, 468, 96, 135, 26}, 750));
            add(new TopTensModel("Fireball Whiskey", "02/26/15", 3.5, "ABC liquor", "Publix liquor", 1.3, 1.8, 16.02,
                    15.56, 3, true, "handle",  33, 66, new int[] {361, 496, 210, 46, 12}, 750));
            add(new TopTensModel("Wyborowa Wodka Rye Grain Polish Vodka", "07/25/15", 4.0, "ABC liquor", "Publix liquor", 1.3, 1.8, 23.61,
                    21.49, 3, true, "fifth",  40, 80, new int[] {465, 129, 76, 32, 15}, 750));
            add(new TopTensModel("Henninger", "08/11/15", 3.5, "Publix liquor", 1.8, 8.26, 1, true, "(6) bottle", 4.80, 10, new int[] {162, 168, 35, 12, 1}, 750));
            add(new TopTensModel("Domaine Gavoty Provence Rose", "02/26/15", 3.5, "ABC liquor", "Publix liquor", 1.3, 1.8, 29.99,
                    28.36, 2, true, "fifth",  13, 26, new int[] {612, 345, 61, 124, 10}, 750));
            add(new TopTensModel("Ciroc French Pineapple Vodka", "10/11/15", 3.5, "Publix liquor", 1.8, 22.99, 3, true, "fifth", 35, 70, new int[] {236, 61, 96, 23, 9}, 750));
            add(new TopTensModel("Skyy Vodka9", "12/02/14", 3.5, "ABC liquor","Publix liquor",  1.3, 1.80, 18.73,
                    17.00, 3, true, "fifth",  40, 80, new int[] {251, 196, 75, 49, 21}, 750));
            add(new TopTensModel("Miller Light", "06/11/15", 3.0, "Publix liquor", 1.8, 7.23, 1, true, "(6) bottle", 4.17, 8, new int[] {129, 62, 514, 486, 69}, 750));
            add(new TopTensModel("Bud Light", "04/25/15", 4.0, "ABC liquor", "Walmart", 1.3, 2.34, 8.02,
                    6.73, 1, true, "(6) bottle",  4.20, 8, new int[] {251, 496, 765, 49, 128}, 750));
            add(new TopTensModel("Moscato", "02/09/15", 3.5,"Publix liquor", 1.8, 11.46, 2, true, "fifth", 5.5, 11, new int[] {265, 468, 96, 135, 26}, 750));
            add(new TopTensModel("Fireball Whiskey", "02/26/15", 3.5, "ABC liquor", "Publix liquor", 1.3, 1.8, 16.02,
                    15.56, 3, true, "handle",  33, 66, new int[] {361, 496, 210, 46, 12}, 750));
            add(new TopTensModel("Wyborowa Wodka Rye Grain Polish Vodka", "07/25/15", 4.0, "ABC liquor", "Publix liquor", 1.3, 1.8, 23.61,
                    21.49, 3, true, "fifth",  40, 80, new int[] {465, 129, 76, 32, 15}, 750));
            add(new TopTensModel("Henninger", "08/11/15", 3.5, "Publix liquor", 1.8, 8.26, 1, true, "(6) bottle", 4.80, 10, new int[] {162, 168, 35, 12, 1}, 750));
            add(new TopTensModel("Domaine Gavoty Provence Rose", "02/26/15", 3.5, "ABC liquor", "Publix liquor", 1.3, 1.8, 29.99,
                    28.36, 2, true, "fifth",  13, 26, new int[] {612, 345, 61, 124, 10}, 750));
            add(new TopTensModel("Ciroc French Pineapple Vodka", "10/11/15", 3.5, "Publix liquor", 1.8, 22.99, 3, true, "fifth", 35, 70, new int[] {236, 61, 96, 23, 9}, 750));
            add(new TopTensModel("Skyy Vodka10", "12/02/14", 3.5, "ABC liquor","Publix liquor",  1.3, 1.80, 18.73,
                    17.00, 3, true, "fifth",  40, 80, new int[] {251, 196, 75, 49, 21}, 750));
            add(new TopTensModel("Miller Light", "06/11/15", 3.0, "Publix liquor", 1.8, 7.23, 1, true, "(6) bottle", 4.17, 8, new int[] {129, 62, 514, 486, 69}, 750));
            add(new TopTensModel("Bud Light", "04/25/15", 4.0, "ABC liquor", "Walmart", 1.3, 2.34, 8.02,
                    6.73, 1, true, "(6) bottle",  4.20, 8, new int[] {251, 496, 765, 49, 128}, 750));
            add(new TopTensModel("Moscato", "02/09/15", 3.5,"Publix liquor", 1.8, 11.46, 2, true, "fifth", 5.5, 11, new int[] {265, 468, 96, 135, 26}, 750));
            add(new TopTensModel("Fireball Whiskey", "02/26/15", 3.5, "ABC liquor", "Publix liquor", 1.3, 1.8, 16.02,
                    15.56, 3, true, "handle",  33, 66, new int[] {361, 496, 210, 46, 12}, 750));
            add(new TopTensModel("Wyborowa Wodka Rye Grain Polish Vodka", "07/25/15", 4.0, "ABC liquor", "Publix liquor", 1.3, 1.8, 23.61,
                    21.49, 3, true, "fifth",  40, 80, new int[] {465, 129, 76, 32, 15}, 750));
            add(new TopTensModel("Henninger", "08/11/15", 3.5, "Publix liquor", 1.8, 8.26, 1, true, "(6) bottle", 4.80, 10, new int[] {162, 168, 35, 12, 1}, 750));
            add(new TopTensModel("Domaine Gavoty Provence Rose", "02/26/15", 3.5, "ABC liquor", "Publix liquor", 1.3, 1.8, 29.99,
                    28.36, 2, true, "fifth",  13, 26, new int[] {612, 345, 61, 124, 10}, 750));
            add(new TopTensModel("Ciroc French Pineapple Vodka", "10/11/15", 3.5, "Publix liquor", 1.8, 22.99, 3, true, "fifth", 35, 70, new int[] {236, 61, 96, 23, 9}, 750));
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

        mAdapter = new AdapterHandler((MainActivity) getActivity());
        mRecyclerView.setAdapter(mAdapter);

        int screenSize = getResources().getConfiguration().screenLayout &
                Configuration.SCREENLAYOUT_SIZE_MASK;

        switch(screenSize) {
            case Configuration.SCREENLAYOUT_SIZE_LARGE:
                mAdapter.changeSize(840, 175);
                break;
            case Configuration.SCREENLAYOUT_SIZE_NORMAL:
                break;
            case Configuration.SCREENLAYOUT_SIZE_SMALL:
                break;
            default:
        }

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView mRecyclerView, int dx, int dy) {
                super.onScrolled(mRecyclerView, dx, dy);
                if (Math.abs(dy) > 20) {
                    if (dy > 0) {
                        ((MainActivity) getActivity()).FAB.menuButton.hide(true);
                        closeMenu();
                    } else {
                        ((MainActivity) getActivity()).FAB.menuButton.show(true);
                        closeMenu();
                    }
                }
            }
        });

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (mAdapter.getCursorCheck()) {

                    visibleItemCount = mLayoutManager.getChildCount();
                    totalItemCount = mLayoutManager.getItemCount() - 2;
                    pastVisiblesItems = mLayoutManager.findFirstVisibleItemPosition();

                    if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                        mAdapter.addWithScroll();
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
                askForProductListrefresh(mAdapter, swipeRefreshLayout);
                swipeRefreshLayout.setRefreshing(false);

                /*mAdapter.clearData();
                mAdapter.startList(DataSet);*/
            }
        });

        //mAdapter.clearData();
        mAdapter.startList(askForProductList());
        //mAdapter.startList(DataSet);
    }

    public AdapterHandler getmAdapter() {
        return mAdapter;
    }

    public SwipeRefreshLayout getSwipeRefreshLayout() {
        return swipeRefreshLayout;
    }

    public interface OnPass {
        int AskForColorPrimary();
        int AskForColorPrimaryDark();
        int AskForColorAccent();
        int AskForColorAccentDark();
        void AskToHideFilterButtons();
        void AskToShowFilterButtons();
        void CloseMenu();
        void AskForProductListrefresh(AdapterHandler mAdapter, SwipeRefreshLayout swipeRefreshLayout);
        List<TopTensModel> AskForProductList();
    }

    public int askColorPrimary() { return dataPasser.AskForColorPrimary(); }

    public int askColorPrimaryDark() { return dataPasser.AskForColorPrimaryDark(); }

    public int askColorAccent() { return dataPasser.AskForColorAccent(); }

    public int askColorAccentDark() { return dataPasser.AskForColorAccentDark(); }

    public void closeMenu() { dataPasser.CloseMenu(); }

    public void askHideFilterButtons() { dataPasser.AskToHideFilterButtons(); }

    public void askShowFilterButtons() { dataPasser.AskToShowFilterButtons(); }

    public void askForProductListrefresh(AdapterHandler mAdapter, SwipeRefreshLayout swipeRefreshLayout) { dataPasser.AskForProductListrefresh(mAdapter, swipeRefreshLayout); }

    public List<TopTensModel> askForProductList() { return dataPasser.AskForProductList(); }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        Activity a;
        if( context instanceof Activity) {
            a = (Activity) context;
            dataPasser = (OnPass) a;
        }
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
}
