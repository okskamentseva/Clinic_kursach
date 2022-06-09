package com.example.clinic_kursach;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import java.util.List;

public class MyPolyclinicAdapter extends RecyclerView.Adapter<MyPolyclinicAdapter.MyViewHolder> {

    Context context;
    List<Polyclinic> polyclinicList;
    Calback calback;

    public MyPolyclinicAdapter(Context context, List<Polyclinic> polyclinicList, Calback calback) {
        this.context = context;
        this.polyclinicList = polyclinicList;
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
        Polyclinic polyclinic=polyclinicList.get(position);
        holder.txt_poly_name.setText(polyclinic.getName());
        holder.txt_poly_address.setText(polyclinic.getAdress());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Data.polyclinic = polyclinic;
                Log.i("msg"," " +polyclinic.getDoctors().size());
                calback.next();
            }
        });
    }

    @Override
    public int getItemCount() {
        return polyclinicList.size();
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
