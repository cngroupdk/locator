package dk.cngroup.intranet.locator.services.storage;

import org.springframework.beans.factory.annotation.Value;

/**
 *
 * The StoragePhotoFolder class is used by the API to provide information on the location of the employeee photo folder
 *
 * @author Victor Cano
 */
public class StoragePhotoFolder {

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    private String url;

    public StoragePhotoFolder(){

    }
}
