
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

    public Usuario setIdusuario(long idusuario) {
        this.idusuario = idusuario;
        
        return this;
    }

    public String getRunusuario() {
        return runusuario;
    }

    public Usuario setRunusuario(String runusuario) {
        this.runusuario = runusuario;
        
        return this;
    }

    public String getNombresusuario() {
        return nombresusuario;
    }

    public Usuario setNombresusuario(String nombresusuario) {
        this.nombresusuario = nombresusuario;
        
        return this;
    }

    public String getAppaterno() {
        return appaterno;
    }

    public Usuario setAppaterno(String appaterno) {
        this.appaterno = appaterno;
        
        return this;        
    }

    public String getApmaterno() {
        return apmaterno;
    }

    public Usuario setApmaterno(String apmaterno) {
        this.apmaterno = apmaterno;
        
        return this;
    }

    public Date getFnacimientousuario() {
        return fnacimientousuario;
    }

    public Usuario setFnacimientousuario(Date fnacimientousuario) {
        this.fnacimientousuario = fnacimientousuario;
        
        return this;
    }

    public String getSexousuario() {
        return sexousuario;
    }

    public Usuario setSexousuario(String sexousuario) {
        this.sexousuario = sexousuario;
        
        return this;
    }

    public String getTelusuario() {
        return telusuario;
    }

    public Usuario setTelusuario(String telusuario) {
        this.telusuario = telusuario;
        
        return this;
    }

    public String getMailusuario() {
        return mailusuario;
    }

    public Usuario setMailusuario(String mailusuario) {
        this.mailusuario = mailusuario;
        
        return this;
    }

    public long getEstadousuario() {
        return estadousuario;
    }

    public Usuario setEstadousuario(long estadousuario) {
        this.estadousuario = estadousuario;
        
        return this;
    }

    public String getClaveusuario() {
        return claveusuario;
    }

    public Usuario setClaveusuario(String claveusuario) {
        this.claveusuario = claveusuario;
        
        return this;
    }

    public long getPerfilidperfil() {
        return perfilidperfil;
    }

    public Usuario setPerfilidperfil(long perfilidperfil) {
        this.perfilidperfil = perfilidperfil;
        
        return this;
    }

    public long getClienteidcliente() {
        return clienteidcliente;
    }

    public Usuario setClienteidcliente(long clienteidcliente) {
        this.clienteidcliente = clienteidcliente;
        
        return this;
    }
    
    public String getFullName(){
        return nombresusuario + " " + appaterno + " " + apmaterno;
    }
}
