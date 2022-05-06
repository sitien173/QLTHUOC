package com.ptithcm.qlthuoc.Adapter;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ptithcm.qlthuoc.AddDrug;
import com.ptithcm.qlthuoc.DbContext;
import com.ptithcm.qlthuoc.EditDrug;
import com.ptithcm.qlthuoc.Entity.Thuoc;
import com.ptithcm.qlthuoc.QuanLiThuoc;
import com.ptithcm.qlthuoc.R;
import com.ptithcm.qlthuoc.RecyclerListener;


import java.util.ArrayList;
import java.util.List;

public class drugAdapter extends RecyclerView.Adapter<drugAdapter.ViewHolder>{
    private Context mContext;
    private List<Thuoc> listThuoc = new ArrayList<>();
    private RecyclerListener listener;
    public drugAdapter(Context mContext){
        this.mContext = mContext;
    }

    public drugAdapter(Context mContext,RecyclerListener listener){
        this.mContext = mContext;
        this.listener = listener;

    }
    public void setData( ArrayList<Thuoc> listThuoc){
        this.listThuoc = listThuoc;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_drug, parent, false);
        return new ViewHolder(view);
    }
    public int deleteDrug(Thuoc drug) {
        DbContext dbContext = new DbContext(mContext.getApplicationContext());
        SQLiteDatabase db = dbContext.getWritableDatabase();
        return db.delete("Thuoc","Id = ?",new String[]{String.valueOf(drug.getId())});
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textView.setText(listThuoc.get(position).getTenthuoc());
        holder.btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteDrug(listThuoc.get(holder.getAdapterPosition()));
                listThuoc.remove(holder.getAdapterPosition());
                notifyItemRemoved(holder.getAdapterPosition());
            }
        });

    }

    @Override
    public int getItemCount() {
        return listThuoc.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
            TextView textView;
            Button btnSua;
            Button btnXoa;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textViewDrug);
            btnSua = itemView.findViewById(R.id.btnSua);
            btnXoa = itemView.findViewById(R.id.btnXoa);
            btnSua.setOnClickListener(v -> {
                listener.onItemClick(getAdapterPosition());
            });
        }
    }

}
