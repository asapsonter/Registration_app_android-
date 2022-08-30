package com.mqtt.workactiv.ui.home;

import android.content.Context;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.github.mikephil.charting.components.IMarker;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.mqtt.workactiv.R;
import com.mqtt.workactiv.databinding.FragmentHomeBinding;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        setupGraph();
        setupListeners();
        return root;
    }

    private void setupListeners() {
        binding.switchWorkactivMode.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                binding.switchBoostMode.setChecked(false);
                binding.switchComfortMode.setChecked(false);
            }
        });

        binding.switchComfortMode.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                binding.switchBoostMode.setChecked(false);
                binding.switchWorkactivMode.setChecked(false);
            }
        });

        binding.switchBoostMode.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                binding.switchWorkactivMode.setChecked(false);
                binding.switchComfortMode.setChecked(false);
            }
        });

        binding.etSearch.setOnFocusChangeListener((view, b) -> {
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
                if (s.length() != 0) {
                    binding.ivSearch.setVisibility(View.GONE);
                    binding.tvSearch.setVisibility(View.GONE);
                } else {
                    binding.ivSearch.setVisibility(View.VISIBLE);
                    binding.tvSearch.setVisibility(View.VISIBLE);
                }

            }
        });
    }


    private void setupGraph() {
        binding.graphLine.setDrawBorders(false);
        binding.graphLine.setDrawGridBackground(false);
        binding.graphLine.getDescription().setEnabled(false);
        binding.graphLine.getLegend().setEnabled(false);
        binding.graphLine.getAxisRight().setEnabled(false);
        binding.graphLine.getAxisLeft().setEnabled(false);
        binding.graphLine.getXAxis().setEnabled(false);
        binding.graphLine.setDoubleTapToZoomEnabled(false);
        binding.graphLine.setPinchZoom(false);

        ArrayList<Pair<Integer, Integer>> tempList = new ArrayList<>();
        tempList.add(Pair.create(0, 0));
        tempList.add(Pair.create(1, 1300));
        tempList.add(Pair.create(2, 2800));
        tempList.add(Pair.create(3, 4200));
        tempList.add(Pair.create(4, 4700));
        tempList.add(Pair.create(5, 4200));
        tempList.add(Pair.create(6, 2800));
        tempList.add(Pair.create(7, 1300));
        tempList.add(Pair.create(8, 0));

        List<Entry> entries = new ArrayList<>();
        for (Pair<Integer, Integer> data : tempList) {
            entries.add(new Entry(data.first, data.second));
        }
        LineDataSet dataSet = new LineDataSet(entries, "Label");
        dataSet.setDrawCircles(false);
        dataSet.setCubicIntensity(0.2f);
        dataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        dataSet.setValueTextColor(Color.TRANSPARENT);
        dataSet.setLineWidth(7f);
//        dataSet.setDrawVerticalHighlightIndicator(true);
        dataSet.setDrawHorizontalHighlightIndicator(false);
//        dataSet.getx
        int[] colors = {
                getResources().getColor(R.color.orange, requireContext().getTheme()),
                getResources().getColor(R.color.white, requireContext().getTheme()),
                getResources().getColor(R.color.light_blue, requireContext().getTheme())
        };
        LinearGradient gradient = new LinearGradient(
                0f, 350f, 0f, 0f,
                colors,
                null,
                Shader.TileMode.REPEAT);
        Paint paint = binding.graphLine.getRenderer().getPaintRender();
        binding.graphLine.getRenderer().getPaintValues().setDither(true);
        paint.setShader(gradient);
        LineData lineData = new LineData(dataSet);
        binding.graphLine.setData(lineData);
        binding.graphLine.invalidate();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onPause() {
        super.onPause();
        binding.etSearch.setText("");
    }


    public class CustomMarkerView extends MarkerView implements IMarker {

        private TextView tvContent;

        public CustomMarkerView(Context context, int layoutResource) {
            super(context, layoutResource);
            // this markerview only displays a textview
            tvContent = (TextView) findViewById(R.id.tvContent);
        }

        // callbacks everytime the MarkerView is redrawn, can be used to update the
        // content (user-interface)
        @Override
        public void refreshContent(Entry e, Highlight highlight) {
            tvContent.setText("sdsds" + e.getX()); // set the entry-value as the display text
        }
//
//        @Override
//        public int getXOffset(float xpos) {
//            // this will center the marker-view horizontally
//            return -(getWidth() / 2);
//    }
//
//        @Override
//        public int getYOffset(float ypos) {
//            // this will cause the marker-view to be above the selected value
//            return -getHeight();
//        }
    }
}