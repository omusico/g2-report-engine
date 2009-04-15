package com.googlecode.g2re.excel;

/**
 * An enumeration type used to define the type of cell that will
 * be rendered in an Excel spreadsheet.
 * @author Brad Rydzewski
 */
public enum CellType {
    /**
     * A hard-coded, static line of text.
     */
    STATIC_TEXT,
    /**
     * Data driven text, bound to a data column.
     */
    DATABOUND_TEXT,
    /**
     * A URL, like http://www.google.com.
     */
    HYPERLINK,
    /**
     * An Excel Formula, ie =(A1+B1)*2+SUM(A1:A9).
     */
    FORMULA
}
