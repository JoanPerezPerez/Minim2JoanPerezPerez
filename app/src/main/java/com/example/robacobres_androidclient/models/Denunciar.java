package com.example.robacobres_androidclient.models;

import java.util.Date;

public class Denunciar {
    private Date date;
    private String title;
    private String informer;
    private String description;

    public Denunciar (){
        this.date = new Date();
    }
    public Denunciar(String informer,  String title, String descripcion){
        this.date = new Date();
        this.title = title;
        this.description = descripcion;
        this.informer = informer;

    }
    public void setDate(Date date) {
        this.date = date;
    }

    public void setInformer(String informer) {
        this.informer = informer;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public String getInformer() {
        return informer;
    }

    public String getDescription() {
        return description;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}