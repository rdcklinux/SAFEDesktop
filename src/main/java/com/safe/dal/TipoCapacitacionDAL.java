/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.safe.dal;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.safe.entity.TipoCapacitacion;

/**
 *
 * @author developer
 */
public class TipoCapacitacionDAL extends DAL {
    
    public TipoCapacitacionDAL(String domain){
        this.domain = domain;
        initObjectMapper();
    }
    
    public TipoCapacitacion byId(int id) throws UnirestException{
        
        String url = getURI("tipoCap/readOneTipoCap/%d");
        HttpResponse<TipoCapacitacion[]> response = Unirest.get(String.format(url, id)).asObject(TipoCapacitacion[].class);
        TipoCapacitacion[] tipos = response.getBody();
        
        
        return tipos[0];
    }
    
    public TipoCapacitacion[] all() throws UnirestException{
        String url = getURI("tipoCap/getAllTipoCap/");
        HttpResponse<TipoCapacitacion[]> response = Unirest.get(String.format(url)).asObject(TipoCapacitacion[].class);
        TipoCapacitacion[] tipos = response.getBody();
                
        return tipos;
    }
    
    public long create(TipoCapacitacion tipo) throws UnirestException {
        String url = getURI("tipoCap/createTipoCapSP");
        HttpResponse<String> result = Unirest.post(url)
        .header("accept", "application/json")
        .header("Content-Type", "application/json")
        .body(tipo).asString();
        
        //TODO: debe retornar un ID de cliente
        return tipo.getIdtipocap();
    }
    
    public long update(TipoCapacitacion tipo) throws UnirestException {
        String url = getURI("tipoCap/upTipoCap");
        HttpResponse<String> postResponse = Unirest.put(String.format(url))
        .header("accept", "application/json")
        .header("Content-Type", "application/json")
        .body(tipo)
        .asString();
        
        return tipo.getIdtipocap();
    }
    
    public void delete(long id) throws UnirestException {
        String url = getURI("tipoCap/deleteTipoCap/%d/0");
        
        HttpResponse<String> postResponse = Unirest.put(String.format(url, id))
        .header("accept", "application/json")
        .header("Content-Type", "application/json")
        .body(String.class)
        .asString();
    }
}
