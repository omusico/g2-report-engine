package com.googlecode.g2re.excel;

import com.googlecode.g2re.domain.DataQuery;
import com.googlecode.g2re.excel.ExcelBuilderArgs;
import com.googlecode.g2re.domain.JdbcQuery;
import com.googlecode.g2re.jdbc.DataSet;
import java.util.List;

/**
 * A Representation of Columns and Rows to display data in a grid-like format.
 * Typically, a table will be bound to a {@link DataSet} to build all data
 * from a dynamic data source (ie database SQL query). A data table 
 * also contains header and footer rows, as well as basic formatting.
 * @author Brad Rydzewski
 */
public class Table {

    private Row headerRow;
    private Row footerRow;
    private Row bodyRow;
    private DataQuery dataSet;
    
    public Table(){
        
    }
    public Table (DataQuery dataSet){
        setDataSet(dataSet);
    }

    /**
     * Gets the DataSet bound to the table, used for rendering data in
     * a tabular format.
     * @return
     */
    public DataQuery getDataSet() {
        return dataSet;
    }

    /**
     * Sets the DataSet bound to the table, used for rendering data in
     * a tabular format.
     * @param dataSet
     */
    public void setDataSet(DataQuery dataSet) {
        this.dataSet = dataSet;
    }

    /**
     * gets the table's body row, containing all data-bound cells
     * @return
     */
    public Row getBodyRow() {
        return bodyRow;
    }

    /**
     * sets the tables body row, containing all data-bound cells
     * @param bodyRow
     */
    public void setBodyRow(Row bodyRow) {
        this.bodyRow = bodyRow;
    }

    /**
     * Gets the footer row - the last row in the table typically used for
     * aggregations
     * @return
     */
    public Row getFooterRow() {
        return footerRow;
    }

    /**
     * Sets the footer row - the last row in the table, typically
     * used for aggregations
     * @param footerRow
     */
    public void setFooterRow(Row footerRow) {
        this.footerRow = footerRow;
    }

    /**
     * Gets the Header row - the first row in the table typically used
     * for column headers (ie Labels)
     * @return
     */
    public Row getHeaderRow() {
        return headerRow;
    }

    /**
     * Sets the Header row - the first row in the table typically used
     * for column headers (ie Labels)
     * @param headerRow
     */
    public void setHeaderRow(Row headerRow) {
        this.headerRow = headerRow;
    }
    
    /**
     * Provides a simple Facade to easily add a {@link Cell} to a 
     * header {@link Row}
     * @param cell
     * @return Table
     */
    public Table addHeaderRowCell(Cell... cell){
        
        if(headerRow==null){
            headerRow = new Row();
        }
        
        for(Cell c : cell){
            headerRow.addCell(c);
        }
        
        return this;
    }
    
    /**
     * Provides a simple facade to easiloy add a {@link Cell} to a 
     * footer {@link Row}
     * @param cell
     * @return Table
     */
    public Table addFooterRowCell(Cell... cell){
        
        if(footerRow==null){
            footerRow = new Row();
        }
        
        for(Cell c : cell){
            footerRow.addCell(c);
        }
        
        return this;
    }

    /**
     * Provides a simple Facade to easily add a {@link Cell} to a 
     * body {@link Row}
     * @param cell
     * @return Table
     */
    public Table addBodyRowCell(Cell... cell){
        
        if(bodyRow==null){
            bodyRow = new Row();
        }
        
        for(Cell c : cell){
            bodyRow.addCell(c);
        }
        
        return this;
    }
    
    /**
     * Builds a tabular data set (with headers, body and footer rows) into an
     * Excel worksheet
     * @param args
     */
    public void build(ExcelBuilderArgs args) {
        
        //reset rows and column to 0,0
        args.resetRowsAndColumns();
        
        //get the data set
        String queryName = getDataSet().getName();
        DataSet resultDataSet = args.getResults().get(queryName);
        List resultList = resultDataSet.getRows();
        
        //set the data set as the current data set in the event args
        //that way it can be referenced by any data-bound children of this element
        args.setCurrentDataSet(resultDataSet);
        
        //render the header row
        //for now we only have a single header row
        if(getHeaderRow()!=null){
            args.setCurrentDataRow(null);
            getHeaderRow().sortCells();
            getHeaderRow().build(args);
            args.incrementRow();
        }
        
        //sort the data rows
        getBodyRow().sortCells();
        
        //render the data rows
        for(int i=0;i<resultList.size();i++){
            args.setCurrentDataRow(
                    (Object[])resultList.get(i));
            
            getBodyRow().build(args);
            args.incrementRow();
        }
        
        //render the footer row
        //for now we only have a single footer row
        if(getFooterRow()!=null){
            args.setCurrentDataRow(null);
            getFooterRow().sortCells();
            getFooterRow().build(args);
            args.incrementRow();
        }    
    }
}
