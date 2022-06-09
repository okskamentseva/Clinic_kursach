package com.example.clinic_kursach;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class BookingStep1Fragment extends Fragment implements IAllClinicLoadListener, IPolyLoadListener {

    DatabaseReference allClinicRef;
    DatabaseReference polyRef;

    IAllClinicLoadListener iAllClinicLoadListener;
    IPolyLoadListener iPolyLoadListener;

    @BindView(R.id.spinner)
    MaterialSpinner spinner;
    @BindView(R.id.recycler_clinic)
    RecyclerView cardView;

    Unbinder unbinder;
    List<List<Polyclinic>> clinics = new ArrayList<>();
    List<String> list = new ArrayList<>();

    static BookingStep1Fragment instance;

    public static BookingStep1Fragment getInstance() {
        if (instance == null)
            instance = new BookingStep1Fragment();
        return instance;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        allClinicRef = database.getReference("areas");
        iAllClinicLoadListener = this;
        iPolyLoadListener = this;

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View itemView = inflater.inflate(R.layout.fragment_booking_step_one, container, false);
        unbinder = ButterKnife.bind(this, itemView);

        initView();
        return itemView;
    }

    @Override
    public void onResume() {
        super.onResume();

        loadAllClinic();
    }

    private void initView() {
        cardView.setHasFixedSize(true);
        cardView.setLayoutManager(new LinearLayoutManager(getActivity()));
        cardView.addItemDecoration(new SpacesItemDecoration(4));
    }

    private void loadAllClinic() {
        allClinicRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    list.add("Пожалуйста, выберите район");
                    for (DataSnapshot dataSnapshot:task.getResult().getChildren()) {
                        List<Polyclinic> clinic = new ArrayList<>();
                        for (DataSnapshot dataSnapshot1: dataSnapshot.child("clinic").getChildren()) {
                            Polyclinic polyclinic = new Polyclinic(dataSnapshot1.child("name").getValue(String.class), dataSnapshot1.child("adress").getValue(String.class));
                            List<Doctor> doctors = new ArrayList<>();
                            for (DataSnapshot dataSnapshot2: dataSnapshot1.child("doctor").getChildren()) {
                                doctors.add(new Doctor(dataSnapshot2.child("name").getValue(String.class), dataSnapshot2.child("spec").getValue(String.class)));
                            }
                            polyclinic.setDoctors(doctors);
                            clinic.add(polyclinic);

                        }
                        clinics.add(clinic);
                        list.add(dataSnapshot.child("name").getValue(String.class));
                    }
                    iAllClinicLoadListener.onAllClinicLoadSuccess(list);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                iAllClinicLoadListener.onAllClinicLoadFailed(e.getMessage());
            }
        });
    }

    @Override
    public void onAllClinicLoadSuccess(List<String> areaNameList) {
        spinner.setItems(areaNameList);
        spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                if(position > 0) {
                    cardView.setVisibility(View.VISIBLE);
                    Log.i("mg",item.toString());
//                    loadPolyclinicofArea(item.toString());
                    iPolyLoadListener.onPolyLoadSuccess(clinics.get(position-1));
                }
                else
                    cardView.setVisibility(View.GONE);
            }
        });
    }

    private void loadPolyclinicofArea(String areaName) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        polyRef = database.getReference("areas").child(areaName).child("clinic");
        polyRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {

//                Log.i("mg","item.toString()"+task.getResult().getChildren().toString());
                List<Polyclinic> list = new ArrayList<>();
                if (task.isSuccessful()) {
                    for (DataSnapshot dataSnapshot : task.getResult().getChildren()) {
                        list.add(dataSnapshot.child("name1").getValue(Polyclinic.class));
                        Log.i("mg","item.toString()"+dataSnapshot.toString());
                    }
                    iPolyLoadListener.onPolyLoadSuccess(list);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                iPolyLoadListener.onPolyLoadFailed(e.getMessage());
            }
        });
    }

    @Override
    public void onAllClinicLoadFailed(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onPolyLoadSuccess(List<Polyclinic> polyclinicList) {
        MyPolyclinicAdapter adapter = new MyPolyclinicAdapter(getActivity(), polyclinicList, new MyPolyclinicAdapter.Calback() {
            @Override
            public void next() {
//                ViewPager2 viewPager2 =  getActivity().findViewById(R.id.view_pager);
//                viewPager2.setCurrentItem(1);

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frame,new BookingStep2Fragment());
                fragmentTransaction.commit();
            }
        });
        cardView.setAdapter(adapter);
    }

    @Override
    public void onPolyLoadFailed(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
    }
}
