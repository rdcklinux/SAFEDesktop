/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.safe.dal;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.safe.entity.SoliEvalTer;
import java.text.ParseException;


public class SolicitudDAL extends DAL {
    
    public SolicitudDAL(String domain){
        this.domain = domain;
    }
    
    public SoliEvalTer byId(int id) throws UnirestException{
        
        String url = getURI("SolicitudEvalTerreno/readOneSoliEvalTer/%d");
        HttpResponse<SoliEvalTer[]> response = Unirest.get(String.format(url, id)).asObject(SoliEvalTer[].class);
        SoliEvalTer[] solicitudes = response.getBody();
        
        return solicitudes[0];
    }
    
    public SoliEvalTer[] getAllEvaluaciones() throws UnirestException, ParseException {
        String url = getURI("SolicitudEvalTerreno/getAllSoliEvalTer/");
        HttpResponse<SoliEvalTer[]> response = Unirest.get(url).asObject(SoliEvalTer[].class);
        SoliEvalTer[] solicitudes = response.getBody();
        
        return solicitudes;
    }
    
    public long create(SoliEvalTer solicitud) throws UnirestException {
        String url = getURI("SolicitudEvalTerreno/createSoliEvalTerSP");
        HttpResponse<String> result = Unirest.post(url)
        .header("accept", "application/json")
        .header("Content-Type", "application/json")
        .body(solicitud).asString();
        
        return solicitud.getIdsolicitud();
    }
    
    public long update(SoliEvalTer solicitud) throws UnirestException {
        String url = getURI("SolicitudEvalTerreno/upSoliEvalTer");
        HttpResponse<String> postResponse = Unirest.put(String.format(url))
        .header("accept", "application/json")
        .header("Content-Type", "application/json")
        .body(solicitud)
        .asString();
        
        return solicitud.getIdsolicitud();
    }
    
    
}
