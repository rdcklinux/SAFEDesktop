/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.safe.service;

import com.mashape.unirest.http.exceptions.UnirestException;
import com.safe.dal.CapacitacionDAL;
import com.safe.entity.Capacitacion;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author developer
 */
public class CapacitacionService {
    
    private final CapacitacionDAL capacitacionDAL;
    
    public static String[] ESTADOS = {
        "Todos",
        "Activo",
        "Desactivo",
    };
    
    public CapacitacionService(String domain){
        capacitacionDAL = new CapacitacionDAL(domain);
    }
    
    public Capacitacion getOne(int id){
        Capacitacion capacitacion = null;
        try {
            capacitacion = capacitacionDAL.byId(id);
        } catch (UnirestException ex) {
            Logger.getLogger(CapacitacionService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return capacitacion;
    }
    
    public Capacitacion[] getCollection(){
        Capacitacion[] capacitaciones = null;
        try {
            capacitaciones = capacitacionDAL.all();
        } catch (UnirestException ex) {
            Logger.getLogger(CapacitacionService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return capacitaciones;
    }
    
    public long save(Capacitacion capacitacion) {
        long id = 0;
        if(capacitacion.getIdcap()> 0){
            try {
                id = capacitacionDAL.update(capacitacion);
            } catch (UnirestException ex) {
                Logger.getLogger(CapacitacionService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else {
            try {
                id = capacitacionDAL.create(capacitacion);
            } catch (UnirestException ex) {
                Logger.getLogger(CapacitacionService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return id;
    }
    
    public void delete(long id) {
        try {
            capacitacionDAL.delete(id);
        } catch (UnirestException ex) {
            Logger.getLogger(CapacitacionService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
