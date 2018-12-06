/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.safe.service;

import com.mashape.unirest.http.exceptions.UnirestException;
import com.safe.dal.UserDAL;
import com.safe.entity.Usuario;
import java.util.HashMap;


public class TokenManager {
    
    private boolean isAuthenticated = false;
    private Usuario usuario;
    
    private final HashMap<String, String> roles;
    private String role;
    private final UserDAL userDAL;
     
    public TokenManager(HashMap<String, String> roles, String domain){
        this.roles = roles;
        userDAL = new UserDAL(domain);
    }
    
    public boolean getSuccessAuthentication(String username, String password, boolean impersonate){
        try {
            if(impersonate) {
                usuario = userDAL.byRun(username);
            } else {
                usuario = userDAL.login(username, password);
            }
            if(usuario == null) {
                expire();
                return false;
            }
        }catch(UnirestException e) {
            expire();
            return false;
        }
        isAuthenticated = true;
        switch ((int)usuario.getPerfilidperfil()) {
            case 1:
                this.role = "ROLE_ADMIN";
                break;
            case 2:
                this.role = "ROLE_SUPERVISOR";
                break;
            case 3:
                this.role = "ROLE_ENGINEER";
                break;
            default:
                this.role = null;
                isAuthenticated = false;
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
    
    public void authenticateDebug(String userRole){
        role = userRole;
        isAuthenticated = true;
    }
    
    public String getRoleName(){
        return roles.get(role);
    }
}
