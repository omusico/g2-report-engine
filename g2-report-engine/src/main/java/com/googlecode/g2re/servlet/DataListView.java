package com.googlecode.g2re.servlet;

import com.thoughtworks.xstream.io.json.JettisonMappedXmlDriver;

/**
 *
 * @author Brad Rydzewski
 */
public class DataListView implements ServletView {

    public final static DataListView INSTANCE = new DataListView();
    protected DataListView() {
    }

    @Override
    public void build(ServletViewArgs args) {

        try {
            //generate Report HTML
            //String html = HTMLReportBuilder.build(report, params);

            //write html to output stream
            //output.print(html);

            //set as JSON!
            args.getResponse().setContentType("text/html;charset=UTF-8");
        }
        catch(Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
