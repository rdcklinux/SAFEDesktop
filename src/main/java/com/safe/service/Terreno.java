/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.safe.service;

import com.mashape.unirest.http.exceptions.UnirestException;
import com.safe.dal.TerrenoDAL;
import com.safe.entity.SoliEvalTer;
import java.text.ParseException;
import java.util.ArrayList;

/**
 *
 * @author familia
 */
public class Terreno {
    
    public static String[] TIPOS = {
        "",
        "Instalación",
        "Persona",
    };
    
    public static String[] ESTADOS = {
        "",
        "Creada",
        "Pendiente Revisión Supervisor",
        "Pendiente Revisión Ingeniero",
        "Aprobada",
        "Rechazada con Observaciones",
    };
    
    public ArrayList<SoliEvalTer> getCollection(){
        TerrenoDAL terrenoDAL = new TerrenoDAL();
        ArrayList<SoliEvalTer> collection;
        try {
            collection = terrenoDAL.getAllEvaluaciones();
        }catch(UnirestException ex){
            collection = null;
        } catch (ParseException ex) {
            collection = null;
        }
        
        return collection;
    }
}
