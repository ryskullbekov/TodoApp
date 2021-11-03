package com.example.todoapp.ui.create;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TimePicker;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;
import com.example.todoapp.R;
import com.example.todoapp.databinding.FragmentCreateTask2Binding;
import com.example.todoapp.utils.Constants;

import org.jetbrains.annotations.NotNull;

import java.util.Calendar;


public class CreateTaskFragment extends Fragment {
    private FragmentCreateTask2Binding binding;
    String userTask;
    String userChoosedDate;
    String time;
    String image;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCreateTask2Binding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        userTask = binding.taskEd.getText().toString();
        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_content_main);

        binding.setTimeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateTimePicker();
            }
        });

        binding.applyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userTask = binding.taskEd.getText().toString();
                TaskModel model = new TaskModel(R.color.purple_200,userTask,userChoosedDate+"/"+time,image);
                Bundle bundle = new Bundle();
                bundle.putSerializable(Constants.USER_TASK, model);
                navController.navigate(R.id.nav_home, bundle);
            }
        });
        binding.addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mGetContent.launch("image/*");
            }
        });
    }

    ActivityResultLauncher<String> mGetContent = registerForActivityResult(new ActivityResultContracts.GetContent(),
            new ActivityResultCallback<Uri>() {
                @Override
                public void onActivityResult(Uri uri) {
                    image = uri.toString();
                    Glide.with(binding.imageView).load(image).centerCrop().into(binding.imageView);
                }
            });

    public void showDateTimePicker() {
        final Calendar currentDate = Calendar.getInstance();

        Calendar date = Calendar.getInstance();
        new DatePickerDialog(requireContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                date.set(year, monthOfYear, dayOfMonth);
                new TimePickerDialog(requireContext(), new TimePickerDialog.OnTimeSetListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        date.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        date.set(Calendar.MINUTE, minute);
                        time = hourOfDay + " : " + minute;
                        userChoosedDate = date.get(Calendar.MONTH) + "." + date.get(Calendar.DAY_OF_MONTH);

                        binding.timeTv.setText(userChoosedDate + "/" + time);
                    }
                }, currentDate.get(Calendar.HOUR_OF_DAY), currentDate.get(Calendar.MINUTE), false).show();
            }
        }, currentDate.get(Calendar.YEAR), currentDate.get(Calendar.MONTH), currentDate.get(Calendar.DATE)).show();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}