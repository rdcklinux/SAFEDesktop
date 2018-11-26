/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.safe.service;

import com.mashape.unirest.http.exceptions.UnirestException;
import com.safe.dal.UserDAL;
import com.safe.entity.Usuario;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author familia
 */
public class UsuarioService {

    private final UserDAL userDAL;
    
    public static String[] ESTADOS = {
        "Todos",
        "Activo",
        "Registro pendiente",
        "Desactivado",
    };
    
    public static String[] PERFIL = {
        "",
        "Administrador",
        "Supervisor",
        "Prevencionista",
        "Trabajador",
        "Técnico",
        "Cliente",
        "Médico",
    };
    
     public static String[] GENERO = {        
        "Masculino",
        "Femenino",
    };
    
    public UsuarioService(String domain){
        userDAL = new UserDAL(domain);
    }
    
    public Usuario getOne(String run){
        Usuario usuario = null;
        try {
            usuario = userDAL.byRun(run);
        } catch (UnirestException ex) {
            Logger.getLogger(ClienteService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return usuario;
    }
    
    public Usuario[] getCollection(){
        Usuario[] clientes = null;
        try {
            clientes = userDAL.all();
        } catch (UnirestException ex) {
            Logger.getLogger(ClienteService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return clientes;
    }
    
    public long save(Usuario usuario) {
        long id = 0;
        if(usuario.getIdusuario() > 0){
            try {
                id = userDAL.update(usuario);
            } catch (UnirestException ex) {
                Logger.getLogger(ClienteService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else {
            try {
                id = userDAL.create(usuario);
            } catch (UnirestException ex) {
                Logger.getLogger(ClienteService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return id;
    }
    
    public void delete(long id) {
        try {
            userDAL.delete(id);
        } catch (UnirestException ex) {
            Logger.getLogger(ClienteService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
