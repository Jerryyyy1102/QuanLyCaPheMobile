package com.example.myapplication.Adapter;

import static android.content.Context.MODE_PRIVATE;
import static com.example.myapplication.Database.DAO_Users.xoaDataUser;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Model.Users;
import com.example.myapplication.R;
import com.example.myapplication.UpdateInfoUserActivity;
import com.example.myapplication.ui.qlsp.UpdateSPActivity;

import io.realm.RealmResults;

public class adapterNhanSu extends RecyclerView.Adapter<adapterNhanSu.NhanSuHolder> {

    RealmResults<Users> listUsers;

    public adapterNhanSu(RealmResults<Users> lstUsers) {
        listUsers = lstUsers;
    }

    @NonNull
    @Override
    public NhanSuHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_listnhanvien, parent, false);
        return new NhanSuHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NhanSuHolder holder, int position) {
        Users users = listUsers.get(position);
        holder.bindData(users);

    }

    @Override
    public int getItemCount() {
        return listUsers.size();
    }

    class NhanSuHolder extends RecyclerView.ViewHolder {
        TextView txt_tenNhanVien, txt_maNhanVien;
        ImageButton btn_info, btn_update, btn_delete;

        public NhanSuHolder(@NonNull View itemView) {
            super(itemView);
            txt_tenNhanVien = itemView.findViewById(R.id.txt_tenNhanVien_qlnv);
            txt_maNhanVien = itemView.findViewById(R.id.txt_maNhanVien_qlnv);
            btn_delete = itemView.findViewById(R.id.imgbtn_NhanViendelete);
            btn_info=itemView.findViewById(R.id.imgbtn_NhanVieninformation);
            btn_update=itemView.findViewById(R.id.imgbtn_NhanVienupdate);
        }

        public void bindData(Users users) {

            txt_tenNhanVien.setText(users.getHoTen());
            txt_maNhanVien.setText(String.valueOf(users.getUser_id()));

            btn_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Users users1=(Users) listUsers.get(getAdapterPosition());
                    xoaDataUser(users1.getUser_id());
                    notifyItemRemoved(getAdapterPosition());
                    notifyItemRangeChanged(getAdapterPosition(), listUsers.size());
                    bindData(users);
                }
            });
            btn_info.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Users users1=(Users) listUsers.get(getAdapterPosition());
                    SharedPreferences preferences = view.getContext().getSharedPreferences("User", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putInt("user_id", users.getUser_id());
                    editor.apply();

                    Intent i=new Intent(view.getContext(), UpdateInfoUserActivity.class);
                    view.getContext().startActivity(i);
                }
            });


        }
    }
}
