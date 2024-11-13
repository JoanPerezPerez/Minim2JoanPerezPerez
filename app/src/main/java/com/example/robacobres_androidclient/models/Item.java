package com.example.robacobres_androidclient.models;

public class Item {
    String id;
    String name;
    double cost;
    static int lastId;


    public Item(String id, String name) {
        this.setId(id);
        this.setName(name);
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
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

    @Override
    public String toString() {
        return "User [id="+id+", name=" + name +"]";
    }
}
