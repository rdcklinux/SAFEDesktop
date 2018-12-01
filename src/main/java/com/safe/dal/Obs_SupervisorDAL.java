/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.safe.dal;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.safe.entity.Obs_Supervisor;

/**
 *
 * @author developer
 */
public class Obs_SupervisorDAL extends DAL {
    
    public Obs_SupervisorDAL(String domain){
        this.domain = domain;
        initObjectMapper();
    }
    
    public Obs_Supervisor byId(int id) throws UnirestException{
        
        String url = getURI("obsSupervisor/readOneObsSup/%d");
        HttpResponse<Obs_Supervisor[]> response = Unirest.get(String.format(url, id)).asObject(Obs_Supervisor[].class);
        Obs_Supervisor[] obs = response.getBody();
        
        
        return obs[0];
    }
    
    public Obs_Supervisor[] all() throws UnirestException{
        String url = getURI("obsSupervisor/getAllObsSup/");
        HttpResponse<Obs_Supervisor[]> response = Unirest.get(String.format(url)).asObject(Obs_Supervisor[].class);
        Obs_Supervisor[] obs = response.getBody();
                
        return obs;
    }
    
    public long create(Obs_Supervisor obs) throws UnirestException {
        String url = getURI("obsSupervisor/createObsSupSP");
        HttpResponse<String> result = Unirest.post(url)
        .header("accept", "application/json")
        .header("Content-Type", "application/json")
        .body(obs).asString();
        
        //TODO: debe retornar un ID de cliente
        return obs.getIdobssupervisor();
    }
    
    public long update(Obs_Supervisor obs) throws UnirestException {
        String url = getURI("obsSupervisor/upObsSup");
        HttpResponse<String> postResponse = Unirest.put(String.format(url))
        .header("accept", "application/json")
        .header("Content-Type", "application/json")
        .body(obs)
        .asString();
        
        return obs.getIdobssupervisor();
    }
    
    public void delete(long id) throws UnirestException {
        String url = getURI("obsSupervisor/deleteObsSup/%d/0");
        
        HttpResponse<String> postResponse = Unirest.put(String.format(url, id))
        .header("accept", "application/json")
        .header("Content-Type", "application/json")
        .body(String.class)
        .asString();
    }
}
