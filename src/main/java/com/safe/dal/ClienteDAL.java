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
    public ClienteDAL(){
        initObjectMapper();
    }
    
    public Cliente byId(int id) throws UnirestException{
        
        String url = getURI("cliente/%d.json");
        //HttpResponse<JsonNode> jsonResponse = Unirest.get(String.format(url, id)).asObject(Cliente.class);
        //JSONObject obj = jsonResponse.getBody().getObject();
        HttpResponse<Cliente> response = Unirest.get(String.format(url, id)).asObject(Cliente.class);
        Cliente cliente = response.getBody();
        
        
        return cliente;
    }
}
