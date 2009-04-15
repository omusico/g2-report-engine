package com.googlecode.g2re.html.style;

/**
 * Aligns the text in an element. The initial value is left if 
 * {@link Direction} is ltr, and right if rtl
 * @author Brad Rydzewski
 */
public enum TextAlign implements StyledElement {
    /**
     * This value makes the text left justified
     */
    left,
    /**
     * This value makes the text right justified
     */
    right,
    /**
     * This value makes the text right justified
     */
    center,
    /**
     * This value makes the text left and right justified. 
     * In this case, inline boxes may be stretched in addition to being 
     * repositioned. If white-space is pre or pre-line, the alignment 
     * is set to the initial value.
     */
    justify;

    /**
     * Converts the Enum value to a CSS String
     * @return
     */
    public String toCSS(){
        return new StringBuilder()
                .append("text-align:")
                .append(this.toString())
                .append(";").toString();
    }
}
