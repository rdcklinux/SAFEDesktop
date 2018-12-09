
package com.safe.entity;

import java.io.Serializable;

public class List_Asis_Cap implements Serializable {
    private long idlistacap = 0;
    private long estadoasistcap = 1;
    private long sesioncapidsesioncap = 0;
    private String fechacreacion = (new java.text.SimpleDateFormat("dd-MM-yyyy")).format(new java.util.Date());;

    public long getIdlistacap() {
        return idlistacap;
    }

    public void setIdlistacap(long idlistacap) {
        this.idlistacap = idlistacap;
    }

    public long getEstadoasistcap() {
        return estadoasistcap;
    }

    public void setEstadoasistcap(long estadoasistcap) {
        this.estadoasistcap = estadoasistcap;
    }

    public long getSesioncapidsesioncap() {
        return sesioncapidsesioncap;
    }

    public void setSesioncapidsesioncap(long sesioncapidsesioncap) {
        this.sesioncapidsesioncap = sesioncapidsesioncap;
    }

    @Override
    public String toString() {
        return "List_Asis_Cap{" + "idlistacap=" + idlistacap + ", estadoasistcap=" + estadoasistcap + ", sesioncapidsesioncap=" + sesioncapidsesioncap + '}';
    }

    public String getFechacreacion() {
        return fechacreacion;
    }

    public void setFechacreacion(String fechacreacion) {
        this.fechacreacion = fechacreacion;
    }

    
}
