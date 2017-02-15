package com.eagleeye.eagleeye;

import java.util.Date;

/**
 * Created by ortim on 07/02/2017.
 */

public class MensajesChat {
    private String texto_mensaje;
    private String usuario_mensaje;
    private String id;
    private long date_mensaje;

    public MensajesChat() {
    }

    public MensajesChat(String texto_mensaje, String id,String usuario_mensaje) {
        this.texto_mensaje = texto_mensaje;
        this.id = id;
        this.usuario_mensaje = usuario_mensaje;
        date_mensaje = new Date().getTime();
    }

    public String getTexto_mensaje() {
        return texto_mensaje;
    }

    public void setTexto_mensaje(String texto_mensaje) {
        this.texto_mensaje = texto_mensaje;
    }

    public String getUsuario_mensaje() {
        return usuario_mensaje;
    }

    public void setUsuario_mensaje(String usuario_mensaje) {
        this.usuario_mensaje = usuario_mensaje;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getDate_mensaje() {
        return date_mensaje;
    }

    public void setDate_mensaje(long date_mensaje) {
        this.date_mensaje = date_mensaje;
    }


}
