package com.googlecode.g2re.html;

import com.googlecode.g2re.html.HTMLBuilderArgs;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a simple HTML table, containing a set of Rows
 * @author Brad Rydzewski
 */
public class Grid extends Element {

    private List<GridRow> rows;
    private int cellPadding = 0;
    private int cellSpacing = 0;

    public int getCellPadding() {
        return cellPadding;
    }

    public void setCellPadding(int cellPadding) {
        this.cellPadding = cellPadding;
    }

    public int getCellSpacing() {
        return cellSpacing;
    }

    public void setCellSpacing(int cellSpacing) {
        this.cellSpacing = cellSpacing;
    }
    
    

    /**
     * Gets the a collection of Rows in the table
     * @return
     */
    public List<GridRow> getRows() {
        return rows;
    }

    /**
     * Sets the Rows in the table
     * @param rows
     */
    public void setRows(List<GridRow> rows) {
        this.rows = rows;
    }
    
    /**
     * Adds one or many rows to the table
     * @param rows
     */
    public void addRows(GridRow... rows){
        if(getRows()==null)
            setRows(new ArrayList<GridRow>());
        
        for(GridRow row : rows){
            getRows().add(row);
        }
    }
    
    /**
     * Adds a single row to the table
     * @param row
     * @return
     */
    public Grid addRow(GridRow row){
        if(getRows()==null)
            setRows(new ArrayList<GridRow>());
        
        getRows().add(row);
        return this;
    }
    
    /**
     * Builds an a basic HTML table
     * @param args
     */
    @Override
    public void build(HTMLBuilderArgs args) {
        
        //open start tag
        args.getHtml().append("<table cellpadding='")
                .append(getCellPadding())
                .append("' cellspacing='")
                .append(getCellSpacing())
                .append("' ");
        
        
        //set id
        if(getId()!=null){
            args.getHtml().append("id='");
            args.getHtml().append(getId());
            args.getHtml().append("' ");
        }
        
        //add style, if exists
        this.addClassToTag(args);        
        
        //close out the opening tag
        args.getHtml().append(">");
        
        //build rows (<TR>)
        for(GridRow row : getRows()){
            row.build(args);
        }
        
        //close table
        args.getHtml().append("</table>");
        
    }

}
