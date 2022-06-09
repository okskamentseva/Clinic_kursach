package com.example.clinic_kursach;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class BookingStep2Fragment extends Fragment {

    static BookingStep2Fragment instance;
    Unbinder unbinder;
    @BindView(R.id.recycler_clinic)
    RecyclerView recyclerView;
    @BindView(R.id.buttonPrevious)
    Button btnPre;
//    ViewPager2 viewPager2;



    public static BookingStep2Fragment getInstance() {
        if (instance == null)
            instance = new BookingStep2Fragment();
        return instance;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View itemView = inflater.inflate(R.layout.fragment_booking_step_two, container, false);
        unbinder = ButterKnife.bind(this, itemView);
//        viewPager2 = getActivity().findViewById(R.id.view_pager);
        initView();
        return itemView;
    }


    private void initView() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(new SpacesItemDecoration(4));
        MyDoctorAdapter adapter = new MyDoctorAdapter(getContext(), Data.polyclinic.getDoctors(), new MyDoctorAdapter.Calback() {
            @Override
            public void next() {
//                viewPager2.setCurrentItem(2);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frame,new BookingStep3Fragment());
                fragmentTransaction.commit();
            }
        });
        recyclerView.setAdapter(adapter);
        btnPre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                viewPager2.setCurrentItem(0);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frame,new BookingStep1Fragment());
                fragmentTransaction.commit();
            }
        });
    }

}
