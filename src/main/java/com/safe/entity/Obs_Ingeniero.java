
package com.safe.entity;

import java.io.Serializable;

public class Obs_Ingeniero implements Serializable{
    
    
    private long idobsingeniero;    
    private String fechahoraobsing = (new java.text.SimpleDateFormat("dd-MM-yyyy")).format(new java.util.Date());
    private String obsing;
    private long evalterridevalterr;
    private long estadoObsIng = 1;
    private long evalterridusuario;

    public long getEvalterridusuario() {
        return evalterridusuario;
    }

    public void setEvalterridusuario(long evalterridusuario) {
        this.evalterridusuario = evalterridusuario;
    }

    public long getIdobsingeniero() {
        return idobsingeniero;
    }

    public void setIdobsingeniero(long idobsingeniero) {
        this.idobsingeniero = idobsingeniero;
    }

    public String getFechahoraobsing() {
        return fechahoraobsing;
    }

    public void setFechahoraobsing(String fechahoraobsing) {
        this.fechahoraobsing = fechahoraobsing;
    }

    public String getObsing() {
        return obsing;
    }

    public void setObsing(String obsing) {
        this.obsing = obsing;
    }

    public long getEvalterridevalterr() {
        return evalterridevalterr;
    }

    public void setEvalterridevalterr(long evalterridevalterr) {
        this.evalterridevalterr = evalterridevalterr;
    }

    public long getEstadoObsIng() {
        return estadoObsIng;
    }

    public void setEstadoObsIng(long estadoObsIng) {
        this.estadoObsIng = estadoObsIng;
    }

    @Override
    public String toString() {
        return "Obs_Ingeniero{" + "idobsingeniero=" + idobsingeniero + ", fechahoraobsing=" + fechahoraobsing + ", obsing=" + obsing + ", evalterridevalterr=" + evalterridevalterr + ", estadoObsIng=" + estadoObsIng + '}';
    }

    

    
}
