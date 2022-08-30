package com.mqtt.workactiv.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.snackbar.SnackbarContentLayout;
import com.mqtt.workactiv.databinding.FragmentAboutBinding;

import com.mqtt.workactiv.R;
import com.mqtt.workactiv.databinding.FragmentGatewayBinding;

import java.util.Objects;

public class AboutFragment extends Fragment {

    private CoordinatorLayout mSnackbarLayout;
    private AboutFragment binding;
    private AboutViewModel mViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        AboutViewModel aboutViewModel =
                new ViewModelProvider(this).get(AboutViewModel.class);
        com.mqtt.workactiv.databinding.FragmentAboutBinding binding = FragmentAboutBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final NavController navController = Navigation.findNavController(view);

        //back to settings button
        Button button = view.findViewById(R.id.about_back_settings);

        button.setOnClickListener(v -> navController.navigate(R.id.action_aboutFragment_to_navigation_setting));



        //update snack bar
        mSnackbarLayout  = requireView().findViewById(R.id.snackbar_layout);
        Button snackBarBtn = requireView().findViewById(R.id.snack_update_button);
        snackBarBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                //snackbar text
                Snackbar snackbar = Snackbar.make(mSnackbarLayout, "Update WorkActiv", Snackbar.LENGTH_LONG);
                //snackbar timer
                snackbar.setDuration(10000);
                //snack slide animation
                snackbar.setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE);
               // snackbar.setBackgroundTint();
                snackbar.setAction("update", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // write a loop to update or tell user they software is up to date
                    }
                });

                snackbar.show();
            }
        });
    }



/*
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(AboutViewModel.class);
        // TODO: Use the ViewModel
    }*/


}