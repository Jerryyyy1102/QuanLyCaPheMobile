package com.example.myapplication.ui.trangchu;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Adapter.adapterLoaiSanPham;
import com.example.myapplication.Adapter.adapterTrangChu;
import com.example.myapplication.Model.LoaiSanPham;
import com.example.myapplication.Model.SanPham;
import com.example.myapplication.R;
import com.example.myapplication.ui.qlsp.AddSPActivity;

import java.util.zip.Inflater;

import io.realm.Realm;
import io.realm.RealmResults;


public class TrangchuFragment extends Fragment {

    Toolbar toolbar;
    RecyclerView recyclerSP, recyclerLoaiSP;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_trangchu, container, false);
        toolbar = view.findViewById(R.id.toolbar_trangchu);
        recyclerSP = view.findViewById(R.id.recycleview_Coffee);

        recyclerSP.setLayoutManager(new LinearLayoutManager(getContext()));


        recyclerLoaiSP = view.findViewById(R.id.recycleview_LSP);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 3);
        recyclerLoaiSP.setLayoutManager(layoutManager);


        //Xử lí toolbar
        AppCompatActivity compatActivity = (AppCompatActivity) getActivity();
        compatActivity.setSupportActionBar(toolbar);
        compatActivity.getSupportActionBar().setTitle("Trang chủ");


        Realm realm = Realm.getDefaultInstance();

        RealmResults<SanPham> data = realm.where(SanPham.class).findAll();
        LoaiSanPham loaiSanPham=realm.where(LoaiSanPham.class).findFirst();
        RealmResults<LoaiSanPham> dataloai = realm.where(LoaiSanPham.class).findAll();


        adapterTrangChu trangChu = new adapterTrangChu(data);

        adapterLoaiSanPham sanPham = new adapterLoaiSanPham(dataloai,new TrangchuFragment());

        recyclerSP.setAdapter(trangChu);
        recyclerLoaiSP.setAdapter(sanPham);



        realm.close();



        return view;
    }

    public RecyclerView getRecyclerView() {
        return recyclerSP;
    }

    public void showList(int id,View v) {
        if (!isAdded()) {
            return;
        }
        RecyclerView rcvSP=v.findViewById(R.id.recycleview_Coffee);
        RealmResults<SanPham> data1 = Realm.getDefaultInstance().where(SanPham.class).equalTo("loaiSanPham.maLoaiSanPham", id).findAll();
        adapterTrangChu trangChu = new adapterTrangChu(data1);
        rcvSP.setAdapter(trangChu);
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_addgiohang, menu);
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
            case R.id.menu_add_sp_giohang:

                Intent i = new Intent(this.getContext(), GioHangMainActivity.class);
                startActivity(i);
                break;
            default:

                break;
        }
        return super.onOptionsItemSelected(menuItem);
    }
}