package actors;


import javax.persistence.*;
import java.sql.ResultSet;

/**
 * Created by cano on 29.9.2016.
 */

@MappedSuperclass
public abstract class StaffMemberFactory implements StaffMember {

    @Id
    private String employee_id;
    private String manager_id;
    private String first_name;
    private String last_name;
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
        setFirstName(p.first_name);
        setLastName(p.last_name);
        setAbbreviation(p.abbreviation);
        setExtension(p.extension);
        setRole(p.role);
        setEmail(p.email);
        setLocation(p.location);
        setDetail(p.detail);
        setManagerId(p.manager_id);
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
            result = employee_id.equals(otherPerson.employee_id) && manager_id.equals(otherPerson.manager_id)
                    && first_name.equals(otherPerson.first_name) && last_name.equals(otherPerson.last_name)
                    && location.equals(otherPerson.location) && email.equals(otherPerson.email)
            ;
        } // end else

        return result;
    }

    @Override
    public int hashCode(){

        int result = 0;

        result = employee_id.hashCode() * manager_id.hashCode() * first_name.hashCode() * last_name.hashCode()
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
        return employee_id;
    }

    public void setId(String id) {
        this.employee_id = new String(id);
    }

    public void setManagerId(String id){
        this.manager_id = new String(id);
    }

    public String getManagerId(){
        return manager_id;
    }

    public String getFirstName() {
        return first_name;
    }

    public void setFirstName(String firstName) {
        this.first_name = new String(firstName);
    }

    public String getLastName() {
        return last_name;
    }

    public void setLastName(String lastName) {
        this.last_name = new String(lastName);
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
