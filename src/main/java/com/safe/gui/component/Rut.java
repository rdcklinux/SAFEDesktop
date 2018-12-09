/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.safe.gui.component;

/**
 *
 * @author roderick
 */
public class Rut {

    public static boolean isValid(String rut) {
        int i=0, sum=0, num=2, dv=0;
        String inv="",d,v;
        try {
            String[] part = rut.split("-");
            d=part[0];
            v=part[1];
        } catch(ArrayIndexOutOfBoundsException ex){
            return false;
        }
        if(v.equals("0")) v="11"; else if(v.equalsIgnoreCase("K")) v="10";
        for(char c: d.toCharArray()) {
         inv = c + inv;
        }
        for(char c: inv.toCharArray()) {
          i = Integer.parseInt(c+"");
          sum+=(i * num);
          num++;
          if(num>7) num=2;
        }
        dv=11-(sum % 11);
        return (Integer.parseInt(v) == dv);
    }
}
