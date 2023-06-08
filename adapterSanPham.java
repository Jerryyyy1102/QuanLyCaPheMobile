package com.example.myapplication.Adapter;

import static android.content.Context.MODE_PRIVATE;
import static com.example.myapplication.Database.DAO_SanPham.xoaDataSanPham;

import android.app.Dialog;
import android.content.Intent;
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
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Model.SanPham;
import com.example.myapplication.R;
import com.example.myapplication.ui.qlsp.QuanLySanPhamFragment;
import com.example.myapplication.ui.qlsp.UpdateSPActivity;

import java.lang.reflect.Array;

import io.realm.RealmResults;

public class adapterSanPham extends RecyclerView.Adapter<adapterSanPham.MyViewHolder> {


    private static RealmResults<SanPham> mData;

    public static ImageButton btn_infoSP, btn_updSP, btn_DelSP;

    public adapterSanPham(RealmResults<SanPham> data) {
        mData = data;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_coffee, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        SanPham data = mData.get(position);
        holder.bindData(data);


    }


    @Override
    public int getItemCount() {
        return mData.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener, View.OnClickListener {

        private ItemClickListener itemClickListener;

        public ItemClickListener getItemClickListener() {
            return itemClickListener;
        }


        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

        private TextView txt_tenSP;
        private ImageView img_hinhSP;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_tenSP = itemView.findViewById(R.id.txt_tenSanPham);
            img_hinhSP = itemView.findViewById(R.id.img_HinhAnhSP);

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);


            btn_infoSP = itemView.findViewById(R.id.imgbtn_SanPhaminformation);
            btn_updSP = itemView.findViewById(R.id.imgbtn_SanPhamupdate);
            btn_DelSP = itemView.findViewById(R.id.imgbtn_SanPhamdelete);

            btn_infoSP.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SanPham sanPham=(SanPham) mData.get(getAdapterPosition());
                    Dialog dialog = new Dialog(view.getContext());
                    dialog.setContentView(R.layout.dialog_infosanpham);
                    dialog.setCancelable(true);

                    TextView txt_tensp = dialog.findViewById(R.id.txt_tensp_infodialog);
                    TextView txt_giasp = dialog.findViewById(R.id.txt_giasp_infodialog);
                    ImageView img_hinhanh = dialog.findViewById(R.id.hinanhSP_infodialog);
                    TextView txt_tenloaisp = dialog.findViewById(R.id.txt_tenloaisanpham_infodialog);
                    TextView txt_maloaisp = dialog.findViewById(R.id.txt_maloaisanpham_infodialog);

                    byte[] imageBytes = sanPham.getHinhanhSanPham();
                    Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);


                    txt_tensp.setText(sanPham.getTenSanPham());
                    txt_giasp.setText(String.valueOf(sanPham.getGiaSanPham()));
                    txt_maloaisp.setText(String.valueOf(sanPham.getLoaiSanPham().getMaLoaiSanPham()));
                    txt_tenloaisp.setText(sanPham.getLoaiSanPham().getTenLoaiSanPham());
                    img_hinhanh.setImageBitmap(bitmap);

                    dialog.show();
                }
            });


            btn_DelSP.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SanPham sanPham=(SanPham) mData.get(getAdapterPosition());
                    xoaDataSanPham(sanPham.getMaSanPham());
                    notifyItemRemoved(getAdapterPosition());
                    notifyItemRangeChanged(getAdapterPosition(), mData.size());

                }

            });
            btn_updSP.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SanPham sanPham=(SanPham) mData.get(getAdapterPosition());
                    SharedPreferences preferences = view.getContext().getSharedPreferences("MyPrefs", MODE_PRIVATE);
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putInt("maSanPham", sanPham.getMaSanPham());
                            editor.apply();

                            Intent i=new Intent(view.getContext(),UpdateSPActivity.class);
                            view.getContext().startActivity(i);
                }
            });

        }

        public void bindData(SanPham data) {
            txt_tenSP.setText(data.getTenSanPham());

            byte[] imageBytes = data.getHinhanhSanPham();
            Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
            img_hinhSP.setImageBitmap(bitmap);

        }


        @Override
        public boolean onLongClick(View view) {
            return false;
        }

        @Override
        public void onClick(View view) {

        }
    }


}
