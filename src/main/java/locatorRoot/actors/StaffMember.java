package locatorRoot.actors;

/**
 * Created by cano on 3.10.2016.
 */

public interface StaffMember {

    public String getVoIP();

    public void setVoIP(String voIP);

    public String getId();

    public void setId(String id);

    public void setManagerId(String id);

    public String getManagerId();

    public String getFirstName();

    public void setFirstName(String firstName);

    public String getLastName();

    public void setLastName(String lastName);

    public String getAbbreviation();

    public void setAbbreviation(String abbreviation);

    public String getExtension();

    public void setExtension(String extension);

    public String getRole();

    public void setRole(String role);

    public String getEmail();

    public void setEmail(String email);

    public String getLocation();

    public void setLocation(String location);

    public String getDetail();

    public void setDetail(String detail);

}
