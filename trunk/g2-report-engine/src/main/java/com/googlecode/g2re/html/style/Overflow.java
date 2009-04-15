package com.googlecode.g2re.html.style;

/**
 * Sets what happens if the content of an element overflow its area
 * @author Brad Rydzewski
 */
public enum Overflow implements StyledElement {
    /**
     * Default. The content is not clipped. It renders outside the element
     */
    visible, 
    /**
     * The content is clipped, but the browser does not display a scroll-bar 
     * to see the rest of the content 
     */
    hidden, 
    /**
     * The content is clipped, but the browser displays a scroll-bar to 
     * see the rest of the content
     */
    scroll, 
    /**
     * If the content is clipped, the browser should display a scroll-bar 
     * to see the rest of the content
     */
    auto;
    
    /**
     * Converts the Enum value to a CSS String
     * @return
     */
    public String toCSS(){
        return new StringBuilder()
                .append("overflow:")
                .append(this.toString())
                .append(";").toString();
    }
}
