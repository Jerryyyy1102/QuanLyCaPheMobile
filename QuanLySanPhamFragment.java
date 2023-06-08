package com.example.myapplication.ui.qlsp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Model.LoaiSanPham;
import com.example.myapplication.Model.SanPham;
import com.example.myapplication.Adapter.adapterSanPham;
import com.example.myapplication.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import io.realm.Realm;
import io.realm.RealmResults;

public class QuanLySanPhamFragment extends Fragment {
    Toolbar toolbar;
    RecyclerView recyclerView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_q_ly_san_pham, container, false);
        toolbar = view.findViewById(R.id.toolbar_qlspham);
        recyclerView = view.findViewById(R.id.recycleView_SP);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        //Xử lí toolbar
        AppCompatActivity compatActivity = (AppCompatActivity) getActivity();
        compatActivity.setSupportActionBar(toolbar);
        compatActivity.getSupportActionBar().setTitle("Quản lý sản phẩm");

        BottomNavigationView bottomNavigationView1w = getActivity().findViewById(R.id.Bottom_nv_view);
        bottomNavigationView1w.setVisibility(View.VISIBLE);

        Realm realm = Realm.getDefaultInstance();
        RealmResults<SanPham> data = realm.where(SanPham.class).findAll();


        adapterSanPham adapterSanPham = new adapterSanPham(data);
        recyclerView.setAdapter(adapterSanPham);
        recyclerView.scrollToPosition(data.size());


        realm.close();
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_addsanpham, menu);
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
            case R.id.menu_add_sp:

                Intent i = new Intent(this.getContext(), AddSPActivity.class);
                startActivity(i);
                break;
            default:

                break;
        }
        return super.onOptionsItemSelected(menuItem);
    }




}

