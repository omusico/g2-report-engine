package com.googlecode.g2re;

import com.googlecode.g2re.domain.ReportDefinition;
import java.io.File;

/**
 * Serializes and De-searilizes {@link ReportDefinition} classes.
 * @author Brad Rydzewski
 * @version 1.0.1
 * @since 1.0.1
 */
public interface SerializerService {
    
    /**
     * Serializes a {@link ReportDefinition} object to a String representation.
     * @param reportDefinition ReportDefinition to serialize.
     * @return a ReportDefinition object serialzied to a string.
     */
    public String toString(ReportDefinition reportDefinition);

    /**
     * De-Serializes a ReportDefinition object from a String representation.
     * @param xmlString
     * @return
     */
    public ReportDefinition fromString(String xmlString);

    /**
     * De-Serializes a ReportDefinition object from a text-based File.
     * @param xmlFile
     * @return
     */
    public ReportDefinition fromFile(File xmlFile);

    /**
     * Serializes a ReportDefinition object and writes it to disk.
     * @param to file to which the report defintion will be written
     * @param from report defintion object to be serialized
     */
    public void toFile(File to, ReportDefinition from);
}
