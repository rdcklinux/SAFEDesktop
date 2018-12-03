/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.safe.dal;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.safe.entity.ListTrabSalud;

/**
 *
 * @author developer
 */
public class ListTrabSaludDAL extends DAL {
    
    public ListTrabSaludDAL(String domain) {
        this.domain = domain;
        initObjectMapper();
    }
    
    public ListTrabSalud byId(int id) throws UnirestException{
        
        String url = getURI("listaTrabajadoresSalud/readOneListTrabSaludById/%d");
        HttpResponse<ListTrabSalud[]> response = Unirest.get(String.format(url, id)).asObject(ListTrabSalud[].class);
        ListTrabSalud[] listas = response.getBody();
        
        return listas[0];
    }
    
    public ListTrabSalud[] all() throws UnirestException{
        String url = getURI("listaTrabajadoresSalud/getAllListTrabSalud/");
        HttpResponse<ListTrabSalud[]> response = Unirest.get(String.format(url)).asObject(ListTrabSalud[].class);
        ListTrabSalud[] listas = response.getBody();
                
        return listas;
    }
    
    public long create(ListTrabSalud lista) throws UnirestException {
        String url = getURI("listaTrabajadoresSalud/createListTrabSaludSP");
        HttpResponse<ListTrabSalud[]> result = Unirest.post(url)
        .header("accept", "application/json")
        .header("Content-Type", "application/json")
        .body(lista).asObject(ListTrabSalud[].class);
        
        ListTrabSalud[] entities = result.getBody();
        if(entities.length > 0) return entities[0].getIdlistrabsalud();
        
        return 0;
    }
    
    public long update(ListTrabSalud lista) throws UnirestException {
        String url = getURI("listaTrabajadoresSalud/upListTrabSalud");
        HttpResponse<String> postResponse = Unirest.put(String.format(url))
        .header("accept", "application/json")
        .header("Content-Type", "application/json")
        .body(lista)
        .asString();
        
        return lista.getIdlistrabsalud();
    }
    
    public void delete(long id) throws UnirestException {
        String url = getURI("listaTrabajadoresSalud/deleteListTrabSalud/%d/0");
        
        HttpResponse<String> postResponse = Unirest.put(String.format(url, id))
        .header("accept", "application/json")
        .header("Content-Type", "application/json")
        .body(String.class)
        .asString();
    }
}
