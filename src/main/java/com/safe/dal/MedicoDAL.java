/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.safe.dal;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.safe.entity.Medico;

/**
 *
 * @author developer
 */
public class MedicoDAL extends DAL {
    
    public MedicoDAL(String domain){
        this.domain = domain;
        initObjectMapper();
    }
    
    public Medico byId(int id) throws UnirestException {
        
        String url = getURI("medico/readOneMedico/%d");
        HttpResponse<Medico[]> response = Unirest.get(String.format(url, id)).asObject(Medico[].class);
        Medico[] medicos = response.getBody();
        
        
        return medicos[0];
    }
    
    public Medico[] all() throws UnirestException{
        String url = getURI("medico/getAllMedico/");
        HttpResponse<Medico[]> response = Unirest.get(String.format(url)).asObject(Medico[].class);
        Medico[] medicos = response.getBody();
                
        return medicos;
    }
    
    public long create(Medico medico) throws UnirestException {
        String url = getURI("medico/createMedicoSP");
        HttpResponse<String> result = Unirest.post(url)
        .header("accept", "application/json")
        .header("Content-Type", "application/json")
        .body(medico).asString();
        
        //TODO: debe retornar un ID de cliente
        return medico.getIdmedico();
    }
    
    public long update(Medico medico) throws UnirestException {
        String url = getURI("medico/upMedico");
        HttpResponse<String> postResponse = Unirest.put(String.format(url))
        .header("accept", "application/json")
        .header("Content-Type", "application/json")
        .body(medico)
        .asString();
        
        return medico.getIdmedico();
    }
    
    public void delete(long id) throws UnirestException {
        String url = getURI("medico/deleteMedico/%d/0");
        
        HttpResponse<String> postResponse = Unirest.put(String.format(url, id))
        .header("accept", "application/json")
        .header("Content-Type", "application/json")
        .body(String.class)
        .asString();
    }
}
