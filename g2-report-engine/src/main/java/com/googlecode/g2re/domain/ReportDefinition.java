package com.googlecode.g2re.domain;

import com.googlecode.g2re.excel.Workbook;
import com.googlecode.g2re.html.WebPage;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Defines a Report including data sources, parameters, output and formatting
 * @author Brad Rydzewski
 */
public class ReportDefinition {


    private String name;
    private Date created = new Date();
    private Date updated = new Date();
    private String description;
    private String author;
    private List<JdbcConnection> dataConnections = new ArrayList<JdbcConnection>();
    private List<ReportParameter> reportParameters = new ArrayList<ReportParameter>();
    private List<DataQuery> dataQueries = new ArrayList<DataQuery>();
    private Workbook excelWorkbook = null;
    private WebPage htmlWebPage = new WebPage();


    public WebPage getWebPage() {
        return htmlWebPage;
    }

    public void setWebPage(WebPage htmlDocument) {
        this.htmlWebPage = htmlDocument;
    }
    
    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public List<JdbcConnection> getDataConnections() {
        return dataConnections;
    }

    public void setDataConnections(List<JdbcConnection> dataConnections) {
        this.dataConnections = dataConnections;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ReportParameter> getReportParameters() {
        return reportParameters;
    }

    public void setReportParameters(List<ReportParameter> reportParameters) {
        this.reportParameters = reportParameters;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public List<DataQuery> getDataQueries() {
        return dataQueries;
    }

    public void setDataQueries(List<DataQuery> dataQueries) {
        this.dataQueries = dataQueries;
    }

  

    public Workbook addExcelDocument(){
        excelWorkbook = new Workbook();
        return excelWorkbook;
    }
    
    public Workbook getExcelDocument() {
        return excelWorkbook;
    }

    public void setExcelDocument(Workbook excelWorkbook) {
        this.excelWorkbook = excelWorkbook;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
    
    
}
