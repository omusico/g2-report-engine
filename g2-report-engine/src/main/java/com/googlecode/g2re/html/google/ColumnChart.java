package com.googlecode.g2re.html.google;

import com.googlecode.g2re.domain.DataColumn;
import com.googlecode.g2re.domain.JdbcQuery;
import com.googlecode.g2re.html.BoundElement;
import com.googlecode.g2re.html.HTMLBuilderArgs;
import com.googlecode.g2re.jdbc.DataSet;
import com.googlecode.g2re.util.DataSetUtil;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 *
 * @author Brad
 */
public class ColumnChart extends BoundElement {

    private DataColumn valueSeriesColumn;    //Y Axis
    private DataColumn categorySeriesColumn; //X Axis
    private DataColumn seriesGroupColumn;    //Optional Y Series Grouping
    private String title = "";
    private boolean threeDimensional = true;
    private boolean stacked = false;
    private float width = 400;
    private float height = 240;
    private JdbcQuery dataSet;
    private LegendLocation legendLocation = LegendLocation.right;

    public JdbcQuery getDataSet() {
        return dataSet;
    }

    public void setDataSet(JdbcQuery dataSet) {
        this.dataSet = dataSet;
    }
    
    public DataColumn getCategorySeriesColumn() {
        return categorySeriesColumn;
    }

    public void setCategorySeriesColumn(DataColumn categorySeriesColumn) {
        this.categorySeriesColumn = categorySeriesColumn;
    }

    public DataColumn getSeriesGroupColumn() {
        return seriesGroupColumn;
    }

    public void setSeriesGroupColumn(DataColumn seriesGroupColumn) {
        this.seriesGroupColumn = seriesGroupColumn;
    }

    public DataColumn getValueSeriesColumn() {
        return valueSeriesColumn;
    }

