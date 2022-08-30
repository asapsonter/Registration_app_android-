package com.mqtt.workactiv.ui.control;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.mqtt.workactiv.MainActivity;
import com.mqtt.workactiv.R;
import com.mqtt.workactiv.databinding.FragmentControlBinding;
import com.mqtt.workactiv.databinding.RoomControlItemBinding;


public class ControlFragment extends Fragment {

    private FragmentControlBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ControlViewModel controlViewModel =
                new ViewModelProvider(this).get(ControlViewModel.class);

        binding = FragmentControlBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        setListeners();
        setupViews();
        return root;
    }

    @Override
    public void onPause() {
        super.onPause();
        binding.etSearch.setText("");
    }


    private void setupViews() {
        binding.ilMeetingRoom.tvHeadingRoomName.setText(R.string.meeting_room);
        binding.ilMeetingRoom.tvWorkModeDesc.setText(R.string.meeting_room_desc);
    }


    private void setListeners() {
        binding.etSearch.setOnFocusChangeListener((view, b) -> {
            ((MainActivity)getActivity()).hideNavBar();
            if (b) {
                binding.ivSearch.setVisibility(View.GONE);
                binding.tvSearch.setVisibility(View.GONE);
            }
        });

        binding.etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                ((MainActivity)getActivity()).hideNavBar();
                if (s.length() != 0) {
                    if ("All lights".toLowerCase().contains(s.toString().toLowerCase())) {
                        binding.ilAllLights.getRoot().setVisibility(View.VISIBLE);
                        binding.ilMeetingRoom.getRoot().setVisibility(View.GONE);
                    } else if ("Meeting room".toLowerCase().contains(s.toString().toLowerCase())) {
                        binding.ilAllLights.getRoot().setVisibility(View.GONE);
                        binding.ilMeetingRoom.getRoot().setVisibility(View.VISIBLE);
                    } else {
                        binding.ilAllLights.getRoot().setVisibility(View.GONE);
                        binding.ilMeetingRoom.getRoot().setVisibility(View.GONE);
                    }
                    binding.ivSearch.setVisibility(View.GONE);
                    binding.tvSearch.setVisibility(View.GONE);
                } else {
                    binding.ivSearch.setVisibility(View.VISIBLE);
                    binding.tvSearch.setVisibility(View.VISIBLE);
                    binding.ilMeetingRoom.getRoot().setVisibility(View.VISIBLE);
                    binding.ilAllLights.getRoot().setVisibility(View.VISIBLE);
                }

            }
        });

        binding.ilAllLights.switchAutoMode.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                changeAllLightsItemsVisibility(binding.ilAllLights, View.GONE);
            } else {
                changeAllLightsItemsVisibility(binding.ilAllLights, View.VISIBLE);
            }
        });

        binding.ilMeetingRoom.switchAutoMode.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                changeAllLightsItemsVisibility(binding.ilMeetingRoom, View.GONE);
            } else {
                changeAllLightsItemsVisibility(binding.ilMeetingRoom, View.VISIBLE);
            }
        });
    }

    private void changeAllLightsItemsVisibility(RoomControlItemBinding roomControlItemBinding, Integer visibility) {
        roomControlItemBinding.seperator1.setVisibility(visibility);
        roomControlItemBinding.seperator2.setVisibility(visibility);
        roomControlItemBinding.seperator3.setVisibility(visibility);
        roomControlItemBinding.tvHeadingMasterSwitch.setVisibility(visibility);
        roomControlItemBinding.switchMaster.setVisibility(visibility);
        roomControlItemBinding.viewColorIndicatorLeft.setVisibility(visibility);
        roomControlItemBinding.viewLightIndicatorRight.setVisibility(visibility);
        roomControlItemBinding.viewColorIndicatorRight.setVisibility(visibility);
        roomControlItemBinding.viewLightIndicatorLeft.setVisibility(visibility);
        roomControlItemBinding.sb1.setVisibility(visibility);
        roomControlItemBinding.colorPicker.setVisibility(visibility);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}