package com.googlecode.g2re.servlet;

import com.googlecode.g2re.domain.ReportDefinition;
import com.googlecode.g2re.util.ReportSerializationUtil;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Brad
 */
public class ServletDispatcher extends HttpServlet {

    public static final String VIEW_PARAM_NAME = "view";
    public static final String REPORT_PARAM_NAME = "report";
    public static final String VARIABLE_PARAM_NAME = "var";
    public static final String HTML_VIEW_NAME = "html";
    public static final String EXCEL_VIEW_NAME = "excel";
    public static final String PDF_VIEW_NAME = "pdf";
    public static final String INPUT_VIEW_NAME = "input";
    public static final String LIST_VIEW_NAME = "list";

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {


        //response.setContentType("text/html;charset=UTF-8");
        //PrintWriter out = response.getWriter();


        Object viewParam = request.getParameter(VIEW_PARAM_NAME);
        Object reportParam = request.getParameter(REPORT_PARAM_NAME);
        ServletOutputStream output = response.getOutputStream();
        ServletView view = null;



        if (viewParam.equals(HTML_VIEW_NAME)) {

            view = HTMLReportView.INSTANCE;

        } else if (viewParam.equals(EXCEL_VIEW_NAME)) {

            view = ExcelReportView.INSTANCE;

        } else if (viewParam.equals(PDF_VIEW_NAME)) {

            view = PDFReportView.INSTANCE;

        } else if (viewParam.equals(INPUT_VIEW_NAME)) {

            view = DataEntryView.INSTANCE;

        } else if (viewParam.equals(LIST_VIEW_NAME)) {

            view = DataListView.INSTANCE;

        } else {
            System.out.println(Arrays.toString(request.getParameterMap().values().toArray()));
            System.out.println("cannot find matching view");
        //ERROR?
        }


        //get report from file system
        File report = new File(reportParam.toString());

        //create args
        ServletViewArgs args = new ServletViewArgs();
        args.setReportFile(report);
        args.setReportName(reportParam.toString());
        args.setRequest(request);
        args.setResponse(response);
        args.setServletUri(request.getRequestURI());
        args.setViewName(viewParam.toString());
        args.setOutputStream(output);
        args.setParams(getParamsAsMap(request));



        try {

        /* Load the report from an XML file */
        ReportDefinition reportDefinition =
                ReportSerializationUtil.fromXMLFile(args.getReportFile());
        
        /* add to args */
        args.setReportDefinition(reportDefinition);            
            
            //build the View
            view.build(args);

        } catch (Exception ex1) {

            try {
                
                view = ErrorView.INSTANCE; 
                ((ErrorView)view).build(args,ex1);
            }catch(Exception ex2) {
                output.print(ex1.toString());
            }

        } finally {
            //out.close();
            output.close();
        }
    }

    public Map getParamsAsMap(HttpServletRequest request) {
        Map params = new HashMap();

        Enumeration keys = request.getParameterNames();
        while (keys.hasMoreElements()) {
            String key = (String) keys.nextElement();
            String value = request.getParameter(key);

            if (!key.equals(ServletDispatcher.REPORT_PARAM_NAME) &&
                    !key.equals(ServletDispatcher.VARIABLE_PARAM_NAME) &&
                    !key.equals(ServletDispatcher.VIEW_PARAM_NAME)) {
                params.put(key, value);
            //System.out.println("key: " + key + "  value: " + value);
            }
        }

        return params;
    }
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
