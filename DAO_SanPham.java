package com.example.myapplication.Database;

import static io.realm.Realm.getApplicationContext;

import android.widget.Toast;

import com.example.myapplication.Model.LoaiSanPham;
import com.example.myapplication.Model.SanPham;

import java.text.NumberFormat;
import java.util.Locale;

import io.realm.Realm;
import io.realm.RealmResults;

public class DAO_SanPham {

    public static Boolean checkTenSanPham(String tensanpham) {
        Realm realm = Realm.getDefaultInstance();
        SanPham sp = realm.where(SanPham.class).equalTo("tenSanPham", tensanpham).findFirst();
        if (sp != null) {
            return true;
        } else {
            return false;
        }
    }

    public static void saveDataSanPham(String tensp, double giaSP, byte[] sp1, int loaiSanPhamId) {
        Realm realm=Realm.getDefaultInstance();
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm bgrealm) {
                Number maxID = bgrealm.where(SanPham.class).max("maSanPham");
                int newkey = (maxID == null) ? 1 : maxID.intValue() + 1;


                SanPham sp = bgrealm.createObject(SanPham.class, newkey);

                sp.setTenSanPham(tensp);
                sp.setGiaSanPham(giaSP);
                sp.setHinhanhSanPham(sp1);
                LoaiSanPham loaiSanPham =bgrealm.where(LoaiSanPham.class).equalTo("maLoaiSanPham", loaiSanPhamId).findFirst();

                sp.setLoaiSanPham(loaiSanPham);


            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {

                Toast.makeText(getApplicationContext(),"Thêm sản phẩm thành công",Toast.LENGTH_SHORT).show();


            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {

                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
                System.out.println(error);

            }
        });
        realm.close();
    }

    public static void xoaDataSanPham(int id_sp)
    {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm bgrealm) {
                // Tìm đối tượng cần xóa theo id
                SanPham sp = bgrealm.where(SanPham.class).equalTo("maSanPham", id_sp).findFirst();
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
    public static void capnhatDataSanPham(int sanpham_id,String tensp,int giaSP,byte[] hinhanh,int loaispId)
    {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm bgrealm) {
                // Tìm đối tượng cần sửa theo id
                SanPham sp = bgrealm.where(SanPham.class).equalTo("maSanPham", sanpham_id).findFirst();
                if (sp != null) {
                    // Thiết lập các thuộc tính mới cho đối tượng
                    sp.setTenSanPham(tensp);
                    sp.setGiaSanPham(giaSP);
                    sp.setHinhanhSanPham(hinhanh);
                    LoaiSanPham loaiSanPham = bgrealm.where(LoaiSanPham.class).equalTo("maLoaiSanPham", loaispId).findFirst();

                    sp.setLoaiSanPham(loaiSanPham);
                }
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                Toast.makeText(getApplicationContext(), "Sửa thành công", Toast.LENGTH_SHORT).show();
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}
