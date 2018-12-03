/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.safe.dal;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.safe.entity.List_Asis_Salud;

/**
 *
 * @author developer
 */
public class ListAsisSaludDAL extends DAL {
    
    public ListAsisSaludDAL(String domain){
        this.domain = domain;
        initObjectMapper();
    }
    
    public List_Asis_Salud byId(int id) throws UnirestException{
        
        String url = getURI("listAsisSalud/readOneListAsisSalud/%d");
        HttpResponse<List_Asis_Salud[]> response = Unirest.get(String.format(url, id)).asObject(List_Asis_Salud[].class);
        List_Asis_Salud[] listas = response.getBody();
        
        
        return listas[0];
    }
    
    public List_Asis_Salud[] all() throws UnirestException{
        String url = getURI("listAsisSalud/getAllListAsisSalud/");
        HttpResponse<List_Asis_Salud[]> response = Unirest.get(String.format(url)).asObject(List_Asis_Salud[].class);
        List_Asis_Salud[] listas = response.getBody();
                
        return listas;
    }
    
    public long create(List_Asis_Salud lista) throws UnirestException {
        String url = getURI("listAsisSalud/createListAsisSalud");
        HttpResponse<String> result = Unirest.post(url)
        .header("accept", "application/json")
        .header("Content-Type", "application/json")
        .body(lista).asString();
        
        //TODO: debe retornar un ID de cliente
        return lista.getIdlistsalud();
    }
    
    public long update(List_Asis_Salud lista) throws UnirestException {
        String url = getURI("listAsisSalud/upListAsisSalud");
        HttpResponse<String> postResponse = Unirest.put(String.format(url))
        .header("accept", "application/json")
        .header("Content-Type", "application/json")
        .body(lista)
        .asString();
        
        return lista.getIdlistsalud();
    }
    
    public void delete(long id) throws UnirestException {
        String url = getURI("listaTrabajadoresCap/deleteListAsisSalud/%d/0");
        
        HttpResponse<String> postResponse = Unirest.put(String.format(url, id))
        .header("accept", "application/json")
        .header("Content-Type", "application/json")
        .body(String.class)
        .asString();
    }
}
