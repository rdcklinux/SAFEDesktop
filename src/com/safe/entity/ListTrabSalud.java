
package com.safe.entity;

import java.io.Serializable;

/**
 *
 * @author Rodrigo
 */





public class ListTrabSalud implements Serializable{
    
    
    private long idlistrabsalud;
    
    
    private long presenteSalud;
    
    
    private long estadoSalud;
    
    
    private long usuarioidusuario;
    
    
    private long lisasissaludidlistasalud;
    
    
    private long certificadoidcertificado;

    public long getIdlistrabsalud() {
        return idlistrabsalud;
    }

    public void setIdlistrabsalud(long idlistrabsalud) {
        this.idlistrabsalud = idlistrabsalud;
    }

    public long getPresenteSalud() {
        return presenteSalud;
    }

    public void setPresenteSalud(long presenteSalud) {
        this.presenteSalud = presenteSalud;
    }

    public long getEstadoSalud() {
        return estadoSalud;
    }

    public void setEstadoSalud(long estadoSalud) {
        this.estadoSalud = estadoSalud;
    }

    public long getUsuarioidusuario() {
        return usuarioidusuario;
    }

    public void setUsuarioidusuario(long usuarioidusuario) {
        this.usuarioidusuario = usuarioidusuario;
    }

    public long getLisasiscapidlistasalud() {
        return lisasissaludidlistasalud;
    }

    public void setLisasiscapidlistasalud(long lisasiscapidlistasalud) {
        this.lisasissaludidlistasalud = lisasiscapidlistasalud;
    }

    public long getCertificadoidcertificado() {
        return certificadoidcertificado;
    }

    public void setCertificadoidcertificado(long certificadoidcertificado) {
        this.certificadoidcertificado = certificadoidcertificado;
    }

    @Override
    public String toString() {
        return "ListTrabSalud{" + "idlistrabsalud=" + idlistrabsalud + ", presenteSalud=" + presenteSalud + ", estadoSalud=" + estadoSalud + ", usuarioidusuario=" + usuarioidusuario + ", lisasissaludidlistasalud=" + lisasissaludidlistasalud + ", certificadoidcertificado=" + certificadoidcertificado + '}';
    }

       
}
