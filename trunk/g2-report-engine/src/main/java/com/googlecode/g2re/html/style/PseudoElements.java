package com.googlecode.g2re.html.style;

/**
 * Represents CSS Pseudo-class/elements, used to add special effects to 
 * some selectors. See {@linkplain http://www.w3.org/TR/CSS2/selector.html#pseudo-elements}
 * @author Brad Rydzewski
 */
public enum PseudoElements implements StyledElement {
    /**
     * Adds special style to an activated element
     */    
    active, 
    /**
     * Adds special style to an element while the element has focus
     */
    focus,
    /**
     * Adds special style to an element when you mouse over it
     */
    hover, 
    /**
     * Adds special style to an unvisited link
     */
    link, 
    /**
     * Adds special style to a visited link
     */
    visited, 
    /**
     * Adds special style to an element that is the first child of 
     * some other element
     */
    first_child, 
    /**
     * Allows the author to specify a language to use in a specified element
     */
    lang, 
    /**
     * Adds special style to the first letter of a text
     */
    first_letter,
    /**
     * Adds special style to the first line of a text
     */
    first_line, 
    /**
     * Inserts some content before an element
     */
    before,
    /**
     * Inserts some content after an element
     */
    after;
    
    /**
     * Converts the Enum value to a CSS String
     * @return
     */
    public String toCSS(){
        return new StringBuilder()
                .append(":")
                .append(this.toString().replace("_","-"))
                .toString();
    }
}
