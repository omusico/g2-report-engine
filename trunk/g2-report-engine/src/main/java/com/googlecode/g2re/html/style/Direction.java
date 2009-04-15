package com.googlecode.g2re.html.style;

/**
 * This property specifies the following:
 * <li>the base writing direction of blocks</li>
 * <li>the direction of embeddings and overrides—see unicode-bidi—for the 
 * Unicode bidirectional algorithm</li>
 * <li>the direction of table column layout</li>
 * <li>the direction of horizontal overflow</li>
 * <li>the position of an incomplete last line in a block, when the text-align 
 * property has the value justify</li>
 * @author Brad Rydzewski
 */
public enum Direction implements StyledElement {
    /**
     * sets a left-to-right direction
     */
    ltr,
    /**
     * sets a right-to-left direction
     */
    rtl;
    
    /**
     * Converts the Enum value to a CSS String
     * @return
     */
    public String toCSS(){
        return new StringBuilder()
                .append("direction:")
                .append(this.toString())
                .append(";").toString();
    }
}
