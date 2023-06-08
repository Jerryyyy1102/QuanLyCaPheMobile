package com.example.myapplication.Model;

import androidx.annotation.NonNull;

import javax.annotation.PropertyKey;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class SanPham extends RealmObject {
    @PrimaryKey
    private int maSanPham;
    private String tenSanPham;
    private double giaSanPham;
    private byte[] hinhanhSanPham;

    public SanPham() {
    }

    public SanPham(int maSanPham, String tenSanPham, double giaSanPham, byte[] hinhanhSanPham) {
        this.maSanPham = maSanPham;
        this.tenSanPham = tenSanPham;
        this.giaSanPham = giaSanPham;
        this.hinhanhSanPham = hinhanhSanPham;
    }

    public Double getGiaSanPham() {
        return giaSanPham;
    }

    public void setGiaSanPham(double giaSanPham) {
        this.giaSanPham = giaSanPham;
    }

    public byte[] getHinhanhSanPham() {
        return hinhanhSanPham;
    }

    public void setHinhanhSanPham(byte[] hinhanhSanPham) {
        this.hinhanhSanPham = hinhanhSanPham;
    }


    private LoaiSanPham loaiSanPham; // khóa ngoại

    public int getMaSanPham() {
        return maSanPham;
    }

    public void setMaSanPham(int maSanPham) {
        this.maSanPham = maSanPham;
    }

    public String getTenSanPham() {
        return tenSanPham;
    }

    public void setTenSanPham(String tenSanPham) {
        this.tenSanPham = tenSanPham;
    }

    public LoaiSanPham getLoaiSanPham() {
        return loaiSanPham;
    }

    public void setLoaiSanPham(LoaiSanPham loaiSanPham) {
        this.loaiSanPham = loaiSanPham;
    }

    @NonNull
    @Override
    public String toString() {
        return maSanPham +" "+ tenSanPham + " "+ giaSanPham+" "+ hinhanhSanPham + " "+ loaiSanPham.getTenLoaiSanPham();
    }
}
