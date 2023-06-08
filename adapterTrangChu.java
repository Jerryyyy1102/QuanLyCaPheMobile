package com.example.myapplication.Adapter;

import static android.content.Context.MODE_PRIVATE;
import static com.example.myapplication.Database.DAO_GioHang.saveDataGioHang;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Model.GioHang;
import com.example.myapplication.Model.SanPham;
import com.example.myapplication.Model.Users;
import com.example.myapplication.R;

import java.text.DecimalFormat;

import io.realm.Realm;
import io.realm.RealmResults;

public class adapterTrangChu extends RecyclerView.Adapter<adapterTrangChu.TrangChuHolder> {
    private static RealmResults<SanPham> ListSanPham;

    public adapterTrangChu(RealmResults<SanPham> listSanPham) {
        ListSanPham = listSanPham;
    }

    @NonNull
    @Override
    public TrangChuHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.listcoffee, parent, false);
        return new TrangChuHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TrangChuHolder holder, int position) {
        SanPham data = ListSanPham.get(position);
        holder.bindData(data);
    }

    @Override
    public int getItemCount() {
        return ListSanPham.size();
    }

    class TrangChuHolder extends RecyclerView.ViewHolder {
        private TextView txt_tenSP, txt_giaSP;
        private ImageView img_hinhSP;

        private ImageButton btn_themvaogiohang;

        public TrangChuHolder(@NonNull View itemView) {
            super(itemView);
            txt_tenSP = itemView.findViewById(R.id.txt_NameCoffee_trangchu);
            txt_giaSP = itemView.findViewById(R.id.txt_PriceCoffee_trangchu);
            img_hinhSP = itemView.findViewById(R.id.img_hinhanh_trangchu);
            btn_themvaogiohang=itemView.findViewById(R.id.imgbtn_themvaogiohang);


        }

        public void bindData(SanPham data) {
            SharedPreferences preferences = itemView.getContext().getSharedPreferences("User", MODE_PRIVATE);
            int userId = preferences.getInt("user_id", 0);
            DecimalFormat decimalFormat=new DecimalFormat("###,###,###");
            txt_tenSP.setText(data.getTenSanPham());
            txt_giaSP.setText(String.valueOf(decimalFormat.format(data.getGiaSanPham())));
            byte[] imageBytes = data.getHinhanhSanPham();
            Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
            img_hinhSP.setImageBitmap(bitmap);
            btn_themvaogiohang.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Realm realm = Realm.getDefaultInstance();
                    Users users = realm.where(Users.class).equalTo("user_id",userId).findFirst();
                    SanPham sanPham=(SanPham) ListSanPham.get(getAdapterPosition());

                    saveDataGioHang(sanPham.getMaSanPham(),1,users.getUser_id());
                }
            });
        }
    }
}
