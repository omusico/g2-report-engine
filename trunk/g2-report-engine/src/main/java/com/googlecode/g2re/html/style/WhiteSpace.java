package com.googlecode.g2re.html.style;

/**
 * 
 * @author Brad Rydzewski
 */
public enum WhiteSpace implements StyledElement {
    /**
     * Default. White-space is ignored by the browser
     */
    normal, 
    /**
     * White-space is preserved by the browser. Acts like the <pre> tag in HTML
     */
    pre, 
    /**
     * The text will never wrap, it continues on the same line until a <br> 
     * tag is encountered
     */
    nowrap;
    
    /**
     * Converts the Enum value to a CSS String
     * @return
     */
    public String toCSS(){
        return new StringBuilder()
                .append("white-space:")
                .append(this.toString())
                .append(";").toString();
    }
}
