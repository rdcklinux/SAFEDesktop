/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.safe.service;

import com.mashape.unirest.http.exceptions.UnirestException;
import com.safe.dal.MedicoDAL;
import com.safe.entity.Medico;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author developer
 */
public class MedicoService {
    
    private MedicoDAL medicoDAL;
    
    public MedicoService(String domain){
        medicoDAL = new MedicoDAL(domain);
    }
    
    public Medico getOne(int id){
        Medico medico = null;
        try {
            medico = medicoDAL.byId(id);
        } catch (UnirestException ex) {
            Logger.getLogger(MedicoService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return medico;
    }
    
    public Medico[] getCollection(){
        Medico[] medicos = null;
        try {
            medicos = medicoDAL.all();
        } catch (UnirestException ex) {
            Logger.getLogger(MedicoService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return medicos;
    }
    
    public long save(Medico medico) {
        long id = 0;
        if(medico.getIdmedico()> 0){
            try {
                id = medicoDAL.update(medico);
            } catch (UnirestException ex) {
                Logger.getLogger(MedicoService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else {
            try {
                id = medicoDAL.create(medico);
            } catch (UnirestException ex) {
                Logger.getLogger(MedicoService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return id;
    }
    
    public void delete(long id) {
        try {
            medicoDAL.delete(id);
        } catch (UnirestException ex) {
            Logger.getLogger(MedicoService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
