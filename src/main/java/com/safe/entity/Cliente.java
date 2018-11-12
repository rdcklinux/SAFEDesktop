
package com.safe.entity;

import java.io.Serializable;

public class Cliente implements Serializable{
    
    
    private long idcliente;
    
    
    private String razonsocial;
    
    
    private String rutcliente;
    
    
    private String girocliente;
    
    
    private String direccioncliente;
    
    
    private String teloficina;
    
    
    private String nombrecontacto;
    
    
    private String fonocontacto;
    
    
    private String mailcontacto;
    
    
    private String cargocontacto;
    
    
    private String observacionescliente;
    
    
    private long estadocliente;

    public long getIdcliente() {
        return idcliente;
    }

    public void setIdcliente(long idcliente) {
        this.idcliente = idcliente;
    }

    public String getRazonsocial() {
        return razonsocial;
    }

    public void setRazonsocial(String razonsocial) {
        this.razonsocial = razonsocial;
    }

    public String getRutcliente() {
        return rutcliente;
    }

    public void setRutcliente(String rutcliente) {
        this.rutcliente = rutcliente;
    }

    public String getGirocliente() {
        return girocliente;
    }

    public void setGirocliente(String girocliente) {
        this.girocliente = girocliente;
    }

    public String getDireccioncliente() {
        return direccioncliente;
    }

    public void setDireccioncliente(String direccioncliente) {
        this.direccioncliente = direccioncliente;
    }

    public String getTeloficina() {
        return teloficina;
    }

    public void setTeloficina(String teloficina) {
        this.teloficina = teloficina;
    }

    public String getNombrecontacto() {
        return nombrecontacto;
    }

    public void setNombrecontacto(String nombrecontacto) {
        this.nombrecontacto = nombrecontacto;
    }

    public String getFonocontacto() {
        return fonocontacto;
    }

    public void setFonocontacto(String fonocontacto) {
        this.fonocontacto = fonocontacto;
    }

    public String getMailcontacto() {
        return mailcontacto;
    }

    public void setMailcontacto(String mailcontacto) {
        this.mailcontacto = mailcontacto;
    }

    public String getCargocontacto() {
        return cargocontacto;
    }

    public void setCargocontacto(String cargocontacto) {
        this.cargocontacto = cargocontacto;
    }

    public String getObservacionescliente() {
        return observacionescliente;
    }

    public void setObservacionescliente(String observacionescliente) {
        this.observacionescliente = observacionescliente;
    }

    public long getEstadocliente() {
        return estadocliente;
    }

    public void setEstadocliente(long estadocliente) {
        this.estadocliente = estadocliente;
    }

    @Override
    public String toString() {
        return "Cliente{" + "idcliente=" + idcliente + ", razonsocial=" + razonsocial + ", rutcliente=" + rutcliente + ", girocliente=" + girocliente + ", direccioncliente=" + direccioncliente + ", teloficina=" + teloficina + ", nombrecontacto=" + nombrecontacto + ", fonocontacto=" + fonocontacto + ", mailcontacto=" + mailcontacto + ", cargocontacto=" + cargocontacto + ", observacionescliente=" + observacionescliente + ", estadocliente=" + estadocliente + '}';
    }
    
        
}
