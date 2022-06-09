package com.example.clinic_kursach;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class BookingStep4Fragment extends Fragment {

    static BookingStep4Fragment instance;
    Unbinder unbinder;
    @BindView(R.id.buttonPrevious)
    Button btnPre;
    @BindView(R.id.buttonSave)
    Button btnSave;
    @BindView(R.id.clinic)
    TextView clinic;
    @BindView(R.id.doctor)
    TextView doctor;
    @BindView(R.id.spec)
    TextView spec;
    @BindView(R.id.date)
    TextView date;
    @BindView(R.id.time)
    TextView time;

    public static BookingStep4Fragment getInstance() {
        if (instance == null)
            instance = new BookingStep4Fragment();
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
        View itemView = inflater.inflate(R.layout.fragment_booking_step_four, container, false);
        unbinder = ButterKnife.bind(this, itemView);
        initView();
        return itemView;
    }



    private void initView() {

        clinic.setText(Data.polyclinic.getName());
        doctor.setText(Data.doctor.getName());
        spec.setText(Data.doctor.getSpec());
        date.setText(Data.date);
        time.setText(Data.time);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Record record = new Record();
                record.setClinic(Data.polyclinic.getName());
                record.setDoctor(Data.doctor.getName());
                record.setSpec(Data.doctor.getSpec());
                record.setDate(Data.date);
                record.setTime(Data.time);
                FirebaseDatabase.getInstance().getReference("users")
                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                        .child("record")
                        .setValue(record).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(getActivity(), "Вы записаны на приём", Toast.LENGTH_LONG).show();
                        showMainActivity();
                    }
                });
            }
        });

        btnPre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frame, new BookingStep3Fragment());
                fragmentTransaction.commit();
            }
        });
    }


    private void showMainActivity() {
        Intent intent = new Intent(getActivity(), MainActivity.class);
        startActivity(intent);
        getActivity().finish();
    }
}
