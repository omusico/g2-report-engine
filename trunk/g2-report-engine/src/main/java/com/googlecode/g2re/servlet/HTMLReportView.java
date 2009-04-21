package com.googlecode.g2re.servlet;

import com.googlecode.g2re.HTMLReportBuilder;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.util.Arrays;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

/**
 *
 * @author Brad Rydzewski
 */
public class HTMLReportView implements ServletView {

    public final static HTMLReportView INSTANCE = new HTMLReportView();
    protected HTMLReportView() {
    }

    
    public void buildOld(ServletViewArgs args) {

        try {
            //generate Report HTML
            String html = HTMLReportBuilder.build(args.getReportFile(), args.getParams(), true);

            //write html to output stream
            args.getOutputStream().print(html);
            
            //set as HTML
            args.getResponse().setContentType("text/html;charset=UTF-8");
        }
        catch(Exception ex) {
            throw new RuntimeException(ex);
        }
    }
    
    @Override
    public void build(ServletViewArgs args) {

        InputStream is = null;
        BufferedReader br = null;
        StringBuilder builder = new StringBuilder();
        String line;

        try {
            
            //run report
            String html = HTMLReportBuilder.build(
                    args.getReportFile(), args.getParams(), true);
            
            //get the Velocity html template from the Resources folder
            is = ErrorView.class.getResourceAsStream("/HTMLReportView.html");
            br = new BufferedReader(new InputStreamReader(is));

            //read the file stream into the string builder
            while (null != (line = br.readLine())) {
                builder.append(line).append("\n");
            }
            
            //set up Velocity template and context
            StringWriter writer = new StringWriter();
            VelocityContext context = new VelocityContext();
            context.put("args", args);
            context.put("html", html);

            //fill out the velocity template
            Velocity.evaluate(context, writer, "", builder.toString());

            //write the filled-out velocity template to the http stream
            args.getOutputStream().print(writer.toString());

            //set as HTML
            args.getResponse().setContentType("text/html;charset=UTF-8");
            
        } catch (Exception ex) {

            throw new RuntimeException(ex);
        }
    }
}
