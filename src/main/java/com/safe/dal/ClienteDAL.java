/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.safe.dal;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.safe.entity.Cliente;

/**
 *
 * @author developer
 */
public class ClienteDAL extends DAL {
    
    public ClienteDAL(String domain){
        this.domain = domain;
        initObjectMapper();
    }
    
    public Cliente byId(int id) throws UnirestException{
        
        String url = getURI("cliente/readOneCliente/%d");
        HttpResponse<Cliente[]> response = Unirest.get(String.format(url, id)).asObject(Cliente[].class);
        Cliente[] clientes = response.getBody();
        
        
        return clientes[0];
    }
}
