
package org.komea.product.backend.utils;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CharacterUtils {
    
    private static String ALPHA_REGEX = "[a-zA-Z]";
    // private static String ALPHA_REGEX = "[a-zA-ZéèêëàâäùûüÉÈÊËÀAÂÄÙÜÛ]";
    private static String DIGIT_REGEX = "\\d";
    
    /**
     * This method check if a char id an alphabetic char ([a-zA-Z])
     * 
     * @param _c
     * @return true if it is an alphabetic letter
     */
    public static boolean isAlphabetic(final char _c) {
    
        Pattern p = Pattern.compile(ALPHA_REGEX);
        Matcher matcher = p.matcher(String.valueOf(_c));
        return matcher.matches();
    }
    //
    
    /**
     * This method check if the char id a number between 0 an 9
     * 
     * @param _c
     * @return
     */
    public static boolean isDigit(final char _c) {
    
        Pattern p = Pattern.compile(DIGIT_REGEX);
        Matcher matcher = p.matcher(String.valueOf(_c));
        return matcher.matches();
    }
}
