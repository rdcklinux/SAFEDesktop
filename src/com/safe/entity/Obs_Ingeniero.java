
package com.safe.entity;

import java.io.Serializable;
import java.util.Date;

public class Obs_Ingeniero implements Serializable{
    
    
    private long idobsingeniero;
    
    
    private Date fechahoraobsing;
    
    
    private String obsing;
    
    
    private long evalterridevalterr;
    
    
    private long estadoObsIng;

    public long getIdobsingeniero() {
        return idobsingeniero;
    }

    public void setIdobsingeniero(long idobsingeniero) {
        this.idobsingeniero = idobsingeniero;
    }

    public Date getFechahoraobsing() {
        return fechahoraobsing;
    }

    public void setFechahoraobsing(Date fechahoraobsing) {
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
