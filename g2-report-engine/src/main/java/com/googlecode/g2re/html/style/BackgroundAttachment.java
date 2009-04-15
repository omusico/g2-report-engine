package com.googlecode.g2re.html.style;

/**
 * Sets whether a background image is fixed or scrolls with the rest of the page
 * @author Brad Rydzewski
 */
public enum BackgroundAttachment implements StyledElement {
    /**
     * The background image moves when the rest of the page scrolls
     */
    scroll, 
    /**
     * The background image does not move when the rest of the page scrolls
     */
    fixed;
    
    /**
     * Converts the Enum value to a CSS String
     * @return
     */
    public String toCSS(){
        return new StringBuilder()
                .append("background-attachment:")
                .append(this.toString())
                .append(";")
                .toString();
    }
    
}
