package com.googlecode.g2re.html;

import com.googlecode.g2re.domain.*;
import java.util.Comparator;

/**
 * 
 * @author Brad Rydzewski
 */
public class DataGroupComparator implements Comparator<Object[]>{

    private DataGroup dataGroup;

    public DataGroup getDataGroup() {
        return dataGroup;
    }

    public void setDataGroup(DataGroup dataGroup) {
        this.dataGroup = dataGroup;
    }
    
    @Override
    public int compare(Object[] o1, Object[] o2) {
        

        Comparable a = (Comparable)o1[dataGroup.getDataColumn().getOrder()];
        Comparable b = (Comparable)o2[dataGroup.getDataColumn().getOrder()];
        
        if(a==null && b==null){
            return 0;
        } else if(a==null){
            return -1;
        } else if(b==null){
            return 1;
        } else if(dataGroup.getSortOrder() == SortOrder.Ascending){
            return a.compareTo(b);
        } else 
            return b.compareTo(a);
    }
}
