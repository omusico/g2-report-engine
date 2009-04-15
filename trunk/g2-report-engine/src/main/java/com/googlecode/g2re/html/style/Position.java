package com.googlecode.g2re.html.style;

/**
 * Places an element in a static, relative, absolute or fixed position
 * @author Brad Rydzewski
 */
public enum Position implements StyledElement {

    /**
     * Default. An element with position: static always has the position the 
     * normal flow of the page gives it (a static element ignores any top, 
     * bottom, left, or right declarations)
     */
    Static,
    /**
     * An element with position: relative moves an element relative to 
     * its normal position, so "left:20" adds 20 pixels to the element's 
     * LEFT position
     */
    relative,
    /**
     * An element with position: absolute is positioned at the specified 
     * coordinates relative to its containing block. The element's position 
     * is specified with the "left", "top", "right", and "bottom" properties
     */
    absolute,
    /**
     *  An element with position: fixed is positioned at the specified 
     * coordinates relative to the browser window. The element's position is 
     * specified with the "left", "top", "right", and "bottom" properties. 
     * The element remains at that position regardless of scrolling. 
     * Works in IE7 (strict mode)
     */
    fixed;
    
    /**
     * Converts the Enum value to a CSS String
     * @return
     */
    public String toCSS(){
        return new StringBuilder()
                .append("position:")
                .append(this.toString())
                .append(";").toString();
    }
}