    public void setValueSeriesColumn(DataColumn valueSeriesColumn) {
        this.valueSeriesColumn = valueSeriesColumn;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public boolean isThreeDimensional() {
        return threeDimensional;
    }

    public void setThreeDimensional(boolean threeDimensional) {
        this.threeDimensional = threeDimensional;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public LegendLocation getLegendLocation() {
        return legendLocation;
    }

    public void setLegendLocation(LegendLocation legendLocation) {
        this.legendLocation = legendLocation;
    }

    public boolean isStacked() {
        return stacked;
    }

    public void setStacked(boolean stacked) {
        this.stacked = stacked;
    }

    
    @Override
    public void build(HTMLBuilderArgs args) {
        
        //add script files
        args.getScriptFiles().add("http://www.google.com/jsapi");
        
        //add the google visualization module for this widget
        args.getGoogleModules().add("columnchart");

        //gets a dataset and filters it, if applicable
        DataSet ds = args.getResults().get(getDataSet().getName());
        ds = DataSetUtil.getFilteredDataSet(ds, getFilters());
        
        //create Sets to "pivot" the data
        Map seriesGroupMap = new HashMap(); //Y Axis Grouping (Optional)
        Map categorySeriesMap = new HashMap(); //X Axis
        
        //put column order values in tmp variables for later reference
        int valueSeriesColInt = getValueSeriesColumn().getOrder();
        int categorySeriesColInt = getCategorySeriesColumn().getOrder();
        int seriesGroupColInt = (getSeriesGroupColumn()==null)?-1:getSeriesGroupColumn().getOrder(); //optional

        //iterate through list to get unique values for dimensions
        for(int i=0; i< ds.getRows().size(); i++){

            //get data row
            //get axis values and put into temp variables
            Object[] tmpRow = (Object[])ds.getRows().get(i);
            String categorySeriesObj = tmpRow[categorySeriesColInt].toString();
            
            //put category series in Map
            if(!categorySeriesMap.containsKey(categorySeriesObj)) {
                categorySeriesMap.put(categorySeriesObj, 
                        categorySeriesMap.size());
            }            
            
            //put category series in Map, assuming provided
            // used primarily to identify labels in Legend
            if(seriesGroupColInt > -1) {
                
                //get series group value
                String seriesGroupObj = tmpRow[seriesGroupColInt].toString();
            
                //add item to category series map, assuming not already there
                if(!seriesGroupMap.containsKey(seriesGroupObj)) {
                    seriesGroupMap.put(seriesGroupObj, 
                        seriesGroupMap.size());
                }
            }
        }
        
        //now lets build the chart
        int seq = args.getSequence();
        args.getPreScript().append("  var data"+seq+" = new google.visualization.DataTable(); \n");
        args.getPreScript().append("  data"+seq+".addRows(").append(categorySeriesMap.size()).append("); \n");
        args.getPreScript().append("  data"+seq+".addColumn('string', '").append(getCategorySeriesColumn().getName()).append("'); \n");
        
        //add column header
        //if series group not specified, use the value column name by default
        if(seriesGroupMap.isEmpty()) {
            args.getPreScript().append("  data"+seq+".addColumn('number', '").append(getValueSeriesColumn().getName()).append("'); \n");
        } else {
            //iterate through list of series groups (Y) and list
            Iterator seriesGroupMapIterator = seriesGroupMap.keySet().iterator();
            while( seriesGroupMapIterator.hasNext() ){
                String seriesGroupValue =  seriesGroupMapIterator.next().toString();
                args.getPreScript().append("  data").append(seq).append(".addColumn('number','").append(seriesGroupValue).append("'); \n");
            }
        }
        
        //iterate through list of series groups (Y) and list
        //adds each group as a "0" tmpRow value. gets the order from the hashmap value via lookup
        //Iterator categorySeriesMapIterator = categorySeriesMap.keySet().iterator();
        //while( categorySeriesMapIterator.hasNext() ){
        //    String categorySeriesValue =  categorySeriesMapIterator.next().toString();
        //    args.getPreScript().append("  data").append(seq).append(".setValue(").append(categorySeriesMap.get(categorySeriesValue)).append(",0,'").append(categorySeriesValue).append("'); \n");
        //}
        
        //now add each value to data table
        for(int i=0; i<ds.getRows().size(); i++) {
            
            Object[] tmpRow = (Object[])ds.getRows().get(i);
            
            Object valueSeriesObj = tmpRow[valueSeriesColInt];
            Object categorySeriesObj = tmpRow[categorySeriesColInt];
            int categorySeriesInt = (Integer)categorySeriesMap.get(categorySeriesObj.toString());
            
            
            if(seriesGroupMap.isEmpty()) {
                args.getPreScript().append("  data").append(seq).append(".setValue(").append(categorySeriesInt).append(",").append(0).append(",'").append(categorySeriesObj.toString()).append("'); \n");
                args.getPreScript().append("  data").append(seq).append(".setValue(").append(categorySeriesInt).append(",").append(1).append(",").append(valueSeriesObj.toString()).append("); \n");
            } else {
                //iterate through list of series groups (Y) and list
                //Iterator seriesGroupMapIterator = seriesGroupMap.keySet().iterator();
                //while( seriesGroupMapIterator.hasNext() ){
                
                Object seriesGroupObj = tmpRow[seriesGroupColInt];
                int seriesGroupInt = (Integer)seriesGroupMap.get(seriesGroupObj.toString());
                args.getPreScript().append("  data").append(seq).append(".setValue(").append(categorySeriesInt).append(",").append(seriesGroupInt).append(",").append(valueSeriesObj.toString()).append("); \n");
                //}
            }            

            //create js string to add value
            //args.getPreScript().append("  data").append(seq).append(".setValue(").append(categorySeriesInt).append(",").append(seriesGroupInt).append(",").append(valueSeriesString).append("); \n");
        }
        
        
        
        
        //create and draw the object
        args.getPreScript().append("   var chart"+seq+" = new google.visualization.ColumnChart(document.getElementById('chart_div"+seq+"')); \n");
        args.getPreScript().append("   chart"+seq+".draw(data"+seq+", { isStacked:"+isStacked()+",is3D: " + isThreeDimensional() + ",title:'"+getTitle()+"',legend:'"+getLegendLocation()+"'}); \n");
        
        //add DIV container to body of page
        args.getHtml().append("<div id='chart_div"+seq+"' style='width: "+getWidth()+"px; height: "+getHeight()+"px;'></div> \n");  

    }
    
    
}
