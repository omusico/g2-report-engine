package com.googlecode.g2re.excel;

import com.googlecode.g2re.excel.ExcelBuilderArgs;
import com.googlecode.g2re.domain.JdbcQuery;
import jxl.write.WritableSheet;

/**
 * Represents a grid of columns and rows, contained as a "page" in a
 * Microsoft Excel workbook.
 * @author Brad
 */
public class Worksheet implements Comparable<Worksheet> {

    private int order = 0;
    private String name;
    private Table table;
    //private boolean locked = false;
    //private boolean hidden = false;
    //private boolean selected = false;
    //private PageOrientation pageOrientation = PageOrientation.PORTRAIT;
    
    
    public Worksheet(){

    }
    public Worksheet(String name,int order){
        setName(name);
        setOrder(order);
    }
    public Worksheet(String name, int order, Table table){
        setName(name);
        setTable(table);
        setOrder(order);
    }
    
    /**
     * Gets the name of the Worksheet (ie. Sheet1)
     * @return
     */
    public String getName() {
        return name;
    }

    /** 
     * Sets the name of the Worksheet
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the worksheet's table, responsible
     * for displaying all data in a tabular format
     * @return
     */
    public Table getTable() {
        return table;
    }

    /**
     * Sets the worksheets table, responsible
     * for displaying all data in a tabular format
     * @param table
     */
    public void setTable(Table table) {
        this.table = table;
    }
    
    /**
     * Provides a simple facade for adding a new table to the worksheet
     * using the given DataSet.
     * @param dataSet binding the table
     * @return Table added to the Worksheet
     */
    public Table addTable(JdbcQuery dataSet){
        Table newTable = new Table(dataSet);
        setTable(newTable);
        return newTable;
    }

    /**
     * Gets the order in which the worksheet will appear in the workbook
     * @return
     */
    public int getOrder() {
        return order;
    }

    /**
     * Sets the order in which the worksheet will appear in the workbook
     * @param order
     */
    public void setOrder(int order) {
        this.order = order;
    }
    
    
    
    /**
     * Builds an Excel worksheet and all child items (ie Data Tables)
     * @param args
     */
    public void build(ExcelBuilderArgs args) {
        

        //reset arg values (row,column)
        args.setColumn(0);
        args.setRow(0);
        
        //for now, a worksheet can only have one data table...
        //if it isn't null, lets add it to the build
        if(getTable()!=null){
            getTable().build(args);
        }
    }
    
    /**
     * Compares two Worksheets based on their assigned Orders
     * @param anotherCell
     * @return
     */
    @Override
    public int compareTo(Worksheet anotherCell) {
	int thisVal = this.getOrder();
	int anotherVal = anotherCell.getOrder();
	return (thisVal<anotherVal ? -1 : (thisVal==anotherVal ? 0 : 1));
    }
}
