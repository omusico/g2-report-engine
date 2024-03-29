package com.googlecode.g2re.html.google;

import com.googlecode.g2re.domain.DataColumn;
import com.googlecode.g2re.domain.JdbcQuery;
import com.googlecode.g2re.html.BoundElement;
import com.googlecode.g2re.html.HTMLBuilderArgs;
import com.googlecode.g2re.jdbc.DataSet;
import com.googlecode.g2re.util.DataSetUtil;

/**
 * <p>A widget to render one or more gauges using SVG or VML.
 * Widget is provided as part of the Google Visualization suite.
 * {@linkplain http://code.google.com/apis/visualization/documentation/gallery/gauge.html}</p>
 * @author Brad Rydzewski
 */
public class Gauge extends BoundElement {

    private float width = 400f;
    private float height = 120f;
    private DataColumn nameColumn;
    private DataColumn valueColumn;

    
    public DataColumn getValueColumn() {
        return valueColumn;
    }

    public void setValueColumn(DataColumn valueColumn) {
        this.valueColumn = valueColumn;
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

        //make sure this is set to true, so we can load API's
        args.getGoogleModules().add("gauge");
        
        //get column where date value is stored
        int nameColInt = getNameColumn().getOrder();
        int valueColInt = getValueColumn().getOrder();

        //get widgets dataset
        DataSet ds = args.getResults().get(this.getDataQuery().getName());//args.getResults().get(getDataSet().getName());
        ds = DataSetUtil.getFilteredDataSet(ds, getFilters());
        
        //get sequence for this object, to distinguish it from other DOM objects and JS variables
        int seq = args.getSequence();
        
        //create new data table
        args.getPreScript().append("  var data"+seq+" = new google.visualization.DataTable(); ");
        args.getPreScript().append("  data"+seq+".addColumn('string', 'Label'); ");
        args.getPreScript().append("  data"+seq+".addColumn('number', 'Value'); ");
        args.getPreScript().append("  data"+seq+".addRows(1); ");
        
        //populate data table
        Object[] tmpRow = (Object[])ds.getRows().get(0); //gets first tmpRow... assumes scalar execution...
        args.getPreScript().append("  data"+seq+".setValue(0,0,'").append(tmpRow[nameColInt]).append("'); ");
        args.getPreScript().append("  data"+seq+".setValue(0,1,").append(tmpRow[valueColInt]).append("); ");
        
        //create and draw the object
        args.getPreScript().append("   var chart"+seq+" = new google.visualization.Gauge(document.getElementById('chart_div"+seq+"')); ");
        args.getPreScript().append("   chart"+seq+".draw(data"+seq+", {width: "+getWidth()+", height:"+getHeight()+"}); ");
        
        //add DIV container to body of page
        args.getHtml().append("<div id='chart_div"+seq+"' style='width: "+getWidth()+"px; height: "+getHeight()+"px;'></div>");  
        
    }
    
    
    
}
