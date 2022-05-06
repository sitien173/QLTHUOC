package com.ptithcm.qlthuoc.Entity;

import java.io.Serializable;
import java.util.List;




public class HoaDon implements Serializable {
    private int id;
    private NhaThuoc nhaThuoc;
    private AppUser khachhang;
    private String ghichu;
    private float total;

    public HoaDon(NhaThuoc nhaThuoc, AppUser khachhang, String ghichu, float total) {
        this.nhaThuoc = nhaThuoc;
        this.khachhang = khachhang;
        this.ghichu = ghichu;
        this.total = total;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public NhaThuoc getNhaThuoc() {
        return nhaThuoc;
    }

    public void setNhaThuoc(NhaThuoc nhaThuoc) {
        this.nhaThuoc = nhaThuoc;
    }

    public AppUser getKhachhang() {
        return khachhang;
    }

    public void setKhachhang(AppUser khachhang) {
        this.khachhang = khachhang;
    }

    public String getGhichu() {
        return ghichu;
    }

    public void setGhichu(String ghichu) {
        this.ghichu = ghichu;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }
}
