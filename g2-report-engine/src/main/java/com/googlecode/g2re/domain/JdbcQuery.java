package com.googlecode.g2re.domain;

import com.googlecode.g2re.jdbc.DataSet;
import com.googlecode.g2re.jdbc.DataSourceFactory;
import com.googlecode.g2re.jdbc.JdbcTemplate;
import com.googlecode.g2re.jdbc.PreparedStatementCreatorImpl;
import com.googlecode.g2re.jdbc.RowCallbackHandlerImpl;
import com.googlecode.g2re.util.DataTypeConversionUtil;
import javax.sql.DataSource;

/**
 *
 * @author Brad Rydzewski
 */
public class JdbcQuery extends DataQuery {

    
    private String sqlQuery;
    private JdbcConnection dataConnection;

    public JdbcConnection getDataConnection() {
        return dataConnection;
    }

    public void setDataConnection(JdbcConnection dataSource) {
        this.dataConnection = dataSource;
    }

    public String getSqlQuery() {
        return sqlQuery;
    }

    public void setSqlQuery(String sqlQuery) {
        this.sqlQuery = sqlQuery;
    }
    
    @Override
    public DataSet execute(){

            //gets datasource
            DataSource ds = DataSourceFactory.createDataSource(
                    getDataConnection().getDatabaseUrl(), 
                    getDataConnection().getDatabaseUser(), 
                    getDataConnection().getDatabasePassword(), 
                    getDataConnection().getDriverClass(), 
                    getDataConnection().getDatabaseJndiName());
            
            //create query
            PreparedStatementCreatorImpl pstmt = 
                    new PreparedStatementCreatorImpl(getSqlQuery());
            
            //add params
            for(JdbcParameter param : getParameters()){
                
                //need to convert value to expected parameter type
                Object reportParamValue = DataTypeConversionUtil
                        .getCastedObjectToType(param.getType(),param.getValue());
                
                //get the sql type to give to the prepared statement
                int sqlType = DataTypeConversionUtil
                        .getSqlTypeFromDataTypeEnum(param.getType());
                
                //add parameter to the prepared statement
                pstmt.addParameter(param.getPosition(), reportParamValue, sqlType);
            }
            
            //create jdbc template
            JdbcTemplate template = new JdbcTemplate(ds);
            DataSet results = template.query(pstmt,new RowCallbackHandlerImpl());

            //return results
            return results;
    }
    
}
