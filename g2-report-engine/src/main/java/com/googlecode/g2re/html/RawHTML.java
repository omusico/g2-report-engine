package com.googlecode.g2re.html;

import com.googlecode.g2re.html.HTMLBuilderArgs;

/**
 * Represents a raw string of HTML text
 * @author Brad Rydzewski
 */
public class RawHTML extends Element {
    
    private String value;

    public RawHTML(){}
    public RawHTML(String value){
        
        this.setValue(value);
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public void build(HTMLBuilderArgs args) {
        args.getHtml().append(getValue());
    }
    
    
}
