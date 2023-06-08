package com.example.myapplication.Database;

import static io.realm.Realm.getApplicationContext;

import android.widget.Toast;

import com.example.myapplication.Model.LoaiSanPham;
import com.example.myapplication.Model.SanPham;
import com.example.myapplication.Model.Users;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import io.realm.Realm;

public class DAO_Users {

    public static Boolean checkAccount(String taikhoan) {
        Realm realm = Realm.getDefaultInstance();
        Users user = realm.where(Users.class).equalTo("tk", taikhoan).findFirst();
        if (user != null) {
            return true;
        } else {
            return false;
        }
    }

    public static Boolean checkSDT(String sdt) {
        Realm realm = Realm.getDefaultInstance();
        Users user = realm.where(Users.class).equalTo("SDT", sdt).findFirst();
        if (user != null) {
            return true;
        } else {
            return false;
        }
    }

    public static void saveDataUser(String tk, String mk, String ChucVu, String hoten, String gioitinh, String NgaySinh, String SDT) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm bgrealm) {
                Number maxID = bgrealm.where(Users.class).max("user_id");
                int newkey = (maxID == null) ? 1 : maxID.intValue() + 1;

                Users users = bgrealm.createObject(Users.class, newkey);

                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                try {
                    Date date = sdf.parse(NgaySinh);

                    users.setTk(tk);
                    users.setMk(mk);
                    users.setGioiTinh(gioitinh);
                    users.setHoTen(hoten);
                    users.setNgaySinh(sdf.format(date));
                    users.setSDT(SDT);
                    users.setChucVu(ChucVu);
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {

                Toast.makeText(getApplicationContext(), "Đăng ký thành công", Toast.LENGTH_SHORT).show();

            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {

                Toast.makeText(getApplicationContext(), "fail", Toast.LENGTH_SHORT).show();

            }
        });
        realm.close();
    }

    public static void xoaDataUser(int id_sp) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm bgrealm) {
                // Tìm đối tượng cần xóa theo id
                Users users = bgrealm.where(Users.class).equalTo("user_id", id_sp).findFirst();
                if (users != null) {
                    // Xóa đối tượng khỏi Realm database
                    users.deleteFromRealm();
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

    public static void capnhatDataUser(String mk, String hoTen, String ngaySinh, String SDT, int user_id, String chucvu) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm bgrealm) {
                // Tìm đối tượng cần sửa theo id
                Users users = bgrealm.where(Users.class).equalTo("user_id", user_id).findFirst();
                if (users != null) {
                    // Thiết lập các thuộc tính mới cho đối tượng
                    users.setHoTen(hoTen);
                    users.setNgaySinh(ngaySinh);
                    users.setMk(mk);
                    users.setSDT(SDT);
                    users.setChucVu(chucvu);
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
