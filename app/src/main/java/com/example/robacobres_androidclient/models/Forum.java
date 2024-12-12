package com.example.robacobres_androidclient.models;

import java.io.Serializable;

public class Forum  implements Serializable {
    String name;
    String comentario;

    public Forum() {
    }

    public Forum(String name, String comentario) {
        this.name = name;
        this.comentario = comentario;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }
}