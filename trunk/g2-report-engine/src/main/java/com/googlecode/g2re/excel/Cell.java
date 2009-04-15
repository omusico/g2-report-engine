package com.googlecode.g2re.excel;

import com.googlecode.g2re.excel.ExcelBuilderArgs;
import com.googlecode.g2re.domain.DataColumn;
import com.googlecode.g2re.domain.DataType;
import java.util.Date;
import jxl.write.WritableCell;

/**
 * A cell is the intersection of a column and row in a tabular data set,
 * containing some type of data or value. In Excel, a cell can contain
 * multiple value types, such as dates, numbers, strings and formulas. This
 * base class defines the required attributes for all types of cells.
 * @author Brad Rydzewski
 */
public class Cell implements Comparable<Cell> {

    private CellFormat format;
    private CellStyle style;
    private CellType type = CellType.STATIC_TEXT;
    private boolean scriptable = false;
    private String script;
    private String hyperlink;
    private String formula;
    private String text;
    private DataColumn dataCell;
    private int order = 0;

    public Cell() {
    }
    
    public Cell (String text, String script, int order){
        assert(text!=null);
        assert(script!=null);
        this.setScript(script);
        this.setScriptable(!script.isEmpty());
        this.setText(text);
        this.setType(CellType.STATIC_TEXT);
        this.setOrder(order);
    }
    
    public Cell (DataColumn dataCell, String script, int order){
        assert(dataCell!=null);
        assert(script!=null);
        this.setScript(script);
        this.setScriptable(!script.isEmpty());
        this.setDataCell(dataCell);
        this.setType(CellType.DATABOUND_TEXT);
        this.setOrder(order);
    }

    /**
     * Gets the order of the cell determining what column in the grid it
     * appears in
     * @return
     */
    public int getOrder() {
        return order;
    }

    /**
     * Sets the order of the cell determining what column in the grid it
     * appears in
     * @param order
     */
    public void setOrder(int order) {
        this.order = order;
    }

    /**
     * Gets the script used to render the cell. Only applicable
     * if the scriptable attribute is set to true.
     * @return
     */
    public String getScript() {
        return script;
    }

    /**
     * Sets the script used to render the cell. Only applicable
     * if the scriptable attribute is set to true.
     * @param script
     */
    public void setScript(String script) {
        this.script = script;
    }

    /**
     * Gets if the cell is "scriptable", meaning the 
     * {@link javax.script.ScriptEngine} will be used to 
     * render the contents of the cell
     * @return
     */
    public boolean isScriptable() {
        return scriptable;
    }

    /**
     * Sets if the cell is "scriptable", meaning the 
     * {@link javax.script.ScriptEngine} will be used to render 
     * the contents of the cell
     * @param scriptable
     */
    public void setScriptable(boolean scriptable) {
        this.scriptable = scriptable;
    }

    /**
     * Gets the Style used to format the Cell
     * @return
     */
    public CellStyle getStyle() {
        return style;
    }

    /**
     * Sets the Style used to format the cell
     * @param style
     */
    public void setStyle(CellStyle style) {
        this.style = style;
    }

    /**
     * Gets the type of Cell being rendered, ie Static Text, Dynamic Text,
     * Formula, Hyperlink, etc
     * @return
     */
    public CellType getType() {
        return type;
    }

    /**
     * Sets the type of Cell being rendered, ie Static Text, Dynamic Text,
     * Formula, Hyperlink, etc
     * @param type
     */
    public void setType(CellType type) {
        this.type = type;
    }

    /**
     * Gets the Format used to render the Cell (ie Number, Date, etc)
     * @return
     */
    public CellFormat getFormat() {
        return format;
    }

    /**
     * Sets the Format used to render the Cell (ie Number, Date, etc)
     * @param format
     */
    public void setFormat(CellFormat format) {
        this.format = format;
    }

