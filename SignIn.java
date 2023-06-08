package com.example.myapplication;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Model.Users;
import com.google.android.material.navigation.NavigationView;

import io.realm.Realm;
import io.realm.RealmResults;

public class SignIn extends AppCompatActivity {

    EditText txt_tk, txt_mk;

    Button btn_DangNhap, btn_DangKy;
    Realm realm;Context context;
    Menu navMenu;MenuItem menuItem;NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        btn_DangKy = findViewById(R.id.btn_signup);
        btn_DangNhap = findViewById(R.id.btn_login);









        txt_tk=findViewById(R.id.edit_user);
        txt_mk=findViewById(R.id.edit_password);


        btn_DangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent i = new Intent(SignIn.this, SignUp.class);
                startActivity(i);
            }
        });

        btn_DangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tk = txt_tk.getText().toString().trim();
                String mk = txt_mk.getText().toString().trim();
                realm = Realm.getDefaultInstance();
                Users user = realm.where(Users.class).equalTo("tk", tk).findFirst();



                if (user != null  ){
                    if(user.getMk().equals(mk)) {
                        if(user.getChucVu().equals("Nhân viên"))
                        {

                            SharedPreferences preferences = getSharedPreferences("User", MODE_PRIVATE);
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putInt("user_id", user.getUser_id());
                            editor.apply();
                            Intent i = new Intent(SignIn.this, MonitorCF.class);
                            i.putExtra("userType", "Nhân viên");

                                System.out.println("Nhân viên");

                            startActivity(i);

                        }
                        else {
                            SharedPreferences preferences = getSharedPreferences("User", MODE_PRIVATE);
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putInt("user_id", user.getUser_id());
                            editor.apply();
                            Intent i = new Intent(SignIn.this, MonitorCF.class);
                            startActivity(i);

                            Toast.makeText(SignIn.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                        }




                    }
                    else
                    {
                        Toast.makeText(SignIn.this, "Sai mật khẩu", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Intent i = new Intent(SignIn.this, SignUp.class);
                    startActivity(i);
                    Toast.makeText(SignIn.this, "Tài khoản không tồn tại", Toast.LENGTH_SHORT).show();
                }


            }

        });


    }

}