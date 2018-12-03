/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.safe.dal;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.safe.entity.List_Trab_Cap;

/**
 *
 * @author developer
 */
public class ListTrabCapDAL extends DAL {
    
    public ListTrabCapDAL(String domain){
        this.domain = domain;
        initObjectMapper();
    }
    
    public List_Trab_Cap byId(int id) throws UnirestException{
        
        String url = getURI("listaTrabajadoresCap/readOneListTrabCap/%d");
        HttpResponse<List_Trab_Cap[]> response = Unirest.get(String.format(url, id)).asObject(List_Trab_Cap[].class);
        List_Trab_Cap[] listas = response.getBody();
        
        
        return listas[0];
    }
    
    public List_Trab_Cap[] all() throws UnirestException{
        String url = getURI("listaTrabajadoresCap/getAllListTrabCap/");
        HttpResponse<List_Trab_Cap[]> response = Unirest.get(String.format(url)).asObject(List_Trab_Cap[].class);
        List_Trab_Cap[] listas = response.getBody();
                
        return listas;
    }
    
    public long create(List_Trab_Cap lista) throws UnirestException {
        String url = getURI("listaTrabajadoresCap/createListTrabCapSP");
        HttpResponse<List_Trab_Cap[]> result = Unirest.post(url)
        .header("accept", "application/json")
        .header("Content-Type", "application/json")
        .body(lista).asObject(List_Trab_Cap[].class);
        
        List_Trab_Cap[] entities = result.getBody();
        if(entities.length > 0) return entities[0].getIdlistrabcap();
        
        return 0;
    }
    
    public long update(List_Trab_Cap lista) throws UnirestException {
        String url = getURI("listaTrabajadoresCap/upListTrabCap");
        HttpResponse<String> postResponse = Unirest.put(String.format(url))
        .header("accept", "application/json")
        .header("Content-Type", "application/json")
        .body(lista)
        .asString();
        
        return lista.getIdlistrabcap();
    }
    
    public void delete(long id) throws UnirestException {
        String url = getURI("listaTrabajadoresCap/deleteListTrabCap/%d/0");
        
        HttpResponse<String> postResponse = Unirest.put(String.format(url, id))
        .header("accept", "application/json")
        .header("Content-Type", "application/json")
        .body(String.class)
        .asString();
    }
}
