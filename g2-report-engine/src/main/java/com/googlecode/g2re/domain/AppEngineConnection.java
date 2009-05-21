package com.googlecode.g2re.domain;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManagerFactory;

/**
 * Connection to google app engine data store.
 * @author Brad Rydzewski
 */
public class AppEngineConnection extends DataConnection {
    
    /**
     * Persistence manager.
     */
    private PersistenceManagerFactory pmf = null;

    public PersistenceManagerFactory getPersistenceManagerFactory() {
        return pmf;
    }

    public void setPersistenceManagerFactory(PersistenceManagerFactory pmf) {
        this.pmf = pmf;
    }
}
