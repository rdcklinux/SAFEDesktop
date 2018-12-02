/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.safe.dal;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.safe.entity.Expositor;

/**
 *
 * @author developer
 */
public class ExpositorDAL extends DAL {
    
    public ExpositorDAL(String domain){
        this.domain = domain;
        initObjectMapper();
    }
    
    public Expositor byId(int id) throws UnirestException{
        
        String url = getURI("expositor/readOneExpositor/%d");
        HttpResponse<Expositor[]> response = Unirest.get(String.format(url, id)).asObject(Expositor[].class);
        Expositor[] expositores = response.getBody();
        
        
        return expositores[0];
    }
    
    public Expositor[] all() throws UnirestException{
        String url = getURI("expositor/getAllExpositor/");
        HttpResponse<Expositor[]> response = Unirest.get(String.format(url)).asObject(Expositor[].class);
        Expositor[] expositores = response.getBody();
                
        return expositores;
    }
    
    public long create(Expositor expositor) throws UnirestException {
        String url = getURI("expositor/createExpositorSP");
        HttpResponse<String> result = Unirest.post(url)
        .header("accept", "application/json")
        .header("Content-Type", "application/json")
        .body(expositor).asString();
        
        //TODO: debe retornar un ID de cliente
        return expositor.getIdexpositor();
    }
    
    public long update(Expositor expositor) throws UnirestException {
        String url = getURI("expositor/upExpositor");
        HttpResponse<String> postResponse = Unirest.put(String.format(url))
        .header("accept", "application/json")
        .header("Content-Type", "application/json")
        .body(expositor)
        .asString();
        
        return expositor.getIdexpositor();
    }
    
    public void delete(long id) throws UnirestException {
        String url = getURI("expositor/deleteExpositor/%d/0");
        
        HttpResponse<String> postResponse = Unirest.put(String.format(url, id))
        .header("accept", "application/json")
        .header("Content-Type", "application/json")
        .body(String.class)
        .asString();
    }
}
