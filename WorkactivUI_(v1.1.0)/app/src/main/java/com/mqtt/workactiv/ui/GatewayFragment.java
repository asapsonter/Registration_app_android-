package com.mqtt.workactiv.ui;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.mqtt.workactiv.R;
import com.mqtt.workactiv.databinding.FragmentGatewayBinding;

public class GatewayFragment extends Fragment {
    //private Button gateway_back_settings;
    private GatewayFragment binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        GatewayViewModel settingsViewModel =
                new ViewModelProvider(this).get(GatewayViewModel.class);
        com.mqtt.workactiv.databinding.FragmentGatewayBinding binding = FragmentGatewayBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    /*@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                Bundle savedInstanceState){
        //inflate layout for this fragment
        return  inflater.inflate(R.layout.fragment_gateway, container, false);

    }*/
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final NavController navController = Navigation.findNavController(view);

        Button button = view.findViewById(R.id.gateway_back_settings);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_gatewayFragment_to_navigation_setting);
            }
        });
        /*binding.gateway_back_settings.onSetClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            navController.navigate(R.id.action_gatewayFragment_to_navigation_setting);
            }*/
    }
/*
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(GatewayViewModel.class);
        // TODO: Use the ViewModel
    }*/
}
