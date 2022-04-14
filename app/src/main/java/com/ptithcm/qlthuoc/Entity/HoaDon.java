package com.ptithcm.qlthuoc.Entity;

import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class HoaDon implements Serializable {
    private int id;
    private NhaThuoc nhaThuoc;
    private AppUser khachhang;
    private String ghichu;
    private float total;
}
