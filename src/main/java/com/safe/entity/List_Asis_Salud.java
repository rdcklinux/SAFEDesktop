
package com.safe.entity;

import java.io.Serializable;


public class List_Asis_Salud implements Serializable {
    private long idlistsalud = 0;
    private long estadoasistsalud = 1;
    private long sesionsaludidsesionsalud = 0;
    private String fechacreacion = (new java.text.SimpleDateFormat("dd-MM-yyyy")).format(new java.util.Date());;

    public long getIdlistsalud() {
        return idlistsalud;
    }

    public void setIdlistsalud(long idlistsalud) {
        this.idlistsalud = idlistsalud;
    }

    public long getEstadoasistsalud() {
        return estadoasistsalud;
    }

    public void setEstadoasistsalud(long estadoasistsalud) {
        this.estadoasistsalud = estadoasistsalud;
    }

    public long getSesionsaludidsesionsalud() {
        return sesionsaludidsesionsalud;
    }

    public void setSesionsaludidsesionsalud(long sesionsaludidsesionsalud) {
        this.sesionsaludidsesionsalud = sesionsaludidsesionsalud;
    }

    @Override
    public String toString() {
        return "List_Asis_Salud{" + "idlistsalud=" + idlistsalud + ", estadoasistsalud=" + estadoasistsalud + ", sesionsaludidsesionsalud=" + sesionsaludidsesionsalud + '}';
    }

    public String getFechacreacion() {
        return fechacreacion;
    }

    public void setFechacreacion(String fechacreacion) {
        this.fechacreacion = fechacreacion;
    }

    
}
