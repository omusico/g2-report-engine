package com.googlecode.g2re.excel;

import com.googlecode.g2re.excel.ExcelBuilderArgs;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import jxl.write.WritableSheet;

/**
 * Represents an Excel Workbook, holding one or more Worksheets. Typically
 * a collection of worksheets would be defined as an Excel File with an
 * XLS file extension.
 * 
 * @author Brad Rydzewski
 */
public class Workbook {

    private List<Worksheet> worksheets;

    /**
     * Gets the list of workseets in this workbook
     * @return
     */
    public List<Worksheet> getWorksheets() {
        return worksheets;
    }

    /**
     * Sets the list of worksheets in this workbook
     * @param worksheets
     */
    public void setWorksheets(List<Worksheet> worksheets) {
        this.worksheets = worksheets;
    }

    /**
     * Adds a {@link Worksheet} to the {@link Workbook}. If the
     * list is currently null, a new list will be created to
     * avoid a {@code NullPointerException}.
     * @param worksheet
     */
    public void addWorksheet(Worksheet worksheet) {

        if (getWorksheets() == null) {
            setWorksheets(new ArrayList<Worksheet>());
        }
        getWorksheets().add(worksheet);
    }

    /**
     * Creates a {@link Worksheet} with the given name and 
     * adds to the {@link Workbook}. If the
     * list is currently null, a new list will be created to
     * avoid a {@code NullPointerException}.
     * @param name of the worksheet
     * @param order order the worksheet should appear in the excel workbook
     */
    public Worksheet addWorksheet(String name, int order) {

        if (getWorksheets() == null) {
            setWorksheets(new ArrayList<Worksheet>());
        }
        Worksheet worksheet = new Worksheet(name, order);
        getWorksheets().add(worksheet);

        return worksheet;
    }

    /**
     * Builds an Excel Workbook based on the given stream, Rendering Args
     * and child elements (worksheets, tables, etc).
     * @param args
     */
    public void build(ExcelBuilderArgs args) {

        //sort the worksheets
        Collections.sort(getWorksheets());

        for (Worksheet worksheet : getWorksheets()) {

        //create a worksheet
        WritableSheet sheet = args.getWorkbook().createSheet(worksheet.getName(), worksheet.getOrder());
        //add it to args as the current sheet
        args.setWorksheet(sheet);            
            
            //build a worksheets
            worksheet.build(args);
        }

    }
}
