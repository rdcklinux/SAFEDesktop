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
import com.safe.entity.Usuario;
import java.text.ParseException;
import java.text.SimpleDateFormat;


public class SolicitudDAL extends DAL {
    
    public SolicitudDAL(String domain){
        this.domain = domain;
    }
    
    public SoliEvalTer[] getAllEvaluaciones() throws UnirestException, ParseException {
        String url = getURI("SolicitudEvalTerreno/all");
        HttpResponse<SoliEvalTer[]> response = Unirest.get(url).asObject(SoliEvalTer[].class);
        SoliEvalTer[] solicitudes = response.getBody();
        return solicitudes;
    }
}
