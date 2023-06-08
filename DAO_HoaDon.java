package com.example.myapplication.Database;


import static io.realm.Realm.getApplicationContext;

import android.content.Intent;
import android.widget.Toast;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.example.myapplication.Model.ChiTietHoaDon;
import com.example.myapplication.Model.GioHang;
import com.example.myapplication.Model.HoaDon;
import com.example.myapplication.Model.LoaiSanPham;
import com.example.myapplication.Model.SanPham;
import com.example.myapplication.Model.Users;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

public class DAO_HoaDon {
    public static void saveDataHoaDon(int userId) {
        Realm realm = Realm.getDefaultInstance();

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm hdRealm) {
                Number maxID = hdRealm.where(HoaDon.class).max("maHoaDon");
                int newkey = (maxID == null) ? 1 : maxID.intValue() + 1;
                Users users = hdRealm.where(Users.class).equalTo("user_id", userId).findFirst();
                RealmResults<GioHang> gioHangList=hdRealm.where(GioHang.class).findAll();
                HoaDon hoaDon = hdRealm.createObject(HoaDon.class, newkey);
                hoaDon.setNgayLap(new Date());
                hoaDon.setTenNV(users.getHoTen());
                hoaDon.setThanhTien(0);
                hoaDon.setChecktrangthai(false);

                // Lặp qua danh sách sản phẩm trong Giỏ hàng và tạo các đối tượng Chi tiết hóa đơn
                for (GioHang gioHang : gioHangList) {
                    SanPham sanPham = gioHang.getSanPham();
                    ChiTietHoaDon chiTietHoaDon = hdRealm.createObject(ChiTietHoaDon.class, hoaDon.getMaHoaDon() + "" + sanPham.getMaSanPham());
                    chiTietHoaDon.setMaHoaDon(hoaDon.getMaHoaDon());
                    chiTietHoaDon.setMaSanPham(sanPham.getMaSanPham());
                    chiTietHoaDon.setGhiChu(gioHang.getGhichu());
                    chiTietHoaDon.setSoLuong(gioHang.getSoLuong());
                    chiTietHoaDon.setTongTien((int) (sanPham.getGiaSanPham() * gioHang.getSoLuong()));

                    // Cập nhật tổng tiền của Hóa đơn
                    hoaDon.setThanhTien(hoaDon.getThanhTien() + chiTietHoaDon.getTongTien());
                }

                    // Xóa các đối tượng GioHang sau khi tạo Hóa đơn và Chi tiết hóa đơn thành công
                gioHangList.deleteAllFromRealm();
            }



        });


    }
    public static void capnhattrangthaiHoaDon(int maHD) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm bgrealm) {
                // Tìm đối tượng cần sửa theo id
                HoaDon sp = bgrealm.where(HoaDon.class).equalTo("maHoaDon", maHD).findFirst();
                if (sp != null) {
                    // Thiết lập các thuộc tính mới cho đối tượng
                    sp.setChecktrangthai(true);
                }
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                Intent intent = new Intent("com.example.myapplication.ui.trangchu.ChiTietHoaDon");
                LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
            }
        });
    }
}
