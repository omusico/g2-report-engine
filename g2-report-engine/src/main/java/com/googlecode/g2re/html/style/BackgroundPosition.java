package com.googlecode.g2re.html.style;

/**
 * Sets the starting position of a background image
 * @author Brad Rydzewski
 */
public enum BackgroundPosition implements StyledElement {
    top_left,
    top_center,
    top_right,
    center_left,
    center_center,
    center_right,
    bottom_left,
    bottom_center,
    bottom_right, 
    xy_position, 
    xy_percent;

    /**
     * Converts the Enum value to a CSS String
     * @return
     */
    public String toCSS(){
        if(this==xy_position || this == xy_percent) return "";
        return new StringBuilder()
                .append("background-position:")
                .append(this.toString().replace("_", " "))
                .append(";")
                .toString();
    }
}
