package com.googlecode.g2re.excel;

import com.googlecode.g2re.excel.ExcelBuilderArgs;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A Row defines a horizontal collection of cells to be displayed in a
 * {@link Table}.
 * @author Brad Rydzewski
 */
public class Row {

    private List<Cell> cells;

    /**
     * Gets the list of Cells in the row
     * @return
     */
    public List<Cell> getCells() {
        return cells;
    }

    /**
     * Sets the lst of cells in the row
     * @param cells
     */
    public void setCells(List<Cell> cells) {
        this.cells = cells;
    }
    
    /**
     * Provides a simple facade to add Cells to a row. If the List
     * of cells is null, a new List is created to avoid a 
     * {@link NullPointerException}
     * @param cell
     */
    public void addCell(Cell cell){
        if(getCells() == null){
            setCells(new ArrayList<Cell>());
        }
        
        getCells().add(cell);
    }
    
    /**
     * Builds a row in a tabular data set. Renders all cells, in multiple
     * formats including Formulas, Numbers, Text and more
     * @param args
     */
    public void build(ExcelBuilderArgs args) {
        
        //reset the column
        //for now only 1 data table per worksheet, so by default, 
        // it always starts in column A
        args.setColumn(0);
        
        //render each row cell
        for(int i=0;i<getCells().size();i++){
            
            getCells().get(i).build(args);
            
            //increment the column, so that the next cell rendered will be
            // in colun B, C, D, etc
            args.incrementColumn();
        }
    }
    
    public void sortCells(){
        Collections.sort(getCells());
    }
}
