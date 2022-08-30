package com.mqtt.workactiv.ui.settings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.mqtt.workactiv.R;
import com.mqtt.workactiv.databinding.FragmentSettingsBinding;

public class SettingsFragment extends Fragment {

    private FragmentSettingsBinding binding;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        SettingsViewModel settingsViewModel =
                new ViewModelProvider(this).get(SettingsViewModel.class);

        binding = FragmentSettingsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }
    ///// initiate view binding

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final NavController navController = Navigation.findNavController(view);
        binding.gatewayConnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //binding from settings to gateway page.
                navController.navigate(R.id.action_navigation_setting_to_gatewayFragment);

            }
        });
        //binding from settings to account login page
        binding.accountButton01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_navigation_setting_to_accountFragment2);
            }
        });
        //binding from settings to about page
        binding.aboutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_navigation_setting_to_aboutFragment);
            }
        });


        //viewbinding to terms n conditions page

        binding.termsConBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_navigation_setting_to_termsFragment);
            }
        });




    }

//destroy view
    @Override
    public void onDestroyView () {
        super.onDestroyView();
        binding = null;
    }
}


