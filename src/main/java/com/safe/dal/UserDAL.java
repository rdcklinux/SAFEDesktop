/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.safe.dal;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.safe.entity.Usuario;

/**
 *
 * @author familia
 */
public class UserDAL extends DAL {
    
    public UserDAL(String domain){
        this.domain = domain;
        initObjectMapper();
    }
    
    public Usuario login(String run, String password) throws UnirestException {
        
        String url = getURI("usuarios/login");
        Usuario usuario = new Usuario();
        usuario.setRunusuario(run);
        usuario.setClaveusuario(password);
        usuario.setNombresusuario("");
        usuario.setAppaterno("");
        usuario.setApmaterno("");
        usuario.setFnacimientousuario("");
        usuario.setTelusuario("");
        usuario.setMailusuario("");
        usuario.setSexousuario("");
        
        HttpResponse<Usuario[]> response = Unirest.post(String.format(url))
        .header("accept", "application/json")
        .header("Content-Type", "application/json")
        .body(usuario)
        .asObject(Usuario[].class);
        
        Usuario[] usuarios = response.getBody();
        
        if(usuarios.length > 0) return usuarios[0];
        
        return null;
    }
          
    public Usuario byRun(String run) throws UnirestException {
        
        String url = getURI("usuarios/readOneUsuario/%s");
        HttpResponse<Usuario[]> response = Unirest.get(String.format(url, run)).asObject(Usuario[].class);
        Usuario[] usuarios = response.getBody();
        
        return usuarios[0];
    }
    
    public Usuario[] all() throws UnirestException {
        String url = getURI("usuarios/getAllUsuarios/");
        HttpResponse<Usuario[]> response = Unirest.get(String.format(url)).asObject(Usuario[].class);
        Usuario[] usuarios = response.getBody();
                
        return usuarios;
    }
    
    public long create(Usuario usuario) throws UnirestException {
        String url = getURI("usuarios/createUsuarioSP");
        HttpResponse<Usuario[]> result = Unirest.post(url)
        .header("accept", "application/json")
        .header("Content-Type", "application/json")
        .body(usuario).asObject(Usuario[].class);
        
        Usuario[] entities = result.getBody();
        if(entities.length > 0) return entities[0].getIdusuario();
        
        return 0;
    }
    
    public long update(Usuario usuario) throws UnirestException {
        String url = getURI("usuarios/upUsuario");
        HttpResponse<String> postResponse = Unirest.put(String.format(url))
        .header("accept", "application/json")
        .header("Content-Type", "application/json")
        .body(usuario)
        .asString();
        
        return usuario.getIdusuario();
    }
    
    public void delete(String rut) throws UnirestException {
        String url = getURI("usuarios/deleteMedico/%s/0"); //TODO: deberia ser deleteUsuario
        
        HttpResponse<String> postResponse = Unirest.put(String.format(url, rut))
        .header("accept", "application/json")
        .header("Content-Type", "application/json")
        .body(String.class)
        .asString();
    }
}
