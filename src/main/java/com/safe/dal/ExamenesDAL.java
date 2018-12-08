/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.safe.dal;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.safe.entity.Examenes;

/**
 *
 * @author developer
 */
public class ExamenesDAL extends DAL {
    
    public ExamenesDAL(String domain){
        this.domain = domain;
        initObjectMapper();
    }
    
    public Examenes byId(int id) throws UnirestException{
        
        String url = getURI("examenes/readOneExamen/%d");
        HttpResponse<Examenes[]> response = Unirest.get(String.format(url, id)).asObject(Examenes[].class);
        Examenes[] examenes = response.getBody();
        
        
        return examenes[0];
    }
    
    public Examenes[] all() throws UnirestException{
        String url = getURI("examenes/getAllExamenes/");
        HttpResponse<Examenes[]> response = Unirest.get(String.format(url)).asObject(Examenes[].class);
        Examenes[] examenes = response.getBody();
                
        return examenes;
    }
    
    public long create(Examenes examen) throws UnirestException {
        String url = getURI("examenes/createExamenSP");
        HttpResponse<Examenes[]> result = Unirest.post(url)
        .header("accept", "application/json")
        .header("Content-Type", "application/json")
        .body(examen).asObject(Examenes[].class);
        
        Examenes[] cap = result.getBody();
        if(cap.length > 0) return cap[0].getIdexamen();
        
        return 0;
    }
    
    public long update(Examenes examen) throws UnirestException {
        String url = getURI("examenes/upExamen");
        HttpResponse<String> postResponse = Unirest.put(String.format(url))
        .header("accept", "application/json")
        .header("Content-Type", "application/json")
        .body(examen)
        .asString();
        
        return examen.getIdexamen();
    }
    
    public void delete(long id) throws UnirestException {
        String url = getURI("examenes/deleteExamenes/%d/0");
        
        HttpResponse<String> postResponse = Unirest.put(String.format(url, id))
        .header("accept", "application/json")
        .header("Content-Type", "application/json")
        .body(String.class)
        .asString();
    }
}
