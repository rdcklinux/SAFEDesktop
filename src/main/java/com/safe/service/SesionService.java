/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.safe.service;

import com.mashape.unirest.http.exceptions.UnirestException;
import com.safe.dal.Sesion_CapDAL;
import com.safe.dal.Sesion_SaludDAL;
import com.safe.entity.Sesion_Cap;
import com.safe.entity.Sesion_Salud;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author roderick
 */
public class SesionService {
    private final Sesion_SaludDAL sesionSaludDAL;
    private final Sesion_CapDAL sesionCapacitacionDAL;
    
    public SesionService(String domain){
        sesionSaludDAL = new Sesion_SaludDAL(domain);
        sesionCapacitacionDAL = new Sesion_CapDAL(domain);
    }
    
    public Sesion_Cap getOneCapacitacion(int id){
        Sesion_Cap entity = null;
        try {
            entity = sesionCapacitacionDAL.byId(id);
        } catch (UnirestException ex) {
            Logger.getLogger(ClienteService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return entity;
    }
    
    public Sesion_Salud getOneSalud(int id){
        Sesion_Salud entity = null;
        try {
            entity = sesionSaludDAL.byId(id);
        } catch (UnirestException ex) {
            Logger.getLogger(ClienteService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return entity;
    }
    
    public Sesion_Cap[] getCapacitacionCollection() {
        Sesion_Cap[] entities = {};
        try {
            entities = sesionCapacitacionDAL.all();
        } catch (UnirestException ex) {
            Logger.getLogger(SesionService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return entities;
    }
    
    public Sesion_Salud[] getSaludCollection() {
        Sesion_Salud[] entities = {};
        try {
            entities = sesionSaludDAL.all();
        } catch (UnirestException ex) {
            Logger.getLogger(SesionService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return entities;
    }
    
    public long saveCapacitacion(Sesion_Cap entity) {
        long id = 0;
        if(entity.getIdsesioncap()> 0){
            try {
                id = sesionCapacitacionDAL.update(entity);
            } catch (UnirestException ex) {
                Logger.getLogger(ClienteService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else {
            try {
                id = sesionCapacitacionDAL.create(entity);
            } catch (UnirestException ex) {
                Logger.getLogger(ClienteService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return id;
    }
    
    public long saveSalud(Sesion_Salud entity) {
        long id = 0;
        if(entity.getIdsesionsalud()> 0){
            try {
                id = sesionSaludDAL.update(entity);
            } catch (UnirestException ex) {
                Logger.getLogger(ClienteService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else {
            try {
                id = sesionSaludDAL.create(entity);
            } catch (UnirestException ex) {
                Logger.getLogger(ClienteService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return id;
    }
    
    public void deleteCapacitacion(long id) {
        try {
            sesionCapacitacionDAL.delete(id);
        } catch (UnirestException ex) {
            Logger.getLogger(ClienteService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void deleteSalud(long id) {
        try {
            sesionSaludDAL.delete(id);
        } catch (UnirestException ex) {
            Logger.getLogger(ClienteService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
