package com.googlecode.g2re.html.google;

import com.googlecode.g2re.Settings;
import com.googlecode.g2re.domain.DataColumn;
import com.googlecode.g2re.html.BoundElement;
import com.googlecode.g2re.html.HTMLBuilderArgs;
import com.googlecode.g2re.jdbc.DataSet;
import com.googlecode.g2re.util.DataSetUtil;

/**
 * Displays a map using the Google Maps API. Data values are displayed as points 
 * on the map. Data values are input as addresses. This widget is provided as 
 * part of the Google Visualization API.
 * {@linkplain http://code.google.com/apis/visualization/documentation/gallery/map.html}
 * @author Brad Rydzewski
 */
public class Map extends BoundElement {
    
    private float width = 400f;
    private float height = 240f;
    private MapType mapType = MapType.hybrid;
    private DataColumn addressColumn;
    private DataColumn descriptionColumn;
    private boolean showTip = false;
    /*private JdbcQuery dataSet;

    
    public JdbcQuery getDataSet() {
        return dataSet;
    }

    
    public void setDataSet(JdbcQuery dataSet) {
        this.dataSet = dataSet;
    }*/

    public DataColumn getAddressColumn() {
        return addressColumn;
    }

    public void setAddressColumn(DataColumn addressColumn) {
        this.addressColumn = addressColumn;
    }

    public DataColumn getDescriptionColumn() {
        return descriptionColumn;
    }

    public void setDescriptionColumn(DataColumn descriptionColumn) {
        this.descriptionColumn = descriptionColumn;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public MapType getMapType() {
        return mapType;
    }

    public void setMapType(MapType mapType) {
        this.mapType = mapType;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public boolean isShowTip() {
        return showTip;
    }

    public void setShowTip(boolean showTip) {
        this.showTip = showTip;
    }

    
    public void build(HTMLBuilderArgs args) {
        
        //add script files
        args.getScriptFiles().add("http://www.google.com/jsapi");
        args.getScriptFiles().add("http://maps.google.com/maps?file=api&amp;v=2&amp;key="+Settings.get().getGoogleMapKey());//ABQIAAAA-O3c-Om9OcvXMOJXreXHAxSsTL4WIgxhMZ0ZK_kHjwHeQuOD4xSbZqVZW2U_OWOxMp3YPfzZl2GavQ");
        
        //make sure this is set to true, so we can load API's
        args.getGoogleModules().add("map");
        
        //get column where date value is stored
        int addrColInt = getAddressColumn().getOrder();
        int descColInt = getDescriptionColumn().getOrder();

        //get widgets dataset
        DataSet ds = args.getResults().get(this.getDataQuery().getName());
        ds = DataSetUtil.getFilteredDataSet(ds, getFilters());
        
        //get sequence for this object, to distinguish it from other DOM objects and JS variables
        int seq = args.getSequence();
        
        //create new data table
        args.getPreScript().append("  var data"+seq+" = new google.visualization.DataTable(); ");
        args.getPreScript().append("  data"+seq+".addColumn('string', 'Address'); ");
        args.getPreScript().append("  data"+seq+".addColumn('string', 'Description'); ");
        args.getPreScript().append("  data"+seq+".addRows("+ ds.getRows().size() +"); ");
        
        for(int i=0; i<ds.getRows().size(); i++){
            //populate data table
            Object[] tmpRow = (Object[])ds.getRows().get(i); //gets first tmpRow... assumes scalar execution...
            
            args.getPreScript().append("  data"+seq+".setCell("+i+",0,'").append(tmpRow[addrColInt]).append("'); ");
            args.getPreScript().append("  data"+seq+".setCell("+i+",1,'").append(tmpRow[descColInt]).append("'); ");
        
        }
        
        //create and draw the object
        args.getPreScript().append("   var chart"+seq+" = new google.visualization.Map(document.getElementById('map_div"+seq+"')); ");
        args.getPreScript().append("   chart"+seq+".draw(data"+seq+", {showTip: "+isShowTip()+", mapType: '"+getMapType()+"'}); ");        
       
        //add DIV container to body of page
        args.getHtml().append("<div id='map_div"+seq+"' style='width: "+getWidth()+"px; height: "+getHeight()+"px;'></div>");  
    }

    
}
