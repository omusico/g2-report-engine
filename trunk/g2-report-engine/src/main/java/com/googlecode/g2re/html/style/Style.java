package com.googlecode.g2re.html.style;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Defines an elements style
 * TODO: add in ability to declar pseudo elements
 * DONE: add in ability for use to add "custom" style. like -moz-shadow or something non-standard
 * DONE: let the toCSS() method use reflection to get all StyledElements that aren't null and add to StringBuilder
 * TODO: replace border with TopBorder, BottomBorder, LeftBorder, RightBorder
 * @author Brad Rydzewski
 */
public class Style implements StyledElement {
    
    private String name;
    
    /* what about :hover :visited .. etc */
    
    private Background background = null;
    private Color color = null;
    private Margin margin = null;
    private Padding padding = null;
    private Border border = null;
    private Border borderTop = null;
    private Border borderBottom = null;
    private Border borderLeft = null;
    private Border borderRight = null;
    private Size size = null;
    private Font font = null;
    private Visibility visibility;
    private Float floatPosition;
    private Cursor cursor = null;
    private Display display = null;
    private Overflow overflow = null;
    private WhiteSpace whiteSpace = null;
    private FontWeight fontWeight = null;
    private FontStyle fontStyle = null;
    private TextTransform textTransform = null;
    private Direction direction = null;
    private TextDecoration textDecoration = null;
    private TextAlign textAlign = null;
    private BorderCollapse borderCollapse = null;
    private List<CustomStyleAttribute> customAttributes 
            = new ArrayList<CustomStyleAttribute>();
    
    
    public Style(String name) {
        setName(name);
    }

    public List<CustomStyleAttribute> getCustomAttributes() {
        return customAttributes;
    }

    public void setCustomAttributes(List<CustomStyleAttribute> customAttributes) {
        this.customAttributes = customAttributes;
    }
    
    public Background getBackground() {
        return background;
    }

    public void setBackground(Background background) {
        this.background = background;
    }

    public Border getBorder() {
        return border;
    }

    public void setBorder(Border border) {
        this.border = border;
    }

    public Border getBorderBottom() {
        return borderBottom;
    }

    public void setBorderBottom(Border borderBottom) {
        borderBottom.setLocation(BorderLocation.bottom);
        this.borderBottom = borderBottom;
    }

    public Border getBorderLeft() {
        return borderLeft;
    }

    public void setBorderLeft(Border borderLeft) {
        borderLeft.setLocation(BorderLocation.left);
        this.borderLeft = borderLeft;
    }

    public Border getBorderRight() {
        return borderRight;
    }

    public void setBorderRight(Border borderRight) {
        borderRight.setLocation(BorderLocation.right);
        this.borderRight = borderRight;
    }

    public Border getBorderTop() {
        return borderTop;
    }

    public void setBorderTop(Border borderTop) {
        borderTop.setLocation(BorderLocation.top);
        this.borderTop = borderTop;
    }

    public Float getFloatPosition() {
        return floatPosition;
    }

    public void setFloatPosition(Float floatPosition) {
        this.floatPosition = floatPosition;
    }

    public BorderCollapse getBorderCollapse() {
        return borderCollapse;
    }

