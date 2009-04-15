package com.googlecode.g2re;

import com.googlecode.g2re.domain.DataQuery;
import com.googlecode.g2re.domain.ReportDefinition;
import com.googlecode.g2re.domain.ReportParameter;
import com.googlecode.g2re.excel.ExcelBuilderArgs;
import com.googlecode.g2re.jdbc.DataSet;
import com.googlecode.g2re.util.DataSetUtil;
import com.googlecode.g2re.util.ReportSerializationUtil;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Map;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import jxl.write.WritableWorkbook;

/**
 * Builds an Excel document based on a {@link ReportDefinition}
 * @author Brad Rydzewski
 */
public class ExcelReportBuilder {

    private static final String DEFAULT_SCRIPT_ENGINE = "JavaScript";
    private static final String EXCEL_FORMAT_NOT_FOUND = "An Excel format for this report has not been specified";
    
    ExcelReportBuilder() {
        
    }
    
    /**
     * Builds an Excel file based on the provided report definition and
     * report parameters. Once complete, writes the file to disk. Throws
     * a {@link RuntimeException} if any errors are encountered.
     * @param reportDefinitionXML location report definition file
     * @param params database parameters
     * @param outputFile File name / location for output
     */
    public static void build(File reportDefinitionXML, 
            Map params, 
            File outputFile) {

        OutputStream stream = null;

        try {
            //create the stream
            stream = new FileOutputStream(outputFile);
            build(reportDefinitionXML, params, stream);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        } finally {
            //close the stream
            try {
                stream.close();
            } catch (Exception e) {
            }
        }
    }

    /**
     * Builds an Excel file based on the provided report definition and
     * report parameters. Once complete, writes the file to disk. Throws
     * a {@link RuntimeException} if any errors are encountered.
     * @param reportDefiniton report definition object
     * @param params database parameters
     * @param outputFile File name / location for output
     */
    public static void build(ReportDefinition reportDefiniton, 
            Map params, 
            File outputFile) {
        OutputStream stream = null;

        try {
            //create the stream
            stream = new FileOutputStream(outputFile);
            build(reportDefiniton, params, stream);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        } finally {
            //close the stream
            try {
                stream.close();
            } catch (Exception e) {
            }
        }
    }

    /**
     * Builds an Excel file based on the provided report definition and
     * report parameters. Once complete, writes the file to disk. Throws
     * a {@link RuntimeException} if any errors are encountered.
     * @param reportDefinitonXML location of report definition file
     * @param params database parameters
     * @param stream stream that excel file will be written to
     */
    public static void build(File reportDefinitonXML, Map params, OutputStream stream) {

        /* Load the report from an XML file */
        ReportDefinition reportDefinition =
                ReportSerializationUtil.fromXMLFile(reportDefinitonXML);

        build(reportDefinition, params, stream);
    }

    /**
     * Builds an Excel file based on the provided report definition and
     * report parameters. Once complete, writes the file to disk. Throws
     * a {@link RuntimeException} if any errors are encountered.
     * @param reportDefiniton report definition object
     * @param params database parameters
     * @param stream stream that the Excel file will be written to
     */
    public static void build(ReportDefinition reportDefiniton, 
            Map params, 
            OutputStream stream) {

        //make sure an Excel output has been defined
        if (reportDefiniton.getExcelDocument() == null) {
            throw new RuntimeException(EXCEL_FORMAT_NOT_FOUND);
        }

        //go through and set parameters
        if(params!=null) {
            for(ReportParameter param : reportDefiniton.getReportParameters() ) {

                Object paramValue = params.get(param.getName());
                if(paramValue!=null) {
                    param.setValue(paramValue);
                }
            }
        }
        
        //execute query and get report results
        Dictionary<String, DataSet> results = new Hashtable<String,DataSet>(); //= ResultGeneratorUtil.generateResults(reportDefiniton, params);
        for(DataQuery dq : reportDefiniton.getDataQueries()) {
            DataSet ds = dq.execute();
            ds = DataSetUtil.getFilteredDataSet(ds, dq.getFilters());
            results.put(dq.getName(), ds);
        }
        
        //get the script manager, for custom scripting
        ScriptEngineManager mgr = new ScriptEngineManager();
        ScriptEngine jsEngine = mgr.getEngineByName(DEFAULT_SCRIPT_ENGINE);

        //declar workbook variable
        WritableWorkbook workbook = null;

        try {
            
            //create the workbook
            workbook = jxl.Workbook.createWorkbook(stream);

            //set report args
            ExcelBuilderArgs args = new ExcelBuilderArgs(
                    reportDefiniton,
                    results,
                    jsEngine,
                    stream,
                    workbook);

            //build the report
            reportDefiniton.getExcelDocument().build(args);

            //write the workbook and close
            workbook.write();
            workbook.close();

        } catch (IOException ex) {
            throw new RuntimeException(ex);
        } catch (jxl.write.WriteException ex) {
            throw new RuntimeException(ex);
        }

    }
}
