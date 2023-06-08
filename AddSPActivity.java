package com.example.myapplication.ui.qlsp;

import static com.example.myapplication.Database.DAO_LoaiSanPham.checkLoaiSanPham;
import static com.example.myapplication.Database.DAO_LoaiSanPham.saveDataLoaiSanPham;
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
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
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
import android.widget.Toast;

import com.example.myapplication.Model.LoaiSanPham;
import com.example.myapplication.Model.SanPham;
import com.example.myapplication.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class AddSPActivity extends AppCompatActivity {
    Button btn_themmaloai, btn_themsanpham, btn_themhinhanh;
    byte[] byteArray;
    ImageView img_hinhanhSP;
    EditText txt_tensp, txt_giasp;
    Spinner spinner_LoaiSP;

    private static final int My_Request_Code = 10;
    public static final String TAG = AddSPActivity.class.getName();
    Realm realm;
    private ArrayList<LoaiSanPham> listLoaiSanPham = new ArrayList<>();

    ActivityResultLauncher<Intent> mActivityResultLauncher = registerForActivityResult(new
            ActivityResultContracts.StartActivityForResult(), new
            ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    Log.e(TAG, "onActivityResult");
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        if (data == null) {
                            return;
                        }
                        Uri uri = data.getData();
                        try {

                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);

                            ByteArrayOutputStream stream = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                            byteArray = stream.toByteArray();

                            img_hinhanhSP.setImageBitmap(bitmap);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_spactivity);

        btn_themmaloai = findViewById(R.id.btn_themmaloaisanpham);
        spinner_LoaiSP = findViewById(R.id.maloaisanpham_spinner);
        btn_themhinhanh = findViewById(R.id.btn_themhinanhsanpham);
        img_hinhanhSP = findViewById(R.id.hinanhSP_upload);
        btn_themsanpham = findViewById(R.id.btn_themSanPham);
        txt_tensp = findViewById(R.id.txt_tensp);
        txt_giasp = findViewById(R.id.txt_giasp);

        realm = Realm.getDefaultInstance();

        RealmResults<LoaiSanPham> results = realm.where(LoaiSanPham.class).distinct("tenLoaiSanPham").findAll();
        List<LoaiSanPham> loaiSanPhamList = new ArrayList<>(results);
        List<String> tenLoaiSanPhamList = new ArrayList<>();
        for (LoaiSanPham loaiSanPham : results) {
            tenLoaiSanPhamList.add(loaiSanPham.getTenLoaiSanPham());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, tenLoaiSanPhamList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner_LoaiSP.setAdapter(adapter);


        btn_themmaloai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogAdd();
                adapter.notifyDataSetChanged();
            }
        });

        btn_themhinhanh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickRequestPermisson();
            }
        });
        btn_themsanpham.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int loaiSanPham=0;
                String tensp = txt_tensp.getText().toString().trim();
                int giasp = Integer.parseInt(txt_giasp.getText().toString().trim());
                loaiSanPham=loaiSanPhamList.get(spinner_LoaiSP.getSelectedItemPosition()).getMaLoaiSanPham();
                String tenLoaiSP=loaiSanPhamList.get(spinner_LoaiSP.getSelectedItemPosition()).getTenLoaiSanPham();

                Realm realm=Realm.getDefaultInstance();

                if (tensp.equals("")||loaiSanPham == 0) {
                    Toast.makeText(AddSPActivity.this, "Không được để trống", Toast.LENGTH_SHORT).show();
                } else if (giasp < 0) {
                    Toast.makeText(AddSPActivity.this, "Giá không được âm", Toast.LENGTH_SHORT).show();
                }else if(checkTenSanPham(tensp))
                {
                    Toast.makeText(AddSPActivity.this, "Tên sản phẩm đã có rồi", Toast.LENGTH_SHORT).show();
                } else if (byteArray == null) {
                    Toast.makeText(AddSPActivity.this, "Vui lòng chọn ảnh sản phẩm", Toast.LENGTH_SHORT).show();
                }
                else {
                    saveDataSanPham(tensp, giasp, byteArray, loaiSanPham);


                    Toast.makeText(AddSPActivity.this, "Thêm thành công", Toast.LENGTH_SHORT).show();

                }
                realm.close();
            }
        });

    }

    private void onClickRequestPermisson() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            openGallery();
            return;
        }
        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            openGallery();
        } else {
            String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
            requestPermissions(permissions, My_Request_Code);

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grandResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grandResults);
        if (requestCode == My_Request_Code) {
            if (grandResults.length > 0 && grandResults[0] == PackageManager.PERMISSION_GRANTED) {
                openGallery();
            }
        }
    }


    private void openGallery() {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);

        mActivityResultLauncher.launch(Intent.createChooser(i, "Select Picture"));
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        // Đóng Realm
        realm.close();
    }

    private void DialogAdd() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_addloaisanpham);
        dialog.setCancelable(true);

        EditText txt_tenloai = dialog.findViewById(R.id.txt_tenloai);
        Button btn_addmaLoai = dialog.findViewById(R.id.btn_addmaloai);


        btn_addmaLoai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tl = txt_tenloai.getText().toString().trim();
                if (tl.equals("")) {
                    Toast.makeText(AddSPActivity.this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                } else if (checkLoaiSanPham(tl)) {
                    Toast.makeText(AddSPActivity.this, "Tên loại đã được sử dụng", Toast.LENGTH_SHORT).show();
                } else {
                    saveDataLoaiSanPham(tl);

                    Toast.makeText(AddSPActivity.this, "Thêm loại sản phẩm thành công", Toast.LENGTH_SHORT).show();


                }
                dialog.dismiss();
            }
        });


        dialog.show();
    }
}
