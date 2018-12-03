/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.safe.service;

import com.mashape.unirest.http.exceptions.UnirestException;
import com.safe.dal.ListTrabCapDAL;
import com.safe.dal.ListTrabSaludDAL;
import com.safe.entity.List_Trab_Cap;
import com.safe.entity.ListTrabSalud;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author roderick
 */
public class ListTrabajadorService {
    private final ListTrabCapDAL listTrabCapDAL;
    private final ListTrabSaludDAL listTrabSaludDAL;
    
    public ListTrabajadorService(String domain){
        listTrabCapDAL = new ListTrabCapDAL(domain);
        listTrabSaludDAL = new ListTrabSaludDAL(domain);
    }
    
    public List_Trab_Cap getOneListCap(int id){
        List_Trab_Cap entity = null;
        try {
            entity = listTrabCapDAL.byId(id);
        } catch (UnirestException ex) {
            Logger.getLogger(ClienteService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return entity;
    }
    
    public ListTrabSalud getOneListSalud(int id){
        ListTrabSalud entity = null;
        try {
            entity = listTrabSaludDAL.byId(id);
        } catch (UnirestException ex) {
            Logger.getLogger(ClienteService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return entity;
    }
    
    public List_Trab_Cap[] getListCapCollection() {
        List_Trab_Cap[] entities = {};
        try {
            entities = listTrabCapDAL.all();
        } catch (UnirestException ex) {
            Logger.getLogger(ListTrabajadorService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return entities;
    }
    
    public ListTrabSalud[] getListSaludCollection() {
        ListTrabSalud[] entities = {};
        try {
            entities = listTrabSaludDAL.all();
        } catch (UnirestException ex) {
            Logger.getLogger(ListTrabajadorService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return entities;
    }
    
    public long saveListCap(List_Trab_Cap entity) {
        long id = 0;
        if(entity.getIdlistrabcap() > 0) {
            try {
                id = listTrabCapDAL.update(entity);
            } catch (UnirestException ex) {
                Logger.getLogger(ClienteService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else {
            try {
                id = listTrabCapDAL.create(entity);
            } catch (UnirestException ex) {
                Logger.getLogger(ClienteService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return id;
    }
    
    public long saveListSalud(ListTrabSalud entity) {
        long id = 0;
        if(entity.getIdlistrabsalud()> 0){
            try {
                id = listTrabSaludDAL.update(entity);
            } catch (UnirestException ex) {
                Logger.getLogger(ClienteService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else {
            try {
                id = listTrabSaludDAL.create(entity);
            } catch (UnirestException ex) {
                Logger.getLogger(ClienteService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return id;
    }
    
    public void deleteListCap(long id) {
        try {
            listTrabCapDAL.delete(id);
        } catch (UnirestException ex) {
            Logger.getLogger(ClienteService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void deleteListSalud(long id) {
        try {
            listTrabSaludDAL.delete(id);
        } catch (UnirestException ex) {
            Logger.getLogger(ClienteService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
