package com.googlecode.g2re.html.style;

/**
 * This property controls if and how an element’s text content is capitalized
 * @author Brad Rydzewski
 */
public enum TextTransform implements StyledElement {
    /**
     * Default. Defines normal text, with lower case letters and capital letters
     */
    none, 
    /**
     * Each word in a text starts with a capital letter
     */
    capitalize, 
    /**
     * Defines only capital letters
     */
    uppercase, 
    /**
     * Defines no capital letters, only lower case letters
     */
    lowercase;
            
    /**
     * Converts the Enum value to a CSS String
     * @return
     */
    public String toCSS(){
        return new StringBuilder()
                .append("text-transform:")
                .append(this.toString())
                .append(";").toString();
    }    
}
