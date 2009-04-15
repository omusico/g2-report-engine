package com.googlecode.g2re;

import com.googlecode.g2re.domain.ReportDefinition;
import com.googlecode.g2re.domain.ReportFormat;
import com.googlecode.g2re.jdbc.DataSet;
import java.util.Dictionary;
import javax.script.ScriptEngine;

/**
 * Abstract class to provide basic fields for building a user-defined report.
 * @author Brad Rydzewski
 */
public abstract class BuilderArgs {

    private DataSet currentDataSet;
    private Object[] currentDataRow;
    private ScriptEngine scriptEngine;
    private Dictionary<String,DataSet> results;
    private ReportDefinition reportDefinition; 
    
    public BuilderArgs(ReportDefinition reportDefinition,
            Dictionary<String, DataSet> results,
            ScriptEngine scriptEngine) {
        
        this.scriptEngine = scriptEngine;
        this.results = results;
        this.reportDefinition = reportDefinition;
    }
    
    public ReportDefinition getReportDefinition(){
        return this.reportDefinition;
    }

    public Dictionary<String, DataSet> getResults(){
        return this.results;
    }  
    
    /**
     * Gets the DataSet currently being processed by the build engine
     * @return
     */
    public DataSet getCurrentDataSet() {
        return currentDataSet;
    }

    /**
     * Sets the DataSet currently being processed by the build engine
     * @param currentDataSet
     */
    public void setCurrentDataSet(DataSet currentDataSet) {
        this.currentDataSet = currentDataSet;
    }

    /**
     * Gets the DataRow currently being processed by the build engine
     * @return
     */
    public Object[] getCurrentDataRow() {
        return currentDataRow;
    }

    /**
     * Sets the DataRow currently being processed by the build engine
     * @param dataRow
     */
    public void setCurrentDataRow(Object[] dataRow) {
        this.currentDataRow = dataRow;
    }

    /**
     * Gets the script engine to be used for custom scripting
     * @return
     */
    public ScriptEngine getScriptEngine() {
        return scriptEngine;
    }
    
    /**
     * Indicates the {@link ReportFormat} type being built, IE excel, pdf
     * and HTML.
     * @return
     */    
    public abstract ReportFormat getFormat();
}
   