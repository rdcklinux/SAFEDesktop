/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.safe.service;

import com.mashape.unirest.http.exceptions.UnirestException;
import com.safe.dal.ClienteDAL;
import com.safe.entity.Cliente;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author developer
 */
public class ClienteService {
    
    private ClienteDAL clienteDAL;
    
    public ClienteService(String domain){
        clienteDAL = new ClienteDAL(domain);
    }
    
    public Cliente getOne(int id){
        Cliente cliente = null;
        try {
            cliente = clienteDAL.byId(id);
        } catch (UnirestException ex) {
            Logger.getLogger(ClienteService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return cliente;
    }
    
    
    public Cliente getOneByRut(String rut){
        Cliente cliente = null;
        try {
            cliente = clienteDAL.byIdRut(rut);
        } catch (UnirestException ex) {
            Logger.getLogger(ClienteService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return cliente;
    }
    
    public Cliente[] getCollection(){
        Cliente[] clientes = null;
        try {
            clientes = clienteDAL.all();
        } catch (UnirestException ex) {
            Logger.getLogger(ClienteService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return clientes;
    }
    
    public long save(Cliente cliente) {
        long id = 0;
        if(cliente.getIdcliente() > 0){
            try {
                id = clienteDAL.update(cliente);
            } catch (UnirestException ex) {
                Logger.getLogger(ClienteService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else {
            try {
                id = clienteDAL.create(cliente);
            } catch (UnirestException ex) {
                Logger.getLogger(ClienteService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return id;
    }
    
    public void delete(String rut) {
        try {
            clienteDAL.delete(rut);
        } catch (UnirestException ex) {
            Logger.getLogger(ClienteService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
