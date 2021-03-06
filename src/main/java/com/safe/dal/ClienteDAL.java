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
        
        String url = getURI("cliente/readOneClienteById/%d");
        HttpResponse<Cliente[]> response = Unirest.get(String.format(url, id)).asObject(Cliente[].class);
        Cliente[] clientes = response.getBody();
        
        
        return clientes[0];
    }
    
    public Cliente byIdRut(String rut) throws UnirestException {
        Cliente cliente = new Cliente();
        cliente.setRutcliente(rut);
        String url = getURI("cliente/readOneClienteByRut/");
        HttpResponse<Cliente[]> response = Unirest.post(String.format(url))
        .header("accept", "application/json")
        .header("Content-Type", "application/json")
        .body(cliente).asObject(Cliente[].class);
        
        Cliente[] clientes = response.getBody();
        
        if(clientes.length > 0) return clientes[0];
        
        return null;
    }
    
    public Cliente[] all() throws UnirestException{
        String url = getURI("cliente/getAllCliente/");
        HttpResponse<Cliente[]> response = Unirest.get(String.format(url)).asObject(Cliente[].class);
        Cliente[] clientes = response.getBody();
                
        return clientes;
    }
    
    public long create(Cliente cliente) throws UnirestException {
        String url = getURI("cliente/createClienteSP");
        HttpResponse<Cliente[]> result = Unirest.post(url)
        .header("accept", "application/json")
        .header("Content-Type", "application/json")
        .body(cliente).asObject(Cliente[].class);
        
        Cliente[] clientes = result.getBody();
        if(clientes.length > 0) return clientes[0].getIdcliente();
        
        return 0;
    }
    
    public long update(Cliente cliente) throws UnirestException {
        String url = getURI("cliente/upCliente");
        HttpResponse<String> postResponse = Unirest.put(String.format(url))
        .header("accept", "application/json")
        .header("Content-Type", "application/json")
        .body(cliente)
        .asString();
        
        return cliente.getIdcliente();
    }
    
    public void delete(String rut) throws UnirestException {
        String url = getURI("cliente/deleteCliente/%s/0");
        
        HttpResponse<String> postResponse = Unirest.put(String.format(url, rut))
        .header("accept", "application/json")
        .header("Content-Type", "application/json")
        .body(String.class)
        .asString();
    }
}
