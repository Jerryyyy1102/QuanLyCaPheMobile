package com.example.myapplication.ui.qlns;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Adapter.adapterNhanSu;
import com.example.myapplication.Model.Users;
import com.example.myapplication.R;
import com.example.myapplication.ui.qlsp.AddSPActivity;

import io.realm.Realm;
import io.realm.RealmResults;


public class QuanLyNhanSuFragment extends Fragment {

    Toolbar toolbar;
    RecyclerView rcv_listNhanSu;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_quan_ly_nhan_su, container, false);

        toolbar = view.findViewById(R.id.toolbar_nhansu);
        rcv_listNhanSu=view.findViewById(R.id.rcv_listNhanVien);
        rcv_listNhanSu.setLayoutManager(new LinearLayoutManager(getContext()));

        AppCompatActivity compatActivity = (AppCompatActivity) getActivity();
        compatActivity.setSupportActionBar(toolbar);
        compatActivity.getSupportActionBar().setTitle("Quản lý nhân viên");


        RealmResults<Users> users= Realm.getDefaultInstance().where(Users.class).equalTo("chucVu","Nhân viên").findAll();
        System.out.println(users);
        adapterNhanSu data=new adapterNhanSu(users);
        rcv_listNhanSu.setAdapter(data);
        Realm.getDefaultInstance().close();

        return view;
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_addnhanvien, menu);
        super.onCreateOptionsMenu(menu, inflater);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.menu_add_nv:

                Intent i = new Intent(this.getContext(), ThemNhanVienActivity.class);
                startActivity(i);
                break;
            default:

                break;
        }
        return super.onOptionsItemSelected(menuItem);
    }
}