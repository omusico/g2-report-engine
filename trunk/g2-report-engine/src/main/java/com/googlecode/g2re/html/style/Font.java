package com.googlecode.g2re.html.style;

/**
 * Defines how an elements font should be rendered
 * @author Brad Rydzewski
 */
public class Font implements StyledElement {
        private Color color;
        private float pixels = -1;
        private String fontFamily;
        private FontStyle fontStyle;
        private FontWeight fontWeight;
        
        public Font() {
            
        }
        
        public Font(String fontFamily) {
            setFontFamily(fontFamily);
        }
       
        public Font(String ff, float px) {
            setFontFamily(ff);
            setPixels(px);
        }
        
        public Font(float px) {
            setPixels(px);
        }

        public Color getColor() {
            return color;
        }

        public void setColor(Color color) {
            this.color = color;
        }

        public String getFontFamily() {
            return fontFamily;
        }

        public void setFontFamily(String fontFamily) {
            this.fontFamily = fontFamily;
        }

        public FontStyle getFontStyle() {
            return fontStyle;
        }

        public void setFontStyle(FontStyle fontStyle) {
            this.fontStyle = fontStyle;
        }

        public FontWeight getFontWeight() {
            return fontWeight;
        }

        public void setFontWeight(FontWeight fontWeight) {
            this.fontWeight = fontWeight;
        }

        public float getPixels() {
            return pixels;
        }

        public void setPixels(float pixels) {
            this.pixels = pixels;
        }
        
        /**
         * Converts this object to a CSS String
         * @return
         */
        public String toCSS(){
            
            StringBuilder css = new StringBuilder();
            
            //append font-family
            if(getFontFamily()!=null && getFontFamily().isEmpty()==false){
                css.append("font-family:").append(getFontFamily());
            }
            
            if(getFontWeight()!=null)
                css.append(getFontWeight().toCSS());
            
            if(getFontStyle()!=null)
                css.append(getFontStyle().toCSS());
            
            if(getColor()!=null)
                css.append("color:").append(getColor().toString()).append(";");
            
            if(getPixels()!=-1)
                css.append("font-size:").append(getPixels()).append(";");
            
            return css.toString();
            
        }
}
