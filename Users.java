package com.example.myapplication.Model;


import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Users extends RealmObject {
    String tk;
    String mk;
    String chucVu;
    double tongLuong, tongGioLamViec1ngay;
    public static double luongCB = 25000;

    String nhaplaimk, hoTen, gioiTinh, ngaySinh, SDT;
    @PrimaryKey
    int user_id;

    public Users() {
    }

    public Users(String tk, String mk, String chucVu, double luongCB, double tongLuong, double tongGioLamViec1ngay, String nhaplaimk, String hoTen, String gioiTinh, String ngaySinh, String SDT, int user_id) {
        this.tk = tk;
        this.mk = mk;
        this.chucVu = chucVu;
        this.luongCB = luongCB;
        this.tongLuong = tongLuong;
        this.tongGioLamViec1ngay = tongGioLamViec1ngay;
        this.nhaplaimk = nhaplaimk;
        this.hoTen = hoTen;
        this.gioiTinh = gioiTinh;
        this.ngaySinh = ngaySinh;
        this.SDT = SDT;
        this.user_id = user_id;
    }

    public String getTk() {
        return tk;
    }

    public void setTk(String tk) {
        this.tk = tk;
    }

    public String getMk() {
        return mk;
    }

    public void setMk(String mk) {
        this.mk = mk;
    }

    public String getChucVu() {
        return chucVu;
    }

    public void setChucVu(String chucVu) {
        this.chucVu = chucVu;
    }

    public double getLuongCB() {
        return luongCB;
    }

    public void setLuongCB(double luongCB) {
        this.luongCB = luongCB;
    }

    public double getTongLuong() {
        return tongLuong;
    }

    public void setTongLuong(double tongLuong) {
        this.tongLuong = tongLuong;
    }

    public double getTongGioLamViec1ngay() {
        return tongGioLamViec1ngay;
    }

    public void setTongGioLamViec1ngay(double tongGioLamViec1ngay) {
        this.tongGioLamViec1ngay = tongGioLamViec1ngay;
    }


    public String getNhaplaimk() {
        return nhaplaimk;
    }

    public void setNhaplaimk(String nhaplaimk) {
        this.nhaplaimk = nhaplaimk;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(String gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public String getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(String ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public String getSDT() {
        return SDT;
    }

    public void setSDT(String SDT) {
        this.SDT = SDT;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }




}
