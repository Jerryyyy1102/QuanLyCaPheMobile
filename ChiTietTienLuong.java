package com.example.myapplication.Model;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class ChiTietTienLuong extends RealmObject {
    private int maWork,maNhanVien;

    @PrimaryKey
    private String ctTL;
    private int tongtien;

    public ChiTietTienLuong(int maWork, int maNhanVien, String ctTL, int tongtien) {
        this.maWork = maWork;
        this.maNhanVien = maNhanVien;
        this.ctTL = ctTL;
        this.tongtien = tongtien;
    }

    public ChiTietTienLuong() {
    }

    public int getMaWork() {
        return maWork;
    }

    public void setMaWork(int maWork) {
        this.maWork = maWork;
    }

    public int getMaNhanVien() {
        return maNhanVien;
    }

    public void setMaNhanVien(int maNhanVien) {
        this.maNhanVien = maNhanVien;
    }

    public String getCtTL() {
        return ctTL;
    }

    public void setCtTL(String ctTL) {
        this.ctTL=this.maNhanVien+" "+this.maWork;
    }



    public int getTongtien() {
        return tongtien;
    }

    public void setTongtien(int tongtien) {
        this.tongtien = tongtien;
    }
}
