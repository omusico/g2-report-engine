package com.googlecode.g2re.servlet;

import com.googlecode.g2re.HTMLReportBuilder;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Brad Rydzewski
 */
public class HTMLReportView implements ServletView {

    public final static HTMLReportView INSTANCE = new HTMLReportView();
    protected HTMLReportView() {
    }

    @Override
    public void build(ServletViewArgs args) {

        try {
            //generate Report HTML
            String html = HTMLReportBuilder.build(args.getReportFile(), args.getParams());

            //write html to output stream
            args.getOutputStream().print(html);
            
            //set as HTML
            args.getResponse().setContentType("text/html;charset=UTF-8");
        }
        catch(Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
