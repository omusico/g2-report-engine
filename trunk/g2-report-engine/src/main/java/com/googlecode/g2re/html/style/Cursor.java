package com.googlecode.g2re.html.style;

/**
 * Specifies the type of cursor to be displayed
 * @author Brad Rydzewski
 */
public enum Cursor implements StyledElement {
    /**
     * Default. The browser sets a cursor
     */
    auto,
    /**
     * The cursor render as a crosshair
     */
    crosshair,
    /**
     * The default cursor (often an arrow)
     */
    Default, 
    /**
     * The cursor render as a pointer (a hand) that indicates a link 
     */
    pointer,
    /**
     * The cursor indicates something that should be moved
     */
    move, 
    /**
     * The cursor indicates text
     */
    text, 
    /**
     * The cursor indicates that the program is busy (often a watch 
     * or an hourglass)
     */
    wait,
    /**
     * The cursor indicates that help is available (often a question mark 
     * or a balloon)
     */
    help;
    
    /**
     * Converts the Enum value to a CSS String
     * @return
     */
    public String toCSS(){
        return new StringBuilder()
                .append("cursor:")
                .append(this.toString())
                .append(";").toString();
    }
}
