/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.safe.service;

import com.mashape.unirest.http.exceptions.UnirestException;
import com.safe.dal.PlanSaludDAL;
import com.safe.entity.Plan_Salud;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author developer
 */
public class PlanSaludService {
    
    private final PlanSaludDAL planSaludDAL;
    
    public static String[] ESTADOS = {
        "Desactivado",
        "Activo",
    };
    
    public PlanSaludService(String domain){
        planSaludDAL = new PlanSaludDAL(domain);
    }
    
    public Plan_Salud getOne(int id){
        Plan_Salud plan = null;
        try {
            plan = planSaludDAL.byId(id);
        } catch (UnirestException ex) {
            Logger.getLogger(PlanSaludService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return plan;
    }
    
    public Plan_Salud[] getCollection(){
        Plan_Salud[] planes = null;
        try {
            planes = planSaludDAL.all();
        } catch (UnirestException ex) {
            Logger.getLogger(PlanSaludService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return planes;
    }
    
    public long save(Plan_Salud plan) {
        long id = 0;
        if(plan.getIdplansalud()> 0){
            try {
                id = planSaludDAL.update(plan);
            } catch (UnirestException ex) {
                Logger.getLogger(PlanSaludService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else {
            try {
                id = planSaludDAL.create(plan);
            } catch (UnirestException ex) {
                Logger.getLogger(PlanSaludService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return id;
    }
    
    public void delete(long id) {
        try {
            planSaludDAL.delete(id);
        } catch (UnirestException ex) {
            Logger.getLogger(PlanSaludService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
