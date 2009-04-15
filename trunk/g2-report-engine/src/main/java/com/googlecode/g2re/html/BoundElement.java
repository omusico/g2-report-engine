package com.googlecode.g2re.html;

import com.googlecode.g2re.domain.DataQuery;
import com.googlecode.g2re.filter.Filter;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents an element that is bound to a DataQuery.
 * @author Brad Rydzewski
 */
public abstract class BoundElement extends Element {

    private DataQuery dataQuery;
    private List<Filter> filters;

    public DataQuery getDataQuery() {
        return dataQuery;
    }

    public void setDataQuery(DataQuery dataQuery) {
        this.dataQuery = dataQuery;
    }

    public List<Filter> getFilters() {
        return filters;
    }

    public void setFilters(List<Filter> filters) {
        this.filters = filters;
    }
    
    public void addFilter(Filter filter) {
        if(getFilters()==null)
            setFilters(new ArrayList<Filter>());
        
        getFilters().add(filter);
    }
    
}
