package com.example.myapplication;

import static com.example.myapplication.Database.DAO_Users.capnhatDataUser;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.myapplication.Model.Users;
import com.example.myapplication.Model.Work;
import com.example.myapplication.R;
import com.example.myapplication.databinding.ActivityUpdateInfoUserBinding;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import io.realm.Realm;

public class UpdateInfoUserActivity extends AppCompatActivity {

    ActivityUpdateInfoUserBinding binding;
    Realm realm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUpdateInfoUserBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        DecimalFormat decimalFormat=new DecimalFormat("###,###,###");
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

        SharedPreferences preferences = getSharedPreferences("User", MODE_PRIVATE);
        int userId = preferences.getInt("user_id", 0);


        realm = Realm.getDefaultInstance();
        Users users = realm.where(Users.class).equalTo("user_id", userId).findFirst();
        Work work=realm.where(Work.class).equalTo("users.user_id",users.getUser_id()).findFirst();

        binding.txtChucVuInfoactivity.setText(users.getChucVu());
        binding.txtMaNVInfoactivity.setText(String.valueOf(users.getUser_id()));
        binding.txtNgaysinhNVInfoactivity.setText(users.getNgaySinh());
        binding.txtSdtNVInfoactivity.setText(users.getSDT());
        binding.txtTenNVInfoactivity.setText(users.getHoTen());
        binding.txtLCBNVInfoactivity.setText(String.valueOf(users.getLuongCB()));
        binding.txtGioitinhNVInfoactivity.setText(users.getGioiTinh());
        binding.txtMKInfoactivity.setText(users.getMk());

        if(work != null)
        {
            binding.txtGiobatdauNVInfoactivity.setText(sdf.format(work.getGioBatDau()));
            binding.txtGioketthucNVInfoactivity.setText(sdf.format(work.getGioKetThuc()));
            binding.txtTonggiolamviecNVInfoactivity.setText(String.valueOf(users.getTongGioLamViec1ngay()));
            binding.txtTongluongNVInfoactivity.setText(String.valueOf(decimalFormat.format(users.getTongLuong())));
        }


        binding.btnSuaInfoactivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.btnLuuInfoactivity.setVisibility(View.VISIBLE);
            }
        });

        binding.btnLuuInfoactivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                capnhatDataUser(binding.txtMKInfoactivity.getText().toString().trim(),binding.txtTenNVInfoactivity.getText().toString().trim(),binding.txtNgaysinhNVInfoactivity.getText().toString().trim(),binding.txtSdtNVInfoactivity.getText().toString().trim(),users.getUser_id(),binding.txtChucVuInfoactivity.getText().toString().trim());
            }
        });



    }
}