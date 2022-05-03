package com.ptithcm.qlthuoc.Entity;

import android.database.Cursor;

import java.io.Serializable;


public class CT_BanLe implements Serializable {
    private Thuoc thuoc;
    private HoaDon hoadon;
    private int soluong;
    private float total;

    /**
     * 1: đã xóa
     * 2: chưa xuất hóa đơn
     * 3: đã xuất hóa đơn
     **/
    private int status;
    private AppUser khachhang;

    public CT_BanLe(Thuoc thuoc, HoaDon hoadon, int soluong, float total, int status, AppUser khachhang){
        this.thuoc = thuoc;
        this.hoadon = hoadon;
        this.soluong = soluong;
        this.total = total;
        this.status = status;
        this.khachhang = khachhang;
    }

    public Thuoc getThuoc() {
        return thuoc;
    }

    public void setThuoc(Thuoc thuoc) {
        this.thuoc = thuoc;
    }

    public HoaDon getHoadon() {
        return hoadon;
    }

    public void setHoadon(HoaDon hoadon) {
        this.hoadon = hoadon;
    }

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public AppUser getKhachhang() {
        return khachhang;
    }

    public void setKhachhang(AppUser khachhang) {
        this.khachhang = khachhang;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}