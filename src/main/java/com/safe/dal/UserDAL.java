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
    
    public Usuario getUserForRunAndPassword(String username, String password) throws UnirestException {
        String url = getURI("usuarios/readOneUsuario/%s");
        HttpResponse<Usuario[]> response = Unirest.get(String.format(url, username)).asObject(Usuario[].class);
        Usuario[] usuario = response.getBody();
        
        /*
        HttpResponse<JsonNode> jsonResponse = Unirest.get(String.format(url, username)).asJson();
        JSONObject obj = jsonResponse.getBody().getObject();
        usuario = new Usuario();
        usuario
            .setRunusuario(obj.getString("RUN_USUARIO"))
            .setClaveusuario(obj.getString("CLAVE_USUARIO"))
            .setAppaterno(obj.getString("AP_PATERNO"))
            .setApmaterno(obj.getString("AP_MATERNO"))
            .setNombresusuario(obj.getString("NOMBRES_USUARIO"))
            .setPerfilidperfil(obj.getLong("PERFIL_ID_PERFIL"))
        ;
        */
        return usuario[0];
    }
}
