package comjason_lewisg.httpsgithub.boozic.Fragments;


import android.app.Activity;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import comjason_lewisg.httpsgithub.boozic.R;

public class ThemeFragment extends Fragment {

    private ImageView lastSelectedPrimaryColor;
    private ImageView lastSelectedColorAccent;
    private View rootView;

    private int colorPrimary;
    private int colorAccent;

    OnDataPass dataPasser;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        colorPrimary = askColorPrimary();
        colorAccent = askColorAccent();
        Log.v("STATE", "primary = "+colorPrimary+" accent = "+colorAccent);

        rootView = inflater.inflate(R.layout.fragment_theme,container,false);

        lastSelectedPrimaryColor = (ImageView)rootView.findViewById(R.id.Primary_color_1_ring);
        lastSelectedPrimaryColor.setVisibility(View.VISIBLE);
        lastSelectedColorAccent = (ImageView)rootView.findViewById(R.id.Color_accent_1_ring);
        lastSelectedColorAccent.setVisibility(View.VISIBLE);

        setButtonPrimary(colorPrimary);
        setButtonAccent(colorAccent);

        Button button = (Button)rootView.findViewById(R.id.color_select_button);

        ImageView primColor1 = (ImageView)rootView.findViewById(R.id.Primary_color_1);
        ImageView primColor2 = (ImageView)rootView.findViewById(R.id.Primary_color_2);
        ImageView primColor3 = (ImageView)rootView.findViewById(R.id.Primary_color_3);
        ImageView primColor4 = (ImageView)rootView.findViewById(R.id.Primary_color_4);
        ImageView primColor5 = (ImageView)rootView.findViewById(R.id.Primary_color_5);

        Drawable drawable = getResources().getDrawable(R.drawable.color_selector_circle, null);
        drawable.setColorFilter(Color.parseColor("#673AB7"), PorterDuff.Mode.MULTIPLY);
        primColor1.setBackground(drawable);

        drawable = getResources().getDrawable(R.drawable.color_selector_circle, null);
        drawable.setColorFilter(Color.parseColor("#3F51B5"), PorterDuff.Mode.MULTIPLY);
        primColor2.setBackground(drawable);

        drawable = getResources().getDrawable(R.drawable.color_selector_circle, null);
        drawable.setColorFilter(Color.parseColor("#000000"), PorterDuff.Mode.MULTIPLY);
        primColor3.setBackground(drawable);

        drawable = getResources().getDrawable(R.drawable.color_selector_circle, null);
        drawable.setColorFilter(Color.parseColor("#FFC107"), PorterDuff.Mode.MULTIPLY);
        primColor4.setBackground(drawable);

        drawable = getResources().getDrawable(R.drawable.color_selector_circle, null);
        drawable.setColorFilter(Color.parseColor("#F44336"), PorterDuff.Mode.MULTIPLY);
        primColor5.setBackground(drawable);

        ImageView colorAccent1 = (ImageView)rootView.findViewById(R.id.Color_accent_1);
        ImageView colorAccent2 = (ImageView)rootView.findViewById(R.id.Color_accent_2);
        ImageView colorAccent3 = (ImageView)rootView.findViewById(R.id.Color_accent_3);
        ImageView colorAccent4 = (ImageView)rootView.findViewById(R.id.Color_accent_4);
        ImageView colorAccent5 = (ImageView)rootView.findViewById(R.id.Color_accent_5);

        drawable = getResources().getDrawable(R.drawable.color_selector_circle, null);
        drawable.setColorFilter(Color.parseColor("#F48FB1"), PorterDuff.Mode.MULTIPLY);
        colorAccent1.setBackground(drawable);

        drawable = getResources().getDrawable(R.drawable.color_selector_circle, null);
        drawable.setColorFilter(Color.parseColor("#C5E1A5"), PorterDuff.Mode.MULTIPLY);
        colorAccent2.setBackground(drawable);

        drawable = getResources().getDrawable(R.drawable.color_selector_circle, null);
        drawable.setColorFilter(Color.parseColor("#FFF59D"), PorterDuff.Mode.MULTIPLY);
        colorAccent3.setBackground(drawable);

        drawable = getResources().getDrawable(R.drawable.color_selector_circle, null);
        drawable.setColorFilter(Color.parseColor("#B0BEC5"), PorterDuff.Mode.MULTIPLY);
        colorAccent4.setBackground(drawable);

