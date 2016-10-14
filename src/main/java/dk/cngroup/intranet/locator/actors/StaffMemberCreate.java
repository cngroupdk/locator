package dk.cngroup.intranet.locator.actors;


import javax.persistence.*;

/**
 * Factory class to generate different types of employees implementing the StaffMember interface.
 *
 * The StaffMemberCreate class implements JPA and is mapped to the 'employee' database table
 *
 * @author Victor Cano
 */

@Entity
@Inheritance
@DiscriminatorColumn(name="role", discriminatorType = DiscriminatorType.STRING)
@Table(name="employee")
public abstract class StaffMemberCreate implements StaffMember {

    @Id
    @Column(name = "employee_guid")
    private String employeeGuid;
    @Column(name = "employee_id")
    private String employeeId;
    @Column(name = "manager_id")
    private String managerId;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    private String abbreviation;
    @Column(updatable = false, insertable = false)
    private String role;
    private String email;
    private String location;
    private String detail;
    private String extension;
    @Column(name="voip")
    private String voIP;

    protected StaffMemberCreate(){

    }

    /**
     * Returns an Object implementing the StaffMember interface depending on
     * the field 'type' in the 'employee' database
     *
     * The type of object being returned at the moment are:
     * Developer
     * HR
     * Tester
     * @param actor
     * @return
     */
    public static StaffMember getStaffMember(StaffMember actor){

        StaffMember employee = null;

        try{

            switch(actor.getRole()){
                case "Developer":
                    employee = new Developer(actor);
                    break;
                case "HR":
                    employee = new HR(actor);
                    break;
                case "Tester":
                    employee = new Tester(actor);
                    break;
            }
        }
        catch ( Exception e){
            //TO DO: error handling
        }

        return employee;

    }

    @Override
    public boolean equals(Object obj){
        boolean result;
        if((obj == null) || (getClass() != obj.getClass())){
            result = false;
        } // end if
        else{
            StaffMemberCreate otherPerson = (StaffMemberCreate)obj;
            result = employeeId.equals(otherPerson.employeeId) && managerId.equals(otherPerson.managerId)
                    && firstName.equals(otherPerson.firstName) && lastName.equals(otherPerson.lastName)
                    && location.equals(otherPerson.location) && email.equals(otherPerson.email)
            ;
        } // end else

        return result;
    }

    @Override
    public int hashCode(){

        int result = 0;

        result = employeeId.hashCode()
                * managerId.hashCode()
                * firstName.hashCode()
                * lastName.hashCode()
                * location.hashCode()
                * email.hashCode();

        return result;

    }

    public String getEmployeeGuid() {
        return employeeGuid;
    }

    public void setEmployeeGuid(String employeeGuid) {
        this.employeeGuid = employeeGuid;
    }

    public String getVoIP() {
        return voIP;
    }

    public void setVoIP(String voIP) {
        this.voIP = voIP;
    }

    public String getId() {
        return employeeId;
    }

    public void setId(String id) {
        this.employeeId = id;
    }

    public void setManagerId(String id){
        this.managerId = id;
    }

    public String getManagerId(){
        return managerId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role){ this.role = role; }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

}
