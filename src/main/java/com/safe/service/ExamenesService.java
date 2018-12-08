/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.safe.service;

import com.mashape.unirest.http.exceptions.UnirestException;
import com.safe.dal.ExamenesDAL;
import com.safe.entity.Examenes;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author developer
 */
public class ExamenesService {
    
    private final ExamenesDAL examenesDAL;
    
    public static String[] ESTADOS = {
        "Todos",
        "Activo",
        "Desactivo",
    };
    
    public ExamenesService(String domain){
        examenesDAL = new ExamenesDAL(domain);
    }
    
    public Examenes getOne(int id){
        Examenes examen = null;
        try {
            examen = examenesDAL.byId(id);
        } catch (UnirestException ex) {
            Logger.getLogger(ExamenesService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return examen;
    }
    
    public Examenes[] getCollection(){
        Examenes[] examenes = null;
        try {
            examenes = examenesDAL.all();
        } catch (UnirestException ex) {
            Logger.getLogger(ExamenesService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return examenes;
    }
    
    public long save(Examenes examen) {
        long id = 0;
        if(examen.getIdexamen()> 0){
            try {
                id = examenesDAL.update(examen);
            } catch (UnirestException ex) {
                Logger.getLogger(ExamenesService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else {
            try {
                id = examenesDAL.create(examen);
            } catch (UnirestException ex) {
                Logger.getLogger(ExamenesService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return id;
    }
    
    public void delete(long id) {
        try {
            examenesDAL.delete(id);
        } catch (UnirestException ex) {
            Logger.getLogger(ExamenesService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
