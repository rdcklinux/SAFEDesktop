
package com.safe.entity;

import java.io.Serializable;

/**
 *
 * @author roderick
 */
public class ListTrabSalud implements Serializable{
    private long idlistrabsalud = 0;
    private long presenteSalud = 0;
    private long estadoSalud = 1;
    private long usuarioidusuario = 0;
    private long lisasissaludidlistasalud = 0;
    private long certificadoidcertificado = 0;
    private String fechacreacion = (new java.text.SimpleDateFormat("dd-MM-yyyy")).format(new java.util.Date());;

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

    public long getLisasissaludidlistasalud() {
        return lisasissaludidlistasalud;
    }

    public void setLisasissaludidlistasalud(long lisasissaludidlistasalud) {
        this.lisasissaludidlistasalud = lisasissaludidlistasalud;
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

    public String getFechacreacion() {
        return fechacreacion;
    }

    public void setFechacreacion(String fechacreacion) {
        this.fechacreacion = fechacreacion;
    }
}
