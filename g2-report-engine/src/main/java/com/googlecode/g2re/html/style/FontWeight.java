package com.googlecode.g2re.html.style;

/**
 * This property sets the font weight that’s applied to the text 
 * content of an element.
 * <p>Note that many common computer fonts are only available in a limited 
 * number of weights (often, the only options are normal and bold).</p>
 * @author Brad Rydzewski
 */
public enum FontWeight implements StyledElement {
    /**
     * Defines normal characters
     */
    normal,
    /**
     * Defines thick characters, a weight of 700
     */
    bold,
    /**
     * Selects a font weight that’s darker than that inherited from the 
     * parent element
     */
    bolder,
    /**
     * Selects a font weight that’s lighter than that inherited from the 
     * parent element
     */
    lighter;
    
    /**
     * Converts the Enum value to a CSS String
     * @return
     */
    public String toCSS(){
        return new StringBuilder()
                .append("font-weight:")
                .append(this.toString())
                .append(";").toString();
    }
}
