package com.googlecode.g2re.html.style;

/**
 * Sets where an image or a text will appear in another element
 * @author Brad Rydzewski
 */
public enum Float implements StyledElement {
    /**
     * The image or text moves to the left in the parent element
     */
    left, 
    /**
     * The image or text moves to the right in the parent element
     */
    right, 
    /**
     * Default. The image or the text will be displayed just where 
     * it occurs in the text
     */
    none;
    
    /**
     * Converts the Enum value to a CSS String
     * @return
     */
    public String toCSS(){
        return new StringBuilder()
                .append("float:")
                .append(this.toString())
                .append(";").toString();
    }
}	 