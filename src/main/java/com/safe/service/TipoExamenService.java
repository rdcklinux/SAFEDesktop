/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.safe.service;

import com.mashape.unirest.http.exceptions.UnirestException;
import com.safe.dal.TipoExamenDAL;
import com.safe.entity.TipoExamen;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author developer
 */
public class TipoExamenService {
    
    private final TipoExamenDAL tipoExamenDAL;
    
    public static String[] ESTADOS = {
        "Todos",
        "Activo",
        "Desactivo",
    };
    
    public TipoExamenService(String domain){
        tipoExamenDAL = new TipoExamenDAL(domain);
    }
    
    public TipoExamen getOne(int id){
        TipoExamen tipo = null;
        try {
            tipo = tipoExamenDAL.byId(id);
        } catch (UnirestException ex) {
            Logger.getLogger(TipoExamenService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return tipo;
    }
    
    public TipoExamen[] getCollection(){
        TipoExamen[] tipos = {};
        try {
            tipos = tipoExamenDAL.all();
        } catch (UnirestException ex) {
            Logger.getLogger(TipoExamenService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return tipos;
    }
    
    public long save(TipoExamen tipo) {
        long id = 0;
        if(tipo.getIdtipoexam()> 0){
            try {
                id = tipoExamenDAL.update(tipo);
            } catch (UnirestException ex) {
                Logger.getLogger(TipoExamenService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else {
            try {
                id = tipoExamenDAL.create(tipo);
            } catch (UnirestException ex) {
                Logger.getLogger(TipoExamenService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return id;
    }
    
    public void delete(long id) {
        try {
            tipoExamenDAL.delete(id);
        } catch (UnirestException ex) {
            Logger.getLogger(TipoExamenService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
