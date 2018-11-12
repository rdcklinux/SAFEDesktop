/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.safe.dal;

/**
 *
 * @author familia
 */
public abstract class DAL {
    
    private String schema = "http";
    private String domain = "localhost";
    private int port = 7001;
    
    protected String getURI(String path){
        return schema + "://" + domain + ':' + port + '/' + path;
    }
}
