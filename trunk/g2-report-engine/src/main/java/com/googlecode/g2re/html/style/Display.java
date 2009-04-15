package com.googlecode.g2re.html.style;

/**
 * Sets how/if an element is displayed
 * @author Brad Rydzewski
 */
public enum Display implements StyledElement {
    /**
     * The element will not be displayed
     */
    none,
    /**
     * The element will be displayed as a block-level element, with a line 
     * before and after the element
     */
    block,
    /**
     * Default. The element will be displayed as an inline element, with no 
     * line break before or after the element
     */
    inline,
    /**
     * The element will be displayed as a list
     */
    list_item,
    /**
     * The element will be displayed as a block table (like <table>), with a 
     * line break before and after the table
     */
    table,
    /**
     * The element will be displayed as an inline table (like <table>), 
     * with no line break before or after the table
     */
    inline_table,
    /**
     * The element will be displayed as a table cell (like <td> and <th>)
     */
    table_cell,
    /**
     * The element will be displayed as a column of cells (like <col>)
     */
    table_column,
    /**
     * The element will be displayed as a table caption (like <caption>)
     */
    table_caption,
    /**
     * The element will be displayed as a table row (like <tr>)
     */
    table_row,
    /**
     * The element will be displayed as a group of one or more columns 
     * (like <colgroup>)
     */
    table_column_group,
    /**
     * The element will be displayed as a group of one or more rows 
     * (like <tbody>)
     */
    table_row_group;
    
    /**
     * Converts the Enum value to a CSS String
     * @return
     */
    public String toCSS(){
        return new StringBuilder()
                .append("display:")
                .append(this.toString().replace("_", "-"))
                .append(";").toString();
    }
}
