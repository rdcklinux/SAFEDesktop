/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.safe.service;

import com.mashape.unirest.http.exceptions.UnirestException;
import com.safe.dal.ExpositorDAL;
import com.safe.entity.Expositor;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author developer
 */
public class ExpositorService {
    
    private ExpositorDAL expositorDAL;
    
    public ExpositorService(String domain){
        expositorDAL = new ExpositorDAL(domain);
    }
    
    public Expositor getOne(int id){
        Expositor expositor = null;
        try {
            expositor = expositorDAL.byId(id);
        } catch (UnirestException ex) {
            Logger.getLogger(ExpositorService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return expositor;
    }
    
    public Expositor[] getCollection(){
        Expositor[] expositores = null;
        try {
            expositores = expositorDAL.all();
        } catch (UnirestException ex) {
            Logger.getLogger(ExpositorService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return expositores;
    }
    
    public long save(Expositor expositor) {
        long id = 0;
        if(expositor.getIdexpositor()> 0){
            try {
                id = expositorDAL.update(expositor);
            } catch (UnirestException ex) {
                Logger.getLogger(ExpositorService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else {
            try {
                id = expositorDAL.create(expositor);
            } catch (UnirestException ex) {
                Logger.getLogger(ExpositorService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return id;
    }
    
    public void delete(long id) {
        try {
            expositorDAL.delete(id);
        } catch (UnirestException ex) {
            Logger.getLogger(ExpositorService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
