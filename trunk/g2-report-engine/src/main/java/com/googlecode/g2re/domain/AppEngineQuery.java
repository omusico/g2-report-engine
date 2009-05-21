package com.googlecode.g2re.domain;

import com.googlecode.g2re.jdbc.DataSet;
import java.util.List;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;

/**
 *
 * @author Brad
 */
public class AppEngineQuery extends DataQuery {

    private AppEngineConnection connection;
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
            pm = connection.getPersistenceManagerFactory().getPersistenceManager();//connection.get().getPersistenceManager();
            Query query = pm.newQuery(sqlQuery);
            
            
            if(getParameters() == null || getParameters().isEmpty()) {
                results = (List)query.execute();
                System.out.println("Query executed with " + results.size() + " result(s)");
            } else {
                
                List<JdbcParameter> params = this.getParameters();
                Object[] objs = new Object[params.size()];
                StringBuilder paramDeclareString = new StringBuilder();
                int i = 0;
                
                for(JdbcParameter p : params) {
                    
                    objs[i] = p.getValue();
                    String type = "String";
                    switch(p.getType()) {
                        case STRING : type = "String"; break;
                        case FLOAT : type = "float"; break;
                        case DATE : type = "Date"; break;
                        case DOUBLE : type = "double"; break;
                        case INTEGER : type = "int"; break;
                    }
                    
                    if(i>0) {
                        paramDeclareString.append(", ");
                    }
                    
                    paramDeclareString.append(type).append(" ").append(p.getName());
                }
                
                query.declareParameters(paramDeclareString.toString());
                results = (List)query.executeWithArray(this.getParameters().toArray());
                
            }
            
            ds.setRows(results);
        } catch(Exception ex) {
            
        } finally {
            try { pm.close(); } finally { }
        }
        
        
        
        
        return ds;
    }

}
