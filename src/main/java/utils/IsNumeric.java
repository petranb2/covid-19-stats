/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.util.regex.Pattern;

/**
* Τμήμα ΗΛΕ 46
* @author Κουλιανός Πέτρος 119076
* @author Κρουκλόβα Όλγα 94996
* @author Μάλαμας Σάββας 119131
*/
public class IsNumeric {

    private static final Pattern PATTERN = Pattern.compile("-?\\d+(\\.\\d+)?");

    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        return PATTERN.matcher(strNum).matches();
    }
}
