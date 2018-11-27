/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.safe.service;

import com.mashape.unirest.http.exceptions.UnirestException;
import com.safe.dal.PlanCapacitacionDAL;
import com.safe.entity.PlanCapacitacion;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author developer
 */
public class PlanCapacitacionService {
    
    private final PlanCapacitacionDAL planCapacitacionDAL;
    
    public PlanCapacitacionService(String domain){
        planCapacitacionDAL = new PlanCapacitacionDAL(domain);
    }
    
    public PlanCapacitacion getOne(int id){
        PlanCapacitacion plan = null;
        try {
            plan = planCapacitacionDAL.byId(id);
        } catch (UnirestException ex) {
            Logger.getLogger(PlanCapacitacionService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return plan;
    }
    
    public PlanCapacitacion[] getCollection(){
        PlanCapacitacion[] planes = null;
        try {
            planes = planCapacitacionDAL.all();
        } catch (UnirestException ex) {
            Logger.getLogger(PlanCapacitacionService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return planes;
    }
    
    public PlanCapacitacion save(PlanCapacitacion plan) {
        if(plan.getIdplancap() > 0){
            try {
                planCapacitacionDAL.update(plan);
            } catch (UnirestException ex) {
                Logger.getLogger(PlanCapacitacionService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else {
            try {
                long id = planCapacitacionDAL.create(plan);
                plan.setIdplancap(id);
            } catch (UnirestException ex) {
                Logger.getLogger(PlanCapacitacionService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return plan;
    }
    
    public void delete(long id) {
        try {
            planCapacitacionDAL.delete(id);
        } catch (UnirestException ex) {
            Logger.getLogger(PlanCapacitacionService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
