/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.safe.dal;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.safe.entity.PlanCapacitacion;

/**
 *
 * @author developer
 */
public class PlanCapacitacionDAL extends DAL {
    
    public PlanCapacitacionDAL(String domain){
        this.domain = domain;
        initObjectMapper();
    }
    
    public PlanCapacitacion byId(int id) throws UnirestException{
        
        String url = getURI("planCapacitacion/readOnePlanCap/%d");
        HttpResponse<PlanCapacitacion[]> response = Unirest.get(String.format(url, id)).asObject(PlanCapacitacion[].class);
        PlanCapacitacion[] plan = response.getBody();
        
        
        return plan[0];
    }
    
    public PlanCapacitacion[] all() throws UnirestException{
        String url = getURI("planCapacitacion/getAllPlanCap/");
        HttpResponse<PlanCapacitacion[]> response = Unirest.get(String.format(url)).asObject(PlanCapacitacion[].class);
        PlanCapacitacion[] planes = response.getBody();
                
        return planes;
    }
    
    public long create(PlanCapacitacion plan) throws UnirestException {
        String url = getURI("planCapacitacion/createPlanCapSP");
        HttpResponse<PlanCapacitacion[]> result = Unirest.post(url)
        .header("accept", "application/json")
        .header("Content-Type", "application/json")
        .body(plan).asObject(PlanCapacitacion[].class);
        
        PlanCapacitacion[] entities = result.getBody();
        if(entities.length > 0) return entities[0].getIdplancap();
        
        return 0;
    }
    
    public long update(PlanCapacitacion capacitacion) throws UnirestException {
        String url = getURI("planCapacitacion/upPlanCap");
        HttpResponse<String> postResponse = Unirest.put(String.format(url))
        .header("accept", "application/json")
        .header("Content-Type", "application/json")
        .body(capacitacion)
        .asString();
        
        return capacitacion.getIdplancap();
    }
    
    public void delete(long id) throws UnirestException {
        String url = getURI("planCapacitacion/deletePlanCap/%d/0");
        
        HttpResponse<String> postResponse = Unirest.put(String.format(url, id))
        .header("accept", "application/json")
        .header("Content-Type", "application/json")
        .body(String.class)
        .asString();
    }
}
