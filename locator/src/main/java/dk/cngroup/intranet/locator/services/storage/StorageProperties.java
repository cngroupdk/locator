package dk.cngroup.intranet.locator.services.storage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("storage")
public class StorageProperties {

    /**
     * Folder location for storing files
     */
    @Value("${internal.resources.files.location}")
    private String location;

    @Value("${internal.resources.files.location.temp}")
    private String tempLocation;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTempLocation() {
        return tempLocation;
    }

    public void setTempLocation(String tempLocation) {
        this.tempLocation = tempLocation;
    }

}
