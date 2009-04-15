package com.googlecode.g2re;

import com.googlecode.g2re.domain.ReportDefinition;
import com.googlecode.g2re.domain.ReportFormat;
import com.googlecode.g2re.domain.ReportParameter;
import com.googlecode.g2re.util.ReportSerializationUtil;
import java.io.File;

/**
 * Main method allowing a user to generate reports from the command line
 */
public class CommandLineRunner 
{
    public static void main( String[] args )
    {
        //show help menu, if requested
        if(assertShowHelp(args)) showHelpMenuAndExit();
        
        //get the input file and output file
        File inputFile = getReportInputFile(args);
        File outputFile = getReportOutputPath(args);
        
        //make sure the file exists, if not, print an error and exit
        if(!assertReportExists(inputFile)) {
            System.out.println("No report could be found at the given path");
            System.exit(1);
        }
               
        //if asked, show parameters (will de-serialize) and then exit
        if(assertShowParams(args)) showParamsAndExit(inputFile);
        
        //make sure an output file was provided, if not, print an error and exit
        if(!assertOutputFileProvided(outputFile)) {
            System.out.println("An output file path was not provided");
            System.exit(1);
        }
        
        
        //TODO - need to actually get Parameters
        
        
        //get report format,will default to HTML
        ReportFormat format = getReportFormatType(args);
        
        //Execute report
        if(format == ReportFormat.HTML){
            
            //build HTML formatted report
            HTMLReportBuilder builder = new HTMLReportBuilder();
            builder.build(inputFile, null, outputFile);
            
        } else if (format == ReportFormat.Excel){
            
            //build Excel formatted report
            ExcelReportBuilder builder = new ExcelReportBuilder();
            builder.build(inputFile, null, outputFile);
            
        } else {
            //TODO, implement PDF reports
            System.out.println("Cannot create ["+format+"] report...feature not yet implemented");
            System.exit(1);
        }
        
        //exit
    }
    
    public static File getReportInputFile(String[] args){ 

        //check all args to see if help menu was requested
        for(int i=0;i<args.length;i++){
            
            if(args[i].equals("-in") && i<args.length-1)
                return new File(args[i+1]);
        }
        
        return null;
    }
    public static File getReportOutputPath(String[] args){ 
        
        //check all args to see if help menu was requested
        for(int i=0;i<args.length;i++){
            
            if(args[i].equals("-out") && i<args.length-1)
                return new File(args[i+1]);
        }
        
        return null;
    }
    public static ReportFormat getReportFormatType(String[] args){
        
        //check all args to see if help menu was requested
        for(String s : args){
            
            if(s.equals("-format:")){
                String formatString = s.replace("-format:", "");
                if(formatString.equals("excel")) return ReportFormat.Excel;
                else if(formatString.equals("pdf")) return ReportFormat.PDF;
            }
        }
        
        return ReportFormat.HTML;
    }
    public void getReportParameters(){}
    
    public static boolean assertShowHelp(String[] args){
        
        //check all args to see if help menu was requested
        for(String s : args){
            if(s.equals("-h") || s.equals("-help") || s.equals("-?"))
                return true;
        }
        
        return false;
    }
    public static boolean assertShowParams(String[] args){
        
        //check all args to see if help menu was requested
        for(String s : args){
            if(s.equals("-showparams"))
                return true;
        }
        
        return false;
    }
    public static boolean assertOutputFileProvided(File path){
        
        return path!=null;
    }
    public static boolean assertReportExists(File path){
        
        return path!=null && path.exists();
    }
    public static void showHelpMenuAndExit(){
        
        System.out.println("  ");
        System.out.println(" Report Builder Command Line Options:");
        System.out.println("  ");
        System.out.println(" -in \t\t\t\t Path of report definition (xml) to be run");
        System.out.println(" -out \t\t\t\t Path where the report results are generated");
        System.out.println(" -format:html|excel|pdf \t Specifies the report format to be rendered");
        System.out.println(" -D<name>=<value> \t\t Declares a report parameter (optional)");
        System.out.println(" -h -? -help \t\t\t Prints this help message");
        System.out.println(" -showparams \t\t\t Prints all parameters required to run a report");
        System.out.println("  ");
        System.out.println(" Example args to generate an HTML report:");
        System.out.println("   java -jar web-reports-core.jar ");
        System.out.println("     -in [path to report definition xml file]");
        System.out.println("     -out [path to html output file]");
        System.out.println("     -format:html -Dparam1=foo -Dparam2=bar");
        System.exit(0);
    }
    public static void showParamsAndExit(File inputFile){
        
        //parse report definition
        ReportDefinition report = ReportSerializationUtil.fromXMLFile(inputFile);
        
        //get count of report parameters
        int paramCount = (report.getReportParameters()==null)?0:report.getReportParameters().size();
        
        System.out.println(" ");
        System.out.println(" There are ("+paramCount+") defined report parameters");
        System.out.println(" ");
        
        if(paramCount>0){
            for(ReportParameter param : report.getReportParameters()){
                
                //print the parameters
                System.out.println("  name: ["+param.getName()+"] prompt: ["+param.getPrompt()+"]");
            }
        }
        
        System.exit(0);
    }
}
