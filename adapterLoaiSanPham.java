package com.example.myapplication.Adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Model.LoaiSanPham;
import com.example.myapplication.Model.SanPham;
import com.example.myapplication.R;
import com.example.myapplication.ui.trangchu.TrangchuFragment;

import java.util.zip.Inflater;

import io.realm.Realm;
import io.realm.RealmResults;

public class adapterLoaiSanPham extends RecyclerView.Adapter<adapterLoaiSanPham.LoaiSanPhamHolder> {

    private static RealmResults<LoaiSanPham> lstLoaiSP;
    Fragment fragment;

    public adapterLoaiSanPham(Fragment fragment) {
        this.fragment = fragment;
    }

    public adapterLoaiSanPham(RealmResults<LoaiSanPham> loaiSanPhams,Fragment Fragment) {
        lstLoaiSP = loaiSanPhams;
        fragment=Fragment;
    }

    @NonNull
    @Override
    public LoaiSanPhamHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.typecoffee, parent, false);
        return new LoaiSanPhamHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull LoaiSanPhamHolder holder, int position) {
        LoaiSanPham data = lstLoaiSP.get(position);
        holder.bindData(data);
    }

    @Override
    public int getItemCount() {
        return lstLoaiSP.size();
    }

    class LoaiSanPhamHolder extends RecyclerView.ViewHolder {

        TextView txt_TenLoaiSP;
        ImageButton btn_imgLoaiSP;

        public LoaiSanPhamHolder(@NonNull View itemView) {
            super(itemView);
            txt_TenLoaiSP = itemView.findViewById(R.id.txt_tenLoaiSP);
            btn_imgLoaiSP = itemView.findViewById(R.id.img_fruittea);

        }

        public void bindData(LoaiSanPham data) {
            txt_TenLoaiSP.setText(data.getTenLoaiSanPham());
            btn_imgLoaiSP.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("TrangchuFragment", "onClick: View: " + view);
                    LoaiSanPham loaiSanPham = (LoaiSanPham) lstLoaiSP.get(getAdapterPosition());
                    if (fragment instanceof TrangchuFragment) {
                        TrangchuFragment trangchuFragment = (TrangchuFragment) fragment;
                        Log.d("TrangchuFragment", "onClick: Fragment: " + trangchuFragment);
                        trangchuFragment.showList(loaiSanPham.getMaLoaiSanPham(), trangchuFragment.getView());
                    }
                }
            });

        }
    }
}
