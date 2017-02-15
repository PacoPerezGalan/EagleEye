package com.eagleeye.eagleeye;


import android.graphics.Bitmap;

/**
 * Created by ortim on 08/02/2017.
 */

public class Fallas {
    String nombre;
    String seccion;
    String lema;
    String boceto;
    Double longitud;
    Double latitud;

    public Fallas() {
    }


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getSeccion() {
        return seccion;
    }

    public void setSeccion(String seccion) {
        this.seccion = seccion;
    }

    public String getLema() {
        return lema;
    }

    public void setLema(String lema) {
        this.lema = lema;
    }

    public String getBoceto() {
        return boceto;
    }

    public void setBoceto(String boceto) {
        this.boceto = boceto;
    }

    public Double getLongitud() {
        return longitud;
    }

    public void setLongitud(Double longitud) {
        this.longitud = longitud;
    }

    public Double getLatitud() {
        return latitud;
    }

    public void setLatitud(Double latitud) {
        this.latitud = latitud;
    }
}
