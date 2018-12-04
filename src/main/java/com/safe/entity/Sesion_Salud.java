
package com.safe.entity;

import java.io.Serializable;

public class Sesion_Salud implements Serializable {
    private long idsesionsalud;
    private long numsesionsalud;
    private String nombresesionsalud;
    private long cupossesion;
    private String fechasesion;
    private String horainiciosalud;
    private String horaterminosalud;
    private String descripcionsesionsalud;
    private long medicoidmedico;
    private long examenesidexamenes;
    private long estadosesionsalud;

    public long getIdsesionsalud() {
        return idsesionsalud;
    }

    public void setIdsesionsalud(long idsesionsalud) {
        this.idsesionsalud = idsesionsalud;
    }

    public long getNumsesionsalud() {
        return numsesionsalud;
    }

    public void setNumsesionsalud(long numsesionsalud) {
        this.numsesionsalud = numsesionsalud;
    }

    public String getNombresesionsalud() {
        return nombresesionsalud;
    }

    public void setNombresesionsalud(String nombresesionsalud) {
        this.nombresesionsalud = nombresesionsalud;
    }

    public long getCupossesion() {
        return cupossesion;
    }

    public void setCupossesion(long cupossesion) {
        this.cupossesion = cupossesion;
    }
    
    public String getCupossesionString() {
        return (new Long(cupossesion)).toString();
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

    public String getHorainiciosalud() {
        return horainiciosalud;
    }

    public void setHorainiciosalud(String horainiciosalud) {
        this.horainiciosalud = horainiciosalud;
    }

    public String getHoraterminosalud() {
        return horaterminosalud;
    }

    public void setHoraterminosalud(String horaterminosalud) {
        this.horaterminosalud = horaterminosalud;
    }

    public String getDescripcionsesionsalud() {
        return descripcionsesionsalud;
    }

    public void setDescripcionsesionsalud(String descripcionsesionsalud) {
        this.descripcionsesionsalud = descripcionsesionsalud;
    }

    public long getMedicoidmedico() {
        return medicoidmedico;
    }

    public void setMedicoidmedico(long medicoidmedico) {
        this.medicoidmedico = medicoidmedico;
    }

    public long getExamenesidexamenes() {
        return examenesidexamenes;
    }

    public void setExamenesidexamenes(long examenesidexamenes) {
        this.examenesidexamenes = examenesidexamenes;
    }

    public long getEstadosesionsalud() {
        return estadosesionsalud;
    }

    public void setEstadosesionsalud(long estadosesionsalud) {
        this.estadosesionsalud = estadosesionsalud;
    }

    @Override
    public String toString() {
        return "Sesion_Salud{" + "idsesionsalud=" + idsesionsalud + ", numsesionsalud=" + numsesionsalud + ", nombresesionsalud=" + nombresesionsalud + ", cupossesion=" + cupossesion + ", fechasesion=" + fechasesion + ", horainiciosalud=" + horainiciosalud + ", horaterminosalud=" + horaterminosalud + ", descripcionsesionsalud=" + descripcionsesionsalud + ", medicoidmedico=" + medicoidmedico + ", examenesidexamenes=" + examenesidexamenes + ", estadosesionsalud=" + estadosesionsalud + '}';
    }

    
    
    
}
