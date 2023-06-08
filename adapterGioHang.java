package com.example.myapplication.Adapter;

import static android.content.Context.MODE_PRIVATE;

import static com.example.myapplication.Database.DAO_GioHang.capnhatDataGioHang;
import static com.example.myapplication.Database.DAO_GioHang.saveDataGioHang;
import static com.example.myapplication.Database.DAO_GioHang.xoaDataGioHang;
import static com.example.myapplication.Database.DAO_HoaDon.saveDataHoaDon;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Model.ChiTietHoaDon;
import com.example.myapplication.Model.GioHang;
import com.example.myapplication.Model.HoaDon;
import com.example.myapplication.Model.SanPham;
import com.example.myapplication.Model.Users;
import com.example.myapplication.R;
import com.example.myapplication.ui.trangchu.GioHangMainActivity;
import com.example.myapplication.ui.trangchu.TrangchuFragment;

import java.text.DecimalFormat;

import io.realm.Realm;
import io.realm.RealmResults;

public class adapterGioHang extends RecyclerView.Adapter<adapterGioHang.GioHangHolder> {

    private RealmResults<GioHang> ListGH;
    public Context context;
    protected double tongGiaTri;

    public adapterGioHang(Context Context, RealmResults<GioHang> Listgh) {
        context = Context;
        ListGH = Listgh;
    }

    @NonNull
    @Override
    public GioHangHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemcoffee_giohang, parent, false);
        return new GioHangHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull GioHangHolder holder, int position) {
        GioHang gh = ListGH.get(position);
        holder.bindData(gh);
    }

    @Override
    public int getItemCount() {
        return ListGH.size();
    }

    class GioHangHolder extends RecyclerView.ViewHolder {
        private TextView txt_tenSP, txt_giaSP, txt_sl, txt_tongtien;
        private ImageView img_hinhSP;

        private Button btn_plus, btn_minus, btn_ThanhToan;

        private RecyclerView rcv_giohang, btn_giohang;


        public GioHangHolder(@NonNull View itemView) {
            super(itemView);
            txt_tenSP = itemView.findViewById(R.id.txt_NameCoffee_giohang);
            txt_giaSP = itemView.findViewById(R.id.txt_PriceCoffee_giohang);
            txt_sl = itemView.findViewById(R.id.txt_soluong);
            img_hinhSP = itemView.findViewById(R.id.img_hinhanh_giohang);
            txt_tongtien = ((GioHangMainActivity) context).findViewById(R.id.giatien_giohang);
            rcv_giohang=((GioHangMainActivity) context).findViewById(R.id.rcv_giohang);


            btn_plus = itemView.findViewById(R.id.btn_plus);
            btn_minus = itemView.findViewById(R.id.btn_minus);
            btn_ThanhToan = ((GioHangMainActivity) context).findViewById(R.id.btn_ThanhToan_giohang);


        }

        public void bindData(GioHang gioHang) {
            SharedPreferences preferences = context.getSharedPreferences("User", MODE_PRIVATE);
            int userId = preferences.getInt("user_id", 0);

            DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
            byte[] imageBytes = gioHang.getSanPham().getHinhanhSanPham();
            Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
            txt_tenSP.setText(gioHang.getSanPham().getTenSanPham());
            img_hinhSP.setImageBitmap(bitmap);
            txt_sl.setText(String.valueOf(gioHang.getSoLuong()));


            double gia = gioHang.getSanPham().getGiaSanPham();
            txt_giaSP.setText(decimalFormat.format(gia));

            double tonggiatrihientai=0.0;
            updateTotalPrice(tonggiatrihientai);


            btn_plus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int slmoinhat = Integer.parseInt(txt_sl.getText().toString()) + 1;
                    txt_sl.setText(String.valueOf(slmoinhat));
                    double gia = gioHang.getSanPham().getGiaSanPham();
                    double giaTriSPMoi = gia * slmoinhat;
                    capnhatDataGioHang(gioHang.getMaGioHang(), "", slmoinhat);
                    double tongGiaTriMoi = 0.0;
                   updateTotalPrice(tongGiaTriMoi);
                    txt_giaSP.setText(decimalFormat.format(giaTriSPMoi));
                    System.out.println(tongGiaTriMoi);

                }

            });

            btn_minus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int slmoinhat = Integer.parseInt(txt_sl.getText().toString()) - 1;
                    if (slmoinhat < 1) {
                        GioHang gh = (GioHang) ListGH.get(getAdapterPosition());
                        xoaDataGioHang(gh.getMaGioHang());
                        notifyItemRemoved(getAdapterPosition());
                        notifyItemRangeChanged(getAdapterPosition(), ListGH.size());
                        rcv_giohang.scrollToPosition(ListGH.size());
                        double tonggiatrimoi=0;
                        updateTotalPrice(tonggiatrimoi);
                    } else {
                        txt_sl.setText(String.valueOf(slmoinhat));
                        double gia = gioHang.getSanPham().getGiaSanPham();
                        double giaTriSPMoi = gia * slmoinhat;
                        capnhatDataGioHang(gioHang.getMaGioHang(), "", slmoinhat);
                        double tongGiaTriMoi = 0.0;
                        updateTotalPrice(tongGiaTriMoi);
                        txt_giaSP.setText(decimalFormat.format(giaTriSPMoi));
                        System.out.println(tongGiaTriMoi);
                    }


                }


            });


            btn_ThanhToan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    saveDataHoaDon(userId);
                    rcv_giohang.scrollToPosition(ListGH.size());
                    tongGiaTri = 0.0;
                    txt_tongtien.setText("0");
                    RealmResults<HoaDon> hd = Realm.getDefaultInstance().where(HoaDon.class).findAll();
                    RealmResults<ChiTietHoaDon> cthd = Realm.getDefaultInstance().where(ChiTietHoaDon.class).findAll();
                    System.out.println(cthd + "" + hd);
                }
            });


        }

        private void updateTotalPrice(double total) {
            DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
            total = 0.0;
            for (GioHang GH : ListGH) {
                double giaSP = GH.getSanPham().getGiaSanPham();
                int soLuong = GH.getSoLuong();
                double giaTriSP = giaSP * soLuong;
                total += giaTriSP;
            }
            tongGiaTri = total;
            txt_tongtien.setText(decimalFormat.format(tongGiaTri));
        }


    }
}
