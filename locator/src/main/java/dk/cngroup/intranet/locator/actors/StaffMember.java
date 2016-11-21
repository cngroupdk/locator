package dk.cngroup.intranet.locator.actors;


import javax.persistence.*;

/**
 *
 * The StaffMember class implements JPA and is mapped to the 'employee' database table
 *
 * @author Victor Cano
 */

@Entity
@Table(name="employees")
public class StaffMember{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "employees_employee_guid_seq")
    @SequenceGenerator(name = "employees_employee_guid_seq", sequenceName = "employees_employee_guid_seq", allocationSize=1)
    @Column(name = "employee_guid")
    private Integer employeeGuid;
    @Column(name = "employee_id")
    private String employeeId;
    @Column(name = "manager_id")
    private String managerId;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    private String abbreviation;
    private String role;
    private String email;
    private String location;

    public StaffMember(){

    }

    public StaffMember(StaffMember sm){
        this.employeeId = sm.getId();
        this.managerId = sm.getManagerId();
        this.firstName = sm.getFirstName();
        this.lastName = sm.getLastName();
        this.abbreviation = sm.getAbbreviation();
        this.role = sm.getRole();
        this.email = sm.getEmail();
        this.location = sm.getLocation();
    }

    @Override
    public boolean equals(Object obj){
        boolean result;
        if((obj == null) || (getClass() != obj.getClass())){
            result = false;
        } // end if
        else{
            StaffMember otherPerson = (StaffMember)obj;
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

    public Integer getEmployeeGuid() {
        return employeeGuid;
    }

    public void setEmployeeGuid(Integer employeeGuid) {
        this.employeeGuid = employeeGuid;
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

}
