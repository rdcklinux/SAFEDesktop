
package com.safe.entity;

import java.io.Serializable;

public class Expositor implements Serializable{
    private long idexpositor;
    private String runexpositor;
    private String nombreexpositor;
    private String telexpositor;
    private String mailexpositor;
    private long estadoexpositor;
    private String fechacreacion = (new java.text.SimpleDateFormat("dd-MM-yyyy")).format(new java.util.Date());;

    public long getIdexpositor() {
        return idexpositor;
    }

    public void setIdexpositor(long idexpositor) {
        this.idexpositor = idexpositor;
    }

    public String getRunexpositor() {
        return runexpositor;
    }

    public void setRunexpositor(String runexpositor) {
        this.runexpositor = runexpositor;
    }

    public String getNombreexpositor() {
        return nombreexpositor;
    }

    public void setNombreexpositor(String nombreexpositor) {
        this.nombreexpositor = nombreexpositor;
    }

    public String getTelexpositor() {
        return telexpositor;
    }

    public void setTelexpositor(String telexpositor) {
        this.telexpositor = telexpositor;
    }

    public String getMailexpositor() {
        return mailexpositor;
    }

    public void setMailexpositor(String mailexpositor) {
        this.mailexpositor = mailexpositor;
    }

    public long getEstadoexpositor() {
        return estadoexpositor;
    }

    public void setEstadoexpositor(long estadoexpositor) {
        this.estadoexpositor = estadoexpositor;
    }
    
    public int getEstadoexpositorIndex() {
        return (new Long(estadoexpositor)).intValue();
    }

    public void setEstadoexpositorIndex(int estadoexpositor) {
        this.estadoexpositor = estadoexpositor;
    }

    @Override
    public String toString() {
        return "Expositor{" + "idexpositor=" + idexpositor + ", runexpositor=" + runexpositor + ", nombreexpositor=" + nombreexpositor + ", telexpositor=" + telexpositor + ", mailexpositor=" + mailexpositor + ", estadoexpositor=" + estadoexpositor + '}';
    }

    public String getFechacreacion() {
        return fechacreacion;
    }

    public void setFechacreacion(String fechacreacion) {
        this.fechacreacion = fechacreacion;
    }
    
    
}
