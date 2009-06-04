package com.googlecode.g2re.html.google;

import com.googlecode.g2re.domain.DataColumn;
import com.googlecode.g2re.domain.DataType;
import com.googlecode.g2re.domain.JdbcQuery;
import com.googlecode.g2re.html.BoundElement;
import com.googlecode.g2re.html.HTMLBuilderArgs;
import com.googlecode.g2re.jdbc.DataSet;
import com.googlecode.g2re.util.DataSetUtil;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * A widget for displaying data in Motion Chart format,
 * a time-based, animated flash Chart provided by the Google 
 * Visualization suite.
 * {@linkplain http://code.google.com/apis/visualization/documentation/gallery/motionchart.html}
 * @author Brad Rydzewski
 */
public class MotionChart extends BoundElement {

    private float width = 300f;
    private float height = 500f;
    
    /*
        # The second column must contain time values. Time can be expressed in a few different ways:
            * Years. Column type: 'number'. Example: 2008.
            * Month, day and year. Column type should be 'date' and the values should be javascript Date instances.
            * Week numbers. Column type should be 'string' and the values should have the pattern YYYYWww, which conforms to ISO 8601. Example: '2008W03'.
            * Quarters. Column type should be 'string' and the values should have the pattern YYYYQq, which conforms to ISO 8601. Example: '2008Q3'.
     * */
    private DataColumn nameColumn;
    private DataColumn dateColumn;
    private List<DataColumn> additionalColumns = new ArrayList<DataColumn>();

    
    public List<DataColumn> getAdditionalColumns() {
        return additionalColumns;
    }

    public void setAdditionalColumns(DataColumn[] column) {
        this.additionalColumns = Arrays.asList(column);
    }    
    
    public void setAdditionalColumns(List<DataColumn> column) {
        this.additionalColumns = column;
    }
    
    public void addAdditionalColumn(DataColumn column){
        this.additionalColumns.add(column);
    }

    public DataColumn getDateColumn() {
        return dateColumn;
    }

    public void setDateColumn(DataColumn dateColumn) {
        this.dateColumn = dateColumn;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public DataColumn getNameColumn() {
        return nameColumn;
    }

    public void setNameColumn(DataColumn nameColumn) {
        this.nameColumn = nameColumn;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    
    public void build(HTMLBuilderArgs args) {

        //add script files
        args.getScriptFiles().add("http://www.google.com/jsapi");
        args.getScriptFiles().add("http://maps.google.com/maps?file=api&amp;v=2&amp;key=ABQIAAAA-O3c-Om9OcvXMOJXreXHAxSsTL4WIgxhMZ0ZK_kHjwHeQuOD4xSbZqVZW2U_OWOxMp3YPfzZl2GavQ");
        
        //make sure this is set to true, so we can load API's
        args.getGoogleModules().add("motionchart");
        
        
        int nameColInt = getNameColumn().getOrder();
        int dateColInt = getDateColumn().getOrder();

        
        //get widgets dataset
        DataSet ds = args.getResults().get(this.getDataQuery().getName());
        ds = DataSetUtil.getFilteredDataSet(ds, getFilters());
        
        int seq = args.getSequence();
        //add function
        
        
        args.getPreScript().append("  var data"+seq+" = new google.visualization.DataTable(); ");
        args.getPreScript().append("  data"+seq+".addRows(").append(ds.getRows().size()).append("); ");
        args.getPreScript().append("  data"+seq+".addColumn('string', '").append(getNameColumn().getName()).append("'); ");
        args.getPreScript().append("  data"+seq+".addColumn('date', '").append(getDateColumn().getName()).append("'); ");
        
        for(int i=0; i< getAdditionalColumns().size();i++) {
            
            DataColumn additionalColumn = getAdditionalColumns().get(i);
            String additionalColumnType = "number";
            
            if(additionalColumn.getType() == DataType.STRING){
                additionalColumnType = "string";
            }
            args.getPreScript().append("  data"+seq+".addColumn('"+additionalColumnType+"', '").append(additionalColumn.getName()).append("'); ");
        }
        
        
        for(int i = 0; i< ds.getRows().size(); i++){
            
            Object[] tmpRow = (Object[])ds.getRows().get(i);
                args.getPreScript().append("  data"+seq+".setValue(")
                        .append(i).append(",0,'")
                        .append(tmpRow[nameColInt])
                        .append("'); ");
                
                Date rowDate = (Date)tmpRow[dateColInt];
                args.getPreScript().append("  data"+seq+".setValue(")
                        .append(i)
                        .append(",1,")
                        .append("new Date(")
                        .append(1900 + rowDate.getYear())
                        .append(",")
                        .append(rowDate.getMonth())
                        .append(",")
                        .append(rowDate.getDate())
                        .append(")")
                        .append("); ");
                for(int x=0; x< getAdditionalColumns().size();x++){
                        
                        DataColumn additionalColumn = getAdditionalColumns().get(x);
                        args.getPreScript().append("  data"+seq+".setValue(")
                                .append(i)
                                .append(","+(2+x)+",");
                                
                        if(additionalColumn.getType() == DataType.STRING){
                            args.getPreScript().append("'"+tmpRow[additionalColumn.getOrder()].toString()+"'");
                        } else{
                            args.getPreScript().append(tmpRow[additionalColumn.getOrder()].toString());                            
                        }
                                
                                
                        args.getPreScript().append("); ");
                }
        }
        
        args.getPreScript().append("   var chart"+seq+" = new google.visualization.MotionChart(document.getElementById('chart_div"+seq+"')); ");
        args.getPreScript().append("   chart"+seq+".draw(data"+seq+", {width: 600, height:300}); ");
        
        

        args.getHtml().append("<div id='chart_div"+seq+"' style='width: 600px; height: 300px;'></div>");


        
    }
    
  
    
}
