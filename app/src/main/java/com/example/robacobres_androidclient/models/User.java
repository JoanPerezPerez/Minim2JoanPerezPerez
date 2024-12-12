package com.example.robacobres_androidclient.models;

import java.io.Serializable;

public class User implements Serializable {
    int id;
    String name;
    String password;
    String correo;
    double money;
    double cobre;
    static int lastId;
    public User(){}
    public User(String user, String password, String mail) {
        this(0, user, password, mail);
    }

    public User(String user, String password) {
        this(0,user, password,null);
    }

    public User(int id, String user, String password, String mail) {
        this.setId(id);
        this.setName(user);
        this.setPassword(password);
        this.setCorreo(mail);
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id=id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public double getMoney() {
        return money;
    }
    public void setMoney(double money) {
        this.money = money;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public double getCobre() {
        return cobre;
    }

    public void setCobre(double cobre) {
        this.cobre = cobre;
    }

    @Override
    public String toString() {
        return "User [id="+id+", user=" + name + ", password=" + password +"]";
    }
}
