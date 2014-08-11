/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.centralita.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Diego Ivan Perez Michel <ivanevil31@gmail.com>
 */
public class Util {
    
    public static final String PHONE_REGEX = "^(\\+\\d{1,2}\\s)?\\(?\\d{3}\\)?[\\s.-]\\d{3}[\\s.-]\\d{4}$";

    /**
     * Converts the String date into a Date object.
     */
    public static Date parseDate(String date) throws java.text.ParseException {
        DateFormat formatter = new SimpleDateFormat("d-MMM-yyyy,HH:mm:ss aaa");

        return formatter.parse(date);
    }

    /**
     *  @param date Converts the param date object into a formated string with date.
     */
    public static String formatDate(Date date){
        SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");

        return formater.format(date);
    }

}
