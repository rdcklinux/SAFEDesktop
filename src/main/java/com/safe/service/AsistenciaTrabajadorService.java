/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.safe.service;

import com.mashape.unirest.http.exceptions.UnirestException;
import com.safe.dal.ListAsisCapDAL;
import com.safe.dal.ListAsisSaludDAL;
import com.safe.entity.List_Asis_Cap;
import com.safe.entity.List_Asis_Salud;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author roderick
 */
public class AsistenciaTrabajadorService {
    private final ListAsisCapDAL listAsisCapDAL;
    private final ListAsisSaludDAL listAsisSaludDAL;
    
    public AsistenciaTrabajadorService(String domain){
        listAsisCapDAL = new ListAsisCapDAL(domain);
        listAsisSaludDAL = new ListAsisSaludDAL(domain);
    }
    
    public List_Asis_Cap getOneListCap(int id){
        List_Asis_Cap entity = null;
        try {
            entity = listAsisCapDAL.byId(id);
        } catch (UnirestException ex) {
            Logger.getLogger(ClienteService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return entity;
    }
    
    public List_Asis_Salud getOneListSalud(int id){
        List_Asis_Salud entity = null;
        try {
            entity = listAsisSaludDAL.byId(id);
        } catch (UnirestException ex) {
            Logger.getLogger(ClienteService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return entity;
    }
    
    public List_Asis_Cap[] getListCapCollection() {
        List_Asis_Cap[] entities = {};
        try {
            entities = listAsisCapDAL.all();
        } catch (UnirestException ex) {
            Logger.getLogger(AsistenciaTrabajadorService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return entities;
    }
    
    public List_Asis_Salud[] getListSaludCollection() {
        List_Asis_Salud[] entities = {};
        try {
            entities = listAsisSaludDAL.all();
        } catch (UnirestException ex) {
            Logger.getLogger(AsistenciaTrabajadorService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return entities;
    }
    
    public long saveListCap(List_Asis_Cap entity) {
        long id = 0;
        if(entity.getIdlistacap()> 0) {
            try {
                id = listAsisCapDAL.update(entity);
            } catch (UnirestException ex) {
                Logger.getLogger(ClienteService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else {
            try {
                id = listAsisCapDAL.create(entity);
            } catch (UnirestException ex) {
                Logger.getLogger(ClienteService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return id;
    }
    
    public long saveListSalud(List_Asis_Salud entity) {
        long id = 0;
        if(entity.getIdlistsalud()> 0){
            try {
                id = listAsisSaludDAL.update(entity);
            } catch (UnirestException ex) {
                Logger.getLogger(ClienteService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else {
            try {
                id = listAsisSaludDAL.create(entity);
            } catch (UnirestException ex) {
                Logger.getLogger(ClienteService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return id;
    }
    
    public void deleteListCap(long id) {
        try {
            listAsisCapDAL.delete(id);
        } catch (UnirestException ex) {
            Logger.getLogger(ClienteService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void deleteListSalud(long id) {
        try {
            listAsisSaludDAL.delete(id);
        } catch (UnirestException ex) {
            Logger.getLogger(ClienteService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
