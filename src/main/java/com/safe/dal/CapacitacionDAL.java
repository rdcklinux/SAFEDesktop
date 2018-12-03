/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.safe.dal;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.safe.entity.Capacitacion;

/**
 *
 * @author developer
 */
public class CapacitacionDAL extends DAL {
    
    public CapacitacionDAL(String domain){
        this.domain = domain;
        initObjectMapper();
    }
    
    public Capacitacion byId(int id) throws UnirestException{
        
        String url = getURI("capacitacion/readOneCapacitacion/%d");
        HttpResponse<Capacitacion[]> response = Unirest.get(String.format(url, id)).asObject(Capacitacion[].class);
        Capacitacion[] capacitaciones = response.getBody();
        
        
        return capacitaciones[0];
    }
    
    public Capacitacion[] all() throws UnirestException{
        String url = getURI("capacitacion/getAllCapacitacion/");
        HttpResponse<Capacitacion[]> response = Unirest.get(String.format(url)).asObject(Capacitacion[].class);
        Capacitacion[] capacitaciones = response.getBody();
                
        return capacitaciones;
    }
    
    public long create(Capacitacion capacitacion) throws UnirestException {
        String url = getURI("capacitacion/createCapacitacionSP");
        HttpResponse<Capacitacion[]> result = Unirest.post(url)
        .header("accept", "application/json")
        .header("Content-Type", "application/json")
        .body(capacitacion).asObject(Capacitacion[].class);
        
        Capacitacion[] cap = result.getBody();
        if(cap.length > 0) return cap[0].getIdcap();
        
        return 0;
    }
    
    public long update(Capacitacion capacitacion) throws UnirestException {
        String url = getURI("capacitacion/upCapacitacion");
        HttpResponse<String> postResponse = Unirest.put(String.format(url))
        .header("accept", "application/json")
        .header("Content-Type", "application/json")
        .body(capacitacion)
        .asString();
        
        return capacitacion.getIdcap();
    }
    
    public void delete(long id) throws UnirestException {
        String url = getURI("capacitacion/deleteCapacitacion/%d/0");
        
        HttpResponse<String> postResponse = Unirest.put(String.format(url, id))
        .header("accept", "application/json")
        .header("Content-Type", "application/json")
        .body(String.class)
        .asString();
    }
}
