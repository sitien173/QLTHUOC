package com.ptithcm.qlthuoc.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.ptithcm.qlthuoc.DbContext;
import com.ptithcm.qlthuoc.Entity.AppUser;
import com.ptithcm.qlthuoc.Entity.CT_BanLe;
import com.ptithcm.qlthuoc.Entity.Thuoc;
import com.ptithcm.qlthuoc.ListUser;
import com.ptithcm.qlthuoc.Order.AddInfoCustomer;
import com.ptithcm.qlthuoc.Order.Order;
import com.ptithcm.qlthuoc.Order.OrderSuccess;
import com.ptithcm.qlthuoc.Order.ProductOrder;
import com.ptithcm.qlthuoc.ProfileUser;
import com.ptithcm.qlthuoc.R;
import com.ptithcm.qlthuoc.RecyclerListener;

import java.util.ArrayList;
import java.util.List;

public class CartAdapter extends BaseAdapter {
    Order newOrder = new Order();
    Context context;
    private Activity activity;
    private List<CT_BanLe> listBanLe = new ArrayList<>();
    private AppUser customer;
    DbContext dbContext = null;
    int resource;

    public CartAdapter(@NonNull Context context, int resource, @NonNull List<CT_BanLe> listBanLe, DbContext dbContext){
        this.listBanLe = listBanLe;
        this.context  = context;
        this.resource = resource;
        this.dbContext = dbContext;
    }

    @Override
    public int getCount() {
        return listBanLe.toArray().length;
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    private void dialogDelOrderLine(CT_BanLe ctBanLe, int position){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        //Thiết lập tiêu đề
        builder.setTitle("Xác nhận");
        builder.setMessage("Bạn có chắc chắn muốn xóa sản phẩm này");
        // Nút Ok
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                deleteOrderLine(ctBanLe, position);
                dialog.cancel();
            }
        });
        //Nút Cancel
        builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        //Tạo dialog
        AlertDialog al = builder.create();
        //Hiển thị
        al.show();
    }

    private void deleteOrderLine (CT_BanLe ctBanLe, int position){
        try (SQLiteDatabase db = dbContext.getWritableDatabase()) {
            ContentValues values = new ContentValues();
            values.put("status", 1);
            db.update("CT_BanLe", values, "status = ? AND id_customer = ? AND id_thuoc = ?", new String[]{ String.valueOf(ctBanLe.getStatus()), String.valueOf(ctBanLe.getKhachhang().getId()), String.valueOf(ctBanLe.getThuoc().getId()) });
            listBanLe.remove(position);
            notifyDataSetChanged();

            Toast.makeText(context, "Sản phẩm đã được xóa khỏi giỏ hàng", Toast.LENGTH_LONG).show();

        } catch (Exception e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {

        convertView = LayoutInflater.from(context).inflate(resource, null);

        // map to view
        TextView txtDrugName = (TextView) convertView.findViewById(R.id.txtDrugName);
        TextView txtDrugPrice = (TextView) convertView.findViewById(R.id.txtDrugPrice);
        TextView txtQuantity = (TextView) convertView.findViewById(R.id.txtQuantity);
        ImageView imageDrug = (ImageView) convertView.findViewById(R.id.imageDrug);
        Button btnDelOrderLine = (Button) convertView.findViewById(R.id.btnDelOrderLine);

        CT_BanLe banLe = listBanLe.get(i);
        txtDrugName.setText(banLe.getThuoc().getTenthuoc());
        txtDrugPrice.setText(String.valueOf(banLe.getThuoc().getDongia() * banLe.getSoluong()) + "VNĐ");
        txtQuantity.setText("SL: " + banLe.getSoluong());
        // set image order line
        if(banLe.getThuoc().getHinhanh() != null){
            Bitmap bmp = BitmapFactory.decodeByteArray(banLe.getThuoc().getHinhanh(), 0, banLe.getThuoc().getHinhanh().length);
            imageDrug.setImageBitmap(bmp);
        } else{
            imageDrug.setImageResource(R.drawable.medicine);
        }
        btnDelOrderLine.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialogDelOrderLine(banLe, i);
            }
        });
        return convertView;
    }
}
