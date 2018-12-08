/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.safe.dal.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author familia
 */
public class DateFomatter {
    private static Date dateObject;
    private static SimpleDateFormat dbDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private static SimpleDateFormat appDateFormat = new SimpleDateFormat("dd-MM-yyyy");
    
    private static SimpleDateFormat dbHourFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");    
    private static SimpleDateFormat appHoursFormat = new SimpleDateFormat("HH:mm");
    
    public static String dateFromDB(String datetime) {        
        
        try {
            dateObject = dbDateFormat.parse(datetime);
        } catch (ParseException ex) {
            return datetime;
        }
        
        return appDateFormat.format(dateObject);
    }
    
    public static String hourFromDB(String datetime) {        
        
        try {
            dateObject = dbHourFormat.parse(datetime);
        } catch (ParseException ex) {
            return datetime;
        }
        
        return appHoursFormat.format(dateObject);
    }
    
    public static String dateToDB(String datetime) {        
        
        try {
            dateObject = appDateFormat.parse(datetime + " 00:00:00");
        } catch (ParseException ex) {
            return datetime;
        }
        
        return appDateFormat.format(dateObject);
    }
    
    public static String hourToDB(String datetime) {        
      return appDateFormat.format(new Date()) + " " + datetime + ":00";
    }
    
    public static String hourToApp(String datetime) {
        try {
            dateObject = dbHourFormat.parse(datetime);
        } catch (ParseException ex) {
            return datetime;
        }
        
        return appHoursFormat.format(dateObject);
    }
}
