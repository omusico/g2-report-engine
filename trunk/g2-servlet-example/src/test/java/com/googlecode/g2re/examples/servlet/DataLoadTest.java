package com.googlecode.g2re.examples.servlet;

import com.googlecode.g2re.HTMLReportBuilder;
import com.googlecode.g2re.domain.DataColumn;
import com.googlecode.g2re.domain.DataType;
import com.googlecode.g2re.domain.JdbcConnection;
import com.googlecode.g2re.domain.JdbcParameter;
import com.googlecode.g2re.domain.JdbcQuery;
import com.googlecode.g2re.domain.ReportDefinition;
import com.googlecode.g2re.domain.ReportParameterTextBox;
import com.googlecode.g2re.html.DataElement;
import com.googlecode.g2re.html.DataTable;
import com.googlecode.g2re.html.GridRow;
import com.googlecode.g2re.html.RawHTML;
import com.googlecode.g2re.html.google.Map;
import com.googlecode.g2re.html.google.MapType;
import com.googlecode.g2re.util.ReportSerializationUtil;
import java.io.File;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.h2.tools.*;

/**
 *
 * @author Brad Rydzewski
 */
public class DataLoadTest {

    private PetStoreDataManager dataManager = null;
    //private static final String SCHEMA_FILE = "src\\main\\resources\\jpetstore-hsqldb-schema.sql";
    private static final String DATA_FILE = "src\\main\\resources\\petstore.sql";
    private static final String CLASS_NAME = "org.h2.Driver";//org.hsqldb.jdbcDriver";
    private static final String DB_URL = "jdbc:h2:mem:test-db;DB_CLOSE_DELAY=-1";//"jdbc:hsqldb:mem:.;";//default_schema=true;shutdown=false;";//ifexists=true;type=cached;";//C:\\SVN\\g2-report-engine\\g2-servlet-example\\test-db;default_schema=true;shutdown=false;ifexists=true;";//type=cached;ifexists=false;shutdown=false";
    private static final String DB_USER = "sa";
    private static final String DB_PW = "";
    
    private static final String PET_LOCATION_RPT_PATH = "src\\main\\resources\\PetLocationReport.rxml";
    private Server server = null;
    @Before
    public void setUp() {
        dataManager = new PetStoreDataManager();
        
        dataManager.openConnection(DB_URL, DB_USER, DB_PW, CLASS_NAME);
        //dataManager.loadFile(new File(SCHEMA_FILE));
        dataManager.loadFile(new File(DATA_FILE));
        //org.hsqldb.Server.main(arg0);

    }

    @After
    public void tearDown() {
        dataManager.closeConnection();
        //server.stop();
    }
    
    public JdbcConnection getJdbcConnection() {

        /* Create the first data source */
        JdbcConnection source1 = new JdbcConnection();
        source1.setName("PetStoreDataSource");
        source1.setDatabaseJndiName("jndi/PetStoreDataSource");
        source1.setDatabaseUser(DB_USER);
        source1.setDatabasePassword(DB_PW);
        source1.setDriverClass(CLASS_NAME);
        source1.setDatabaseUrl(DB_URL);

        return source1;
    }
    
