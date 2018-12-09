/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.safe.entity;

/**
 *
 * @author familia
 */
public class PlanCapacitacion {
    private long idplancap;
    private String fechacreacion = (new java.text.SimpleDateFormat("dd-MM-yyyy")).format(new java.util.Date());;
    private long estadoplancap;
    private long clienteidcliente;

    public long getIdplancap() {
        return idplancap;
    }

    public void setIdplancap(long idplancap) {
        this.idplancap = idplancap;
    }

    public String getFechacreacion() {
        return fechacreacion;
    }

    public void setFechacreacion(String fechacreacion) {
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
    
    
}
