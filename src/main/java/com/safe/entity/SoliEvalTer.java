
package com.safe.entity;

import com.safe.service.SolicitudService;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class SoliEvalTer implements Serializable{
    
    
    private long idsolicitud;
    
    
    private String fechacreacion;
    
    private String fechaderivacion;
    
    private String pdf;
    
    private String direccionvisita;
    
    
    private String descripcionvisita;
    
    
    private long clienteidcliente;
    
    
    private long tipovisitteridtipovister;
    
    
    private long estadosolievalter;
    
    private String clientenombre;

    public long getIdsolicitud() {
        return idsolicitud;
    }

    public void setIdsolicitud(long idsolicitud) {
        this.idsolicitud = idsolicitud;
    }

    public String getFechacreacion() {
        return fechacreacion;
    }

    public void setFechacreacion(String fechacreacion) {
        SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat date = new SimpleDateFormat("dd-MM-yyyy");
        try {
            fechacreacion = date.format(dt.parse(fechacreacion));
        } catch (ParseException ex) {
            fechacreacion = "";
        }
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
    
    public String getTipovisitaText() {
        return SolicitudService.TIPOS[(int)tipovisitteridtipovister];
    }

    public long getEstadosolievalter() {
        return estadosolievalter;
    }

    public void setEstadosolievalter(long estadosolievalter) {
        this.estadosolievalter = estadosolievalter;
    }
    
    public String getPdf() {
        return pdf;
    }

    public void setPdf(String pdf) {
        this.pdf = pdf;
    }

    public String getFechaderivacion() {
        if(fechaderivacion == null) fechaderivacion = "";
        return fechaderivacion;
    }

    public void setFechaderivacion(String fechaderivacion) {
        SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat date = new SimpleDateFormat("dd-MM-yyyy");
        try {
            fechaderivacion = date.format(dt.parse(fechaderivacion));
        } catch (ParseException ex) {
            fechaderivacion = "";
        }
        this.fechaderivacion = fechaderivacion;
    }
    
    public String getClientenombre() {
        return clientenombre;
    }

    public void setClientenombre(String clientenombre) {
        this.clientenombre = clientenombre;
    }

    @Override
    public String toString() {
        return "SoliEvalTer{" + "idsolicitud=" + idsolicitud + ", fechacreacion=" + fechacreacion + ", direccionvisita=" + direccionvisita + ", descripcionvisita=" + descripcionvisita + ", clienteidcliente=" + clienteidcliente + ", tipovisitteridtipovister=" + tipovisitteridtipovister + ", estadosolievalter=" + estadosolievalter + '}';
    }    
    
}
