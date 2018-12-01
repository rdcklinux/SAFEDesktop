/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.safe.service;

import com.mashape.unirest.http.exceptions.UnirestException;
import com.safe.dal.Obs_IngenieroDAL;
import com.safe.dal.Obs_SupervisorDAL;
import com.safe.entity.Obs_Ingeniero;
import com.safe.entity.Obs_Supervisor;
import com.safe.entity.SoliEvalTer;
import com.safe.entity.Usuario;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author roderick
 */
public class ObservacionService {
    private final Obs_IngenieroDAL obsIngenieroDAL;
    private final Obs_SupervisorDAL obsSupervisorDAL;
    
    public ObservacionService(String domain){
        obsIngenieroDAL = new Obs_IngenieroDAL(domain);
        obsSupervisorDAL = new Obs_SupervisorDAL(domain);
    }
    
    public Obs_Supervisor getOneSupervisor(int id){
        Obs_Supervisor entity = null;
        try {
            entity = obsSupervisorDAL.byId(id);
        } catch (UnirestException ex) {
            Logger.getLogger(ClienteService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return entity;
    }
    
    public Obs_Ingeniero getOneIngeniero(int id){
        Obs_Ingeniero entity = null;
        try {
            entity = obsIngenieroDAL.byId(id);
        } catch (UnirestException ex) {
            Logger.getLogger(ClienteService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return entity;
    }
    
    public Obs_Supervisor[] getSupervisorCollection() {
        Obs_Supervisor[] entities = {};
        try {
            entities = obsSupervisorDAL.all();
        } catch (UnirestException ex) {
            Logger.getLogger(ObservacionService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return entities;
    }
    
    public Obs_Ingeniero[] getIngenieroCollection() {
        Obs_Ingeniero[] entities = {};
        try {
            entities = obsIngenieroDAL.all();
        } catch (UnirestException ex) {
            Logger.getLogger(ObservacionService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return entities;
    }
    
    public long saveSupervisor(Obs_Supervisor entity) {
        long id = 0;
        if(entity.getIdobssupervisor()> 0){
            try {
                id = obsSupervisorDAL.update(entity);
            } catch (UnirestException ex) {
                Logger.getLogger(ClienteService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else {
            try {
                id = obsSupervisorDAL.create(entity);
            } catch (UnirestException ex) {
                Logger.getLogger(ClienteService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return id;
    }
    
    public long saveIngeniero(Obs_Ingeniero entity) {
        long id = 0;
        if(entity.getIdobsingeniero()> 0){
            try {
                id = obsIngenieroDAL.update(entity);
            } catch (UnirestException ex) {
                Logger.getLogger(ClienteService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else {
            try {
                id = obsIngenieroDAL.create(entity);
            } catch (UnirestException ex) {
                Logger.getLogger(ClienteService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return id;
    }
    
    public void deleteSupervisor(long id) {
        try {
            obsSupervisorDAL.delete(id);
        } catch (UnirestException ex) {
            Logger.getLogger(ClienteService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void deleteIngeniero(long id) {
        try {
            obsIngenieroDAL.delete(id);
        } catch (UnirestException ex) {
            Logger.getLogger(ClienteService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
