/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.safe.service;

import com.mashape.unirest.http.exceptions.UnirestException;
import com.safe.dal.SolicitudDAL;
import com.safe.entity.SoliEvalTer;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author familia
 */
public class SolicitudService {
    private final SolicitudDAL solicitudDAL;
    
    public static String[] TIPOS = {
        "Todos",
        "Instalación",
        "Persona",
    };
    
    public static String[] ESTADOS = {
        "Todos",
        "Creada",
        "Pendiente Revisión Supervisor",
        "Pendiente Revisión Ingeniero",
        "Aprobada",
        "Rechazada con Observaciones",
    };
    
    public SolicitudService(String domain){
        solicitudDAL = new SolicitudDAL(domain);
    }
    
    public SoliEvalTer getOne(int id){
        SoliEvalTer solicitud = null;
        try {
            solicitud = solicitudDAL.byId(id);
            solicitud.setClientenombre("CLIENTE:NULL");
            solicitud.setPdf("http://localhost/NULL.pdf");
            solicitud.setFechaderivacion("2018-10-01");
        } catch (UnirestException ex) {
            Logger.getLogger(ClienteService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return solicitud;
    }
    
    public SoliEvalTer[] getCollection(){
        
        SoliEvalTer[] solicitudes;
        try {
            solicitudes = solicitudDAL.getAllEvaluaciones();
            for(SoliEvalTer solicitud: solicitudes){
                solicitud.setClientenombre("CLIENTE:NULL");
                solicitud.setPdf("http://localhost/NULL.pdf");
                solicitud.setFechaderivacion("2018-10-01");
            }
        }catch(UnirestException ex){
            solicitudes = null;
        } catch (ParseException ex) {
            solicitudes = null;
        }
        
        return solicitudes;
    }
    
    public long save(SoliEvalTer solicitud) {
        long id = 0;
        if(solicitud.getIdsolicitud()> 0){
            try {
                id = solicitudDAL.update(solicitud);
            } catch (UnirestException ex) {
                Logger.getLogger(ClienteService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else {
            try {
                id = solicitudDAL.create(solicitud);
            } catch (UnirestException ex) {
                Logger.getLogger(ClienteService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return id;
    }
}
