package com.googlecode.g2re.util;

import com.googlecode.g2re.domain.DataType;
import java.util.Date;

/**
 * Used for type conversion of objects 
 * @author Brad Rydzewski
 */
public class DataTypeConversionUtil {

    
    public static int getCastedIntObject(Object value){
        
        if(value instanceof Integer){
            return (Integer)value;
        }
        else if(value instanceof String){
            return Integer.parseInt((String)value);
        }
        else if(value instanceof Double){
            return ((Double)value).intValue();
        }
        else if(value instanceof Float){
            return ((Float)value).intValue();
        }
        
        throw new RuntimeException("unable to convert object ["+value.toString()+"] to an integer");
    }
    
    public static String getCastedStringObject(Object value){
        return value.toString();
    }
    
    public static float getCastedFloatObject(Object value){
       
        if(value instanceof Float){
            return (Float)value;
        }
        else if(value instanceof String){
            return Float.parseFloat((String)value);
        }
        else if(value instanceof Double){
            return ((Double)value).floatValue();
        }
        else if(value instanceof Integer){
            return ((Integer)value).floatValue();
        }
        
        throw new RuntimeException("unable to convert object ["+value.toString()+"] to a float");  
    }
    
    public static double getCastedDoubleObject(Object value){
       
        if(value instanceof Double){
            return (Double)value;
        }
        else if(value instanceof String){
            return Double.parseDouble((String)value);
        }
        else if(value instanceof Float){
            return ((Float)value).doubleValue();
        }
        else if(value instanceof Integer){
            return ((Integer)value).doubleValue();
        }
        
        throw new RuntimeException("unable to convert object ["+value.toString()+"] to a double");  
    }  
    
    public static Date getCastedDateObject(Object value){
        
        if( !(value instanceof Date) )
            throw new RuntimeException("unable to convert object ["+value.toString()+"] to a Date");
        
        return (Date)value;
    }
    
    
    public static Object getCastedObjectToType(DataType type, Object value){
        
        switch(type){
            case DATE : return getCastedDateObject(value);
            case DOUBLE : return getCastedDoubleObject(value);
            case FLOAT : return getCastedFloatObject(value);
            case INTEGER : return getCastedIntObject(value);
            case STRING : return getCastedStringObject(value);
        }
        
        return value;
    } 

    public static int getSqlTypeFromDataTypeEnum(DataType type){
        
        switch(type){
            case DATE : return java.sql.Types.DATE;
            case DOUBLE : return java.sql.Types.DOUBLE;
            case FLOAT : return java.sql.Types.FLOAT;
            case INTEGER : return java.sql.Types.INTEGER;
            case STRING : return java.sql.Types.VARCHAR;
        }
        
        return java.sql.Types.OTHER;
    }
    
}