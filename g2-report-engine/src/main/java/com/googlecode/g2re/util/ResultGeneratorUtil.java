package com.googlecode.g2re.util;

import com.googlecode.g2re.domain.JdbcConnection;
import com.googlecode.g2re.domain.JdbcParameter;
import com.googlecode.g2re.domain.JdbcQuery;
import com.googlecode.g2re.domain.ReportDefinition;
import com.googlecode.g2re.jdbc.DataSet;
import com.googlecode.g2re.jdbc.DataSourceFactory;
import com.googlecode.g2re.jdbc.JdbcTemplate;
import com.googlecode.g2re.jdbc.PreparedStatementCreatorImpl;
import com.googlecode.g2re.jdbc.RowCallbackHandlerImpl;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Map;
import javax.sql.DataSource;

/**
 *
 * @author Brad
 */
public class ResultGeneratorUtil {

    public static Dictionary<String,DataSet> generateResults(ReportDefinition report, Map params){
         
        Dictionary<String,DataSet> reportResults = new Hashtable<String,DataSet>();
        
        for(int i = 0; i < report.getDataQueries().size(); i++){
            
            JdbcQuery dataQuery = (JdbcQuery)report.getDataQueries().get(i);
            JdbcConnection dataConn = dataQuery.getDataConnection();
            
            //gets datasource
            DataSource ds = DataSourceFactory.createDataSource(
                    dataConn.getDatabaseUrl(), 
                    dataConn.getDatabaseUser(), 
                    dataConn.getDatabasePassword(), 
                    dataConn.getDriverClass(), 
                    dataConn.getDatabaseJndiName());
            
            //create query
            PreparedStatementCreatorImpl pstmt = 
                    new PreparedStatementCreatorImpl(dataQuery.getSqlQuery());
            
            //add params
            for(JdbcParameter param : dataQuery.getParameters()){
                
                //need to convert value to expected parameter type
                Object reportParamValue = DataTypeConversionUtil
                        .getCastedObjectToType(param.getType(),param.getValue());
                
                //get the sql type to give to the prepared statement
                int sqlType = DataTypeConversionUtil.getSqlTypeFromDataTypeEnum(param.getType());
                
                //add parameter to the prepared statement
                pstmt.addParameter(param.getPosition(), reportParamValue, sqlType);
            }
            
            //create jdbc template
            JdbcTemplate template = new JdbcTemplate(ds);
            DataSet results = template.query(pstmt,new RowCallbackHandlerImpl());

            reportResults.put(dataQuery.getName(), results);
            
        }
        
        return reportResults;
    }
}
