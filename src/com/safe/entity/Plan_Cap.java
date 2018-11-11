
package com.safe.entity;

import java.io.Serializable;
import java.util.Date;

public class Plan_Cap implements Serializable{
    
    
    private long idplancap;
    
    
    private Date fechacreacion;
    
    
    private long estadoplancap;
    
    
    private long clienteidcliente;

    public long getIdplancap() {
        return idplancap;
    }

    public void setIdplancap(long idplancap) {
        this.idplancap = idplancap;
    }

    public Date getFechacreacion() {
        return fechacreacion;
    }

    public void setFechacreacion(Date fechacreacion) {
        this.fechacreacion = fechacreacion;
    }

    public long getEstadoplancap() {
        return estadoplancap;
    }

    public void setEstadoplancap(long estadoplancap) {
        this.estadoplancap = estadoplancap;
    }

    public long getClienteidcliente() {
        return clienteidcliente;
    }

    public void setClienteidcliente(long clienteidcliente) {
        this.clienteidcliente = clienteidcliente;
    }

    @Override
    public String toString() {
        return "Plan_Cap{" + "idplancap=" + idplancap + ", fechacreacion=" + fechacreacion + ", estadoplancap=" + estadoplancap + ", clienteidcliente=" + clienteidcliente + '}';
    }

    
}
