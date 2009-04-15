package com.googlecode.g2re.html;

import com.googlecode.g2re.html.HTMLBuilderArgs;
import com.googlecode.g2re.html.style.Style;

/**
 *
 * @author Brad Rydzewski
 */
public class GridCell extends Element {

    private int order;
    private int columnSpan;
    private int rowSpan;
    private Element child;
    
    public GridCell() {
        
    }
    public GridCell(Element child) {
        this(child,0,0);
    }
    public GridCell(Element child, Style style) {
        setChild(child);
        setStyle(style);
    }
    public GridCell(Element child, int cSpan, int rSpan) {
        setChild(child);
        setColumnSpan(cSpan);
        setRowSpan(rSpan);
    }

    public Element getChild() {
        return child;
    }

    public void setChild(Element child) {
        this.child = child;
    }

    public int getColumnSpan() {
        return columnSpan;
    }

    public void setColumnSpan(int columnSpan) {
        this.columnSpan = columnSpan;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public int getRowSpan() {
        return rowSpan;
    }

    public void setRowSpan(int rowSpan) {
        this.rowSpan = rowSpan;
    }
    
    
    
    @Override
    public void build(HTMLBuilderArgs args) {
        
        //render cell open
        args.getHtml().append("<td");
        this.addClassToTag(args);
        this.addIdToTag(args);
        //add col span
        if(getColumnSpan()>1){
            args.getHtml().append(" colspan='");
            args.getHtml().append(getColumnSpan());
            args.getHtml().append("'"); 
        }
                
        //add row span
        if(getRowSpan()>1){
            args.getHtml().append(" rowspan='");
            args.getHtml().append(getRowSpan());
            args.getHtml().append("'");
        }
        
        //close out the opening tag
        args.getHtml().append(">");
        
        //render item in cell if is renderable and not null
        if(getChild() !=null){
            getChild().build(args);
        }
        else{
           args.getHtml().append("&nbsp;"); 
        }
        
        //render cell close
        args.getHtml().append("</td>");
    }

}
