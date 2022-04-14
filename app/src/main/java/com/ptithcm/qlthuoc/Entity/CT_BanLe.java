package com.ptithcm.qlthuoc.Entity;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CT_BanLe implements Serializable {
    private Thuoc thuoc;
    private HoaDon hoadon;
    private int soluong;
    private float total;
}