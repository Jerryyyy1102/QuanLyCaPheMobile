package com.example.myapplication.Database;

import io.realm.DynamicRealm;
import io.realm.RealmMigration;
import io.realm.RealmSchema;
import io.realm.annotations.PrimaryKey;

public class MyMigration implements RealmMigration {
    @Override
    public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {
        RealmSchema schema = realm.getSchema();
        if (oldVersion == 0) {
            // Thêm trường nhập lại mật khẩu
            schema.get("Users")
                    .addField("nhaplaimk", int.class);
            schema.get("LoaiSanPham");
            schema.get("SanPham");
            schema.get("HoaDon").addField("checktrangthai",boolean.class);
            schema.get("ChiTietTienLuong").addField("maWork",int.class);
            schema.get("ChiTietTienLuong").addField("maNhanVien",int.class);
            schema.get("ChiTietTienLuong").addField("ctTL",String.class);
            schema.get("ChiTietTienLuong").addField("soNgay",int.class);
            schema.get("ChiTietTienLuong").addField("tongtien",int.class);
            oldVersion++;

        }
    }
}
