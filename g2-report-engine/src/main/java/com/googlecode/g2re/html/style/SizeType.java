package com.googlecode.g2re.html.style;

/**
 * Indicates if a width / height is displayed as Pixels (px) or as 
 * a Percent (%)
 * @author Brad Rydzewski
 */
public enum SizeType implements StyledElement {
    pixel, percent;
    
    /**
     * Converts the value to a CSS String
     * @return
     */
    public String toCSS(){
        
        switch(this){
            case pixel : return "px";
            case percent : return "%";
            default : return "px";
        }
    }
}
