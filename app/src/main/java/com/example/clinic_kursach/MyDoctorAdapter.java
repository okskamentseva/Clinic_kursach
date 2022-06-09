package com.example.clinic_kursach;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyDoctorAdapter extends RecyclerView.Adapter<MyDoctorAdapter.MyViewHolder> {

    Context context;
    List<Doctor> doctorList;
    Calback calback;

    public MyDoctorAdapter(Context context, List<Doctor> doctorList, Calback calback) {
        this.context = context;
        this.doctorList = doctorList;
        this.calback = calback;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.layout_polyclinic, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Doctor doctor=doctorList.get(position);
        holder.txt_poly_address.setText(doctor.getName());
        holder.txt_poly_name.setText(doctor.getSpec());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Data.doctor = doctor;
                calback.next();
            }
        });
    }

    @Override
    public int getItemCount() {
        return doctorList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txt_poly_name, txt_poly_address;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_poly_name = (TextView) itemView.findViewById(R.id.txt_poly_name);
            txt_poly_address = (TextView) itemView.findViewById(R.id.txt_poly_address);
        }
    }

    interface Calback {
        void next();
    }
}
