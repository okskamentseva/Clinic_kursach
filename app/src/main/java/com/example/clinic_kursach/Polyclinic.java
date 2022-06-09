package com.example.clinic_kursach;

import java.util.ArrayList;
import java.util.List;

public class Polyclinic {
    private String name, adress;
    private List<Doctor> doctors = new ArrayList<>();

    public Polyclinic() {
    }

    public Polyclinic(String name, String adress) {
        this.name = name;
        this.adress = adress;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public List<Doctor> getDoctors() {
        return doctors;
    }

    public void setDoctors(List<Doctor> doctors) {
        this.doctors = doctors;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