    public ReportDefinition getPetLocationReport() {

        /* creat report */
        ReportDefinition report = new ReportDefinition();
        report.setName("Pet Location Report");
        report.setDescription("desc goes here");
        report.setAuthor("Brad Rydzewski");

        /* Create and add JDBC connection */
        JdbcConnection conn = getJdbcConnection();
        report.getDataConnections().add(conn);

        /* create report params */
        ReportParameterTextBox minParam = new ReportParameterTextBox();
        minParam.setName("Minimum Price");
        minParam.setPrompt("Enter the minimum price range");
        minParam.setValue(0);
        minParam.setRequired(true);
        ReportParameterTextBox maxParam = new ReportParameterTextBox();
        maxParam.setName("Maximum Price");
        maxParam.setPrompt("Enter the maximum price range");
        maxParam.setValue(999999999);
        maxParam.setRequired(true);
        
        /* add params to report */
        report.getReportParameters().add(minParam);
        report.getReportParameters().add(maxParam);

        /* Sql syntax for 1st query */
        String sqlA = new StringBuffer()
                .append("SELECT item.name, item.description, categoryid, price, imagethumburl, city, state, zip ")
                .append("FROM item, address, product ")
                .append("WHERE item.address_addressid = address.addressid ")
                .append("AND item.productid = product.productid ")
                .append("AND price between ? and ?").toString();
        
        /* Create a jdbc sql query and add to the report */
        JdbcQuery queryA = new JdbcQuery();
        queryA.setDataConnection(conn);
        queryA.setName("PetLocationQuery");
        queryA.setSqlQuery(sqlA);
        DataColumn col1 = new DataColumn("Name", 0, DataType.STRING);
        DataColumn col2 = new DataColumn("Description", 1, DataType.STRING);
        DataColumn col3 = new DataColumn("Category", 2, DataType.STRING);
        DataColumn col4 = new DataColumn("Price", 3, DataType.DOUBLE);
        DataColumn col5 = new DataColumn("Image", 4, DataType.STRING);
        DataColumn col6 = new DataColumn("City", 5, DataType.STRING);
        DataColumn col7 = new DataColumn("State", 6, DataType.STRING);
        DataColumn col8 = new DataColumn("Zip", 7, DataType.STRING);
        queryA.getColumns().add(col1);
        queryA.getColumns().add(col2);
        queryA.getColumns().add(col3);
        queryA.getColumns().add(col4);
        queryA.getColumns().add(col5);
        queryA.getColumns().add(col6);
        queryA.getColumns().add(col7);
        queryA.getColumns().add(col8);
        queryA.getParameters().add(new JdbcParameter(1, DataType.INTEGER, minParam));
        queryA.getParameters().add(new JdbcParameter(2, DataType.INTEGER, maxParam));
        report.getDataQueries().add(queryA);
        
        
        /* create data table, set the data query */
        DataTable table = new DataTable();
        table.setCellPadding(1);
        table.setDataQuery(queryA);

        /* create table header rows */
        GridRow tableHeader = new GridRow();
        tableHeader.addCell(new com.googlecode.g2re.html.GridCell(new RawHTML("Name")));
        tableHeader.addCell(new com.googlecode.g2re.html.GridCell(new RawHTML("Description")));
        tableHeader.addCell(new com.googlecode.g2re.html.GridCell(new RawHTML("City")));
        tableHeader.addCell(new com.googlecode.g2re.html.GridCell(new RawHTML("State")));
        tableHeader.addCell(new com.googlecode.g2re.html.GridCell(new RawHTML("Zip")));
        tableHeader.addCell(new com.googlecode.g2re.html.GridCell(new RawHTML("Price")));
        tableHeader.addCell(new com.googlecode.g2re.html.GridCell(new RawHTML("Image")));
        table.getHeaderRows().add(tableHeader);

        /* create table body rows */
        GridRow tableBody = new GridRow();
        tableBody.addCell(new com.googlecode.g2re.html.GridCell(new DataElement(col1, 0)));
        tableBody.addCell(new com.googlecode.g2re.html.GridCell(new DataElement(col2, 1)));
        tableBody.addCell(new com.googlecode.g2re.html.GridCell(new DataElement(col6, 2)));
        tableBody.addCell(new com.googlecode.g2re.html.GridCell(new DataElement(col7, 3)));
        tableBody.addCell(new com.googlecode.g2re.html.GridCell(new DataElement(col8, 4)));
        tableBody.addCell(new com.googlecode.g2re.html.GridCell(new DataElement("\"<img src='\" + row[4].toString() + \"' />\"",6)));
        
        /* add formatted price column */
        DataElement priceCellElement = new DataElement(col4, 5);
        priceCellElement.setNumberFormat("$###,###,###,##0.00");
        tableBody.addCell(new com.googlecode.g2re.html.GridCell(priceCellElement));
        
        /* add rows to table */
        table.getBodyRows().add(tableBody);

        /* add the table to the report */
        report.getWebPage().addChildElement(table);
        
        /* create and add a map */
        Map m = new Map();
        m.setDataQuery(queryA);
        m.setMapType(MapType.normal);
        m.setShowTip(true);
        m.setName("Pet Map");
        m.setDescriptionColumn(col1);
        m.setAddressColumn(col8);
        
        //omit the map, for now...
        //report.getWebPage().addChildElement(m);
        
        return report;
    }

    @Test
    public void buildAndRunTestReports() {
        
        //get report definition
        ReportDefinition petLocationReport = getPetLocationReport();
        
        //create output file where report definition will be save
        File petLocationReportXml = new File(PET_LOCATION_RPT_PATH);
     
        //write report xml file to folder
        ReportSerializationUtil.toXMLFile(
                petLocationReportXml, petLocationReport);
        
        HTMLReportBuilder.build(petLocationReportXml, null, new File("test-file.html"), false);
    }

}