package com.googlecode.g2re;

import com.googlecode.g2re.domain.ReportDefinition;
import com.thoughtworks.xstream.XStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;

/**
 * Implementation of the ReportSerializer that Serializes and De-Serializes
 * reports to and from XML. All serialization is done using the XStream API.
 * @author Brad Rydzewski
 * @version 1.0.1
 * @since 1.0.1
 */
public class SerializerServiceXStreamImpl implements SerializerService {

    @Override
    public ReportDefinition fromFile(File xmlFile) {

        try{
            XStream xstream = new XStream();
            ReportDefinition reportDefinition =
                    (ReportDefinition)xstream.fromXML(new FileReader(xmlFile));
            return reportDefinition;
        }catch(FileNotFoundException ex){
            throw new RuntimeException(ex.getMessage());
        }
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

        XStream xstream = new XStream();
        return (ReportDefinition)xstream.fromXML(xmlString);
    }

    @Override
    public String toString(ReportDefinition reportDefinition) {

        XStream xstream = new XStream();
        String xml = xstream.toXML(reportDefinition);
        return xml;
    }

}
