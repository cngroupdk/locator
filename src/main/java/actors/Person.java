package actors;

/**
 * Created by cano on 29.9.2016.
 */

public class Person {

    private String id;
    private String managerId;
    private String firstName;
    private String lastName;
    private String abbreviation;
    private String extension;
    private String voIP;
    private String role;
    private String email;
    private String location;
    private String detail;

    public Person()
    {
    }

    public Person(Person p){
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

    public Person(String firstName, String lastName, String abbreviation,
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
            Person otherPerson = (Person)obj;
            result = id.equals(otherPerson.id) && managerId.equals(otherPerson.managerId)
                    && firstName.equals(otherPerson.firstName) && lastName.equals(otherPerson.lastName)
                    && location.equals(otherPerson.location) && email.equals(otherPerson.email)
            ;
        } // end else

        return result;
    }

    @Override
    public int hashCode(){

        int result = 0;

        result = id.hashCode() * managerId.hashCode() * firstName.hashCode() * lastName.hashCode()
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
        return id;
    }

    public void setId(String id) {
        this.id = new String(id);
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
