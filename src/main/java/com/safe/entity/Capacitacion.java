
package com.safe.entity;

import java.io.Serializable;

public class Capacitacion implements Serializable {
   
    private long idcap;
    
    
    private String nombrecapacitacion;
    
   
    private long estadocapacitacion;
    
   
    private long plancapidplancap;

    private long tipocapidtipocap;

    public long getIdcap() {
        return idcap;
    }

    public void setIdcap(long idcap) {
        this.idcap = idcap;
    }

    public String getNombrecapacitacion() {
        return nombrecapacitacion;
    }

    public void setNombrecapacitacion(String nombrecapacitacion) {
        this.nombrecapacitacion = nombrecapacitacion;
    }

    public long getEstadocapacitacion() {
        return estadocapacitacion;
    }

    public void setEstadocapacitacion(long estadocapacitacion) {
        this.estadocapacitacion = estadocapacitacion;
    }

    public long getPlancapidplancap() {
        return plancapidplancap;
    }

    public void setPlancapidplancap(long plancapidplancap) {
        this.plancapidplancap = plancapidplancap;
    }

    public long getTipocapidtipocap() {
        return tipocapidtipocap;
    }

    public void setTipocapidtipocap(long tipocapidtipocap) {
        this.tipocapidtipocap = tipocapidtipocap;
    }

    @Override
    public String toString() {
        return "Capacitacion{" + "idcap=" + idcap + ", nombrecapacitacion=" + nombrecapacitacion + ", estadocapacitacion=" + estadocapacitacion + ", plancapidplancap=" + plancapidplancap + ", tipocapidtipocap=" + tipocapidtipocap + '}';
    }

    
           
}
