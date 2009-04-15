package com.googlecode.g2re;

import com.googlecode.g2re.domain.DataQuery;
import com.googlecode.g2re.domain.ReportDefinition;
import com.googlecode.g2re.domain.ReportParameter;
import com.googlecode.g2re.html.HTMLBuilderArgs;
import com.googlecode.g2re.jdbc.DataSet;
import com.googlecode.g2re.util.ReportSerializationUtil;
import com.googlecode.g2re.html.WebPage;
import com.googlecode.g2re.util.DataSetUtil;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Map;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

/**
 * Required methods for taking a ReportDefinition and turning it into
 * an HTML-based report.
 * @author Brad Rydzewski
 */
public class HTMLReportBuilder {

    HTMLReportBuilder (){
        
    }
    
    public static void build(File reportDefinitionXML, Map params, File reportResultsFile) {

        BufferedWriter out = null;

        try {

            //get report results
            String results = build(reportDefinitionXML, params);

            //write to file
            out = new BufferedWriter(new FileWriter(reportResultsFile));
            out.write(results);

        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {

            //close output stream
            try {
                out.close();
            } catch (Exception ex) {}
        }
    }

    /**
     * Builds a report given a ReportDefinition File. The File will be
     * parsed and serialized to a ReportDefinition object. Then, it executes
     * all required queries and then builds the report. A report will
     * be returned in HTML format, as a string
     * @param reportDefinitonXML Definition of report to be rendered, stored in a File
     * @param params Parameters (ie JDBC) to pass into the report (Optional)
     * @return
     */
    public static String build(File reportDefinitonXML, Map params) {

        /* Load the report from an XML file */
        ReportDefinition reportDefinition =
                ReportSerializationUtil.fromXMLFile(reportDefinitonXML);

        return build(reportDefinition, params);
    }

    /**
     * Builds a report given a ReportDefinition object. It executes
     * all required queries and then builds the report. A report will
     * be returned in HTML format, as a string
     * @param reportDefinition Definition of report to be rendered
     * @param params Parameters (ie JDBC) to pass into the report (Optional)
     * @return report in HTML format
     */
    public static String build(ReportDefinition reportDefinition, Map params) {

        
        //make sure an Excel output has been defined
        if(reportDefinition.getWebPage()==null){
            throw new RuntimeException("An HTML format for this report has not been specified");
        }
        
        //go through and set parameters
        if(params!=null) {
            for(ReportParameter param : reportDefinition.getReportParameters() ) {

                Object paramValue = params.get(param.getName());
                if(paramValue!=null) {
                    param.setValue(paramValue);
                }
            }
        }
        
        //execute query and get report results
        Dictionary<String,DataSet> results = new Hashtable<String,DataSet>(); //= ResultGeneratorUtil.generateResults(reportDefinition, params);
        for(DataQuery dq : reportDefinition.getDataQueries()) {

            
            DataSet ds = dq.execute();
            ds = DataSetUtil.getFilteredDataSet(ds, dq.getFilters());
            results.put(dq.getName(), ds);
        }
        
        //get the script manager, for custom scripting
        ScriptEngineManager mgr = new ScriptEngineManager();
        ScriptEngine jsEngine = mgr.getEngineByName("JavaScript");

        
        //set report args
        HTMLBuilderArgs args = new HTMLBuilderArgs(reportDefinition,
                results,jsEngine);
                
        
        //get document and render
        WebPage document = reportDefinition.getWebPage();
        
        //build and return output
        return document.build(args);


        //append open doctype, html tag and head tag
        /*
        htmlDocument.append("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">\n");
        htmlDocument.append("<html>\n");
        htmlDocument.append(" <head>\n");

        //render scripts that need to be imported, using <script/>
        String[] scriptFiles = new String[args.getScriptFiles().size()];
        args.getScriptFiles().toArray(scriptFiles);
        for (int i = 0; i < scriptFiles.length; i++) {
            htmlDocument.append(" <script src='");
            htmlDocument.append(scriptFiles[i]);
            htmlDocument.append("'></script>\n");
        }

        //render modules (good visualization) that need to be imported, using google.load
        String[] modules = new String[args.getGoogleModules().size()];
        args.getGoogleModules().toArray(modules);

        //if there are moduels to load, we'll load them and add js init()
        if (modules.length > 0) {
            htmlDocument.append(" <script>\n");
            htmlDocument.append("  google.setOnLoadCallback(init);\n");
            htmlDocument.append("  google.load('visualization', '1', {'packages':[");
            for (int i = 0; i < modules.length; i++) {
                htmlDocument.append("'").append(modules[i]).append("'");
                if (i < modules.length - 1) {
                    htmlDocument.append(",");
                }
            }
            htmlDocument.append("]});\n");
        }
        //finish rendering modules

        //create init method. 
        //this is where any required java script gets executed
        htmlDocument.append("  function init(){ \n");
        htmlDocument.append(args.getPreScript()).append("\n");
        htmlDocument.append("  }\n");
        htmlDocument.append(" </script>\n");
        htmlDocument.append(" </head>\n");

        //create html body
        htmlDocument.append("<body>\n");
        htmlDocument.append(args.getHtml());
        htmlDocument.append("</body>\n");

        //append close HTML tag
        htmlDocument.append("</html>");

        //return the created HTML document
        return htmlDocument.toString
         * */
    }
}
