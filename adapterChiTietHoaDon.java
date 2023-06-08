package com.example.myapplication.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Model.ChiTietHoaDon;
import com.example.myapplication.Model.HoaDon;
import com.example.myapplication.Model.SanPham;
import com.example.myapplication.R;
import com.example.myapplication.ui.trangchu.ChiTietHoaDonActivity;
import com.example.myapplication.ui.trangchu.GioHangMainActivity;

import java.text.DecimalFormat;

import io.realm.Realm;
import io.realm.RealmResults;

public class adapterChiTietHoaDon extends RecyclerView.Adapter<adapterChiTietHoaDon.ChiTietHoaDonHolder> {

    private RealmResults<ChiTietHoaDon> listHoaDon;

    public Context context;
    public adapterChiTietHoaDon(Context conText,RealmResults<ChiTietHoaDon> listHD)
    {
        listHoaDon = listHD;
        context=conText;

    }


    @NonNull
    @Override
    public adapterChiTietHoaDon.ChiTietHoaDonHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chitiethoadon,parent,false);
        return new adapterChiTietHoaDon.ChiTietHoaDonHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull adapterChiTietHoaDon.ChiTietHoaDonHolder holder, int position) {
        ChiTietHoaDon chiTietHoaDon = listHoaDon.get(position);
        holder.BindData(chiTietHoaDon);

    }

    @Override
    public int getItemCount() {
        return listHoaDon.size();
    }

    class ChiTietHoaDonHolder extends RecyclerView.ViewHolder{

        private TextView txt_TongTien, txt_TenSP,txt_SL,txt_TongThanhTien;

        public ChiTietHoaDonHolder(@NonNull View itemView) {
            super(itemView);
            txt_TongTien = itemView.findViewById(R.id.txt_ThanhTien_CTHD);
            txt_SL = itemView.findViewById(R.id.txt_SL_CTHD);
            txt_TenSP = itemView.findViewById(R.id.txt_tenSP_CTHD);
            txt_TongThanhTien=((ChiTietHoaDonActivity) context).findViewById(R.id.txt_tongthanhtien_cthd);

        }

        public void BindData(ChiTietHoaDon chiTietHoaDon)
        {
            DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
            Realm realm=Realm.getDefaultInstance();
            SanPham sanPham = realm.where(SanPham.class).equalTo("maSanPham",chiTietHoaDon.getMaSanPham()).findFirst();
            HoaDon hoaDon=realm.where(HoaDon.class).equalTo("maHoaDon",chiTietHoaDon.getMaHoaDon()).findFirst();
            txt_TongTien.setText(String.valueOf(decimalFormat.format(chiTietHoaDon.getTongTien())));
            txt_SL.setText(String.valueOf(chiTietHoaDon.getSoLuong()));
            txt_TenSP.setText(sanPham.getTenSanPham());
            txt_TongThanhTien.setText(String.valueOf(decimalFormat.format(hoaDon.getThanhTien())));

        }
    }

}
