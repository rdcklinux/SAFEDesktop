
package com.safe.entity;

import java.io.Serializable;

public class Sesion_Cap implements Serializable {    
    private long idsesioncap = 0;
    private long numsesioncap = 0;
    private String nombresesion = "";
    private long cupossesion = 0;
    private String fechasesion = "";
    private String horainiciocap = "";
    private String horaterminocap = "";
    private String descripcionsesion = "";
    private long estadosesioncap = 1;
    private long capacitacionidcap = 0;
    private long expositoridexpositor = 0;

    public long getIdsesioncap() {
        return idsesioncap;
    }

    public void setIdsesioncap(Long idsesioncap) {
        this.idsesioncap = idsesioncap;
    }

    public long getNumsesioncap() {
        return numsesioncap;
    }

    public void setNumsesioncap(Long numsesioncap) {
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

    public void setCupossesion(Long cupossesion) {
        this.cupossesion = cupossesion;
    }
    
    public String getCupossesionString() {
        return (new Long(this.cupossesion)).toString();
    }
    
    public void setCupossesionString(String cupossesion) {
        this.cupossesion = Long.parseLong(cupossesion);
    }

    public String getFechasesion() {
        return fechasesion;
    }

    public void setFechasesion(String fechasesion) {
        this.fechasesion = fechasesion;
    }

    public String getHorainiciocap() {
        return horainiciocap;
    }
    
    public String getHorainiciocapFixed() {
        return horainiciocap.substring(11,16);
    }

    public void setHorainiciocap(String horainiciocap) {
        
        this.horainiciocap = horainiciocap;
    }

    public String getHoraterminocap() {
        return horaterminocap;
    }
    
    public String getHoraterminocapFixed() {
        return horaterminocap.substring(11,16);
    }

    public void setHoraterminocap(String horaterminocap) {
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

    public void setEstadosesioncap(Long estadosesioncap) {
        this.estadosesioncap = estadosesioncap;
    }

    public long getCapacitacionidcap() {
        return capacitacionidcap;
    }
    
    public int getCapacitacionidcapIndex() {
        return (int)capacitacionidcap;
    }

    public void setCapacitacionidcap(Long capacitacionidcap) {
        this.capacitacionidcap = capacitacionidcap;
    }
    
    public void setCapacitacionidcapIndex(int capacitacionidcap) {
        this.capacitacionidcap = capacitacionidcap;
    }

    public long getExpositoridexpositor() {
        return expositoridexpositor;
    }

    public void setExpositoridexpositor(Long expositoridexpositor) {
        this.expositoridexpositor = expositoridexpositor;
    }
    
    public int getExpositoridexpositorIndex() {
        return (int)expositoridexpositor;
    }

    public void setExpositoridexpositorIndex(int expositoridexpositor) {
        this.expositoridexpositor = expositoridexpositor;
    }

    @Override
    public String toString() {
        return "Sesion_Cap{" + "idsesioncap=" + idsesioncap + ", numsesioncap=" + numsesioncap + ", nombresesion=" + nombresesion + ", cupossesion=" + cupossesion + ", fechasesion=" + fechasesion + ", horainiciocap=" + horainiciocap + ", horaterminocap=" + horaterminocap + ", descripcionsesion=" + descripcionsesion + ", estadosesioncap=" + estadosesioncap + ", capacitacionidcap=" + capacitacionidcap + ", expositoridexpositor=" + expositoridexpositor + '}';
    }
}
