package com.ptithcm.qlthuoc.Entity;

import java.io.Serializable;
import java.util.Collection;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Thuoc implements Serializable {
    private int id;
    private String tenthuoc;
    private String thanhphan;
    private String donvitinh;
    private int soluong;
    private byte[] hinhanh;
    private float dongia;
    private Collection<Thuoc_NhaThuoc> nhaThuocs;
}
