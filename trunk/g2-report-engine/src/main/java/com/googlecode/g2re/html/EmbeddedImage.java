package com.googlecode.g2re.html;

import com.googlecode.g2re.html.HTMLBuilderArgs;

/**
 * Renders an image {@code <img>} using an embedded, base64 encoded image.
 * See {@linkplain http://en.wikipedia.org/wiki/Data_URI_scheme}
 * @author Brad Rydzewski
 */
public class EmbeddedImage extends Element {

    private String base64Image;
    private String alt;
    private ImageMimeType mimeType = ImageMimeType.gif;
    
    public enum ImageMimeType {
        png, gif, jpg
    }

    public String getAlt() {
        return alt;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }

    public String getBase64Image() {
        return base64Image;
    }

    public void setBase64Image(String base64Image) {
        this.base64Image = base64Image;
    }

    public ImageMimeType getMimeType() {
        return mimeType;
    }

    public void setMimeType(ImageMimeType mimeType) {
        this.mimeType = mimeType;
    }
    
    
    @Override
    public void build(HTMLBuilderArgs args) {
        args.getHtml()
                .append("<img ")
                .append("src='data:image/")
                .append(getMimeType().toString())
                .append(";base64,")
                .append(getBase64Image())
                .append("' ")
                .append("alt='")
                .append(getAlt())//alt
                .append("' />");
    }

}
