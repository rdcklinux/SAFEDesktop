
package com.safe.entity;

import java.io.Serializable;
import java.util.Date;

public class Obs_Supervisor implements Serializable{
    
    
    private long idobssupervisor;
    
    
    private Date fechahoraobssupervisor;
    
    
    private String obssupervisor;
    
    
    private long estadoObsSupervisor;
    
    
    private long evalterridevalterr;

    public long getIdobssupervisor() {
        return idobssupervisor;
    }

    public void setIdobssupervisor(long idobssupervisor) {
        this.idobssupervisor = idobssupervisor;
    }

    public Date getFechahoraobssupervisor() {
        return fechahoraobssupervisor;
    }

    public void setFechahoraobssupervisor(Date fechahoraobssupervisor) {
        this.fechahoraobssupervisor = fechahoraobssupervisor;
    }

    public String getObssupervisor() {
        return obssupervisor;
    }

    public void setObssupervisor(String obssupervisor) {
        this.obssupervisor = obssupervisor;
    }

    public long getEstadoObsSupervisor() {
        return estadoObsSupervisor;
    }

    public void setEstadoObsSupervisor(long estadoObsSupervisor) {
        this.estadoObsSupervisor = estadoObsSupervisor;
    }

    public long getEvalterridevalterr() {
        return evalterridevalterr;
    }

    public void setEvalterridevalterr(long evalterridevalterr) {
        this.evalterridevalterr = evalterridevalterr;
    }

    @Override
    public String toString() {
        return "Obs_Supervisor{" + "idobssupervisor=" + idobssupervisor + ", fechahoraobssupervisor=" + fechahoraobssupervisor + ", obssupervisor=" + obssupervisor + ", estadoObsSupervisor=" + estadoObsSupervisor + ", evalterridevalterr=" + evalterridevalterr + '}';
    }

    
    
}
