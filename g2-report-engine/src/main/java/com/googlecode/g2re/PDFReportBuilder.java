/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.googlecode.g2re;

import com.googlecode.g2re.domain.ReportDefinition;
import com.googlecode.g2re.util.ReportSerializationUtil;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.StringBufferInputStream;
import java.util.Map;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.xhtmlrenderer.pdf.ITextRenderer;

/**
 *
 * @author Brad Rydzewski
 */
public class PDFReportBuilder {


    /**
     * Builds a PDF file based on the provided report definition and
     * report parameters. Once complete, writes the file to disk. Throws
     * a {@link RuntimeException} if any errors are encountered.
     * @param reportDefinitionXML location report definition file
     * @param params database parameters
     * @param outputFile File name / location for output
     */
    public static void build(File reportDefinitionXML,
            Map params,
            File outputFile) {

        /* Load the report from an XML file */
        ReportDefinition reportDefinition =
                ReportSerializationUtil.fromXMLFile(reportDefinitionXML);

        OutputStream stream = null;

        try {
            //create the stream
            stream = new FileOutputStream(outputFile);
            build(reportDefinitionXML, params, stream);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        } finally {
            //close the stream
            try {
                stream.close();
            } catch (Exception e) {
            }
        }
    }


    /**
     * Builds a PDF file based on the provided report definition and
     * report parameters. Once complete, writes the file to disk. Throws
     * a {@link RuntimeException} if any errors are encountered.
     * @param reportDefiniton report definition object
     * @param params database parameters
     * @param outputFile File name / location for output
     */
    public static void build(ReportDefinition reportDefiniton,
            Map params,
            File outputFile) {
        
        OutputStream stream = null;

        try {
            //create the stream
            stream = new FileOutputStream(outputFile);
            build(reportDefiniton, params, stream);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        } finally {
            //close the stream
            try {
                stream.close();
            } catch (Exception e) {
            }
        }
    }


    /**
     * Builds a PDF file based on the provided report definition and
     * report parameters. Once complete, writes the file to disk. Throws
     * a {@link RuntimeException} if any errors are encountered.
     * @param reportDefinitonXML location of report definition file
     * @param params database parameters
     * @param stream stream that excel file will be written to
     */
    public static void build(File reportDefinitonXML, Map params, OutputStream stream) {

        /* Load the report from an XML file */
        ReportDefinition reportDefinition =
                ReportSerializationUtil.fromXMLFile(reportDefinitonXML);

        build(reportDefinition, params, stream);
    }


    /**
     * Builds an Excel file based on the provided report definition and
     * report parameters. Once complete, writes the file to disk. Throws
     * a {@link RuntimeException} if any errors are encountered.
     * @param reportDefiniton report definition object
     * @param params database parameters
     * @param stream stream that the Excel file will be written to
     */
    public static void build(ReportDefinition reportDefiniton,
            Map params,
            OutputStream stream) {

        String html = HTMLReportBuilder.build(reportDefiniton, params, false);
        html = html.substring(64); //removes the DOCTYPE
        System.out.println(html);
        try {

            // parse the markup into an xml Document
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = builder.parse(new StringBufferInputStream(html));
            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocument(doc, null);

            renderer.layout();
            renderer.createPDF(stream);

        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