    public void setBorderCollapse(BorderCollapse borderCollapse) {
        this.borderCollapse = borderCollapse;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Cursor getCursor() {
        return cursor;
    }

    public void setCursor(Cursor cursor) {
        this.cursor = cursor;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public Display getDisplay() {
        return display;
    }

    public void setDisplay(Display display) {
        this.display = display;
    }

    public Float getFloat() {
        return floatPosition;
    }

    public void setFloat(Float floatPosition) {
        this.floatPosition = floatPosition;
    }

    public Font getFont() {
        return font;
    }

    public void setFont(Font font) {
        this.font = font;
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

    public Margin getMargin() {
        return margin;
    }

    public void setMargin(Margin margin) {
        this.margin = margin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Overflow getOverflow() {
        return overflow;
    }

    public void setOverflow(Overflow overflow) {
        this.overflow = overflow;
    }

    public Padding getPadding() {
        return padding;
    }

    public void setPadding(Padding padding) {
        this.padding = padding;
    }

    public Size getSize() {
        return size;
    }

    public void setSize(Size size) {
        this.size = size;
    }

    public TextAlign getTextAlign() {
        return textAlign;
    }

    public void setTextAlign(TextAlign textAlign) {
        this.textAlign = textAlign;
    }

    public TextDecoration getTextDecoration() {
        return textDecoration;
    }

    public void setTextDecoration(TextDecoration textDecoration) {
        this.textDecoration = textDecoration;
    }

    public TextTransform getTextTransform() {
        return textTransform;
    }

    public void setTextTransform(TextTransform textTransform) {
        this.textTransform = textTransform;
    }

    public Visibility getVisibility() {
        return visibility;
    }

    public void setVisibility(Visibility visibility) {
        this.visibility = visibility;
    }

    public WhiteSpace getWhiteSpace() {
        return whiteSpace;
    }

    public void setWhiteSpace(WhiteSpace whiteSpace) {
        this.whiteSpace = whiteSpace;
    }

    @Override
    public String toCSS() {
        
        //create string builder
        StringBuilder css = new StringBuilder();
        
        //get list of styled elements
        List<StyledElement> styledElements = getStyledElement();
        
        //iterate through list and generate css
        for(StyledElement element : styledElements){
            css.append(element.toCSS());
        }
        
        //iterate through use defined attributes
        List<CustomStyleAttribute> customStyles = getCustomAttributes();
        for(CustomStyleAttribute customStyle : customStyles){
            css.append(customStyle.toCSS());
        }
        
        //return the css string
        return css.toString();
        
        /*
        //OLD METHOD FOR GENERATING CSS... maybe more efficient?? but too much code
        if(getBackground()!=null)
            css.append(getBackground().toCSS());
        
        if(getColor()!=null)
            css.append(getColor().toCSS());
        
        if(getPadding()!=null)
            css.append(getPadding().toCSS());
        
        if(getMargin() !=null)
            css.append(getMargin().toCSS());
        
        if(getBorder()!=null)
            css.append(getBorder().toCSS());
        
        if(getSize()!=null)
            css.append(getSize().toCSS());
        
        if(getFont()!=null)
            css.append(getFont().toCSS());

        if(getVisibility()!=null)
            css.append(getVisibility().toCSS());
        
        if(getFloat()!=null)
            css.append(getFloat().toCSS());        

        if(getCursor()!=null)
            css.append(getCursor().toCSS()); 
        
        if(getDisplay()!=null)
            css.append(getDisplay().toCSS()); 
        
        if(getOverflow()!=null)
            css.append(getOverflow().toCSS());         

        if(getWhiteSpace()!=null)
            css.append(getWhiteSpace().toCSS()); 
        
        if(getFontWeight()!=null)
            css.append(getFontWeight().toCSS()); 
        
        if(getFontStyle()!=null)
            css.append(getFontStyle().toCSS());
        
        if(getTextTransform()!=null)
            css.append(getTextTransform().toCSS());
        
        if(getDirection()!=null)
            css.append(getDirection().toCSS());        
        
        if(getTextDecoration()!=null)
            css.append(getTextDecoration().toCSS());        
        
        if(getTextAlign()!=null)
            css.append(getTextAlign().toCSS());        

        if(getBorderCollapse()!=null)
            css.append(getBorderCollapse().toCSS());
         
         return css.toString();
         */ 
    }
    
    /**
     * Gets a list of all elements, that are not null, that implement
     * the {@link StyledElement} interface. 
     * @return
     */
    List<StyledElement> getStyledElement(){
        
        //create list
        List<StyledElement> elements = new ArrayList<StyledElement>();
        
        //get all fields for this class
        Field[] fields = this.getClass().getDeclaredFields();
        
        //iterate through fields
        for(Field f : fields){
            
            //get interfaces
            Class[] interfaces = f.getType().getInterfaces();
            
            //iterate through interfaces
            for(Class i : interfaces){
                
                //if a styled element, get css
                if(i == StyledElement.class){
                    try{
                        
                        //get the field value for "this"
                        Object element = f.get(this);
                        
                        //if not null, add the object to the list
                        if(element!=null)
                            elements.add((StyledElement)element);
                        
                        break;
                    } catch(Exception ex){ }
                }
            }
        }
        
        return elements;
    }
}