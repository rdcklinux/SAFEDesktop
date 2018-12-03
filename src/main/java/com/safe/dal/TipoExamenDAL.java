/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.safe.dal;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.safe.entity.TipoExamen;

/**
 *
 * @author developer
 */
public class TipoExamenDAL extends DAL {
    
    public TipoExamenDAL(String domain){
        this.domain = domain;
        initObjectMapper();
    }
    
    public TipoExamen byId(int id) throws UnirestException{
        
        String url = getURI("tipoExa/readOneTipoExa/%d");
        HttpResponse<TipoExamen[]> response = Unirest.get(String.format(url, id)).asObject(TipoExamen[].class);
        TipoExamen[] tipos = response.getBody();
        
        
        return tipos[0];
    }
    
    public TipoExamen[] all() throws UnirestException{
        String url = getURI("tipoExa/getAllTipoExa/");
        HttpResponse<TipoExamen[]> response = Unirest.get(String.format(url)).asObject(TipoExamen[].class);
        TipoExamen[] tipos = response.getBody();
                
        return tipos;
    }
    
    public long create(TipoExamen tipo) throws UnirestException {
        String url = getURI("tipoExa/createTipoExaSP");
        HttpResponse<TipoExamen[]> result = Unirest.post(url)
        .header("accept", "application/json")
        .header("Content-Type", "application/json")
        .body(tipo).asObject(TipoExamen[].class);
        
        TipoExamen[] entities = result.getBody();
        if(entities.length > 0) return entities[0].getIdtipoexam();
        
        return 0;
    }
    
    public long update(TipoExamen tipo) throws UnirestException {
        String url = getURI("tipoExa/upTipoExa");
        HttpResponse<String> postResponse = Unirest.put(String.format(url))
        .header("accept", "application/json")
        .header("Content-Type", "application/json")
        .body(tipo)
        .asString();
        
        return tipo.getIdtipoexam();
    }
    
    public void delete(long id) throws UnirestException {
        String url = getURI("tipoExa/deleteTipoExa/%d/0");
        
        HttpResponse<String> postResponse = Unirest.put(String.format(url, id))
        .header("accept", "application/json")
        .header("Content-Type", "application/json")
        .body(String.class)
        .asString();
    }
}
