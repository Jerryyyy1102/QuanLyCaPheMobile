package com.example.myapplication.ui.qlsp;

import static com.example.myapplication.Database.DAO_SanPham.capnhatDataSanPham;
import static com.example.myapplication.Database.DAO_SanPham.checkTenSanPham;
import static com.example.myapplication.Database.DAO_SanPham.saveDataSanPham;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Model.LoaiSanPham;
import com.example.myapplication.Model.SanPham;
import com.example.myapplication.Model.Users;
import com.example.myapplication.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class UpdateSPActivity extends AppCompatActivity {
    EditText txt_tensp, txt_giasp;
    ImageView img_hinhanhSP;

    Button btn_luu, btn_themhinhanh;

    Spinner upd_loaisp;
    Realm realm;
    private ArrayList<LoaiSanPham> listLoaiSanPham = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_spactivity);

        txt_tensp = findViewById(R.id.txt_tensp_update);
        txt_giasp = findViewById(R.id.txt_giasp_update);
        btn_luu = findViewById(R.id.btn_suaSanPham);
        btn_themhinhanh = findViewById(R.id.btn_suahinanhsanpham);
        upd_loaisp = findViewById(R.id.maloaisanpham_spinner_update);
        img_hinhanhSP=findViewById(R.id.hinanhSP_upload_update);


        realm = Realm.getDefaultInstance();

        RealmResults<LoaiSanPham> results = realm.where(LoaiSanPham.class).distinct("tenLoaiSanPham").findAll();
        List<LoaiSanPham> loaiSanPhamList = new ArrayList<>(results);
        List<String> tenLoaiSanPhamList = new ArrayList<>();
        for (LoaiSanPham loaiSanPham : results) {
            tenLoaiSanPhamList.add(loaiSanPham.getTenLoaiSanPham());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, tenLoaiSanPhamList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        upd_loaisp.setAdapter(adapter);


        //Hiển thị thông tin theo mã sản phẩm
        SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        int sp_Id = preferences.getInt("maSanPham", 0);

        System.out.println(sp_Id);

        Realm realm = Realm.getDefaultInstance();

        SanPham sanPham = realm.where(SanPham.class).equalTo("maSanPham", sp_Id).findFirst();

        byte[] imageBytes = sanPham.getHinhanhSanPham();
        System.out.println(imageBytes);
        Bitmap bitmap2 = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
        System.out.println(bitmap2);
        int loaiSanPhamId = sanPham.getLoaiSanPham().getMaLoaiSanPham(); // Lấy ID loại sản phẩm từ đối tượng sản phẩm hiện tại
        LoaiSanPham loaisanPham = realm.where(LoaiSanPham.class).equalTo("maLoaiSanPham", loaiSanPhamId).findFirst();// Truy vấn loại sản phẩm từ ID tương ứng
        int spinnerPosition = adapter.getPosition(String.valueOf(loaisanPham.getTenLoaiSanPham()));// Thiết lập Adapter cho Spinner upd_loaisp và hiển thị loại sản phẩm tương ứng

        img_hinhanhSP.setImageBitmap(bitmap2);
        txt_tensp.setText(sanPham.getTenSanPham());
        txt_giasp.setText(String.valueOf(sanPham.getGiaSanPham()));
        upd_loaisp.setSelection(spinnerPosition);


        btn_luu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int loaiSanPham = 0;
                String tensp = txt_tensp.getText().toString().trim();
                int giasp = Integer.parseInt(txt_giasp.getText().toString().trim());

                loaiSanPham = loaiSanPhamList.get(upd_loaisp.getSelectedItemPosition()).getMaLoaiSanPham();


                if (tensp.equals("") || loaiSanPham == 0) {
                    Toast.makeText(UpdateSPActivity.this, "Không được để trống", Toast.LENGTH_SHORT).show();
                } else if (giasp < 0) {
                    Toast.makeText(UpdateSPActivity.this, "Giá không được âm", Toast.LENGTH_SHORT).show();
                } else if (checkTenSanPham(tensp)) {
                    Toast.makeText(UpdateSPActivity.this, "Tên sản phẩm đã có rồi", Toast.LENGTH_SHORT).show();
                } else {


                    capnhatDataSanPham(sp_Id, tensp, giasp, imageBytes, loaiSanPham);

                    Toast.makeText(UpdateSPActivity.this, "Thêm thành công", Toast.LENGTH_SHORT).show();

                }
                realm.close();
            }
        });

    }







    @Override
    public void onDestroy() {
        super.onDestroy();
        // Đóng Realm
        realm.close();
    }

}