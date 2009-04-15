package com.googlecode.g2re.html;

import com.googlecode.g2re.*;
import com.googlecode.g2re.domain.ReportDefinition;
import com.googlecode.g2re.domain.ReportFormat;
import com.googlecode.g2re.jdbc.DataSet;
import java.util.Dictionary;
import java.util.HashSet;
import java.util.Set;
import javax.script.ScriptEngine;

/**
 * Provides an element with details required to Build an HTML file
 * from a {@link ReportDefinition}
 * @author Brad Rydzewski
 */
public class HTMLBuilderArgs extends BuilderArgs {

   
    private StringBuilder html;
    private StringBuilder preScript;
    private StringBuilder postScript;
    private Set<String> googleModules;
    private Set<String> scriptFiles;
    private int sequence;
    private static final ReportFormat REPORT_FORMAT = ReportFormat.HTML;

    public HTMLBuilderArgs(ReportDefinition reportDefinition,
            Dictionary<String, DataSet> results,
            ScriptEngine scriptEngine) {
        
        super(reportDefinition,results,scriptEngine);
        init();
    }

    void init() {
        html = new StringBuilder();
        preScript = new StringBuilder();
        postScript = new StringBuilder();
        googleModules = new HashSet<String>();
        scriptFiles = new HashSet<String>();
        sequence = 0;
    }

    /**
     * Gets a list of Google Visualization modules to be loaded
     * by the Report
     * @return
     */
    public Set<String> getGoogleModules() {
        return googleModules;
    }

    /**
     * Gets a String of HTML characters representing the body of an HTML-
     * formatted report
     * @return
     */
    public StringBuilder getHtml() {
        return html;
    }

    /**
     * Gets a block of javascript to be executed at the end of the page,
     * after the body tag is closed
     * @return
     */
    public StringBuilder getPostScript() {
        return postScript;
    }

    /**
     * Gets a block of javascript to be executed on page initialization
     * @return
     */
    public StringBuilder getPreScript() {
        return preScript;
    }

    /**
     * Gets a list of Javascript files to be loaded into the page,
     * using the the script tag, ex: {@code &#60;script src=[your file] /&#62;}
     * @return
     */
    public Set<String> getScriptFiles() {
        return scriptFiles;
    }

    /**
     * Gets a sequence number, which is unique and incremented each time
     * it is requested
     * @return
     */
    public int getSequence() {
        sequence += 1;
        return sequence;
    }

    /**
     * Gets the Format of the Report (ie HTML)
     * @return Returns the ReportFormat
     */
    @Override
    public ReportFormat getFormat() {
        return REPORT_FORMAT;
    }
}
