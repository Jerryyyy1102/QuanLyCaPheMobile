package com.example.myapplication.Model;

import static com.example.myapplication.Model.Users.luongCB;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Work extends RealmObject {

    @PrimaryKey
    int worktime_id;
    Date gioBatDau;
    Date gioKetThuc;
    Users users;

    public Work(Date gioBatDau, Date gioKetThuc, Users users,int worktime) {
        this.gioBatDau = gioBatDau;
        this.gioKetThuc = gioKetThuc;
        this.users = users;
        this.worktime_id =worktime;
    }

    public Work() {
    }

    public int getWorktime_id() {
        return worktime_id;
    }

    public void setWorktime_id(int worktime_id) {
        this.worktime_id = worktime_id;
    }

    public Date getGioBatDau() {
        return gioBatDau;
    }

    public void setGioBatDau(Date gioBatDau) {
        this.gioBatDau = gioBatDau;
    }

    public Date getGioKetThuc() {
        return gioKetThuc;
    }

    public void setGioKetThuc(Date gioKetThuc) {
        this.gioKetThuc = gioKetThuc;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public double tinhGioLamViec() {
        long thoiGianLamViecMillis = gioKetThuc.getTime() -gioBatDau.getTime(); // Số millisecond của thời gian làm việc
        users.setTongGioLamViec1ngay((double) thoiGianLamViecMillis / (1000 * 60 * 60));
//
        return users.getTongGioLamViec1ngay();
    }
    public double tinhLuongLamViec1ngay() {

        users.setTongLuong(tinhGioLamViec() * users.getLuongCB());
        return users.getTongLuong();
    }
}
