
package com.safe.entity;

import java.io.Serializable;

public class Certificado implements Serializable{
    private long idcertificado;
    private String tipocertificado;
    private String codcertificado;
    private long estadocert;    
    private String fechacreacion = (new java.text.SimpleDateFormat("dd-MM-yyyy")).format(new java.util.Date());;
    private long clienteidcliente = 0;

    public long getClienteidcliente() {
        return clienteidcliente;
    }

    public void setClienteidcliente(long clienteidcliente) {
        this.clienteidcliente = clienteidcliente;
    }
    
    public long getIdcertificado() {
        return idcertificado;
    }

    public void setIdcertificado(long idcertificado) {
        this.idcertificado = idcertificado;
    }

    public String getTipocertificado() {
        return tipocertificado;
    }

    public void setTipocertificado(String tipocertificado) {
        this.tipocertificado = tipocertificado;
    }

    public String getCodcertificado() {
        return codcertificado;
    }

    public void setCodcertificado(String codcertificado) {
        this.codcertificado = codcertificado;
    }

    public long getEstadocert() {
        return estadocert;
    }

    public void setEstadocert(long estadocert) {
        this.estadocert = estadocert;
    }

    @Override
    public String toString() {
        return "Certificado{" + "idcertificado=" + idcertificado + ", tipocertificado=" + tipocertificado + ", codcertificado=" + codcertificado + ", estadocert=" + estadocert + '}';
    }

    public String getFechacreacion() {
        return fechacreacion;
    }

    public void setFechacreacion(String fechacreacion) {
        this.fechacreacion = fechacreacion;
    }
    
              
}
