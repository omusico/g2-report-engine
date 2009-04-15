package com.googlecode.g2re.html;

import com.googlecode.g2re.domain.DataColumn;
import com.googlecode.g2re.domain.DataType;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;

/**
 * Element bound to a data column, able to render the contents
 * of that column, or render a scripted set of data.
 * TODO: Need to add formatting and custom scripting
 * @author Brad Rydzewski
 */
public class DataElement extends Element implements Comparable<DataElement> {

    private boolean scriptable;
    private String script;
    private DataColumn value;
    private int order = 0;
    private String numberFormat = "";
    private String dateFormat = "";
    
    //private NumberFormat numberFormatter;
    //private DateFormat dateFormatter;

    public DataElement(){
        
    }
    public DataElement(DataColumn value, int order){
        setValue(value);
        setOrder(order);
    }
    public DataElement(String script, int order){
        setScript(script);
        setOrder(order);
        setScriptable(true);
    }

    public String getDateFormat() {
        return dateFormat;
    }

    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }

    public String getNumberFormat() {
        return numberFormat;
    }

    public void setNumberFormat(String numberFormat) {
        this.numberFormat = numberFormat;
    }
    
    
    
    public boolean isScriptable() {
        return scriptable;
    }

    public void setScriptable(boolean scriptable) {
        this.scriptable = scriptable;
    }

    public DataColumn getValue() {
        return value;
    }

    public void setValue(DataColumn value) {
        this.value = value;
    }    

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getScript() {
        return script;
    }

    public void setScript(String script) {
        this.script = script;
    }
    
    
    
    @Override
    public void build(HTMLBuilderArgs args) {
        
        
        
        String rendering;
        
        if(assertCustomScripted()){
            rendering = getScriptValue(args).toString();
        } else {

            
            //check if date, then format
            if(assertDateCell() && getDateFormat().isEmpty()==false) {
                DateFormat dateFormatter = new SimpleDateFormat(getDateFormat());
                rendering = dateFormatter.format(
                        args.getCurrentDataRow()[getValue().getOrder()]);
            }
            //check if number, then format
            else if(assertNumberCell() && getNumberFormat().isEmpty()==false) {
                NumberFormat numberFormatter = new DecimalFormat(getNumberFormat());
                rendering = numberFormatter.format(
                        args.getCurrentDataRow()[getValue().getOrder()]); 
            }
            else {
                rendering = 
                    args.getCurrentDataRow()[getValue().getOrder()].toString();
            }
        }
        
        args.getHtml().append(rendering);
    }
    
    /**
     * Asserts if a Cell should be built using a user-defined script
     * @return
     */
    boolean assertCustomScripted() {
        return isScriptable() && getScript() != null;
    }    

    /**
     * Gets a cell value based on a user defined script. Uses the ScriptEngine
     * eval function, currently only supporting the JavaScript language
     * @param args
     * @return
     */
    Object getScriptValue(HTMLBuilderArgs args) {

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
     * Compares two DataElement based on their assigned Orders
     * @param anotherDataElement
     * @return
     */
    @Override
    public int compareTo(DataElement anotherDataElement) {
	int thisVal = this.getOrder();
	int anotherVal = anotherDataElement.getOrder();
	return (thisVal<anotherVal ? -1 : (thisVal==anotherVal ? 0 : 1));
    }
    

    /**
     * Asserts a cell is in date format
     * @param value
     * @return
     */
    boolean assertDateCell() {
        if(getValue()==null)return false;
        return getValue().getType() == DataType.DATE;
    }

    /**
     * Asserts a cell is in Number format
     * @param value
     * @return
     */
    boolean assertNumberCell() {
        if(getValue()==null)return false;

        switch (getValue().getType()) {
            case DOUBLE:
            case FLOAT:
            case INTEGER:
                return true;
            default:
                return false;
        }
    }

}
