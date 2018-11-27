/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.safe.service;

import com.mashape.unirest.http.exceptions.UnirestException;
import com.safe.dal.CapacitacionDAL;
import com.safe.dal.TipoCapacitacionDAL;
import com.safe.entity.TipoCapacitacion;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author developer
 */
public class TipoCapacitacionService {
    
    private final TipoCapacitacionDAL tipoCapacitacionDAL;
    
    public static String[] ESTADOS = {
        "Todos",
        "Activo",
        "Desactivo",
    };
    
    public TipoCapacitacionService(String domain){
        tipoCapacitacionDAL = new TipoCapacitacionDAL(domain);
    }
    
    public TipoCapacitacion getOne(int id){
        TipoCapacitacion tipo = null;
        try {
            tipo = tipoCapacitacionDAL.byId(id);
        } catch (UnirestException ex) {
            Logger.getLogger(TipoCapacitacionService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return tipo;
    }
    
    public TipoCapacitacion[] getCollection(){
        TipoCapacitacion[] tipos = null;
        try {
            tipos = tipoCapacitacionDAL.all();
        } catch (UnirestException ex) {
            Logger.getLogger(TipoCapacitacionService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return tipos;
    }
    
    public long save(TipoCapacitacion tipo) {
        long id = 0;
        if(tipo.getIdtipocap() > 0){
            try {
                id = tipoCapacitacionDAL.update(tipo);
            } catch (UnirestException ex) {
                Logger.getLogger(TipoCapacitacionService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else {
            try {
                id = tipoCapacitacionDAL.create(tipo);
            } catch (UnirestException ex) {
                Logger.getLogger(TipoCapacitacionService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return id;
    }
    
    public void delete(long id) {
        try {
            tipoCapacitacionDAL.delete(id);
        } catch (UnirestException ex) {
            Logger.getLogger(TipoCapacitacionService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
