package com.example.myapplication.ui.qlns;

import static com.example.myapplication.Database.DAO_Users.checkAccount;
import static com.example.myapplication.Database.DAO_Users.checkSDT;
import static com.example.myapplication.Database.DAO_Users.saveDataUser;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.SignUp;
import com.example.myapplication.databinding.ActivityThemNhanVienBinding;
import com.example.myapplication.databinding.ActivityUpdateInfoUserBinding;

public class ThemNhanVienActivity extends AppCompatActivity {
    ActivityThemNhanVienBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityThemNhanVienBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        binding.btnSignupSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tk=binding.editUserSignup.getText().toString().trim();
                String mk=binding.editPasswordSignup.getText().toString().trim();
                String nhaplaimk=binding.editRepasswordSignup.getText().toString().trim();
                String hotennv=binding.editHotenUserSignup.getText().toString().trim();
                String ngaysinh=binding.editNgaysinhUserSignup.getText().toString().trim();
                String sdt=binding.editSDTUserSignup.getText().toString().trim();

                String gioitinh="";
                if(binding.rdNu.isChecked())
                {
                    gioitinh="Nữ";
                }
                else if(binding.rdNam.isChecked())
                {
                    gioitinh="Nam";
                }
                else
                {
                    gioitinh="Khác";
                }
                if(tk.equals("") || mk.equals("") || nhaplaimk.equals("")||hotennv.equals("")||sdt.equals("")||ngaysinh.equals("")) {
                    Toast.makeText(ThemNhanVienActivity.this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                }
                else if(!mk.equals(nhaplaimk)) {
                    Toast.makeText(ThemNhanVienActivity.this, "Mật khẩu không khớp", Toast.LENGTH_SHORT).show();
                }
                else if(checkAccount(tk)) {
                    Toast.makeText(ThemNhanVienActivity.this, "Tài khoản đã có người sử dụng", Toast.LENGTH_SHORT).show();
                }
                else if(checkSDT(sdt))
                {
                    Toast.makeText(ThemNhanVienActivity.this, "Số điện thoại đã được đăng ký", Toast.LENGTH_SHORT).show();
                }
                else {

                    saveDataUser(tk, mk, "Nhân viên", hotennv, gioitinh, ngaysinh, sdt);
                }
            }
        });
    }
}