package com.example.myapplication.Database;

import static io.realm.Realm.getApplicationContext;

import android.widget.Toast;

import com.example.myapplication.Model.LoaiSanPham;

import io.realm.Realm;

public class DAO_LoaiSanPham {
    public static Boolean checkLoaiSanPham(String tenLoaiSanPham) {
        Realm realm = Realm.getDefaultInstance();
        LoaiSanPham loaisp = realm.where(LoaiSanPham.class).equalTo("tenLoaiSanPham", tenLoaiSanPham).findFirst();
        if (loaisp != null) {
            return true;
        } else {
            return false;
        }
    }

    public static void saveDataLoaiSanPham(String tenLoaiSanPham) {
        Realm realm=Realm.getDefaultInstance();
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm bgrealm) {
                Number maxID = bgrealm.where(LoaiSanPham.class).max("maLoaiSanPham");
                int newkey = (maxID == null) ? 1 : maxID.intValue() + 1;

                LoaiSanPham loaisp = bgrealm.createObject(LoaiSanPham.class, newkey);

                loaisp.setTenLoaiSanPham(tenLoaiSanPham);

            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {

                Toast.makeText(getApplicationContext(),"Thêm loại sản phẩm thành công",Toast.LENGTH_SHORT).show();

            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {

                Toast.makeText(getApplicationContext(), "Thêm loại sản phẩm thất bại", Toast.LENGTH_SHORT).show();

            }
        });
        realm.close();
    }
}
