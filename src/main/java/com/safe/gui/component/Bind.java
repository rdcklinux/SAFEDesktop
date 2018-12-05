/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.safe.gui.component;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JLabel;

/**
 *
 * @author developer
 */
public class Bind {
    
    public static void setComponent(Object object, JFrame container, Object scope) {
        performChange(object, container, scope, false);
    }
    
    public static void setEntity(Object object, JFrame container, Object scope) {
        performChange(object, container, scope, true);
    }
    
    private static void setter(JComponent component, Method method, Object object) throws IllegalAccessException {
        try {
            if(component instanceof JTextField){
                method.invoke(object,((JTextField)component).getText());
            }else if(component instanceof JTextArea) {
                method.invoke(object,((JTextArea)component).getText());
            }else if(component instanceof JComboBox){
                method.invoke(object,((JComboBox)component).getSelectedIndex());
            }
        } catch (InvocationTargetException ex) {}
    }
    
    private static void getter(JComponent component, Method method, Object object) throws IllegalAccessException {
        Object result;
        try {
            result = method.invoke(object);
        } catch (InvocationTargetException ex) {
            return;
        }
        if(result == null) result = "";
        if(component instanceof JTextField) {
            ((JTextField)component).setText(result.toString());
        } else if(component instanceof JTextArea){
            ((JTextArea)component).setText(result.toString());
        } else if(component instanceof JLabel){
            ((JLabel)component).setText(result.toString());
        } else if(component instanceof JComboBox){
            ((JComboBox)component).setSelectedIndex((int)result);
        }
    }
    
    private static void performChange(Object object, JFrame container,Object scope, boolean set) {
        Class<?> mapEntity = object.getClass();
        String name = object.getClass().toString().replace("class ","");
        String attribute;
        Class<? extends JFrame> swing = container.getClass();
        Field[] components = swing.getDeclaredFields();
        JComponent component;
        String type = set?"set":"get";
        for(Field c: components) {
            try {
                c.setAccessible(true);
                if(!(c.get(container) instanceof JComponent)) continue;
                if(Modifier.isFinal(c.getModifiers())) continue;
                component = (JComponent)c.get(container);
                if(component.getName() == null) continue;
                if(!component.getName().startsWith(name)) continue;
                if(!component.getRootPane().getParent().equals(scope)) continue;
                attribute = component.getName().replace(name + ".", "");
                attribute = (attribute.charAt(0)+"").toUpperCase() + attribute.substring(1);
                Method method = null;
                Method[] methods = mapEntity.getMethods();
                for(Method m: methods){
                    if(m.getName().equals(type + attribute)){
                        method = m;
                        break;
                    }
                }
                if(method == null) continue;
                if(set){
                    setter(component, method, object);
                } else {
                    getter(component, method, object);
                }
            } catch (IllegalArgumentException ex) {
                Logger.getLogger(Bind.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                Logger.getLogger(Bind.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}
