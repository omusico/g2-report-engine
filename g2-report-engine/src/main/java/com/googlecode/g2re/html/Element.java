package com.googlecode.g2re.html;

import com.googlecode.g2re.html.style.Style;

/**
 * Represents an HTML Element
 * @author Brad Rydzewski
 */
public abstract class Element {

    private String name;
    private Style style;
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Style getStyle() {
        return style;
    }

    public void setStyle(Style style) {
        this.style = style;
    }
    
    
    /**
     * Builds an HTML element
     * @param args
     */
    public abstract void build(HTMLBuilderArgs args);
    
    /**
     * Adds the class to the HTML string builder element
     * @param args
     */
    void addClassToTag(HTMLBuilderArgs args){
        
        //set class
        if(getStyle()!=null){
            args.getHtml().append(" class='");
            args.getHtml().append(getStyle().getName());
            args.getHtml().append("'");
        }

    }
    
    /**
     * Adds the id to the HTML string builder element
     * @param args
     */
    void addIdToTag(HTMLBuilderArgs args){
        
        //set id
        if(getId()!=null){
            args.getHtml().append(" id='");
            args.getHtml().append(getId());
            args.getHtml().append("'");
        }
    }
}
