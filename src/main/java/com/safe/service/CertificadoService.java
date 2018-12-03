/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.safe.service;

import com.mashape.unirest.http.exceptions.UnirestException;
import com.safe.dal.CertificadoDAL;
import com.safe.entity.Certificado;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author developer
 */
public class CertificadoService {
    
    private final CertificadoDAL certificadoDAL;
    
    public static String[] ESTADOS = {
        "Todos",
        "Activo",
        "Desactivo",
    };
    
    public CertificadoService(String domain){
        certificadoDAL = new CertificadoDAL(domain);
    }
    
    public Certificado getOne(int id){
        Certificado cert = null;
        try {
            cert = certificadoDAL.byId(id);
        } catch (UnirestException ex) {
            Logger.getLogger(CertificadoService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return cert;
    }
    
    public Certificado[] getCollection(){
        Certificado[] certs = null;
        try {
            certs = certificadoDAL.all();
        } catch (UnirestException ex) {
            Logger.getLogger(CertificadoService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return certs;
    }
    
    public long save(Certificado cert) {
        long id = 0;
        if(cert.getIdcertificado()> 0){
            try {
                id = certificadoDAL.update(cert);
            } catch (UnirestException ex) {
                Logger.getLogger(CertificadoService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else {
            try {
                id = certificadoDAL.create(cert);
            } catch (UnirestException ex) {
                Logger.getLogger(CertificadoService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return id;
    }
    
    public void delete(long id) {
        try {
            certificadoDAL.delete(id);
        } catch (UnirestException ex) {
            Logger.getLogger(CertificadoService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
