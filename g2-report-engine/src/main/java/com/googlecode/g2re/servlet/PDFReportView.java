package com.googlecode.g2re.servlet;

import com.googlecode.g2re.PDFReportBuilder;

/**
 *
 * @author Brad Rydzewski
 */
public class PDFReportView implements ServletView {

    public final static PDFReportView INSTANCE = new PDFReportView();
    protected PDFReportView() {
    }

    @Override
    public void build(ServletViewArgs args) {

        try {

            //get report name and suggested type
            String fileName = args.getReportName() + ".pdf";
            String contentType = "application/pdf";

            //set content type
            args.getResponse().setContentType(contentType);
            //set header as attachment
            args.getResponse().setHeader("Content-Disposition", "attachment; filename=" + fileName);

            //build report, passing output stream
            PDFReportBuilder.build(args.getReportFile(), args.getParams(), args.getOutputStream());

        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
