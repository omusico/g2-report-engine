package com.googlecode.g2re.servlet;

import com.googlecode.g2re.domain.ReportDefinition;
import java.io.File;
import java.util.Map;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Brad Rydzewski
 */
public class ServletViewArgs {

    private File reportFile;
    private String reportName;
    private String servletUri;
    private String viewName;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private ServletOutputStream outputStream;
    private Map params;
    private ReportDefinition reportDefinition;
    private Exception expection;

    public ReportDefinition getReportDefinition() {
        return reportDefinition;
    }

    public void setReportDefinition(ReportDefinition reportDefinition) {
        this.reportDefinition = reportDefinition;
    }
    
    

    public File getReportFile() {
        return reportFile;
    }

    public void setReportFile(File reportFile) {
        this.reportFile = reportFile;
    }

    public String getReportName() {
        return reportName;
    }

    public void setReportName(String reportName) {
        this.reportName = reportName;
    }

    public HttpServletRequest getRequest() {
        return request;
    }

    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    public HttpServletResponse getResponse() {
        return response;
    }

    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }

    public String getServletUri() {
        return servletUri;
    }

    public void setServletUri(String servletUri) {
        this.servletUri = servletUri;
    }

    public String getViewName() {
        return viewName;
    }

    public void setViewName(String viewName) {
        this.viewName = viewName;
    }

    public ServletOutputStream getOutputStream() {
        return outputStream;
    }

    public void setOutputStream(ServletOutputStream outputStream) {
        this.outputStream = outputStream;
    }

    public Map getParams() {
        return params;
    }

    public void setParams(Map params) {
        this.params = params;
    }

}
