
package com.safe.entity;

import java.io.Serializable;
import java.util.Date;

public class Plan_Salud implements Serializable{
    
    
    private long idplansalud;
    
    
    private Date fechacreacion;
    
    
    private long estadoplansalud;
    
    
    private long clienteidcliente;

    public long getIdplansalud() {
        return idplansalud;
    }

    public void setIdplansalud(long idplansalud) {
        this.idplansalud = idplansalud;
    }

    public Date getFechacreacion() {
        return fechacreacion;
    }

    public void setFechacreacion(Date fechacreacion) {
        this.fechacreacion = fechacreacion;
    }

    public long getEstadoplansalud() {
        return estadoplansalud;
    }

    public void setEstadoplansalud(long estadoplansalud) {
        this.estadoplansalud = estadoplansalud;
    }

    public long getClienteidcliente() {
        return clienteidcliente;
    }

    public void setClienteidcliente(long clienteidcliente) {
        this.clienteidcliente = clienteidcliente;
    }

    @Override
    public String toString() {
        return "Plan_Salud{" + "idplansalud=" + idplansalud + ", fechacreacion=" + fechacreacion + ", estadoplansalud=" + estadoplansalud + ", clienteidcliente=" + clienteidcliente + '}';
    }

    

    
}
