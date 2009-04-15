package com.googlecode.g2re.html;

import com.googlecode.g2re.html.HTMLBuilderArgs;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a row in a standard HTML table, corespoding to a <tr> tag
 * @author Brad Rydzewski
 */
public class GridRow extends Element {

    private List<GridCell> cells;


    /**
     * Gets a list of cells in a row
     * @return
     */
    public List<GridCell> getCells() {
        return cells;
    }

    /**
     * Sets a list of cells in a ro
     * @param cells
     */
    public void setCells(List<GridCell> cells) {
        this.cells = cells;
    }
    
    /**
     * Adds one or many cells to the row
     * @param cells
     */
    public void addCells(GridCell... cells){
        if(getCells()==null)
            setCells(new ArrayList<GridCell>());
        
        for(GridCell c : cells){
            getCells().add(c);
        }
    }
    
    /**
     * Adds a single cell to the row
     * @param cell
     * @return
     */
    public GridRow addCell(GridCell cell){
        if(getCells()==null)
            setCells(new ArrayList<GridCell>());
        
        getCells().add(cell);
        return this;
    }
    
    @Override
    public void build(HTMLBuilderArgs args) {
        
        args.getHtml().append("<tr");
        this.addClassToTag(args);
        this.addIdToTag(args);
        args.getHtml().append(">");
        
        for(GridCell cell : getCells()){
            cell.build(args);
        }
        
        args.getHtml().append("</tr>");
    }

    
}
