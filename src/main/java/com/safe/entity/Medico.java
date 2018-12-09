
package com.safe.entity;

import java.io.Serializable;

public class Medico implements Serializable {
    private long idmedico;
    private String runmedico;
    private String nombremedico;
    private String universidadMed;
    private String mailmedico;
    private String telmedico;
    private long estadomedico;
    private String fechacreacion = (new java.text.SimpleDateFormat("dd-MM-yyyy")).format(new java.util.Date());;

    public long getIdmedico() {
        return idmedico;
    }

    public void setIdmedico(long idmedico) {
        this.idmedico = idmedico;
    }

    public String getRunmedico() {
        return runmedico;
    }

    public void setRunmedico(String runmedico) {
        this.runmedico = runmedico;
    }

    public String getNombremedico() {
        return nombremedico;
    }

    public void setNombremedico(String nombremedico) {
        this.nombremedico = nombremedico;
    }

    public String getUniversidadMed() {
        return universidadMed;
    }

    public void setUniversidadMed(String universidadMed) {
        this.universidadMed = universidadMed;
    }

    public String getMailmedico() {
        return mailmedico;
    }

    public void setMailmedico(String mailmedico) {
        this.mailmedico = mailmedico;
    }

    public String getTelmedico() {
        return telmedico;
    }

    public void setTelmedico(String telmedico) {
        this.telmedico = telmedico;
    }

    public long getEstadomedico() {
        return estadomedico;
    }

    public void setEstadomedico(long estadomedico) {
        this.estadomedico = estadomedico;
    }
    
    public int getEstadomedicoIndex() {
        return (new Long(estadomedico)).intValue();
    }

    public void setEstadomedicoIndex(int estadomedico) {
        this.estadomedico = estadomedico;
    }

    @Override
    public String toString() {
        return "Medico{" + "idmedico=" + idmedico + ", runmedico=" + runmedico + ", nombremedico=" + nombremedico + ", universidadMed=" + universidadMed + ", mailmedico=" + mailmedico + ", telmedico=" + telmedico + ", estadomedico=" + estadomedico + '}';
    }    

    public String getFechacreacion() {
        return fechacreacion;
    }

    public void setFechacreacion(String fechacreacion) {
        this.fechacreacion = fechacreacion;
    }
    
}
