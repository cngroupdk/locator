package dk.cngroup.intranet.locator.actors;

/**
 * StaffMember interface to define common public methods
 *
 * @author Victor Cano
 */

public interface StaffMember {

    String getVoIP();

    void setVoIP(String voIP);

    String getEmployeeGuid();

    void setEmployeeGuid(String employeeGuid);

    String getId();

    void setId(String id);

    void setManagerId(String id);

    String getManagerId();

    String getFirstName();

    void setFirstName(String firstName);

    String getLastName();

    void setLastName(String lastName);

    String getAbbreviation();

    void setAbbreviation(String abbreviation);

    String getExtension();

    void setExtension(String extension);

    String getRole();

    void setRole(String role);

    String getEmail();

    void setEmail(String email);

    String getLocation();

    void setLocation(String location);

    String getDetail();

    void setDetail(String detail);

}
