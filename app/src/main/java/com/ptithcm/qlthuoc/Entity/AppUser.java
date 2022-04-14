package com.ptithcm.qlthuoc.Entity;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AppUser implements Serializable {
    private String username;
    private String password;
    private String hoten;
    private byte[] avatar;
    private String role;
}
