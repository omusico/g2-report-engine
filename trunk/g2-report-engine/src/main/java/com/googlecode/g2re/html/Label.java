package com.googlecode.g2re.html;

/**
 *
 * @author Brad Rydzewski
 */
public class Label extends Element {

    private String value;

    public Label(){}
    public Label(String value){
        
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
        
        //append the value to the body of the HTML document
        args.getHtml().append("<span");
        this.addClassToTag(args);
        this.addIdToTag(args);
        args.getHtml().append(">");
        args.getHtml().append(getValue());
        args.getHtml().append("</span>");
    }


}
