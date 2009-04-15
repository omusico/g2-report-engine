package com.googlecode.g2re.html.style;

/**
 * Sets whether the table borders are collapsed into a single border or 
 * detached as in standard HTML
 * @author Brad Rydzewski
 */
public enum BorderCollapse implements StyledElement {
    /**
     * Default. Borders are collapsed into a single border when possible
     */
    collapse, 
    /**
     * Borders are detached
     */
    separate;

    /**
     * Converts the Enum value to a CSS String
     * @return
     */
    public String toCSS(){
        return new StringBuilder()
                .append("border-collapse:")
                .append(this.toString())
                .append(";").toString();
    }
}
