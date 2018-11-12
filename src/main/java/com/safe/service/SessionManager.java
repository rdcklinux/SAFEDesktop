/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.safe.service;

import com.safe.gui.Main;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

/**
 *
 * @author familia
 */
public class SessionManager implements ActionListener {
    
    private final Main main;
    private final int sessionExpireTime = 1000;
    private final Timer timer;
    
    public SessionManager(Main main, int sessionTime){
        this.main = main;
        this.timer = new Timer(this.sessionExpireTime * sessionTime, this);
    }
    
    @Override
    public void actionPerformed(ActionEvent evt) {
         this.main.logout("La sesión ha finalizado por inactividad.");
    }
    
    public void start(){
        this.timer.start();
    }
    
    public void stop(){
        this.timer.stop();
    }
    
    public void alive(){
        if(this.timer instanceof Timer){
            this.timer.restart();            
        }
    }
}
