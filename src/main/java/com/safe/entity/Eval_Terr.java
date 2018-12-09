
package com.safe.entity;

import java.io.Serializable;



public class Eval_Terr implements Serializable {
    private long idevalterr;
    private String obsvisita;
    private String fechavisita;
    private long estado;
    private long solievalteridsolicitud;
    private long estadoevalterridestado;
    private long certificadoidcertificado;
    private String fechacreacion = (new java.text.SimpleDateFormat("dd-MM-yyyy")).format(new java.util.Date());;
    private long usuariosidusuarios;
    
    public String getFechavisita() {
        return fechavisita;
    }

    public void setFechavisita(String fechavisita) {
        this.fechavisita = fechavisita;
    }

    public long getUsuariosidusuarios() {
        return usuariosidusuarios;
    }

    public void setUsuariosidusuarios(long usuariosidusuarios) {
        this.usuariosidusuarios = usuariosidusuarios;
    }

    public long getIdevalterr() {
        return idevalterr;
    }

    public void setIdevalterr(long idevalterr) {
        this.idevalterr = idevalterr;
    }

    public String getObsvisita() {
        return obsvisita;
    }

    public void setObsvisita(String obsvisita) {
        this.obsvisita = obsvisita;
    }

    public Long getEstado() {
        return estado;
    }

    public void setEstado(Long estado) {
        this.estado = estado;
    }

    public Long getSolievalteridsolicitud() {
        return solievalteridsolicitud;
    }

    public void setSolievalteridsolicitud(Long solievalteridsolicitud) {
        this.solievalteridsolicitud = solievalteridsolicitud;
    }

    public Long getEstadoevalterridestado() {
        return estadoevalterridestado;
    }

    public void setEstadoevalterridestado(Long estadoevalterridestado) {
        this.estadoevalterridestado = estadoevalterridestado;
    }

    public long getCertificadoidcertificado() {
        return certificadoidcertificado;
    }

    public void setCertificadoidcertificado(long certificadoidcertificado) {
        this.certificadoidcertificado = certificadoidcertificado;
    }

    public String getFechacreacion() {
        return fechacreacion;
    }

    public void setFechacreacion(String fechacreacion) {
        this.fechacreacion = fechacreacion;
    }
}
