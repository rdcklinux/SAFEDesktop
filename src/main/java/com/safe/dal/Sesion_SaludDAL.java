/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.safe.dal;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.safe.entity.Sesion_Salud;

/**
 *
 * @author developer
 */
public class Sesion_SaludDAL extends DAL {
    
    public Sesion_SaludDAL(String domain){
        this.domain = domain;
        initObjectMapper();
    }
    
    public Sesion_Salud byId(int id) throws UnirestException{
        
        String url = getURI("sesionSalud/readOneSesionSalud/%d");
        HttpResponse<Sesion_Salud[]> response = Unirest.get(String.format(url, id)).asObject(Sesion_Salud[].class);
        Sesion_Salud[] sesiones = response.getBody();
        
        
        return sesiones[0];
    }
    
    public Sesion_Salud[] all() throws UnirestException{
        String url = getURI("sesionSalud/getAllSesionSalud/");
        HttpResponse<Sesion_Salud[]> response = Unirest.get(String.format(url)).asObject(Sesion_Salud[].class);
        Sesion_Salud[] sesiones = response.getBody();
                
        return sesiones;
    }
    
    public long create(Sesion_Salud sesion) throws UnirestException {
        String url = getURI("sesionSalud/createSesionSaludSP");
        HttpResponse<Sesion_Salud[]> result = Unirest.post(url)
        .header("accept", "application/json")
        .header("Content-Type", "application/json")
        .body(sesion).asObject(Sesion_Salud[].class);
        
        Sesion_Salud[] entities = result.getBody();
        if(entities.length > 0) return entities[0].getIdsesionsalud();
        
        return 0;
    }
    
    public long update(Sesion_Salud sesion) throws UnirestException {
        String url = getURI("sesionSalud/upSesionSalud");
        HttpResponse<String> postResponse = Unirest.put(String.format(url))
        .header("accept", "application/json")
        .header("Content-Type", "application/json")
        .body(sesion)
        .asString();
        
        return sesion.getIdsesionsalud();
    }
    
    public void delete(long id) throws UnirestException {
        String url = getURI("sesionSalud/deleteSesionSalud/%d/0");
        
        HttpResponse<String> postResponse = Unirest.put(String.format(url, id))
        .header("accept", "application/json")
        .header("Content-Type", "application/json")
        .body(String.class)
        .asString();
    }
}
