package com.googlecode.g2re.html.style;

/**
 * Defines the style for how the border of an element will be rendered.
 * //TODO: Add support for transparent colors
 * @author Brad Rydzewski
 */
public class Border implements StyledElement {

    private float width;
    private BorderStyle style;
    private BorderLocation location = BorderLocation.all;
    private Color color;

    public Border() {
    }

    public Border(float width, BorderStyle style, Color color) {
        this.width = width;
        this.style = style;
        this.color = color;
    }

    public Border(float width, BorderStyle style, String color) {
        this.width = width;
        this.style = style;
        this.color = new Color(color);
    }
    
    public Border(float width, BorderStyle style, String color, BorderLocation location) {
        this.width = width;
        this.style = style;
        this.location = location;
        this.color = new Color(color);
    }

    public BorderLocation getLocation() {
        return location;
    }

    public void setLocation(BorderLocation location) {
        this.location = location;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public BorderStyle getStyle() {
        return style;
    }

    public void setStyle(BorderStyle style) {
        this.style = style;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    /**
     * Converts the Enum value to a CSS String
     * @return
     */
    public String toCSS() {

        StringBuilder css = new StringBuilder();
        
        if(getLocation()==BorderLocation.all) {
            css.append("border:");
        } else {
            css.append("border-")
                    .append(getLocation().toString())
                    .append(":");
        }
        
        css.append(getWidth())
                .append("px ")
                .append(getStyle().toString())
                .append(" ")
                .append(getColor().toString())
                .append(";");
        
        return css.toString();
    }
}
