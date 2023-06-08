package com.example.myapplication.ui.cuahang;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.example.myapplication.Model.ChiTietHoaDon;
import com.example.myapplication.Model.HoaDon;
import com.example.myapplication.Model.SanPham;
import com.example.myapplication.Model.Users;
import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentThongkeBinding;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.Inflater;

import io.realm.Realm;
import io.realm.RealmResults;

public class CuahangFragment extends Fragment {
    Toolbar toolbar;
    TextView tk_hoadon, tk_nhanvien, tk_ngaybatdau,tk_ngayketthuc,tk_nambatdau;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_thongke, container, false);

        toolbar = view.findViewById(R.id.toolbar_thongke);
        tk_hoadon = view.findViewById(R.id.hoadon_thongke);
        tk_nhanvien = view.findViewById(R.id.nhanvien_thongke);
        tk_ngaybatdau=view.findViewById(R.id.thangbatdau_thongke);
        tk_ngayketthuc=view.findViewById(R.id.ngayketthuc_thongke);
        tk_nambatdau=view.findViewById(R.id.nambatdau_thongke);

        AppCompatActivity compatActivity = (AppCompatActivity) getActivity();
        compatActivity.setSupportActionBar(toolbar);
        compatActivity.getSupportActionBar().setTitle("Thống kê");

        DecimalFormat decimalFormat=new DecimalFormat("###,###,###");


        Realm realm = Realm.getDefaultInstance();

        // Truy vấn danh sách hóa đơn hoặc mục từ Realm dựa trên tháng
        int thang = Integer.parseInt(tk_ngaybatdau.getText().toString().trim());
        int nam = Integer.parseInt(tk_nambatdau.getText().toString().trim());



        // Tạo ngày bắt đầu và ngày kết thúc của tháng
        Calendar calendar = Calendar.getInstance();
        calendar.set(nam, thang - 1, 1, 0, 0, 0);
        Date ngayBatDau = calendar.getTime();
        calendar.set(nam, thang, 1, 0, 0, 0);
        calendar.add(Calendar.SECOND, -1);
        Date ngayKetThuc = calendar.getTime();
        tk_ngayketthuc.setText(String.valueOf( ngayKetThuc));

        // Truy vấn danh sách hóa đơn từ Realm dựa trên tháng
        RealmResults<HoaDon> hoaDonList = realm.where(HoaDon.class)
                .between("NgayLap", ngayBatDau, ngayKetThuc)
                .findAll();

        // Thực hiện tính toán và hiển thị kết quả tương ứng với mỗi yêu cầu

        // Tính tổng trung bình thu nhập của quán
        double tongDoanhThu = 0;
        for (HoaDon hoaDon : hoaDonList) {
            tongDoanhThu += hoaDon.getThanhTien();
        }
        double trungBinhThuNhap = tongDoanhThu / hoaDonList.size();


        tk_hoadon.setText(String.valueOf(decimalFormat.format(trungBinhThuNhap)));


        // Tạo một cấu trúc dữ liệu Map để lưu trữ tổng doanh thu của từng nhân viên
        Map<String, Integer> doanhThuByNhanVien = new HashMap<>();

        // Tính toán tổng doanh thu của từng nhân viên
        for (HoaDon hoaDon : hoaDonList) {
            String maNhanVien = hoaDon.getTenNV();
            long doanhThu = hoaDon.getThanhTien();

            if (doanhThuByNhanVien.containsKey(maNhanVien)) {
                // Nếu nhân viên đã có doanh thu trước đó, cộng thêm vào tổng
                doanhThu += doanhThuByNhanVien.get(maNhanVien);
            }

            // Lưu trữ tổng doanh thu của nhân viên
            doanhThuByNhanVien.put(maNhanVien, (int) doanhThu);
        }

            // Tìm nhân viên có doanh thu cao nhất
        String nhanVienDoanhThuCaoNhat = "";
        int doanhThuCaoNhat = 0;

        for (Map.Entry<String, Integer> entry : doanhThuByNhanVien.entrySet()) {
            String maNhanVien = entry.getKey();
            int doanhThu = entry.getValue();

            if (doanhThu > doanhThuCaoNhat) {
                // Cập nhật thông tin nhân viên có doanh thu cao nhất
                nhanVienDoanhThuCaoNhat = maNhanVien;
                doanhThuCaoNhat = doanhThu;
            }
        }


             // Hiển thị kết quả
        if (!nhanVienDoanhThuCaoNhat.isEmpty()) {
            // Tìm thông tin chi tiết về nhân viên từ Realm (nếu cần)
            Users nhanVien = realm.where(Users.class).equalTo("hoTen", nhanVienDoanhThuCaoNhat).findFirst();

            if (nhanVien != null) {
                String tenNhanVien = nhanVien.getHoTen();
                // Hiển thị thông tin nhân viên có doanh thu cao nhất

                String tong=tenNhanVien+"\n"+decimalFormat.format(doanhThuCaoNhat);
                tk_nhanvien.setText(tong);
                System.out.println("Nhân viên có doanh thu cao nhất: " + tenNhanVien + " - Doanh thu: " + doanhThuCaoNhat);
            }
        }



        realm.close();
        return view;
    }
}