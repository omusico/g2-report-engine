package com.googlecode.g2re.html.style;

/**
 * Represents the size of an Element
 * @author Brad Rydzewski
 */
public class Size implements StyledElement {
        private SizeType widthType;
        private SizeType heightType;
        private float width = -1;
        private float height = -1;
        
        public Size() {
            
        }
        public Size setWidth(float width, SizeType heightType) {
            this.setWidth(width);
            this.setWidthType(heightType);
            return this;
        }
        public Size setHeight(float height, SizeType heightType) {
            this.setHeight(height);
            this.setHeightType(heightType);
            return this;
        }

        public SizeType getHeightType() {
            return heightType;
        }

        public void setHeightType(SizeType heightType) {
            this.heightType = heightType;
        }

        public SizeType getWidthType() {
            return widthType;
        }

        public void setWidthType(SizeType widthType) {
            this.widthType = widthType;
        }

        
        public float getHeight() {
            return height;
        }

        public void setHeight(float height) {
            this.height = height;
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
    public String toCSS(){
        
        StringBuilder css = new StringBuilder();
        
        //render the width
        if(getWidthType()!=null && getWidth()!=-1){
            css.append("width:")
                    .append(getWidth())
                    .append(getWidthType().toCSS())
                    .append(";");
        }

        //render the height
        if(getHeightType()!=null && getHeight()!=-1){
            css.append("height:")
                    .append(getHeight())
                    .append(getHeightType().toCSS())
                    .append(";");
        }
        
        return css.toString();
    }
}
