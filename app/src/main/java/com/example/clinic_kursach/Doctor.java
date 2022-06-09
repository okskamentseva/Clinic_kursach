package com.example.clinic_kursach;

public class Doctor {

    private String name, spec;

    public Doctor(String name, String spec) {
        this.name = name;
        this.spec = spec;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }
}
