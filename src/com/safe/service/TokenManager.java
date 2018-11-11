/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.safe.service;

import com.safe.entity.Usuario;
import java.util.HashMap;


public class TokenManager {
    
    private boolean isAuthenticated = false;
    private Usuario usuario;
    
    private final HashMap<String, String> roles;
    private String role;
     
     public TokenManager(HashMap roles){
         this.roles = roles;
     }
    
    public boolean getSuccessAuthentication(String username, String password){
        //DUMMY AUTHORIZATION
        usuario = new Usuario();
        usuario.setRunusuario(username);
        usuario.setClaveusuario("qwerty");
        usuario.setAppaterno("Lagunas");
        usuario.setApmaterno("Muñoz");
        usuario.setNombresusuario("Roderick");
        
        isAuthenticated = (username.equals(usuario.getRunusuario()) && password.equals(usuario.getClaveusuario()));
        switch(username){
            case "1-9": this.role = "ROLE_ADMIN";
            break;
            case "2-7": this.role = "ROLE_SUPERVISOR";
            break;
            case "3-5": this.role = "ROLE_ENGINER";
            break;
        }
        
        if(roles.keySet().contains(role)) {
           //TODO: realizar operaciones para el usuario activo 
        } else {
            expire();
        }
        
        return isAuthenticated;
    }
    
    public boolean isAuthenticated(){
        return isAuthenticated;
    }
    
    public void expire(){
        isAuthenticated = false;
        role = null;
    }
    
    public Usuario getUser(){
        return usuario;
    }
    
    public String getUserRole(){
        return role;
    }
    
    public String getRoleName(){
        return roles.get(role);
    }
}
