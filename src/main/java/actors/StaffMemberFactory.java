package actors;


import javax.persistence.*;
import java.sql.ResultSet;

/**
 * Created by cano on 29.9.2016.
 */

@MappedSuperclass
public abstract class StaffMemberFactory implements StaffMember {

    @Id
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
    private String detail;
    private String extension;
    @Column(name="voip")
    private String voIP;

    public StaffMemberFactory()
    {
    }

    public static StaffMember setCommonProperties(ResultSet resultSet, StaffMember employee){

        try {
            String employee_id = resultSet.getString("employee_id");
            employee.setId(employee_id != null ? employee_id : null);

            String manager_id = resultSet.getString("manager_id");
            employee.setManagerId(manager_id != null ? manager_id : null);

            String first_name = resultSet.getString("first_name");
            employee.setFirstName(first_name != null ? first_name : null);

            String last_name = resultSet.getString("last_name");
            employee.setLastName(last_name != null ? last_name : null);

            String abbreviation = resultSet.getString("abbreviation");
            employee.setAbbreviation(abbreviation != null ? abbreviation : null);

            String role = resultSet.getString("role");
            employee.setRole(role!= null ? role : null);

            String email = resultSet.getString("email");
            employee.setEmail(email!= null ? email : null);

            String location = resultSet.getString("location");
            employee.setLocation(location!= null ? location : null);

            String detail = resultSet.getString("detail");
            employee.setDetail(detail!= null ? detail : null);
        }catch(Exception e){
            //TO DO exception handling
        }

        return employee;
    }

    public static StaffMember getStaffMember(ResultSet resultSet){

        StaffMember employee = null;

        try{
            String type = resultSet.getString("role");

            switch(type){
                case "Developer":
                    employee = new Developer();
                    employee = setCommonProperties(resultSet, employee);
                    //capacity to add role specific logic here
                    break;
                case "HR":
                    employee = new HR();
                    employee = setCommonProperties(resultSet, employee);
                    break;
                case "Tester":
                    employee = new Tester();
                    employee = setCommonProperties(resultSet, employee);
                    break;
            }
        }
        catch ( Exception e){
            //TO DO: error handling
        }

        return employee;

    }

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

    public StaffMemberFactory(StaffMemberFactory p){
        setFirstName(p.firstName);
        setLastName(p.lastName);
        setAbbreviation(p.abbreviation);
        setExtension(p.extension);
        setRole(p.role);
        setEmail(p.email);
        setLocation(p.location);
        setDetail(p.detail);
        setManagerId(p.managerId);
    }

    public StaffMemberFactory(String firstName, String lastName, String abbreviation,
                              String extension, String role, String email, String location,
                              String detail, String managerId){

        setFirstName(firstName);
        setLastName(lastName);
        setAbbreviation(abbreviation);
        setExtension(extension);
        setRole(role);
        setEmail(email);
        setLocation(location);
        setDetail(detail);
        setManagerId(managerId);
    }

    @Override
    public boolean equals(Object obj){
        boolean result;
        if((obj == null) || (getClass() != obj.getClass())){
            result = false;
        } // end if
        else{
            StaffMemberFactory otherPerson = (StaffMemberFactory)obj;
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

        result = employeeId.hashCode() * managerId.hashCode() * firstName.hashCode() * lastName.hashCode()
                * location.hashCode() * email.hashCode();

        return result;

    }

    public String getVoIP() {
        return voIP;
    }

    public void setVoIP(String voIP) {
        this.voIP = new String(voIP);
    }

    public String getId() {
        return employeeId;
    }

    public void setId(String id) {
        this.employeeId = new String(id);
    }

    public void setManagerId(String id){
        this.managerId = new String(id);
    }

    public String getManagerId(){
        return managerId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = new String(firstName);
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = new String(lastName);
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = new String(abbreviation);
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = new String (extension);
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = new String(role);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = new String(email);
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = new String(location);
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = new String(detail);
    }

}
