package com.googlecode.g2re.html.style;

/**
 * This property specifies the decorations that will be applied to the text 
 * content of an element. These decorations are rendered in the color specified 
 * by the element’s color property
 * @author Brad Rydzewski
 */
public enum TextDecoration implements StyledElement {
    /**
     * Default. Defines a normal text
     */
    none, 
    /**
     * Defines a line under the text
     */
    underline, 
    /**
     * Defines a line over the text
     */
    overline, 
    /**
     * Defines a line through the text
     */
    line_through, 
    /**
     * Defines a blinking text
     */
    blink;
    
    /**
     * Converts the Enum value to a CSS String
     * @return
     */
    public String toCSS(){
        return new StringBuilder()
                .append("text-decoration:")
                .append(this.toString().replace("_","-"))
                .append(";").toString();
    }
}
