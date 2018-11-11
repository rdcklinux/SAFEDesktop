
package com.safe.entity;

import java.io.Serializable;
import java.util.Date;

public class Usuario implements Serializable{
    
    
    private long idusuario;
    
    
    private String runusuario;
    
    
    private String nombresusuario;
    
    
    private String appaterno;
    
    
    private String apmaterno;
    
    
    private Date fnacimientousuario;
    
    
    private String sexousuario;

    
    private String telusuario;
    
    
    private String mailusuario;
    
    
    private long estadousuario;
    
    
    private String claveusuario;
    
    
    private long perfilidperfil;
    
    
    private long clienteidcliente;

    public long getIdusuario() {
        return idusuario;
    }

    public void setIdusuario(long idusuario) {
        this.idusuario = idusuario;
    }

    public String getRunusuario() {
        return runusuario;
    }

    public void setRunusuario(String runusuario) {
        this.runusuario = runusuario;
    }

    public String getNombresusuario() {
        return nombresusuario;
    }

    public void setNombresusuario(String nombresusuario) {
        this.nombresusuario = nombresusuario;
    }

    public String getAppaterno() {
        return appaterno;
    }

    public void setAppaterno(String appaterno) {
        this.appaterno = appaterno;
    }

    public String getApmaterno() {
        return apmaterno;
    }

    public void setApmaterno(String apmaterno) {
        this.apmaterno = apmaterno;
    }

    public Date getFnacimientousuario() {
        return fnacimientousuario;
    }

    public void setFnacimientousuario(Date fnacimientousuario) {
        this.fnacimientousuario = fnacimientousuario;
    }

    public String getSexousuario() {
        return sexousuario;
    }

    public void setSexousuario(String sexousuario) {
        this.sexousuario = sexousuario;
    }

    public String getTelusuario() {
        return telusuario;
    }

    public void setTelusuario(String telusuario) {
        this.telusuario = telusuario;
    }

    public String getMailusuario() {
        return mailusuario;
    }

    public void setMailusuario(String mailusuario) {
        this.mailusuario = mailusuario;
    }

    public long getEstadousuario() {
        return estadousuario;
    }

    public void setEstadousuario(long estadousuario) {
        this.estadousuario = estadousuario;
    }

    public String getClaveusuario() {
        return claveusuario;
    }

    public void setClaveusuario(String claveusuario) {
        this.claveusuario = claveusuario;
    }

    public long getPerfilidperfil() {
        return perfilidperfil;
    }

    public void setPerfilidperfil(long perfilidperfil) {
        this.perfilidperfil = perfilidperfil;
    }

    public long getClienteidcliente() {
        return clienteidcliente;
    }

    public void setClienteidcliente(long clienteidcliente) {
        this.clienteidcliente = clienteidcliente;
    }
    
    public String getFullName(){
        return nombresusuario + " " + appaterno + " " + apmaterno;
    }

    @Override
    public String toString() {
        return "Usuarios{" + "idusuario=" + idusuario + ", runusuario=" + runusuario + ", nombresusuario=" + nombresusuario + ", appaterno=" + appaterno + ", apmaterno=" + apmaterno + ", fnacimientousuario=" + fnacimientousuario + ", sexousuario=" + sexousuario + ", telusuario=" + telusuario + ", mailusuario=" + mailusuario + ", estadousuario=" + estadousuario + ", claveusuario=" + claveusuario + ", perfilidperfil=" + perfilidperfil + ", clienteidcliente=" + clienteidcliente + '}';
    }   
}
