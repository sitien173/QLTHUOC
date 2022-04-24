package com.ptithcm.qlthuoc.Entity;

import java.io.Serializable;


public class AppUser implements Serializable {
    private String username;
    private String password;
    private String hoten;
    private byte[] avatar;
    private String role;

    public AppUser(){
        this.username = "";
        this.password = "";
        this.hoten = "";
        this.avatar = null;
        this.role = "";
    }
    public AppUser(String username,String password, String hoten, byte[] avatar, String role){
        this.username = username;
        this.password = password;
        this.hoten = hoten;
        this.avatar = avatar;
        this.role = role;
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
}
