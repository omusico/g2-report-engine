package com.googlecode.g2re.html.style;

/**
 * Defines the style for how the background of an element will be rendered.
 * <br/><br/>
 * TODO: The Background css class will not render xpos ypos correctly. Add xSizeType and ySizeType
 * @author Brad Rydzewski
 */
public class Background implements StyledElement {

    private Color color = null;
    private String image;
    private BackgroundAttachment backgroundAttachment;
    private BackgroundPosition backgroundPosition;
    private BackgroundRepeat backgroundRepeat;
    private float xPosition;
    private float yPosition;

    public Background() {
        
    }
    public Background(String hex) {
        setColor(new Color(hex));
    }
    
    public BackgroundAttachment getBackgroundAttachment() {
        return backgroundAttachment;
    }

    public void setBackgroundAttachment(BackgroundAttachment backgroundAttachment) {
        this.backgroundAttachment = backgroundAttachment;
    }

    public BackgroundPosition getBackgroundPosition() {
        return backgroundPosition;
    }

    public void setBackgroundPosition(BackgroundPosition backgroundPosition) {
        this.backgroundPosition = backgroundPosition;
    }

    public BackgroundRepeat getBackgroundRepeat() {
        return backgroundRepeat;
    }

    public void setBackgroundRepeat(BackgroundRepeat backgroundRepeat) {
        this.backgroundRepeat = backgroundRepeat;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public float getXPosition() {
        return xPosition;
    }

    public void setXPosition(float xPosition) {
        this.xPosition = xPosition;
    }

    public float getYPosition() {
        return yPosition;
    }

    public void setYPosition(float yPosition) {
        this.yPosition = yPosition;
    }
    
    /**
     * Converts this object to a CSS String
     * @return
     */
    @Override
    public String toCSS(){
        
        StringBuilder css = new StringBuilder();
        
        //render the color
        if(getColor()!=null){
            css.append("background-color:")
                    .append(color.toString())
                    .append(";");
        }
        
        if(getImage()!=null){
            css.append("background-image:url(")
                    .append(this.getImage())
                    .append(");");
        }
        
        if(getBackgroundAttachment()!=null){
            css.append(getBackgroundAttachment().toCSS());
        } 
        
        if(getBackgroundRepeat() != null){
            css.append(getBackgroundRepeat().toCSS());
        }
        
        if(getBackgroundPosition()==null){
            //do nothing
        }
        else if(getBackgroundPosition() == BackgroundPosition.xy_percent){
            css.append("background-position:")
                    .append(getXPosition())
                    .append("% ")
                    .append(getYPosition())
                    .append("%;");
        }else if(getBackgroundPosition() == BackgroundPosition.xy_position){
            css.append("background-position:")
                    .append(getXPosition())
                    .append("px ")
                    .append(getYPosition())
                    .append("px;");
        }else{
            css.append(getBackgroundPosition().toCSS());
        }
           
        
        return css.toString();
    }
}
