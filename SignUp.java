package com.example.myapplication;

import static com.example.myapplication.Database.DAO_Users.checkAccount;
import static com.example.myapplication.Database.DAO_Users.checkSDT;
import static com.example.myapplication.Database.DAO_Users.saveDataUser;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class SignUp extends AppCompatActivity {

    Button btn_dki;

    EditText txt_tk, txt_mk, txt_nhaplaimk,txt_sdt,txt_hoten,txt_ngaySinh;
    RadioButton rd_Nam,rd_Nu,rd_Khac;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        btn_dki = findViewById(R.id.btn_signup_signup);
        txt_tk = findViewById(R.id.edit_user_signup);
        txt_mk = findViewById(R.id.edit_password_signup);
        txt_nhaplaimk = findViewById(R.id.edit_Repassword_signup);
        txt_hoten=findViewById(R.id.edit_Hoten_user_signup);
        txt_sdt=findViewById(R.id.edit_SDT_user_signup);
        txt_ngaySinh=findViewById(R.id.edit_ngaysinh_user_signup);

        rd_Nu=findViewById(R.id.rd_Nu);
        rd_Nam=findViewById(R.id.rd_Nam);
        rd_Khac=findViewById(R.id.rd_Khac);



        btn_dki.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tk = txt_tk.getText().toString().trim();
                String mk = txt_mk.getText().toString().trim();
                String nhaplaimk = txt_nhaplaimk.getText().toString().trim();
                String hoten=txt_hoten.getText().toString().trim();
                String ngaysinh=txt_ngaySinh.getText().toString().trim();
                String sdt=txt_sdt.getText().toString().trim();

                String gioiting="";
                if (rd_Nam.isChecked()) {
                    gioiting = "Nam";
                } else if (rd_Nu.isChecked()) {
                    gioiting = "Nữ";
                }
                else if(rd_Khac.isChecked()){
                    gioiting="Khác";
                }



                if(tk.equals("") || mk.equals("") || nhaplaimk.equals("")||hoten.equals("")||sdt.equals("")||ngaysinh.equals("")) {
                    Toast.makeText(SignUp.this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                }
                else if(!mk.equals(nhaplaimk)) {
                    Toast.makeText(SignUp.this, "Mật khẩu không khớp", Toast.LENGTH_SHORT).show();
                }
                else if(checkAccount(tk)) {
                    Toast.makeText(SignUp.this, "Tài khoản đã có người sử dụng", Toast.LENGTH_SHORT).show();
                }
                else if(checkSDT(sdt))
                {
                    Toast.makeText(SignUp.this, "Số điện thoại đã được đăng ký", Toast.LENGTH_SHORT).show();
                }
                else {
                    saveDataUser(tk, mk,"Quản lý",hoten,gioiting,ngaysinh,sdt);
                    Toast.makeText(SignUp.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(SignUp.this, SignIn.class);
                    startActivity(i);
                }
            }
        });
    }

}