package com.googlecode.g2re.html.style;

/**
 * Defines a Color.
 * @author Brad Rydzewski
 */
public class Color implements StyledElement {

    /**
     * The red color component value, range of 0-255.
     * @serial
     */
    private int red;
    /**
     * The green color component value, range of 0-255.
     * @serial
     */
    private int green;
    /**
     * The blue color component value, range of 0-255.
     * @serial
     */
    private int blue;

    /**
     * Constructs an empty Color with no specified color components.
     */
    public Color() {
    }

    /**
     * Constructs an Color given the red, green and blue integer
     * color components.
     * @param r the red component.
     * @param g the green component.
     * @param b the blue component.
     */
    public Color(final int r, final int g, final int b) {
        setRGB(r, g, b);
    }

    /**
     * Constructs an Color based on the Hex string provided. The hex
     * string is commently used in HTML and CSS. Example: #CCCCCC.
     * @param hex hex color value (i.e. #000000).
     */
    public Color(final String hex) {
        String tmpHex = hex.replace("#", "");
        tmpHex = "0x" + tmpHex;
        java.awt.Color tmpColor
                = java.awt.Color.decode(tmpHex);
        setRGB(tmpColor.getRed(), tmpColor.getGreen(), tmpColor.getBlue());
    }

    /**
     * Gets the blue color component, in the range 0-255.
     * @return blue integer color component.
     */
    public final int getBlue() {
        return blue;
    }

    /**
     * Sets the blue color component, in the range 0-255.
     * @param b blue integer color component.
     */
    public final void setBlue(final int b) {
        this.blue = b;
    }

    /**
     * Gets the green color component, in the range 0-255.
     * @return green integer color component.
     */
    public final int getGreen() {
        return green;
    }

    /**
     * Sets the green color component, in the range 0-255.
     * @param g green integer color component.
     */
    public final void setGreen(final int g) {
        this.green = g;
    }

    /**
     * Gets the red color component, in the range 0-255.
     * @return red integer color component.
     */
    public final int getRed() {
        return red;
    }

    /**
     * Sets the red color component, in the range 0-255.
     * @param r red integer color component.
     */
    public final void setRed(final int r) {
        this.red = r;
    }

    /**
     * Sets the color given the red, green and blue colors values, in the
     * range 0-255.
     * @param r the red color component.
     * @param g the green color component.
     * @param b the blue color component.
     */
    public final void setRGB(final int r, final int g, final int b) {
        setRed(r);
        setGreen(g);
        setBlue(b);
    }

    /**
     * Converts a RGB value to string in a standard CSS rbg format of
     * rgb([red],[green],[blue]).
     * {@link java.awt.Color}
     * @return color in a css rgb string, ie rgb([red],[green],[blue]).
     */
    @Override
    public final String toString() {
        return new StringBuilder()
                .append("rgb(")
                .append(getRed())
                .append(",")
                .append(getGreen())
                .append(",")
                .append(getBlue())
                .append(")")
                .toString();
    }

    /**
     * Builds a css string from the Color.
     * @return css string for color.
     */
    @Override
    public final String toCSS() {
        return new StringBuilder()
                .append("color")
                .append(this.toString())
                .append(";")
                .toString();
    }
}
