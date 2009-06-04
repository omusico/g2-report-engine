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
 * <p>A widget for displaying data in Timeline format,
 * a time-based, animated flash Chart provided by the Google 
 * Visualization suite.
 * {@linkplain http://code.google.com/apis/visualization/documentation/gallery/annotatedtimeline.html}</p>
 * <p><u>Note:</u>
 * Because of Flash security settings, this (and all Flash-based visualizations) 
 * might not work correctly when accessed from a file location in the browser 
 * (e.g., file:///c:/webhost/myhost/myviz.html) rather than from a web server 
 * URL (e.g., http://www.myhost.com/myviz.html). This is typically a testing 
 * issue only. You can overcome this issue as described on the
 * <a href='http://www.macromedia.com/support/documentation/en/flashplayer/help/settings_manager02.html'>Macromedia Website</a></p>
 * @author Brad Rydzewski
 */
public class AnnotatedTimeline extends BoundElement {

    private float width = 700f;
    private float height = 240f;
    private boolean allowHTML;
    private boolean allValuesSuffix;
    private int annotationsWidth;
    private boolean displayAnnotations;
    private boolean displayAnnotationsFilter;
    private boolean displayExactValues;
    private boolean displayZoomButtons;
    private int min;
    private String legendPosition; //'sameRow' or 'newRow'
    private Date zoomEndTime;
    private Date zoomStartTime;
    private DataColumn dateColumn;
    private String wmode; //'opaque', 'window' or 'transparent'
    private List<DataColumn> additionalColumns = new ArrayList<DataColumn>();
    
    /*
    private JdbcQuery dataSet;

    
    public JdbcQuery getDataSet() {
        return dataSet;
    }

    
    public void setDataSet(JdbcQuery dataSet) {
        this.dataSet = dataSet;
    }*/
    
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

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    
    public void build(HTMLBuilderArgs args) {
        
        //add script files
        args.getScriptFiles().add("http://www.google.com/jsapi");

        //make sure this is set to true, so we can load API's
        args.getGoogleModules().add("annotatedtimeline");
        
        //get column where date value is stored
        int dateColInt = getDateColumn().getOrder();

        //get widgets dataset
        DataSet ds = args.getResults().get(this.getDataQuery().getName());//getDataSet().getName());
        ds = DataSetUtil.getFilteredDataSet(ds, getFilters());
        
        //get sequence for this object, to distinguish it from other DOM objects and JS variables
        int seq = args.getSequence();
        
        //TODO: This really needs to be more of a pivot table...shit!
        //create new data table
        args.getPreScript().append("  var data"+seq+" = new google.visualization.DataTable(); ");
        args.getPreScript().append("  data"+seq+".addRows(").append(ds.getRows().size()).append("); ");
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
                
                Date rowDate = (Date)tmpRow[dateColInt];
                args.getPreScript().append("  data"+seq+".setValue(")
                        .append(i)
                        .append(",0,")
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
                                .append(","+(1+x)+",");
                                
                        if(additionalColumn.getType() == DataType.STRING){
                            args.getPreScript().append("'"+tmpRow[additionalColumn.getOrder()].toString()+"'");
                        } else{
                            args.getPreScript().append(tmpRow[additionalColumn.getOrder()].toString());                            
                        }
                                
                                
                        args.getPreScript().append("); ");
                }
        }
        
        args.getPreScript().append("   var chart"+seq+" = new google.visualization.AnnotatedTimeLine(document.getElementById('chart_div"+seq+"')); ");
        args.getPreScript().append("   chart"+seq+".draw(data"+seq+", {width: "+getWidth()+", height:"+getHeight()+"}); ");
        args.getHtml().append("<div id='chart_div"+seq+"' style='width: "+getWidth()+"px; height: "+getHeight()+"px;'></div>");  

    }
    
    

}
