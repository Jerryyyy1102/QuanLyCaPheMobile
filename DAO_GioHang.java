package com.example.myapplication.Database;

import static io.realm.Realm.getApplicationContext;

import android.widget.Toast;

import com.example.myapplication.Model.GioHang;
import com.example.myapplication.Model.LoaiSanPham;
import com.example.myapplication.Model.SanPham;
import com.example.myapplication.Model.Users;

import io.realm.Realm;
import io.realm.RealmResults;

public class DAO_GioHang {
    public static void saveDataGioHang(int maSp, int sl, int maUser) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm bgrealm) {
                GioHang gioHang = bgrealm.where(GioHang.class)
                        .equalTo("sanPham.maSanPham", maSp).findFirst();

                if (gioHang == null) {
                    // Giỏ hàng chưa tồn tại, tạo mới
                    gioHang = bgrealm.createObject(GioHang.class,generateNewMaGioHang(bgrealm));

                    gioHang.setSanPham(bgrealm.where(SanPham.class).equalTo("maSanPham", maSp).findFirst());
                    gioHang.setUsers(bgrealm.where(Users.class).equalTo("user_id",maUser).findFirst());
                    gioHang.setSoLuong(sl);
                } else {
                    // Giỏ hàng đã tồn tại, cập nhật số lượng
                    gioHang.setSoLuong(gioHang.getSoLuong() + 1);
                }

            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {

                Toast.makeText(getApplicationContext(), "Thêm vào giỏ hàng thành công", Toast.LENGTH_SHORT).show();

            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {

                Toast.makeText(getApplicationContext(), "Thêm vào giỏ hàng thất bại", Toast.LENGTH_SHORT).show();
                System.out.println(error);
            }
        });
        realm.close();
    }

    private static int generateNewMaGioHang(Realm realm) {
        Number maxID = realm.where(GioHang.class).max("maGioHang");
        int newkey = (maxID == null) ? 1 : maxID.intValue() + 1;
        return newkey;
    }

    public static void xoaDataGioHang(int id_GH) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm bgrealm) {
                // Tìm đối tượng cần xóa theo id
                GioHang sp = bgrealm.where(GioHang.class).equalTo("maGioHang", id_GH).findFirst();
                if (sp != null) {
                    // Xóa đối tượng khỏi Realm database
                    sp.deleteFromRealm();
                }
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                Toast.makeText(getApplicationContext(), "Thành công", Toast.LENGTH_SHORT).show();
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    public static void capnhatDataGioHang(int id_GH, String ghichu,int soLuongMoi) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm bgrealm) {
                // Tìm đối tượng cần sửa theo id
                GioHang gioHang= bgrealm.where(GioHang.class).equalTo("maGioHang", id_GH).findFirst();
                if (gioHang != null) {
                    // Thiết lập các thuộc tính mới cho đối tượng
                    gioHang.setSoLuong(soLuongMoi);
                    gioHang.setGhichu(ghichu);

                }


            }
        });

    }


}
