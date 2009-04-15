package com.googlecode.g2re.servlet;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.util.Arrays;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

/**
 *
 * @author Brad
 */
public class ErrorView implements ServletView  {
    
    public final static ErrorView INSTANCE = new ErrorView();
    protected ErrorView() {
    }

    public void build(ServletViewArgs args, Exception e) {

        InputStream is = null;
        BufferedReader br = null;
        StringBuilder builder = new StringBuilder();
        String line;

        try {
            
            //get the Velocity html template from the Resources folder
            is = ErrorView.class.getResourceAsStream("/ErrorView.html");
            br = new BufferedReader(new InputStreamReader(is));

            //read the file stream into the string builder
            while (null != (line = br.readLine())) {
                builder.append(line).append("\n");
            }
            

            //set up Velocity template and context
            StringWriter writer = new StringWriter();
            VelocityContext context = new VelocityContext();
            context.put("args", args);
            context.put("ex", e);
            context.put("stackTrace", Arrays.toString(e.getStackTrace()));

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
    
    @Override
    public void build(ServletViewArgs args) {
        build(args, null);
    }
    
    
}
