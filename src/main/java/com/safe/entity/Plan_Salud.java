
package com.safe.entity;

import java.io.Serializable;

public class Plan_Salud implements Serializable {
    private long idplansalud = 0;
    private String fechacreacion = "";
    private long estadoplansalud = 1;
    private long clienteidcliente = 0;

    public long getIdplansalud() {
        return idplansalud;
    }

    public void setIdplansalud(long idplansalud) {
        this.idplansalud = idplansalud;
    }

    public String getFechacreacion() {
        return fechacreacion;
    }

    public void setFechacreacion(String fechacreacion) {
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
