package com.mqtt.workactiv.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;

import com.mqtt.workactiv.R;
import com.mqtt.workactiv.databinding.FragmentTermsBinding;

public class TermsFragment extends Fragment {

    private TermsViewModel mViewModel;
    private TermsFragment binding;

    public static TermsFragment newInstance() {
        return new TermsFragment();
    }



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState){
        TermsViewModel DatabaseViewModel =
                new ViewModelProvider(this).get(TermsViewModel.class);
        com.mqtt.workactiv.databinding.FragmentTermsBinding binding = FragmentTermsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final NavController navController = Navigation.findNavController(view);

        Button button = view.findViewById(R.id.terms_back_settings);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
     //           navController.navigate(R.id.action_termsFragment_to_navigation_setting);
                NavOptions navOptions = new NavOptions.Builder().setPopUpTo(com.mqtt.workactiv.R.id.navigation_setting, true).build();
                navController.navigate(R.id.action_termsFragment_to_navigation_setting, null, navOptions);
            }
        });
    }

   /* @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(TermsViewModel.class);
        // TODO: Use the ViewModel
    }*/
@Override
    public void onDestroy() {
    super.onDestroy();
    binding = null;
}
}