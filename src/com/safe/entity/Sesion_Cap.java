
package com.safe.entity;

import java.io.Serializable;
import java.util.Date;

public class Sesion_Cap implements Serializable{
    
    
    private long idsesioncap;
    
    
    private long numsesioncap;
    
    
    private String nombresesion;
    
    
    private long cupossesion;
    
    
    private Date fechasesion;
    
    
    private Date horainiciocap;
    
    
    private Date horaterminocap;
    
    
    private String descripcionsesion;
    
    
    private long estadosesioncap;
    
    
    private long capacitacionidcap;
    
    
    private long expositoridexpositor;

    public long getIdsesioncap() {
        return idsesioncap;
    }

    public void setIdsesioncap(long idsesioncap) {
        this.idsesioncap = idsesioncap;
    }

    public long getNumsesioncap() {
        return numsesioncap;
    }

    public void setNumsesioncap(long numsesioncap) {
        this.numsesioncap = numsesioncap;
    }

    public String getNombresesion() {
        return nombresesion;
    }

    public void setNombresesion(String nombresesion) {
        this.nombresesion = nombresesion;
    }

    public long getCupossesion() {
        return cupossesion;
    }

    public void setCupossesion(long cupossesion) {
        this.cupossesion = cupossesion;
    }

    public Date getFechasesion() {
        return fechasesion;
    }

    public void setFechasesion(Date fechasesion) {
        this.fechasesion = fechasesion;
    }

    public Date getHorainiciocap() {
        return horainiciocap;
    }

    public void setHorainiciocap(Date horainiciocap) {
        this.horainiciocap = horainiciocap;
    }

    public Date getHoraterminocap() {
        return horaterminocap;
    }

    public void setHoraterminocap(Date horaterminocap) {
        this.horaterminocap = horaterminocap;
    }

    public String getDescripcionsesion() {
        return descripcionsesion;
    }

    public void setDescripcionsesion(String descripcionsesion) {
        this.descripcionsesion = descripcionsesion;
    }

    public long getEstadosesioncap() {
        return estadosesioncap;
    }

    public void setEstadosesioncap(long estadosesioncap) {
        this.estadosesioncap = estadosesioncap;
    }

    public long getCapacitacionidcap() {
        return capacitacionidcap;
    }

    public void setCapacitacionidcap(long capacitacionidcap) {
        this.capacitacionidcap = capacitacionidcap;
    }

    public long getExpositoridexpositor() {
        return expositoridexpositor;
    }

    public void setExpositoridexpositor(long expositoridexpositor) {
        this.expositoridexpositor = expositoridexpositor;
    }

    @Override
    public String toString() {
        return "Sesion_Cap{" + "idsesioncap=" + idsesioncap + ", numsesioncap=" + numsesioncap + ", nombresesion=" + nombresesion + ", cupossesion=" + cupossesion + ", fechasesion=" + fechasesion + ", horainiciocap=" + horainiciocap + ", horaterminocap=" + horaterminocap + ", descripcionsesion=" + descripcionsesion + ", estadosesioncap=" + estadosesioncap + ", capacitacionidcap=" + capacitacionidcap + ", expositoridexpositor=" + expositoridexpositor + '}';
    }

    
    
    

}
