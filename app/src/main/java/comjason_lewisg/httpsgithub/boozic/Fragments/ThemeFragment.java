package comjason_lewisg.httpsgithub.boozic.Fragments;


import android.app.Activity;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.transition.Fade;
import android.transition.Slide;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.ImageView;

import comjason_lewisg.httpsgithub.boozic.Handlers.ThemeHandler;
import comjason_lewisg.httpsgithub.boozic.R;

public class ThemeFragment extends Fragment {

    private ImageView lastSelectedPrimaryColor;
    private ImageView lastSelectedColorAccent;
    private View rootView;

    private int colorPrimaryId;
    private int colorAccentId;
    private int colorPrimary;
    private int colorAccent;

    private int primaryColor;
    private int primaryColorDark;
    private int accentColor;
    private int accentColorDark;

    private ScaleAnimation scaleUp;
    private ScaleAnimation scaleDown;

    private ThemeHandler themeHandler;

    OnDataPass dataPasser;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        themeHandler = new ThemeHandler();

        colorPrimary = askColorPrimaryId();
        colorAccent = askColorAccentId();
        colorPrimaryId = colorPrimary;
        colorAccentId = colorAccent;

        primaryColor = askColorPrimary();
        primaryColorDark = askColorPrimaryDark();
        accentColor = askColorAccent();
        accentColorDark = askColorAccentDark();

        rootView = inflater.inflate(R.layout.fragment_theme,container,false);
        viewSet(rootView);

