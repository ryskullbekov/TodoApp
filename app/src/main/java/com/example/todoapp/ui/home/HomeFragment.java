package com.example.todoapp.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.todoapp.R;
import com.example.todoapp.databinding.FragmentHomeBinding;
import com.example.todoapp.ui.create.TaskAdapter;
import com.example.todoapp.ui.create.TaskModel;
import com.example.todoapp.utils.Constants;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class HomeFragment extends Fragment {


    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;
    TaskModel model;
    ArrayList<TaskModel> list = new ArrayList<>();
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_content_main);

        if (getArguments() != null) {
            model = (TaskModel) getArguments().getSerializable(Constants.USER_TASK);
            list.add(model);
        }
        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.createTaskFragment);
            }
        });

        initAdapter();
    }
    private void initAdapter() {
        TaskAdapter adapter = new TaskAdapter(list);
        binding.taskRecycler.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}