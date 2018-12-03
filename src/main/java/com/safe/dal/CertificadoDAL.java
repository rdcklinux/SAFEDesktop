/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.safe.dal;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.safe.entity.Certificado;

/**
 *
 * @author developer
 */
public class CertificadoDAL extends DAL {
    
    public CertificadoDAL(String domain){
        this.domain = domain;
        initObjectMapper();
    }
    
    public Certificado byId(int id) throws UnirestException{
        
        String url = getURI("certificado/readOneCertificado/%d");
        HttpResponse<Certificado[]> response = Unirest.get(String.format(url, id)).asObject(Certificado[].class);
        Certificado[] certs = response.getBody();
        
        
        return certs[0];
    }
    
    public Certificado[] all() throws UnirestException{
        String url = getURI("certificado/getAllCertificado/");
        HttpResponse<Certificado[]> response = Unirest.get(String.format(url)).asObject(Certificado[].class);
        Certificado[] certs = response.getBody();
                
        return certs;
    }
    
    public long create(Certificado cert) throws UnirestException {
        String url = getURI("certificado/createCertificadoSP");
        HttpResponse<Certificado[]> certs = Unirest.post(url)
        .header("accept", "application/json")
        .header("Content-Type", "application/json")
        .body(cert).asObject(Certificado[].class);
        
        //TODO: debe retornar un ID de cliente
        return certs.getBody()[0].getIdcertificado();
    }
    
    public long update(Certificado cert) throws UnirestException {
        String url = getURI("certificado/upCertificado");
        HttpResponse<String> postResponse = Unirest.put(String.format(url))
        .header("accept", "application/json")
        .header("Content-Type", "application/json")
        .body(cert)
        .asString();
        
        return cert.getIdcertificado();
    }
    
    public void delete(long id) throws UnirestException {
        String url = getURI("certificado/deleteCertificado/%d/0");
        
        HttpResponse<String> postResponse = Unirest.put(String.format(url, id))
        .header("accept", "application/json")
        .header("Content-Type", "application/json")
        .body(String.class)
        .asString();
    }
}