        return rootView;
    }

    private void viewSet(View rootView) {
        lastSelectedPrimaryColor = (ImageView)rootView.findViewById(R.id.Primary_color_1_ring);
        lastSelectedPrimaryColor.setVisibility(View.VISIBLE);
        lastSelectedColorAccent = (ImageView)rootView.findViewById(R.id.Color_accent_1_ring);
        lastSelectedColorAccent.setVisibility(View.VISIBLE);

        Drawable setButton = getResources().getDrawable(R.drawable.custon_button, null);
        setButton.setColorFilter(accentColor, PorterDuff.Mode.MULTIPLY);
        Button tmp = (Button) rootView.findViewById(R.id.color_select_button);
        tmp.setBackground(setButton);

        setButtonPrimary(colorPrimary);
        setButtonAccent(colorAccent);

        ImageView primColor1 = (ImageView)rootView.findViewById(R.id.Primary_color_1);
        ImageView primColor2 = (ImageView)rootView.findViewById(R.id.Primary_color_2);
        ImageView primColor3 = (ImageView)rootView.findViewById(R.id.Primary_color_3);
        ImageView primColor4 = (ImageView)rootView.findViewById(R.id.Primary_color_4);
        ImageView primColor5 = (ImageView)rootView.findViewById(R.id.Primary_color_5);


        Drawable drawable = getResources().getDrawable(R.drawable.color_selector_circle, null);
        drawable.setColorFilter(ContextCompat.getColor(getActivity().getApplicationContext(), R.color.ColorPrimary), PorterDuff.Mode.MULTIPLY);
        primColor1.setBackground(drawable);

        drawable = getResources().getDrawable(R.drawable.color_selector_circle, null);
        drawable.setColorFilter(ContextCompat.getColor(getActivity().getApplicationContext(), R.color.ColorPrimary2), PorterDuff.Mode.MULTIPLY);
        primColor2.setBackground(drawable);

        drawable = getResources().getDrawable(R.drawable.color_selector_circle, null);
        drawable.setColorFilter(ContextCompat.getColor(getActivity().getApplicationContext(), R.color.ColorPrimary3), PorterDuff.Mode.MULTIPLY);
        primColor3.setBackground(drawable);

        drawable = getResources().getDrawable(R.drawable.color_selector_circle, null);
        drawable.setColorFilter(ContextCompat.getColor(getActivity().getApplicationContext(), R.color.ColorPrimary4), PorterDuff.Mode.MULTIPLY);
        primColor4.setBackground(drawable);

        drawable = getResources().getDrawable(R.drawable.color_selector_circle, null);
        drawable.setColorFilter(ContextCompat.getColor(getActivity().getApplicationContext(), R.color.ColorPrimary5), PorterDuff.Mode.MULTIPLY);
        primColor5.setBackground(drawable);

        ImageView colorAccent1 = (ImageView)rootView.findViewById(R.id.Color_accent_1);
        ImageView colorAccent2 = (ImageView)rootView.findViewById(R.id.Color_accent_2);
        ImageView colorAccent3 = (ImageView)rootView.findViewById(R.id.Color_accent_3);
        ImageView colorAccent4 = (ImageView)rootView.findViewById(R.id.Color_accent_4);
        ImageView colorAccent5 = (ImageView)rootView.findViewById(R.id.Color_accent_5);

        drawable = getResources().getDrawable(R.drawable.color_selector_circle, null);
        drawable.setColorFilter(ContextCompat.getColor(getActivity().getApplicationContext(), R.color.ColorAccent), PorterDuff.Mode.MULTIPLY);
        colorAccent1.setBackground(drawable);

        drawable = getResources().getDrawable(R.drawable.color_selector_circle, null);
        drawable.setColorFilter(ContextCompat.getColor(getActivity().getApplicationContext(), R.color.ColorAccent2), PorterDuff.Mode.MULTIPLY);
        colorAccent2.setBackground(drawable);

        drawable = getResources().getDrawable(R.drawable.color_selector_circle, null);
        drawable.setColorFilter(ContextCompat.getColor(getActivity().getApplicationContext(), R.color.ColorAccent3), PorterDuff.Mode.MULTIPLY);
        colorAccent3.setBackground(drawable);

        drawable = getResources().getDrawable(R.drawable.color_selector_circle, null);
        drawable.setColorFilter(ContextCompat.getColor(getActivity().getApplicationContext(), R.color.ColorAccent4), PorterDuff.Mode.MULTIPLY);
        colorAccent4.setBackground(drawable);

        drawable = getResources().getDrawable(R.drawable.color_selector_circle, null);
        drawable.setColorFilter(ContextCompat.getColor(getActivity().getApplicationContext(), R.color.ColorAccent5), PorterDuff.Mode.MULTIPLY);
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

        tmp.setOnClickListener(selectButtonListener);

        scaleUp =
                new ScaleAnimation(0.0f, 1f, 0.0f, 1f,
                        ScaleAnimation.RELATIVE_TO_SELF, 0.5f,
                        ScaleAnimation.RELATIVE_TO_SELF, 0.5f);
        scaleUp.setDuration(250);

        scaleDown =
                new ScaleAnimation(1.0f, 0.0f, 1f, 0.0f,
                        ScaleAnimation.RELATIVE_TO_SELF, 0.5f,
                        ScaleAnimation.RELATIVE_TO_SELF, 0.5f);
        scaleDown.setDuration(250);

    }

    public View.OnClickListener primaryColorOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.Primary_color_1:
                    if (colorPrimaryId != 1) {
                        lastSelectedPrimaryColor.setVisibility(View.GONE);
                        lastSelectedPrimaryColor.startAnimation(scaleDown);
                        lastSelectedPrimaryColor = (ImageView) rootView.findViewById(R.id.Primary_color_1_ring);
                        lastSelectedPrimaryColor.startAnimation(scaleUp);
                        lastSelectedPrimaryColor.setVisibility(View.VISIBLE);
                        colorPrimaryId = 1;
                    }
                    break;
                case R.id.Primary_color_2:
                    if (colorPrimaryId != 2) {
                        lastSelectedPrimaryColor.setVisibility(View.GONE);
                        lastSelectedPrimaryColor.startAnimation(scaleDown);
                        lastSelectedPrimaryColor = (ImageView) rootView.findViewById(R.id.Primary_color_2_ring);
                        lastSelectedPrimaryColor.startAnimation(scaleUp);
                        lastSelectedPrimaryColor.setVisibility(View.VISIBLE);
                        colorPrimaryId = 2;
                    }
                    break;
                case R.id.Primary_color_3:
                    if (colorPrimaryId != 3) {
                        lastSelectedPrimaryColor.setVisibility(View.GONE);
                        lastSelectedPrimaryColor.startAnimation(scaleDown);
                        lastSelectedPrimaryColor = (ImageView) rootView.findViewById(R.id.Primary_color_3_ring);
                        lastSelectedPrimaryColor.startAnimation(scaleUp);
                        lastSelectedPrimaryColor.setVisibility(View.VISIBLE);
                        colorPrimaryId = 3;
                    }
                    break;
                case R.id.Primary_color_4:
                    if (colorPrimaryId != 4) {
                        lastSelectedPrimaryColor.setVisibility(View.GONE);
                        lastSelectedPrimaryColor.startAnimation(scaleDown);
                        lastSelectedPrimaryColor = (ImageView) rootView.findViewById(R.id.Primary_color_4_ring);
                        lastSelectedPrimaryColor.startAnimation(scaleUp);
                        lastSelectedPrimaryColor.setVisibility(View.VISIBLE);
                        colorPrimaryId = 4;
                    }
                    break;
                case R.id.Primary_color_5:
                    if (colorPrimaryId != 5) {
                        lastSelectedPrimaryColor.setVisibility(View.GONE);
                        lastSelectedPrimaryColor.startAnimation(scaleDown);
                        lastSelectedPrimaryColor = (ImageView) rootView.findViewById(R.id.Primary_color_5_ring);
                        lastSelectedPrimaryColor.startAnimation(scaleUp);
                        lastSelectedPrimaryColor.setVisibility(View.VISIBLE);
                        colorPrimaryId = 5;
                    }

                    break;
            }
        }
    };

    public View.OnClickListener colorAccentOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            switch (v.getId()) {
                case R.id.Color_accent_1:
                    if (colorAccentId != 1) {
                        lastSelectedColorAccent.setVisibility(View.GONE);
                        lastSelectedColorAccent.startAnimation(scaleDown);
                        lastSelectedColorAccent = (ImageView) rootView.findViewById(R.id.Color_accent_1_ring);
                        lastSelectedColorAccent.startAnimation(scaleUp);
                        lastSelectedColorAccent.setVisibility(View.VISIBLE);
                        colorAccentId = 1;
                    }
                    break;
                case R.id.Color_accent_2:
                    if (colorAccentId != 2) {
                        lastSelectedColorAccent.setVisibility(View.GONE);
                        lastSelectedColorAccent.startAnimation(scaleDown);
                        lastSelectedColorAccent = (ImageView) rootView.findViewById(R.id.Color_accent_2_ring);
                        lastSelectedColorAccent.startAnimation(scaleUp);
                        lastSelectedColorAccent.setVisibility(View.VISIBLE);
                        colorAccentId = 2;
                    }
                    break;
                case R.id.Color_accent_3:
                    if (colorAccentId != 3) {
                        lastSelectedColorAccent.setVisibility(View.GONE);
                        lastSelectedColorAccent.startAnimation(scaleDown);
                        lastSelectedColorAccent = (ImageView) rootView.findViewById(R.id.Color_accent_3_ring);
                        lastSelectedColorAccent.startAnimation(scaleUp);
                        lastSelectedColorAccent.setVisibility(View.VISIBLE);
                        colorAccentId = 3;
                    }
                    break;
                case R.id.Color_accent_4:
                    if (colorAccentId != 4) {
                        lastSelectedColorAccent.setVisibility(View.GONE);
                        lastSelectedColorAccent.startAnimation(scaleDown);
                        lastSelectedColorAccent = (ImageView) rootView.findViewById(R.id.Color_accent_4_ring);
                        lastSelectedColorAccent.startAnimation(scaleUp);
                        lastSelectedColorAccent.setVisibility(View.VISIBLE);
                        colorAccentId = 4;
                    }
                    break;
                case R.id.Color_accent_5:
                    if (colorAccentId != 5) {
                        lastSelectedColorAccent.setVisibility(View.GONE);
                        lastSelectedColorAccent.startAnimation(scaleDown);
                        lastSelectedColorAccent = (ImageView) rootView.findViewById(R.id.Color_accent_5_ring);
                        lastSelectedColorAccent.startAnimation(scaleUp);
                        lastSelectedColorAccent.setVisibility(View.VISIBLE);
                        colorAccentId = 5;
                    }
                    break;
            }
        }
    };

    public View.OnClickListener selectButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.color_select_button:
                    if ((colorAccentId != colorAccent) || (colorPrimaryId != colorPrimary)) {
                        if (colorPrimaryId != colorPrimary) {
                            passPrimaryId(colorPrimaryId);
                            themeHandler.setStylePrimary(colorPrimaryId, getActivity());
                            passPrimary(themeHandler.primaryColor, themeHandler.primaryColorDark);
                        }
                        if (colorAccentId != colorAccent) {
                            passAccentId(colorAccentId);
                            themeHandler.setStyleAccent(colorAccentId, getActivity());
                            passAccent(themeHandler.accentColor, themeHandler.accentColorDark);
                        }
                        applyTheme();
                    }
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
        void PassColorPrimaryId(int colorPrimary_id);
        void PassColorPrimary(int colorPrimary, int colorPrimaryDark);
        void PassColorAccentId(int colorAccent_id);
        void PassColorAccent(int colorAccent, int colorAccentDark);
        void ApplyTheme();
        int AskForColorPrimaryId();
        int AskForColorAccentId();
        int AskForColorPrimary();
        int AskForColorPrimaryDark();
        int AskForColorAccent();
        int AskForColorAccentDark();

    }

    public void passPrimaryId(int colorPrimary_id) {
        dataPasser.PassColorPrimaryId(colorPrimary_id);
    }

    public void passAccentId(int colorAccent_id) {
        dataPasser.PassColorAccentId(colorAccent_id);
    }

    public void passPrimary(int colorPrimary, int colorAccent) {
        dataPasser.PassColorPrimary(colorPrimary, colorAccent);
    }

    public void passAccent(int colorAccent, int colorAccentDark) {
        dataPasser.PassColorAccent(colorAccent, colorAccentDark);
    }

    public void applyTheme () {
        dataPasser.ApplyTheme();
    }

    public int askColorPrimaryId() { return dataPasser.AskForColorPrimaryId(); }

    public int askColorAccentId() { return dataPasser.AskForColorAccentId(); }

    public int askColorPrimary() { return dataPasser.AskForColorPrimary(); }

    public int askColorPrimaryDark() { return dataPasser.AskForColorPrimaryDark(); }

    public int askColorAccent() { return dataPasser.AskForColorAccent(); }

    public int askColorAccentDark() { return dataPasser.AskForColorAccentDark(); }

    @Override
    public void onAttach(Activity a) {
        super.onAttach(a);
        dataPasser = (OnDataPass) a;
    }

    @Override
    public void onStart(){
        super.onStart();
    }
}
