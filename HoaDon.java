package com.example.myapplication.Model;

import androidx.annotation.NonNull;

import java.util.Date;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.annotations.PrimaryKey;

public class HoaDon extends RealmObject {

    @PrimaryKey
    private int maHoaDon;

    private long ThanhTien;
    private Date NgayLap;
    private String tenNV;
    boolean checktrangthai;

    public boolean isChecktrangthai() {
        return checktrangthai;
    }

    public void setChecktrangthai(boolean checktrangthai) {
        this.checktrangthai = checktrangthai;
    }

    public HoaDon(int maHoaDon, long thanhTien, Date ngayLap, String tenNV, boolean checktrangthai) {
        this.maHoaDon = maHoaDon;
        ThanhTien = thanhTien;
        NgayLap = ngayLap;
        this.tenNV = tenNV;
        this.checktrangthai = checktrangthai;
    }

    public HoaDon() {
    }



    public String getTenNV() {
        return tenNV;
    }

    public void setTenNV(String tenNV) {
        this.tenNV = tenNV;
    }

    public int getMaHoaDon() {
        return maHoaDon;
    }

    public void setMaHoaDon(int maHoaDon) {
        this.maHoaDon = maHoaDon;
    }

    public long getThanhTien() {
        return ThanhTien;
    }

    public void setThanhTien(int thanhTien) {
        ThanhTien = thanhTien;
    }

    public Date getNgayLap() {
        return NgayLap;
    }

    public void setNgayLap(Date ngayLap) {
        NgayLap = ngayLap;
    }




    public void setThanhTien(long thanhTien) {
        ThanhTien = thanhTien;
    }

    @NonNull
    @Override
    public String toString() {
        return maHoaDon+""+ThanhTien+""+NgayLap+""+tenNV;
    }



}
