package com.example.mauthuhai;

public class HocSinh {
    private Integer Id;
    private String SBD;
    private String HoTen;
    private Double DToan;
    private Double DLy;
    private Double DHoa;

    public HocSinh(Integer id, String SBD, String hoTen, Double DToan, Double DLy, Double DHoa) {
        Id = id;
        this.SBD = SBD;
        HoTen = hoTen;
        this.DToan = DToan;
        this.DLy = DLy;
        this.DHoa = DHoa;
    }

    public HocSinh(int id) {
        Id = id;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getSBD() {
        return SBD;
    }

    public void setSBD(String SBD) {
        this.SBD = SBD;
    }

    public String getHoTen() {
        return HoTen;
    }

    public void setHoTen(String hoTen) {
        HoTen = hoTen;
    }

    public Double getDToan() {
        return DToan;
    }

    public void setDToan(Double DToan) {
        this.DToan = DToan;
    }

    public Double getDLy() {
        return DLy;
    }

    public void setDLy(Double DLy) {
        this.DLy = DLy;
    }

    public Double getDHoa() {
        return DHoa;
    }

    public void setDHoa(Double DHoa) {
        this.DHoa = DHoa;
    }
}
