package com.googlecode.g2re.html.style;

/**
 * Defines the space between the element border and the element content
 * @author Brad Rydzewski
 */
public class Padding implements StyledElement {
        private float top;
        private float bottom;
        private float left;
        private float right;
        
        public Padding(int top,int bottom, int left, int right){
            this.top = top;
            this.bottom = bottom;
            this.left = left;
            this.right = right;
        }

        public float getBottom() {
            return bottom;
        }

        public void setBottom(float bottom) {
            this.bottom = bottom;
        }

        public float getLeft() {
            return left;
        }

        public void setLeft(float left) {
            this.left = left;
        }

        public float getRight() {
            return right;
        }

        public void setRight(float right) {
            this.right = right;
        }

        public float getTop() {
            return top;
        }

        public void setTop(float top) {
            this.top = top;
        }
        
        /**
         * Builds a CSS string from this object's properties
         * @return padding: [top],[right],[bottom],[left]
         */
        public String toCSS(){
            return new StringBuilder()
                    .append("padding:")
                    .append(getTop())
                    .append("px ")
                    .append(getRight())
                    .append("px ")
                    .append(getBottom())
                    .append("px ")
                    .append(getLeft())
                    .append("px")
                    .append(";")
                    .toString();
        }
}
