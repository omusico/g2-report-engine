package com.googlecode.g2re.domain;

/**
 *
 * @author Brad Rydzewski
 */
public class JdbcConnection {

    private String name;
    private String driverClass;
    private String databaseUrl;
    private String databaseUser;
    private String databasePassword;
    private String databaseJndiName;
    

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDatabaseJndiName() {
        return databaseJndiName;
    }

    public void setDatabaseJndiName(String databaseJndiName) {
        this.databaseJndiName = databaseJndiName;
    }

    public String getDatabasePassword() {
        return databasePassword;
    }

    public void setDatabasePassword(String databasePassword) {
        this.databasePassword = databasePassword;
    }

    public String getDatabaseUrl() {
        return databaseUrl;
    }

    public void setDatabaseUrl(String databaseUrl) {
        this.databaseUrl = databaseUrl;
    }

    public String getDatabaseUser() {
        return databaseUser;
    }

    public void setDatabaseUser(String databaseUser) {
        this.databaseUser = databaseUser;
    }

    public String getDriverClass() {
        return driverClass;
    }

    public void setDriverClass(String driverClass) {
        this.driverClass = driverClass;
    }    
    
    
}
