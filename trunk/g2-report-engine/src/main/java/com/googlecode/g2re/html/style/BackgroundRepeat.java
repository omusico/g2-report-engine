package com.googlecode.g2re.html.style;

/**
 * Sets if/how a background image will be repeated
 * @author Brad Rydzewski
 */
public enum BackgroundRepeat implements StyledElement {
    /**
     * Default. The background image will be repeated vertically and 
     * horizontally
     */
    repeat, 
    /**
     * The background image will be repeated horizontally
     */
    repeat_x,
    /**
     * The background image will be repeated vertically
     */
    repeat_y,
    /**
     * The background-image will be displayed only once
     */
    no_repeat;
    
    /**
     * Converts the Enum value to a CSS String
     * @return
     */
    public String toCSS(){
        return new StringBuilder()
                .append("background-repeat:")
                .append(this.toString().replace("_", "-"))
                .append(";")
                .toString();
    }
}
