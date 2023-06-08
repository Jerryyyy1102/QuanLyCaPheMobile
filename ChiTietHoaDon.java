package com.example.myapplication.Model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class ChiTietHoaDon extends RealmObject {
    private int maHoaDon,maSanPham;
    
    @PrimaryKey
    private String maCTHD;

    public String getmaCTHD() {
        return maCTHD;
    }

    public void setmaCTHD() {
        this.maCTHD = this.maHoaDon+" "+this.maSanPham;
    }


    private String ghiChu;
    private int soLuong,tongTien;

    public ChiTietHoaDon() {
    }

    public ChiTietHoaDon(int maHoaDon, int maSanPham, String ghiChu, int soLuong, int tongTien) {
        this.maHoaDon = maHoaDon;
        this.maSanPham = maSanPham;
        this.ghiChu = ghiChu;
        this.soLuong = soLuong;
        this.tongTien = tongTien;
    }

    public int getMaHoaDon() {
        return maHoaDon;
    }

    public void setMaHoaDon(int maHoaDon) {
        this.maHoaDon = maHoaDon;
    }

    public int getMaSanPham() {
        return maSanPham;
    }

    public void setMaSanPham(int maSanPham) {
        this.maSanPham = maSanPham;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public int getTongTien() {
        return tongTien;
    }

    public void setTongTien(int tongTien) {
        this.tongTien = tongTien;
    }
}
