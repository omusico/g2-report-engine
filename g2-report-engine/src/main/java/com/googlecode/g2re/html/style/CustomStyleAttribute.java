package com.googlecode.g2re.html.style;

/**
 * Allows a custom-defined style attribute
 * @author Brad Rydzewski
 */
public class CustomStyleAttribute implements StyledElement {

    private String name;
    private String value;
    
    public CustomStyleAttribute(){
        
    }
    
    public CustomStyleAttribute(String name, String value){
        this.name = name;
        this.value= value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toCSS() {
        return new StringBuilder()
                .append(getName())
                .append(":")
                .append(getValue())
                .append(";")
                .toString();
    }
    
    
}
