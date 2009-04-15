package com.googlecode.g2re.servlet;

import com.googlecode.g2re.ExcelReportBuilder;
import java.io.File;
import java.net.URLConnection;
import java.util.Map;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Brad Rydzewski
 */
public class ExcelReportView implements ServletView {

    public final static ExcelReportView INSTANCE = new ExcelReportView();
    protected ExcelReportView() {
    }

    @Override
    public void build(ServletViewArgs args) {

        try {

            //get report name and suggested type
            String fileName = args.getReportName() + ".xls";
            String contentType = URLConnection.guessContentTypeFromName(fileName);

            //set content type
            args.getResponse().setContentType(contentType);
            //set header as attachment
            args.getResponse().setHeader("Content-Disposition", "attachment; filename=" + fileName);

            //build report, passing output stream
            ExcelReportBuilder.build(args.getReportFile(), args.getParams(), args.getOutputStream());

        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
