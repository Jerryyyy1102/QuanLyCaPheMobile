package com.example.myapplication.Database;

import static android.content.Context.MODE_PRIVATE;

import static io.realm.Realm.getApplicationContext;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.myapplication.Model.Users;
import com.example.myapplication.Model.Work;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import io.realm.Realm;

public class DAO_Work {
    public static void saveDataWork(Date gioBatDau, int users_id) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm bgrealm) {
                long count = bgrealm.where(Work.class).count();

                int newkey = (int) (count + 1);

                Work work = bgrealm.createObject(Work.class, newkey);

                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                if (gioBatDau != null) {
                    work.setGioBatDau(gioBatDau);
                }

                Users users = bgrealm.where(Users.class).equalTo("user_id", users_id).findFirst();
                work.setUsers(users);

                double luongLamViec = work.tinhLuongLamViec1ngay();
                work.getUsers().setTongLuong(luongLamViec);


            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                System.out.println("thêm thành công");
                SharedPreferences preferences = getApplicationContext().getSharedPreferences("User", MODE_PRIVATE);
                int userId = preferences.getInt("user_id", 0);

                Users users = realm.where(Users.class).equalTo("user_id", userId).findFirst();
                Work work=realm.where(Work.class).equalTo("users.user_id",users.getUser_id()).findFirst();
                SharedPreferences preferences1 = getApplicationContext().getSharedPreferences("Work", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences1.edit();
                editor.putInt("worktime_id", work.getWorktime_id());
                editor.apply();
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                System.out.println(error);
            }
        });
    }

    public static void capnhatgioKetThuc(Date gioKetThuc, int work_id) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm bgrealm) {
                // Tìm đối tượng cần sửa theo id
                Work work = bgrealm.where(Work.class).equalTo("worktime_id", work_id).findFirst();
                if (work != null) {
                    // Cập nhật giờ kết thúc
                    work.setGioKetThuc(gioKetThuc);

                    // Lấy đối tượng Users liên quan
                    Users users = work.getUsers();
                    if (users != null) {
                        // Cập nhật giờ kết thúc và tổng lương

                        double luongLamViec = work.tinhLuongLamViec1ngay();
                        users.setTongLuong(luongLamViec);
                    }
                }
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                System.out.println("Cập nhật thành công");
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                System.out.println(error);
            }
        });
    }
}
