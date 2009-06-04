package com.googlecode.g2re.html.google;

import com.googlecode.g2re.Settings;
import com.googlecode.g2re.domain.DataColumn;
import com.googlecode.g2re.domain.JdbcQuery;
import com.googlecode.g2re.html.BoundElement;
import com.googlecode.g2re.html.HTMLBuilderArgs;
import com.googlecode.g2re.jdbc.DataSet;
import com.googlecode.g2re.util.DataSetUtil;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * An intensity map that highlights regions or countries based on relative values. 
 * The map is rendered in the browser using images from the google maps API. This widget 
 * is provided as part of the Google Visualization API.
 * 
 * The data format is as follows: the first column should be a string, and contain 
 * country ISO codes or USA state codes. Any number of columns can follow, all 
 * must be numeric. Each column is displayed as a separate map tab. 
 * {@linkplain http://code.google.com/apis/visualization/documentation/gallery/intensitymap.html}
 * @author Brad Rydzewski
 */
public class IntensityMap extends BoundElement {

    private float width = 440f;
    private float height = 240f;
    private DataColumn regionColumn;
    private DataColumn valueColumn;
    private IntensityMapRegion region = IntensityMapRegion.world;
    private boolean showOneTab = false;
    
    
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

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public IntensityMapRegion getRegion() {
        return region;
    }

    public void setRegion(IntensityMapRegion region) {
        this.region = region;
    }

    public DataColumn getRegionColumn() {
        return regionColumn;
    }

    public void setRegionColumn(DataColumn regionColumn) {
        this.regionColumn = regionColumn;
    }

    public boolean isShowOneTab() {
        return showOneTab;
    }

    public void setShowOneTab(boolean showOneTab) {
        this.showOneTab = showOneTab;
    }

    public DataColumn getValueColumn() {
        return valueColumn;
    }

    public void setValueColumn(DataColumn valueColumn) {
        this.valueColumn = valueColumn;
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
        args.getScriptFiles().add("http://maps.google.com/maps?file=api&amp;v=2&amp;key="+Settings.get().getGoogleMapKey());//ABQIAAAA-O3c-Om9OcvXMOJXreXHAxSsTL4WIgxhMZ0ZK_kHjwHeQuOD4xSbZqVZW2U_OWOxMp3YPfzZl2GavQ");
        
        //make sure this is set to true, so we can load API's
        args.getGoogleModules().add("intensitymap");
        
        int regionColInt = getRegionColumn().getOrder();
        int valueColInt = getValueColumn().getOrder();
        boolean hasAdditionalColumns = getAdditionalColumns() != null;

        //get widgets dataset
        DataSet ds = args.getResults().get(this.getDataQuery().getName());
        ds = DataSetUtil.getFilteredDataSet(ds, getFilters());
        
        int seq = args.getSequence();
        //add function
        
        args.getPreScript().append("  var data"+seq+" = new google.visualization.DataTable(); ");
        args.getPreScript().append("  data"+seq+".addRows(").append(ds.getRows().size()).append("); ");
        args.getPreScript().append("  data"+seq+".addColumn('string', '").append(getRegionColumn().getName()).append("'); ");
        args.getPreScript().append("  data"+seq+".addColumn('number', '").append(getValueColumn().getName()).append("'); ");
        
        if(hasAdditionalColumns){
            for(int i=0; i< getAdditionalColumns().size();i++) {

                DataColumn additionalColumn = getAdditionalColumns().get(i);
                args.getPreScript().append("  data"+seq+".addColumn('number', '").append(additionalColumn.getName()).append("'); ");
            }
        }
        
        
        for(int i = 0; i< ds.getRows().size(); i++){
            
            Object[] tmpRow = (Object[])ds.getRows().get(i);
            
            args.getPreScript().append("  data"+seq+".setValue(")
                        .append(i).append(",0,'")
                        .append(tmpRow[regionColInt])
                        .append("'); ");
                
            args.getPreScript().append("  data"+seq+".setValue(")
                        .append(i).append(",1,")
                        .append(tmpRow[valueColInt])
                        .append("); ");
            
            if(hasAdditionalColumns){
                
                for(int x=0; x< getAdditionalColumns().size();x++){

                        DataColumn additionalColumn = getAdditionalColumns().get(x);
                        int additionalColumnInt = additionalColumn.getOrder();

                        args.getPreScript().append("  data"+seq+".setValue(")
                                    .append(i).append(","+(2+x)+",")
                                    .append(tmpRow[additionalColumnInt])
                                    .append("); ");
                }
            }
        }
        
        //create and draw the object
        args.getPreScript().append("   var chart"+seq+" = new google.visualization.IntensityMap(document.getElementById('map_div"+seq+"')); ");
        args.getPreScript().append("   chart"+seq+".draw(data"+seq+", {region: '"+getRegion()+"', showOneTab:" + isShowOneTab() + " }); ");
        
        //add DIV container to body of page
        args.getHtml().append("<div id='map_div"+seq+"' style='width: "+getWidth()+"px; height: "+getHeight()+"px;'></div>");  

    }
    
    
}
