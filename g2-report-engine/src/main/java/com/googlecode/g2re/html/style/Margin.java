package com.googlecode.g2re.html.style;

/**
 * Defines space around elements
 * @author Brad
 */
public class Margin implements StyledElement {
        private float top;
        private float bottom;
        private float left;
        private float right;
        
        public Margin(int top,int bottom, int left, int right){
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
         * @return margin: [top],[right],[bottom],[left]
         */
        public String toCSS(){
            return new StringBuilder()
                    .append("margin:")
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