    /**
     * Gets the DataColumn to which the Cell is bound.
     * This is only used when the CellType is set to DATABOUND_TEXT
     * @return
     */
    public DataColumn getDataCell() {
        return dataCell;
    }

    /**
     * Sets the DataColumn to which the Cell is bound.
     * This is only used when the CellType is set to DATABOUND_TEXT
     * @param dataCell
     */
    public void setDataCell(DataColumn dataCell) {
        this.dataCell = dataCell;
    }

    /**
     * Gets plain text to be inserted into the Cell.
     * This is only used when the CellType is set to STATIC_TEXT
     * @return
     */
    public String getText() {
        return text;
    }

    /**
     * Sets plain text to be inserted into the Cell.
     * This is only used when the CellType is set to STATIC_TEXT
     * @param text
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * Gets an Excel based formula (ie VBA) to be inserted into the Cell.
     * This is only used when the CellType is set to FORMULA
     * @return
     */
    public String getFormula() {
        return formula;
    }

    /**
     * Sets an Excel based formula (ie VBA) to be inserted into the Cell.
     * This is only used when the CellType is set to FORMULA
     * @param formula
     */
    public void setFormula(String formula) {
        this.formula = formula;
    }

    /**
     * Gets the Hyperlink to be rendered in the Cell (ie http://www.google.com).
     * This is only used when the CellType is set to HYPERLINK
     * @return
     */
    public String getHyperlink() {
        return hyperlink;
    }

    /**
     * Sets the Hyperlink to be rendered in the Cell (ie http://www.google.com).
     * This is only used when the CellType is set to HYPERLINK
     * @param hyperlink
     */
    public void setHyperlink(String hyperlink) {
        this.hyperlink = hyperlink;
    }

    /**
     * Builds a cell in a row of a tabular data set. Determines the cell 
     * type, format and style and adds it to the Excel worksheet
     * @param args
     */
    public void build(ExcelBuilderArgs args) {

        //cell value stored here
        Object value = null;
        //jexcel "cell" object
        WritableCell cell = null;

        //is this custom scripted
        if (assertCustomScripted()) {
            //set cell value from script
            value = getScriptValue(args);
        } else {
            //set cell value based on cell type
            // ie formula, hyperlink, text, etc
            value = getCellValue(args);
        }

        //gets a cell based on the cell type
        if (assertNullCellValue(value)) {
            cell = getEmptyCell(args);
        } else if (assertNumberCell(value)) {
            cell = getWritableNumberCellFromValue(value, args);
        } else if (assertDateCell(value)) {
            cell = getWritableDateCellFromValue(value, args);
        } else {
            cell = getWritableCellFromValue(value, args);
        }

        //adds the cell to the worksheet
        try {
            args.getWorksheet().addCell(cell);
        } catch (jxl.write.WriteException ex) {
            //re-throw any exceptions
            throw new RuntimeException(ex);
        }
    }

    /**
     * Gets a cell value based on a user defined script. Uses the ScriptEngine
     * eval function, currently only supporting the JavaScript language
     * @param args
     * @return
     */
    Object getScriptValue(ExcelBuilderArgs args) {

        try {
            //add arguments to the script engine for script access
            args.getScriptEngine().put("row", args.getCurrentDataRow());
            //run the script and get the value
            return args.getScriptEngine().eval(getScript());

        } catch (javax.script.ScriptException ex) {
            //re-throw error
            throw new RuntimeException(ex);
        }
    }

    /**
     * Gets a cell value based on the cell type. For example,
     * for a static cell it will return getText(), for a formula it
     * will return getFormula(), etc
     * @param args
     * @return
     */
    Object getCellValue(ExcelBuilderArgs args) {

        Object value = null;

        switch (getType()) {
            case STATIC_TEXT:
                value = getText(); break;
            case FORMULA:
                value = getFormula(); break;
            case HYPERLINK:
                value = getHyperlink(); break;
            default:
                value = args.getCurrentDataRow()[getDataCell().getOrder()];
        }

        return value;
    }

