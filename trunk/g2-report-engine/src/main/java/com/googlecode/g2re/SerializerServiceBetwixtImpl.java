package com.googlecode.g2re;

import com.googlecode.g2re.domain.ReportDefinition;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.StringReader;
import java.io.StringWriter;
import org.apache.commons.betwixt.io.BeanReader;
import org.apache.commons.betwixt.io.BeanWriter;

/**
 * Implementation of the ReportSerializer that Serializes and De-Serializes
 * reports to and from XML. All serialization is done using the Betwixt API.
 * @author Brad Rydzewski
 * @version 1.0.1
 * @since 1.0.1
 */
public class SerializerServiceBetwixtImpl implements SerializerService {

    @Override
    public ReportDefinition fromFile(File xmlFile) {
        
        FileReader fileReader = null;
        BeanReader beanReader = null;
        ReportDefinition reportDefinition = null;

        try {
            fileReader = new FileReader(xmlFile);
            beanReader = new BeanReader();
            beanReader.registerBeanClass(ReportDefinition.class);
            reportDefinition = (ReportDefinition)beanReader.parse(fileReader);
        }catch(Exception ex) {
            throw new RuntimeException(ex);
        }
        return reportDefinition;
    }

    @Override
    public void toFile(File to, ReportDefinition from) {
        String xml = toString(from);
        FileOutputStream stream = null;

        try{
            stream = new FileOutputStream(to);
            stream.write(xml.getBytes());

        }catch(Exception ex){
            throw new RuntimeException(ex.getMessage());
        }finally{
            //close the stream
            try{ stream.flush(); }catch(Exception ex){}
            try{ stream.close(); }catch(Exception ex){}
        }
    }

    @Override
    public ReportDefinition fromString(String xmlString) {
        BeanReader beanReader = null;
        ReportDefinition reportDefinition = null;
        StringReader stringReader = null;

        try {
            stringReader = new StringReader(xmlString);
            beanReader = new BeanReader();
            beanReader.registerBeanClass(ReportDefinition.class);
            reportDefinition = (ReportDefinition)beanReader.parse(stringReader);
        }catch(Exception ex) {
            throw new RuntimeException(ex);
        }
        return reportDefinition;
    }
    
    @Override
    public String toString(ReportDefinition reportDefinition) {

        BeanWriter beanWriter = null;
        StringWriter stringWriter = null;
        String xml = "";

        try {
            stringWriter = new StringWriter();
            beanWriter = new BeanWriter(stringWriter);
            beanWriter.enablePrettyPrint();
            beanWriter.write(reportDefinition);
            xml = stringWriter.toString();
        } catch(Exception ex) {
            throw new RuntimeException(ex);
        } finally {
            try {beanWriter.flush();}catch(java.io.IOException ex){}
            try {beanWriter.close();}catch(java.io.IOException ex){}
            //try {stringWriter.close();}catch(java.io.IOException ex){}
        }

        return xml;
    }
}
