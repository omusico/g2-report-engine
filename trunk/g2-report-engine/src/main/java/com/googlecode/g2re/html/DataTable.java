package com.googlecode.g2re.html;

import com.googlecode.g2re.html.style.TextAlign;
import com.googlecode.g2re.html.style.VerticalAlign;
import com.googlecode.g2re.jdbc.DataSet;
import com.googlecode.g2re.util.DataSetUtil;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Brad Rydzewski
 */
public class DataTable extends BoundElement {

    private List<GridRow> headerRows = new ArrayList<GridRow>();
    private List<GridRow> bodyRows = new ArrayList<GridRow>();
    private List<GridRow> footerRows = new ArrayList<GridRow>();
    private List<DataGroup> dataGroups = new ArrayList<DataGroup>();
    private VerticalAlign verticalAlign = null;
    private TextAlign textAlign = null;
    
    private static final String DEFAULT_CLASS = "g2-data-table";

    private int cellPadding=0;
    private int cellSpacing=0;
    private int border;

    public int getCellPadding() {
        return cellPadding;
    }

    public void setCellPadding(int cellPadding) {
        this.cellPadding = cellPadding;
    }

    public int getCellSpacing() {
        return cellSpacing;
    }

    public void setCellSpacing(int cellSpacing) {
        this.cellSpacing = cellSpacing;
    }

    public int getBorder() {
        return border;
    }

    public void setBorder(int border) {
        this.border = border;
    }
    
    
    
    

    public List<GridRow> getBodyRows() {
        return bodyRows;
    }

    public void setBodyRows(List<GridRow> bodyRows) {
        this.bodyRows = bodyRows;
    }

    public List<DataGroup> getDataGroups() {
        return dataGroups;
    }

    public void setDataGroups(List<DataGroup> dataGroups) {
        this.dataGroups = dataGroups;
    }

    public List<GridRow> getFooterRows() {
        return footerRows;
    }

    public void setFooterRows(List<GridRow> footerRows) {
        this.footerRows = footerRows;
    }

    public List<GridRow> getHeaderRows() {
        return headerRows;
    }

    public void setHeaderRows(List<GridRow> headerRows) {
        this.headerRows = headerRows;
    }

    public TextAlign getTextAlign() {
        return textAlign;
    }

    public void setTextAlign(TextAlign textAlign) {
        this.textAlign = textAlign;
    }

    public VerticalAlign getVerticalAlign() {
        return verticalAlign;
    }

    public void setVerticalAlign(VerticalAlign verticalAlign) {
        this.verticalAlign = verticalAlign;
    }
    

    
    
    @Override
    public void build(HTMLBuilderArgs args) {
        
        //build open tag, add style and such
        //open start tag
        args.getHtml().append("<table cellpadding='")
                .append(getCellPadding())
                .append("' cellspacing='")
                .append(getCellSpacing())
                .append("' ");
        
        if(border > 0) {
            args.getHtml().append(" border='")
                    .append(getBorder())
                    .append("' ");
        }
        
        this.addClassToTag(args,DEFAULT_CLASS);
        this.addIdToTag(args);
        
        if(this.getVerticalAlign()!=null) 
            args.getHtml().append("valign='")
                    .append(getVerticalAlign())
                    .append("' ");
        if(this.getTextAlign()!=null) 
            args.getHtml().append("align='")
                    .append(getTextAlign())
                    .append("' ");
        args.getHtml().append(">");
        
        //STEP 1: check to see if we have grouping
        //If this uses groups, for now, let's just go to a whole
        // sepearate method... even if there is some duplicate code.
        // it keeps things clear and simple, and less overhead
        // for reports not using grouping
        if(getDataGroups().size()>0){
            buildGroups(args);
            args.getHtml().append("</table>");
            return;
        }
        
        //STEP 2: get the data set
        String queryName = getDataQuery().getName();
        DataSet resultDataSet = args.getResults().get(queryName);
        //NEW: filter a dataSet, when applicable
        resultDataSet = DataSetUtil.getFilteredDataSet(resultDataSet, getFilters());
        List resultList = resultDataSet.getRows();
        
        
       //STEP 3: render basic HTML table.. pretty simple
        //a) render the header row(s)
        args.getHtml().append("<thead>");
        for(int i=0;i<getHeaderRows().size();i++){
            getHeaderRows().get(i).build(args);
        }
        args.getHtml().append("</thead>");
        
        //b) render the data rows
        args.getHtml().append("<tbody>");
        for(int i=0;i<resultList.size();i++){
            
            //sets the current data row
            args.setCurrentDataRow((Object[])resultList.get(i));
            
            for(int j=0;j<getBodyRows().size();j++){
                getBodyRows().get(j).build(args);
            }
        }
        args.getHtml().append("</tbody>");
        
        //set current data row back to null
        args.setCurrentDataRow(null);
        
        //d) render the footer row(s)
        for(int i=0;i<getFooterRows().size();i++){
            getFooterRows().get(i).build(args);
        }  
                
        //close table tag
        args.getHtml().append("</table>");
    }

