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
    
    public SoliEvalTer[] getAllEvaluaciones() throws UnirestException, ParseException {
        String url = getURI("SolicitudEvalTerreno/getAllSoliEvalTer/");
        HttpResponse<SoliEvalTer[]> response = Unirest.get(url).asObject(SoliEvalTer[].class);
        SoliEvalTer[] solicitudes = response.getBody();
        return solicitudes;
    }
}
