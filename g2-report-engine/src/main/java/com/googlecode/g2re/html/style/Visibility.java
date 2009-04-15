package com.googlecode.g2re.html.style;

/**
 * Sets if an element should be visible or invisible
 * @author Brad Rydzewski
 */
public enum Visibility implements StyledElement {
    /** 
     * The element is visible
     */
    visible, 
    /**
     * The element is invisible
     */
    hidden, 
    /**
     * When used in table elements, this value removes a row or column, but it 
     * does not affect the table layout. The space taken up by the row or 
     * column will be available for other content. If this value is used on 
     * other elements, it renders as "hidden"
     */
    collapse;
    
    /**
     * Converts the Enum value to a CSS String
     * @return
     */
    public String toCSS(){
        return new StringBuilder()
                .append("visibility:")
                .append(this.toString())
                .append(";").toString();
    }
}
