/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.safe.dal;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.safe.entity.List_Asis_Cap;

/**
 *
 * @author developer
 */
public class ListAsisCapDAL extends DAL {
    
    public ListAsisCapDAL(String domain){
        this.domain = domain;
        initObjectMapper();
    }
    
    public List_Asis_Cap byId(int id) throws UnirestException{
        
        String url = getURI("listAsisCap/readOneListAsisCap/%d");
        HttpResponse<List_Asis_Cap[]> response = Unirest.get(String.format(url, id)).asObject(List_Asis_Cap[].class);
        List_Asis_Cap[] listas = response.getBody();
        
        
        return listas[0];
    }
    
    public List_Asis_Cap[] all() throws UnirestException{
        String url = getURI("listAsisCap/getAllListAsisCap/");
        HttpResponse<List_Asis_Cap[]> response = Unirest.get(String.format(url)).asObject(List_Asis_Cap[].class);
        List_Asis_Cap[] listas = response.getBody();
                
        return listas;
    }
    
    public long create(List_Asis_Cap lista) throws UnirestException {
        String url = getURI("listAsisCap/createListAsisCap");
        HttpResponse<List_Asis_Cap[]> result = Unirest.post(url)
        .header("accept", "application/json")
        .header("Content-Type", "application/json")
        .body(lista).asObject(List_Asis_Cap[].class);
        
        List_Asis_Cap[] entities = result.getBody();
        if(entities.length > 0) return entities[0].getIdlistacap();
        
        return 0;
    }
    
    public long update(List_Asis_Cap lista) throws UnirestException {
        String url = getURI("listAsisCap/upListAsisCap");
        HttpResponse<String> postResponse = Unirest.put(String.format(url))
        .header("accept", "application/json")
        .header("Content-Type", "application/json")
        .body(lista)
        .asString();
        
        return lista.getIdlistacap();
    }
    
    public void delete(long id) throws UnirestException {
        String url = getURI("listaTrabajadoresCap/deleteListAsisCap/%d/0");
        
        HttpResponse<String> postResponse = Unirest.put(String.format(url, id))
        .header("accept", "application/json")
        .header("Content-Type", "application/json")
        .body(String.class)
        .asString();
    }
}
