
package com.safe.entity;

import com.safe.service.UsuarioService;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Usuario implements Serializable {
    private long idusuario;
    private String runusuario;
    private String nombresusuario;
    private String appaterno;
    private String apmaterno;
    private String fnacimientousuario;
    private String sexousuario;
    private String telusuario;
    private String mailusuario;
    private long estadousuario;
    private String claveusuario;
    private long perfilidperfil;
    private long clienteidcliente;
    private String fechacreacion = (new java.text.SimpleDateFormat("dd-MM-yyyy")).format(new java.util.Date());;

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

    public String getFnacimientousuario() {
        SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat date = new SimpleDateFormat("dd-MM-yyyy");
        try {
            return date.format(dt.parse(this.fnacimientousuario));
        } catch (ParseException ex) {
            return "";
        }
    }

    public Usuario setFnacimientousuario(String fnacimientousuario) {
        this.fnacimientousuario = fnacimientousuario;
        
        return this;
    }

    public String getSexousuario() {
        return sexousuario;
    }
    
    public int getSexousuarioIndex() {
        return sexousuario.equals(UsuarioService.GENERO[1])?1:0;
    }
    
    public Usuario setSexousuario(String sexousuario) {
        this.sexousuario = sexousuario;
        
        return this;
    }
    
    public Usuario setSexousuarioIndex(int sexousuario) {
        this.sexousuario = UsuarioService.GENERO[sexousuario];
        
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
    
    public int getEstadousuarioIndex() {
        return (int)estadousuario - 1;
    }

    public Usuario setEstadousuario(long estadousuario) {
        this.estadousuario = estadousuario;
        
        return this;
    }
    
    public Usuario setEstadousuarioIndex(long estadousuario) {
        this.estadousuario = estadousuario + 1;
        
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
    
    public int getPerfilidperfilIndex() {
        return (int)perfilidperfil - 1;
    }

    public Usuario setPerfilidperfil(long perfilidperfil) {
        this.perfilidperfil = perfilidperfil;
        
        return this;
    }
    
    public Usuario setPerfilidperfilIndex(long perfilidperfil) {
        this.perfilidperfil = perfilidperfil + 1;
        
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

    public String getFechacreacion() {
        return fechacreacion;
    }

    public void setFechacreacion(String fechacreacion) {
        this.fechacreacion = fechacreacion;
    }
}
