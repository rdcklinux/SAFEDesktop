/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.safe.dal;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.safe.entity.Obs_Ingeniero;

/**
 *
 * @author developer
 */
public class Obs_IngenieroDAL extends DAL {
    
    public Obs_IngenieroDAL(String domain){
        this.domain = domain;
        initObjectMapper();
    }
    
    public Obs_Ingeniero byId(int id) throws UnirestException{
        
        String url = getURI("obsIngeniero/readOneObsIng/%d");
        HttpResponse<Obs_Ingeniero[]> response = Unirest.get(String.format(url, id)).asObject(Obs_Ingeniero[].class);
        Obs_Ingeniero[] obs = response.getBody();
        
        
        return obs[0];
    }
    
    public Obs_Ingeniero[] all() throws UnirestException{
        String url = getURI("obsIngeniero/getAllObsIng/");
        HttpResponse<Obs_Ingeniero[]> response = Unirest.get(String.format(url)).asObject(Obs_Ingeniero[].class);
        Obs_Ingeniero[] obs = response.getBody();
                
        return obs;
    }
    
    public long create(Obs_Ingeniero obs) throws UnirestException {
        String url = getURI("obsIngeniero/createObsIngSP");
        HttpResponse<String> result = Unirest.post(url)
        .header("accept", "application/json")
        .header("Content-Type", "application/json")
        .body(obs).asString();
        
        return obs.getIdobsingeniero();
    }
    
    public long update(Obs_Ingeniero obs) throws UnirestException {
        String url = getURI("obsIngeniero/upObsIng");
        HttpResponse<String> postResponse = Unirest.put(String.format(url))
        .header("accept", "application/json")
        .header("Content-Type", "application/json")
        .body(obs)
        .asString();
        
        return obs.getIdobsingeniero();
    }
    
    public void delete(long id) throws UnirestException {
        String url = getURI("obsIngeniero/deleteObsIng/%d/0");
        
        HttpResponse<String> postResponse = Unirest.put(String.format(url, id))
        .header("accept", "application/json")
        .header("Content-Type", "application/json")
        .body(String.class)
        .asString();
    }
}
