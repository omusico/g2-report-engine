package com.googlecode.g2re.html;

import com.googlecode.g2re.html.style.Style;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents an HTML document, with head, script, style, and body tags. 
 * Contains one or many child elements, as specified in the ReportDefinition
 * @author Brad Rydzewski
 */
public class WebPage  {

    private List<Style> styles = null;
    private boolean renderAsDiv;
    private List<Element> childElements;
    
    

    public List<Style> getStyles() {
        return styles;
    }

    public void setStyles(List<Style> styles) {
        this.styles = styles;
    }
    
    public WebPage addStyle(Style style){
        if(getStyles()==null)
            setStyles(new ArrayList<Style>());
        
        getStyles().add(style);
        return this;
    }

    public List<Element> getChildElements() {
        return childElements;
    }

    public void setChildElements(List<Element> childElements) {
        this.childElements = childElements;
    }

    public boolean isRenderAsDiv() {
        return renderAsDiv;
    }

    public void setRenderAsDiv(boolean renderAsDiv) {
        this.renderAsDiv = renderAsDiv;
    }
    
    public void addChildElements(Element... elements){
        if(getChildElements()==null)
            setChildElements(new ArrayList<Element>());
        
        for(Element element : elements){
            getChildElements().add(element);
        }
    }
    
    public WebPage addChildElement(Element element){
        if(getChildElements()==null)
            setChildElements(new ArrayList<Element>());
        
        getChildElements().add(element);
        return this;
    }
    
    
    public String build(HTMLBuilderArgs args) {
        
        for(Element element : getChildElements()){
            element.build(args);
        }
        
        StringBuilder document = new StringBuilder();
        document.append("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">\n");
        document.append("<html>");
        document.append("<head>");
        
        if(getStyles()!=null){
            document.append("<style> ");
            for(Style style : getStyles()){
                document.append(".");
                document.append(style.getName());
                document.append(" {");
                document.append(style.toCSS());
                document.append(" } ");
            }
            document.append("</style>");
        }
        
        
        //render scripts that need to be imported, using <script/>
        String[] scriptFiles = new String[args.getScriptFiles().size()];
        args.getScriptFiles().toArray(scriptFiles);
        for (int i = 0; i < scriptFiles.length; i++) {
            document.append(" <script src='");
            document.append(scriptFiles[i]);
            document.append("'></script>");
        }
        //render modules (good visualization) that need to be imported, using google.load
        String[] modules = new String[args.getGoogleModules().size()];
        args.getGoogleModules().toArray(modules);
        
        //if there are moduels to load, we'll load them and add js init()
        if (modules.length > 0) {
            document.append(" <script>\n");
            document.append("  google.setOnLoadCallback(init);\n");
            document.append("  google.load('visualization', '1', {'packages':[");
            for (int i = 0; i < modules.length; i++) {
                document.append("'").append(modules[i]).append("'");
                if (i < modules.length - 1) {
                    document.append(",");
                }
            }
            document.append("]});\n");
        } else {
            document.append("<script>");
        }
        
        document.append(" function init(){ ");
        document.append(args.getPreScript());
        document.append(" } ");
        document.append("</script>");
        document.append("</head>");
        document.append("<body>");
        document.append(args.getHtml());
        document.append("</body>");
        document.append("<script>");
        document.append(args.getPostScript());
        document.append("</script>");
        document.append("</html>");
        return document.toString();
    }
}
