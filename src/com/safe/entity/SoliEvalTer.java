
package com.safe.entity;

import java.io.Serializable;
import java.util.Date;

public class SoliEvalTer implements Serializable{
    
    
    private long idsolicitud;
    
    
    private Date fechacreacion;
    
    
    private String direccionvisita;
    
    
    private String descripcionvisita;
    
    
    private long clienteidcliente;
    
    
    private long tipovisitteridtipovister;
    
    
    private long estadosolievalter;

    public long getIdsolicitud() {
        return idsolicitud;
    }

    public void setIdsolicitud(long idsolicitud) {
        this.idsolicitud = idsolicitud;
    }

    public Date getFechacreacion() {
        return fechacreacion;
    }

    public void setFechacreacion(Date fechacreacion) {
        this.fechacreacion = fechacreacion;
    }

    public String getDireccionvisita() {
        return direccionvisita;
    }

    public void setDireccionvisita(String direccionvisita) {
        this.direccionvisita = direccionvisita;
    }

    public String getDescripcionvisita() {
        return descripcionvisita;
    }

    public void setDescripcionvisita(String descripcionvisita) {
        this.descripcionvisita = descripcionvisita;
    }

    public long getClienteidcliente() {
        return clienteidcliente;
    }

    public void setClienteidcliente(long clienteidcliente) {
        this.clienteidcliente = clienteidcliente;
    }

    public long getTipovisitteridtipovister() {
        return tipovisitteridtipovister;
    }

    public void setTipovisitteridtipovister(long tipovisitteridtipovister) {
        this.tipovisitteridtipovister = tipovisitteridtipovister;
    }

    public long getEstadosolievalter() {
        return estadosolievalter;
    }

    public void setEstadosolievalter(long estadosolievalter) {
        this.estadosolievalter = estadosolievalter;
    }

    @Override
    public String toString() {
        return "SoliEvalTer{" + "idsolicitud=" + idsolicitud + ", fechacreacion=" + fechacreacion + ", direccionvisita=" + direccionvisita + ", descripcionvisita=" + descripcionvisita + ", clienteidcliente=" + clienteidcliente + ", tipovisitteridtipovister=" + tipovisitteridtipovister + ", estadosolievalter=" + estadosolievalter + '}';
    }    
    
}
