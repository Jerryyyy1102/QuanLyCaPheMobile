package com.example.myapplication.Model;

import androidx.annotation.NonNull;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class LoaiSanPham extends RealmObject {
    @PrimaryKey
    private int maLoaiSanPham;
    private String tenLoaiSanPham;
    private RealmList<SanPham> sanPhams; // danh sách các sản phẩm thuộc loại sản phẩm này

    public int getMaLoaiSanPham() {
        return maLoaiSanPham;
    }



    public String getTenLoaiSanPham() {
        return tenLoaiSanPham;
    }

    public void setTenLoaiSanPham(String tenLoaiSanPham) {
        this.tenLoaiSanPham = tenLoaiSanPham;
    }

    public RealmList<SanPham> getSanPhams() {
        return sanPhams;
    }

    public void setSanPhams(RealmList<SanPham> sanPhams) {
        this.sanPhams = sanPhams;
    }

    @NonNull
    @Override
    public String toString() {
        return getTenLoaiSanPham();
    }
}
