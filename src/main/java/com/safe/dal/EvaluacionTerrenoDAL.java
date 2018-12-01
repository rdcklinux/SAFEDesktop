/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.safe.dal;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.safe.entity.Eval_Terr;

/**
 *
 * @author developer
 */
public class EvaluacionTerrenoDAL extends DAL {
    
    public EvaluacionTerrenoDAL(String domain){
        this.domain = domain;
        initObjectMapper();
    }
    
    public Eval_Terr byId(int id) throws UnirestException{
        
        String url = getURI("evalTerreno/readOneEvalTerreno/%d");
        HttpResponse<Eval_Terr[]> response = Unirest.get(String.format(url, id)).asObject(Eval_Terr[].class);
        Eval_Terr[] evaluaciones = response.getBody();
        
        
        return evaluaciones[0];
    }
    
    public Eval_Terr[] all() throws UnirestException{
        String url = getURI("evalTerreno/getAllEvalTerreno/");
        HttpResponse<Eval_Terr[]> response = Unirest.get(String.format(url)).asObject(Eval_Terr[].class);
        Eval_Terr[] evaluaciones = response.getBody();
                
        return evaluaciones;
    }
    
    public long create(Eval_Terr evaluacion) throws UnirestException {
        String url = getURI("evalTerreno/createEvalTerrenoSP");
        HttpResponse<String> result = Unirest.post(url)
        .header("accept", "application/json")
        .header("Content-Type", "application/json")
        .body(evaluacion).asString();
        
        //TODO: debe retornar un ID de cliente
        return evaluacion.getIdevalterr();
    }
    
    public long update(Eval_Terr evaluacion) throws UnirestException {
        String url = getURI("evalTerreno/upEvalTerreno");
        HttpResponse<String> postResponse = Unirest.put(String.format(url))
        .header("accept", "application/json")
        .header("Content-Type", "application/json")
        .body(evaluacion)
        .asString();
        
        return evaluacion.getIdevalterr();
    }
    
    public void delete(long id) throws UnirestException {
        String url = getURI("evalTerreno/deleteEvalTerreno/%d/0");
        
        HttpResponse<String> postResponse = Unirest.put(String.format(url, id))
        .header("accept", "application/json")
        .header("Content-Type", "application/json")
        .body(String.class)
        .asString();
    }
}