        drawable = getResources().getDrawable(R.drawable.color_selector_circle, null);
        drawable.setColorFilter(Color.parseColor("#CE93D8"), PorterDuff.Mode.MULTIPLY);
        colorAccent5.setBackground(drawable);

        primColor1.setOnClickListener(primaryColorOnClickListener);
        primColor2.setOnClickListener(primaryColorOnClickListener);
        primColor3.setOnClickListener(primaryColorOnClickListener);
        primColor4.setOnClickListener(primaryColorOnClickListener);
        primColor5.setOnClickListener(primaryColorOnClickListener);

        colorAccent1.setOnClickListener(colorAccentOnClickListener);
        colorAccent2.setOnClickListener(colorAccentOnClickListener);
        colorAccent3.setOnClickListener(colorAccentOnClickListener);
        colorAccent4.setOnClickListener(colorAccentOnClickListener);
        colorAccent5.setOnClickListener(colorAccentOnClickListener);

        button.setOnClickListener(selectButtonListener);

        return rootView;
    }

    public View.OnClickListener primaryColorOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.Primary_color_1:
                    lastSelectedPrimaryColor.setVisibility(View.GONE);
                    lastSelectedPrimaryColor = (ImageView) rootView.findViewById(R.id.Primary_color_1_ring);
                    lastSelectedPrimaryColor.setVisibility(View.VISIBLE);
                    passPrimary(1);
                    colorPrimary = 1;
                    break;
                case R.id.Primary_color_2:
                    lastSelectedPrimaryColor.setVisibility(View.GONE);
                    lastSelectedPrimaryColor = (ImageView)rootView.findViewById(R.id.Primary_color_2_ring);
                    lastSelectedPrimaryColor.setVisibility(View.VISIBLE);
                    passPrimary(2);
                    colorPrimary = 1;
                    break;
                case R.id.Primary_color_3:
                    lastSelectedPrimaryColor.setVisibility(View.GONE);
                    lastSelectedPrimaryColor = (ImageView)rootView.findViewById(R.id.Primary_color_3_ring);
                    lastSelectedPrimaryColor.setVisibility(View.VISIBLE);
                    passPrimary(3);
                    break;
                case R.id.Primary_color_4:
                    lastSelectedPrimaryColor.setVisibility(View.GONE);
                    lastSelectedPrimaryColor = (ImageView)rootView.findViewById(R.id.Primary_color_4_ring);
                    lastSelectedPrimaryColor.setVisibility(View.VISIBLE);
                    passPrimary(4);
                    break;
                case R.id.Primary_color_5:
                    lastSelectedPrimaryColor.setVisibility(View.GONE);
                    lastSelectedPrimaryColor = (ImageView)rootView.findViewById(R.id.Primary_color_5_ring);
                    lastSelectedPrimaryColor.setVisibility(View.VISIBLE);
                    passPrimary(5);
                    break;
            }
        }
    };

    public View.OnClickListener colorAccentOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.Color_accent_1:
                    lastSelectedColorAccent.setVisibility(View.GONE);
                    lastSelectedColorAccent = (ImageView)rootView.findViewById(R.id.Color_accent_1_ring);
                    lastSelectedColorAccent.setVisibility(View.VISIBLE);
                    passAccent(1);
                    break;
                case R.id.Color_accent_2:
                    lastSelectedColorAccent.setVisibility(View.GONE);
                    lastSelectedColorAccent = (ImageView)rootView.findViewById(R.id.Color_accent_2_ring);
                    lastSelectedColorAccent.setVisibility(View.VISIBLE);
                    passAccent(2);
                    break;
                case R.id.Color_accent_3:
                    lastSelectedColorAccent.setVisibility(View.GONE);
                    lastSelectedColorAccent = (ImageView)rootView.findViewById(R.id.Color_accent_3_ring);
                    lastSelectedColorAccent.setVisibility(View.VISIBLE);
                    passAccent(3);
                    break;
                case R.id.Color_accent_4:
                    lastSelectedColorAccent.setVisibility(View.GONE);
                    lastSelectedColorAccent = (ImageView)rootView.findViewById(R.id.Color_accent_4_ring);
                    lastSelectedColorAccent.setVisibility(View.VISIBLE);
                    passAccent(4);
                    break;
                case R.id.Color_accent_5:
                    lastSelectedColorAccent.setVisibility(View.GONE);
                    lastSelectedColorAccent = (ImageView)rootView.findViewById(R.id.Color_accent_5_ring);
                    lastSelectedColorAccent.setVisibility(View.VISIBLE);
                    passAccent(5);
                    break;
            }
        }
    };

    public View.OnClickListener selectButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.color_select_button:
                    applyTheme();
                    break;
            }
        }
    };

    private void setButtonPrimary(int colorP) {
        switch (colorP) {
            case 1:
                lastSelectedPrimaryColor.setVisibility(View.GONE);
                lastSelectedPrimaryColor = (ImageView) rootView.findViewById(R.id.Primary_color_1_ring);
                lastSelectedPrimaryColor.setVisibility(View.VISIBLE);
                break;
            case 2:
                lastSelectedPrimaryColor.setVisibility(View.GONE);
                lastSelectedPrimaryColor = (ImageView)rootView.findViewById(R.id.Primary_color_2_ring);
                lastSelectedPrimaryColor.setVisibility(View.VISIBLE);
                break;
            case 3:
                lastSelectedPrimaryColor.setVisibility(View.GONE);
                lastSelectedPrimaryColor = (ImageView)rootView.findViewById(R.id.Primary_color_3_ring);
                lastSelectedPrimaryColor.setVisibility(View.VISIBLE);
                break;
            case 4:
                lastSelectedPrimaryColor.setVisibility(View.GONE);
                lastSelectedPrimaryColor = (ImageView)rootView.findViewById(R.id.Primary_color_4_ring);
                lastSelectedPrimaryColor.setVisibility(View.VISIBLE);
                break;
            case 5:
                lastSelectedPrimaryColor.setVisibility(View.GONE);
                lastSelectedPrimaryColor = (ImageView)rootView.findViewById(R.id.Primary_color_5_ring);
                lastSelectedPrimaryColor.setVisibility(View.VISIBLE);
                break;
        }
    }

    private void setButtonAccent(int colorA) {
        switch (colorA) {
            case 1:
                lastSelectedColorAccent.setVisibility(View.GONE);
                lastSelectedColorAccent = (ImageView)rootView.findViewById(R.id.Color_accent_1_ring);
                lastSelectedColorAccent.setVisibility(View.VISIBLE);
                break;
            case 2:
                lastSelectedColorAccent.setVisibility(View.GONE);
                lastSelectedColorAccent = (ImageView)rootView.findViewById(R.id.Color_accent_2_ring);
                lastSelectedColorAccent.setVisibility(View.VISIBLE);
                break;
            case 3:
                lastSelectedColorAccent.setVisibility(View.GONE);
                lastSelectedColorAccent = (ImageView)rootView.findViewById(R.id.Color_accent_3_ring);
                lastSelectedColorAccent.setVisibility(View.VISIBLE);
                break;
            case 4:
                lastSelectedColorAccent.setVisibility(View.GONE);
                lastSelectedColorAccent = (ImageView)rootView.findViewById(R.id.Color_accent_4_ring);
                lastSelectedColorAccent.setVisibility(View.VISIBLE);
                break;
            case 5:
                lastSelectedColorAccent.setVisibility(View.GONE);
                lastSelectedColorAccent = (ImageView)rootView.findViewById(R.id.Color_accent_5_ring);
                lastSelectedColorAccent.setVisibility(View.VISIBLE);
                break;
        }
    }

    public interface OnDataPass {
        void PassColorPrimary(int colorPrimary);
        void PassColorAccent(int colorAccent);
        void ApplyTheme();
        int AskForColorPrimary();
        int AskForColorAccent();
    }

    public void passPrimary(int colorPrimary) {
        dataPasser.PassColorPrimary(colorPrimary);
    }

    public void passAccent(int colorAccent) {
        dataPasser.PassColorAccent(colorAccent);
    }

    public void applyTheme () {
        dataPasser.ApplyTheme();
    }

    public int askColorPrimary() { return dataPasser.AskForColorPrimary(); }

    public int askColorAccent() { return dataPasser.AskForColorAccent(); }

    @Override
    public void onAttach(Activity a) {
        super.onAttach(a);
        dataPasser = (OnDataPass) a;
    }
}
