package com.googlecode.g2re.domain;

import com.googlecode.g2re.jdbc.DataSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;

/**
 * Defines a query against the Google App Engine data store, 
 * using the JDO API.
 * @author Brad Rydzewski
 */
public class AppEngineQuery extends DataQuery {

    /**
     * App Engine data store connection.
     */
    private AppEngineConnection connection;
    /**
     * JDO SQL Query.
     */
    private String sqlQuery;

    public AppEngineConnection getConnection() {
        return connection;
    }

    public void setConnection(AppEngineConnection connection) {
        this.connection = connection;
    }

    public String getSqlQuery() {
        return sqlQuery;
    }

    public void setSqlQuery(String sqlQuery) {
        this.sqlQuery = sqlQuery;
    }

    
    @Override
    public DataSet execute() {
        
        DataSet ds = new DataSet();
        List results = null;
        PersistenceManager pm = null;
        
        try {
            
            //get Persistence Manager from connection
            pm = connection.getPersistenceManagerFactory()
                    .getPersistenceManager();
            
            //create query
            Query query = pm.newQuery(sqlQuery);
            
            //if no parameters, we just execute query, plain and simple
            if(getParameters() == null || getParameters().isEmpty()) {
                
                results = (List)query.execute();
            
            //for parameters, we need to create a map of params and give
            // to the query
            } else {
                
                Map paramMap = new HashMap();                
                for(JdbcParameter p : getParameters()) {
                    //add each param to map
                    paramMap.put(p.getName(),p.getValue());
                }

                //execute query with map
                results = (List)query.executeWithMap(paramMap);
            }
            
            //add result set rows (Object[]) to the data set
            ds.setRows(results);
            
        } catch(Exception ex) {
            //too lazy to catch appropriate exceptions, so let's catch all of them
            // and print out an error if one occurs for debugging
            System.out.println(ex.getMessage().toString());
        } finally {
            try { pm.close(); } finally { }
        }

        return ds;
    }

}
