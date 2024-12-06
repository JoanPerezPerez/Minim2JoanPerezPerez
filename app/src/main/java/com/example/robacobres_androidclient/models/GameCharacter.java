package com.example.robacobres_androidclient.models;

public class GameCharacter {
    String name;
    int strength;
    int speed;
    double cost;

    public GameCharacter(){}

    public GameCharacter(int stealth, int speed, int strength, String name, double cost) {
        this.setSpeed(speed);
        this.setStrength(strength);
        this.setName(name);
        this.setCost(cost);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }
}
