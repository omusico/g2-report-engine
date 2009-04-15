package com.googlecode.g2re.html.style;

/**
 * Defines style of borders
 * @author Brad Rydzewski
 */
public enum BorderStyle implements StyledElement {
    /**
     * Means no border will show, and the computed border-width is zero
     */
    none,
    /**
     * Has the same meaning as none, except when it refers to table borders in 
     * cases where two cells share a border, and the table cells have collapsed 
     * borders (border-collapse:collapse;). The value hidden ensures that no 
     * border is drawn, since hidden takes precedence over all other border 
     * styles. If none had been used for one border in the cell, the border 
     * would still be drawn, as the adjacent cell’s border would take precedence
     */
    hidden, 
    /**
     * Implements the border as a series of dots
     */
    dotted, 
    /**
     * Implements the border as a series of dashes
     */
    dashed, 
    /**
     * Implements the border as a solid line
     */
    solid,
    /**
     * Implements the border as two solid lines. The sum of the two border 
     * widths and the space between them equals the value that has been set 
     * for border-width
     */
    Double, 
    /**
     * A three-dimensional effect that gives the impression that the border 
     * is carved into the canvas.
     */
    groove, 
    /**
     * A three-dimensional effect that has the opposite effect of groove, in 
     * that the border appears to protrude from the canvas
     */
    ridge, 
    /** 
     * A three-dimensional effect that gives the impression that the box is 
     * embedded into the canvas. When it’s used on tables to which the separated 
     * borders model has been applied, the inset value appears to make the 
     * whole box look as though it were embedded into the canvas. When used with 
     * the collapsing border model, it’s treated the same as the value ridge. 
     */
    inset, 
    /**
     * A three-dimensional effect that has the opposite effect of inset in that 
     * the border gives the impression that the box protrudes from the canvas. 
     * When it’s used on tables to which the separated borders model has been 
     * applied, the border makes the whole box look as though it were coming out 
     * of the canvas. When it’s used with the collapsing border model, it 
     * behaves the same way as groove
     */
    outset;
    
    /**
     * Converts the Enum value to a CSS String
     * @return
     */
    public String toCSS(){
        return new StringBuilder()
                .append("border-style:")
                .append(this.toString())
                .append(";")
                .toString();
    }
}