    void buildGroups(HTMLBuilderArgs args){
        
        //STEP 1: get the data set
        String queryName = getDataQuery().getName();
        DataSet resultDataSet = args.getResults().get(queryName);
        //NEW: filter a dataSet, when applicable
        resultDataSet = DataSetUtil.getFilteredDataSet(resultDataSet, getFilters());
        List dataList = resultDataSet.getRows();
        
        //STEP 2: sort the data sets, as required
        // yes... we sort multiple times, instead of sorting once by column
        // let's consider this a "to-do", but for now, we will just deal with it
        
        //a) first we need to sort the actual groups, ascending, by order
        DataGroup[] dataGroupArray = new DataGroup[getDataGroups().size()];
        getDataGroups().toArray(dataGroupArray);
        Arrays.sort(dataGroupArray);
        
        //b) now sort the actual data, based on the groups
        //TODO probably need to rename from DataGroupComparator to have a mroe accurate description
        for (DataGroup group : dataGroupArray) {

            DataGroupComparator comparator = new DataGroupComparator();
            comparator.setDataGroup(group);
            Collections.sort(dataList, comparator);
            //System.out.println("SORTING: " + group.getDataColumn().getName());

        }
        
        //???? what is this??
        /*
        for(Object o : dataList){
            Object[] row = (Object[])o;
            System.out.println(Arrays.toString(row));
        }*/
        
        
        //STEP 3: render the header row(s).. global rows, like column names
        for(int i=0;i<getHeaderRows().size();i++){
            getHeaderRows().get(i).build(args);
        }
        
        //STEP 4: create some placeholder vaiables

        //b) need groupValues to hold a placeholder for things we are grouping on
        //    that way we know when we start / finish a group
        Object[] groupValues = new Object[getDataGroups().size()];
        Object[] groupStart = new Object[getDataGroups().size()];
        //Object[] groupEnd = new Object[getDataGroups().size()];
        

        //OK, here is the problem... don't really care about cells
        // just care about when a new group starts or stops
        // so that I can render aggregate header / footer rows.
        // but in this example i do render cell... hmmm
        
        //STEP 5: Iterate through data set to render table
        for (int i = 0; i < dataList.size(); i++) {

            //a) get the current row
            Object[] row = (Object[]) dataList.get(i);
            
            //add row to args
            args.setCurrentDataRow(row);

            //b) go through groups, let's see if a new group starts /ends
            boolean newGroup = false;
            for(int g=0; g<dataGroupArray.length;g++){
                
                DataGroup group = dataGroupArray[g];
                int groupColIndex = group.getDataColumn().getOrder();//group.getOrder(); //RENAMED from groupColIndex
                
                //is it a new group??
                newGroup = groupValues[g]==null || !groupValues[g].equals(row[groupColIndex]); //groupColIndex
                
                //if it is, we have to render the new group functions
                if(newGroup){
                    
                    //set groupValue in array
                    //that way we can match / compare to subsequent rows
                    groupValues[g] = row[groupColIndex];
                    
                    //if the start position is not null for the group,
                    // that means we have already rendered the headers group rows
                    // and now we need to finish by rendering the footer group grows
                    if(groupStart[g]!=null){ 
                        //render footer group rows
                        for(int j=0;j<group.getFooterRows().size();j++){
                            group.getFooterRows().get(j).build(args);
                        }
                    }
                    
                    //set int indicating where group starts
                    groupStart[g] = i;
                    
                    //render new group's group header
                    for(int j=0;j<group.getHeaderRows().size();j++){
                        group.getHeaderRows().get(j).build(args);
                    }
                    
                }
                
            }

            
            //if its still a new group we can render the data rows
            // this is because we can only render body rows when the LAST
            // group in the list is new, otherwise if a group is repeated,
            // we will just be rendering aggregate rows
            //if(newGroup){ //maybe i'm wrong, maybe we render each row...
            
                //render each body row
                for(int j=0;j<getBodyRows().size();j++){
                    getBodyRows().get(j).build(args);
                }
            //}
            
            
        }
        
        //if newGroup is still set to true, we can render the last groups inner cells
        
        //remove current row from args
        args.setCurrentDataRow(null);
        
        //STEP 6: render the footer row(s)
        for(int i=0;i<getFooterRows().size();i++){
            getFooterRows().get(i).build(args);
        }  
         
    }
}
