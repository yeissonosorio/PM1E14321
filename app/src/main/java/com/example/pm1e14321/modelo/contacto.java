package com.example.pm1e14321.modelo;

import java.sql.Blob;
public class contacto {

    private int codigo;
    private String nombreContacto;
    private String numeroContacto;
    private String nota;

    private  String Pais;

    private Blob image;

    public Blob getImage() {
        return image;
    }

    public contacto() {
    }

    public contacto(int codigo, String nombreContacto, String numeroContacto, String nota, Blob image,String Pais) {
        this.codigo = codigo;
        this.Pais = Pais;
        this.nombreContacto = nombreContacto;
        this.numeroContacto = numeroContacto;
        this.nota = nota;
        this.image = image;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNombreContacto() {
        return nombreContacto;
    }

    public void setNombreContacto(String nombreContacto) {
        this.nombreContacto = nombreContacto;
    }

    public String getNumeroContacto() {
        return numeroContacto;
    }

    public void setNumeroContacto(String numeroContacto) {
        this.numeroContacto = numeroContacto;
    }

    public String getNota() {
        return nota;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }

    public void setImage(Blob image) {
        this.image = image;
    }


}
