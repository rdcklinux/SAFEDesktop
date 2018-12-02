/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.safe.gui.component;

import java.awt.Window;

/**
 *
 * @author cetecom
 */
public  class WindowComponent {
    private static java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
    
    public static void centerWindow(Window object){
        int px = (screenSize.width / 2) - ( object.getWidth() / 2);
        int py = (screenSize.height / 2) - ( object.getHeight() / 2);
        
        object.setLocation(new java.awt.Point(px, py));
    }
}
