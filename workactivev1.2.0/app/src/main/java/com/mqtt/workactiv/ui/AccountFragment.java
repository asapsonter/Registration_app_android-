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

public class AccountFragment extends Fragment {


    private AccountViewModel mViewModel;
    private AccountFragment binding;
    private View account_back_settings;
        public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState){
        AccountViewModel accountViewModel =
                new ViewModelProvider(this).get(AccountViewModel.class);
        com.mqtt.workactiv.databinding.FragmentAccountBinding binding = FragmentAccountBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final NavController navController = Navigation.findNavController(view);

        Button button = view.findViewById(R.id.account_back_settings);

        button.setOnClickListener(v -> navController.navigate(R.id.action_accountFragment_to_navigation_setting));


    }


    /*@Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(AccountViewModel.class);
        // TODO: Use the ViewModel
    }*/
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;

    }
}