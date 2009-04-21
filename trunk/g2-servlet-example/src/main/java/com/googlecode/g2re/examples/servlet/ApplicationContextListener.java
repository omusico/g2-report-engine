package com.googlecode.g2re.examples.servlet;

import java.io.File;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Web application lifecycle listener.
 * @author Brad
 */

public class ApplicationContextListener implements ServletContextListener {

    private PetStoreDataManager dataManager = null;
    private static final String DATA_FILE = "WEB-INF\\classes\\petstore.sql";
    private static final String CLASS_NAME = "org.h2.Driver";
    private static final String DB_URL = "jdbc:h2:mem:test-db;DB_CLOSE_DELAY=-1";
    private static final String DB_USER = "sa";
    private static final String DB_PW = "";
    
    @Override
    public void contextInitialized(ServletContextEvent arg0) {
        
        //open connection
        dataManager = new PetStoreDataManager();
        dataManager.openConnection(DB_URL, DB_USER, DB_PW, CLASS_NAME);
        
        //load data
        dataManager.loadFile(
                new File(arg0.getServletContext().getRealPath(DATA_FILE)));
    }

    @Override
    public void contextDestroyed(ServletContextEvent arg0) {
        
        //close connection
        dataManager.closeConnection();
    }
}