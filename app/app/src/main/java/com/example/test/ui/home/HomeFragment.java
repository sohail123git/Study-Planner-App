package com.example.test.ui.home;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.test.DataBaseHelper;
import com.example.test.EventActivity;
import com.example.test.EventAdapter;
import com.example.test.EventModel;
import com.example.test.MainActivity;
import com.example.test.R;
import com.example.test.databinding.FragmentHomeBinding;
import com.google.android.material.tabs.TabLayout;

import java.util.List;

public class HomeFragment extends Fragment implements EventAdapter.EventListener {

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
            }
        });

        TabLayout tabLayout = root.findViewById(R.id.tabLayout);
        int position = tabLayout.getSelectedTabPosition();

        updateRecyclerView(position, root);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();

                updateRecyclerView(position, root);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        return root;

    }

    @Override
    public void onStart() {
        super.onStart();
        View root = binding.getRoot();

        TabLayout tabLayout = root.findViewById(R.id.tabLayout);
        int position = tabLayout.getSelectedTabPosition();

        updateRecyclerView(position, root);
    }

    private void updateRecyclerView(int position, View root) {
        DataBaseHelper dataBaseHelper = new DataBaseHelper(getActivity());
        List<EventModel> currentEvents = dataBaseHelper.getSpecificEvents(position);

        Log.d("updaterecycler", currentEvents.toString());
        Toast.makeText(getContext(),"updaterecycler",Toast.LENGTH_SHORT);
        Toast.makeText(getContext(),currentEvents.toString(),Toast.LENGTH_SHORT);
//        recycler view setup
        RecyclerView recyclerView;
        RecyclerView.Adapter mAdapter;
        RecyclerView.LayoutManager layoutManager;

        recyclerView = root.findViewById(R.id.rv_events);
        recyclerView.setHasFixedSize(true);

        layoutManager =new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

//        Connecting Adapter to RecyclerView
        mAdapter = new EventAdapter(currentEvents,getActivity(),this);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onEvent() {
        View root = binding.getRoot();
        TabLayout tabLayout = root.findViewById(R.id.tabLayout);
        int position = tabLayout.getSelectedTabPosition();
        updateRecyclerView(position,root);
    }
}