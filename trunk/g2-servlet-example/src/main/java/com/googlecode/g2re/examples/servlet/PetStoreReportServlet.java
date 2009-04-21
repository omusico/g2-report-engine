/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.googlecode.g2re.examples.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Brad
 */
public class PetStoreReportServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest arg0, HttpServletResponse arg1) throws ServletException, IOException {
        String rpt1 = this.getServletContext().getRealPath("WEB-INF\\classes\\PetLocationReport.rxml");
        arg1.getWriter().print("<a href='ReportViewer.htm?view=input&report="+rpt1+"'>Pet Location Report</a>");
    }

}
