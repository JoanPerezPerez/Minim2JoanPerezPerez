package com.example.robacobres_androidclient.models;

public class User {
    String id;
    String name;
    String password;
    double money;
    static int lastId;

    public User(String user, String password) {
        this(null, user, password);
    }

    public User(String id, String user, String password) {
        this.setId(id);
        this.setName(user);
        this.setPassword(password);
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
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

    @Override
    public String toString() {
        return "User [id="+id+", user=" + name + ", password=" + password +"]";
    }
}
