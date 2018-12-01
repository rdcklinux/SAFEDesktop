/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.safe.service;

import com.mashape.unirest.http.exceptions.UnirestException;
import com.safe.dal.EvaluacionTerrenoDAL;
import com.safe.entity.Eval_Terr;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author roderick
 */
public class EvaluacionTerrenoService {
    
    private EvaluacionTerrenoDAL evaluacionTerrenoDAL;
    
    public EvaluacionTerrenoService(String domain){
        evaluacionTerrenoDAL = new EvaluacionTerrenoDAL(domain);
    }
    
    public Eval_Terr getOne(int id){
        Eval_Terr evaluacion = null;
        try {
            evaluacion = evaluacionTerrenoDAL.byId(id);
        } catch (UnirestException ex) {
            Logger.getLogger(ClienteService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return evaluacion;
    }
    
    public Eval_Terr[] getCollection(){
        Eval_Terr[] evaluaciones = null;
        try {
            evaluaciones = evaluacionTerrenoDAL.all();
        } catch (UnirestException ex) {
            Logger.getLogger(ClienteService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return evaluaciones;
    }
    
    public long save(Eval_Terr evaluacion) {
        long id = 0;
        if(evaluacion.getIdevalterr()> 0){
            try {
                id = evaluacionTerrenoDAL.update(evaluacion);
            } catch (UnirestException ex) {
                Logger.getLogger(ClienteService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else {
            try {
                id = evaluacionTerrenoDAL.create(evaluacion);
            } catch (UnirestException ex) {
                Logger.getLogger(ClienteService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return id;
    }
    
    public void delete(long id) {
        try {
            evaluacionTerrenoDAL.delete(id);
        } catch (UnirestException ex) {
            Logger.getLogger(ClienteService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
