package com.googlecode.g2re.html;

import com.googlecode.g2re.html.HTMLBuilderArgs;
import com.googlecode.g2re.html.style.Style;

/**
 * Represents an Image element, corresponding to the {@code <image>} tag
 * @author Brad Rydzewski
 */
public class Image extends Element {
    private String src;
    private String alt;
    
    public Image(){}
    public Image(String src){
        this.src = src;
    }
    public Image(String src, Style style) {
        this.src = src;
        this.setStyle(style);
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getAlt() {
        return (alt==null)?"":alt;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }
    
    @Override
    public void build(HTMLBuilderArgs args) {
        
        args.getHtml().append("<img src='");
        args.getHtml().append(getSrc());
        args.getHtml().append("' alt='");
        args.getHtml().append("' ");
        args.getHtml().append(getAlt());
        
        //add style, if exists
        this.addClassToTag(args);  
        
        args.getHtml().append(" />");
    }   
}
