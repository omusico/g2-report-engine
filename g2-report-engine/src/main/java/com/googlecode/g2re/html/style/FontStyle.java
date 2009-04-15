package com.googlecode.g2re.html.style;

/**
 * This property sets the font style to be applied for the text content 
 * of an element
 * @author Brad Rydzewski
 */
public enum FontStyle implements StyledElement {
    /**
     * 
    This value specifies a font classified as "normal" in the user agent’s font 
     * database. This is typically a Roman (upright) font for Latin characters.
     */
    normal, 
    /**
     * This value specifies a font that’s labeled "italic" in the user agent’s 
     * font database. If such a font isn’t available, it will use one 
     * labeled "oblique."
     */
    italic, 
    /**
     * This value specifies a font that’s labeled “italic” in the user agent’s 
     * font database. If such a font isn’t available, it will use one labeled 
     * "oblique."
     */
    oblique;
    
    /**
     * Converts the Enum value to a CSS String
     * @return
     */
    public String toCSS(){
        return new StringBuilder()
                .append("font-style:")
                .append(this.toString())
                .append(";").toString();
    }
}
