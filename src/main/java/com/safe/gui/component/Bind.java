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
    
    public static void setComponent(Object object, JFrame container) {
        Class<?> mapEntity = object.getClass();
        String name = object.getClass().toString().replace("class ", "");
        String attribute;
        Class<? extends JFrame> swing = container.getClass();
        Field[] components = swing.getDeclaredFields();
        JComponent component;
        Object result;
        for(Field c: components) {
            try {
                c.setAccessible(true);
                if(!(c.get(container) instanceof JComponent)) continue;
                if(Modifier.isFinal(c.getModifiers())) continue;
                component = (JComponent)c.get(container);
                if(component.getName() == null) continue;
                if(!component.getName().startsWith(name)) continue;
                attribute = component.getName().replace(name + ".", "");
                
                attribute = (attribute.charAt(0)+"").toUpperCase() + attribute.substring(1);
                Method method;
                try {
                    method = mapEntity.getMethod("get" + attribute);
                    method.setAccessible(true);
                } catch (NoSuchMethodException ex) {
                    continue;
                }
                try {
                    result = method.invoke(object);
                } catch (InvocationTargetException ex) {
                    continue;
                }
                if(component instanceof JTextField) {
                    ((JTextField)component).setText(result.toString());
                } else if(component instanceof JTextArea){
                    ((JTextArea)component).setText(result.toString());
                } else if(component instanceof JLabel){
                    ((JLabel)component).setText(result.toString());
                } else if(component instanceof JComboBox){
                    ((JComboBox)component).setSelectedIndex((int)result);
                }
            } catch (IllegalArgumentException ex) {
                Logger.getLogger(Bind.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                Logger.getLogger(Bind.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public static void setEntity(Object object, JFrame container) {
        Class<?> mapEntity = object.getClass();
        String name = object.getClass().toString().replace("class ","");
        String attribute;
        Class<? extends JFrame> swing = container.getClass();
        Field[] components = swing.getDeclaredFields();
        JComponent component;
        
        for(Field c: components) {
            try {
                c.setAccessible(true);
                if(!(c.get(container) instanceof JComponent)) continue;
                if(Modifier.isFinal(c.getModifiers())) continue;
                component = (JComponent)c.get(container);
                if(component.getName() == null) continue;
                if(!component.getName().startsWith(name)) continue;
                attribute = component.getName().replace(name + ".", "");
                
                attribute = (attribute.charAt(0)+"").toUpperCase() + attribute.substring(1);
                Method method;
                try {
                    method = mapEntity.getMethod("set" + attribute, Object.class);
                    method.setAccessible(true);
                } catch (NoSuchMethodException ex) {
                    continue;
                }
                
                try {
                    if(component instanceof JTextField){
                        method.invoke(object,((JTextField)component).getText());
                    }else if(component instanceof JTextArea) {
                        method.invoke(object,((JTextArea)component).getText());
                    }else if(component instanceof JComboBox){
                        method.invoke(object,((JComboBox)component).getSelectedIndex());
                    }
                } catch (InvocationTargetException ex) {
                    continue;
                }
            } catch (IllegalArgumentException ex) {
                Logger.getLogger(Bind.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                Logger.getLogger(Bind.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    private static void performChange(Object object, JFrame container, boolean toComponent) {
        Class<?> mapEntity = object.getClass();
        String name = object.getClass().toString().replace("class ","");
        String attribute;
        Class<? extends JFrame> swing = container.getClass();
        Field[] components = swing.getDeclaredFields();
        JComponent component;
        
        for(Field c: components) {
            try {
                c.setAccessible(true);
                if(!(c.get(container) instanceof JComponent)) continue;
                if(Modifier.isFinal(c.getModifiers())) continue;
                component = (JComponent)c.get(container);
                if(component.getName() == null) continue;
                if(!component.getName().startsWith(name)) continue;
                attribute = component.getName().replace(name + ".", "");
                try {   
                    Field field = mapEntity.getDeclaredField(attribute);
                    field.setAccessible(true);
                    if(toComponent){
                        if(component instanceof JTextField) {
                            JTextField instance = (JTextField)component;
                            instance.setText(field.get(object).toString());
                        }
                        if(component instanceof JComboBox) {
                            ((JComboBox)component).setSelectedItem(field.get(object));
                            //instance.setText((String) );
                        }
                    }
                } catch (NoSuchFieldException ex) {
                    Logger.getLogger(Bind.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SecurityException ex) {
                    Logger.getLogger(Bind.class.getName()).log(Level.SEVERE, null, ex);
                }       
            } catch (IllegalArgumentException ex) {
                Logger.getLogger(Bind.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                Logger.getLogger(Bind.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}
