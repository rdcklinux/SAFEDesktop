
package com.safe.entity;

import java.io.Serializable;

/**
 *
 * @author Rodrigo
 */





public class List_Trab_Cap implements Serializable {
    private long idlistrabcap = 0;
    private long presenteCap = 1;
    private long estadoCap = 1;
    private long usuarioidusuario = 0;
    private long lisasiscapidlistacap = 0;
    private long certificadoidcertificado;

    public long getIdlistrabcap() {
        return idlistrabcap;
    }

    public void setIdlistrabcap(long idlistrabcap) {
        this.idlistrabcap = idlistrabcap;
    }

    public long getPresente() {
        return presenteCap;
    }

    public void setPresente(long presente) {
        this.presenteCap = presente;
    }

    public long getEstado() {
        return estadoCap;
    }

    public void setEstado(long estado) {
        this.estadoCap = estado;
    }

    public long getUsuarioidusuario() {
        return usuarioidusuario;
    }

    public void setUsuarioidusuario(long usuarioidusuario) {
        this.usuarioidusuario = usuarioidusuario;
    }

    public long getLisasiscapidlistacap() {
        return lisasiscapidlistacap;
    }

    public void setLisasiscapidlistacap(long lisasiscapidlistacap) {
        this.lisasiscapidlistacap = lisasiscapidlistacap;
    }

    public long getCertificadoidcertificado() {
        return certificadoidcertificado;
    }

    public void setCertificadoidcertificado(long certificadoidcertificado) {
        this.certificadoidcertificado = certificadoidcertificado;
    }

    @Override
    public String toString() {
        return "List_Trab_Cap{" + "idlistrabcap=" + idlistrabcap + ", presente=" + presenteCap + ", estado=" + estadoCap + ", usuarioidusuario=" + usuarioidusuario + ", lisasiscapidlistacap=" + lisasiscapidlistacap + ", certificadoidcertificado=" + certificadoidcertificado + '}';
    }
    
    

    
}
