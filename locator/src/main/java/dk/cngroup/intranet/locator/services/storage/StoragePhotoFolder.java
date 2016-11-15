package dk.cngroup.intranet.locator.services.storage;

import org.springframework.beans.factory.annotation.Value;

/**
 * Created by victorcano on 15/11/2016.
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
