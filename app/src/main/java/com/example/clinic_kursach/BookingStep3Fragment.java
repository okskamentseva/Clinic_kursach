package com.example.clinic_kursach;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class BookingStep3Fragment extends Fragment {

    static BookingStep3Fragment instance;
    Unbinder unbinder;
    @BindView(R.id.buttonPrevious)
    Button btnPre;
    @BindView(R.id.buttonNext)
    Button btnNext;
    @BindView(R.id.date)
    DatePicker date;
    @BindView(R.id.time)
    TimePicker time;

    public static BookingStep3Fragment getInstance() {
        if (instance == null)
            instance = new BookingStep3Fragment();
        return instance;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View itemView = inflater.inflate(R.layout.fragment_booking_step_three, container, false);
        unbinder = ButterKnife.bind(this, itemView);
        initView();
        return itemView;
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    private void initView() {

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frame,new BookingStep4Fragment());
                fragmentTransaction.commit();
            }
        });

        btnPre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frame,new BookingStep2Fragment());
                fragmentTransaction.commit();
            }
        });

        Data.date = String.format("%02d-%02d-%d",date.getDayOfMonth(),date.getMonth(),date.getYear());

        date.setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker datePicker, int i, int i1, int i2) {
                Data.date = String.format("%02d-%02d-%d",i,i1+1,i2);
            }
        });

        Data.time = String.format("%02d-%02d",time.getHour(),time.getMinute());
        time.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker timePicker, int i, int i1) {
                Data.time = String.format("%02d-%02d",i,i1);
            }
        });


    }
}