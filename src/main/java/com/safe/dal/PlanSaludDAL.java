/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.safe.dal;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.safe.entity.Plan_Salud;

/**
 *
 * @author developer
 */
public class PlanSaludDAL extends DAL {
    
    public PlanSaludDAL(String domain){
        this.domain = domain;
        initObjectMapper();
    }
    
    public Plan_Salud byId(int id) throws UnirestException{
        
        String url = getURI("planSalud/readOnePlanSalud/%d");
        HttpResponse<Plan_Salud[]> response = Unirest.get(String.format(url, id)).asObject(Plan_Salud[].class);
        Plan_Salud[] plan = response.getBody();
        
        
        return plan[0];
    }
    
    public Plan_Salud[] all() throws UnirestException{
        String url = getURI("planSalud/getAllPlanSalud/");
        HttpResponse<Plan_Salud[]> response = Unirest.get(String.format(url)).asObject(Plan_Salud[].class);
        Plan_Salud[] planes = response.getBody();
                
        return planes;
    }
    
    public long create(Plan_Salud plan) throws UnirestException {
        String url = getURI("planSalud/createPlanSaludSP");
        HttpResponse<Plan_Salud[]> result = Unirest.post(url)
        .header("accept", "application/json")
        .header("Content-Type", "application/json")
        .body(plan).asObject(Plan_Salud[].class);
        
        Plan_Salud[] entities = result.getBody();
        if(entities.length > 0) return entities[0].getIdplansalud();
        
        return 0;
    }
    
    public long update(Plan_Salud capacitacion) throws UnirestException {
        String url = getURI("planSalud/upPlanSalud");
        HttpResponse<String> postResponse = Unirest.put(String.format(url))
        .header("accept", "application/json")
        .header("Content-Type", "application/json")
        .body(capacitacion)
        .asString();
        
        return capacitacion.getIdplansalud();
    }
    
    public void delete(long id) throws UnirestException {
        String url = getURI("planSalud/deletePlanSalud/%d/0");
        
        HttpResponse<String> postResponse = Unirest.put(String.format(url, id))
        .header("accept", "application/json")
        .header("Content-Type", "application/json")
        .body(String.class)
        .asString();
    }
}
