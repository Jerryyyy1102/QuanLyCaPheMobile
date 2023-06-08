package com.example.myapplication.Model;

import androidx.annotation.NonNull;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class GioHang extends RealmObject {
    @PrimaryKey
    private int maGioHang;
    private SanPham sanPham;
    private Users users;
    private String ghichu;

    private int soLuong;




    public GioHang() {
    }

    public GioHang(int maGioHang, SanPham sanPham, Users users, String ghichu, int soLuong) {
        this.maGioHang = maGioHang;
        this.sanPham = sanPham;
        this.users = users;
        this.ghichu = ghichu;
        this.soLuong = soLuong;
    }

    public int getMaGioHang() {
        return maGioHang;
    }

    public void setMaGioHang(int maGioHang) {
        this.maGioHang = maGioHang;
    }

    public SanPham getSanPham() {
        return sanPham;
    }

    public void setSanPham(SanPham sanPham) {
        this.sanPham = sanPham;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public String getGhichu() {
        return ghichu;
    }

    public void setGhichu(String ghichu) {
        this.ghichu = ghichu;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    @NonNull
    @Override
    public String toString() {
        return users.getHoTen();
    }
}
