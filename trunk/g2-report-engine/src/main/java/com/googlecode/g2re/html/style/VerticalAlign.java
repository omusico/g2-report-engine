package com.googlecode.g2re.html.style;

/**
 * Aligns the element vertically. The initial value is baseline -
 * where the element is placed on the baseline of the parent element.
 * @author Brad Rydzewski
 */
public enum VerticalAlign implements StyledElement {
    /**
     * The top of the element is aligned with the top of the tallest
     * element on the line.
     */
    top,
    /**
     * The bottom of the element is aligned with the lowest
     * element on the line
     */
    bottom,
    /**
     * The element is placed in the middle of the parent element.
     */
    middle;

    /**
     * Converts the Enum value to a CSS String
     * @return
     */
    public String toCSS(){
        return new StringBuilder()
                .append("vertical-align:")
                .append(this.toString())
                .append(";").toString();
    }
}
