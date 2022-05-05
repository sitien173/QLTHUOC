package com.ptithcm.qlthuoc.Entity;

import java.io.Serializable;


public class AppUser implements Serializable {
    private String username;
    private String password;
    private String hoten;
    private byte[] avatar;
    private String role;
    private String address;
    private String phone;
    private int id;

    public AppUser(){
        this.username = "";
        this.password = "";
        this.hoten = "";
        this.avatar = null;
        this.role = "";
        this.address = "";
        this.phone = "";
        this.id = 0;
    }
    public AppUser(String username, String password, String hoten, byte[] avatar, String role, String address, String phone){
        this.username = username;
        this.password = password;
        this.hoten = hoten;
        this.avatar = avatar;
        this.role = role;
        this.address = address;
        this.phone = phone;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getHoten() {
        return hoten;
    }

    public void setHoten(String hoten) {
        this.hoten = hoten;
    }

    public byte[] getAvatar() {
        return avatar;
    }

    public void setAvatar(byte[] avatar) {
        this.avatar = avatar;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