    /**
     * Gets an empty cell
     * @param args required to provide method with grid position (row & column)
     * @return
     */
    WritableCell getEmptyCell(ExcelBuilderArgs args) {
        return new jxl.write.Blank(args.getColumn(), args.getRow());
    }

    /**
     * Gets a Number-formatted cell
     * @param value
     * @param args
     * @return
     */
    WritableCell getWritableNumberCellFromValue(Object value, ExcelBuilderArgs args) {
        try {
            Double d = Double.parseDouble(value.toString());
            return new jxl.write.Number(args.getColumn(), args.getRow(), d);
        } catch (Exception ex) {
            //if it can't be parsed to a double lets just make it a Label
            return new jxl.write.Label(args.getColumn(), args.getRow(), value.toString());
        }
    }

    /**
     * Gets a Date-formatted column
     * @param value
     * @param args
     * @return
     */
    WritableCell getWritableDateCellFromValue(Object value, ExcelBuilderArgs args) {
        if (value instanceof Date) {
            return new jxl.write.DateTime(args.getColumn(), args.getRow(), (Date) value);
        } else {
            //if it isn't a valid date, let's make it a Label
            return new jxl.write.Label(args.getColumn(), args.getRow(), value.toString());
        }
    }

    /**
     * Gets a boolean formatted column ... not yet implemented. Not
     * quite sure how to handle this since in the database Yes / No are typically
     * stored as 1 and 0 or Y and N or YES and NO, etc. Need a strategy here
     * @param value
     * @param args
     * @return
     */
    WritableCell getWritableBooleanCellFromValue(Object value, ExcelBuilderArgs args) {
        throw new RuntimeException("getWritableBooleanCellFromValue method not yet implemented");
    }

    /**
     * Gets a Column based on the Cell Type specified. For a formula it 
     * returns a Formula cell, for simple text a Label, etc
     * @param value
     * @param args
     * @return
     */
    WritableCell getWritableCellFromValue(Object value, ExcelBuilderArgs args) {

        switch (getType()) {
            case HYPERLINK:  //NOTE: oops, i screwed up, there is no "hyperlink" column
            case STATIC_TEXT:
                return new jxl.write.Label(args.getColumn(), args.getRow(), (String) value);
            case FORMULA:
                return new jxl.write.Formula(args.getColumn(), args.getRow(), (String) value);
            default:
                return new jxl.write.Label(args.getColumn(), args.getRow(), value.toString());
        }

    }

    /**
     * Asserts if a Cell should be built using a user-defined script
     * @return
     */
    boolean assertCustomScripted() {
        return isScriptable() && getScript() != null;
    }

    /**
     * Asserts if a cell's value is null
     * @param value
     * @return
     */
    boolean assertNullCellValue(Object value) {
        return value == null;
    }

    /**
     * Asserts a cell is in date format
     * @param value
     * @return
     */
    boolean assertDateCell(Object value) {
        
        if(getType() != CellType.DATABOUND_TEXT)
            return false;
        
        return getDataCell().getType() == DataType.DATE;
    }

    /**
     * Asserts a cell is in Number format
     * @param value
     * @return
     */
    boolean assertNumberCell(Object value) {

        if (getType() != CellType.DATABOUND_TEXT) {
            return false;
        }
        switch (getDataCell().getType()) {
            case DOUBLE:
            case FLOAT:
            case INTEGER:
                return true;
            default:
                return false;
        }
    }

    /**
     * Compares two Cell based on their assigned Orders
     * @param anotherCell
     * @return
     */
    @Override
    public int compareTo(Cell anotherCell) {
	int thisVal = this.getOrder();
	int anotherVal = anotherCell.getOrder();
	return (thisVal<anotherVal ? -1 : (thisVal==anotherVal ? 0 : 1));
    }
    
    
}
