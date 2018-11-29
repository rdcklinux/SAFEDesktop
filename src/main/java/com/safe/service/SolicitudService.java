/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.safe.service;

import com.mashape.unirest.http.exceptions.UnirestException;
import com.safe.dal.SolicitudDAL;
import com.safe.entity.SoliEvalTer;
import com.safe.entity.Usuario;
import java.text.ParseException;

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
    
    public SoliEvalTer[] getCollection(){
        
        SoliEvalTer[] solicitudes;
        try {
            solicitudes = solicitudDAL.getAllEvaluaciones();
            for(SoliEvalTer solicitud: solicitudes){
                Usuario tecnico = new Usuario();
                tecnico.setNombresusuario("TECNICO:NULL");
                solicitud.setTecnico(tecnico);
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
}
