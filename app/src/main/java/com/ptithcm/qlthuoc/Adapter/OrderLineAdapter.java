package com.ptithcm.qlthuoc.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

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

public class OrderLineAdapter extends BaseAdapter {

    Context context;
    private Activity activity;
    private List<Thuoc> listDrug = new ArrayList<>();
    private AppUser customer;
    DbContext dbContext = null;
    int resource;

    public OrderLineAdapter(@NonNull Context context, int resource, @NonNull List<Thuoc> listDrug, AppUser customer, DbContext dbContext){
        this.listDrug = listDrug;
        this.customer = customer;
        this.context  = context;
        this.resource = resource;
        this.dbContext = dbContext;
    }

    @Override
    public int getCount() {
        return listDrug.toArray().length;
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {

        convertView = LayoutInflater.from(context).inflate(resource, null);

        // map to view
        TextView txtDrugName = (TextView) convertView.findViewById(R.id.txtDrugName);
        TextView txtDrugPrice = (TextView) convertView.findViewById(R.id.txtDrugPrice);
        Button btnAddProduct = (Button) convertView.findViewById(R.id.btnAddProduct);

        Thuoc drug = listDrug.get(i);

        txtDrugName.setText(drug.getTenthuoc());
        txtDrugPrice.setText(String.valueOf(drug.getDongia()));
        btnAddProduct.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // status: 2 (Chưa lập hóa đơn)
                CT_BanLe newCTBanLe = new CT_BanLe(drug, null, 1, 1 * drug.getDongia(), 2, customer);

                Intent intent = new Intent(context, Order.class);
                intent.putExtra("customer", customer);
                ((ProductOrder)context).startActivityForResult(intent,1);
            }
        });
        return convertView;
    }
}
