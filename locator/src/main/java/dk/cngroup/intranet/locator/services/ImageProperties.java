package dk.cngroup.intranet.locator.services;


/**
 * Created by victorcano on 22/11/2016.
 */

public class ImageProperties {

    private String existence;
    private String extension;
    private String filename;

    public ImageProperties(){

    }

    public String getExistence() {
        return this.existence;
    }

    public void setExistence(String s) {
        this.existence = s;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

}