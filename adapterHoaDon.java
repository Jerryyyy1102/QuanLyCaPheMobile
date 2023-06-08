package com.example.myapplication.Adapter;

import static android.content.Context.MODE_PRIVATE;

import static com.example.myapplication.Database.DAO_HoaDon.capnhattrangthaiHoaDon;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Model.HoaDon;
import com.example.myapplication.Model.SanPham;
import com.example.myapplication.R;
import com.example.myapplication.ui.qlsp.UpdateSPActivity;
import com.example.myapplication.ui.trangchu.ChiTietHoaDonActivity;

import java.text.DecimalFormat;

import io.realm.RealmResults;

public class adapterHoaDon extends RecyclerView.Adapter<adapterHoaDon.HoaDonHolder> {

    private RealmResults<HoaDon> listHoaDon;


    public adapterHoaDon(RealmResults<HoaDon> listHD)
    {
        listHoaDon = listHD;

    }


    @NonNull
    @Override
    public HoaDonHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hoadon,parent,false);
        return new HoaDonHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull HoaDonHolder holder, int position) {
        HoaDon hoaDon = listHoaDon.get(position);
        holder.BindData(hoaDon);

    }

    @Override
    public int getItemCount() {
        return listHoaDon.size();
    }

    class HoaDonHolder extends RecyclerView.ViewHolder{

        private TextView txt_TongTien, txt_TenNhanVien,txt_day;

        private ImageView img_HinhSP;
        private Button btn_ChiTietHoaDon;
        private RadioButton rd_checktrangthai;
        public HoaDonHolder(@NonNull View itemView) {
            super(itemView);
            txt_TongTien = itemView.findViewById(R.id.txt_TongTien);
            txt_TenNhanVien= itemView.findViewById(R.id.txt_TrangThaiHoaDon);
            txt_day = itemView.findViewById(R.id.txt_day);
            btn_ChiTietHoaDon=itemView.findViewById(R.id.btn_ChiTietHoaDon);
            rd_checktrangthai=itemView.findViewById(R.id.rdoBtn_Check_HoaDon);
        }

        public void BindData(HoaDon hoaDon)
        {
            DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
            txt_TongTien.setText(String.valueOf(decimalFormat.format(hoaDon.getThanhTien())));
            txt_TenNhanVien.setText(hoaDon.getTenNV());
            txt_day.setText(hoaDon.getNgayLap().toString());
            rd_checktrangthai.setChecked(hoaDon.isChecktrangthai());
            btn_ChiTietHoaDon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    HoaDon hoaDon1=(HoaDon) listHoaDon.get(getAdapterPosition());
                    SharedPreferences preferences = view.getContext().getSharedPreferences("ListHD", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putInt("maHoaDon", hoaDon1.getMaHoaDon());
                    editor.apply();

                    Intent i=new Intent(view.getContext(), ChiTietHoaDonActivity.class);
                    view.getContext().startActivity(i);
                }
            });



            updateUI();

        }
        private void updateUI() {
            // Cập nhật giao diện người dùng tương ứng với trạng thái mới của hóa đơn
            // TODO: Cập nhật UI tương ứng với trạng thái mới của hóa đơn
            // ...

            // Ví dụ:
            // Cập nhật lại trạng thái của RadioButton
            HoaDon hoaDon = (HoaDon) listHoaDon.get(getAdapterPosition());
            rd_checktrangthai.setChecked(hoaDon.isChecktrangthai());
        }
    }



}
