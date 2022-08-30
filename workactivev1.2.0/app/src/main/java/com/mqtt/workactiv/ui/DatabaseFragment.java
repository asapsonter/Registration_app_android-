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
import com.mqtt.workactiv.databinding.FragmentAccountBinding;
import com.mqtt.workactiv.databinding.FragmentDatabaseBinding;

public class DatabaseFragment extends Fragment {

    private DatabaseViewModel mViewModel;
    private DatabaseFragment binding;


    public static DatabaseFragment newInstance() {
        return new DatabaseFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DatabaseViewModel DatabaseViewModel =
                new ViewModelProvider(this).get(DatabaseViewModel.class);
        com.mqtt.workactiv.databinding.FragmentDatabaseBinding binding = FragmentDatabaseBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final NavController navController = Navigation.findNavController(view);

        Button button = view.findViewById(R.id.database_back_settings);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_databaseFragment2_to_navigation_setting2);
            }
        });
    }
    /*@Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(DatabaseViewModel.class);
        // TODO: Use the ViewModel
    }*/

        @Override
        public void onDestroyView () {
            super.onDestroyView();
            binding = null;
        }

    }
