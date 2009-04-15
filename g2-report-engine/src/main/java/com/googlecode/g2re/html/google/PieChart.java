package com.googlecode.g2re.html.google;

import com.googlecode.g2re.domain.DataColumn;
import com.googlecode.g2re.domain.JdbcQuery;
import com.googlecode.g2re.html.BoundElement;
import com.googlecode.g2re.html.HTMLBuilderArgs;
import com.googlecode.g2re.jdbc.DataSet;
import com.googlecode.g2re.util.DataSetUtil;

/**
 * A pie chart that is rendered within the browser using SVG or VML. 
 * Displays tips when clicking on slices. Animates slices when clicking 
 * on legend entries. Provided as part of the Google Visualization API.
 * {@linkplain http://code.google.com/apis/visualization/documentation/gallery/piechart.html}
 * @author Brad Rydzewski
 */
public class PieChart extends BoundElement {
    
    private float width = 400f;
    private float height = 240f;
    private DataColumn nameColumn;
    private DataColumn valueColumn;
    private boolean threeDimensional = true;
    private String title = "";
    private JdbcQuery dataSet;

   
    public JdbcQuery getDataSet() {
        return dataSet;
    }

    public void setDataSet(JdbcQuery dataSet) {
        this.dataSet = dataSet;
    }
    
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isThreeDimensional() {
        return threeDimensional;
    }

    public void setThreeDimensional(boolean threeDimensional) {
        this.threeDimensional = threeDimensional;
    }

    @Override
    public void build(HTMLBuilderArgs args) {

        //add script files
        args.getScriptFiles().add("http://www.google.com/jsapi");
        
        //add the google visualization module for this widget
        args.getGoogleModules().add("piechart");
        
        //get column where date value is stored
        int nameColInt = getNameColumn().getOrder();
        int valueColInt = getValueColumn().getOrder();

        //get widgets dataset
        DataSet ds = args.getResults().get(getDataSet().getName());
        ds = DataSetUtil.getFilteredDataSet(ds, getFilters());
        
        //get sequence for this object, to distinguish it from other DOM objects and JS variables
        int seq = args.getSequence();
        
        //create new data table
        args.getPreScript().append("    var data").append(seq).append(" = new google.visualization.DataTable(); \n");
        args.getPreScript().append("    data").append(seq).append(".addColumn('string', 'Label'); \n");
        args.getPreScript().append("    data").append(seq).append(".addColumn('number', 'Value'); \n");
        args.getPreScript().append("    data").append(seq).append(".addRows(").append(ds.getRows().size()).append("); \n");
        
        for(int i=0; i<ds.getRows().size(); i++){
            //populate data table
            Object[] tmpRow = (Object[])ds.getRows().get(i); 
            
            args.getPreScript().append("    data").append(seq).append(".setValue("+i+",0,'").append(tmpRow[nameColInt]).append("'); \n");
            args.getPreScript().append("    data").append(seq).append(".setValue("+i+",1,").append(tmpRow[valueColInt]).append("); \n");
        
        }
        
        //create and draw the object
        args.getPreScript().append("    var chart").append(seq).append(" = new google.visualization.PieChart(document.getElementById('chart_div").append(seq).append("')); \n");
        args.getPreScript().append("    chart").append(seq).append(".draw(data").append(seq).append(", {width: ").append(getWidth()).append(", height:").append(getHeight()).append(", is3D:").append(isThreeDimensional());
        if(getTitle().isEmpty()==false)
            args.getPreScript().append(", " + "title:'").append(getTitle()).append("'");
        args.getPreScript().append("}); \n");
        
        //add DIV container to body of page
        args.getHtml().append("<div id='chart_div"+seq+"' style='width: "+getWidth()+"px; height: "+getHeight()+"px;'></div> \n");  
        
    }
    
    
    
}
