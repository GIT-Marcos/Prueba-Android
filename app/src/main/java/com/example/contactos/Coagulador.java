package com.example.contactos;

public class Coagulador {
    private String nombre;
    private String telefono;
    private String mail;
    private String observaciones;

    public Coagulador() {

    }

    public Coagulador(String nombre, String telefono, String mail, String observaciones) {
        this.nombre = nombre;
        this.telefono = telefono;
        this.mail = mail;
        this.observaciones = observaciones;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
}
