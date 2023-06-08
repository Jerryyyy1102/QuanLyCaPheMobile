package com.example.myapplication.Model;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class LuongNhanVien extends RealmObject {

    @PrimaryKey
    private int maLuongNV;

    private long ThanhTien;
    private Date NgayTra;
    private String tenNV;
    boolean checktrangthai;

    public LuongNhanVien(int maLuongNV, long thanhTien, Date ngayTra, String tenNV, boolean checktrangthai) {
        this.maLuongNV = maLuongNV;
        ThanhTien = thanhTien;
        NgayTra = ngayTra;
        this.tenNV = tenNV;
        this.checktrangthai = checktrangthai;
    }

    public LuongNhanVien() {
    }

    public int getMaLuongNV() {
        return maLuongNV;
    }

    public void setMaLuongNV(int maLuongNV) {
        this.maLuongNV = maLuongNV;
    }

    public long getThanhTien() {
        return ThanhTien;
    }

    public void setThanhTien(long thanhTien) {
        ThanhTien = thanhTien;
    }

    public Date getNgayTra() {
        return NgayTra;
    }

    public void setNgayTra(Date ngayTra) {
        NgayTra = ngayTra;
    }

    public String getTenNV() {
        return tenNV;
    }

    public void setTenNV(String tenNV) {
        this.tenNV = tenNV;
    }

    public boolean isChecktrangthai() {
        return checktrangthai;
    }

    public void setChecktrangthai(boolean checktrangthai) {
        this.checktrangthai = checktrangthai;
    }
}
