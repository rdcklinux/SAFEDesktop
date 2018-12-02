/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.safe.dal;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.safe.entity.Sesion_Cap;

/**
 *
 * @author developer
 */
public class Sesion_CapDAL extends DAL {
    
    public Sesion_CapDAL(String domain){
        this.domain = domain;
        initObjectMapper();
    }
    
    public Sesion_Cap byId(int id) throws UnirestException{
        
        String url = getURI("sesionCap/readOneSesionCap/%d");
        HttpResponse<Sesion_Cap[]> response = Unirest.get(String.format(url, id)).asObject(Sesion_Cap[].class);
        Sesion_Cap[] sesiones = response.getBody();
        
        
        return sesiones[0];
    }
    
    public Sesion_Cap[] all() throws UnirestException{
        String url = getURI("sesionCap/getAllSesionCap/");
        HttpResponse<Sesion_Cap[]> response = Unirest.get(String.format(url)).asObject(Sesion_Cap[].class);
        Sesion_Cap[] sesiones = response.getBody();
                
        return sesiones;
    }
    
    public long create(Sesion_Cap sesion) throws UnirestException {
        String url = getURI("sesionCap/createSesionCapSP");
        HttpResponse<String> result = Unirest.post(url)
        .header("accept", "application/json")
        .header("Content-Type", "application/json")
        .body(sesion).asString();
        
        return sesion.getIdsesioncap();
    }
    
    public long update(Sesion_Cap sesion) throws UnirestException {
        String url = getURI("sesionCap/upSesionCap");
        HttpResponse<String> postResponse = Unirest.put(String.format(url))
        .header("accept", "application/json")
        .header("Content-Type", "application/json")
        .body(sesion)
        .asString();
        
        return sesion.getIdsesioncap();
    }
    
    public void delete(long id) throws UnirestException {
        String url = getURI("sesionCap/deleteSesionCap/%d/0");
        
        HttpResponse<String> postResponse = Unirest.put(String.format(url, id))
        .header("accept", "application/json")
        .header("Content-Type", "application/json")
        .body(String.class)
        .asString();
    }
}
