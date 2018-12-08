/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.safe.dal;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.safe.dal.utils.DateFomatter;
import com.safe.entity.Sesion_Cap;

/**
 *
 * @author developer
 */
public class Sesion_CapDAL extends DAL {
    
    public Sesion_CapDAL(String domain){
        this.domain = domain;
        initObjectMapper();
    }
    
    public Sesion_Cap byId(int id) throws UnirestException {
        String url = getURI("sesionCap/readOneSesionCap/%d");
        HttpResponse<Sesion_Cap[]> response = Unirest.get(String.format(url, id)).asObject(Sesion_Cap[].class);
        Sesion_Cap[] sesiones = response.getBody();       
        normalizeFromDB(sesiones);
        return sesiones[0];
    }
    
    public Sesion_Cap[] all() throws UnirestException{
        String url = getURI("sesionCap/getAllSesionCap/");
        HttpResponse<Sesion_Cap[]> response = Unirest.get(String.format(url)).asObject(Sesion_Cap[].class);
        Sesion_Cap[] sesiones = response.getBody();
        normalizeFromDB(sesiones);
        return sesiones;
    }
    
    public long create(Sesion_Cap sesion) throws UnirestException {
        Sesion_Cap[] sesiones = {sesion};
        normalizeToDB(sesiones);
        
        String url = getURI("sesionCap/createSesionCapSP");
        HttpResponse<Sesion_Cap[]> result = Unirest.post(url)
        .header("accept", "application/json")
        .header("Content-Type", "application/json")
        .body(sesion).asObject(Sesion_Cap[].class);
        normalizeToApp(sesiones);
        Sesion_Cap[] entities = result.getBody();        
        if(entities.length > 0) return entities[0].getIdsesioncap();
        
        return 0;
    }
    
    public long update(Sesion_Cap sesion) throws UnirestException {
        Sesion_Cap[] sesiones = {sesion};
        normalizeToDB(sesiones);
        
        String url = getURI("sesionCap/upSesionCap");
        HttpResponse<String> postResponse = Unirest.put(String.format(url))
        .header("accept", "application/json")
        .header("Content-Type", "application/json")
        .body(sesion)
        .asString();
        
        return sesion.getIdsesioncap();
    }
    
    public void delete(long id) throws UnirestException {
        String url = getURI("sesionCap/deleteSesionCap/%d/0");
        
        HttpResponse<String> postResponse = Unirest.put(String.format(url, id))
        .header("accept", "application/json")
        .header("Content-Type", "application/json")
        .body(String.class)
        .asString();
    }
    
    private void normalizeFromDB(Sesion_Cap[] entities){
        for(Sesion_Cap e: entities){
            e.setFechasesion(DateFomatter.dateFromDB(e.getFechasesion()));
            e.setHorainiciocap(DateFomatter.hourFromDB(e.getHorainiciocap()));
            e.setHoraterminocap(DateFomatter.hourFromDB(e.getHoraterminocap()));
        }
    }
    
    private void normalizeToDB(Sesion_Cap[] entities){
        for(Sesion_Cap e: entities){            
            e.setHorainiciocap(DateFomatter.hourToDB(e.getHorainiciocap()));
            e.setHoraterminocap(DateFomatter.hourToDB(e.getHoraterminocap()));
        }
    }
    
    private void normalizeToApp(Sesion_Cap[] entities){
        for(Sesion_Cap e: entities){            
            e.setHorainiciocap(DateFomatter.hourToApp(e.getHorainiciocap()));
            e.setHoraterminocap(DateFomatter.hourToApp(e.getHoraterminocap()));
        }
    }
}
