package com.googlecode.g2re;

import javax.jdo.PersistenceManagerFactory;

/**
 * Contains application settings as property values
 * @author Brad
 */
public class Settings {

    /**
     * Single, global instance of the report settings.
     */
    private static Settings INSTANCE = new Settings();
    
    /**
     * Private constructor prevents instantiation from other classes.
     */
    private Settings() { }
    
    /**
     * Gets the Global Settings for the Report Engine.
     * @return
     */
    public static Settings get() {
        return INSTANCE;
    }
    
    /**
     * JDO persistance manager factory, used in conjunction
     * with Google App Engine.
     */
    private PersistenceManagerFactory pmf = null;
    
    /**
     * Google map key. Default value set to generic key
     * to work on localhost domain.
     */
    private String googleMapKey = "ABQIAAAA-O3c-Om9OcvXMOJXreXHAxSsTL4WIgxhMZ0ZK_kHjwHeQuOD4xSbZqVZW2U_OWOxMp3YPfzZl2GavQ";

    /**
     * Gets the Google Maps API key required by google to render Maps
     * using Google's free service. To obtain a key please visit the following
     * URL: {@link http://code.google.com/apis/maps/signup.html}
     * @return
     */
    public String getGoogleMapKey() {
        return googleMapKey;
    }

    /**
     * Sets the Google Map API key required by components that use the
     * Google Maps service to display maps.
     * @param googleMapKey
     */
    public void setGoogleMapKey(String googleMapKey) {
        this.googleMapKey = googleMapKey;
    }

    /**
     * Gets the PersistanceManagerFactory used by JDO to connect to
     * a data source. The report builder will get an instance of the
     * PersistenceManager to execute query.
     * @return
     */
    public PersistenceManagerFactory getPmf() {
        return pmf;
    }

    /**
     * Set the PersistenceManagerFactory to be used by the report builder
     * when executing JDO-based queries. This is intended for use with
     * Google's App Engine, and must be set manually by the developer.
     * @param pmf
     */
    public void setPmf(PersistenceManagerFactory pmf) {
        assert(pmf!=null);
        this.pmf = pmf;
    }
}
